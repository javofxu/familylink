package com.ilop.sthome.ui.activity.device;

import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;

import com.example.common.base.BasePActivity;
import com.example.xmpic.support.utils.UIFactory;
import com.ilop.sthome.data.event.EventAnswerOK;
import com.ilop.sthome.mvp.contract.DeviceSetContract;
import com.ilop.sthome.mvp.present.DeviceSetPresenter;
import com.ilop.sthome.network.api.SendCommandAli;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivityDeviceSettingBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * @author skygge
 * @date 2020-01-10.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：网关/设备设置
 */

public class DeviceSettingActivity extends BasePActivity<DeviceSetPresenter, ActivityDeviceSettingBinding> implements DeviceSetContract.IView, View.OnClickListener{

    private int mDeviceId;
    private String deviceName;
    private Bitmap mQrCodeBmp = null;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_device_setting;
    }

    @Override
    protected void initialize() {
        super.initialize();
        EventBus.getDefault().register(this);
        mDeviceId = getIntent().getIntExtra("deviceId",0);
        deviceName = getIntent().getStringExtra("deviceName");
    }

    @Override
    protected void initPresent() {
        super.initPresent();
        mPresent = new DeviceSetPresenter(mContext, deviceName, mDeviceId);
    }

    @Override
    protected void initView() {
        super.initView();
        mDBind.tvGatewaySetTitle.setText(mDeviceId==0 ? getString(R.string.ali_gateway_setting) : getString(R.string.ali_subdevice_setting));
        mDBind.deviceUnbind.setText(mDeviceId==0 ? getString(R.string.ali_gateway_unbind) : getString(R.string.delete_equipment));
    }

    @Override
    protected void initData() {
        super.initData();
        mPresent.onRefreshView();
        mPresent.showViewByDeviceId(mDeviceId);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDBind.replaceeqid.setOnClickListener(this);
        mDBind.name.setOnClickListener(this);
        mDBind.ota.setOnClickListener(this);
        mDBind.share.setOnClickListener(this);
        mDBind.share.setOnClickListener(this);
        mDBind.ins.setOnClickListener(this);
        mDBind.deviceUnbind.setOnClickListener(this);
        mDBind.ivGatewayBack.setOnClickListener(view -> finish());
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.name:
                mPresent.setDeviceName(mDeviceId);
                break;
            case R.id.ota:
                mPresent.onRouterToOTA();
                break;
            case R.id.share:
                break;
            case R.id.location:
                break;
            case R.id.ins:
                mPresent.onDownloadIns();
                break;
            case R.id.device_unbind:
                mPresent.onUnBindDevice();
                break;
            case R.id.replaceeqid:
                mPresent.onReplaceDevice();
                break;
        }
    }


    @Subscribe
    public  void onEventMainThread(EventAnswerOK event) {
        Log.i(TAG, "onEventMainThread: "+ event.getData_str1()+" -- " +event.getData_str2());
        if (event.getData_str1().length() == 9) {
            int cmd = Integer.parseInt(event.getData_str1().substring(0, 4), 16);
            if (cmd == SendCommandAli.MODIFY_EQUIPMENT_NAME) {
                if ("OK".equals(event.getData_str2())) {
                    mPresent.onModifySuccess();
                }
            }else if(cmd== SendCommandAli.DELETE_EQUIPMENT){
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
    public void isGatewayView() {
        mDBind.replaceeqid.setVisibility(View.GONE);
        mDBind.imgDeviceQRCode.setVisibility(mDeviceId == 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void isSubDeviceView() {
        mDBind.location.setVisibility(View.GONE);
        mDBind.ota.setVisibility(View.GONE);
        mDBind.share.setVisibility(View.GONE);
        mDBind.imgDeviceQRCode.setVisibility(View.GONE);
    }

    @Override
    public void showDeviceName(String name) {
        mDBind.name.setDetailText(name);
    }

    @Override
    public void startActivityByIntent(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void showToastMsg(String msg) {
        showToast(msg);
    }

    @Override
    public void hideSoftBoard() {
        hideSoftKeyboard();
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
