package com.ilop.sthome.ui.activity.device;


import com.example.common.base.BaseActivity;
import com.ilop.sthome.data.greenDao.DeviceInfoBean;
import com.ilop.sthome.utils.greenDao.DeviceDaoUtil;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivityDeviceDetailBinding;

/**
 * @author skygge
 * @date 2019/11/08.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：设备详情
 */

public class DeviceDetailActivity extends BaseActivity<ActivityDeviceDetailBinding> {

    private String mDeviceName;
    private DeviceInfoBean mDeviceInfoBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_device_detail;
    }

    @Override
    protected void initialize() {
        super.initialize();
        immersionStatusBar(true);
        mDeviceName = getIntent().getStringExtra("deviceName");
    }

    @Override
    protected void initView() {
        super.initView();
        mDeviceInfoBean = DeviceDaoUtil.getInstance().findGatewayByDeviceName( mDeviceName);
    }

    @Override
    protected void initData() {
        super.initData();
        mDBind.siIotId.setDetailText(mDeviceInfoBean.getIotId());
        mDBind.siDeviceName.setDetailText(mDeviceInfoBean.getDeviceName());
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDBind.ivStatusBack.setOnClickListener(view -> finish());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
