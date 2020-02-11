package com.ilop.sthome.ui.activity.xmipc;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.aliyun.iot.aep.sdk.apiclient.callback.IoTResponse;
import com.aliyun.iot.aep.sdk.credential.IotCredentialManager.IoTCredentialListener;
import com.aliyun.iot.aep.sdk.credential.IotCredentialManager.IoTCredentialManageError;
import com.aliyun.iot.aep.sdk.credential.IotCredentialManager.IoTCredentialManageImpl;
import com.aliyun.iot.aep.sdk.credential.data.IoTCredentialData;
import com.aliyun.iot.aep.sdk.login.LoginBusiness;
import com.example.common.base.BaseActivity;
import com.example.xmpic.support.FunSupport;
import com.example.xmpic.support.FunWifiPassword;
import com.example.xmpic.support.OnFunDeviceWiFiConfigListener;
import com.example.xmpic.support.models.FunDevice;
import com.example.xmpic.support.utils.MyUtils;
import com.example.xmpic.support.utils.StringUtils;
import com.ilop.sthome.data.greenDao.CameraBean;
import com.ilop.sthome.data.greenDao.CameraBeanDao;
import com.ilop.sthome.mvp.contract.CameraContract;
import com.ilop.sthome.mvp.model.CameraModel;
import com.ilop.sthome.mvp.model.common.onModelCallBack;
import com.ilop.sthome.ui.activity.main.MainActivity;
import com.ilop.sthome.utils.greenDao.CameraDaoUtil;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivityDeviceSetWifiNewBinding;

import java.util.List;


public class ActivityGuideDeviceWifiConfigNew extends BaseActivity<ActivityDeviceSetWifiNewBinding> implements OnFunDeviceWiFiConfigListener {

    private Handler mHandler = new Handler();
	private CameraContract.IModel mModel;

	@Override
	protected int getLayoutId() {
		return R.layout.activity_device_set_wifi_new;
	}

	@Override
	protected void initialize() {
		super.initialize();
		immersionStatusBar(true);
	}

	@Override
	protected void initView() {
		super.initView();
		mModel = new CameraModel();
		FunSupport.getInstance().registerOnFunDeviceWiFiConfigListener(this);
	}

	@Override
	protected void initData() {
		super.initData();
		String mSS_ID = getConnectWifiSSID();
		mDBind.editWifiSSID.setText(mSS_ID);
		mDBind.editWifiPasswd.getCodeEdit().setText(FunWifiPassword.getInstance().getPassword(mSS_ID));
	}

	@Override
	protected void initListener() {
		super.initListener();
		mDBind.ivWifiConfigBack.setOnClickListener(view -> {
			hideSoftKeyboard();
			finish();
		});
		mDBind.btnWifiQuickSetting.setOnClickListener(view -> startQuickSetting());
	}


	// 开始快速配置
	private void startQuickSetting() {
		try {
			WifiManager wifiManage = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
			WifiInfo wifiInfo = wifiManage.getConnectionInfo();
			DhcpInfo wifiDhcp = wifiManage.getDhcpInfo();

			if (null == wifiInfo ) {
				showToast(getString(R.string.device_opt_set_wifi_info_error));
				return;
			}
			
			String ssid = wifiInfo.getSSID().replace("\"", "");
			ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = cm.getActiveNetworkInfo();
			if(ssid.contains("unknow")){
				ssid= networkInfo.getExtraInfo().replace("\"","");
			}
			if ( StringUtils.isStringNULL(ssid) ) {
				showToast(getString(R.string.device_opt_set_wifi_info_error));
				return;
			}

			String wifiPwd = mDBind.editWifiPasswd.getCodeEdit().getText().toString();
			if (  StringUtils.isStringNULL(wifiPwd) ) {
				// 需要密码
				showToast(getString(R.string.device_opt_set_wifi_info_error));
				return;
			}
			int pwdType = networkInfo.getType();
			StringBuffer data = new StringBuffer();
			data.append("S:").append(ssid).append("P:").append(wifiPwd).append("T:").append(pwdType);
			
			String submask;
			if (wifiDhcp.netmask == 0) {
				submask = "255.255.255.0";
			} else {
				submask = MyUtils.formatIpAddress(wifiDhcp.netmask);
			}
			
			String mac = wifiInfo.getMacAddress();
			StringBuffer info = new StringBuffer();
			info.append("gateway:").append(MyUtils.formatIpAddress(wifiDhcp.gateway)).append(" ip:")
					.append(MyUtils.formatIpAddress(wifiDhcp.ipAddress)).append(" submask:").append(submask)
					.append(" dns1:").append(MyUtils.formatIpAddress(wifiDhcp.dns1)).append(" dns2:")
					.append(MyUtils.formatIpAddress(wifiDhcp.dns2)).append(" mac:").append(mac)
					.append(" ");
			
			showProgressDialog();
			
			FunSupport.getInstance().startWiFiQuickConfig(ssid,
					data.toString(), info.toString(), 
					MyUtils.formatIpAddress(wifiDhcp.gateway),
					pwdType, 0, mac, -1);
			
			FunWifiPassword.getInstance().saveWifiPassword(ssid, wifiPwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void stopQuickSetting() {
		FunSupport.getInstance().stopWiFiQuickConfig();
	}

	private String getConnectWifiSSID() {
		try {
			WifiManager wifimanage=(WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);

			String ssidinit = wifimanage.getConnectionInfo().getSSID().replace("\"", "");
			if(ssidinit.contains("ssid")){
				ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo networkInfo = cm.getActiveNetworkInfo();
				ssidinit = networkInfo.getExtraInfo();
			}

			return ssidinit.replace("\"","");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}


	@Override
	public void onDeviceWiFiConfigSetted(FunDevice funDevice) {
		if ( null != funDevice ) {
			refreshModifyUserInfo(funDevice.getDevSn(),funDevice.getDevName());
		}
	}


	private void refreshModifyUserInfo(String devid,String name){
		List<CameraBean> mCamera = CameraDaoUtil.getInstance().getCameraDao().queryAll();
		boolean flag = false;
		for(CameraBean cameraBean : mCamera){
			if(!TextUtils.isEmpty(cameraBean.getDeviceId()) && cameraBean.getDeviceId().equals(devid)){
				flag = true;
				break;
			}
		}
		if(flag){
			showToast(getString(R.string.already_monitor_add));
			dismissProgressDialog();
			return;
		}

		if (!LoginBusiness.isLogin()) {
			return;
		}

		IoTCredentialManageImpl ioTCredentialManage = IoTCredentialManageImpl.getInstance(getApplication());
		if (null == ioTCredentialManage) {
			return;
		}

		if (TextUtils.isEmpty(ioTCredentialManage.getIoTToken())) {
			ioTCredentialManage.asyncRefreshIoTCredential(new IoTCredentialListener() {
				@Override
				public void onRefreshIoTCredentialSuccess(IoTCredentialData ioTCredentialData) {
					modifyUserInfo(devid, name);
				}

				@Override
				public void onRefreshIoTCredentialFailed(IoTCredentialManageError ioTCredentialManageError) {
					dismissProgressDialog();
				}
			});
		} else {
			modifyUserInfo(devid, name);
		}
	}

	private void modifyUserInfo(String deviceId,String name) {
		Log.i(TAG, "modifyUserInfo: " + deviceId + "--" + name);
		List<CameraBean> mCameraList = CameraDaoUtil.getInstance().getCameraDao().queryByQueryBuilder(CameraBeanDao.Properties.DeviceId.eq(deviceId));
		if (mCameraList.size()>0){
			showToast(getString(R.string.input_exist));
			dismissProgressDialog();
			return;
		}
		mModel.toAddCamera(deviceId, name, new onModelCallBack() {
			@Override
			public void onFailure(Exception e) {
				mHandler.post(() -> {
					showToast(e.getMessage());
					dismissProgressDialog();
				});
			}

			@Override
			public void onResponse(IoTResponse response) {
				mHandler.post(() -> {
					dismissProgressDialog();
					if (response.getCode() != 200) {
						showToast(response.getLocalizedMsg());
					}else {
						showToast(getString(R.string.success));
						Intent intent = new Intent(ActivityGuideDeviceWifiConfigNew.this, MainActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
					}
				});
			}
		});
	}

	@Override
	protected void onDestroy() {
		stopQuickSetting();
		super.onDestroy();
	}

}
