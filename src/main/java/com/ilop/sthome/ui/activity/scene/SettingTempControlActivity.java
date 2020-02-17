package com.ilop.sthome.ui.activity.scene;

import android.util.Log;

import com.example.common.base.BaseActivity;
import com.example.common.utils.LiveDataBus;
import com.ilop.sthome.data.greenDao.DeviceInfoBean;
import com.ilop.sthome.utils.tools.ByteUtil;
import com.ilop.sthome.view.WheelView;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivitySetTempControlBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author skygge.
 * @Date on 2020-02-04.
 * @Dec: 设置温控器输出动作（执行动作）
 */
public class SettingTempControlActivity extends BaseActivity<ActivitySetTempControlBinding> {

    private boolean isUpdate;
    private DeviceInfoBean mDevice;

    private List<DeviceInfoBean> mDeviceList;
    private ArrayList<String> set_decimal;
    private ArrayList<String> items_temp;
    private ArrayList<String> items_min;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set_temp_control;
    }

    @Override
    protected void initialize() {
        super.initialize();
        isUpdate = getIntent().getBooleanExtra("isUpdate", false);
        mDevice = (DeviceInfoBean) getIntent().getSerializableExtra("device");
    }

    @Override
    protected void initView() {
        super.initView();
        mDeviceList = new ArrayList<>();
        items_temp = new ArrayList<>();
        set_decimal = new ArrayList<>();
        items_min = new ArrayList<>();
        mDBind.actionMinute.setCyclic(true);
        mDBind.actionSecond.setCyclic(true);
    }

    @Override
    protected void initData() {
        super.initData();
        String[] set_num = {"0", "5"};
        for (int i = 5; i < 31; i++) {
            items_temp.add(String.valueOf(i));
        }
        set_decimal.addAll(Arrays.asList(set_num));
        for (int i = 0; i < 60; i ++) {
            String item = String.valueOf(i);
            if (item.length() == 1) {
                item = "0" + item;
            }
            items_min.add(item);
        }
        mDBind.setTemp.setAdapter(new NumberAdapter(items_temp, 30));
        mDBind.setTempTwo.setAdapter(new NumberAdapter(set_decimal, 30));
        mDBind.actionMinute.setAdapter(new NumberAdapter(items_min, 30));
        mDBind.actionSecond.setAdapter(new NumberAdapter(items_min, 30));
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDBind.ivBack.setOnClickListener(v -> finish());
        mDBind.tvSaveTemp.setOnClickListener(v -> {
            int minute = mDBind.actionMinute.getCurrentItem();
            int second = mDBind.actionSecond.getCurrentItem();

            String ds1 = minute<10?"0":"";
            String ds2 = second<10?"0":"";
            String setting_status = ds1 + minute + ds2+ second;
            Log.i("AAA", "initListener: "+ setting_status);

            if (minute != 0 || second != 0) {
                DeviceInfoBean deviceInfoBean = new DeviceInfoBean();
                deviceInfoBean.setDevice_status(setting_status);
                deviceInfoBean.setDevice_type("DELAY");
                mDeviceList.add(deviceInfoBean);
            }

            if (mDBind.setTemp.getCurrentItem() < (items_temp.size() - 1)) {
                byte ds = (byte) (mDBind.setTemp.getCurrentItem() + 5);
                if (mDBind.setTempTwo.getCurrentItem() == 1) {
                    ds |= 0x20;
                }
                String str1 = ByteUtil.convertByte2HexString(ds);
                mDevice.setDevice_status(str1 + "800000");
            } else {
                mDevice.setDevice_status("1E800000");
            }
            mDeviceList.add(mDevice);
            if (!isUpdate){
                LiveDataBus.get().with("output_condition").setValue(mDeviceList);
            }else {
                LiveDataBus.get().with("update_output").setValue(mDeviceList);
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
