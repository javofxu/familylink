package com.ilop.sthome.ui.activity.device;

import com.example.common.base.BaseActivity;
import com.ilop.sthome.ui.adapter.device.DeviceShareAdapter;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivityDeviceShareBinding;

/**
 * @author skygge
 * @date 2019/9/27.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：设备分享页面
 */

public class DeviceShareActivity extends BaseActivity<ActivityDeviceShareBinding> {

    private DeviceShareAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_device_share;
    }


    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDBind.ivShareBack.setOnClickListener(view -> finish());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
