package com.ilop.sthome.ui.activity.scene;

import android.text.TextUtils;
import android.view.View;

import com.example.common.base.BaseActivity;
import com.example.common.utils.LiveDataBus;
import com.ilop.sthome.data.enums.DeviceTrigger;
import com.ilop.sthome.data.greenDao.DeviceInfoBean;
import com.ilop.sthome.ui.adapter.detail.OptionAdapter;
import com.ilop.sthome.view.WheelView;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivitySetActionBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author skygge
 * @date 2020-02-02.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：设置输入输出动作（触发条件和执行动作）
 */
public class SettingActionActivity extends BaseActivity<ActivitySetActionBinding> {

    private boolean isInput;
    private boolean isUpdate;
    private String[] mTrigger;
    private DeviceInfoBean mDevice;

    private List<DeviceInfoBean> mDeviceList;
    private ArrayList<String> items_min;
    private ArrayList<String> items_trigger;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set_action;
    }

    @Override
    protected void initialize() {
        super.initialize();
        isInput = getIntent().getBooleanExtra("isInput", false);
        isUpdate = getIntent().getBooleanExtra("isUpdate", false);
        mDevice = (DeviceInfoBean) getIntent().getSerializableExtra("device");
        if (isInput){
            mDBind.tvSetTitle.setText(getString(R.string.set_criteria));
        }else {
            mDBind.tvSetAction.setText(getString(R.string.executive_action));
        }
    }

    @Override
    protected void initView() {
        super.initView();
        mDBind.actionIsOutput.setVisibility(isInput ? View.GONE : View.VISIBLE);
        mDBind.actionMinute.setCyclic(true);
        mDBind.actionSecond.setCyclic(true);
        items_min = new ArrayList<>();
        items_trigger = new ArrayList<>();
        mDeviceList = new ArrayList<>();
    }

    @Override
    protected void initData() {
        super.initData();
        for (int i = 0; i < 60; i++) {
            String item = String.valueOf(i);
            if (item.length() == 1) {
                item = "0" + item;
            }
            items_min.add(item);
        }
        if (!TextUtils.isEmpty(mDevice.getProductKey())){
            mTrigger = mContext.getResources().getStringArray(DeviceTrigger.getType("GATEWAY").getCode());
            String[] str = mContext.getResources().getStringArray(DeviceTrigger.getType("GATEWAY").getState());
            items_trigger.addAll(Arrays.asList(str));
        }else {
            mTrigger = mContext.getResources().getStringArray(DeviceTrigger.getType(mDevice.getDevice_type()).getCode());
            String[] str = mContext.getResources().getStringArray(DeviceTrigger.getType(mDevice.getDevice_type()).getState());
            items_trigger.addAll(Arrays.asList(str));
        }
        mDBind.actionTrigger.setAdapter(new OptionAdapter(items_trigger, 150));
        mDBind.actionMinute.setAdapter(new NumberAdapter(items_min));
        mDBind.actionSecond.setAdapter(new NumberAdapter(items_min));
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDBind.tvSaveAction.setOnClickListener(v -> {
            if (isInput) {
                mDevice.setDevice_status(mTrigger[mDBind.actionTrigger.getCurrentItem()]);
                mDeviceList.add(mDevice);
                if (!isUpdate){
                    LiveDataBus.get().with("input_condition").setValue(mDeviceList);
                }else {
                    LiveDataBus.get().with("update_input").setValue(mDeviceList);
                }
            } else {
                int minute = mDBind.actionMinute.getCurrentItem();
                int second = mDBind.actionSecond.getCurrentItem();

                String ds = minute < 10 ? "0" : "";
                String ds2 = second < 10 ? "0" : "";
                String setting_status = ds + minute + ds2 + second;

                if (minute != 0 || second != 0) {
                    DeviceInfoBean deviceInfoBean = new DeviceInfoBean();
                    deviceInfoBean.setDevice_status(setting_status);
                    deviceInfoBean.setDevice_type("DELAY");
                    mDeviceList.add(deviceInfoBean);
                }
                mDevice.setDevice_status(mTrigger[mDBind.actionTrigger.getCurrentItem()]);
                mDeviceList.add(mDevice);
                if (!isUpdate){
                    LiveDataBus.get().with("output_condition").setValue(mDeviceList);
                }else {
                    LiveDataBus.get().with("update_output").setValue(mDeviceList);
                }
            }
            finish();
        });

        mDBind.ivBack.setOnClickListener(v -> finish());
    }

    private class NumberAdapter extends WheelView.WheelArrayAdapter<String> {

        NumberAdapter(ArrayList<String> items) {
            super(items);
        }
    }
}
