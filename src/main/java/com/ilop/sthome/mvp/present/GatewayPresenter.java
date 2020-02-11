package com.ilop.sthome.mvp.present;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.example.common.mvp.BasePresenterImpl;
import com.ilop.sthome.data.bean.DeviceInfoBean;
import com.ilop.sthome.data.bean.SysModelAliBean;
import com.ilop.sthome.data.db.DeviceAliDAO;
import com.ilop.sthome.data.db.SysmodelAliDAO;
import com.ilop.sthome.data.enums.DevType;
import com.ilop.sthome.mvp.contract.GatewayContract;
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
import com.siterwell.familywellplus.R;

import java.util.Arrays;
import java.util.List;

/**
 * @author skygge
 * @date 2020-01-02.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：
 */
public class GatewayPresenter extends BasePresenterImpl<GatewayContract.IView> implements GatewayContract.IPresent {

    private Context mContext;
    private String mDeviceName;
    private DeviceAliDAO mDeviceAliDAO;
    private SysmodelAliDAO mSysModelAliDAO;

    public GatewayPresenter(Context mContext, String deviceName) {
        this.mContext = mContext;
        this.mDeviceName = deviceName;
        mDeviceAliDAO = new DeviceAliDAO(mContext);
        mSysModelAliDAO = new SysmodelAliDAO(mContext);
    }

    @Override
    public void findAllSubDevice() {
        List<DeviceInfoBean> deviceList = mDeviceAliDAO.findAllSubDevice(mDeviceName);
        if (deviceList.size()>0){
            mView.refreshSubList(deviceList);
        }else {
            mView.refreshNoList();
        }
    }

    @Override
    public void getDeviceState() {
        DeviceInfoBean deviceInfoBean = mDeviceAliDAO.findByDeviceid(mDeviceName,0);
        int state = deviceInfoBean.getStatus();
        String name = TextUtils.isEmpty(deviceInfoBean.getNickName())? mContext.getResources().getString(DevType.getType(deviceInfoBean.getProductKey()).getTypeStrId()):deviceInfoBean.getNickName();
        SysModelAliBean sysModelAliBean = mSysModelAliDAO.findIdByChoice(mDeviceName);
        String mode ;
        if(sysModelAliBean==null){
            mode = mContext.getString(R.string.current_mode) + mContext.getString(R.string.home_mode);
        }else {
            switch (sysModelAliBean.getSid()){
                case 0:
                    mode = mContext.getString(R.string.current_mode) + mContext.getString(R.string.home_mode);
                    break;
                case 1:
                    mode = mContext.getString(R.string.current_mode) + mContext.getString(R.string.out_mode);
                    break;
                case 2:
                    mode = mContext.getString(R.string.current_mode) + mContext.getString(R.string.sleep_mode);
                    break;
                default:
                    mode = mContext.getString(R.string.current_mode) + sysModelAliBean.getModleName();
                    break;
            }
        }
        mView.refreshState(name, state, mode);
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

    private List<String> getCode(int array){
        List<String> mCode = Arrays.asList(mContext.getResources().getStringArray(array));
        return mCode;
    }
}
