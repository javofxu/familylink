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
import com.ilop.sthome.data.enums.SmartProduct;
import com.ilop.sthome.data.event.EventAnswerOK;
import com.ilop.sthome.data.event.EventRefreshDevice;
import com.ilop.sthome.data.event.EventRefreshLogs;
import com.ilop.sthome.data.event.EventRefreshScene;
import com.ilop.sthome.data.event.EventRefreshSubDeviceLogs;
import com.ilop.sthome.data.greenDao.AutomationBean;
import com.ilop.sthome.data.greenDao.DeviceInfoBean;
import com.ilop.sthome.data.greenDao.HistoryBean;
import com.ilop.sthome.data.greenDao.SceneBean;
import com.ilop.sthome.data.greenDao.SceneRelationBean;
import com.ilop.sthome.data.greenDao.SceneSwitchBean;
import com.ilop.sthome.data.greenDao.WarnBean;
import com.ilop.sthome.network.api.SendCommandAli;
import com.ilop.sthome.network.api.SendEquipmentDataAli;
import com.ilop.sthome.network.api.SendSceneDataAli;
import com.ilop.sthome.ui.dialog.BaseDialog;
import com.ilop.sthome.utils.CoderALiUtils;
import com.ilop.sthome.utils.HistoryDataUtil;
import com.ilop.sthome.utils.greenDao.AutomationDaoUtil;
import com.ilop.sthome.utils.greenDao.DeviceDaoUtil;
import com.ilop.sthome.utils.greenDao.HistoryDaoUtil;
import com.ilop.sthome.utils.greenDao.SceneDaoUtil;
import com.ilop.sthome.utils.greenDao.SceneRelationDaoUtil;
import com.ilop.sthome.utils.greenDao.SceneSwitchDaoUtil;
import com.ilop.sthome.utils.greenDao.UserInfoDaoUtil;
import com.ilop.sthome.utils.greenDao.WarnDaoUtil;
import com.ilop.sthome.utils.tools.UnitTools;
import com.siterwell.familywellplus.R;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/3.
 */

public class SiterService extends Service {
    private final String TAG = this.getClass().getName();
    private BaseDialog mDialog;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.i(TAG,"on onCreate");
        if (null != mDownStreamListener) {
            MobileChannel.getInstance().unRegisterDownstreamListener(mDownStreamListener);
            MobileChannel.getInstance().registerDownstreamListener(true, mDownStreamListener);
        }
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
                                SceneBean mScene = SceneDaoUtil.getInstance().findSceneByChoice(deviceName);
                                if(mScene!=null){
                                    currentGroup = mScene.getSid();
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
            }
        }
    };

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

    /**
     * 处理数据
     * @param data
     */
    public void doDealData(String data){
        try {
            com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(data);
            String deviceName = jsonObject.getString("deviceName");
            com.alibaba.fastjson.JSONObject jsonObject1;

            jsonObject1 = jsonObject.getJSONObject("value");

            String eventType = jsonObject.getString("name");

            if("事件推送".equals(eventType)){
                String content = jsonObject1.getString("alarmMessage");
                AliAlertPushInfo(content,deviceName);
                return;
            }

            int cmd = jsonObject1.getIntValue("CMD_CODE");
            String content = jsonObject1.getString("data_str1");
            String content2 = jsonObject1.getString("data_str2");
            Log.i(TAG, "doDealData: " + cmd);
            switch (cmd){
                case SendCommandAli.SEND_ACK:
                    sendAck(jsonObject1.toString());
                    break;
                case SendCommandAli.UPLOAD_DEVICE_STATUS:
                    if (content.length() == 8) {
                        uploadDeviceStatus(deviceName, content, content2);
                    }
                    break;
                case SendCommandAli.UPLOAD_CURRENT_SCENE_GROUP:
                    uploadCurrentSceneGroup(deviceName, content);
                    break;
                case SendCommandAli.UPLOAD_SCENE_GROUP_INFO:
                    uploadSceneGroupInfo(deviceName, content2);
                    break;
                case SendCommandAli.UPLOAD_SCENE_INFO:
                    uploadSceneInfo(deviceName, content);
                    break;
                case SendCommandAli.UPLOAD_DEVICE_NAME:
                    uploadDeviceName(deviceName, content);
                    break;
                case SendCommandAli.UPLOAD_ALARM_LOGS_INFO:
                    uploadAlarmLogsInfo(deviceName, content, content2);
                    break;
                case SendCommandAli.UPLOAD_SUBDEVICE_ALARM_LOGS_INFO:
                    uploadSubDeviceAlarmLogsInfo(deviceName, content, content2);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendAck(String content){
        EventAnswerOK eventAnswerOK = JSON.parseObject(content, EventAnswerOK.class);
        EventBus.getDefault().post(eventAnswerOK);
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
                DeviceInfoBean deviceInfoBean = DeviceDaoUtil.getInstance().findByDeviceId(deviceName, ds);
                deviceInfoBean.setSubdeviceName(lastName);
                DeviceDaoUtil.getInstance().getDeviceDao().update(deviceInfoBean);
            } else {
                DeviceInfoBean deviceInfoBean = DeviceDaoUtil.getInstance().findByDeviceId(deviceName, ds);
                deviceInfoBean.setSubdeviceName("");
                DeviceDaoUtil.getInstance().getDeviceDao().update(deviceInfoBean);
            }
        }
    }

    private void uploadCurrentSceneGroup(String deviceName, String current){
        int sid = Integer.parseInt(current, 16);
        SceneDaoUtil.getInstance().updateChoice(sid, deviceName);
        DeviceDaoUtil.getInstance().updateGatewayCurrentSid(deviceName, sid);
        EventRefreshScene eventRefreshDevice = new EventRefreshScene();
        eventRefreshDevice.setDeviceName(deviceName);
        EventBus.getDefault().post(eventRefreshDevice);
    }

    private void uploadSceneGroupInfo(String deviceName, String content){
        if (content.startsWith("0000")) {
            int sid = Integer.parseInt(content.substring(4), 16);
            SceneRelationDaoUtil.getInstance().deleteAllRelation(sid, deviceName);
            if (sid <= 2) {
                SceneDaoUtil.getInstance().updateColor(sid, "F" + sid, deviceName);
            } else {
                SceneDaoUtil.getInstance().deleteBySid(sid, deviceName);
            }
            SceneRelationDaoUtil.getInstance().deleteAllRelation(sid, deviceName);
            SceneSwitchDaoUtil.getInstance().deleteAllSwitch(sid, deviceName);
        } else {
            SceneBean mScene = new SceneBean();
            mScene.setCode(content);
            mScene.setDeviceName(deviceName);
            if (content.length() >= 32) {
                mScene.createScene(deviceName);
                SceneDaoUtil.getInstance().insertScene(mScene);

                if (mScene.getRelationList() != null && mScene.getRelationList().size() > 0) {
                    SceneRelationDaoUtil.getInstance().deleteAllRelation(mScene.getSid(), deviceName);
                    for (SceneRelationBean sceneRelationBean : mScene.getRelationList()) {
                        SceneRelationDaoUtil.getInstance().insertSceneRelation(sceneRelationBean);
                    }
                }

                if (mScene.getSwitchList() != null && mScene.getSwitchList().size() > 0) {
                    SceneSwitchDaoUtil.getInstance().deleteAllSwitch(mScene.getSid(), deviceName);
                    for (SceneSwitchBean shortcutAliBean : mScene.getSwitchList()) {
                        SceneSwitchDaoUtil.getInstance().insertSwitch(shortcutAliBean);
                    }
                }

                if ((mScene.getScene_default() & 0x01) != 0) {
                    SceneRelationBean sceneRelationBean = new SceneRelationBean();
                    sceneRelationBean.setSid(mScene.getSid());
                    sceneRelationBean.setMid(129);
                    sceneRelationBean.setDeviceName(deviceName);
                    SceneRelationDaoUtil.getInstance().insertSceneRelation(sceneRelationBean);
                } else {
                    SceneRelationDaoUtil.getInstance().deleteRelation(129, mScene.getSid(), deviceName);
                }

                if ((mScene.getScene_default() & 0x02) != 0) {
                    SceneRelationBean sceneRelationBean = new SceneRelationBean();
                    sceneRelationBean.setSid(mScene.getSid());
                    sceneRelationBean.setMid(130);
                    sceneRelationBean.setDeviceName(deviceName);
                    SceneRelationDaoUtil.getInstance().insertSceneRelation(sceneRelationBean);
                } else {
                    SceneRelationDaoUtil.getInstance().deleteRelation(130, mScene.getSid(), deviceName);
                }

                if ((mScene.getScene_default() & 0x04) != 0) {
                    SceneRelationBean sceneRelationBean = new SceneRelationBean();
                    sceneRelationBean.setSid(mScene.getSid());
                    sceneRelationBean.setMid(131);
                    sceneRelationBean.setDeviceName(deviceName);
                    SceneRelationDaoUtil.getInstance().insertSceneRelation(sceneRelationBean);
                } else {
                    SceneRelationDaoUtil.getInstance().deleteRelation(131, mScene.getSid(), deviceName);
                }
            }


        }
    }

    private void uploadSceneInfo(String deviceName, String content){
        if ("OVER".equals(content)) {
            int current_mode = DeviceDaoUtil.getInstance().findGatewayCurrentSid(deviceName);
            if (current_mode >= 0) {
                SceneDaoUtil.getInstance().updateChoice(current_mode, deviceName);
            }
            Message message = Message.obtain();
            message.obj = deviceName;
            message.what = 12;
            handler.sendMessageDelayed(message, 0);
        } else if (content.startsWith("0000")) {
            int mid = Integer.parseInt(content.substring(4, 6), 16);
            AutomationDaoUtil.getInstance().deleteByMid(mid, deviceName);
            SceneRelationDaoUtil.getInstance().deleteAllRelation2(mid, deviceName);
        } else {
            AutomationBean automationBean = new AutomationBean();
            automationBean.setCode(content);
            automationBean.create(deviceName);
            if (automationBean.getMid() > 0) {
                AutomationDaoUtil.getInstance().insertAutomation(automationBean);
            }
        }
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
            AutomationBean automationBean = AutomationDaoUtil.getInstance().findAutomationByMid(mid,deviceName);


            String title = "";
                if(automationBean == null){
                    title = String.format(getResources().getString(R.string.ali_gateway_scene_is_happen_has_no_scene),dname,mid);
                }else{
                    SceneName = automationBean.getName();
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
    }
}
