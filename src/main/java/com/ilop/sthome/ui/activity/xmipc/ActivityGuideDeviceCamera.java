package com.ilop.sthome.ui.activity.xmipc;


import android.Manifest;
import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.example.common.base.BaseActivity;
import com.example.common.base.OnCallBackToEdit;
import com.example.common.base.OnCallBackToRefresh;
import com.example.common.utils.DisplayUtils;
import com.example.common.utils.LiveDataBus;
import com.example.common.view.scroll.HeaderScrollHelper;
import com.example.xmpic.support.FunDevicePassword;
import com.example.xmpic.support.FunError;
import com.example.xmpic.support.FunPath;
import com.example.xmpic.support.FunSupport;
import com.example.xmpic.support.OnFunDeviceListener;
import com.example.xmpic.support.OnFunDeviceOptListener;
import com.example.xmpic.support.config.SimplifyEncode;
import com.example.xmpic.support.config.SystemInfo;
import com.example.xmpic.support.models.FunDevStatus;
import com.example.xmpic.support.models.FunDevType;
import com.example.xmpic.support.models.FunDevice;
import com.example.xmpic.support.models.FunLoginType;
import com.example.xmpic.support.models.FunStreamType;
import com.example.xmpic.support.utils.TalkManager;
import com.example.xmpic.support.utils.UIFactory;
import com.example.xmpic.support.widget.FunVideoView;
import com.ilop.sthome.data.greenDao.CameraBean;
import com.ilop.sthome.data.greenDao.CameraBeanDao;
import com.ilop.sthome.ui.adapter.camera.CameraChoseAdapter;
import com.ilop.sthome.ui.dialog.BaseDialog;
import com.ilop.sthome.ui.dialog.BaseEditDialog;
import com.ilop.sthome.ui.dialog.BaseIamgeDialog;
import com.ilop.sthome.ui.dialog.BaseListDialog;
import com.ilop.sthome.utils.NetWorkUtil;
import com.ilop.sthome.utils.greenDao.CommonDaoUtils;
import com.ilop.sthome.utils.greenDao.CameraDaoUtil;
import com.ilop.sthome.utils.tools.SystemTintManager;
import com.lib.EPTZCMD;
import com.lib.FunSDK;
import com.lib.sdk.struct.H264_DVR_FILE_DATA;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivityDeviceCameraBinding;
import com.xmgl.vrsoft.VRSoftDefine;

import java.io.File;
import java.util.List;

/**
 * Demo: 监控类设备播放控制等
 *
 *
 */
public class ActivityGuideDeviceCamera extends BaseActivity<ActivityDeviceCameraBinding> implements OnClickListener, OnFunDeviceOptListener,
		OnPreparedListener, OnErrorListener, OnInfoListener,OnFunDeviceListener, FunVideoView.SwitchViewListener,  HeaderScrollHelper.ScrollableContainer{

    private final String TAG = "DeviceCamera";

	private FunDevice mFunDevice = null;

	private final int REQUEST_PERMISSION = 0;
	private final int MESSAGE_PLAY_MEDIA = 0x100;
	private final int MESSAGE_AUTO_HIDE_CONTROL_BAR = 0x102;
	private final int MESSAGE_TOAST_SCREENSHOT_PREVIEW = 0x103;
	private final int MESSAGE_OPEN_VOICE = 0x104;
	private final int MESSAGE_AUTO_CAPTURE = 0X105;

	private final int MESSAGE_DELAY_FINISH = 0x106;
    private final int MESSAGE_AUTO_HIDE_DIRECTION  = 0x107;
	private final int MESSAGE_SECOND_QUERY_STATUS = 0x108; //第二次查询设备状态，为了兼容门铃唤醒
	private final int AUTO_HIDE_CONTROL_BAR_DURATION = 10000; // 自动隐藏底部的操作控制按钮栏的时间

	private TalkManager mTalkManager = null;

	private boolean mCanToPlay = false;
	private boolean auto_flag = true;
	private boolean auto_play = false;

	private String NativeLoginPsw; //本地密码
	private SystemTintManager systemTintManager;
	private int count_query_door_bell = 0;

	private String deviceId;
	private String deviceName;
	private CameraChoseAdapter mAdapter;
	private CameraBean mCameraBean;
	private List<CameraBean> mCameraList;
	private CommonDaoUtils<CameraBean> mCameraDao;

	@Override
	protected int getLayoutId() {
		return R.layout.activity_device_camera;
	}

	@Override
	protected void initialize() {
		super.initialize();
		initPermission();
		immersionStatusBar(true);
		deviceId = getIntent().getStringExtra("FUN_DEVICE_ID");
		deviceName = getIntent().getStringExtra("FUN_DEVICE_NAME");
		if(TextUtils.isEmpty(deviceId)) finish();
		Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		Uri uri = Uri.fromFile(new File(FunPath.DEFAULT_PATH));
		intent.setData(uri);
		sendBroadcast(intent);
	}

	@Override
	protected void initView() {
		super.initView();
		mCameraDao = CameraDaoUtil.getInstance().getCameraDao();
		mAdapter = new CameraChoseAdapter(mContext);
		LinearLayoutManager manager = new LinearLayoutManager(mContext);
		manager.setOrientation(LinearLayoutManager.HORIZONTAL);
		mDBind.rvCameraList.setLayoutManager(manager);
		mDBind.rvCameraList.setAdapter(mAdapter);

		systemTintManager = new SystemTintManager(this);

		mDBind.switchFishEye.setVisibility(View.GONE);
		mDBind.settingFishEye.setVisibility(View.GONE);
        // 注册设备操作回调
        FunSupport.getInstance().registerOnFunDeviceOptListener(this);
        // 注册设备操作回调
        FunSupport.getInstance().registerOnFunDeviceListener(this);

		// 设置登录方式为本地登录
		FunSupport.getInstance().setLoginType(FunLoginType.LOGIN_BY_LOCAL);
		mDBind.tvCameraTitle.setText(TextUtils.isEmpty(deviceName) ? getString(R.string.dev_type_monitor) : deviceName);

		showVideoControlBar();

		mCanToPlay = false;
		showAsPortrait();
		initAutoCapture();
	}

	@Override
	protected void initData() {
		super.initData();
		if (deviceId != null){
			mCameraBean = mCameraDao.queryByQueryBuilder(CameraBeanDao.Properties.DeviceId.eq(deviceId)).get(0);
			requestDeviceStatus(mCameraBean);
		}
		mCameraList = mCameraDao.queryAll();
		mAdapter.setCameraList(deviceId, mCameraList);
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	protected void initListener() {
		super.initListener();
		mDBind.ivCameraBack.setOnClickListener(view -> onBackPressed());
		mDBind.ivCameraSetting.setOnClickListener(view -> {
			Intent intent =new Intent(this, ActivityGuideSettingMain.class);
			intent.putExtra("deviceSn", mFunDevice.getDevSn());
			intent.putExtra("deviceId", mFunDevice.getId());
			startActivity(intent);
		});

		LiveDataBus.get().with("chose_camera_click", CameraBean.class).observe(this, cameraBean -> {
			mCameraBean = cameraBean;
			assert cameraBean != null;
			requestDeviceStatus(cameraBean);
			mAdapter.setCameraList(mCameraBean.getDeviceId(), mCameraList);
		});

		mDBind.cameraScroll.setCurrentScrollableContainer(this::getScrollableView);
		mDBind.settingFishEye.getImageView_qiu().setOnClickListener(view -> mDBind.funVideoView.setType(VRSoftDefine.XMVRShape.Shape_Ball));
		mDBind.settingFishEye.getImageView_banyuan1().setOnClickListener(view -> mDBind.funVideoView.setType(VRSoftDefine.XMVRShape.Shape_Ball_Hat));
		mDBind.settingFishEye.getImageView_banyuan2().setOnClickListener(view -> mDBind.funVideoView.setType(VRSoftDefine.XMVRShape.Shape_Ball_Bowl));
		mDBind.settingFishEye.getImageView_radius_fangkuai().setOnClickListener(view -> mDBind.funVideoView.setType(VRSoftDefine.XMVRShape.Shape_Cylinder));
		mDBind.settingFishEye.getImageView_duoping().setOnClickListener(view -> mDBind.funVideoView.setType(VRSoftDefine.XMVRShape.Shape_Grid_4R));
		mDBind.settingFishEye.getImageView_fangkuai().setOnClickListener(view -> mDBind.funVideoView.setType(VRSoftDefine.XMVRShape.Shape_Rectangle));
		mDBind.settingFishEye.getImageView_shangxia().setOnClickListener(view -> mDBind.funVideoView.setType(VRSoftDefine.XMVRShape.Shape_Rectangle_2R));
		mDBind.btnVoiceTalk.setOnClickListener(this);
		mDBind.btnVoiceTalk.setOnTouchListener(mIntercomTouchLs);
		mDBind.btnVoiceTalk2.setOnClickListener(this);
		mDBind.btnVoiceTalk2.setOnTouchListener(mIntercomTouchLs);
		mDBind.btnDevCapture.setOnClickListener(this);
		mDBind.btnDevRecord.setOnClickListener(this);
		mDBind.switchFishEye.setOnClickListener(this);

		mDBind.btnPlay.setOnClickListener(this);
		mDBind.btnStop.setOnClickListener(this);
		mDBind.btnStream.setOnClickListener(this);
		mDBind.btnCapture.setOnClickListener(this);
		mDBind.btnScreenRatio.setOnClickListener(this);

		mDBind.funVideoView.setOnTouchListener(new OnVideoViewTouchListener());
		mDBind.funVideoView.setOnPreparedListener(this);
		mDBind.funVideoView.setOnErrorListener(this);
		mDBind.funVideoView.setOnInfoListener(this);
		mDBind.funVideoView.setmOnSwitchViewListener(this);

	}


	@Override
	protected void onResume() {
		systemTintManager.setStatusBarDarkMode(true,this);
		if (mCanToPlay) {
			int d = NetWorkUtil.getNetWorkType(this);
			if(auto_play || d==4){
				auto_play = true;
				playRealMedia();
			}else if(d > 0 && d < 4){
				BaseDialog mDialog = new BaseDialog(mContext, new OnCallBackToRefresh() {
					@Override
					public void onConfirm() {
						auto_play = true;
						playRealMedia();
					}

					@Override
					public void onCancel() {

					}
				});
				mDialog.setTitleAndButton(getString(R.string.gprs_hint), getString(R.string.cancel), getString(R.string.ok));
				mDialog.show();
			}

		}
		checkDelete();
		super.onResume();
	}

	private void checkDelete(){
	    try {
            mCameraList = mCameraDao.queryAll();
            boolean flag = false;
            for (CameraBean bean: mCameraList) {
                if (bean.getDeviceId().equals(mFunDevice.getDevSn())){
                    flag = true;
                    mDBind.tvCameraTitle.setText(bean.getDeviceName());
                    break;
                }
            }
            if(!flag) finish();
        }catch (Exception e){
	        e.printStackTrace();
        }
	}


	@Override
	protected void onPause() {
		stopTalk(0);
		stopMedia();
		if(mTalkManager!=null)
		mTalkManager.onStopTalk();
		mDBind.funVideoView.setMediaSound(true);
		super.onPause();
	}


	@Override
	public void onBackPressed() {
		// 如果当前是横屏，返回时先回到竖屏
		if (getResources().getConfiguration().orientation
	            == Configuration.ORIENTATION_LANDSCAPE) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			return;
		}
		finish();
	}


	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// 检测屏幕的方向：纵向或横向
	    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			// 当前为横屏， 在此处添加额外的处理代码
			showAsLandscape();
	    }else if(getResources().getConfiguration().orientation ==Configuration.ORIENTATION_PORTRAIT) {
			// 当前为竖屏， 在此处添加额外的处理代码
			showAsPortrait();
		}
		super.onConfigurationChanged(newConfig);
	}

	@SuppressLint("ResourceType")
	@Override
	public void onClick(View v) {
		if (v.getId() >= 1000 && v.getId() < 1000) {
			mFunDevice.CurrChannel = v.getId() - 1000;
			mDBind.funVideoView.stopPlayback();
			playRealMedia();
		}
		switch (v.getId()) {
		case R.id.btnPlay:
			int result = NetWorkUtil.getNetWorkType(this);
			if(result>=4){
				mDBind.funVideoView.stopPlayback();
				if(mFunDevice==null){
					requestDeviceStatus(mCameraBean);
				}else{
					mHandler.sendEmptyMessageDelayed(MESSAGE_PLAY_MEDIA, 1000);
				}
			}else if(result > 0){
				BaseDialog mDialog = new BaseDialog(mContext, new OnCallBackToRefresh() {
					@Override
					public void onConfirm() {
						mDBind.funVideoView.stopPlayback();
						if(mFunDevice==null){
							requestDeviceStatus(mCameraBean);
						}else{
							mHandler.sendEmptyMessageDelayed(MESSAGE_PLAY_MEDIA, 1000);
						}
					}

					@Override
					public void onCancel() {

					}
				});
				mDialog.setTitleAndButton(getString(R.string.gprs_hint), getString(R.string.cancel), getString(R.string.ok));
				mDialog.show();
			}else {
				showToast(getString(R.string.network_timeout));
			}
			break;
		case R.id.btnStop: // 静音
			switchMute();
			break;
		case R.id.btnStream: // 切换码流
			int results = NetWorkUtil.getNetWorkType(this);
			if(results>=4){
				switchMediaStream();
			}else if(results<4 && results>0){
				BaseDialog mDialog = new BaseDialog(mContext, new OnCallBackToRefresh() {
					@Override
					public void onConfirm() {
						switchMediaStream();
					}

					@Override
					public void onCancel() {

					}
				});
				mDialog.setTitleAndButton(getString(R.string.gprs_hint), getString(R.string.cancel), getString(R.string.ok));
				mDialog.show();
			}else {
				showToast(getString(R.string.network_timeout));
			}
			break;
		case R.id.btnCapture: // 截图
			tryToCapture();
			break;
		case R.id.btnDevCapture: // 远程设备图像列表
			BaseListDialog mDialog = new BaseListDialog(mContext, i -> {
				switch (i){
					case 0:
						startPictureList();
						break;
					case 1:
						Intent intent2=new Intent(ActivityGuideDeviceCamera.this, ActivityLocalPicVideo.class);
						intent2.putExtra("path",mFunDevice.getDevSn());
						intent2.putExtra("pic_or_video",0);
						startActivity(intent2);
						break;
					default:
						break;
				}
			});
			mDialog.setMsgAndButton(getResources().getStringArray(R.array.check_pics), getString(R.string.cancel));
			mDialog.show();
			break;
		case R.id.btnDevRecord: // 远程设备录像列表
			startRecordList();
			break;
		case R.id.btnScreenRatio: // 横竖屏切换
			switchOrientation();
			break;
		case R.id.switch_fish_eye:
			if(mDBind.settingFishEye.getVisibility() == View.VISIBLE){
				mDBind.switchFishEye.setImageResource(R.mipmap.qiu);
				PropertyValuesHolder valuesHolder = PropertyValuesHolder.ofFloat("scaleX", 1.0f, 0.0f);
				ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(mDBind.settingFishEye, valuesHolder);
				objectAnimator.setDuration(500).start();
				objectAnimator.addListener(new Animator.AnimatorListener() {
					@Override
					public void onAnimationStart(Animator animation) {
					}

					@Override
					public void onAnimationEnd(Animator animation) {
						mDBind.settingFishEye.setVisibility(View.GONE);
					}

					@Override
					public void onAnimationCancel(Animator animation) {

					}

					@Override
					public void onAnimationRepeat(Animator animation) {

					}
				});
			}else{
				mDBind.settingFishEye.setVisibility(View.VISIBLE);
				mDBind.switchFishEye.setImageResource(R.mipmap.qiu);
				PropertyValuesHolder valuesHolder = PropertyValuesHolder.ofFloat("scaleX", 0.0f, 1.0f);
				ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(mDBind.settingFishEye, valuesHolder);
				objectAnimator.setDuration(500).start();
				objectAnimator.addListener(new Animator.AnimatorListener() {
					@Override
					public void onAnimationStart(Animator animation) {
					}

					@Override
					public void onAnimationEnd(Animator animation) {

					}

					@Override
					public void onAnimationCancel(Animator animation) {

					}

					@Override
					public void onAnimationRepeat(Animator animation) {

					}
				});
			}
				break;
		}


	}

	private void switchMute(){
		if(mFunDevice!=null){
			SimplifyEncode simpEnc = (SimplifyEncode) mFunDevice.getConfig(SimplifyEncode.CONFIG_NAME);
			if(FunStreamType.STREAM_SECONDARY ==mDBind.funVideoView.getStreamType()){
				  boolean flag = !simpEnc.extraFormat.AudioEnable;
					simpEnc.mainFormat.AudioEnable = flag;
					simpEnc.extraFormat.AudioEnable = flag;


			}else if(FunStreamType.STREAM_MAIN ==mDBind.funVideoView.getStreamType()){
				boolean flag = !simpEnc.mainFormat.AudioEnable;
				simpEnc.mainFormat.AudioEnable = flag;
				simpEnc.extraFormat.AudioEnable = flag;
			}
			FunSupport.getInstance().requestDeviceSetConfig(mFunDevice, simpEnc);
		}


	}

	private void tryToRecord() {

		if (!mDBind.funVideoView.isPlaying() || mDBind.funVideoView.isPaused()) {
			showToast(getString(R.string.media_record_failure_need_playing));
			return;
		}


		if (mDBind.funVideoView.bRecord) {
			mDBind.funVideoView.stopRecordVideo();
			mDBind.layoutRecording.setVisibility(View.INVISIBLE);

			toastRecordSuccess(mDBind.funVideoView.getFilePath());
		} else {
			mDBind.funVideoView.startRecordVideo(mFunDevice.getDevSn());
			mDBind.layoutRecording.setVisibility(View.VISIBLE);
			showToast(getString(R.string.media_record_start));
		}

	}

	/**
	 * 视频截图,并延时一会提示截图对话框
	 */
	private void tryToCapture() {
		if (!mDBind.funVideoView.isPlaying()) {
			showToast(getString(R.string.media_capture_failure_need_playing));
			return;
		}

		final String path = mDBind.funVideoView.captureImage(mFunDevice.getDevSn());	//图片异步保存
		if (!TextUtils.isEmpty(path)) {
			Message message = new Message();
			message.what = MESSAGE_TOAST_SCREENSHOT_PREVIEW;
			message.obj = path;
			mHandler.sendMessageDelayed(message, 200);			//此处延时一定时间等待图片保存完成后显示，也可以在回调成功后显示
		}
	}

	/**
	 * 视频自动截图
	 */
	private void autoToCapture() {
		if (!mDBind.funVideoView.isPlaying()) {
			//showToast(R.string.media_capture_failure_need_playing);
			Log.i(TAG,getResources().getString(R.string.media_capture_failure_need_playing));
			return;
		}

		final String path = mDBind.funVideoView.autocaptureImage(FunPath.getAutoCapturePath(mFunDevice.getDevSn()));	//图片异步保存
		Log.i(TAG,"存储位置为:"+path);
	}


	/**
	 * 显示截图成功对话框
	 * @param path
	 */
	private void toastScreenShotPreview(final String path) {
		Bitmap bitmap = BitmapFactory.decodeFile(path);
		BaseIamgeDialog mDialog = new BaseIamgeDialog(mContext, new OnCallBackToRefresh() {
			@Override
			public void onConfirm() {
				File file = new File(path);
				File imgPath = new File(FunPath.PATH_CAPTURE_TEMP + File.separator+mFunDevice.getDevSn()+File.separator + file.getName());
				if (imgPath.exists()) {
					String d = String.format(getResources().getString(R.string.device_socket_capture_save_success),path);
					showToast(d);
				}
				Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
				Uri uri = Uri.fromFile(new File(path));
				intent.setData(uri);
				sendBroadcast(intent);
			}

			@Override
			public void onCancel() {
				FunPath.deleteFile(path);
				showToast(getString(R.string.device_socket_capture_delete_success));
			}
		});
		mDialog.setImageAndButton(bitmap, getString(R.string.delete), getString(R.string.save));
		mDialog.show();
	}

	/**
	 * 显示录像成功对话框
	 * @param path
	 */
	private void toastRecordSuccess(final String path) {
		Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		Uri uri = Uri.fromFile(new File(path));
		intent.setData(uri);
		sendBroadcast(intent);
		BaseDialog mDialog = new BaseDialog(mContext, new OnCallBackToRefresh() {
			@Override
			public void onConfirm() {
				Intent intent = new Intent("android.intent.action.VIEW");
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				String type = "video/*";
				Uri uri = Uri.fromFile(new File(path));
				intent.setDataAndType(uri, type);
				startActivity(intent);
			}

			@Override
			public void onCancel() {

			}
		});
		mDialog.setTitleAndButton(getString(R.string.device_sport_camera_record_success)
						+"\n" +getString(R.string.media_record_stop) + path,
				getString(R.string.device_sport_camera_record_success_cancel),
				getString(R.string.device_sport_camera_record_success_open));
        mDialog.show();
	}

	private void showVideoControlBar() {
		if (mDBind.layoutVideoControl.getVisibility() != View.VISIBLE) {
			TranslateAnimation ani = new TranslateAnimation(0, 0, UIFactory.dip2px(this, 42), 0);
			ani.setDuration(200);
			mDBind.layoutVideoControl.startAnimation(ani);
			mDBind.layoutVideoControl.setVisibility(View.VISIBLE);
		}

		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			// 横屏情况下,顶部标题栏也动画显示
			TranslateAnimation ani = new TranslateAnimation(0, 0, -UIFactory.dip2px(this, 48), 0);
			ani.setDuration(200);
		}

		// 显示后设置10秒后自动隐藏
		mHandler.removeMessages(MESSAGE_AUTO_HIDE_CONTROL_BAR);
		mHandler.sendEmptyMessageDelayed(MESSAGE_AUTO_HIDE_CONTROL_BAR, AUTO_HIDE_CONTROL_BAR_DURATION);
	}

	private void hideVideoControlBar() {
		if (mDBind.layoutVideoControl.getVisibility() != View.GONE) {
			TranslateAnimation ani = new TranslateAnimation(0, 0, 0, UIFactory.dip2px(this, 42));
			ani.setDuration(200);
			mDBind.layoutVideoControl.startAnimation(ani);
			mDBind.layoutVideoControl.setVisibility(View.GONE);
		}

		if (getResources().getConfiguration().orientation
	            == Configuration.ORIENTATION_LANDSCAPE) {
			// 横屏情况下,顶部标题栏也隐藏
			TranslateAnimation ani = new TranslateAnimation(0, 0, 0, -UIFactory.dip2px(this, 48));
			ani.setDuration(200);
		}

		// 隐藏后清空自动隐藏的消息
		mHandler.removeMessages(MESSAGE_AUTO_HIDE_CONTROL_BAR);
	}

	private void hideDirection(){
		if(mDBind.leftDirection.getVisibility() == View.VISIBLE){
			mDBind.leftDirection.setVisibility(View.GONE);
		}

		if(mDBind.rightDirection.getVisibility() == View.VISIBLE){
			mDBind.rightDirection.setVisibility(View.GONE);
		}

		if(mDBind.downDirection.getVisibility() == View.VISIBLE){
			mDBind.downDirection.setVisibility(View.GONE);
		}

		if(mDBind.upDirection.getVisibility() == View.VISIBLE){
			mDBind.upDirection.setVisibility(View.GONE);
		}

		// 隐藏后清空自动隐藏的消息
		mHandler.removeMessages(MESSAGE_AUTO_HIDE_DIRECTION);
	}

	private void showAsLandscape() {
		systemTintManager.setStatusBarAlpha(0.0f);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		FrameLayout.LayoutParams lpctrl = (FrameLayout.LayoutParams) mDBind.layoutVideoControl2.getLayoutParams();
		lpctrl.leftMargin = UIFactory.dip2px(this, 120);
		lpctrl.rightMargin = UIFactory.dip2px(this, 120);
		mDBind.layoutVideoControl2.setLayoutParams(lpctrl);
		// 隐藏底部的控制按钮区域
		mDBind.layoutFunctionControl.setVisibility(View.GONE);
		mDBind.vvCameraFull.setVisibility(View.GONE);
		mDBind.rvCameraList.setVisibility(View.GONE);
		mDBind.rlCameraTop.setVisibility(View.GONE);
		mDBind.cameraScroll.setTopViewHeight(0);

		mDBind.btnScreenRatio.setImageResource(R.mipmap.icon_full_back);
		RelativeLayout.LayoutParams lpWnd2 = (RelativeLayout.LayoutParams) mDBind.textStreamStat.getLayoutParams();
		lpWnd2.topMargin = UIFactory.dip2px(this, 60);
		mDBind.textStreamStat.setLayoutParams(lpWnd2);
		mDBind.btnVoiceTalk2.setVisibility(View.VISIBLE);
	}

	private void showAsPortrait() {
		systemTintManager.setStatusBarAlpha(1.0f);
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		mDBind.btnScreenRatio.setImageResource(R.mipmap.icon_full);
		// 显示底部的控制按钮区域

		FrameLayout.LayoutParams lpctrl = (FrameLayout.LayoutParams) mDBind.layoutVideoControl2.getLayoutParams();
		lpctrl.leftMargin = UIFactory.dip2px(this, 0);
		lpctrl.rightMargin = UIFactory.dip2px(this, 0);
		mDBind.layoutVideoControl2.setLayoutParams(lpctrl);

		mDBind.layoutFunctionControl.setVisibility(View.VISIBLE);
        mDBind.vvCameraFull.setVisibility(View.VISIBLE);
        mDBind.rvCameraList.setVisibility(View.VISIBLE);
        mDBind.rlCameraTop.setVisibility(View.VISIBLE);
        mDBind.cameraScroll.setTopViewHeight(DisplayUtils.dip2px(mContext, 120));
		RelativeLayout.LayoutParams lpWnd2 = (RelativeLayout.LayoutParams) mDBind.textStreamStat.getLayoutParams();
		lpWnd2.topMargin = UIFactory.dip2px(this, 10);
		mDBind.textStreamStat.setLayoutParams(lpWnd2);
		mDBind.btnVoiceTalk2.setVisibility(View.GONE);
	}

	/**
	 * 切换视频全屏/小视频窗口(以切横竖屏切换替代)
	 */
	private void switchOrientation() {
		// 横竖屏切换
		if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
				&& getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE) {
			// setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
		} else if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
	}



	@Override
	public void onDeviceListChanged() {

	}

	@Override
	public void onDeviceStatusChanged(FunDevice funDevice) {
	    mFunDevice = funDevice;
		int flag =  FunSupport.getInstance().DevSleepAwake(funDevice.getDevSn());
		Log.i(TAG,"唤醒门铃咯flag："+flag);
		Log.i(TAG,"设备类型："+funDevice.getDevType().name());
			if (funDevice.devStatus == FunDevStatus.STATUS_ONLINE) {
				// 如果设备在线,获取设备信息
				if ((funDevice.devType == null )) {
					funDevice.devType = FunDevType.EE_DEV_NORMAL_MONITOR;
				}

				if (null != mHandler) {
					mHandler.removeMessages(MESSAGE_DELAY_FINISH);
					mHandler.sendEmptyMessage(MESSAGE_DELAY_FINISH);
				}
			} else {
				if(count_query_door_bell == 1){
					count_query_door_bell = 0;
					showToast(getString(R.string.device_stauts_offline));
					mDBind.textVideoStat.setVisibility(View.GONE);
					dismissProgressDialog();
				}else{
					mHandler.sendEmptyMessageDelayed(MESSAGE_SECOND_QUERY_STATUS,3000l);
				}
			}

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

	}
	int nPTZCommand = -1;

	@Override
	public void switchFishEyes(boolean flag) {
		if(flag){
			mDBind.switchFishEye.setVisibility(View.VISIBLE);
		}else {
			mDBind.switchFishEye.setVisibility(View.GONE);
			mDBind.settingFishEye.setVisibility(View.GONE);
		}

	}

	@Override
	public View getScrollableView() {
		return mDBind.llCamera;
	}

	private class OnVideoViewTouchListener implements OnTouchListener {

		private float x1;
		private float y1;
		private float x2;
		private float y2;
		private long firClick = 0;
		private long secClick = 0;
		private boolean isPTZ = false;
		private final static int HandMoveSize = 8;


		@Override
		public boolean onTouch(View v, MotionEvent event) {
			int iAction = event.getAction();
			Log.i(TAG,"iAction"+iAction);
			if (iAction == MotionEvent.ACTION_DOWN) { // 按下
				isPTZ = false;
				x1 = event.getX();
				y1 = event.getY();
			} else if (iAction == MotionEvent.ACTION_UP || iAction == MotionEvent.ACTION_CANCEL) { // 弹起
				Log.i(TAG,"nPTZCommand"+nPTZCommand);
				Log.i(TAG,"isPTZ"+isPTZ);


				if (isPTZ) {

					isPTZ = false;
					onContrlPTZ1(nPTZCommand, !isPTZ);
				} else {
					secClick = System.currentTimeMillis();
					if (firClick > 0) {
						if (secClick - firClick < 500) {
							// double click

						}else{
							if (mDBind.layoutVideoControl.getVisibility() == View.VISIBLE) {
								hideVideoControlBar();
							} else {
								showVideoControlBar();
							}
						}
					}
					firClick = secClick;

					onContrlPTZ1(nPTZCommand, !isPTZ);
				}
			} else if(iAction == MotionEvent.ACTION_MOVE){
				if (!isPTZ) {
					x2 = event.getX();
					y2 = event.getY();
					if (x2 - x1 > HandMoveSize) {
						// right
						isPTZ = true;
						nPTZCommand = EPTZCMD.PAN_LEFT;
						mDBind.rightDirection.setVisibility(View.VISIBLE);
						mDBind.leftDirection.setVisibility(View.GONE);
						mDBind.upDirection.setVisibility(View.GONE);
						mDBind.downDirection.setVisibility(View.GONE);
						mHandler.sendEmptyMessageDelayed(MESSAGE_AUTO_HIDE_DIRECTION, 3000);
						onContrlPTZ1(nPTZCommand, !isPTZ);
					} else if (x1 - x2 > HandMoveSize) {
						// left
						isPTZ = true;
						nPTZCommand = EPTZCMD.PAN_RIGHT;
						mDBind.rightDirection.setVisibility(View.GONE);
						mDBind.leftDirection.setVisibility(View.VISIBLE);
						mDBind.upDirection.setVisibility(View.GONE);
						mDBind.downDirection.setVisibility(View.GONE);
						mHandler.sendEmptyMessageDelayed(MESSAGE_AUTO_HIDE_DIRECTION, 3000);
						onContrlPTZ1(nPTZCommand, !isPTZ);
					} else if (y2 - y1 > HandMoveSize) {
						// down
						isPTZ = true;
						nPTZCommand = EPTZCMD.TILT_UP;
						mDBind.leftDirection.setVisibility(View.GONE);
						mDBind.rightDirection.setVisibility(View.GONE);
						mDBind.downDirection.setVisibility(View.VISIBLE);
						mDBind.upDirection.setVisibility(View.GONE);
						mHandler.sendEmptyMessageDelayed(MESSAGE_AUTO_HIDE_DIRECTION, 3000);
						onContrlPTZ1(nPTZCommand, !isPTZ);
					} else if (y1 - y2 > HandMoveSize) {
						// up
						isPTZ = true;
						nPTZCommand = EPTZCMD.TILT_DOWN;
						mDBind.leftDirection.setVisibility(View.GONE);
						mDBind.rightDirection.setVisibility(View.GONE);
						mDBind.downDirection.setVisibility(View.GONE);
						mDBind.upDirection.setVisibility(View.VISIBLE);
						mHandler.sendEmptyMessageDelayed(MESSAGE_AUTO_HIDE_DIRECTION, 3000);
						onContrlPTZ1(nPTZCommand, !isPTZ);
					}
				}
				if (!isPTZ) {
					x1 = event.getX();
					y1 = event.getY();
				}

			}
			return false;
		}


	}

	private void loginDevice() {
		mDBind.textVideoStat.setText(R.string.opening);
		mDBind.textVideoStat.setVisibility(View.VISIBLE);
		FunSupport.getInstance().requestDeviceLogin(mFunDevice);
	}

	private void requestSystemInfo() {
		mDBind.textVideoStat.setText(R.string.opening);
		mDBind.textVideoStat.setVisibility(View.VISIBLE);
		FunSupport.getInstance().requestDeviceConfig(mFunDevice, SystemInfo.CONFIG_NAME);
		FunSupport.getInstance().requestDeviceConfig(mFunDevice, SimplifyEncode.CONFIG_NAME);
	}


	private void startPictureList() {
		Intent intent = new Intent();
		intent.putExtra("FUN_DEVICE_ID", mFunDevice.getId());
		intent.putExtra("FILE_TYPE", "jpg");
		if (mFunDevice.devType == FunDevType.EE_DEV_NORMAL_MONITOR || mFunDevice.devType == FunDevType.EE_DEV_SMALLEYE) {
			intent.setClass(this, ActivityGuideDevicePictureList.class);
		}
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}

	private void startRecordList() {
		Intent intent = new Intent();
		intent.putExtra("FUN_DEVICE_ID", mFunDevice.getId());
		intent.putExtra("FILE_TYPE", "h264;mp4");
		intent.putExtra("FUN_DEVICE_NAME", deviceName);
		//intent.setClass(this, ActivityGuideDeviceRecordListNew.class);
		intent.setClass(this, ActivityGuideDeviceRecordListNew.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}

	private void playRealMedia() {

		if (mFunDevice.isRemote) {
			mDBind.funVideoView.setRealDevice(mFunDevice.getDevSn(), mFunDevice.CurrChannel);
		} else {
			String deviceIp = FunSupport.getInstance().getDeviceWifiManager().getGatewayIp();
			mDBind.funVideoView.setRealDevice(deviceIp, mFunDevice.CurrChannel);
		}

		// 打开声音
		mDBind.funVideoView.setMediaSound(true);

		// 设置当前播放的码流类型
		if (FunStreamType.STREAM_SECONDARY == mDBind.funVideoView.getStreamType()) {
			mDBind.textStreamStat.setText(R.string.stream_sd);
			mDBind.btnStream.setText(R.string.stream_hd);
		} else {
			mDBind.textStreamStat.setText(R.string.stream_hd);
			mDBind.btnStream.setText(R.string.stream_sd);
		}
	}

	private void stopMedia() {
		if (null != mDBind.funVideoView) {
			mDBind.funVideoView.stopPlayback();
			mDBind.funVideoView.stopRecordVideo();
		}
	}


	private void switchMediaStream() {
		if (null != mDBind.funVideoView) {
			if (FunStreamType.STREAM_MAIN == mDBind.funVideoView.getStreamType()) {
				mDBind.funVideoView.setStreamType(FunStreamType.STREAM_SECONDARY);
			} else {
				mDBind.funVideoView.setStreamType(FunStreamType.STREAM_MAIN);
			}
			mDBind.funVideoView.stopPlayback();
			playRealMedia();
		}
	}

	// 设备登录
	private void requestDeviceStatus(CameraBean cameraBean) {
		showProgressDialog();
		mDBind.textVideoStat.setText(R.string.opening);
		mDBind.textVideoStat.setVisibility(View.VISIBLE);
		FunSupport.getInstance().requestDeviceStatus(FunDevType.EE_DEV_NORMAL_MONITOR, cameraBean.getDeviceId());
	}


	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MESSAGE_PLAY_MEDIA:
				playRealMedia();
				break;
			case MESSAGE_AUTO_HIDE_CONTROL_BAR:
				hideVideoControlBar();
				break;
            case MESSAGE_TOAST_SCREENSHOT_PREVIEW:
				String path = (String) msg.obj;
				toastScreenShotPreview(path);
				break;
            case MESSAGE_OPEN_VOICE:
				mDBind.funVideoView.setMediaSound(true);
			   	break;
			case MESSAGE_AUTO_CAPTURE:
			    autoToCapture();
			    break;
			case MESSAGE_DELAY_FINISH:
					// 启动/打开设备操作界面
				if (null != mFunDevice) {
					// 传入用户名/密码
					mFunDevice.loginName = "admin";
					mFunDevice.loginPsw = "";
					String devicePasswd = FunDevicePassword.getInstance().getDevicePassword(
							mCameraBean.getDeviceId());
					    if(TextUtils.isEmpty(devicePasswd)){
							FunSDK.DevSetLocalPwd(mFunDevice.getDevSn(), "admin", "");
						}
						// 如果支持云台控制，显示方向键和预置点按
					    mTalkManager = new TalkManager(mFunDevice);
						// 如果设备未登录,先登录设备
						if (!mFunDevice.hasLogin() || !mFunDevice.hasConnected()) {
							loginDevice();
						} else {
							requestSystemInfo();
						}
					}
				break;
				case MESSAGE_AUTO_HIDE_DIRECTION:
					hideDirection();
					break;
				case MESSAGE_SECOND_QUERY_STATUS:
					count_query_door_bell = 1;
					if(mCameraBean!=null)
					requestDeviceStatus(mCameraBean);
					break;
			}
		}
	};

	private OnTouchListener mIntercomTouchLs = new OnTouchListener() {

		@Override
		public boolean onTouch(View arg0, MotionEvent arg1) {
			try {
				if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
					startTalk();
				} else if (arg1.getAction() == MotionEvent.ACTION_UP) {
					stopTalk(500);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}
	};

	private void startTalk() {
		if (mTalkManager != null && mHandler != null && mDBind.funVideoView != null) {
			mDBind.funVideoView.setMediaSound(false);			//关闭本地音频

			mTalkManager.onStartTalk();
			mTalkManager.onStartThread();
            mTalkManager.setTalkSound(false);
		}
	}

	private void stopTalk(int delayTime) {
		if (mTalkManager != null && mHandler != null && mDBind.funVideoView != null) {
			mTalkManager.onStopThread();
            mTalkManager.setTalkSound(true);
		}
	}

	/**
	 * 显示输入设备密码对话框
	 */
	private void showInputPasswordDialog() {
		// 重新登录
		BaseEditDialog mEditDialog = new BaseEditDialog(mContext, new OnCallBackToEdit() {
			@Override
			public void onConfirm(String name) {
				if (null != mFunDevice) {
					NativeLoginPsw = name;
					onDeviceSaveNativePws();
					// 重新登录
					loginDevice();
				}
			}

			@Override
			public void onCancel() {

			}
		});
		mEditDialog.setTitleAndButton(getString(R.string.device_login_input_password), getString(R.string.cancel), getString(R.string.ok));
		mEditDialog.show();
	}

	public void onDeviceSaveNativePws() {
		FunDevicePassword.getInstance().saveDevicePassword(mFunDevice.getDevSn(),
				NativeLoginPsw);
		// 库函数方式本地保存密码
		if (FunSupport.getInstance().getSaveNativePassword()) {
			FunSDK.DevSetLocalPwd(mFunDevice.getDevSn(), "admin", NativeLoginPsw);
			// 如果设置了使用本地保存密码，则将密码保存到本地文件
		}
	}

	@Override
	public void onDeviceLoginSuccess(final FunDevice funDevice) {
		System.out.println("TTT---->>>> loginsuccess");
		dismissProgressDialog();
		if (null != mFunDevice && null != funDevice) {
			if (mFunDevice.getId() == funDevice.getId()) {
				requestSystemInfo();
			}
		}
	}

	@Override
	public void onDeviceLoginFailed(final FunDevice funDevice, final Integer errCode) {
		// 设备登录失败
		dismissProgressDialog();
		mDBind.textVideoStat.setVisibility(View.GONE);
		// 如果账号密码不正确,那么需要提示用户,输入密码重新登录
		if (errCode == FunError.EE_DVR_PASSWORD_NOT_VALID) {
			showInputPasswordDialog();
		}
	}

	@Override
	public void onDeviceGetConfigSuccess(final FunDevice funDevice, final String configName, final int nSeq) {
		if (SystemInfo.CONFIG_NAME.equals(configName)) {
			// 更新UI
			//此处为示例如何取通道信息，可能会增加打开视频的时间，可根据需求自行修改代码逻辑
			if (funDevice.channel == null) {
				FunSupport.getInstance().requestGetDevChnName(funDevice);
				requestSystemInfo();
				return;
			}
			dismissProgressDialog();
			mDBind.textVideoStat.setVisibility(View.GONE);

			// 设置允许播放标志
			mCanToPlay = true;

			// 获取信息成功后,如果WiFi连接了就自动播放
			// 此处逻辑客户自定义
			int result = NetWorkUtil.getNetWorkType(this);
			Log.i(TAG,"result="+result);
			if(result == 4){
				playRealMedia();
			}else if(result <4 && result > 0){
				BaseDialog mDialog = new BaseDialog(mContext, new OnCallBackToRefresh() {
					@Override
					public void onConfirm() {
						auto_play = true;
						playRealMedia();
					}

					@Override
					public void onCancel() {

					}
				});
				mDialog.setTitleAndButton(getString(R.string.gprs_hint), getString(R.string.cancel), getString(R.string.ok));
				mDialog.show();
			}else{
				showToast(getString(R.string.meida_not_auto_play_because_no_wifi));
			}

		} else if (SimplifyEncode.CONFIG_NAME.equals(configName)) {

			if(mFunDevice!=null){

				try {
					SimplifyEncode simpEnc = (SimplifyEncode) mFunDevice.getConfig(SimplifyEncode.CONFIG_NAME);
					if(FunStreamType.STREAM_SECONDARY ==mDBind.funVideoView.getStreamType()){
						if(simpEnc.extraFormat.AudioEnable){
							mDBind.btnStop.setImageResource(R.mipmap.icon_is_voice);
						}else{
							mDBind.btnStop.setImageResource(R.mipmap.icon_no_voice);
						}


					}else if(FunStreamType.STREAM_MAIN ==mDBind.funVideoView.getStreamType()){
						if(simpEnc.mainFormat.AudioEnable){
							mDBind.btnStop.setImageResource(R.mipmap.icon_is_voice);
						}else{
							mDBind.btnStop.setImageResource(R.mipmap.icon_no_voice);
						}
					}
				}catch (Exception e){
					e.printStackTrace();
				}

			}

		}
	}

	@Override
	public void onDeviceGetConfigFailed(final FunDevice funDevice, final Integer errCode) {
		//showToast(FunError.getErrorStr(errCode));
	}


	@Override
	public void onDeviceSetConfigSuccess(final FunDevice funDevice,
			final String configName) {
		if(SimplifyEncode.CONFIG_NAME.equals(configName)){
			if(mFunDevice!=null){
				SimplifyEncode simpEnc = (SimplifyEncode) mFunDevice.getConfig(SimplifyEncode.CONFIG_NAME);
				if(FunStreamType.STREAM_SECONDARY ==mDBind.funVideoView.getStreamType()){
					if(simpEnc.extraFormat.AudioEnable){
						mDBind.btnStop.setImageResource(R.mipmap.icon_is_voice);
					}else{
						mDBind.btnStop.setImageResource(R.mipmap.icon_no_voice);
					}


				}else if(FunStreamType.STREAM_MAIN ==mDBind.funVideoView.getStreamType()){
					if(simpEnc.mainFormat.AudioEnable){
						mDBind.btnStop.setImageResource(R.mipmap.icon_is_voice);
					}else{
						mDBind.btnStop.setImageResource(R.mipmap.icon_no_voice);
					}
				}
			}
		}
	}


	@Override
	public void onDeviceSetConfigFailed(final FunDevice funDevice,
			final String configName, final Integer errCode) {

	}

	@Override
	public void onDeviceChangeInfoSuccess(final FunDevice funDevice) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onDeviceChangeInfoFailed(final FunDevice funDevice, final Integer errCode) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onDeviceOptionSuccess(final FunDevice funDevice, final String option) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onDeviceOptionFailed(final FunDevice funDevice, final String option, final Integer errCode) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onDeviceFileListChanged(FunDevice funDevice) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDeviceFileListChanged(FunDevice funDevice, H264_DVR_FILE_DATA[] datas) {

	}


	@Override
	public void onPrepared(MediaPlayer arg0) {
		// TODO Auto-generated method stub


	}


	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		// 播放失败
		showToast(getResources().getString(R.string.media_play_error)
				+ " : "
				+ FunError.getErrorStr(extra));

		if ( FunError.EE_TPS_NOT_SUP_MAIN == extra
				|| FunError.EE_DSS_NOT_SUP_MAIN == extra ) {
			// 不支持高清码流,设置为标清码流重新播放
			if (null != mDBind.funVideoView) {
				mDBind.funVideoView.setStreamType(FunStreamType.STREAM_SECONDARY);
				playRealMedia();
			}
		}

		return true;
	}


	@Override
	public boolean onInfo(MediaPlayer arg0, int what, int extra) {
		Log.i(TAG,"onInfo:what="+what);
		dismissProgressDialog();
		if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START) {
			mDBind.textVideoStat.setText(R.string.media_player_buffering);
			mDBind.textVideoStat.setVisibility(View.VISIBLE);
		} else if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END) {
			mDBind.textVideoStat.setVisibility(View.GONE);
		}
		return true;
	}

	private void onContrlPTZ1(int nPTZCommand, boolean bStop) {
		try{
			FunSupport.getInstance().requestDevicePTZControl(mFunDevice, nPTZCommand, bStop, mFunDevice.CurrChannel);
		}catch (NullPointerException e){
			e.printStackTrace();
		}
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		mFunDevice.CurrChannel = arg1;
		System.out.println("TTT----"+mFunDevice.CurrChannel);
		if (mCanToPlay) {
			playRealMedia();
		}
	}


	@Override
	public void onDeviceFileListGetFailed(FunDevice funDevice) {
		// TODO Auto-generated method stub

	}


    private void initAutoCapture(){

		new Thread(){
			@Override
			public void run() {
				while (auto_flag){
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if(auto_flag)
					mHandler.sendEmptyMessage(MESSAGE_AUTO_CAPTURE);
				}
			}
		}.start();

	}


	private void initPermission() {
		PackageManager pkgManager = getPackageManager();
		boolean maikePermission =
				pkgManager.checkPermission(Manifest.permission.RECORD_AUDIO, getPackageName()) == PackageManager.PERMISSION_GRANTED;
		if ( !maikePermission ) {
			requestPermission();
		}
	}

	private void requestPermission() {
		ActivityCompat.requestPermissions(this, new String[] {android.Manifest.permission.RECORD_AUDIO},
				REQUEST_PERMISSION);
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		if (requestCode == REQUEST_PERMISSION) {
		} else {
			super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		}

	}

	@Override
	protected void onDestroy() {
		stopMedia();
		FunSupport.getInstance().removeOnFunDeviceOptListener(this);
		FunSupport.getInstance().removeOnFunDeviceListener(this);

		if (null != mHandler) {
			mHandler.removeCallbacksAndMessages(null);
			mHandler = null;
		}
		auto_flag = false;
		super.onDestroy();
	}

}
