package com.ilop.sthome.service;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.aliyun.alink.linksdk.channel.mobile.api.IMobileDownstreamListener;
import com.aliyun.alink.linksdk.channel.mobile.api.MobileChannel;
import com.aliyun.iot.aep.sdk.log.ALog;
import com.aliyun.iot.aep.sdk.login.LoginBusiness;
import com.example.common.base.OnCallBackToRefresh;
import com.example.common.utils.RxTimerUtil;
import com.ilop.sthome.common.ControllerWifi;
import com.ilop.sthome.common.SearchWifiHelper;
import com.ilop.sthome.data.bean.SceneAliBean;
import com.ilop.sthome.data.bean.SceneRelationBean;
import com.ilop.sthome.data.bean.ShortcutAliBean;
import com.ilop.sthome.data.bean.SysModelAliBean;
import com.ilop.sthome.data.db.SceneAliDAO;
import com.ilop.sthome.data.db.SceneRelaitonAliDAO;
import com.ilop.sthome.data.db.ShortcutAliDAO;
import com.ilop.sthome.data.db.SysmodelAliDAO;
import com.ilop.sthome.data.enums.SmartProduct;
import com.ilop.sthome.data.event.EventAnswerOK;
import com.ilop.sthome.data.event.EventRefreshDevice;
import com.ilop.sthome.data.event.EventRefreshLogs;
import com.ilop.sthome.data.event.EventRefreshScene;
import com.ilop.sthome.data.event.EventRefreshSubDeviceLogs;
import com.ilop.sthome.data.event.EventUdpReceive;
import com.ilop.sthome.data.event.STEvent;
import com.ilop.sthome.data.greenDao.DeviceInfoBean;
import com.ilop.sthome.data.greenDao.HistoryBean;
import com.ilop.sthome.data.greenDao.WarnBean;
import com.ilop.sthome.network.api.SendCommandAli;
import com.ilop.sthome.network.api.SendEquipmentDataAli;
import com.ilop.sthome.network.api.SendOtherDataAli;
import com.ilop.sthome.network.api.SendSceneDataAli;
import com.ilop.sthome.network.udp.GatewayUdpListConstant;
import com.ilop.sthome.network.udp.UDPRecData;
import com.ilop.sthome.network.udp.UDPSendData;
import com.ilop.sthome.ui.dialog.BaseDialog;
import com.ilop.sthome.utils.CoderALiUtils;
import com.ilop.sthome.utils.HistoryDataUtil;
import com.ilop.sthome.utils.NetWorkUtil;
import com.ilop.sthome.utils.greenDao.DeviceDaoUtil;
import com.ilop.sthome.utils.greenDao.HistoryDaoUtil;
import com.ilop.sthome.utils.greenDao.UserInfoDaoUtil;
import com.ilop.sthome.utils.greenDao.WarnDaoUtil;
import com.ilop.sthome.utils.tools.CacheUtil;
import com.ilop.sthome.utils.tools.ConnectionPojo;
import com.ilop.sthome.utils.tools.SiterSDK;
import com.ilop.sthome.utils.tools.UnitTools;
import com.siterwell.familywellplus.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.math.BigInteger;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/7/3.
 */

public class SiterService extends Service {
    private final String TAG = this.getClass().getName();
    private UDPRecData udpRecData;
    private ExecutorService mSendService,mReceiveService;
    private String now_SSId = null;
    private int now_netType = -1;
    private BaseDialog mDialog;
    private int mCount = 0;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.i(TAG,"on onCreate");
        EventBus.getDefault().register(this);
        mSendService = Executors.newSingleThreadExecutor();
        mReceiveService = Executors.newSingleThreadExecutor();
        if (null != mDownStreamListener) {
            MobileChannel.getInstance().unRegisterDownstreamListener(mDownStreamListener);
            MobileChannel.getInstance().registerDownstreamListener(true, mDownStreamListener);
        }
        initBroadcastReceiveUdp();
        //发现内网设备
        STEvent stEvent3 = new STEvent();
        stEvent3.setServiceevent(6);
        EventBus.getDefault().post(stEvent3);
        super.onCreate();
    }

    @SuppressLint("WrongConstant")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand()");
        flags = START_STICKY;
        return super.onStartCommand(intent, flags, startId);
    }


    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 11:
                    if(LoginBusiness.isLogin() && UserInfoDaoUtil.getInstance().getUserInfoDao().queryAll().size()>0) {
                        String deviceName = (String)msg.obj;
                        if(!TextUtils.isEmpty(deviceName)){
                            int currentGroup;
                            DeviceInfoBean deviceInfoBean = DeviceDaoUtil.getInstance().findGatewayByDeviceName(deviceName);
                            if(deviceInfoBean!=null){
                                SendSceneDataAli sendSceneDataAli = new SendSceneDataAli(SiterService.this,deviceInfoBean);
                                SysmodelAliDAO sysmodelAliDAO = new SysmodelAliDAO(SiterService.this);
                                SysModelAliBean sysModelAliBean = sysmodelAliDAO.findIdByChoice(deviceName);
                                if(sysModelAliBean!=null){
                                    currentGroup = sysModelAliBean.getSid();
                                    String crcscene= CoderALiUtils.getSceneCRC(SiterService.this,deviceName);
                                    String crcscene_group= CoderALiUtils.getSceneGroupCRC(SiterService.this,deviceName);
                                    sendSceneDataAli.synGetSceneInformation(currentGroup,crcscene_group,crcscene);
                                }
                            }
                        }
                    }
                    break;
                case 12:
                    if(LoginBusiness.isLogin() && UserInfoDaoUtil.getInstance().getUserInfoDao().queryAll().size()>0){
                        String deviceName1 = (String)msg.obj;
                        if(!TextUtils.isEmpty(deviceName1)) {
                            DeviceInfoBean deviceInfoBean = DeviceDaoUtil.getInstance().findGatewayByDeviceName(deviceName1);
                            if(deviceInfoBean!=null){
                                List<DeviceInfoBean> deviceInfoBeans = DeviceDaoUtil.getInstance().findAllSubDevice(deviceName1);
                                if(deviceInfoBeans.size()>0){
                                    String crc = CoderALiUtils.getEqNameCRC(SiterService.this,deviceName1);
                                    SendEquipmentDataAli sendEquipmentDataAli = new SendEquipmentDataAli(SiterService.this,deviceInfoBean);
                                    sendEquipmentDataAli.synGetDeviceName(crc);
                                }
                            }

                        }
                    }
                    break;
                case 20:
                    String deviceName = (String)msg.obj;
                    doDealUdpData(deviceName);
                    break;
            }
        }
    };



    @Subscribe          //订阅事件FirstEvent
    public  void onEventMainThread(STEvent event){
           switch (event.getServiceevent()){
               case 6:
                   STEvent stEvent3 = new STEvent();
                   stEvent3.setRefreshevent(7);
                   stEvent3.setProgressText(getResources().getString(R.string.search_local_net));
                   EventBus.getDefault().post(stEvent3);
                   RxTimerUtil.interval(1000, number -> {
                       mCount++;
                       if (mCount>3){
                           RxTimerUtil.cancel();
                       }else {
                           searchUdp();
                       }
                   });
                   break;
               case 7:
                   //监听网络变化做出相应动作
                   String newSSid = event.getSsid();
                   int netType = event.getNettype();
                   if (netType == 4) {
                       if ((!newSSid.equals(now_SSId) && !TextUtils.isEmpty(now_SSId)) || (now_netType < 4 && !TextUtils.isEmpty(now_SSId))) {
                           SearchWifiHelper.MyTaskCallback taskCallback3 = new SearchWifiHelper.MyTaskCallback() {
                               @Override
                               public void operationFailed() {
                                   Log.i(TAG, "+++++++++++++++++++++++++++++++++++++++++++++++ failed");
                                   udpRecData.close();
                               }

                               @Override
                               public void operationSuccess() {
                                   Log.i(TAG, "+++++++++++++++++++++++++++++++++++++++++++++++ success");
                                   initReceiveUdp();
                                   startUdp();
                               }

                               @Override
                               public void doReSendAction() {

                               }
                           };

                           SearchWifiHelper searchWifiHelper3 = new SearchWifiHelper(taskCallback3);
                           searchWifiHelper3.startReSend();
                       }
                       now_SSId = newSSid;
                   }
                   now_netType = netType;
                   break;
           }
    }

    private void searchUdp(){
        try {
            String localAddress = NetWorkUtil.getLocalIpAddress(this);
            InetAddress target = null;
            String targetip = localAddress.substring(0,localAddress.lastIndexOf(".")+1)+255;
            try {
                target = InetAddress.getByName(targetip);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            Log.i(TAG," 发送搜索udp广播地址 ===" + target.toString());
            String searchUdp = "{\"action\":\"IOT_KEY?\",\"devID\":\"NULL\"}";

            UDPSendData udpSendData = new UDPSendData(ControllerWifi.getInstance().ds,target,searchUdp);
            mSendService.execute(udpSendData);
            mSendService.awaitTermination(50, TimeUnit.MICROSECONDS);


        } catch (InterruptedException e) {
            e.printStackTrace();
        }catch (NullPointerException e){
            Log.i(TAG," targetip is null" );
        }
    }


    private void initBroadcastReceiveUdp() {
        Log.i(TAG,"initBroadcastReceiveUdp");
        if(udpRecData!=null){
            udpRecData.close();
        }
        if(mReceiveService!=null){
            mReceiveService.shutdown();
        }


        String localAddress = NetWorkUtil.getLocalIpAddress(this);
        InetAddress ip = null;
        try {
            ip = InetAddress.getByName(localAddress);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            Log.i(TAG, " send create ip failed");
        }



        InetAddress target = null;
            String targetip = localAddress.substring(0, localAddress.lastIndexOf(".") + 1) + 255;
        Log.i(TAG," 广播接收udp地址 ===" + targetip);
            try {
                target = InetAddress.getByName(targetip);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }

        DatagramSocket datagramSocket = null;
        try {

            datagramSocket = new DatagramSocket(1025, ip);
            ControllerWifi.getInstance().ds = datagramSocket;
        } catch (SocketException e) {
            e.printStackTrace();
        }


        mReceiveService = Executors.newSingleThreadExecutor();
        udpRecData = new UDPRecData(datagramSocket,target,this,0);
        mReceiveService.execute(udpRecData);
    }

    private void initReceiveUdp() {

            if(udpRecData!=null){
                udpRecData.close();
            }
            if(mReceiveService!=null){
                mReceiveService.shutdown();
            }

            Log.i(TAG,"initReceiveUdp");
            DatagramSocket datagramSocket = null;
            try {
                    datagramSocket = new DatagramSocket(null);
                    datagramSocket.setReuseAddress(true);
                    datagramSocket.connect(ControllerWifi.getInstance().targetip,1025);
                    ControllerWifi.getInstance().ds = datagramSocket;
            } catch (SocketException e) {
                e.printStackTrace();
            }
        Log.i(TAG," 接收udp地址 ===" + ControllerWifi.getInstance().targetip.toString());
        mReceiveService = Executors.newSingleThreadExecutor();
        udpRecData = new UDPRecData(ControllerWifi.getInstance().ds, ControllerWifi.getInstance().targetip,this,0);
        mReceiveService.execute(udpRecData);


    }


    private void startUdp(){
        try {
            Log.i(TAG," ControllerWifi.getInstance().targetip ===" + ControllerWifi.getInstance().targetip.toString());
            UDPSendData udpSendData = new UDPSendData(ControllerWifi.getInstance().ds, ControllerWifi.getInstance().targetip,"IOT_KEY?"+ ConnectionPojo.getInstance().deviceTid+":"+ CacheUtil.getString(SiterSDK.SETTINGS_CONFIG_UDP_SETTING,""));
            mSendService.execute(udpSendData);
            mSendService.awaitTermination(50, TimeUnit.MICROSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }catch (NullPointerException e){
            Log.i(TAG," targetip is null" );
        }
    }


    private IMobileDownstreamListener mDownStreamListener = new IMobileDownstreamListener() {
        @Override
        public void onCommand(String method, String data) {
            ALog.d(TAG, "接收到Topic = " + method + ", data=" + data);
            doDealData(data);
        }

        @Override
        public boolean shouldHandle(String method) {
            return true;

        }
    };

    @Subscribe          //订阅事件FirstEvent
    public  void onEventMainThread(EventUdpReceive event){
        Log.i(TAG,"内网接收数据："+event.getMsg());
        Message message = Message.obtain();
        message.obj = event.getMsg();
        message.what = 20;
        handler.sendMessage(message);
    }

    public  void doDealUdpData(String data){
        try {
            com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(data);
            String deviceName = jsonObject.getString("devID");
            com.alibaba.fastjson.JSONObject jsonObject1;
            jsonObject1 = jsonObject.getJSONObject("msg");
            if("NODE_SEND".equalsIgnoreCase(jsonObject.getString("action"))){
                    SendOtherDataAli sendOtherDataAli = new SendOtherDataAli(this, GatewayUdpListConstant.getInstance().getDeviceIfoBeanByName(deviceName));
                    sendOtherDataAli.dataGetOk(11);//反馈数据为 11 固定值
            }
            if(!LoginBusiness.isLogin()|| UserInfoDaoUtil.getInstance().getUserInfoDao().queryAll().size()==0){
                return;
            }

            try{
                String content = jsonObject1.getString("alarmMessage");
                if(content!=null){
                    AliAlertPushInfo(content,deviceName);
                    return;
                }
            }catch (Exception e){
                Log.i(TAG,"报警不处理事件");
                e.printStackTrace();
            }

            int cmd = jsonObject1.getIntValue("CMD_CODE");
            if (cmd == SendCommandAli.UPLOAD_DEVICE_STATUS) {
                String content = jsonObject1.getString("data_str1");
                if (content.length() == 8) {
                    String status = jsonObject1.getString("data_str2");
                    uploadDeviceStatus(deviceName, content, status);
                }
            } else if (cmd == SendCommandAli.UPLOAD_TIMER_INFO) {
                String content = jsonObject1.getString("data_str2");
            } else if (cmd == SendCommandAli.SEND_ACK) {
                String content = jsonObject1.toString();
                sendAck(content);
            } else if (cmd == SendCommandAli.UPLOAD_CURRENT_SCENE_GROUP) {
                String current = jsonObject1.getString("data_str1");
                uploadCurrentSceneGroup(deviceName, current);
            } else if (cmd == SendCommandAli.UPLOAD_SCENE_GROUP_INFO) {
                String content = jsonObject1.getString("data_str2");
                uploadSceneGroupInfo(deviceName, content);
            } else if (cmd == SendCommandAli.UPLOAD_SCENE_INFO) {
                String content = jsonObject1.getString("data_str2");
                uploadSceneInfo(deviceName, content);
            } else if (cmd == SendCommandAli.UPLOAD_DEVICE_NAME) {
                String content = jsonObject1.getString("data_str2");
                uploadDeviceName(deviceName, content);
            } else if (cmd == SendCommandAli.UPLOAD_ALARM_LOGS_INFO) {
                String page = jsonObject1.getString("data_str1");
                String content = jsonObject1.getString("data_str2");
                uploadAlarmLogsInfo(deviceName, page, content);
            } else if (cmd == SendCommandAli.UPLOAD_SUBDEVICE_ALARM_LOGS_INFO) {
                String page = jsonObject1.getString("data_str1");
                String content = jsonObject1.getString("data_str2");
                uploadSubDeviceAlarmLogsInfo(deviceName, page, content);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doDealData(String data){
        try {
            com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(data);
            String deviceName = jsonObject.getString("deviceName");
            com.alibaba.fastjson.JSONObject jsonObject1;

            jsonObject1 = jsonObject.getJSONObject("value");

            String eventType = jsonObject.getString("name");

            if(GatewayUdpListConstant.getInstance().checkByname(deviceName)!=null
                    &&GatewayUdpListConstant.getInstance().checkByname(deviceName).isOnline()){//如果是内网 则外网退出
                return;
            }

            if(!LoginBusiness.isLogin() || UserInfoDaoUtil.getInstance().getUserInfoDao().queryAll().size()>0){
                return;
            }

            if("事件推送".equals(eventType)){
                String content = jsonObject1.getString("alarmMessage");
                AliAlertPushInfo(content,deviceName);
                return;
            }

            int cmd = jsonObject1.getIntValue("CMD_CODE");
                if (cmd == SendCommandAli.UPLOAD_DEVICE_STATUS) {
                    String content = jsonObject1.getString("data_str1");
                    if (content.length() == 8) {
                        String status = jsonObject1.getString("data_str2");
                        uploadDeviceStatus(deviceName, content, status);
                    }
                } else if (cmd == SendCommandAli.UPLOAD_TIMER_INFO) {
                    String content = jsonObject1.getString("data_str2");
                } else if (cmd == SendCommandAli.SEND_ACK) {
                    String content = jsonObject1.toString();
                    sendAck(content);
                } else if (cmd == SendCommandAli.UPLOAD_CURRENT_SCENE_GROUP) {
                    String current = jsonObject1.getString("data_str1");
                    uploadCurrentSceneGroup(deviceName, current);
                } else if (cmd == SendCommandAli.UPLOAD_SCENE_GROUP_INFO) {
                    String content = jsonObject1.getString("data_str2");
                    uploadSceneGroupInfo(deviceName, content);
                } else if (cmd == SendCommandAli.UPLOAD_SCENE_INFO) {
                    String content = jsonObject1.getString("data_str2");
                    uploadSceneInfo(deviceName, content);
                } else if (cmd == SendCommandAli.UPLOAD_DEVICE_NAME) {
                    String content = jsonObject1.getString("data_str2");
                    uploadDeviceName(deviceName, content);
                } else if (cmd == SendCommandAli.UPLOAD_ALARM_LOGS_INFO) {
                    String page = jsonObject1.getString("data_str1");
                    String content = jsonObject1.getString("data_str2");
                    uploadAlarmLogsInfo(deviceName, page, content);
                } else if (cmd == SendCommandAli.UPLOAD_SUBDEVICE_ALARM_LOGS_INFO) {
                    String page = jsonObject1.getString("data_str1");
                    String content = jsonObject1.getString("data_str2");
                    uploadSubDeviceAlarmLogsInfo(deviceName, page, content);
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void uploadDeviceStatus(String deviceName, String content, String status){
        String device_id = content.substring(0, 4);
        String device_type = content.substring(4, 8);

        if ("syncOVER".equals(status)) {
            Message message = Message.obtain();
            message.what = 11;
            message.obj = deviceName;
            handler.sendMessageDelayed(message, 0);
        } else if ("00000000".equals(status)) {
            int dev_id = Integer.parseInt(device_id, 16);
            DeviceDaoUtil.getInstance().deleteByDeviceName(deviceName, dev_id);
            EventRefreshDevice eventRefreshDevice = new EventRefreshDevice();
            eventRefreshDevice.setDevice_id(dev_id);
            eventRefreshDevice.setDevice_type(device_type);
            eventRefreshDevice.setDeviceName(deviceName);
            eventRefreshDevice.setDevice_status(status);
            EventBus.getDefault().post(eventRefreshDevice);
        } else {
            int dev_id = Integer.parseInt(device_id, 16);
            int has_device = 0;
            if (dev_id > 0) {
                DeviceDaoUtil.getInstance().HasThisDevice(deviceName, dev_id);
                if (DeviceDaoUtil.getInstance().HasThisDevice(deviceName, dev_id)) {
                    has_device = 1;
                }
                DeviceInfoBean model = new DeviceInfoBean();
                model.setDevice_ID(dev_id);
                model.setDevice_type(device_type);
                model.setDevice_status(status);
                model.setDeviceName(deviceName);
                DeviceDaoUtil.getInstance().insertSubDevice(model);
            }

            EventRefreshDevice eventRefreshDevice = new EventRefreshDevice();
            eventRefreshDevice.setType(has_device);
            eventRefreshDevice.setDevice_id(dev_id);
            eventRefreshDevice.setDevice_type(device_type);
            eventRefreshDevice.setDeviceName(deviceName);
            eventRefreshDevice.setDevice_status(status);
            EventBus.getDefault().post(eventRefreshDevice);
        }
    }

    private void uploadDeviceName(String deviceName, String content){
        if ("NAME_OVER".equals(content)) {
            EventRefreshDevice eventRefreshDevice = new EventRefreshDevice();
            EventBus.getDefault().post(eventRefreshDevice);
        } else {
            int ds = Integer.parseInt(content.substring(0, 4), 16);
            String value = content.substring(4);
            if (value.length() == 32) {
                String lastName = CoderALiUtils.getStringFromAscii(value);
                Log.i(TAG, "name+++++" + lastName);
                DeviceInfoBean deviceInfoBean = new DeviceInfoBean();
                deviceInfoBean.setDevice_ID(ds);
                deviceInfoBean.setDeviceName(deviceName);
                deviceInfoBean.setSubdeviceName(lastName);
                DeviceDaoUtil.getInstance().getDeviceDao().update(deviceInfoBean);
            } else {
                DeviceInfoBean deviceInfoBean = new DeviceInfoBean();
                deviceInfoBean.setDevice_ID(ds);
                deviceInfoBean.setDeviceName(deviceName);
                deviceInfoBean.setSubdeviceName("");
                DeviceDaoUtil.getInstance().getDeviceDao().update(deviceInfoBean);
            }
        }
    }

    private void uploadCurrentSceneGroup(String deviceName, String current){
        int sid = Integer.parseInt(current, 16);
        SysmodelAliDAO sysmodelAliDAO = new SysmodelAliDAO(SiterService.this);
        sysmodelAliDAO.updateChoice(sid, deviceName);
        DeviceDaoUtil.getInstance().updateGatewayCurrentSid(deviceName, sid);
        EventRefreshScene eventRefreshDevice = new EventRefreshScene();
        eventRefreshDevice.setDeviceName(deviceName);
        EventBus.getDefault().post(eventRefreshDevice);
    }

    private void uploadSceneGroupInfo(String deviceName, String content){
        if (content.startsWith("0000")) {
            int sid = Integer.parseInt(content.substring(4), 16);
            SceneRelaitonAliDAO sceneRelaitonAliDAO = new SceneRelaitonAliDAO(SiterService.this);
            ShortcutAliDAO shortcutAliDAO = new ShortcutAliDAO(SiterService.this);
            sceneRelaitonAliDAO.deleteAllShortcurt(sid, deviceName);
            SysmodelAliDAO sysmodelAliDAO = new SysmodelAliDAO(SiterService.this);
            if (sid <= 2) {
                sysmodelAliDAO.updateColor(sid, "F" + sid, deviceName);
            } else {
                sysmodelAliDAO.delete(sid, deviceName);
            }
            sceneRelaitonAliDAO.deleteAllShortcurt(sid, deviceName);
            shortcutAliDAO.deleteAllShortcurt(sid, deviceName);
        } else {
            SysModelAliBean sysModelAliBean = new SysModelAliBean();
            sysModelAliBean.setCode(content);
            sysModelAliBean.setDeviceName(deviceName);
            if (content.length() >= 32) {
                sysModelAliBean.create(deviceName);
                SceneRelaitonAliDAO sceneRelaitonAliDAO = new SceneRelaitonAliDAO(SiterService.this);
                SysmodelAliDAO sysmodelAliDAO = new SysmodelAliDAO(SiterService.this);
                sysmodelAliDAO.insertSysmodel(sysModelAliBean);

                if (sysModelAliBean.getSceneRelationBeanList() != null && sysModelAliBean.getSceneRelationBeanList().size() > 0) {

                    sceneRelaitonAliDAO.deleteAllShortcurt(sysModelAliBean.getSid(), deviceName);
                    for (SceneRelationBean sceneRelationBean : sysModelAliBean.getSceneRelationBeanList()) {
                        sceneRelaitonAliDAO.insertSceneRelation(sceneRelationBean);
                    }
                }

                if (sysModelAliBean.getShortcutAliBeanList() != null && sysModelAliBean.getShortcutAliBeanList().size() > 0) {
                    ShortcutAliDAO shortcutAliDAO = new ShortcutAliDAO(SiterService.this);
                    shortcutAliDAO.deleteAllShortcurt(sysModelAliBean.getSid(), deviceName);
                    for (ShortcutAliBean shortcutAliBean : sysModelAliBean.getShortcutAliBeanList()) {
                        shortcutAliDAO.insertShortcut(shortcutAliBean);
                    }
                }

                if ((sysModelAliBean.getScene_default() & 0x01) != 0) {
                    SceneRelationBean sceneRelationBean = new SceneRelationBean();
                    sceneRelationBean.setSid(sysModelAliBean.getSid());
                    sceneRelationBean.setMid(129);
                    sceneRelationBean.setDeviceName(deviceName);
                    sceneRelaitonAliDAO.insertSceneRelation(sceneRelationBean);
                } else {
                    sceneRelaitonAliDAO.deleteRelation(129, sysModelAliBean.getSid(), deviceName);
                }

                if ((sysModelAliBean.getScene_default() & 0x02) != 0) {
                    SceneRelationBean sceneRelationBean = new SceneRelationBean();
                    sceneRelationBean.setSid(sysModelAliBean.getSid());
                    sceneRelationBean.setMid(130);
                    sceneRelationBean.setDeviceName(deviceName);
                    sceneRelaitonAliDAO.insertSceneRelation(sceneRelationBean);
                } else {
                    sceneRelaitonAliDAO.deleteRelation(130, sysModelAliBean.getSid(), deviceName);
                }

                if ((sysModelAliBean.getScene_default() & 0x04) != 0) {
                    SceneRelationBean sceneRelationBean = new SceneRelationBean();
                    sceneRelationBean.setSid(sysModelAliBean.getSid());
                    sceneRelationBean.setMid(131);
                    sceneRelationBean.setDeviceName(deviceName);
                    sceneRelaitonAliDAO.insertSceneRelation(sceneRelationBean);
                } else {
                    sceneRelaitonAliDAO.deleteRelation(131, sysModelAliBean.getSid(), deviceName);
                }
            }


        }
    }

    private void uploadSceneInfo(String deviceName, String content){
        if ("OVER".equals(content)) {
            int current_mode = DeviceDaoUtil.getInstance().findGatewayCurrentSid(deviceName);
            if (current_mode >= 0) {
                SysmodelAliDAO sysmodelAliDAO = new SysmodelAliDAO(SiterService.this);
                sysmodelAliDAO.updateChoice(current_mode, deviceName);
            }
            Message message = Message.obtain();
            message.obj = deviceName;
            message.what = 12;
            handler.sendMessageDelayed(message, 0);
        } else if (content.startsWith("0000")) {
            int mid = Integer.parseInt(content.substring(4, 6), 16);
            SceneRelaitonAliDAO sceneRelaitonAliDAO = new SceneRelaitonAliDAO(SiterService.this);
            SceneAliDAO sceneAliDAO = new SceneAliDAO(SiterService.this);
            sceneAliDAO.deleteByMid(mid, deviceName);
            sceneRelaitonAliDAO.deleteAllShortcurt2(mid, deviceName);
        } else {
            SceneAliBean sceneAliBean = new SceneAliBean();
            sceneAliBean.setCode(content);
            sceneAliBean.create(deviceName);
            if (sceneAliBean.getMid() > 0) {
                SceneAliDAO sceneAliDAO = new SceneAliDAO(SiterService.this);
                sceneAliDAO.insertScene(sceneAliBean);
            }
        }
    }

    private void sendAck(String content){
        EventAnswerOK eventAnswerOK = JSON.parseObject(content, EventAnswerOK.class);
        EventBus.getDefault().post(eventAnswerOK);
    }

    private void uploadAlarmLogsInfo(String deviceName, String page, String content){
        WarnDaoUtil.getInstance().getWarnDao().deleteAll();
        if (content.length() > 0 && content.endsWith("OVER")) {
            if (content.length() % 30 == 4) {
                for (int i = 0; i < content.length() / 30; i++) {
                    String c = content.substring(i * 30, (i + 1) * 30);
                    String time = c.substring(18, 26);
                    BigInteger big = new BigInteger(time.trim(), 16);
                    long time1 = big.longValue();
                    String type = c.substring(0, 2);

                    if ("AD".equals(type)) {
                        int eqid = Integer.parseInt(c.substring(2, 6), 16);
                        String eqtype = c.substring(6, 10);
                        String eqstatus = c.substring(10, 18);
                        WarnBean warnBean = new WarnBean();
                        warnBean.setTime(time1);
                        warnBean.setType(type);
                        warnBean.setDevice_id(eqid);
                        warnBean.setDevice_type(eqtype);
                        warnBean.setDevice_status(eqstatus);
                        warnBean.setDeviceName(deviceName);
                        warnBean.setCode(c);
                        WarnDaoUtil.getInstance().getWarnDao().insert(warnBean);
                    } else if ("AC".equals(type)) {
                        int mid = Integer.parseInt(c.substring(2, 4), 16);
                        WarnBean warnBean = new WarnBean();
                        warnBean.setTime(time1);
                        warnBean.setType(type);
                        warnBean.setMid(mid);
                        warnBean.setDeviceName(deviceName);
                        warnBean.setCode(c);
                        WarnDaoUtil.getInstance().getWarnDao().insert(warnBean);
                    }

                }

                if (content.length() / 30 >= 10) {
                    int pagenew = Integer.parseInt(page, 16);
                    EventRefreshLogs eventRefreshLogs = new EventRefreshLogs();
                    eventRefreshLogs.setDeviceName(deviceName);
                    eventRefreshLogs.setIs_over(0);
                    eventRefreshLogs.setPage(pagenew);
                    EventBus.getDefault().post(eventRefreshLogs);

                } else {
                    int pagenew = Integer.parseInt(page, 16);
                    EventRefreshLogs eventRefreshLogs = new EventRefreshLogs();
                    eventRefreshLogs.setDeviceName(deviceName);
                    eventRefreshLogs.setIs_over(1);
                    eventRefreshLogs.setPage(pagenew);
                    EventBus.getDefault().post(eventRefreshLogs);
                }
            }
        } else if (content.equals("0000OVER")) {
            EventRefreshLogs eventRefreshLogs = new EventRefreshLogs();
            eventRefreshLogs.setDeviceName(deviceName);
            eventRefreshLogs.setIs_over(1);
            eventRefreshLogs.setPage(Integer.parseInt(page, 16));
            EventBus.getDefault().post(eventRefreshLogs);
        }
    }

    private void uploadSubDeviceAlarmLogsInfo(String deviceName, String page, String content){
        HistoryDaoUtil.getInstance().getHistoryDao().deleteAll();
        if (content.length() > 0 && page.length() == 6) {
            if (content.length() % 24 == 4 && content.endsWith("OVER")) {
                List<HistoryBean> list = new ArrayList<>();
                for (int i = 0; i < content.length() / 24; i++) {
                    String c = content.substring(i * 24, (i + 1) * 24);
                    String time = c.substring(16, 24);
                    BigInteger big = new BigInteger(time.trim(), 16);
                    long time1 = big.longValue();
                    int eqid = Integer.parseInt(c.substring(0, 4), 16);
                    String eqtype = c.substring(4, 8);
                    String eqstatus = c.substring(8, 16);
                    HistoryBean warnBean = new HistoryBean();
                    warnBean.setTime(time1);
                    warnBean.setDevice_id(eqid);
                    warnBean.setDevice_type(eqtype);
                    warnBean.setDevice_status(eqstatus);
                    warnBean.setDeviceName(deviceName);
                    warnBean.setCode(c);
                    list.add(warnBean);
                    if (eqid > 0) {
                        HistoryDaoUtil.getInstance().getHistoryDao().insert(warnBean);
                    }
                }

                if (content.length() / 24 >= 10) {
                    int pagen = Integer.parseInt(page.substring(0, 2), 16);
                    EventRefreshSubDeviceLogs eventRefreshLogs = new EventRefreshSubDeviceLogs();
                    eventRefreshLogs.setDeviceName(deviceName);
                    eventRefreshLogs.setIs_over(0);
                    eventRefreshLogs.setEqid(Integer.parseInt(page.substring(2, 6), 16));
                    eventRefreshLogs.setPage(pagen);
                    eventRefreshLogs.setList(list);
                    EventBus.getDefault().post(eventRefreshLogs);
                } else {
                    int pagen = Integer.parseInt(page.substring(0, 2), 16);
                    EventRefreshSubDeviceLogs eventRefreshLogs = new EventRefreshSubDeviceLogs();
                    eventRefreshLogs.setDeviceName(deviceName);
                    eventRefreshLogs.setIs_over(1);
                    eventRefreshLogs.setEqid(Integer.parseInt(page.substring(2, 6), 16));
                    eventRefreshLogs.setPage(pagen);
                    eventRefreshLogs.setList(list);
                    EventBus.getDefault().post(eventRefreshLogs);
                }
            } else if (content.endsWith("OVER")) {
                EventRefreshSubDeviceLogs eventRefreshLogs = new EventRefreshSubDeviceLogs();
                eventRefreshLogs.setDeviceName(deviceName);
                eventRefreshLogs.setIs_over(1);
                eventRefreshLogs.setEqid(Integer.parseInt(page.substring(2, 6), 16));
                eventRefreshLogs.setPage(Integer.parseInt(page.substring(0, 2), 16));
                eventRefreshLogs.setList(new ArrayList<>());
                EventBus.getDefault().post(eventRefreshLogs);
            }
        }
    }



    /**
     * resoleLocalAlertPushInfo:选择的推送帧解析
     * 作者：Henry on 2017/3/13 14:24
     * 邮箱：xuejunju_4595@qq.com
     * 参数:mid 代表自动化id, flag为true标识当前网关，false代表其他未选择的网关，deviceid为网关id;
     * 返回:
     */
    private void AliAlertPushInfo(String info,String deviceName){
        try {
            String type = info.substring(4,6);
            if("AC".equals(type)){
                int mid = Integer.parseInt(info.substring(6,8),16);
                doAliSceneAlertShow(mid,deviceName);
            }else if("AD".equals(type)) {

                int eqid     = Integer.parseInt(info.substring(6,10),16);
                String device_type   = info.substring(10,14);
                String device_status = info.substring(14,22);
                DeviceInfoBean applicationInfo = new DeviceInfoBean();
                applicationInfo.setDeviceName(deviceName);
                applicationInfo.setDevice_ID(eqid);
                applicationInfo.setDevice_type(device_type);
                applicationInfo.setDevice_status(device_status);
                String status="";
                if(applicationInfo.getDevice_ID()==0){
                    if("00000000".equals(applicationInfo.getDevice_status())){
                        status = getResources().getString(R.string.electric_city_break_off);
                    }else if("00000001".equals(applicationInfo.getDevice_status())){
                        status = getResources().getString(R.string.electric_city_normal);
                    }else if("00000002".equals(applicationInfo.getDevice_status())){
                        status = getResources().getString(R.string.battery_normal);
                    }else if("00000003".equals(applicationInfo.getDevice_status())){
                        status = getResources().getString(R.string.battery_low);
                    }
                    doAliGatewayAlertShow(status,deviceName);
                }else{
                    DeviceInfoBean deviceInfoBean = new DeviceInfoBean();
                    deviceInfoBean.setDevice_ID(eqid);
                    deviceInfoBean.setDevice_status(device_status);
                    deviceInfoBean.setDevice_type(device_type);
                    deviceInfoBean.setDeviceName(deviceName);
                    DeviceDaoUtil.getInstance().insertSubDevice(deviceInfoBean);

                    EventRefreshDevice eventRefreshDevice = new EventRefreshDevice();
                    eventRefreshDevice.setType(1);
                    eventRefreshDevice.setDevice_id(eqid);
                    eventRefreshDevice.setDevice_type(device_type);
                    eventRefreshDevice.setDeviceName(deviceName);
                    eventRefreshDevice.setDevice_status(device_status);
                    EventBus.getDefault().post(eventRefreshDevice);

                    doAliAlertShow(applicationInfo.getDevice_status(),applicationInfo.getDevice_type(),applicationInfo.getDevice_ID(),deviceName);

                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * doSceneAlertShow:
     * 作者：Henry on 2017/3/13 15:26
     * 邮箱：xuejunju_4595@qq.com
     * 参数:mid 代表情景id, flag为true标识当前网关，false代表其他未选择的网关，deviceid为网关id;
     * 返回:
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void doAliSceneAlertShow(int mid,String deviceName){
        try {
            SceneAliDAO SED = new SceneAliDAO(this);
            String SceneName;
            String dname;


            DeviceInfoBean myDeviceBean =  DeviceDaoUtil.getInstance().findGatewayByDeviceName(deviceName);

            if(myDeviceBean == null){
                dname = getResources().getString(R.string.ali_gateway)+":"+"("+deviceName+")";
            }else{
                if(TextUtils.isEmpty(myDeviceBean.getNickName())){
                    dname = getResources().getString(R.string.ali_gateway)+":"+"("+deviceName+")";
                }else {
                    dname =myDeviceBean.getDeviceName();
                }

            }
            SceneAliBean sceneAliBean = SED.findScenceBymid(mid,deviceName);


            String title = "";
                if(sceneAliBean == null){
                    title = String.format(getResources().getString(R.string.ali_gateway_scene_is_happen_has_no_scene),dname,mid);
                }else{
                    SceneName = sceneAliBean.getName();
                    title = String.format(getResources().getString(R.string.ali_gateway_scene_is_happen_has_scene),dname,SceneName);
                }

            if(mDialog==null||!mDialog.isShowing()){
                mDialog = new BaseDialog(this, new OnCallBackToRefresh() {
                    @Override
                    public void onConfirm() {
                        try {
                            UnitTools.stopMusic(SiterService.this);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onCancel() {
                        try {
                            UnitTools.stopMusic(SiterService.this);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        DeviceInfoBean deviceInfoBean = DeviceDaoUtil.getInstance().findGatewayByDeviceName(deviceName);
                        if(deviceInfoBean!=null){
                            SendEquipmentDataAli sendEquipmentDataAli = new SendEquipmentDataAli(SiterService.this,deviceInfoBean);
                            sendEquipmentDataAli.sendGateWaySilence();
                        }
                    }
                });
                mDialog.setTitleAndButton(title, getString(R.string.btn_silence), getString(R.string.ok));
                mDialog.show();
            }
            UnitTools.playNotifycationSound(this);
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    /**
     * doGatewayAlertShow:
     * 作者：Henry on 2017/3/13 15:47
     * 邮箱：xuejunju_4595@qq.com
     * 参数:aaaa:devicestatus,eqid:设备id,deviceid:网关id
     * 返回:
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void doAliGatewayAlertShow(String status,String deviceName){

        String gateway = "";
        String title = "";

        DeviceInfoBean deviceInfoBean = DeviceDaoUtil.getInstance().findGatewayByDeviceName(deviceName);

        if(deviceInfoBean==null){
            gateway = getResources().getString(R.string.ali_gateway)+":"+"("+deviceName+")";
        }else {
            if(TextUtils.isEmpty(deviceInfoBean.getNickName())){
                gateway = getResources().getString(R.string.ali_gateway)+":"+"("+deviceName+")";
            }else {
                gateway = deviceInfoBean.getNickName();
            }
        }

        title = String.format(getResources().getString(R.string.ali_gateway_eq_is_happen_has_eq),gateway,status,"");

        if(mDialog==null||!mDialog.isShowing()){
            mDialog = new BaseDialog(this, new OnCallBackToRefresh() {
                @Override
                public void onConfirm() {
                    try {
                        UnitTools.stopMusic(SiterService.this);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onCancel() {
                    try {
                        UnitTools.stopMusic(SiterService.this);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    DeviceInfoBean deviceInfoBean = DeviceDaoUtil.getInstance().findGatewayByDeviceName(deviceName);
                    if(deviceInfoBean!=null){
                        SendEquipmentDataAli sendEquipmentDataAli = new SendEquipmentDataAli(SiterService.this,deviceInfoBean);
                        sendEquipmentDataAli.sendGateWaySilence();
                    }
                }
            });
            mDialog.setTitleAndButton(title, getString(R.string.btn_silence), getString(R.string.ok));
            mDialog.show();
        }
        UnitTools.playNotifycationSound(this);
    }

    /**
     * doAlertShow:
     * 作者：Henry on 2017/3/13 15:47
     * 邮箱：xuejunju_4595@qq.com
     * 参数:aaaa:devicestatus,eqid:设备id,deviceid:网关id
     * 返回:
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void doAliAlertShow(String status,String dev_type,int eqid,String deviceName){
        String ds = "";
        String place = "";
        String gateway = "";
        String title = "";


        DeviceInfoBean deviceBean = DeviceDaoUtil.getInstance().findGatewayByDeviceName(deviceName);
        DeviceInfoBean equipmentBean = DeviceDaoUtil.getInstance().findByDeviceId(deviceName,eqid);

        if(deviceBean == null){
            gateway = getResources().getString(R.string.ali_gateway)+"("+deviceName+")";
        }else{

            if(TextUtils.isEmpty(deviceBean.getNickName())){

                gateway = getResources().getString(R.string.ali_gateway)+"("+deviceName+")";
            }else {
                gateway = deviceBean.getNickName();
            }
        }

        if(equipmentBean==null){
            place = SmartProduct.getType(dev_type).getDevType()+eqid;
        }else{
            String eqname = equipmentBean.getSubdeviceName();
            if(TextUtils.isEmpty(eqname)){
                place = getResources().getString(SmartProduct.getType(dev_type).getTypeStrId())+eqid;
            }else{
                place = eqname;
            }
        }

        ds = HistoryDataUtil.getAlert(this,dev_type,status);

        title = String.format(getResources().getString(R.string.ali_gateway_eq_is_happen_has_eq),gateway,place,ds);


        if(mDialog==null||!mDialog.isShowing()){
            mDialog = new BaseDialog(this, new OnCallBackToRefresh() {
                @Override
                public void onConfirm() {
                    try {
                        UnitTools.stopMusic(SiterService.this);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onCancel() {
                    try {
                        UnitTools.stopMusic(SiterService.this);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    DeviceInfoBean deviceInfoBean = DeviceDaoUtil.getInstance().findGatewayByDeviceName(deviceName);
                    if(deviceInfoBean!=null){
                        SendEquipmentDataAli sendEquipmentDataAli = new SendEquipmentDataAli(SiterService.this,deviceInfoBean);
                        sendEquipmentDataAli.sendGateWaySilence();
                    }
                }
            });
            mDialog.setTitleAndButton(title, "", getString(R.string.ok));
            mDialog.show();
        }
        UnitTools.playNotifycationSound(this);
    }

    @Override
    public void onDestroy(){
        handler.removeCallbacksAndMessages(null);
        EventBus.getDefault().unregister(this);
        if(udpRecData!=null){
            udpRecData.close();
        }

        if(!mReceiveService.isShutdown()){
            mReceiveService.shutdown();
        }

        if(!mSendService.isShutdown()){
            mSendService.shutdown();
        }
        RxTimerUtil.cancel();
    }
}
