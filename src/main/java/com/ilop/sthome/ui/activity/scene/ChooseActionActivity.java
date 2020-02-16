package com.ilop.sthome.ui.activity.scene;


import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;

import com.example.common.base.BasePActivity;
import com.example.common.utils.LiveDataBus;
import com.ilop.sthome.data.greenDao.DeviceInfoBean;
import com.ilop.sthome.mvp.contract.ChooseActionContract;
import com.ilop.sthome.mvp.present.ChooseActionPresenter;
import com.ilop.sthome.ui.adapter.scene.ChoseDeviceAdapter;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivityChooseActionBinding;

import java.util.List;


public class ChooseActionActivity extends BasePActivity<ChooseActionPresenter, ActivityChooseActionBinding> implements ChooseActionContract.IView {

    private ChoseDeviceAdapter mAdapter;
    private String mDeviceName;
    private boolean isInput;
    private boolean isUpdate;
    private int mDeviceNum;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_choose_action;
    }

    @Override
    protected void initialize() {
        super.initialize();
        isInput = getIntent().getBooleanExtra("isInput", true);
        isUpdate = getIntent().getBooleanExtra("update", false);
        mDeviceName = getIntent().getStringExtra("deviceName");
        mDeviceNum = getIntent().getIntExtra("deviceNum", 0);
    }

    @Override
    protected void initPresent() {
        super.initPresent();
        mPresent = new ChooseActionPresenter(mContext, mDeviceName, mDeviceNum);
    }

    @Override
    protected void initView() {
        super.initView();
        mDBind.tvConditionTitle.setText(isInput ? getString(R.string.increase_input) : getString(R.string.increase_output));
        mAdapter = new ChoseDeviceAdapter(mContext);
        mDBind.physicalEquipmentList.setLayoutManager(new GridLayoutManager(mContext, 3));
        mDBind.physicalEquipmentList.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        if (isInput){
            mPresent.getDeviceInList(mDeviceName);
        }else {
            mPresent.getDeviceOutList(mDeviceName);
        }

        LiveDataBus.get().with("Physical_onClick", Integer.class).observe(this, integer ->
                mPresent.onItemClick(integer, isInput, isUpdate));
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDBind.ivConditionBack.setOnClickListener(v -> finish());
    }

    @Override
    public void getDeviceList(List<DeviceInfoBean> device) {
        mAdapter.setLists(device);
    }

    @Override
    public void withoutData() {
        mAdapter.setLists(null);
    }

    @Override
    public void startActivityByIntent(Intent intent) {
        startActivity(intent);
        finish();
    }


    @Override
    public void finishActivity() {
        finish();
    }
}
