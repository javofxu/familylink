package com.ilop.sthome.ui.activity.config;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.common.base.BasePActivity;
import com.example.common.utils.LiveDataBus;
import com.example.common.utils.MediaPlayerUtil;
import com.example.common.utils.RxTimerUtil;
import com.example.common.utils.SpUtil;
import com.ilop.sthome.data.device.Device;
import com.ilop.sthome.mvp.contract.BindContract;
import com.ilop.sthome.mvp.present.BindPresenter;
import com.ilop.sthome.ui.activity.main.MainActivity;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivityBindBinding;


/**
 * @author skygge
 * @date 2020-01-08.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：网关配网成功
 */
public class BindAndUseActivity extends BasePActivity<BindPresenter, ActivityBindBinding> implements BindContract.IView {
    private String productKey, deviceName;
    private String mGatewayName;
    private String mRoomName;
    private Device device;
    private MediaPlayerUtil mMediaPlayer;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bind;
    }

    @Override
    protected void initialize() {
        super.initialize();
        Bundle data = getIntent().getExtras();
        if (null != data) {
            productKey = data.getString("productKey");
            deviceName = data.getString("deviceName");
            Log.i(TAG, "initialize: "+productKey +"--"+deviceName);
        }
        mGatewayName = SpUtil.getString(mContext, "gateway");
        mRoomName = SpUtil.getString(mContext, "room");
    }

    @Override
    protected void initPresent() {
        super.initPresent();
        mPresent = new BindPresenter(mContext);
    }

    @Override
    protected void initView() {
        super.initView();
        device = new Device();
        mMediaPlayer = new MediaPlayerUtil(mContext);
    }

    @Override
    protected void initData() {
        super.initData();
        device.pk = productKey;
        device.dn = deviceName;
        mPresent.queryProductInfo(device);

        mDBind.tvTick.setChecked(true);
        mMediaPlayer.play("prompt");

        mPresent.bindDevice(device, mRoomName, mGatewayName);
        showProgressDialog();
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDBind.tvToHow.setOnClickListener(view -> skipAnotherActivity(RecoveryGuideActivity.class));
        mDBind.ivBindBack.setOnClickListener(v -> finish());
    }

    @Override
    public void bindSuccess() {
        dismissProgressDialog();
        LiveDataBus.get().with("refresh").setValue(0);
        showToast(getString(R.string.success_add));
        Intent intent = new Intent(mContext, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    @Override
    public void alreadyBind() {
        dismissProgressDialog();
        mDBind.showBeenBind.setVisibility(View.VISIBLE);
    }

    @Override
    public void showToastMsg(String msg) {
        dismissProgressDialog();
        showToast(msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMediaPlayer.destroy();
    }
}
