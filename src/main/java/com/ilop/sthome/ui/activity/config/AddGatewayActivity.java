package com.ilop.sthome.ui.activity.config;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import com.aliyun.alink.business.devicecenter.api.add.DeviceInfo;
import com.aliyun.alink.business.devicecenter.api.discovery.DiscoveryType;
import com.aliyun.alink.business.devicecenter.api.discovery.LocalDeviceMgr;
import com.example.common.base.BaseActivity;
import com.ilop.sthome.data.device.FoundDeviceListItem;
import com.ilop.sthome.data.device.SupportDeviceListItem;
import com.ilop.sthome.ui.adapter.device.AddDeviceAdapter;
import com.ilop.sthome.utils.adddevice.DeviceAddHandler;
import com.ilop.sthome.utils.adddevice.OnDeviceAddListener;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivityAddDeviceBinding;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class AddGatewayActivity extends BaseActivity<ActivityAddDeviceBinding> implements OnDeviceAddListener, SwipeRefreshLayout.OnRefreshListener {

    private DeviceAddHandler mDeviceAddHandler;
    private AddDeviceAdapter mAddDeviceAdapter;
    private boolean isRefresh = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_device;
    }

    @Override
    protected void initView() {
        super.initView();
        mDeviceAddHandler = new DeviceAddHandler(this);
        mAddDeviceAdapter = new AddDeviceAdapter(this);
        mDeviceAddHandler.getSupportDeviceListFromSever();
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(mContext, R.anim.layout_animation_fall_down);
        mDBind.rvAddDevice.setLayoutManager(new LinearLayoutManager(this));
        mDBind.rvAddDevice.setLayoutAnimation(animation);
        mDBind.rvAddDevice.setAdapter(mAddDeviceAdapter);
        mDBind.srlAddDevice.setDistanceToTriggerSync(600);
        mDBind.srlAddDevice.setOnRefreshListener(this);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDBind.ivAddDeviceBack.setOnClickListener(v -> finish());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && null != data) {
            if (data.getStringExtra("productKey") != null) {
                String productKey = data.getStringExtra("productKey");
                String deviceName = data.getStringExtra("deviceName");
                Bundle bundle = new Bundle();
                bundle.putString("productKey", productKey);
                bundle.putString("deviceName", deviceName);
                skipAnotherActivity(bundle, BindAndUseActivity.class);
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        // 获取之前发现的所有设备
        getAllGatewayDevice();
    }

    private void getAllGatewayDevice(){
        mAddDeviceAdapter.resetLocalDevice();
        mDeviceAddHandler.reset();
        EnumSet<DiscoveryType> discoveryTypeEnumSet = EnumSet.allOf(DiscoveryType.class);
        LocalDeviceMgr.getInstance().startDiscovery(this, discoveryTypeEnumSet, null, (discoveryType, list) -> {
            Log.d("TAG", list.toString());
            List<FoundDeviceListItem> foundDeviceListItems = new ArrayList<>();
            for (DeviceInfo deviceInfo : list) {
                final FoundDeviceListItem deviceListItem = new FoundDeviceListItem();
                if (discoveryType == DiscoveryType.LOCAL_ONLINE_DEVICE) {
                    deviceListItem.deviceStatus = FoundDeviceListItem.NEED_BIND;
                } else if (discoveryType == DiscoveryType.CLOUD_ENROLLEE_DEVICE) {
                    deviceListItem.deviceStatus = FoundDeviceListItem.NEED_CONNECT;
                }
                deviceListItem.discoveryType = discoveryType;
                deviceListItem.deviceInfo = deviceInfo;
                deviceListItem.deviceName = deviceInfo.deviceName;
                deviceListItem.productKey = deviceInfo.productKey;
                foundDeviceListItems.add(deviceListItem);
            }
            mDeviceAddHandler.filterDevice(foundDeviceListItems, AddGatewayActivity.this);
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalDeviceMgr.getInstance().stopDiscovery();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDeviceAddHandler.onDestory();
    }


    @Override
    public void showToast(String message) {
        if (!TextUtils.isEmpty(message)) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSupportDeviceSuccess(List<SupportDeviceListItem> mSupportDeviceListItems) {
        Log.e(TAG, "onSupportDeviceSuccess: " + mSupportDeviceListItems);
        mAddDeviceAdapter.setSupportDevces(mSupportDeviceListItems);
        dismissProgressDialog();
    }

    @Override
    public void onFilterComplete(List<FoundDeviceListItem> foundDeviceListItems) {
        mAddDeviceAdapter.addLocalDevice(foundDeviceListItems);
        dismissProgressDialog();
    }

    @Override
    public void onRefresh() {
        if (isRefresh){
            return;
        }else {
            isRefresh = true;
            getAllGatewayDevice();
            mDBind.srlAddDevice.setRefreshing(false);
            isRefresh = false;
            showProgressDialog();
        }
    }
}
