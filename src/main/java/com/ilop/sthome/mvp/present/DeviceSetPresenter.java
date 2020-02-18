package com.ilop.sthome.mvp.present;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.aliyun.iot.aep.component.router.Router;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTResponse;
import com.example.common.base.OnCallBackToEdit;
import com.example.common.base.OnCallBackToRefresh;
import com.example.common.mvp.BasePresenterImpl;
import com.ilop.sthome.data.bean.AlarmNotice;
import com.ilop.sthome.data.enums.DevType;
import com.ilop.sthome.data.enums.SmartProduct;
import com.ilop.sthome.data.greenDao.DeviceInfoBean;
import com.ilop.sthome.mvp.contract.DeviceSetContract;
import com.ilop.sthome.mvp.model.CommonModel;
import com.ilop.sthome.mvp.model.common.CommonModelImpl;
import com.ilop.sthome.mvp.model.common.onModelCallBack;
import com.ilop.sthome.network.api.SendEquipmentDataAli;
import com.ilop.sthome.ui.activity.config.AddDeviceGuideActivity;
import com.ilop.sthome.ui.activity.device.DeviceRenameActivity;
import com.ilop.sthome.ui.activity.main.MainActivity;
import com.ilop.sthome.ui.dialog.BaseDialog;
import com.ilop.sthome.ui.dialog.BaseEditDialog;
import com.ilop.sthome.ui.ota.OTAConstants;
import com.ilop.sthome.ui.ota.bean.OTADeviceSimpleInfo;
import com.ilop.sthome.utils.CoderALiUtils;
import com.ilop.sthome.utils.greenDao.DeviceDaoUtil;
import com.ilop.sthome.utils.tools.ByteUtil;
import com.ilop.sthome.utils.tools.UnitTools;
import com.siterwell.familywellplus.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * @author skygge
 * @Date on 2020-02-13.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：
 */
public class DeviceSetPresenter extends BasePresenterImpl<DeviceSetContract.IView> implements DeviceSetContract.IPresent {

    private Context mContext;
    private int mDeviceId;
    private String mDeviceName;
    private String mNickname;
    private CommonModelImpl mModel;
    private DeviceInfoBean mDeviceInfoBean;
    private SendEquipmentDataAli mSendEquipment;
    private Handler mHandler;

    public DeviceSetPresenter(Context mContext, String deviceName, int deviceId) {
        this.mContext = mContext;
        this.mDeviceId = deviceId;
        this.mDeviceName = deviceName;
        mModel = new CommonModel();
        mHandler = new Handler();
        DeviceInfoBean deviceInfoBean = DeviceDaoUtil.getInstance().findGatewayByDeviceName(deviceName);
        mSendEquipment = new SendEquipmentDataAli(mContext, deviceInfoBean);
    }


    @Override
    public void onRefreshView() {
        mDeviceInfoBean = DeviceDaoUtil.getInstance().findByDeviceId(mDeviceName, mDeviceId);
        if(mDeviceId==0){
            mNickname = TextUtils.isEmpty(mDeviceInfoBean.getNickName())? mContext.getResources().getString(DevType.getType(mDeviceInfoBean.getProductKey()).getTypeStrId()):mDeviceInfoBean.getNickName();
            mView.showDeviceName(mNickname);
            if(mDeviceInfoBean.getOwned()==1) refreshQRCode(mDeviceInfoBean.getIotId());
        }else {
            mNickname = TextUtils.isEmpty(mDeviceInfoBean.getSubdeviceName())? (mContext.getResources().getString(SmartProduct.getType(mDeviceInfoBean.getDevice_type()).getTypeStrId())+mDeviceInfoBean.getDevice_ID()):mDeviceInfoBean.getSubdeviceName();
            mView.showDeviceName(mNickname);
        }
    }

    @Override
    public void showViewByDeviceId(int deviceId) {
        if (deviceId == 0){
            mView.isGatewayView();
        }else {
            mView.isSubDeviceView();
        }
    }

    @Override
    public void setDeviceName() {
        Intent intent = new Intent(mContext, DeviceRenameActivity.class);
        intent.putExtra("deviceId", mDeviceId);
        intent.putExtra("deviceName", mDeviceName);
        intent.putExtra("nickName", mNickname);
        mView.startActivityByIntent(intent);
    }

    @Override
    public void getDeviceNoticeList() {
        mModel.getDeviceNoticeList(mDeviceInfoBean.getIotId(), new onModelCallBack() {
            @Override
            public void onFailure(Exception e) {

            }

            @Override
            public void onResponse(IoTResponse response) {
                Object data = response.getData();
                if (data == null) {
                    return;
                }
                if (!(data instanceof JSONArray)) {
                    return;
                }
                mHandler.post(()->{
                    try {
                        List<AlarmNotice> mNoticeList = JSON.parseArray(data.toString(), AlarmNotice.class);
                        if (mNoticeList.size() > 0) {
                            mView.showNoticeList(mNoticeList);
                        }else {
                            mView.withoutNotice();
                        }
                        for (AlarmNotice notice: mNoticeList) {
                            if (notice.isNoticeEnabled()){
                                mView.showHasEnabledOpen(true);
                                return;
                            }else {
                                mView.showHasEnabledOpen(false);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        });
    }

    @Override
    public void setDeviceFullNoticeEnabled(boolean noticeEnabled) {
        mModel.setDeviceNoticeReminder(mDeviceInfoBean.getIotId(), noticeEnabled, new onModelCallBack() {
            @Override
            public void onFailure(Exception e) {
                mHandler.post(()-> mView.showToastMsg(mContext.getString(R.string.operation_failed)));
            }

            @Override
            public void onResponse(IoTResponse response) {
                int code = response.getCode();
                mHandler.post(()->{
                    if (code == 200){
                        getDeviceNoticeList();
                        mView.showToastMsg(mContext.getString(R.string.operation_success));
                    }else {
                        mView.showToastMsg(response.getMessage());
                    }
                });
            }
        });
    }

    @Override
    public void setDeviceNoticeEnabled(String eventId, boolean noticeEnabled) {
        mModel.deviceNoticeSet(mDeviceInfoBean.getIotId(), eventId, noticeEnabled, new onModelCallBack() {
            @Override
            public void onFailure(Exception e) {
                mHandler.post(()-> mView.showToastMsg(mContext.getString(R.string.operation_failed)));
            }

            @Override
            public void onResponse(IoTResponse response) {
                mHandler.post(()->{
                    if (response.getCode() ==200){
                        getDeviceNoticeList();
                        mView.showToastMsg(mContext.getString(R.string.operation_success));
                    }else {
                        mView.showToastMsg(response.getMessage());
                    }
                });
            }
        });
    }

    @Override
    public void onUnBindDevice() {
        if(mDeviceId==0){
            String name = TextUtils.isEmpty(mDeviceInfoBean.getNickName())? mContext.getResources().getString(DevType.getType(mDeviceInfoBean.getProductKey()).getTypeStrId()):mDeviceInfoBean.getNickName();
            BaseDialog baseDialog = new BaseDialog(mContext, new OnCallBackToRefresh() {
                @Override
                public void onConfirm() {
                    mModel.onUnbindGateway(mDeviceInfoBean.getIotId(), new onModelCallBack() {
                        @Override
                        public void onResponse(IoTResponse response) {
                            if (response.getCode() == 200){
                                DeviceDaoUtil.getInstance().deleteByDeviceName(mDeviceName, 0);
                                Intent intent = new Intent(mContext, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                mView.startActivityByIntent(intent);
                            }else {
                                mHandler.post(()-> mView.showToastMsg(response.getMessage()));
                            }
                        }

                        @Override
                        public void onFailure(Exception e) {

                        }
                    });
                }

                @Override
                public void onCancel() {

                }
            });
            baseDialog.setMsg(String.format(mContext.getResources().getString(R.string.ali_gateay_unbind_hint), name));
            baseDialog.show();
        }else {
            DeviceInfoBean deviceInfoBean1 = DeviceDaoUtil.getInstance().findGatewayByDeviceName(mDeviceInfoBean.getDeviceName());
            String name1 = TextUtils.isEmpty(deviceInfoBean1.getNickName())? mContext.getResources().getString(DevType.getType(deviceInfoBean1.getProductKey()).getTypeStrId()):deviceInfoBean1.getNickName();
            String name = String.format(mContext.getResources().getString(R.string.ali_delete_sub_device_from_gateway),name1);
            BaseDialog baseDialog = new BaseDialog(mContext, new OnCallBackToRefresh() {
                @Override
                public void onConfirm() {
                    mSendEquipment.deleteEquipment(mDeviceId);
                }

                @Override
                public void onCancel() {

                }
            });
            baseDialog.setMsg(name);
            baseDialog.show();
        }

    }

    @Override
    public void onReplaceDevice() {
        if(mDeviceId>0){
            Intent intent = new Intent(mContext, AddDeviceGuideActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("deviceId", mDeviceId);
            bundle.putString("deviceName",mDeviceName);
            intent.putExtras(bundle);
            mView.startActivityByIntent(intent);
        }
    }

    @Override
    public void refreshQRCode(String iotId) {
        mModel.onRefreshQRCode(iotId, new onModelCallBack() {
            @Override
            public void onResponse(IoTResponse response) {
                if (response.getCode() == 200){
                    try {
                        Object data = response.getData();
                        JSONObject jsonObject = (JSONObject) data;
                        String de = jsonObject.getString("qrKey");
                        mHandler.post(()-> mView.showQRCode(de));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else {
                    mHandler.post(()-> mView.showToastMsg(response.getMessage()));
                }
            }

            @Override
            public void onFailure(Exception e) {
            }
        });
    }

    @Override
    public void onRouterToOTA() {
        if(mDeviceId==0){
            DeviceInfoBean deviceInfoBean = DeviceDaoUtil.getInstance().findByDeviceId(mDeviceName, mDeviceId);
            OTADeviceSimpleInfo info = new OTADeviceSimpleInfo();
            info.iotId = deviceInfoBean.getIotId();
            info.deviceName = deviceInfoBean.getDeviceName();
            Bundle bundle = new Bundle();
            bundle.putParcelable(OTAConstants.OTA_KEY_DEVICESIMPLE_INFO, info);
            Router.getInstance().toUrl(mContext, OTAConstants.MINE_URL_OTA, bundle);
        }
    }

    @Override
    public void onDownloadIns() {
        UnitTools unitTools =new UnitTools(mContext);
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        if("zh".equals(unitTools.readLanguage())){
            String url;
            if(mDeviceId == 0){
                url = SmartProduct.EE_SIMULATE_GATEWAY.getIns_url();
            }else {
                DeviceInfoBean deviceInfoBean = DeviceDaoUtil.getInstance().findByDeviceId(mDeviceName, mDeviceId);
                url = SmartProduct.getType(deviceInfoBean.getDevice_type()).getIns_url();
            }
            Uri content_url = Uri.parse(url);
            intent.setData(content_url);
            mView.startActivityByIntent(intent);
        }else {
            String url;
            if(mDeviceId == 0){
                url = SmartProduct.EE_SIMULATE_GATEWAY.getIns_url_en();
            }else {
                DeviceInfoBean deviceInfoBean = DeviceDaoUtil.getInstance().findByDeviceId(mDeviceName, mDeviceId);
                url = SmartProduct.getType(deviceInfoBean.getDevice_type()).getIns_url_en();
            }
            Uri content_url = Uri.parse(url);
            intent.setData(content_url);
            mView.startActivityByIntent(intent);
        }
    }

    @Override
    public void onDeleteSuccess() {
        DeviceDaoUtil.getInstance().deleteByDeviceName(mDeviceName, mDeviceId);
    }
}
