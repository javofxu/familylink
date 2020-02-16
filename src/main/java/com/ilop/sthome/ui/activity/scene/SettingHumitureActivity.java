package com.ilop.sthome.ui.activity.scene;

import android.text.TextUtils;

import com.alibaba.sdk.android.openaccount.ui.util.ToastUtils;
import com.example.common.base.BaseActivity;
import com.example.common.utils.LiveDataBus;
import com.ilop.sthome.data.greenDao.DeviceInfoBean;
import com.ilop.sthome.view.WheelView;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivitySetHumitureBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author skygge.
 * @Date on 2020-02-04.
 * @Dec: 设置温湿度输入动作（触发条件）
 */
public class SettingHumitureActivity extends BaseActivity<ActivitySetHumitureBinding> {

    private String mTemp;
    private String mHum;
    private boolean isUpdate;
    private DeviceInfoBean mDevice;

    private List<DeviceInfoBean> mDeviceList;
    private ArrayList<String> item_temp;
    private ArrayList<String> item_hum;
    private ArrayList<String> item_style;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set_humiture;
    }

    @Override
    protected void initialize() {
        super.initialize();
        isUpdate = getIntent().getBooleanExtra("isUpdate", false);
        mDevice = getIntent().getParcelableExtra("device");
    }

    @Override
    protected void initView() {
        super.initView();
        mDBind.humNum.setCyclic(true);
        mDBind.tempNum.setCyclic(true);
        item_temp = new ArrayList<>();
        item_hum = new ArrayList<>();
        item_style = new ArrayList<>();
        mDeviceList = new ArrayList<>();
    }

    @Override
    protected void initData() {
        super.initData();
        String[] str = mContext.getResources().getStringArray(R.array.thtrigger_style);
        item_style.addAll(Arrays.asList(str));

        for (int i = -40; i <= 90; i ++) {
            String item = String.valueOf(i);
            if ( i>-10 && i<0) {
                item =  item.substring(0,1)+"0"+item.substring(1);
            }else if(i>=0 && i< 10){
                item = "0" + item;
            }
            item_temp.add(item+"℃");
        }

        for (int i = 1; i <= 100; i ++) {
            String item = String.valueOf(i);
            if (item != null && item.length() == 1 && i>=0 && i<10) {
                item = "0" + item;
            }
            item_hum.add(item+"%");
        }

        mDBind.tempStyle.setAdapter(new NumberAdapter(item_style,210));
        mDBind.humStyle.setAdapter(new NumberAdapter(item_style,210));
        mDBind.tempNum.setAdapter(new NumberAdapter(item_temp,210));
        mDBind.humNum.setAdapter(new NumberAdapter(item_hum,210));
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDBind.ivBack.setOnClickListener(v -> finish());
        mDBind.tvSaveHumiture.setOnClickListener(v -> {
            if (TextUtils.isEmpty(mTemp) && TextUtils.isEmpty(mHum)){
                ToastUtils.toast(mContext, mContext.getString(R.string.choose_condition));
                return;
            }
            int a = mDBind.tempNum.getCurrentItem();
            String temp;
            if(a<40){
                temp = Integer.toHexString(256+( a-40 )).toUpperCase();
            } else {
                temp = Integer.toHexString(a-40).toUpperCase();
                if(temp.length()==1){
                    temp = "0"+temp;
                }
            }
            String hum =Integer.toHexString(mDBind.humNum.getCurrentItem()+1).toUpperCase();
            if(hum.length()==1){
                hum = "0"+hum;
            }
            String dsd = null;
            if (!TextUtils.isEmpty(mTemp)) {
                switch (mDBind.tempStyle.getCurrentItem()) {
                    case 0:
                        dsd = temp + "7F" + "00" + "FF";
                        break;
                    case 1:
                        dsd = "D8" + temp + "00" + "FF";
                        break;
                }
            }
            if (!TextUtils.isEmpty(mHum)) {
                switch (mDBind.humStyle.getCurrentItem()) {
                    case 0:
                        dsd = "D8" + "7F" + hum + "FF";
                        break;
                    case 1:
                        dsd = "D8" + "7F" + "00" + hum;
                        break;
                }
            }
            mDevice.setDevice_status(dsd);
            mDeviceList.add(mDevice);
            if (!isUpdate){
                LiveDataBus.get().with("input_condition").setValue(mDeviceList);
            }else {
                LiveDataBus.get().with("update_output").setValue(mDeviceList);
            }
            finish();
        });

        mDBind.tempStyle.addChangingListener(((wheel, oldValue, newValue) -> {
            mTemp = mContext.getString(R.string.ali_temp)+item_style.get(mDBind.tempStyle.getCurrentItem())+item_temp.get(mDBind.tempNum.getCurrentItem());
        }));
        mDBind.tempNum.addChangingListener(((wheel, oldValue, newValue) -> {
            mTemp = mContext.getString(R.string.ali_temp)+item_style.get(mDBind.tempStyle.getCurrentItem())+item_temp.get(mDBind.tempNum.getCurrentItem());
        }));
        mDBind.humStyle.addChangingListener(((wheel, oldValue, newValue) -> {
            mHum = mContext.getString(R.string.hum)+item_style.get(mDBind.humStyle.getCurrentItem())+item_hum.get(mDBind.humStyle.getCurrentItem());
        }));
        mDBind.humNum.addChangingListener(((wheel, oldValue, newValue) -> {
            mHum = mContext.getString(R.string.hum)+item_style.get(mDBind.humStyle.getCurrentItem())+item_hum.get(mDBind.humStyle.getCurrentItem());
        }));
    }

    private class NumberAdapter extends WheelView.WheelArrayAdapter<String> {

        NumberAdapter(ArrayList<String> items, int length) {
            super(items,length);
        }
    }
}
