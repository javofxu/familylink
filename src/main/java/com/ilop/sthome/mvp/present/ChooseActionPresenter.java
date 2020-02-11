package com.ilop.sthome.mvp.present;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.example.common.mvp.BasePresenterImpl;
import com.example.common.utils.LiveDataBus;
import com.ilop.sthome.data.bean.DeviceInfoBean;
import com.ilop.sthome.data.db.DeviceAliDAO;
import com.ilop.sthome.data.enums.DeviceTrigger;
import com.ilop.sthome.mvp.contract.ChooseActionContract;
import com.ilop.sthome.ui.activity.scene.SettingActionActivity;
import com.ilop.sthome.ui.activity.scene.SettingDoubleSwitchActivity;
import com.ilop.sthome.ui.activity.scene.SettingHumitureActivity;
import com.ilop.sthome.ui.activity.scene.SettingTempControlActivity;
import com.ilop.sthome.ui.activity.scene.SettingTimingActivity;
import com.siterwell.familywellplus.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author skygge.
 * @Date on 2019-10-23.
 * @Dec:
 */
public class ChooseActionPresenter extends BasePresenterImpl<ChooseActionContract.IView> implements ChooseActionContract.IPresent{

    private static final String TAG = "ChooseActionPresenter";
    private Context mContext;
    private String mDeviceName;
    private DeviceAliDAO mDeviceAliDAO;
    private List<DeviceInfoBean> deviceInfoBeanList;

    public ChooseActionPresenter(Context mContext, String mDeviceName) {
        this.mContext = mContext;
        this.mDeviceName = mDeviceName;
        mDeviceAliDAO = new DeviceAliDAO(mContext);
        deviceInfoBeanList = new ArrayList<>();
    }

    @Override
    public void getDeviceInList(String deviceName) {
        deviceInfoBeanList.addAll(mDeviceAliDAO.findInput(mDeviceName));
        DeviceInfoBean device = new DeviceInfoBean();
        device.setDevice_type("TIMER");
        deviceInfoBeanList.add(device);
        DeviceInfoBean device2 = new DeviceInfoBean();
        device2.setDevice_type("CLICK");
        device2.setDevice_status("00005500");
        deviceInfoBeanList.add(device2);
        if (deviceInfoBeanList!=null && deviceInfoBeanList.size()>0){
            mView.getDeviceList(deviceInfoBeanList);
        }else {
            mView.withoutData();
        }
    }

    @Override
    public void getDeviceOutList(String deviceName) {
        List<DeviceInfoBean> gateway = mDeviceAliDAO.findAllGateway();
        deviceInfoBeanList.addAll(gateway);
        List<DeviceInfoBean> subDevice = mDeviceAliDAO.findOutput(mDeviceName);
        deviceInfoBeanList.addAll(subDevice);
        DeviceInfoBean deviceInfoBean = new DeviceInfoBean();
        deviceInfoBean.setDevice_type("PHONE");
        deviceInfoBean.setDevice_status("00005500");
        deviceInfoBeanList.add(deviceInfoBean);
        if (deviceInfoBeanList!=null && deviceInfoBeanList.size()>0){
            mView.getDeviceList(deviceInfoBeanList);
        }else {
            mView.withoutData();
        }
    }

    @Override
    public void onItemClick(int position, boolean isInput, boolean isUpdate) {
        DeviceInfoBean device = deviceInfoBeanList.get(position);
        Log.i(TAG, "onItemClick: "+device.getDevice_type());
        if(DeviceTrigger.getType(device.getDevice_type())!=null||!TextUtils.isEmpty(device.getProductKey())){
            Intent intent = new Intent(mContext, SettingActionActivity.class);
            intent.putExtra("isInput", isInput);
            intent.putExtra("isUpdate", isUpdate);
            intent.putExtra("device", device);
            mView.startActivityByIntent(intent);
        }else if(Arrays.asList(mContext.getResources().getStringArray(R.array.AA_DEV_TH_CHECK)).contains(device.getDevice_type())){
            Intent intent = new Intent(mContext, SettingHumitureActivity.class);
            intent.putExtra("isUpdate", isUpdate);
            intent.putExtra("device", device);
            mView.startActivityByIntent(intent);
        }else if(Arrays.asList(mContext.getResources().getStringArray(R.array.AA_DEV_BUTTON)).contains(device.getDevice_type())){
            device.setDevice_status("00000100");
            List<DeviceInfoBean> deviceInfo = new ArrayList<>();
            deviceInfo.add(device);
            if (isUpdate){
                LiveDataBus.get().with("update_input").setValue(deviceInfo);
                mView.finishActivity();
            }else {
                LiveDataBus.get().with("input_condition").setValue(deviceInfo);
                mView.finishActivity();
            }
        }else if(Arrays.asList(mContext.getResources().getStringArray(R.array.AA_TWO_CHANNEL_SOCKET)).contains(device.getDevice_type())){
            Intent intent = new Intent(mContext, SettingDoubleSwitchActivity.class);
            intent.putExtra("isInput", isInput);
            intent.putExtra("isUpdate", isUpdate);
            intent.putExtra("device", device);
            mView.startActivityByIntent(intent);
        }else if(Arrays.asList(mContext.getResources().getStringArray(R.array.AA_TEMP_CONTROL)).contains(device.getDevice_type())){
            Intent intent = new Intent(mContext, SettingTempControlActivity.class);
            intent.putExtra("isUpdate", isUpdate);
            intent.putExtra("device", device);
            mView.startActivityByIntent(intent);
        }else if ("TIMER".equals(device.getDevice_type())){
            Intent intent = new Intent(mContext, SettingTimingActivity.class);
            intent.putExtra("isUpdate", isUpdate);
            intent.putExtra("device", device);
            mView.startActivityByIntent(intent);
        }else if ("CLICK".equals(device.getDevice_type())){
            List<DeviceInfoBean> deviceInfo = new ArrayList<>();
            deviceInfo.add(device);
            if (isUpdate){
                LiveDataBus.get().with("update_input").setValue(deviceInfo);
                mView.finishActivity();
            }else {
                LiveDataBus.get().with("input_condition").setValue(deviceInfo);
                mView.finishActivity();
            }
        }else if ("PHONE".equals(device.getDevice_type())){
            List<DeviceInfoBean> deviceInfo = new ArrayList<>();
            deviceInfo.add(device);
            if (isUpdate){
                LiveDataBus.get().with("update_output").setValue(deviceInfo);
                mView.finishActivity();
            }else {
                LiveDataBus.get().with("output_condition").setValue(deviceInfo);
                mView.finishActivity();
            }
        }
    }
}
