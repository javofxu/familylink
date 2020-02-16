package com.ilop.sthome.mvp.present;

import android.content.Context;
import android.os.Handler;

import com.alibaba.fastjson.JSON;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTResponse;
import com.example.common.mvp.BasePresenterImpl;
import com.ilop.sthome.common.bind.DeviceBindBusiness;
import com.ilop.sthome.common.bind.OnBindDeviceCompletedListener;
import com.ilop.sthome.data.device.Device;
import com.ilop.sthome.data.greenDao.DeviceInfoBean;
import com.ilop.sthome.data.greenDao.RoomBean;
import com.ilop.sthome.mvp.contract.BindContract;
import com.ilop.sthome.mvp.model.CommonModel;
import com.ilop.sthome.mvp.model.common.CommonModelImpl;
import com.ilop.sthome.mvp.model.common.onModelCallBack;
import com.ilop.sthome.utils.greenDao.RoomDaoUtil;
import com.siterwell.familywellplus.R;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author skygge.
 * @Date on 2020-02-10.
 * @Dec:
 */
public class BindPresenter extends BasePresenterImpl<BindContract.IView> implements BindContract.IPresent {

    private Context mContext;
    private String mUserId;
    private int mRoomId;
    private String mRoomName;
    private boolean isAlreadyRoom = false;
    private CommonModelImpl mModel;
    private DeviceBindBusiness mBindBusiness;
    private List<DeviceInfoBean> mDeviceInfoList;
    private RoomBean mRoomBean;
    private Handler mHandler;

    public BindPresenter(Context mContext) {
        this.mContext = mContext;
        mModel = new CommonModel();
        mBindBusiness = new DeviceBindBusiness();
        mHandler = new Handler();
    }

    @Override
    public void queryProductInfo(Device device) {
        mBindBusiness.queryProductInfo(device);
    }

    @Override
    public void bindDevice(Device device, String roomName, String gatewayName) {
        mBindBusiness.bindDevice(device, new OnBindDeviceCompletedListener() {
            @Override
            public void onSuccess(String iotId) {
                renameGateway(iotId, roomName, gatewayName);
            }

            @Override
            public void onFailed(Exception e) {
                mHandler.post(()-> mView.showToastMsg(mContext.getString(R.string.EE_AS_SYS_BINDING_PHONE_CODE6)));
            }

            @Override
            public void onFailed(int code, String message, String localizedMsg) {
                mHandler.post(()->{
                    mView.showToastMsg(mContext.getString(R.string.EE_AS_SYS_BINDING_PHONE_CODE6));
                    if(2064==code){
                        mView.alreadyBind();
                    }
                });
            }
        });
    }


    @Override
    public void renameGateway(String iotId, String roomName, String gatewayName) {
        mModel.onRenameGateway(iotId, gatewayName, new onModelCallBack() {
            @Override
            public void onResponse(IoTResponse response) {
                int code = response.getCode();
                String message = response.getMessage();
                Object data = response.getData();
                if (data == null) {
                    return;
                }
                if (code == 200){
                    List<RoomBean> mRoomList = RoomDaoUtil.getInstance().findRoomByName(roomName);
                    if (mRoomList.size() == 0){
                        createRoom(roomName);
                    }else{
                        isAlreadyRoom = true;
                        getGatewayList();
                    }
                }else {
                    mHandler.post(() -> mView.showToastMsg(message));
                }
            }
            @Override
            public void onFailure(Exception e) {
                mHandler.post(() -> mView.showToastMsg(mContext.getString(R.string.failed)));
            }
        });
    }

    @Override
    public void createRoom(String roomName) {
        this.mRoomName = roomName;
        List<RoomBean> roomBeanList = RoomDaoUtil.getInstance().getRoomDao().queryAll();
        int size = roomBeanList.size();
        mRoomId = size == 0 ? 0 : roomBeanList.get(size-1).getId().intValue()+1;
        mModel.onCreateRoom(String.valueOf(mRoomId), roomName, "", "", "", new onModelCallBack() {
            @Override
            public void onResponse(IoTResponse response) {
                if (response.getCode() == 200){
                    try {
                        Object data = response.getData();
                        JSONObject jsonObject = (JSONObject) data;
                        mUserId = jsonObject.getString("userId");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    getGatewayList();
                }
            }
            @Override
            public void onFailure(Exception e) {
                mHandler.post(() -> mView.showToastMsg(mContext.getString(R.string.failed)));
            }
        });
    }

    @Override
    public void getGatewayList() {
        mModel.onGetGatewayList(new onModelCallBack() {
            @Override
            public void onResponse(IoTResponse response) {
                if (response.getCode() == 200) {
                    Object data = response.getData();
                    JSONObject jsonObject = (JSONObject) data;
                    List<String> stringList = new ArrayList<>();
                    try {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        mDeviceInfoList = JSON.parseArray(jsonArray.toString(), DeviceInfoBean.class);
                        if (mDeviceInfoList.size() > 0) {
                            for (DeviceInfoBean deviceInfoBean : mDeviceInfoList) {
                                stringList.add(deviceInfoBean.getDeviceName());
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (isAlreadyRoom){
                        List<RoomBean> mRoomList = RoomDaoUtil.getInstance().findRoomByName(mRoomName);
                        if (mRoomList.size()>0){
                            mRoomBean = mRoomList.get(0);
                            mUserId = mRoomBean.getUserId();
                        }
                    }
                    String iotId = StringUtils.join(stringList.toArray(),",");
                    updateRoom(iotId);
                }
            }

            @Override
            public void onFailure(Exception e) {
                mHandler.post(() -> mView.showToastMsg(mContext.getString(R.string.failed)));
            }
        });
    }

    @Override
    public void updateRoom(String gatewayList) {
        mModel.onUpdateRoomByGateway(mUserId, gatewayList, new onModelCallBack() {
            @Override
            public void onResponse(IoTResponse response) {
                if (response.getCode() == 200){
                    if (!isAlreadyRoom) {
                        RoomBean roomBean = new RoomBean();
                        roomBean.setUserId(mUserId);
                        roomBean.setRid(mRoomId);
                        roomBean.setRoom_name(mRoomName);
                        roomBean.setGatewayList(mDeviceInfoList);
                        RoomDaoUtil.getInstance().getRoomDao().insert(roomBean);
                        mHandler.post(()-> mView.bindSuccess());
                    }else {
                        mRoomBean.setGatewayList(mDeviceInfoList);
                        RoomDaoUtil.getInstance().getRoomDao().update(mRoomBean);
                    }
                }
            }

            @Override
            public void onFailure(Exception e) {
                mHandler.post(() -> mView.showToastMsg(mContext.getString(R.string.failed)));
            }
        });
    }
}
