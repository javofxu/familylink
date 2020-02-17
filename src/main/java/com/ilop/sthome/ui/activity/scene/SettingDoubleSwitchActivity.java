package com.ilop.sthome.ui.activity.scene;

import android.util.Log;
import android.view.View;

import com.example.common.base.BaseActivity;
import com.example.common.utils.LiveDataBus;
import com.ilop.sthome.data.greenDao.DeviceInfoBean;
import com.ilop.sthome.view.WheelView;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivitySetDoubleSwitchBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author skygge.
 * @Date on 2020-02-04.
 * @Dec: 设置双路开关输入输出动作（触发条件和执行动作）
 */
public class SettingDoubleSwitchActivity extends BaseActivity<ActivitySetDoubleSwitchBinding> {

    private boolean isInput;
    private boolean isUpdate;
    private DeviceInfoBean mDevice;

    private List<DeviceInfoBean> mDeviceList;
    private ArrayList<String> item_channel;
    private ArrayList<String> item_style;
    private ArrayList<String> item_order1;
    private ArrayList<String> item_order2;
    private ArrayList<String> items_timer;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set_double_switch;
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
        item_channel = new ArrayList<>();
        item_style = new ArrayList<>();
        item_order1 = new ArrayList<>();
        item_order2 = new ArrayList<>();
        items_timer = new ArrayList<>();
        mDeviceList = new ArrayList<>();
        mDBind.switchIsOutput.setVisibility(isInput ? View.GONE : View.VISIBLE);
        mDBind.actionMinute.setCyclic(true);
        mDBind.actionSecond.setCyclic(true);
    }

    @Override
    protected void initData() {
        super.initData();
        for (int i = 0; i < 60; i++) {
            String item = String.valueOf(i);
            if (item.length() == 1) {
                item = "0" + item;
            }
            items_timer.add(item);
        }

        String[] str = mContext.getResources().getStringArray(R.array.socket_channel);
        item_channel.addAll(Arrays.asList(str));
        String[] str2 = mContext.getResources().getStringArray(R.array.socket_actions);
        item_style.addAll(Arrays.asList(str2));
        String[] str3 = mContext.getResources().getStringArray(R.array.EE_DEV_SOCKET1);
        item_order1.addAll(Arrays.asList(str3));
        String[] str4 = mContext.getResources().getStringArray(R.array.EE_DEV_SOCKET2);
        item_order2.addAll(Arrays.asList(str4));

        mDBind.itemDualDevice.setAdapter(new NumberAdapter(item_channel, 210));
        mDBind.itemDualDevice2.setAdapter(new NumberAdapter(item_style, 210));
        mDBind.actionMinute.setAdapter(new NumberAdapter(items_timer, 30));
        mDBind.actionSecond.setAdapter(new NumberAdapter(items_timer, 30));
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDBind.ivBack.setOnClickListener(v -> finish());
        mDBind.tvSaveSwitch.setOnClickListener(v -> {
            if (isInput){
                if (mDBind.itemDualDevice.getCurrentItem() == 0) {
                    mDevice.setDevice_status(item_order1.get(mDBind.itemDualDevice2.getCurrentItem()));
                    mDevice.setProductName(item_channel.get(0) + " " + item_style.get(mDBind.itemDualDevice2.getCurrentItem()));
                } else if (mDBind.itemDualDevice.getCurrentItem() == 1) {
                    mDevice.setDevice_status(item_order2.get(mDBind.itemDualDevice2.getCurrentItem()));
                    mDevice.setProductName(item_channel.get(1) + " " + item_style.get(mDBind.itemDualDevice2.getCurrentItem()));
                }
                mDeviceList.add(mDevice);
                if (!isUpdate){
                    LiveDataBus.get().with("input_condition").setValue(mDeviceList);
                }else {
                    LiveDataBus.get().with("update_input").setValue(mDeviceList);
                }
            }else {
                int minute = mDBind.actionMinute.getCurrentItem();
                int second = mDBind.actionSecond.getCurrentItem();

                String ds = minute<10?"0":"";
                String ds2 = second<10?"0":"";
                String setting_status = ds + minute + ds2+ second;
                Log.i("AAA", "initListener: "+ setting_status);

                if (minute != 0 || second != 0) {
                    DeviceInfoBean deviceInfoBean = new DeviceInfoBean();
                    deviceInfoBean.setDevice_status(setting_status);
                    deviceInfoBean.setDevice_type("DELAY");
                    mDeviceList.add(deviceInfoBean);
                }

                if (mDBind.itemDualDevice.getCurrentItem() == 0) {
                    mDevice.setDevice_status(item_order1.get(mDBind.itemDualDevice2.getCurrentItem()));
                } else if (mDBind.itemDualDevice.getCurrentItem() == 1) {
                    mDevice.setDevice_status(item_order2.get(mDBind.itemDualDevice2.getCurrentItem()));
                }
                mDeviceList.add(mDevice);
                if (!isUpdate){
                    LiveDataBus.get().with("output_condition").setValue(mDeviceList);
                }else {
                    LiveDataBus.get().with("update_output").setValue(mDeviceList);
                }
            }
            finish();
        });
    }

    private class NumberAdapter extends WheelView.WheelArrayAdapter<String> {

        NumberAdapter(ArrayList<String> items, int length) {
            super(items, length);
        }
    }
}
