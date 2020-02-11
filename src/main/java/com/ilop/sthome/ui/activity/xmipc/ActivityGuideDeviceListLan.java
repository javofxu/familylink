package com.ilop.sthome.ui.activity.xmipc;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;

import com.aliyun.iot.aep.sdk.apiclient.callback.IoTResponse;
import com.aliyun.iot.aep.sdk.credential.IotCredentialManager.IoTCredentialListener;
import com.aliyun.iot.aep.sdk.credential.IotCredentialManager.IoTCredentialManageError;
import com.aliyun.iot.aep.sdk.credential.IotCredentialManager.IoTCredentialManageImpl;
import com.aliyun.iot.aep.sdk.credential.data.IoTCredentialData;
import com.aliyun.iot.aep.sdk.login.LoginBusiness;
import com.example.common.base.BaseActivity;
import com.example.common.base.OnCallBackToEdit;
import com.example.common.utils.LiveDataBus;
import com.example.common.view.refresh.CustomRefreshView;
import com.example.xmpic.support.FunDevicePassword;
import com.example.xmpic.support.FunError;
import com.example.xmpic.support.FunSupport;
import com.example.xmpic.support.OnFunDeviceListener;
import com.example.xmpic.support.OnFunDeviceOptListener;
import com.example.xmpic.support.models.FunDevice;
import com.example.xmpic.support.models.FunLoginType;
import com.ilop.sthome.data.greenDao.CameraBean;
import com.ilop.sthome.data.greenDao.CameraBeanDao;
import com.ilop.sthome.mvp.contract.CameraContract;
import com.ilop.sthome.mvp.model.CameraModel;
import com.ilop.sthome.mvp.model.common.onModelCallBack;
import com.ilop.sthome.ui.activity.main.MainActivity;
import com.ilop.sthome.ui.adapter.camera.LocalNetIPCAdapter;
import com.ilop.sthome.ui.dialog.BaseEditDialog;
import com.ilop.sthome.utils.greenDao.CameraDaoUtil;
import com.lib.FunSDK;
import com.lib.sdk.struct.H264_DVR_FILE_DATA;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivityLocalnetIpcListBinding;

import java.util.ArrayList;
import java.util.List;


/**
 *  搜索显示同一个局域网内的设备列表
 * 1. 切换访问模式为本地访问  - FunSDK.SysInitLocal()
 * 2. 注册设备列表更新监听  - FunSupport.registerOnFunDeviceListener()
 * 3. 搜索局域网内的设备 - FunSDK.DevSearchDevice()
 * 4. 搜索结果通过监听返回  - onLanDeviceListChanged()/标识设备列表变化了,界面可以刷新了
 * 5. 退出并注销监听
 */
public class ActivityGuideDeviceListLan extends BaseActivity<ActivityLocalnetIpcListBinding> implements OnFunDeviceListener, OnFunDeviceOptListener {

	private final static String TAG  = ActivityGuideDeviceListLan.class.getName();
	private final int MESSAGE_REFRESH_DEVICE_STATUS = 0x100;
	private LocalNetIPCAdapter mAdapter = null;
	private List<FunDevice> mLanDeviceList = new ArrayList<>();
	private BaseEditDialog mDialog;
	private FunDevice funDevice;
	private CameraContract.IModel mModel;

	@Override
	protected int getLayoutId() {
		return R.layout.activity_localnet_ipc_list;
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
		mAdapter = new LocalNetIPCAdapter(mContext);
		mDBind.crLocalList.getRecyclerView().setLayoutManager(new LinearLayoutManager(mContext));
		mDBind.crLocalList.setRefreshing(false);
		mDBind.crLocalList.setAdapter(mAdapter);
		mDBind.crLocalList.setEmptyView(getString(R.string.ali_device_empty),0);
	}

	@Override
	protected void initData() {
		super.initData();
		// 刷新设备列表
		refreshLanDeviceList();
		// Demo，如果是进入设备列表就切换到本地模式,退出时切换回NET模式
		FunSupport.getInstance().setLoginType(FunLoginType.LOGIN_BY_LOCAL);
		// 监听设备类事件
		FunSupport.getInstance().registerOnFunDeviceListener(this);
		// 注册设备操作回调
		FunSupport.getInstance().registerOnFunDeviceOptListener(this);
		// 打开之后进行一次搜索
		requestToGetLanDeviceList();

	}

	@Override
	protected void initListener() {
		super.initListener();
		mDBind.ivLocalNetBack.setOnClickListener(view -> finish());
		mDBind.crLocalList.setOnLoadListener(new CustomRefreshView.OnLoadListener() {
			@Override
			public void onRefresh() {
				// 刷新设备列表
				requestToGetLanDeviceList();
				mDBind.crLocalList.complete();
			}

			@Override
			public void onLoadMore() {

			}
		});

		LiveDataBus.get().with("local_net_ipc", Integer.class).observe(this, integer -> {
			showProgressDialog();
			funDevice = mLanDeviceList.get(integer);
			if (null != funDevice) {
				if(!funDevice.hasLogin() || !funDevice.hasConnected()) {
					// 重新登录
					FunSupport.getInstance().requestDeviceLogin(funDevice);
				}else {
					refreshModifyUserInfo(funDevice.getDevSn(),funDevice.getDevName());
				}
			}
		});
	}


	@Override
	protected void onPause() {
		super.onPause();
	}


	private void requestToGetLanDeviceList() {
		if ( !FunSupport.getInstance().requestLanDeviceList() ) {
			showToast(getString(R.string.guide_message_error_call));
		} else {
			showProgressDialog();
		}
	}
	
	private void refreshLanDeviceList() {
		dismissProgressDialog();
		mLanDeviceList.clear();
		mLanDeviceList.addAll(FunSupport.getInstance().getLanDeviceList());
		mAdapter.setLists(mLanDeviceList);
		mAdapter.notifyDataSetChanged();
		
		// 延时100毫秒更新设备消息
		mHandler.removeMessages(MESSAGE_REFRESH_DEVICE_STATUS);
		if ( mLanDeviceList.size() > 0 ) {
			mHandler.sendEmptyMessageDelayed(MESSAGE_REFRESH_DEVICE_STATUS, 100);
		}
	}

	/**
	 * 以下函数实现来自OnFunDeviceListener()，监听设备列表变化
	 */
	@Override
	public void onDeviceListChanged() {
	}

	@Override
	public void onDeviceStatusChanged(final FunDevice funDevice) {
		mAdapter.notifyDataSetChanged();
	}
	
	@Override
	public void onDeviceAddedSuccess() {
		
	}


	@Override
	public void onDeviceAddedFailed(Integer errCode) {
		
	}


	@Override
	public void onDeviceRemovedSuccess() {
		
	}


	@Override
	public void onDeviceRemovedFailed(Integer errCode) {
		
	}
	
	@Override
	public void onAPDeviceListChanged() {
		
	}
	
	@Override
	public void onLanDeviceListChanged() {
		refreshLanDeviceList();
	}





	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch(msg.what) {
			case MESSAGE_REFRESH_DEVICE_STATUS:
				FunSupport.getInstance().requestAllLanDeviceStatus();
				break;
			}
		}
	};



	@Override
	public void onDeviceLoginSuccess(FunDevice funDevice) {
		if ( null != funDevice ) {
			refreshModifyUserInfo(funDevice.getDevSn(),funDevice.getDevName());
		}
	}

	@Override
	public void onDeviceLoginFailed(FunDevice funDevice, Integer errCode) {
		// 如果账号密码不正确,那么需要提示用户,输入密码重新登录
		dismissProgressDialog();
		if (errCode == FunError.EE_DVR_PASSWORD_NOT_VALID) {
			showInputPasswordDialog();
		}
	}

	@Override
	public void onDeviceGetConfigSuccess(FunDevice funDevice, String configName, int nSeq) {

	}

	@Override
	public void onDeviceGetConfigFailed(FunDevice funDevice, Integer errCode) {

	}

	@Override
	public void onDeviceSetConfigSuccess(FunDevice funDevice, String configName) {

	}

	@Override
	public void onDeviceSetConfigFailed(FunDevice funDevice, String configName, Integer errCode) {

	}

	@Override
	public void onDeviceChangeInfoSuccess(FunDevice funDevice) {

	}

	@Override
	public void onDeviceChangeInfoFailed(FunDevice funDevice, Integer errCode) {

	}

	@Override
	public void onDeviceOptionSuccess(FunDevice funDevice, String option) {

	}

	@Override
	public void onDeviceOptionFailed(FunDevice funDevice, String option, Integer errCode) {

	}

	@Override
	public void onDeviceFileListChanged(FunDevice funDevice) {

	}

	@Override
	public void onDeviceFileListChanged(FunDevice funDevice, H264_DVR_FILE_DATA[] datas) {

	}

	@Override
	public void onDeviceFileListGetFailed(FunDevice funDevice) {

	}


	/**
	 * 显示输入设备密码对话框
	 */
	private void showInputPasswordDialog() {

		mDialog = new BaseEditDialog(mContext, new OnCallBackToEdit() {
			@Override
			public void onConfirm(String name) {
				onDeviceSaveNativePws(name);
				if (null != funDevice) {
					FunSupport.getInstance().requestDeviceLogin(funDevice);
				}
			}

			@Override
			public void onCancel() {
			}
		});
		mDialog.setTitleAndButton(getString(R.string.device_login_input_password), getString(R.string.cancel), getString(R.string.ok));
		mDialog.show();
	}


	public void onDeviceSaveNativePws(String NativeLoginPsw) {
		FunDevicePassword.getInstance().saveDevicePassword(funDevice.getDevSn(),
				NativeLoginPsw);
		// 库函数方式本地保存密码
		if (FunSupport.getInstance().getSaveNativePassword()) {
			FunSDK.DevSetLocalPwd(funDevice.getDevSn(), "admin", NativeLoginPsw);
			// 如果设置了使用本地保存密码，则将密码保存到本地文件
		}
	}


	private void refreshModifyUserInfo(String deviceId, String name){
	    List<CameraBean> cameraList = CameraDaoUtil.getInstance().getCameraDao().queryAll();
	    boolean flag = false;
        for(CameraBean cameraBean : cameraList){
            if(!TextUtils.isEmpty(cameraBean.getDeviceId()) && cameraBean.getDeviceId().equals(deviceId)){
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
					modifyUserInfo(deviceId, name);
				}

				@Override
				public void onRefreshIoTCredentialFailed(IoTCredentialManageError ioTCredentialManageError) {
					showProgressDialog();
				}
			});
		} else {
			modifyUserInfo(deviceId, name);
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
						Intent intent = new Intent(ActivityGuideDeviceListLan.this, MainActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
					}
				});
			}
		});
	}

	@Override
	protected void onDestroy() {
		// 注销设备事件监听
		FunSupport.getInstance().removeOnFunDeviceListener(this);
		FunSupport.getInstance().removeOnFunDeviceOptListener(this);
		super.onDestroy();
	}
}
