package com.ilop.sthome.mvp.present;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.example.common.base.OnCallBackToEdit;
import com.example.common.base.OnCallBackToRefresh;
import com.example.common.mvp.BasePresenterImpl;
import com.ilop.sthome.data.enums.DevType;
import com.ilop.sthome.data.greenDao.DeviceInfoBean;
import com.ilop.sthome.mvp.contract.SubDeviceContract;
import com.ilop.sthome.network.api.SendEquipmentDataAli;
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
import com.ilop.sthome.ui.activity.config.AddDeviceGuideActivity;
import com.ilop.sthome.ui.dialog.BaseDialog;
import com.ilop.sthome.ui.dialog.BaseEditDialog;
import com.ilop.sthome.ui.dialog.BaseListDialog;
import com.ilop.sthome.utils.CoderALiUtils;
import com.ilop.sthome.utils.greenDao.DeviceDaoUtil;
import com.ilop.sthome.utils.tools.ByteUtil;
import com.ilop.sthome.utils.tools.EmojiFilter;
import com.siterwell.familywellplus.R;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

/**
 * @Author skygge.
 * @Date on 2019-10-17.
 * @Dec:
 */
public class SubDevicePresenter extends BasePresenterImpl<SubDeviceContract.IView> implements SubDeviceContract.IPresent {

    private Context mContext;
    private List<DeviceInfoBean> mList;
    private BaseListDialog mListDialog;
    private BaseEditDialog mEditDialog;
    private BaseDialog mDialog;

    public SubDevicePresenter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getAllSubDevice(String deviceName) {
        mList = DeviceDaoUtil.getInstance().findAllSubDevice(deviceName);
        if (mList.size()>0){
            mView.getAllSubDevice(mList);
        }else {
            mView.withOutSubDevice();
        }
    }

    @Override
    public void clickItem(DeviceInfoBean device) {
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

    @Override
    public void longClickItem(DeviceInfoBean device) {
        String[] arr = {mContext.getString(R.string.replace_equipment), mContext.getString(R.string.update_name), mContext.getString(R.string.delete)};
        mListDialog = new BaseListDialog(mContext, i -> {
            switch (i){
                case 0:
                    changeDevice(device);
                    break;
                case 1:
                    renameDevice(device);
                    break;
                case 2:
                    deleteDevice(device);
                    break;
            }
            mListDialog.dismiss();
        });
        mListDialog.setMsgAndButton(arr, mContext.getString(R.string.cancel));
        mListDialog.show();
    }

    private void changeDevice(DeviceInfoBean device){
        Intent intent = new Intent(mContext, AddDeviceGuideActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("deviceId", device.getDevice_ID());
        bundle.putString("deviceName", device.getDeviceName());
        intent.putExtras(bundle);
        mView.skipActivity(intent);
    }

    private void renameDevice(DeviceInfoBean device) {
        mEditDialog = new BaseEditDialog(mContext, new OnCallBackToEdit() {
            @Override
            public void onConfirm(String name) {
                if(!TextUtils.isEmpty(name)){
                    try {
                        if(name.getBytes("GBK").length<=15){
                            if(!EmojiFilter.containsEmoji(name)) {
                                mView.getDeviceInfo(device.getDevice_ID(), name);
                                String ds = CoderALiUtils.getAscii(name);
                                String dsCRC = ByteUtil.CRCmaker(ds);
                                DeviceInfoBean deviceInfoBean1 = DeviceDaoUtil.getInstance().findGatewayByDeviceName(device.getDeviceName());
                                SendEquipmentDataAli sendEquipmentDataAli = new SendEquipmentDataAli(mContext, deviceInfoBean1);
                                sendEquipmentDataAli.modifyEquipmentName(device.getDevice_ID(), ds + dsCRC);
                                mView.showProgress();
                            }else {
                                mView.showToastMsg(mContext.getString(R.string.name_contain_emoji));
                            }
                        }else{
                            mView.showToastMsg(mContext.getString(R.string.name_is_too_long));
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }else{
                    mView.showToastMsg(mContext.getString(R.string.name_is_null));
                }
            }

            @Override
            public void onCancel() {
                mEditDialog.dismiss();
            }
        });
        mEditDialog.setTitleAndButton(mContext.getString(R.string.update_name), mContext.getString(R.string.cancel), mContext.getString(R.string.ok));
        mEditDialog.show();
    }

    private void deleteDevice(DeviceInfoBean device){
        DeviceInfoBean deviceInfoBean1 = DeviceDaoUtil.getInstance().findGatewayByDeviceName(device.getDeviceName());
        String name1 = TextUtils.isEmpty(deviceInfoBean1.getNickName())? mContext.getString(DevType.getType(deviceInfoBean1.getProductKey()).getTypeStrId()) : deviceInfoBean1.getNickName();
        String content = String.format(mContext.getString(R.string.ali_delete_sub_device_from_gateway), name1);
        mDialog = new BaseDialog(mContext, new OnCallBackToRefresh() {
            @Override
            public void onConfirm() {
                DeviceInfoBean deviceInfoBean11 = DeviceDaoUtil.getInstance().findGatewayByDeviceName(device.getDeviceName());
                SendEquipmentDataAli sendEquipmentDataAli = new SendEquipmentDataAli(mContext, deviceInfoBean11);
                sendEquipmentDataAli.deleteEquipment(device.getDevice_ID());
                mView.showProgress();
            }

            @Override
            public void onCancel() {
                mDialog.dismiss();
            }
        });
        mDialog.setTitleAndButton(content, mContext.getString(R.string.cancel), mContext.getString(R.string.ok));
        mDialog.show();
    }

    private List<String> getCode(int array){
        List<String> mCode = Arrays.asList(mContext.getResources().getStringArray(array));
        return mCode;
    }
}
