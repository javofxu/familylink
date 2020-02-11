package com.ilop.sthome.ui.activity.config;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;

import com.example.common.base.BaseActivity;
import com.ilop.sthome.ui.dialog.TipDialog;
import com.ilop.sthome.utils.PermissionUtil;
import com.ilop.sthome.utils.tools.UnitTools;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivityConfigGuideBinding;

/**
 * @author skygge
 * @date 2019-10-10.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：配网引导页
 */
public class ConfigGuideActivity extends BaseActivity<ActivityConfigGuideBinding>{
    private AnimationDrawable mAnimation;
    private static final int REQUEST_LOCATION=1001;
    private static final int REQUEST_LOCATION_SERVICE=1002;
    private String[] permission = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};


    @Override
    protected int getLayoutId() {
        return R.layout.activity_config_guide;
    }


    @Override
    protected void initView() {
        super.initView();
        mDBind.ivGatewayShow.setBackgroundResource(R.drawable.config_tishi);
        mAnimation = (AnimationDrawable) mDBind.ivGatewayShow.getBackground();
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDBind.ivBeforeBack.setOnClickListener(v -> finish());
        mDBind.ivGatewayShow.post(() -> mAnimation.run());
        mDBind.btConfigNext.setOnClickListener(v -> {
            if(Build.VERSION.SDK_INT >= 28){
                if(PermissionUtil.requestPermission(this,permission,REQUEST_LOCATION)){
                    if(UnitTools.isLocServiceEnable(this)){
                        skipAnotherActivity(NetWorkActivity.class);
                    }else {
                        TipDialog mDialog = new TipDialog(mContext, () -> {
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivityForResult(intent, REQUEST_LOCATION_SERVICE);
                        });
                        mDialog.setMsg(getString(R.string.permission_reject_location_service_tip));
                        mDialog.setConfirmMsg(getString(R.string.goto_set));
                        mDialog.show();
                    }
                }
            }else {
                skipAnotherActivity(NetWorkActivity.class);
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION) {
            if (permissions.length == grantResults.length) {
                for (int i = 0; i < permissions.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        if(UnitTools.isLocServiceEnable(this)){
                            skipAnotherActivity(NetWorkActivity.class);
                            finish();
                        }else {
                            TipDialog mDialog = new TipDialog(mContext, () -> {
                                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivityForResult(intent, REQUEST_LOCATION_SERVICE);
                            });
                            mDialog.setMsg(getString(R.string.permission_reject_location_service_tip));
                            mDialog.setConfirmMsg(getString(R.string.goto_set));
                            mDialog.show();
                        }
                    } else {
                        TipDialog mDialog = new TipDialog(mContext, () -> PermissionUtil.startToSetting(this));
                        mDialog.setMsg(getString(R.string.permission_reject_location_tip));
                        mDialog.setConfirmMsg(getString(R.string.goto_set));
                        mDialog.show();
                    }
                }
            }
        }
    }
}
