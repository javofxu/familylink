package com.ilop.sthome.ui.activity.xmipc;

import com.example.common.base.BaseActivity;
import com.example.xmpic.support.FunDevicePassword;
import com.example.xmpic.support.FunError;
import com.example.xmpic.support.FunSupport;
import com.example.xmpic.support.OnFunDeviceOptListener;
import com.example.xmpic.support.config.ModifyPassword;
import com.example.xmpic.support.models.FunDevice;
import com.lib.FunSDK;
import com.lib.sdk.struct.H264_DVR_FILE_DATA;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivityDeviceChangePasswordBinding;

/**
 * Demo: 修改设备密码
 */
public class ActivityGuideDeviceChangePwd extends BaseActivity<ActivityDeviceChangePasswordBinding> implements OnFunDeviceOptListener {

    private FunDevice mFunDevice = null;

	@Override
	protected int getLayoutId() {
		return R.layout.activity_device_change_password;
	}

	@Override
	protected void initialize() {
		super.initialize();
		immersionStatusBar(true);
		int deviceId = getIntent().getIntExtra("FUN_DEVICE_ID", 0);
		FunDevice funDevice = FunSupport.getInstance().findDeviceById(deviceId);
		if ( null == funDevice ) {
			finish();
			return;
		}
		mFunDevice = funDevice;
	}

	@Override
	protected void initView() {
		super.initView();
		mDBind.etNewPassword.setInputType(1);
		mDBind.etConfirmPassword.setInputType(1);
	}

	@Override
	protected void initData() {
		super.initData();
	}

	@Override
	protected void initListener() {
		super.initListener();
		mDBind.ivPwdBack.setOnClickListener(view -> finish());
		mDBind.btChangePassword.setOnClickListener(view -> tryToChangePwd());
		FunSupport.getInstance().registerOnFunDeviceOptListener(this); //注册设备操作类的监听
	}


    private void tryToChangePwd() {

        String oldPassw = mDBind.etOldPassword.getInputContent();
        String newPassw = mDBind.etNewPassword.getInputContent();
        String newPasswConfirm = mDBind.etConfirmPassword.getInputContent();

        if (!newPassw.equals(newPasswConfirm)) {
            showToast(getString(R.string.user_change_password_error_passwnotequal));
            return;
        }
        showProgressDialog();

        // 修改密码,设置ModifyPassword参数
        // 注意,如果是直接调用FunSDK.DevSetConfigByJson()接口,需要对密码做MD5加密,参考ModifyPassword.java的处理
        ModifyPassword modifyPasswd = new ModifyPassword();
        modifyPasswd.PassWord = oldPassw;
        modifyPasswd.NewPassWord = newPasswConfirm;
        
        FunSupport.getInstance().requestDeviceSetConfig(mFunDevice, modifyPasswd);
    }

	@Override
	public void onDeviceLoginSuccess(FunDevice funDevice) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDeviceLoginFailed(FunDevice funDevice, Integer errCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDeviceGetConfigSuccess(FunDevice funDevice,
										 String configName, int nSeq) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDeviceGetConfigFailed(FunDevice funDevice, Integer errCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDeviceSetConfigSuccess(FunDevice funDevice, String configName) {
		if ( ModifyPassword.CONFIG_NAME.equals(configName) ) {
			// 修改密码成功,保存新密码,下次登录使用
			if (null != mFunDevice) {
				FunDevicePassword.getInstance().saveDevicePassword(mFunDevice.getDevSn(), mDBind.etNewPassword.getInputContent());
			}
			// 库函数方式本地保存密码
			if (FunSupport.getInstance().getSaveNativePassword()) {
				FunSDK.DevSetLocalPwd(mFunDevice.getDevSn(), "admin", mDBind.etNewPassword.getInputContent());
				// 如果设置了使用本地保存密码，则将密码保存到本地文件
			}
			// 隐藏等待框
			dismissProgressDialog();
			showToast(getString(R.string.user_forget_pwd_reset_passw_success));
			finish();
		}
	}

	@Override
	public void onDeviceSetConfigFailed(FunDevice funDevice, String configName, Integer errCode) {
		dismissProgressDialog();
		showToast(FunError.getErrorStr(errCode));
	}

	@Override
	public void onDeviceChangeInfoSuccess(FunDevice funDevice) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDeviceChangeInfoFailed(FunDevice funDevice, Integer errCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDeviceOptionSuccess(FunDevice funDevice, String option) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDeviceOptionFailed(FunDevice funDevice, String option,
                                     Integer errCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDeviceFileListChanged(FunDevice funDevice) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDeviceFileListChanged(FunDevice funDevice,
			H264_DVR_FILE_DATA[] datas) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDeviceFileListGetFailed(FunDevice funDevice) {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void onDestroy() {
		// 注销监听
		super.onDestroy();
		FunSupport.getInstance().removeOnFunDeviceOptListener(this);
	}
}
