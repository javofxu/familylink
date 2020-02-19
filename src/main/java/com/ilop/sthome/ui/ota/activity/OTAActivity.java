package com.ilop.sthome.ui.ota.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aliyun.iot.aep.sdk.framework.AActivity;
import com.aliyun.iot.aep.sdk.log.ALog;
import com.example.common.utils.StatusBar.StatusBarUtil;
import com.ilop.sthome.ui.ota.OTAConstants;
import com.ilop.sthome.ui.ota.bean.OTADeviceSimpleInfo;
import com.ilop.sthome.ui.ota.handler.OTAActivityHandler;
import com.ilop.sthome.ui.ota.interfaces.IOTAActivity;
import com.ilop.sthome.ui.ota.view.MineOTAButton;
import com.ilop.sthome.ui.ota.view.MineOTAButton.OnOTAButtonClickListener;
import com.ilop.sthome.ui.ota.view.MineOTADialog;
import com.ilop.sthome.ui.ota.view.MineOTADialog.OnDialogButtonClickListener;
import com.ilop.sthome.ui.ota.view.SimpleTopbar;
import com.ilop.sthome.ui.ota.view.SimpleTopbar.onBackClickListener;
import com.siterwell.familywellplus.R;

public class OTAActivity extends AActivity implements IOTAActivity, OnOTAButtonClickListener, onBackClickListener,
        OnDialogButtonClickListener, OnClickListener {
    private static final String TAG = "OTAActivity";

    private OTADeviceSimpleInfo mSimpleInfo;

    private MineOTAButton mButton;
    private ImageView mUpArrowImageView;
    private TextView mTipsTextView, mCurrentVersionTextView,new_firewareTextView;
    private SimpleTopbar mTopbar;
    private TextView mRefreshTextView;
    private RelativeLayout mWrapper;

    private OTAActivityHandler mHandler;
    private MineOTADialog mRetryDialog;
    private String lastver;
    private boolean update;

    /* =====================lifecycle @start =====================*/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ilop_ota_activity);
        immersionStatusBar(true);
        initView();
        initEvent();
        initData();
        initHandler();
    }

    protected void immersionStatusBar(boolean isDark){

        //当FitsSystemWindows设置 true 时，会在屏幕最上方预留出状态栏高度的 padding
        StatusBarUtil.setRootViewFitsSystemWindows(this,true);
        //设置状态栏透明
        StatusBarUtil.setTranslucentStatus(this);
        //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
        //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
        if (!StatusBarUtil.setStatusBarDarkTheme(this, isDark)) {
            //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
            //这样半透明+白=灰, 状态栏的文字能看得清
            StatusBarUtil.setStatusBarColor(this,0x55000000);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (null == mSimpleInfo) {
            ALog.e(TAG, "onResume() OTADeviceSimpleInfo is null!");
            return;
        }

        if (null != mHandler) {
            mHandler.refreshData(mSimpleInfo);
        }
    }

    @Override
    protected void onDestroy() {
        if (null != mHandler) {
            mHandler.destroy();
        }

        if (null != mRetryDialog) {
            mRetryDialog.cancel();
            mRetryDialog = null;
        }
        super.onDestroy();
    }

    /* =====================lifecycle @end =====================*/

    protected void initView() {
        mTopbar = findViewById(R.id.mine_top_bar);
        mWrapper = findViewById(R.id.mine_setting_ota_detail_wrapper_linearlayout);
        mUpArrowImageView = findViewById(R.id.mine_setting_ota_detail_up_arrow);
        mTipsTextView = findViewById(R.id.mine_setting_ota_detail_tips);
        mCurrentVersionTextView = findViewById(R.id.mine_setting_ota_detail_current_version);
        mRefreshTextView = findViewById(R.id.mine_setting_ota_detail_refresh);
        mButton = findViewById(R.id.mine_setting_ota_detail_button);
        new_firewareTextView = findViewById(R.id.text_tv_one);
    }

    protected void initEvent() {
        if (null != mTopbar) {
            mTopbar.setOnBackClickListener(this);
        }

        if (null != mButton) {
            mButton.setOnOTAButtonClickListener(this);
        }

        if (null != mRefreshTextView) {
            mRefreshTextView.setActivated(true);
            mRefreshTextView.setOnClickListener(this);
        }
    }

    protected void initData() {
        try {
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            if (bundle!=null){
                mSimpleInfo = bundle.getParcelable(OTAConstants.OTA_KEY_DEVICESIMPLE_INFO);
            }
        } catch (Exception e) {
            mSimpleInfo = null;
            e.printStackTrace();
        }

        setTitle();
    }

    protected void initHandler() {
        mHandler = new OTAActivityHandler(this);
    }

    /* =====================implement IOTAActivity @start =====================*/

    @Override
    public void setTitle() {
        if (isFinishing()) {
            return;
        }

        if (null != mTopbar && null != mSimpleInfo) {
            mTopbar.setTitle(getString(R.string.ota));
        }
    }

    @Override
    public void showLoading() {
        if (isFinishing()) {
            return;
        }

        if (null != mWrapper) {
            mWrapper.setVisibility(View.GONE);
        }
    }

    @Override
    public void showLoaded(String msg) {
        if (isFinishing()) {
            return;
        }

        if (null != mWrapper) {
            mWrapper.setVisibility(View.VISIBLE);
        }

        if (!TextUtils.isEmpty(msg)) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showLoadError() {
        showDeviceInfoError();
    }

    @Override
    public void showUpgradeFailureDialog() {
        if (isFinishing()) {
            return;
        }

        if (null == mRetryDialog) {
            mRetryDialog = new MineOTADialog(this);
            mRetryDialog.setNegativeButtonText(getString(R.string.cancel));
            mRetryDialog.setPositiveButtonText(getString(R.string.ok));
            mRetryDialog.setTitle(getString(R.string.ota_did_upgrade_failure));
            mRetryDialog.setCancelable(false);
            mRetryDialog.setOnDialogButtonClickListener(this);
        }

        if (!mRetryDialog.isShowing()) {
            mRetryDialog.show();
        }
    }

    @Override
    public void showTips(String latestVersion) {
        if (isFinishing()) {
            return;
        }

        if (null != mTipsTextView) {
            mTipsTextView.setText(latestVersion);
        }
        lastver = latestVersion;
        if(null != new_firewareTextView){
            new_firewareTextView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showNoNetToast() {
        if (isFinishing()) {
            return;
        }
        Toast.makeText(this, getString(R.string.ota_network_error), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showCurrentVersion(String currentVersion) {
        if (isFinishing()) {
            return;
        }

        if (null != mCurrentVersionTextView) {
            String currentVersionTips = getString(R.string.ota_current_version) + " " + currentVersion;
            mCurrentVersionTextView.setVisibility(View.VISIBLE);
            mCurrentVersionTextView.setText(currentVersionTips);
        }
    }

    @Override
    public void showCurrentVersionNoNeedToupdate(String currentVersion) {
        if (isFinishing()) {
            return;
        }

        if (null != mTipsTextView) {
            mTipsTextView.setText(getResources().getString(R.string.ota_current_is_the_lasted_version));
        }

        if(null != new_firewareTextView){
            new_firewareTextView.setVisibility(View.GONE);
        }

        if (null != mCurrentVersionTextView) {
            String currentVersionTips = getString(R.string.ota_current_version) + " " + currentVersion;
            mCurrentVersionTextView.setVisibility(View.VISIBLE);
            mCurrentVersionTextView.setText(currentVersionTips);
        }

        if (null != mRefreshTextView) {
            mRefreshTextView.setText(getString(R.string.ota_refresh));
            mRefreshTextView.setVisibility(View.VISIBLE);
        }

        if (null != mButton) {
            mButton.setVisibility(View.GONE);
        }

    }

    @Override
    public void showUpgradeStatus(int status) {
        if (isFinishing()) {
            return;
        }

        if (null == mButton) {
            return;
        }

        if (null == mRefreshTextView) {
            return;
        }



        if (OTAConstants.OTA_STATUS_PRE_LOAD == status || OTAConstants.OTA_STATUS_LOADING == status) {
            mRefreshTextView.setVisibility(View.GONE);
            mButton.setVisibility(View.VISIBLE);
            mButton.setStatus(status);
        } else if (OTAConstants.OTA_STATUS_DONE == status) {

            if(update){
                if (null != mTipsTextView) {
                    mTipsTextView.setText(getString(R.string.ota_upgrade_success));
                }

                mRefreshTextView.setVisibility(View.GONE);
                mButton.setVisibility(View.VISIBLE);
                mButton.setStatus(status);
                if(null != new_firewareTextView){
                    new_firewareTextView.setVisibility(View.GONE);
                }

                if(!TextUtils.isEmpty(lastver)){
                    if (null != mCurrentVersionTextView) {
                        String currentVersionTips = getString(R.string.ota_current_version) + " " + lastver;
                        mCurrentVersionTextView.setVisibility(View.VISIBLE);
                        mCurrentVersionTextView.setText(currentVersionTips);
                    }
                    lastver = null;
                }
            }else {
                if (null != mTipsTextView) {
                    mTipsTextView.setText(getResources().getString(R.string.ota_current_is_the_lasted_version));
                }

                if(null != new_firewareTextView){
                    new_firewareTextView.setVisibility(View.GONE);
                }


                if (null != mRefreshTextView) {
                    mRefreshTextView.setText(getString(R.string.ota_refresh));
                    mRefreshTextView.setVisibility(View.VISIBLE);
                }

                if (null != mButton) {
                    mButton.setVisibility(View.GONE);
                }
            }



        } else if (OTAConstants.OTA_STATUS_FAILURE == status) {
            mRefreshTextView.setVisibility(View.GONE);
            //升级失败变为preload 等待触发重新升级
            mButton.setVisibility(View.VISIBLE);
            mButton.setStatus(OTAConstants.OTA_STATUS_PRE_LOAD);
            showUpgradeFailureDialog();
        } else if (OTAConstants.OTA_STATUS_ERROR == status) {
            showDeviceNoResponseError();
        }
    }

    /* =====================implement IOTAActivity @end =====================*/

    /* =====================OnOTAButtonClickListener @start =====================*/

    @Override
    public void onUpgradeClick() {
        if (isFinishing()) {
            return;
        }
        update = true;
        mHandler.requestUpdate();
    }

    @Override
    public void onFinishClick() {
        if (isFinishing()) {
            return;
        }

        this.finish();
    }

    /* =====================OnOTAButtonClickListener @end =====================*/

    /* =====================onBackClickListener @start =====================*/

    @Override
    public void onBackClick() {
        if (isFinishing()) {
            return;
        }

        this.finish();
    }
    /* =====================onBackClickListener @end =====================*/

    //=====================OnDialogButtonClickListener @start=====================

    @Override
    public void onNegativeClick(MineOTADialog dialog) {
        if (isFinishing()) {
            return;
        }

        dialog.dismiss();
        OTAActivity.this.finish();
    }

    @Override
    public void onPositiveClick(MineOTADialog dialog) {
        if (isFinishing()) {
            return;
        }

        dialog.dismiss();
    }

    //=====================OnDialogButtonClickListener @end=====================

    //=====================OnClickListener @start=====================

    @Override
    public void onClick(View v) {
        if (isFinishing()) {
            return;
        }

        if (R.id.mine_setting_ota_detail_refresh == v.getId()) {
            if (null != mHandler && null != mSimpleInfo) {
                mHandler.refreshData(mSimpleInfo);
            }
        }
    }

    //=====================OnClickListener @end=====================

    //-----------------private method @start-------------------

    private void showDeviceInfoError() {
        if (isFinishing()) {
            return;
        }

        if (null != mWrapper) {
            mWrapper.setVisibility(View.VISIBLE);
        }

        if (null != mUpArrowImageView) {
            mUpArrowImageView.setImageResource(R.mipmap.fail);
        }

        if (null != mTipsTextView) {
            mTipsTextView.setText(getString(R.string.ota_get_info_error));
        }

        if (null != mCurrentVersionTextView) {
            mCurrentVersionTextView.setVisibility(View.GONE);
        }

        if (null != mRefreshTextView) {
            mRefreshTextView.setText(getString(R.string.ota_refresh));
            mRefreshTextView.setVisibility(View.VISIBLE);
        }

        if (null != mButton) {
            mButton.setVisibility(View.GONE);
        }
    }

    private void showDeviceNoResponseError() {
        if (isFinishing()) {
            return;
        }

        if (null != mWrapper) {
            mWrapper.setVisibility(View.VISIBLE);
        }

        if (null != mUpArrowImageView) {
            mUpArrowImageView.setImageResource(R.mipmap.fail);
        }

        if (null != mTipsTextView) {
            mTipsTextView.setText(getString(R.string.ota_did_upgrade_error));
        }

        if (null != mCurrentVersionTextView) {
            mCurrentVersionTextView.setVisibility(View.GONE);
        }

        if (null != mRefreshTextView) {
            mRefreshTextView.setText(getString(R.string.retry));
            mRefreshTextView.setVisibility(View.VISIBLE);
        }

        if (null != mButton) {
            mButton.setVisibility(View.GONE);
        }
    }

    //-----------------private method @end-------------------
}
