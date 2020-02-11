package com.ilop.sthome.ui.activity.config;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.text.TextUtils;

import com.example.common.base.BaseActivity;
import com.ilop.sthome.ui.dialog.TipDialog;
import com.ilop.sthome.utils.EspWifiAdminUtil;
import com.ilop.sthome.utils.PermissionUtil;
import com.ilop.sthome.utils.tools.UnitTools;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivityNetWorkBinding;

/**
 * 配置wifi
 */
public class NetWorkActivity extends BaseActivity<ActivityNetWorkBinding>{

	private EspWifiAdminUtil mWifiAdmin;
	private boolean flag_ap;
	private Handler mHandler = new Handler();

	@Override
	protected int getLayoutId() {
		return R.layout.activity_net_work;
	}

	@Override
	protected void initialize() {
		super.initialize();
		immersionStatusBar(true);
		flag_ap = getIntent().getBooleanExtra("ap",false);
	}

	@Override
	protected void initView() {
		super.initView();
		mWifiAdmin = new EspWifiAdminUtil(this);
	}

	@Override
	protected void initListener() {
		super.initListener();
		mDBind.ivConfigBack.setOnClickListener(v -> finish());
		mDBind.btnConfirm.setOnClickListener(v -> {
			hideSoftKeyboard();
			String apSsid = mDBind.tvApSssidConnected.getText().toString();
			String apPassword = mDBind.edtApPassword.getCodeEdit().getText().toString();

			if(TextUtils.isEmpty(apSsid)){
				showToast(getString(R.string.no_wifi));
			}else if(TextUtils.isEmpty(apPassword)){
				showToast(getString(R.string.password_is_empty));
			}else{
				Intent intent = new Intent(mContext, ConnectNetActivity.class);
				intent.putExtra("ssid", apSsid);
				intent.putExtra("psw", apPassword);
				intent.putExtra("ap", flag_ap);
				startActivity(intent);
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		try {
			String apSsid = mWifiAdmin.getWifiConnectedSsid();
			if (apSsid != null) {
				//兼容某些8.0以上手机
				if(apSsid.contains("ssid")){
					ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
					NetworkInfo networkInfo = cm.getActiveNetworkInfo();
					apSsid= networkInfo.getExtraInfo().replace("\"","");
				}
				mDBind.tvApSssidConnected.setText(apSsid);
			} else {
				mDBind.tvApSssidConnected.setText("");
			}
			UnitTools unitTools = new UnitTools(this);
			String ds = unitTools.readSSidcode(apSsid);
			if(ds!=null){
				mDBind.edtApPassword.getCodeEdit().setText(ds);
				mDBind.edtApPassword.getCodeEdit().setSelection(ds.length());
				mDBind.edtApPassword.setCodeShow(false);
			}
			if(flag_ap){
				if(!TextUtils.isEmpty(apSsid)&&!TextUtils.isEmpty(ds)){
				    showProgressDialog();
				    mHandler.postDelayed(()-> startApConfig(ds), 2000);
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			TipDialog mDialog = new TipDialog(mContext, () -> PermissionUtil.startToSetting(NetWorkActivity.this));
			mDialog.setMsg(getString(R.string.permission_reject_location_tip));
			mDialog.setConfirmMsg(getString(R.string.goto_set));
			mDialog.show();
		}
	}

	private void startApConfig(String ds){
		Intent intent = new Intent(this, ConnectNetActivity.class);
		intent.putExtra("ssid",mWifiAdmin.getWifiConnectedSsid());
		intent.putExtra("psw",ds);
		intent.putExtra("ap", true);
		startActivity(intent);
		flag_ap = false;
		dismissProgressDialog();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
