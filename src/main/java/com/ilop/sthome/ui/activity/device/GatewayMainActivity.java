package com.ilop.sthome.ui.activity.device;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.example.common.base.BasePActivity;
import com.example.common.view.refresh.CustomRefreshView;
import com.ilop.sthome.data.event.EventRefreshScene;
import com.ilop.sthome.data.greenDao.DeviceInfoBean;
import com.ilop.sthome.mvp.contract.GatewayContract;
import com.ilop.sthome.mvp.present.GatewayPresenter;
import com.ilop.sthome.ui.activity.config.ProductActivity;
import com.ilop.sthome.ui.adapter.device.DeviceChildAdapter;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivityGatewayMainBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * @author skygge
 * @date 2019/9/27.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：网关主页
 */

public class GatewayMainActivity extends BasePActivity<GatewayPresenter, ActivityGatewayMainBinding> implements DeviceChildAdapter.subDeviceCallBack, GatewayContract.IView {

    private String deviceName;
    private DeviceChildAdapter mChildAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_gateway_main;
    }

    @Override
    protected void initialize() {
        super.initialize();
        EventBus.getDefault().register(this);
        deviceName = getIntent().getStringExtra("deviceName");
    }

    @Override
    protected void initPresent() {
        super.initPresent();
        mPresent = new GatewayPresenter(this, deviceName);
    }

    public void initView(){
        mChildAdapter = new DeviceChildAdapter(mContext);
        mChildAdapter.setCallBack(this);
        mDBind.gatewaySubDevice.getRecyclerView().setLayoutManager(new GridLayoutManager(mContext, 2));
        mDBind.gatewaySubDevice.setAdapter(mChildAdapter);
        mDBind.gatewaySubDevice.setRefreshing(false);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresent.findAllSubDevice();
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDBind.ivGatewayBack.setOnClickListener(view -> finish());
        mDBind.tabHistory.setOnClickListener(view -> {
            Intent intent = new Intent(this, DeviceHistoryActivity.class);
            intent.putExtra("deviceName",deviceName);
            startActivity(intent);
        });
        mDBind.tabDetails.setOnClickListener(view -> {
            Intent intent = new Intent(this, DeviceDetailActivity.class);
            intent.putExtra("deviceName",deviceName);
            startActivity(intent);
        });
        mDBind.tabSetting.setOnClickListener(view -> {
            Intent intent = new Intent(this, DeviceSettingActivity.class);
            intent.putExtra("deviceName",deviceName);
            startActivity(intent);
        });
        mDBind.gatewaySubAdd.setOnClickListener(view -> {
            Intent intent = new Intent(this, ProductActivity.class);
            intent.putExtra("isSubDevice",true);
            startActivity(intent);
        });
        mDBind.stateModelChange.setOnClickListener(view -> {
            Intent intent = new Intent(this, ChangeSceneActivity.class);
            intent.putExtra("deviceName",deviceName);
            startActivity(intent);
        });
        mDBind.gatewaySubDevice.setOnLoadListener(new CustomRefreshView.OnLoadListener() {
            @Override
            public void onRefresh() {
                mPresent.findAllSubDevice();
                mDBind.gatewaySubDevice.complete();
            }

            @Override
            public void onLoadMore() {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresent.getDeviceState();
    }


    @Subscribe          //订阅事件Event
    public  void onEventMainThread(EventRefreshScene event) {
        if (deviceName.equals(event.getDeviceName())){
            mPresent.getDeviceState();
            mPresent.findAllSubDevice();
        }
    }

    @Override
    public void onClick(DeviceInfoBean device) {
        mPresent.clickItem(device);
    }

    @Override
    public void refreshSubList(List<DeviceInfoBean> deviceList) {
        mChildAdapter.setList(deviceList);
        mDBind.gatewaySubAdd.setVisibility(View.VISIBLE);
    }

    @Override
    public void refreshNoList() {
        mChildAdapter.setList(null);
        mDBind.gatewaySubAdd.setVisibility(View.GONE);
        mDBind.gatewaySubDevice.setEmptyView(getString(R.string.ali_device_empty), R.drawable.device_empty_add);
    }

    @Override
    public void skipActivity(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void refreshState(String name, int state, String mode) {
        mDBind.title.setText(name);
        mDBind.titleSceneMode.setText(state == 1 ? getString(R.string.on_line) : getString(R.string.off_line));
        mDBind.modelColor.setBackgroundResource(state == 1 ? R.drawable.device_status_normal : R.drawable.device_status_off_line);
        mDBind.stateModel.setText(mode);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
