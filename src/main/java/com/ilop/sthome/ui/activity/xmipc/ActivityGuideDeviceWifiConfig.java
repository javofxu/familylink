package com.ilop.sthome.ui.activity.xmipc;


import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;

import com.aliyun.iot.aep.sdk.apiclient.callback.IoTResponse;
import com.aliyun.iot.aep.sdk.credential.IotCredentialManager.IoTCredentialListener;
import com.aliyun.iot.aep.sdk.credential.IotCredentialManager.IoTCredentialManageError;
import com.aliyun.iot.aep.sdk.credential.IotCredentialManager.IoTCredentialManageImpl;
import com.aliyun.iot.aep.sdk.credential.data.IoTCredentialData;
import com.aliyun.iot.aep.sdk.login.LoginBusiness;
import com.example.common.base.BaseActivity;
import com.example.xmpic.support.FunSupport;
import com.example.xmpic.support.OnFunDeviceWiFiConfigListener;
import com.example.xmpic.support.models.FunDevice;
import com.ilop.sthome.data.greenDao.CameraBean;
import com.ilop.sthome.data.greenDao.CameraBeanDao;
import com.ilop.sthome.mvp.contract.CameraContract;
import com.ilop.sthome.mvp.model.CameraModel;
import com.ilop.sthome.mvp.model.common.onModelCallBack;
import com.ilop.sthome.ui.activity.main.MainActivity;
import com.ilop.sthome.ui.activity.main.QRCodeActivity;
import com.ilop.sthome.utils.greenDao.CameraDaoUtil;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivityDeviceSetWifiBinding;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ActivityGuideDeviceWifiConfig extends BaseActivity<ActivityDeviceSetWifiBinding> implements OnClickListener, OnFunDeviceWiFiConfigListener {

	private CameraContract.IModel mModel;
    private Handler mHandler = new Handler();

	@Override
	protected int getLayoutId() {
		return R.layout.activity_device_set_wifi;
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
	protected void initListener() {
		super.initListener();
		mDBind.btnWifiQuickAdd.setOnClickListener(this);
		mDBind.btnScanCode.setOnClickListener(this);
		mDBind.ivWifiConfigBack.setOnClickListener(view -> finish());
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
			case R.id.btnWifiQuickAdd:
				showProgressDialog();
				startSNAddResult();
				break;
			case R.id.btnScanCode:
				startActivityForResult(new Intent(this, QRCodeActivity.class),1);
				break;
		}
	}

	private void startSNAddResult(){
		String code = mDBind.editSN.getInputContent();
		if(TextUtils.isEmpty(code)){
			dismissProgressDialog();
			showToast(getResources().getString(R.string.camera_empety_sn));
			return;
		}
		refreshModifyUserInfo(code, code);
	}

	@Override
	public void onDeviceWiFiConfigSetted(FunDevice funDevice) {
		if ( null != funDevice ) {
			refreshModifyUserInfo(funDevice.getDevSn(),funDevice.getDevName());
		}
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode!=RESULT_OK) return;

		if(requestCode==1){
			Pattern pattern = Pattern.compile("^[A-Za-z0-9]{16}$");
			String sn = data.getExtras().getString("SN");
			Matcher m = pattern.matcher(sn);
			if( !m.matches() ){ //匹配不到,說明輸入的不符合條件
              showToast(getString(R.string.sn_is_illegal));
			}else{
				if(!TextUtils.isEmpty(sn))
					mDBind.editSN.setInputContent(sn);
			}
		}
	}

	private void refreshModifyUserInfo(String devid,String name){
		List<CameraBean> mList = CameraDaoUtil.getInstance().getCameraDao().queryAll();
		boolean flag = false;
		for(CameraBean cameraBean : mList){
			if(!TextUtils.isEmpty(cameraBean.getDeviceId()) && cameraBean.getDeviceId().equals(devid)){
				flag = true;
				break;
			}
		}
		if(flag){
			showToast(getResources().getString(R.string.already_monitor_add));
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
					hideSoftKeyboard();
					if (response.getCode() != 200) {
						showToast(response.getLocalizedMsg());
					}else {
						showToast(getString(R.string.success));
						Intent intent = new Intent(ActivityGuideDeviceWifiConfig.this, MainActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
					}
				});
			}
		});
	}
}
