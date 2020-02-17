package com.ilop.sthome.ui.activity.scene;

import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.example.common.base.BaseActivity;
import com.example.common.utils.LiveDataBus;
import com.ilop.sthome.data.greenDao.DeviceInfoBean;
import com.ilop.sthome.ui.adapter.device.WeekAdapter;
import com.ilop.sthome.utils.tools.ByteUtil;
import com.ilop.sthome.view.WheelView;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivitySetTimingBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author skygge
 * @date 2020-02-04.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：设置定时输入动作（触发条件）
 */
public class SettingTimingActivity extends BaseActivity<ActivitySetTimingBinding> {

    private boolean isUpdate;
    private DeviceInfoBean mDevice;
    private List<DeviceInfoBean> mDeviceList;

    private WeekAdapter mAdapter;
    private ArrayList<String> items_hour;
    private ArrayList<String> items_min;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_set_timing;
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
        items_hour = new ArrayList<>();
        items_min = new ArrayList<>();
        mDBind.timerMinute.setCyclic(true);
        mDBind.timerHour.setCyclic(true);
        byte mWeekInt = 0x00;
        mAdapter = new WeekAdapter(mContext, mWeekInt);
        mDBind.weekTrigger.setLayoutManager(new GridLayoutManager(mContext, 5));
        mDBind.weekTrigger.setAdapter(mAdapter);
        if (isUpdate) {
            mDBind.btDelete.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void initData() {
        super.initData();
        for (int i = 0; i < 24; i ++) {
            String item = String.valueOf(i);
            if (item.length() == 1) {
                item = "0" + item;
            }
            items_hour.add(item);
        }

        for (int i = 0; i < 60; i ++) {
            String item = String.valueOf(i);
            if (item.length() == 1) {
                item = "0" + item;
            }
            items_min.add(item);
        }
        mDBind.timerHour.setAdapter(new NumberAdapter(items_hour));
        mDBind.timerMinute.setAdapter(new NumberAdapter(items_min));
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDBind.ivBack.setOnClickListener(v -> finish());
        mDBind.tvSaveTiming.setOnClickListener(v -> {
            String setting_status = getTimerStringFromContent(mAdapter.getIsSelected(), mDBind.timerHour.getCurrentItem(), mDBind.timerMinute.getCurrentItem());
            Log.i(TAG, "initListener: "+setting_status);
            if(TextUtils.isEmpty(setting_status)){
                showToast(getString(R.string.set_first));
                return;
            }else{
                if(setting_status.startsWith("00")){
                    showToast(getString(R.string.set_weekday));
                    return;
                }
                mDevice.setDevice_type("TIMER");
                mDevice.setDevice_status(setting_status);
                mDeviceList.add(mDevice);
            }
            if (!isUpdate){
                LiveDataBus.get().with("input_condition").setValue(mDeviceList);
            }else {
                LiveDataBus.get().with("update_input").setValue(mDeviceList);
            }
            finish();
        });
        mDBind.btDelete.setOnClickListener(view -> {
            LiveDataBus.get().with("delete_condition").setValue("TIMER");
            finish();
        });
    }

    private String getTimerStringFromContent(HashMap<Integer, Boolean> week, int hour, int min) {
        byte f = 0x00;
        for(int i=0;i<week.size();i++){
            if(week.get(i)){
                f =   (byte)((0x02 << i) | f);
            }
        }
        String wek = ByteUtil.convertByte2HexString(f);
        String ds2 = hour<10?"0":"";
        String ds3 = min<10?"0":"";
        return wek + ds2 + hour + ds3 + min;
    }

    private class NumberAdapter extends WheelView.WheelArrayAdapter<String> {
        NumberAdapter(ArrayList<String> items) {
            super(items);
        }
    }
}
