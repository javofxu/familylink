package com.ilop.sthome.mvp.present;

import android.content.Context;
import android.util.Log;

import com.aliyun.iot.aep.sdk.apiclient.callback.IoTResponse;
import com.example.common.base.OnCallBackToRefresh;
import com.example.common.mvp.BasePresenterImpl;
import com.example.common.utils.SpUtil;
import com.ilop.sthome.data.event.EventRefreshDevice;
import com.ilop.sthome.data.greenDao.DeviceInfoBean;
import com.ilop.sthome.data.greenDao.RoomBean;
import com.ilop.sthome.mvp.contract.AddDeviceContract;
import com.ilop.sthome.mvp.model.CommonModel;
import com.ilop.sthome.mvp.model.common.CommonModelImpl;
import com.ilop.sthome.mvp.model.common.onModelCallBack;
import com.ilop.sthome.network.api.SendEquipmentDataAli;
import com.ilop.sthome.ui.dialog.BaseDialog;
import com.ilop.sthome.utils.CoderALiUtils;
import com.ilop.sthome.utils.greenDao.DeviceDaoUtil;
import com.ilop.sthome.utils.greenDao.RoomDaoUtil;
import com.ilop.sthome.utils.tools.ByteUtil;
import com.siterwell.familywellplus.R;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author skygge
 * @Date on 2020-02-12.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：
 */
public class AddDevicePresenter extends BasePresenterImpl<AddDeviceContract.IView> implements AddDeviceContract.IPresent {

    private Context mContext;
    private BaseDialog mDialog;
    private int mDeviceId;
    private int mRoomId;
    private String mDeviceName;
    private String mNickName;
    private List<RoomBean> mRoomList;
    private List<DeviceInfoBean> mDeviceList;
    private SendEquipmentDataAli mSendEquipment;
    private CommonModelImpl mModel;

    public AddDevicePresenter(Context mContext, String deviceName) {
        this.mContext = mContext;
        this.mDeviceName = deviceName;
        mModel = new CommonModel();
        mDeviceList = new ArrayList<>();
        DeviceInfoBean deviceInfoBean = DeviceDaoUtil.getInstance().findGatewayByDeviceName(deviceName);
        mSendEquipment = new SendEquipmentDataAli(mContext, deviceInfoBean);
    }

    @Override
    public void onInsertDevice() {
        mSendEquipment.increaceEquipment();
    }

    @Override
    public void onReplaceDevice(int deviceID) {
        mSendEquipment.replaceEquipment(deviceID);
    }

    @Override
    public void onAddSuccess(EventRefreshDevice event) {
        if(mDialog == null || !mDialog.isShowing()){
            if(event.getDevice_id()>0){
                mDeviceId = event.getDevice_id();
                if (event.getType() == 0){
                    onModifyDevice(mDeviceId);
                }else {
                    mDialog = new BaseDialog(mContext, new OnCallBackToRefresh() {
                        @Override
                        public void onConfirm() {
                            onModifyDevice(mDeviceId);
                        }

                        @Override
                        public void onCancel() {
                            mView.finishActivity();
                        }
                    });
                    mDialog.setMsg(mContext.getString(R.string.already_in_gateway));
                    mDialog.show();
                }
                mView.showAddSuccess();
            }else {
                mView.showAddFailed();
            }
        }
    }

    @Override
    public void onModifyDevice(int deviceId) {
        mNickName = SpUtil.getString(mContext, "device");
        String ds = CoderALiUtils.getAscii(mNickName);
        String dsCRC = ByteUtil.CRCmaker(ds);
        mSendEquipment.modifyEquipmentName(deviceId, ds + dsCRC);
        onModifySuccess();
    }

    @Override
    public void onModifySuccess() {
        String mRoomName = SpUtil.getString(mContext, "room");
        mRoomList = RoomDaoUtil.getInstance().findRoomByName(mRoomName);
        List<RoomBean> roomBeanList = RoomDaoUtil.getInstance().getRoomDao().queryAll();
        int size = roomBeanList.size();
        mRoomId = size == 0 ? 0 : roomBeanList.get(size-1).getId().intValue()+1;
        if(mDeviceId > 0){
            DeviceInfoBean deviceInfoBean = new DeviceInfoBean();
            deviceInfoBean.setSubdeviceName(mNickName);
            deviceInfoBean.setDeviceName(mDeviceName);
            deviceInfoBean.setDevice_ID(mDeviceId);
            deviceInfoBean.setNodeType(String.valueOf(mRoomId));
            DeviceDaoUtil.getInstance().getDeviceDao().update(deviceInfoBean);
            List<DeviceInfoBean> deviceList = DeviceDaoUtil.getInstance().findAllSubDevice(mDeviceName);
            List<String> stringList = new ArrayList<>();
            for (DeviceInfoBean mDevice: deviceList) {
                stringList.add(mDevice.getDeviceName()+"_"+mDevice.getDevice_ID());
                if (String.valueOf(mRoomId).equals(mDevice.getNodeType())){
                    mDeviceList.add(mDevice);
                }
            }
            String devices = StringUtils.join(stringList.toArray(),",");
            if (mRoomList.size() == 0){
                createRoom(mRoomName, devices);
            }else{
                updateRoom(devices);
            }
        }
    }

    @Override
    public void createRoom(String roomName, String deviceList) {

        mModel.onCreateRoom(String.valueOf(mRoomId), roomName, "", deviceList, "", new onModelCallBack() {
            @Override
            public void onResponse(IoTResponse response) {
                if (response.getCode() == 200){
                    String mUserId = null;
                    try {
                        Object data = response.getData();
                        JSONObject jsonObject = (JSONObject) data;
                        mUserId = jsonObject.getString("userId");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    RoomBean roomBean = new RoomBean();
                    roomBean.setUserId(mUserId);
                    roomBean.setRid(mRoomId);
                    roomBean.setRoom_name(roomName);
                    roomBean.setSubDeviceList(mDeviceList);
                    RoomDaoUtil.getInstance().getRoomDao().insert(roomBean);
                    mView.finishActivity();
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

    @Override
    public void updateRoom(String deviceList) {
        String mUserId = mRoomList.get(0).getUserId();
        mModel.onUpdateRoomBySubDevice(mUserId, deviceList, new onModelCallBack() {
            @Override
            public void onResponse(IoTResponse response) {
                if (response.getCode() == 200){
                    RoomBean mRoom = mRoomList.get(0);
                    mRoom.setSubDeviceList(mDeviceList);
                    RoomDaoUtil.getInstance().getRoomDao().update(mRoom);
                    mView.finishActivity();
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

    @Override
    public void onCancel() {
        mSendEquipment.cancelIncreaseEq();
    }
}
