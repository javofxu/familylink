package com.ilop.sthome.mvp.present;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTResponse;
import com.example.common.mvp.BasePresenterImpl;
import com.example.common.utils.SpUtil;
import com.ilop.sthome.app.MyApplication;
import com.ilop.sthome.data.bean.GatewayBean;
import com.ilop.sthome.data.bean.SysModelAliBean;
import com.ilop.sthome.data.bean.VirtualUserBean;
import com.ilop.sthome.data.db.SysmodelAliDAO;
import com.ilop.sthome.data.greenDao.CameraBean;
import com.ilop.sthome.data.greenDao.DeviceInfoBean;
import com.ilop.sthome.data.greenDao.RoomBean;
import com.ilop.sthome.mvp.contract.DeviceContract;
import com.ilop.sthome.mvp.model.CommonModel;
import com.ilop.sthome.mvp.model.common.CommonModelImpl;
import com.ilop.sthome.mvp.model.common.onModelCallBack;
import com.ilop.sthome.network.api.SendEquipmentDataAli;
import com.ilop.sthome.network.udp.GatewayUdpListConstant;
import com.ilop.sthome.ui.activity.detail.ButtonDetailActivity;
import com.ilop.sthome.ui.activity.detail.CoDetailActivity;
import com.ilop.sthome.ui.activity.detail.CxSmDetailActivity;
import com.ilop.sthome.ui.activity.detail.DoorDetailActivity;
import com.ilop.sthome.ui.activity.detail.GasDetailActivity;
import com.ilop.sthome.ui.activity.detail.LockDetailActivity;
import com.ilop.sthome.ui.activity.detail.ModeButtonDetailActivity;
import com.ilop.sthome.ui.activity.detail.OutDoorDetailActivity;
import com.ilop.sthome.ui.activity.detail.PirDetailActivity;
import com.ilop.sthome.ui.activity.detail.SmDetailActivity;
import com.ilop.sthome.ui.activity.detail.SocketDetailActivity;
import com.ilop.sthome.ui.activity.detail.SosDetailActivity;
import com.ilop.sthome.ui.activity.detail.THCheckDetailActivity;
import com.ilop.sthome.ui.activity.detail.TempControlDetailActivity;
import com.ilop.sthome.ui.activity.detail.ThermalDetailActivity;
import com.ilop.sthome.ui.activity.detail.WaterDetailActivity;
import com.ilop.sthome.utils.CoderALiUtils;
import com.ilop.sthome.utils.greenDao.CameraDaoUtil;
import com.ilop.sthome.utils.greenDao.DeviceDaoUtil;
import com.ilop.sthome.utils.greenDao.RoomDaoUtil;
import com.siterwell.familywellplus.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author skygge.
 * @Date on 2019-10-10.
 * @Dec:
 */
public class DevicePresenter extends BasePresenterImpl<DeviceContract.IView> implements DeviceContract.IPresent {

    private static final String TAG = "DevicePresenter";
    private Context mContext;
    private SysmodelAliDAO sysmodelAliDAO;
    private CommonModelImpl mModel;
    private Handler mHandler;

    private String gatewayList = null;
    private String cameraList = null;
    private String subDeviceList = null;
    private int mRoomId;
    private String mRoomName;

    private String mCameraName;
    private String mCameraId;

    public DevicePresenter(Context mContext) {
        this.mContext = mContext;
        mModel = new CommonModel();
        mHandler = new Handler();
        sysmodelAliDAO = new SysmodelAliDAO(mContext);
    }

    @Override
    public void getGatewayListByAccount() {
        mModel.onGetGatewayList(new onModelCallBack() {
            @Override
            public void onResponse(IoTResponse response) {
                if (response.getCode() == 200) {
                    Object data = response.getData();
                    try {
                        JSONObject jsonObject = (JSONObject) data;
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        List<DeviceInfoBean> deviceInfoBeanList = JSON.parseArray(jsonArray.toString(), DeviceInfoBean.class);
                        if (deviceInfoBeanList.size() > 0) {
                            for (DeviceInfoBean deviceInfoBean : deviceInfoBeanList) {
                                if ( DeviceDaoUtil.getInstance().findByDeviceId(deviceInfoBean.getDeviceName(), deviceInfoBean.getDevice_ID()) == null){
                                    DeviceDaoUtil.getInstance().insertGateway(deviceInfoBean);
                                }
                                if (sysmodelAliDAO.findAllSys(deviceInfoBean.getDeviceName()).size() <= 0) {
                                    initSaveSceneAndAuto(deviceInfoBean);
                                }

                                SendEquipmentDataAli sendEquipmentDataAli = new SendEquipmentDataAli(mContext, deviceInfoBean);
                                String crc = CoderALiUtils.getEqCRC(mContext, deviceInfoBean.getDeviceName());
                                sendEquipmentDataAli.synGetDeviceStatus(crc);

                                if (deviceInfoBean.getStatus() == 1) { //发送udp激活状态
                                    GatewayBean gb = GatewayUdpListConstant.getInstance().checkByname(deviceInfoBean.getDeviceName());
                                    if (gb != null && gb.isOnline() && gb.getIpAddress() != null) {
                                        sendEquipmentDataAli.ActivtyUdp();
                                    }
                                }
                            }

                            SpUtil.putString(mContext, "iotId", deviceInfoBeanList.get(0).getIotId());
                            GatewayUdpListConstant.getInstance().setDeviceInfoBeans(deviceInfoBeanList);//从服务器端获取到的网关--网关列表
                        } else {
                            DeviceDaoUtil.getInstance().getDeviceDao().deleteAll();
                            SpUtil.putString(mContext, "iotId", "");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else {
                    mHandler.post(()->mView.showMsg(response.getMessage()));
                }
            }

            @Override
            public void onFailure(Exception e) {
                GatewayUdpListConstant.getInstance().setDeviceInfoBeans(DeviceDaoUtil.getInstance().findAllDevice());//没有成功直接获取手机数据
            }
        });
    }


    @Override
    public void getRoomListByAccount(int pageNo, int pageSize) {
        mModel.onQueryRoomList(pageNo, pageSize, new onModelCallBack() {
            @Override
            public void onResponse(IoTResponse response) {
                mHandler.post(()->{
                    if (response.getCode() == 200){
                        Object data = response.getData();
                        try {
                            JSONObject jsonObject = (JSONObject) data;
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            List<VirtualUserBean> userList = JSON.parseArray(jsonArray.toString(), VirtualUserBean.class);
                            if (userList.size()>0) {
                                RoomDaoUtil.getInstance().getRoomDao().deleteAll();
                                for (VirtualUserBean bean : userList) {
                                    for (int i = 0; i < bean.getAttrList().size(); i++) {
                                        if ("companyAddress".equals(bean.getAttrList().get(i).getAttrKey())) {
                                            gatewayList = bean.getAttrList().get(i).getAttrValue();
                                        }
                                        if ("homeAddress".equals(bean.getAttrList().get(i).getAttrKey())) {
                                            cameraList = bean.getAttrList().get(i).getAttrValue();
                                        }
                                        if ("companyName".equals(bean.getAttrList().get(i).getAttrKey())) {
                                            subDeviceList = bean.getAttrList().get(i).getAttrValue();
                                        }
                                        if ("employeeID".equals(bean.getAttrList().get(i).getAttrKey())){
                                            mRoomId = Integer.parseInt(bean.getAttrList().get(i).getAttrValue());
                                        }
                                        if ("extNum".equals(bean.getAttrList().get(i).getAttrKey())){
                                            mRoomName = bean.getAttrList().get(i).getAttrValue();
                                        }
                                        if ("name".equals(bean.getAttrList().get(i).getAttrKey())){
                                            mCameraName = bean.getAttrList().get(i).getAttrValue();
                                        }
                                        if ("displayName".equals(bean.getAttrList().get(i).getAttrKey())){
                                            mCameraId = bean.getAttrList().get(i).getAttrValue();
                                        }
                                        if ("name".equals(bean.getAttrList().get(i).getAttrKey())) {
                                            CameraDaoUtil.getInstance().getCameraDao().deleteAll();
                                            CameraBean cameraBean = new CameraBean();
                                            cameraBean.setUserId(bean.getUserId());
                                            cameraBean.setDeviceName(mCameraName);
                                            cameraBean.setDeviceId(mCameraId);
                                            CameraDaoUtil.getInstance().getCameraDao().insert(cameraBean);
                                        }
                                    }
                                    processingData(bean.getUserId(), mRoomId, mRoomName, gatewayList, cameraList, subDeviceList);
                                }
                            }else {
                                getRoomList();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

    @Override
    public void processingData(String userId, int roomId, String roomName, String gateway, String camera, String subDevice) {
        RoomBean roomBean= new RoomBean();
        roomBean.setUserId(userId);
        roomBean.setRid(roomId);
        roomBean.setRoom_name(roomName);
        List<DeviceInfoBean> mGatewayList = new ArrayList<>();
        List<DeviceInfoBean> mCameraList = new ArrayList<>();
        List<DeviceInfoBean> mSubDeviceList = new ArrayList<>();
        if (!TextUtils.isEmpty(gateway)){
            String[] gatewayList = gateway.split(",");
            for (String gateways: gatewayList) {
                DeviceInfoBean mDevice = DeviceDaoUtil.getInstance().findGatewayByDeviceName(gateways);
                if (mDevice !=null){
                    mGatewayList.add(mDevice);
                }
            }
            if (mGatewayList.size()>0){
                roomBean.setGatewayList(mGatewayList);
            }
        }
        if (!TextUtils.isEmpty(camera)){
            String[] cameraList = camera.split(",");
            for (String cameras: cameraList) {
                DeviceInfoBean mDevice = DeviceDaoUtil.getInstance().findGatewayByDeviceName(cameras);
                mCameraList.add(mDevice);
            }
            roomBean.setCameraList(mCameraList);
        }
        if (!TextUtils.isEmpty(subDevice)){
            String[] subDeviceList = subDevice.split(",");
            for (String subDevices: subDeviceList) {
                String deviceName = subDevices.split("_")[0];
                String deviceId = subDevices.split("_")[1];
                DeviceInfoBean mDevice = DeviceDaoUtil.getInstance().findByDeviceId(deviceName, Integer.parseInt(deviceId));
                if (mDevice != null){
                    mSubDeviceList.add(mDevice);
                }
            }
            if (mSubDeviceList.size()>0) {
                roomBean.setSubDeviceList(mSubDeviceList);
            }
        }
        RoomDaoUtil.getInstance().getRoomDao().insert(roomBean);
        getRoomList();
    }

    @Override
    public void getRoomList() {
        List<RoomBean> mRoomList = RoomDaoUtil.getInstance().getRoomDao().queryAll();
        if (mRoomList.size()>0){
            mView.showRoomList(mRoomList);
        }else {
            mView.noDeviceList();
        }
    }


    @Override
    public void clickChildItem(DeviceInfoBean device) {
        Intent intent = new Intent();
        intent.putExtra("deviceName", device.getDeviceName());
        intent.putExtra("eqid", device.getDevice_ID());

        if(getCode(R.array.AA_DEV_SOCKET).contains(device.getDevice_type())){
            intent.setClass(mContext, SocketDetailActivity.class);
            mView.skipActivity(intent);
        }else if (getCode(R.array.AA_DEV_SOS1).contains(device.getDevice_type())){
            intent.setClass(mContext, SosDetailActivity.class);
            mView.skipActivity(intent);
        }else if (getCode(R.array.AA_DEV_BUTTON).contains(device.getDevice_type())){
            intent.setClass(mContext, ButtonDetailActivity.class);
            mView.skipActivity(intent);
        }else if (getCode(R.array.AA_DEV_CO_ALARM).contains(device.getDevice_type())){
            intent.setClass(mContext, CoDetailActivity.class);
            mView.skipActivity(intent);
        }else if (getCode(R.array.AA_DEV_DOOR).contains(device.getDevice_type())){
            intent.setClass(mContext, DoorDetailActivity.class);
            mView.skipActivity(intent);
        }else if (getCode(R.array.AA_DEV_GAS_ALARM).contains(device.getDevice_type())){
            intent.setClass(mContext, GasDetailActivity.class);
            mView.skipActivity(intent);
        }else if (getCode(R.array.AA_DEV_LOCK).contains(device.getDevice_type())){
            intent.setClass(mContext, LockDetailActivity.class);
            mView.skipActivity(intent);
        }else if (getCode(R.array.AA_DEV_MODE_BUTTON).contains(device.getDevice_type())){
            intent.setClass(mContext, ModeButtonDetailActivity.class);
            mView.skipActivity(intent);
        }else if (getCode(R.array.AA_TEMP_OUTDOOR_SIREN).contains(device.getDevice_type())){
            intent.setClass(mContext, OutDoorDetailActivity.class);
            mView.skipActivity(intent);
        }else if (getCode(R.array.AA_DEV_PIR1).contains(device.getDevice_type())){
            intent.setClass(mContext, PirDetailActivity.class);
            mView.skipActivity(intent);
        }else if (getCode(R.array.AA_DEV_SM_ALARM).contains(device.getDevice_type())){
            intent.setClass(mContext, SmDetailActivity.class);
            mView.skipActivity(intent);
        }else if (getCode(R.array.AA_DEV_TH_CHECK).contains(device.getDevice_type())){
            intent.setClass(mContext, THCheckDetailActivity.class);
            mView.skipActivity(intent);
        }else if (getCode(R.array.AA_DEV_THE_RM_ALARM).contains(device.getDevice_type())){
            intent.setClass(mContext, ThermalDetailActivity.class);
            mView.skipActivity(intent);
        }else if (getCode(R.array.AA_DEV_WT_ALARM).contains(device.getDevice_type())){
            intent.setClass(mContext, WaterDetailActivity.class);
            mView.skipActivity(intent);
        }else if (getCode(R.array.AA_DEV_SX_SM_ALARM).contains(device.getDevice_type())){
            intent.setClass(mContext, CxSmDetailActivity.class);
            mView.skipActivity(intent);
        }else if (getCode(R.array.AA_TEMP_CONTROL).contains(device.getDevice_type())){
            intent.setClass(mContext, TempControlDetailActivity.class);
            mView.skipActivity(intent);
        }
    }

    private List<String> getCode(int array){
        return Arrays.asList(mContext.getResources().getStringArray(array));
    }


    private void initSaveSceneAndAuto(DeviceInfoBean deviceInfoBean){
        for (int i = 0; i < 4; i++) {
            SysModelAliBean sysModelAliBean = new SysModelAliBean();
            sysModelAliBean.setDeviceName(deviceInfoBean.getDeviceName());
            sysModelAliBean.setSid(i);
            sysModelAliBean.setColor("F"+i);
            sysModelAliBean.setModleName("");
            if (i == 0) sysModelAliBean.setChoice(1);
            sysmodelAliDAO.addinit(sysModelAliBean);
        }
    }

}
