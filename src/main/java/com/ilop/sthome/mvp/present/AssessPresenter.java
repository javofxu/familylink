package com.ilop.sthome.mvp.present;

import android.content.Context;

import com.example.common.mvp.BasePresenterImpl;
import com.ilop.sthome.data.bean.CheckDeviceBean;
import com.ilop.sthome.data.enums.SmartProduct;
import com.ilop.sthome.data.greenDao.CameraBean;
import com.ilop.sthome.data.greenDao.DeviceInfoBean;
import com.ilop.sthome.mvp.contract.AssessContract;
import com.ilop.sthome.utils.greenDao.CameraDaoUtil;
import com.ilop.sthome.utils.greenDao.DeviceDaoUtil;
import com.siterwell.familywellplus.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author skygge.
 * @Date on 2019-10-10.
 * @Dec:
 */
public class AssessPresenter extends BasePresenterImpl<AssessContract.IView> implements AssessContract.IPresent {

    private Context mContext;
    private int mCount;
    private int mScore;

    private Runnable mRunnable;

    private List<CheckDeviceBean> mCheckList;
    private List<DeviceInfoBean> deviceList1;
    private List<DeviceInfoBean> deviceList2;
    private List<DeviceInfoBean> deviceList3;
    private List<DeviceInfoBean> deviceList4;


    public AssessPresenter(Context mContext) {
        this.mContext = mContext;
        mCheckList = new ArrayList<>();
        deviceList1 = new ArrayList<>();
        deviceList2 = new ArrayList<>();
        deviceList3 = new ArrayList<>();
        deviceList4 = new ArrayList<>();
    }

    @Override
    public void startAnalyze() {
        mRunnable = () -> detection();
        new Thread(mRunnable).start();
    }

    @Override
    public void detection() {
        mCheckList.clear();

        List<DeviceInfoBean> list = DeviceDaoUtil.getInstance().findAllDevice();
        List<DeviceInfoBean> k = DeviceDaoUtil.getInstance().findAllGateway();
        mScore = list.size()+k.size();

        //智能消防
        deviceList1.clear();
        CheckDeviceBean check1 = new CheckDeviceBean();
        check1.setDeviceIcon(R.mipmap.intelligent_system);
        check1.setDeviceType(mContext.getString(R.string.ali_wifi_product));

        DeviceInfoBean deviceInfoBean_01 = new DeviceInfoBean();
        deviceInfoBean_01.setDevice_ID(1);
        deviceInfoBean_01.setDevice_type("GATEWAY");
        deviceInfoBean_01.setOwned(k.size() == 0 ? 0:1);
        deviceList1.add(deviceInfoBean_01);
        check1.setDevice(deviceList1);
        mCheckList.add(check1);

        //智能消防
        deviceList2.clear();
        CheckDeviceBean check2 = new CheckDeviceBean();
        check2.setDeviceIcon(R.mipmap.intelligent_fire_protection);
        check2.setDeviceType(mContext.getString(R.string.ali_alarm_product));

        DeviceInfoBean deviceInfoBean_11 = new DeviceInfoBean();
        List<String> SM_ALARM = getCode(R.array.AA_DEV_SM_ALARM);
        for (int i = 0; i < SM_ALARM.size(); i++) {
            int count = DeviceDaoUtil.getInstance().isDevTypeExists(SM_ALARM.get(i));
            if (count!=0){
                deviceInfoBean_11.setDevice_type(SM_ALARM.get(i));
                deviceInfoBean_11.setOwned(1);
                break;
            }else {
                deviceInfoBean_11.setDevice_type(SM_ALARM.get(i));
                deviceInfoBean_11.setOwned(0);
            }
        }
        deviceInfoBean_11.setDevice_ID(3);
        deviceList2.add(deviceInfoBean_11);

        DeviceInfoBean deviceInfoBean_12 = new DeviceInfoBean();
        List<String> CO_ALARM = getCode(R.array.AA_DEV_CO_ALARM);
        for (int i = 0; i < CO_ALARM.size(); i++) {
            int count = DeviceDaoUtil.getInstance().isDevTypeExists(CO_ALARM.get(i));
            if (count!=0){
                deviceInfoBean_12.setDevice_type(CO_ALARM.get(i));
                deviceInfoBean_12.setOwned(1);
                break;
            }else {
                deviceInfoBean_12.setDevice_type(CO_ALARM.get(i));
                deviceInfoBean_12.setOwned(0);
            }
        }
        deviceInfoBean_12.setDevice_ID(4);
        deviceList2.add(deviceInfoBean_12);

        DeviceInfoBean deviceInfoBean_13 = new DeviceInfoBean();
        List<String> WT_ALARM = getCode(R.array.AA_DEV_WT_ALARM);
        for (int i = 0; i < WT_ALARM.size(); i++) {
            int count = DeviceDaoUtil.getInstance().isDevTypeExists(WT_ALARM.get(i));
            if (count!=0){
                deviceInfoBean_13.setDevice_type(WT_ALARM.get(i));
                deviceInfoBean_13.setOwned(1);
                break;
            }else {
                deviceInfoBean_13.setDevice_type(WT_ALARM.get(i));
                deviceInfoBean_13.setOwned(0);
            }
        }
        deviceInfoBean_13.setDevice_ID(5);
        deviceList2.add(deviceInfoBean_13);

        DeviceInfoBean deviceInfoBean_14 = new DeviceInfoBean();
        List<String> SX_SM_ALARM = getCode(R.array.AA_DEV_SX_SM_ALARM);
        for (int i = 0; i < SX_SM_ALARM.size(); i++) {
            int count = DeviceDaoUtil.getInstance().isDevTypeExists(SX_SM_ALARM.get(i));
            if (count!=0){
                deviceInfoBean_14.setDevice_type(SX_SM_ALARM.get(i));
                deviceInfoBean_14.setOwned(1);
                break;
            }else {
                deviceInfoBean_14.setDevice_type(SX_SM_ALARM.get(i));
                deviceInfoBean_14.setOwned(0);
            }
        }
        deviceInfoBean_14.setDevice_ID(6);
        deviceList2.add(deviceInfoBean_14);

        DeviceInfoBean deviceInfoBean_15 = new DeviceInfoBean();
        List<String> GAS_ALARM = getCode(R.array.AA_DEV_GAS_ALARM);
        for (int i = 0; i < GAS_ALARM.size(); i++) {
            int count = DeviceDaoUtil.getInstance().isDevTypeExists(GAS_ALARM.get(i));
            if (count!=0){
                deviceInfoBean_15.setDevice_type(GAS_ALARM.get(i));
                deviceInfoBean_15.setOwned(1);
                break;
            }else {
                deviceInfoBean_15.setDevice_type(GAS_ALARM.get(i));
                deviceInfoBean_15.setOwned(0);
            }
        }
        deviceInfoBean_15.setDevice_ID(7);
        deviceList2.add(deviceInfoBean_15);

        DeviceInfoBean deviceInfoBean_16 = new DeviceInfoBean();
        List<String> THE_RM_ALARM = getCode(R.array.AA_DEV_THE_RM_ALARM);
        for (int i = 0; i < THE_RM_ALARM.size(); i++) {
            int count = DeviceDaoUtil.getInstance().isDevTypeExists(THE_RM_ALARM.get(i));
            if (count!=0){
                deviceInfoBean_16.setDevice_type(THE_RM_ALARM.get(i));
                deviceInfoBean_16.setOwned(1);
                break;
            }else {
                deviceInfoBean_16.setDevice_type(THE_RM_ALARM.get(i));
                deviceInfoBean_16.setOwned(0);
            }
        }
        deviceInfoBean_16.setDevice_ID(8);
        deviceList2.add(deviceInfoBean_16);
        check2.setDevice(deviceList2);
        mCheckList.add(check2);

        //智能防盗
        deviceList3.clear();
        CheckDeviceBean check3 = new CheckDeviceBean();
        check3.setDeviceIcon(R.mipmap.device_prevent);
        check3.setDeviceType(mContext.getString(R.string.ali_theft_product));

        List<CameraBean> j = CameraDaoUtil.getInstance().getCameraDao().queryAll();
        DeviceInfoBean deviceInfoBean_21 = new DeviceInfoBean();
        deviceInfoBean_21.setDevice_ID(10);
        deviceInfoBean_21.setDevice_type("IPC");
        deviceInfoBean_21.setOwned(j.size() == 0 ? 0:1);
        deviceList3.add(deviceInfoBean_21);

        mCount = DeviceDaoUtil.getInstance().isDevTypeExists(SmartProduct.EE_DEV_DOOR1.getDevType());
        DeviceInfoBean deviceInfoBean_22 = new DeviceInfoBean();
        deviceInfoBean_22.setDevice_ID(11);
        deviceInfoBean_22.setDevice_type(SmartProduct.EE_DEV_DOOR1.getDevType());
        deviceInfoBean_22.setOwned(mCount == 0 ? 0:1);
        deviceList3.add(deviceInfoBean_22);

        mCount = DeviceDaoUtil.getInstance().isDevTypeExists(SmartProduct.EE_DEV_PIR1.getDevType());
        DeviceInfoBean deviceInfoBean_23 = new DeviceInfoBean();
        deviceInfoBean_23.setDevice_type(SmartProduct.EE_DEV_PIR1.getDevType());
        deviceInfoBean_23.setOwned(mCount == 0 ? 0:1);
        deviceInfoBean_23.setDevice_ID(12);
        deviceList3.add(deviceInfoBean_23);

        mCount = DeviceDaoUtil.getInstance().isDevTypeExists(SmartProduct.EE_DEV_SOS1.getDevType());
        DeviceInfoBean deviceInfoBean_24 = new DeviceInfoBean();
        deviceInfoBean_24.setDevice_type(SmartProduct.EE_DEV_SOS1.getDevType());
        deviceInfoBean_24.setOwned(mCount == 0 ? 0:1);
        deviceInfoBean_24.setDevice_ID(13);
        deviceList3.add(deviceInfoBean_24);

        mCount = DeviceDaoUtil.getInstance().isDevTypeExists(SmartProduct.EE_DEV_LOCK.getDevType());
        DeviceInfoBean deviceInfoBean_25 = new DeviceInfoBean();
        deviceInfoBean_25.setDevice_type(SmartProduct.EE_DEV_LOCK.getDevType());
        deviceInfoBean_25.setOwned(mCount == 0 ? 0:1);
        deviceInfoBean_25.setDevice_ID(14);
        deviceList3.add(deviceInfoBean_25);

        mCount = DeviceDaoUtil.getInstance().isDevTypeExists(SmartProduct.EE_TEMP_OUTDOOR_SIREN1.getDevType());
        DeviceInfoBean deviceInfoBean_26 = new DeviceInfoBean();
        deviceInfoBean_26.setDevice_type(SmartProduct.EE_TEMP_OUTDOOR_SIREN1.getDevType());
        deviceInfoBean_26.setOwned(mCount == 0 ? 0:1);
        deviceInfoBean_26.setDevice_ID(15);
        deviceList3.add(deviceInfoBean_26);
        check3.setDevice(deviceList3);
        mCheckList.add(check3);

        //智能环境
        deviceList4.clear();
        CheckDeviceBean check4 = new CheckDeviceBean();
        check4.setDeviceIcon(R.mipmap.device_prevent);
        check4.setDeviceType(mContext.getString(R.string.ali_environment_product));

        mCount = DeviceDaoUtil.getInstance().isDevTypeExists(SmartProduct.EE_DEV_SOCKET1.getDevType());
        DeviceInfoBean deviceInfoBean_31 = new DeviceInfoBean();
        deviceInfoBean_31.setDevice_type(SmartProduct.EE_DEV_SOCKET1.getDevType());
        deviceInfoBean_31.setOwned(mCount == 0 ? 0:1);
        deviceInfoBean_31.setDevice_ID(17);
        deviceList4.add(deviceInfoBean_31);

        mCount = DeviceDaoUtil.getInstance().isDevTypeExists(SmartProduct.EE_DEV_THCHECK1.getDevType());
        DeviceInfoBean deviceInfoBean_32 = new DeviceInfoBean();
        deviceInfoBean_32.setDevice_type(SmartProduct.EE_DEV_THCHECK1.getDevType());
        deviceInfoBean_32.setOwned(mCount == 0 ? 0:1);
        deviceInfoBean_32.setDevice_ID(18);
        deviceList4.add(deviceInfoBean_32);

        mCount = DeviceDaoUtil.getInstance().isDevTypeExists(SmartProduct.EE_DEV_BUTTON1.getDevType());
        DeviceInfoBean deviceInfoBean_33 = new DeviceInfoBean();
        deviceInfoBean_33.setDevice_type(SmartProduct.EE_DEV_BUTTON1.getDevType());
        deviceInfoBean_33.setOwned(mCount == 0 ? 0:1);
        deviceInfoBean_33.setDevice_ID(19);
        deviceList4.add(deviceInfoBean_33);

        mCount = DeviceDaoUtil.getInstance().isDevTypeExists(SmartProduct.EE_DEV_MODE_BUTTON.getDevType());
        DeviceInfoBean deviceInfoBean_34 = new DeviceInfoBean();
        deviceInfoBean_34.setDevice_type(SmartProduct.EE_DEV_MODE_BUTTON.getDevType());
        deviceInfoBean_34.setOwned(mCount == 0 ? 0:1);
        deviceInfoBean_34.setDevice_ID(20);
        deviceList4.add(deviceInfoBean_34);

        mCount = DeviceDaoUtil.getInstance().isDevTypeExists(SmartProduct.EE_TWO_CHANNEL_SOCKET1.getDevType());
        DeviceInfoBean deviceInfoBean_35 = new DeviceInfoBean();
        deviceInfoBean_35.setDevice_type(SmartProduct.EE_TWO_CHANNEL_SOCKET1.getDevType());
        deviceInfoBean_35.setOwned(mCount == 0 ? 0:1);
        deviceInfoBean_35.setDevice_ID(21);
        deviceList4.add(deviceInfoBean_35);

        mCount = DeviceDaoUtil.getInstance().isDevTypeExists(SmartProduct.EE_TEMP_CONTROL1.getDevType());
        DeviceInfoBean deviceInfoBean_36 = new DeviceInfoBean();
        deviceInfoBean_36.setDevice_type(SmartProduct.EE_TEMP_CONTROL1.getDevType());
        deviceInfoBean_36.setOwned(mCount == 0 ? 0:1);
        deviceInfoBean_36.setDevice_ID(22);
        deviceList4.add(deviceInfoBean_36);
        check4.setDevice(deviceList4);
        mCheckList.add(check4);

        mView.refresh(mScore, mCheckList);
    }


    private List<String> getCode(int array){
        List<String> mCode = Arrays.asList(mContext.getResources().getStringArray(array));
        return mCode;
    }
}
