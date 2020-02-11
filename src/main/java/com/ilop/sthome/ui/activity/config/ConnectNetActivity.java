package com.ilop.sthome.ui.activity.config;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.aliyun.alink.business.devicecenter.api.add.AddDeviceBiz;
import com.aliyun.alink.business.devicecenter.api.add.DeviceInfo;
import com.aliyun.alink.business.devicecenter.api.add.IAddDeviceListener;
import com.aliyun.alink.business.devicecenter.api.add.ProvisionStatus;
import com.aliyun.alink.business.devicecenter.base.DCErrorCode;
import com.example.common.base.BaseActivity;
import com.ilop.sthome.data.enums.DevType;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivityConnectNetBinding;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 许格 on 2019/10/15.
 * 配置网关页面
 */
public class ConnectNetActivity extends BaseActivity<ActivityConnectNetBinding>{
    private final String TAG = ConnectNetActivity.class.getName();
    private Timer timer = null;
    private MyTask timerTask;
    private int count = 0;
    private final int SPEED1 = 8;
    private final int SPEED2 = 50;
    private final int SPEED3 = 20000;
    private int Now_speed;
    private String apSsid;
    private String apPassword;

    private int flag = -1;  //1代表绑定成功,2代表绑定失败，3代表已绑定其他设备,4代表回调绑定失败
    private String fail_msg = null;
    private DeviceInfo mDeviceBean;
    private boolean flag_ap_config;
    private boolean is_Config = true;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_connect_net;
    }

    @Override
    protected void initialize() {
        super.initialize();
        apSsid = getIntent().getStringExtra("ssid");
        apPassword = getIntent().getStringExtra("psw");
        flag_ap_config = getIntent().getBooleanExtra("ap",false);
        immersionStatusBar(true);
    }

    @Override
    protected void initView() {
        super.initView();
        mDBind.failReason.setText(getReasonSpan());
        mDBind.failReason.setMovementMethod(LinkMovementMethod.getInstance());
        mDBind.failReason.setVisibility(View.INVISIBLE);
        mDBind.changeConfig.setVisibility(View.INVISIBLE);
        mDBind.retry.setVisibility(View.INVISIBLE);
        mDBind.changeConfig.setVisibility(View.INVISIBLE);

        Now_speed = SPEED1;
        mDBind.tvTip.setText(getString(R.string.esptouch_is_configuring));

        timer = new Timer();
        timerTask = new MyTask();
        timer.schedule(timerTask,0,1);
    }

    @Override
    protected void initData() {
        super.initData();
        Log.i(TAG, "initData: "+ flag_ap_config);
        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.productKey = DevType.EE_GATEWAY.getProductkey(); // 商家后台注册的 productKey，不可为空
        deviceInfo.linkType = (flag_ap_config ? "ForceAliLinkTypePhoneAP" : "ForceAliLinkTypeNone"); // 默认一键配网
        AddDeviceBiz.getInstance().setDevice(deviceInfo);//设置待添加设备的基本信息
        AddDeviceBiz.getInstance().startAddDevice(this, new IAddDeviceListener() {
            @Override
            public void onPreCheck(boolean b, DCErrorCode dcErrorCode) {
                Log.i(TAG,"onPreCheck:"+b);
            }

            @Override
            public void onProvisionPrepare(int i) {
                Log.i(TAG,"onProvisionPrepare"+i);
                if (i == 1){
                    AddDeviceBiz.getInstance().toggleProvision(apSsid, apPassword, 30);
                }else {
                    Intent intent = new Intent(ConnectNetActivity.this, SetApActivity.class);
                    intent.putExtra("ssid", apSsid);
                    intent.putExtra("psw", apPassword);
                    intent.putExtra("ap",true);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onProvisioning() {
                Log.i(TAG,"onProvisioning()");
            }

            @Override
            public void onProvisionStatus(ProvisionStatus provisionStatus) {
                flag = 2;
                fail_msg = provisionStatus.message();
                Log.i(TAG,"onProvisionStatus:"+provisionStatus.message());
            }

            @Override
            public void onProvisionedResult(boolean b, DeviceInfo deviceInfo, DCErrorCode dcErrorCode) {
                Log.i(TAG,"onProvisionedResult:"+b);
                if(b){
                    flag = 1;
                    mDeviceBean = deviceInfo;
                    mDBind.tvTip.setText(String.format(getString(R.string.is_connected_to_wifi),deviceInfo.deviceName));
                }else {

                    flag = 2;
                    if(dcErrorCode!=null){
                        fail_msg = dcErrorCode.msg+"err code:"+dcErrorCode.code;
                    }
                }
            }
        });
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDBind.retry.setOnClickListener(v -> {
            if (is_Config){
                showToast(getString(R.string.is_config));
            }else {
                Intent intent = new Intent(ConnectNetActivity.this, ConfigGuideActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        mDBind.ivConnectBack.setOnClickListener(v -> {
            if (is_Config){
                showToast(getString(R.string.is_config));
            }else {
                finish();
            }
        });

        mDBind.changeConfig.setOnClickListener(view -> {
            if (flag_ap_config){
                Intent intent = new Intent(ConnectNetActivity.this, SetApActivity.class);
                intent.putExtra("ssid", apSsid);
                intent.putExtra("psw", apPassword);
                intent.putExtra("ap",true);
                startActivity(intent);
                finish();
            }else {
                Intent intent = new Intent(ConnectNetActivity.this, NetWorkActivity.class);
                intent.putExtra("ap",true);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }


    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    float progress = (int)msg.obj / 2000f;
                    String current = Math.round(progress) + "%";
                    Log.i(TAG, "handleMessage: "+ current);
                    mDBind.tvProgressNum.setText(current);
                    is_Config = true;
                    break;
                case 2:
                    mDBind.pvProgress.setVisibility(View.GONE);
                    mDBind.tvProgressNum.setVisibility(View.GONE);
                    mDBind.ivConnectError.setVisibility(View.VISIBLE);
                    Now_speed = SPEED1;
                    if(timer!=null){
                        timer.cancel();
                        timer = null;
                        count = 0;
                    }
                    mDBind.failReason.setVisibility(View.VISIBLE);
                    mDBind.changeConfig.setVisibility(View.VISIBLE);
                    mDBind.retry.setVisibility(View.VISIBLE);
                    mDBind.retry.setText(getString(R.string.retry));
                    is_Config = false;
                    switch (flag){
                        case -1:
                            mDBind.tvTip.setText(getString(R.string.failed_Esptouch_check_eq));
                            break;
                        case 2:
                            if(!TextUtils.isEmpty(fail_msg))
                                mDBind.tvTip.setText(fail_msg);
                            break;
                    }
                    break;
                case 3:
                    Log.i(TAG,"跳转到成功页面");
                    if(timer!=null){
                        timer.cancel();
                        timer = null;
                    }
                    is_Config = false;
                    mDBind.retry.setText(getString(R.string.ota_did_upgrade));
                    mDBind.retry.setEnabled(false);
                    handler.sendEmptyMessageDelayed(4, 1000);
                    break;
                case 4:
                    Bundle bundle = new Bundle();
                    bundle.putString("productKey", mDeviceBean.productKey);
                    bundle.putString("deviceName", mDeviceBean.deviceName);
                    skipAnotherActivity(bundle, BindAndUseActivity.class);
                    handler.removeMessages(4);
                    finish();
                    break;
            }
        }
    };


        class MyTask extends TimerTask{
        @Override
        public void run() {
            if(count>=200000){
               if(flag==1){
                   if(timer!=null){
                       timer.cancel();
                       timer = null;
                   }
                   handler.sendMessage(handler.obtainMessage(3));
               } else handler.sendMessage(handler.obtainMessage(2,flag));
            }else{
                count = count+Now_speed;
                Message message = new Message();
                message.what = 1;
                message.obj = count;
                handler.sendMessage(message);
                if(flag<=0){
                    Now_speed = SPEED1;
                }else if(flag==1){
                    Now_speed = SPEED2;
                }else {
                    Now_speed = SPEED3;
                }
            }
        }
    }


    private SpannableString getReasonSpan() {
        View.OnClickListener l = v -> startActivity(new Intent(ConnectNetActivity.this, QuestionActivity.class));
        SpannableString info = new SpannableString(getString(R.string.config_fail_reason));
        int end = info.length();
        info.setSpan(new Clickable(l), 0, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return info;
    }

    /**
     * 内部类，用于截获点击富文本后的事件
     */
    class Clickable extends ClickableSpan implements View.OnClickListener {
        private final View.OnClickListener mListener;

        public Clickable(View.OnClickListener mListener) {
            this.mListener = mListener;
        }

        @Override
        public void onClick(View v) {
            mListener.onClick(v);
        }
        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(getResources().getColor(R.color.main_color));
            ds.setUnderlineText(true);    //去除超链接的下划线
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK ) {
            if (is_Config){
                showToast(getString(R.string.is_config));
            }else {
                finish();
            }
            return true;
        } else {
            return super.dispatchKeyEvent(event);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDeviceBean = null;
        if(timer!=null){
            timer.cancel();
            timer = null;
        }
        AddDeviceBiz.getInstance().stopAddDevice();
    }
}
