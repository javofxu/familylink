package com.ilop.sthome.ui.activity.device;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.example.common.base.BasePActivity;
import com.example.common.utils.LiveDataBus;
import com.example.xmpic.support.utils.UIFactory;
import com.ilop.sthome.data.bean.AlarmNotice;
import com.ilop.sthome.data.event.EventAnswerOK;
import com.ilop.sthome.mvp.contract.DeviceSetContract;
import com.ilop.sthome.mvp.present.DeviceSetPresenter;
import com.ilop.sthome.network.api.SendCommandAli;
import com.ilop.sthome.ui.adapter.main.SetUpAdapter;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivityDeviceSettingBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * @author skygge
 * @date 2020-01-10.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：网关/设备设置
 */

public class DeviceSettingActivity extends BasePActivity<DeviceSetPresenter, ActivityDeviceSettingBinding> implements DeviceSetContract.IView{

    private int mDeviceId;
    private boolean mEnabled;
    private String mDeviceName;
    private Bitmap mQrCodeBmp = null;
    private SetUpAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_device_setting;
    }

    @Override
    protected void initialize() {
        super.initialize();
        EventBus.getDefault().register(this);
        mDeviceId = getIntent().getIntExtra("deviceId",0);
        mDeviceName = getIntent().getStringExtra("deviceName");
    }

    @Override
    protected void initPresent() {
        super.initPresent();
        mPresent = new DeviceSetPresenter(mContext, mDeviceName, mDeviceId);
    }

    @Override
    protected void initView() {
        super.initView();
        mDBind.tvGatewaySetTitle.setText(mDeviceId==0 ? getString(R.string.ali_gateway_setting) : getString(R.string.ali_subdevice_setting));
        mDBind.deviceUnbind.setText(mDeviceId==0 ? getString(R.string.ali_gateway_unbind) : getString(R.string.delete_equipment));
        mAdapter = new SetUpAdapter(mContext);
        mDBind.rvNoticeList.setLayoutManager(new LinearLayoutManager(mContext));
        mDBind.rvNoticeList.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresent.onRefreshView();
        mPresent.showViewByDeviceId(mDeviceId);
        if (mDeviceId == 0) mPresent.getDeviceNoticeList();
        LiveDataBus.get().with("alarm_notice", AlarmNotice.class).observe(this, alarmNotice -> {
            assert alarmNotice != null;
            mPresent.setDeviceNoticeEnabled(alarmNotice.getEventId(), !alarmNotice.isNoticeEnabled());
            showProgressDialog();
        });
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDBind.replaceDevice.setOnClickListener(view -> mPresent.onReplaceDevice());
        mDBind.name.setOnClickListener(view -> mPresent.setDeviceName());
        mDBind.share.setOnClickListener(view -> skipAnotherActivity(DeviceShareActivity.class));
        mDBind.ota.setOnClickListener(view -> mPresent.onRouterToOTA());
        mDBind.ins.setOnClickListener(view -> mPresent.onDownloadIns());
        mDBind.deviceUnbind.setOnClickListener(view -> mPresent.onUnBindDevice());
        mDBind.setUpAll.setOnClickListener(view -> {
            mPresent.setDeviceFullNoticeEnabled(!mEnabled);
            showProgressDialog();
        });
        mDBind.ivGatewayBack.setOnClickListener(view -> finish());
    }


    @Subscribe
    public  void onEventMainThread(EventAnswerOK event) {
        if (event.getData_str1().length() == 9) {
            int cmd = Integer.parseInt(event.getData_str1().substring(0, 4), 16);
            if(cmd== SendCommandAli.DELETE_EQUIPMENT){
                if("OK".equals(event.getData_str2())){
                    mPresent.onDeleteSuccess();
                    finish();
                }else {
                    showToast(getString(R.string.failed));
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresent.onRefreshView();
    }

    @Override
    public void isGatewayView() {
        mDBind.replaceDevice.setVisibility(View.GONE);
    }

    @Override
    public void isSubDeviceView() {
        mDBind.ota.setVisibility(View.GONE);
        mDBind.share.setVisibility(View.GONE);
        mDBind.imgDeviceQRCode.setVisibility(View.GONE);
        mDBind.rlGatewaySet.setVisibility(View.GONE);
        mDBind.rvNoticeList.setVisibility(View.GONE);
    }

    @Override
    public void showDeviceName(String name) {
        mDBind.name.setDetailText(name);
    }

    @Override
    public void showNoticeList(List<AlarmNotice> noticeList) {
        mAdapter.setList(noticeList);
    }

    @Override
    public void withoutNotice() {
        mAdapter.setList(null);
    }

    @Override
    public void showHasEnabledOpen(boolean open) {
        mEnabled = open;
        mDBind.setUpAll.setImageResource(open ? R.mipmap.btn_on_48 : R.mipmap.btn_off_48);
    }

    @Override
    public void startActivityByIntent(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void showToastMsg(String msg) {
        dismissProgressDialog();
        showToast(msg);
    }

    @Override
    public void showQRCode(String qrCode) {// 生成二维码
        Bitmap qrCodeBmp = UIFactory.createCode(qrCode, 600, 0xff202020);
        if ( null != qrCodeBmp ) {
            if ( null != mQrCodeBmp ) {
                mQrCodeBmp.recycle();
            }
            mQrCodeBmp = qrCodeBmp;
            mDBind.imgDeviceQRCode.setImageBitmap(qrCodeBmp);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
