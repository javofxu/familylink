package com.ilop.sthome.ui.activity.main;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.aliyun.iot.aep.oa.OALanguageHelper;
import com.aliyun.iot.aep.sdk.login.ILoginCallback;
import com.aliyun.iot.aep.sdk.login.LoginBusiness;
import com.example.common.base.BaseActivity;
import com.example.common.utils.LiveDataBus;
import com.example.common.utils.RxTimerUtil;
import com.ilop.sthome.common.ProtocolFilter;
import com.ilop.sthome.ui.dialog.TipDialog;
import com.ilop.sthome.utils.PermissionUtil;
import com.siterwell.familywellplus.R;

import java.util.Locale;


public class StartActivity extends BaseActivity {

    private Handler mH = new Handler();
    private static final int REQUEST_PERMISSION_SERVICE=1005;
    private String[] permission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA,
            Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION};

    private TipDialog mDialog;
    private TipDialog mTipDialog;

    @Override
    protected int getLayoutId() {
        setTheme(R.style.AppTheme);
        return R.layout.activity_start;
    }

    @Override
    protected void initView() {
        super.initView();
        Resources resource = getResources();
        Locale locale = resource.getConfiguration().locale;//获得local对象
        String lan = locale.getLanguage();
        if("zh".equals(lan)){
            OALanguageHelper.setLanguageCode(Locale.SIMPLIFIED_CHINESE);
        }else if("en".equals(lan)){
            OALanguageHelper.setLanguageCode(Locale.US);
        }else if("fr".equals(lan)){
            OALanguageHelper.setLanguageCode(Locale.FRANCE);
        }else if("de".equals(lan)){
            OALanguageHelper.setLanguageCode(Locale.GERMANY);
        }else if("es".equals(lan)){
            OALanguageHelper.setLanguageCode(new Locale("es","ES"));
        }else {
            OALanguageHelper.setLanguageCode(Locale.US);
        }
    }

    @Override
    protected void initData() {
        super.initData();

        SharedPreferences setting = getSharedPreferences("isFirst", 0);
        boolean user_first = setting.getBoolean("FIRST",true);
        if(user_first){//第一次
            setting.edit().putBoolean("FIRST", false).apply();
            ProtocolFilter mFilter = new ProtocolFilter();
            mFilter.setContext(StartActivity.this);
        }else{
            mH.postDelayed(this::doNext, 2000);
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        LiveDataBus.get().with("close_dialog", Integer.class).observe(this, integer -> {
            PermissionUtil.requestPermission(this, permission, REQUEST_PERMISSION_SERVICE);
        });
    }

    
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_SERVICE) {
            if (permissions.length == grantResults.length) {
                for (int i = 0; i < permissions.length; i++) {
                    if(permissions[i].equals(android.Manifest.permission.READ_PHONE_STATE)){
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            if(mDialog == null || !mDialog.isShowing()){
                                mDialog = new TipDialog(mContext, () -> {
                                    PermissionUtil.startToSetting(StartActivity.this);
                                    mDialog = null;
                                });
                                mDialog.setMsg(getString(R.string.permission_reject_imei_tip));
                                mDialog.setConfirmMsg(getString(R.string.goto_set));
                                mDialog.setCancel(()-> mDialog = null);
                                mDialog.show();
                            }
                        }else {
                            if(mDialog!=null && mDialog.isShowing()){
                                mDialog.dismiss();
                                mDialog = null;
                            }
                        }
                    }else if(permissions[i].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            if(mTipDialog == null || !mTipDialog.isShowing()){
                                mTipDialog = new TipDialog(mContext, () -> {
                                    PermissionUtil.startToSetting(StartActivity.this);
                                    mTipDialog = null;
                                });
                                mTipDialog.setMsg(getString(R.string.permission_reject_write_local_tip));
                                mTipDialog.setConfirmMsg(getString(R.string.goto_set));
                                mTipDialog.setCancel(()-> mTipDialog = null);
                                mTipDialog.show();
                            }
                        }else {
                            if(mTipDialog!=null && mTipDialog.isShowing()){
                                mTipDialog.dismiss();
                                mTipDialog = null;
                            }
                        }
                    }
                }
            }
            RxTimerUtil.interval(1000, number -> {
                if (mTipDialog == null && mDialog == null){
                    doNext();
                }
            });
        }
    }


    private void doNext(){
        if (LoginBusiness.isLogin()) {
            startActivity(new Intent(StartActivity.this, MainActivity.class));
            finish();
        } else {
            LoginBusiness.login(new ILoginCallback() {
                @Override
                public void onLoginSuccess() {
                    mH.postDelayed(() -> startActivity(new Intent(StartActivity.this, MainActivity.class)), 0);
                }

                @Override
                public void onLoginFailed(int i, String s) {
                    showToast(getString(R.string.EE_DEV_NOT_LOGIN)+" : "+ s);
                }
            });
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxTimerUtil.cancel();
    }
}
