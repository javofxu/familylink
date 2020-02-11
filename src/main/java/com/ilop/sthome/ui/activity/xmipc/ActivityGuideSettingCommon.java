package com.ilop.sthome.ui.activity.xmipc;

import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.aliyun.iot.aep.sdk.apiclient.callback.IoTResponse;
import com.aliyun.iot.aep.sdk.credential.IotCredentialManager.IoTCredentialListener;
import com.aliyun.iot.aep.sdk.credential.IotCredentialManager.IoTCredentialManageError;
import com.aliyun.iot.aep.sdk.credential.IotCredentialManager.IoTCredentialManageImpl;
import com.aliyun.iot.aep.sdk.credential.data.IoTCredentialData;
import com.aliyun.iot.aep.sdk.login.LoginBusiness;
import com.example.common.base.BaseActivity;
import com.example.common.base.OnCallBackToEdit;
import com.example.common.base.OnCallBackToRefresh;
import com.example.xmpic.support.FunError;
import com.example.xmpic.support.FunSupport;
import com.example.xmpic.support.OnFunDeviceOptListener;
import com.example.xmpic.support.config.CameraParam;
import com.example.xmpic.support.config.OPTimeQuery;
import com.example.xmpic.support.config.OPTimeSetting;
import com.example.xmpic.support.models.FunDevice;
import com.example.xmpic.support.models.FunLoginType;
import com.example.xmpic.support.utils.DeviceConfigType;
import com.ilop.sthome.data.greenDao.CameraBean;
import com.ilop.sthome.data.greenDao.CameraBeanDao;
import com.ilop.sthome.mvp.contract.CameraContract;
import com.ilop.sthome.mvp.model.CameraModel;
import com.ilop.sthome.mvp.model.common.onModelCallBack;
import com.ilop.sthome.ui.dialog.BaseDialog;
import com.ilop.sthome.ui.dialog.BaseEditDialog;
import com.ilop.sthome.utils.greenDao.CameraDaoUtil;
import com.lib.sdk.struct.H264_DVR_FILE_DATA;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivityPicSettingCommonBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Administrator on 2017/8/30.
 */

public class ActivityGuideSettingCommon extends BaseActivity<ActivityPicSettingCommonBinding> implements View.OnClickListener,OnFunDeviceOptListener {
    private final static String TAG = ActivityGuideSettingCommon.class.getName();
    private int id;
    private String deviceId;
    private FunDevice mFunDevice;
    private BaseEditDialog mEditDialog;
    private BaseDialog mDialog;
    private List<CameraBean> mCameraBeanList;
    private CameraBean mCameraBean;
    private Handler mHandler = new Handler();
    /**
     * 本界面需要获取到的设备配置信息（监控类）
     */
    private final String[] DEV_CONFIGS_FOR_CAMERA = { CameraParam.CONFIG_NAME };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pic_setting_common;
    }

    @Override
    protected void initialize() {
        super.initialize();
        immersionStatusBar(true);
        id = getIntent().getIntExtra("FUN_DEVICE_ID", 0);
        deviceId = getIntent().getStringExtra("FUN_DEVICE_SN");
    }

    @Override
    public void initView(){
        mFunDevice = FunSupport.getInstance().findDeviceById(id);
        if ( null == mFunDevice ) {
            finish();
            return;
        }
        // 注册设备操作监听
        FunSupport.getInstance().registerOnFunDeviceOptListener(this);
        requestTimeInfo();
        // 获取报警配置信息
        tryGetCameraConfig();
    }

    @Override
    protected void initData() {
        super.initData();
        mCameraBeanList = CameraDaoUtil.getInstance().getCameraDao().queryByQueryBuilder(CameraBeanDao.Properties.DeviceId.eq(deviceId));
        if (mCameraBeanList.size()>0){
            mCameraBean = mCameraBeanList.get(0);
            mDBind.name.setText(mCameraBean.getDeviceName());
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDBind.updatename.setOnClickListener(this);
        mDBind.updatetime.setOnClickListener(this);
        mDBind.btnSwitchCameraFlip.setOnClickListener(this);
        mDBind.btnSwitchCameraMirror.setOnClickListener(this);
        mDBind.ivCommonSettingBack.setOnClickListener(this);
    }

    private void tryGetCameraConfig() {
        if (null != mFunDevice) {
            showProgressDialog();
            for (String configName : DEV_CONFIGS_FOR_CAMERA) {
                // 删除老的配置信息
                mFunDevice.invalidConfig(configName);
                //根据是否需要传通道号 重新搜索新的配置信息
                if (contains(DeviceConfigType.DeviceConfigCommon, configName)) {
                    FunSupport.getInstance().requestDeviceConfig(mFunDevice, configName);
                }else if (contains(DeviceConfigType.DeviceConfigByChannel, configName)) {
                    FunSupport.getInstance().requestDeviceConfig(mFunDevice, configName, mFunDevice.CurrChannel);
                }

            }
        }
    }

    public static boolean contains(String[] stringArray, String source) {
        // 转换为list
        List<String> tempList = Arrays.asList(stringArray);

        // 利用list的包含方法,进行判断
        return tempList.contains(source);
    }

    /**
     * 判断是否所有需要的配置都获取到了
     *
     * @return
     */
    private boolean isAllConfigGetted() {
        for (String configName : DEV_CONFIGS_FOR_CAMERA) {
            if (null == mFunDevice.getConfig(configName)) {
                return false;
            }
        }
        return true;
    }

    private boolean isCurrentUsefulConfig(String configName) {
        for (int i = 0; i < DEV_CONFIGS_FOR_CAMERA.length; i++) {
            if (DEV_CONFIGS_FOR_CAMERA[i].equals(configName)) {
                return true;
            }
        }
        return false;
    }

    private void refreshCameraConfig() {
        CameraParam cameraParam = (CameraParam) mFunDevice.getConfig(CameraParam.CONFIG_NAME);
        if (null != cameraParam) {
            // 图像上下翻转
            mDBind.btnSwitchCameraFlip.setSelected(cameraParam.getPictureFlip());
            // 图像左右翻转
            mDBind.btnSwitchCameraMirror.setSelected(cameraParam.getPictureMirror());
        }
    }


    @Override
    public void onClick(View view) {
         switch (view.getId()){
             case R.id.iv_common_setting_back:
                 finish();
                 break;
             case R.id.updatename:
                 mEditDialog = new BaseEditDialog(mContext, new OnCallBackToEdit() {
                     @Override
                     public void onConfirm(String name) {
                         if(!TextUtils.isEmpty(name)){
                             if(name.length() <= 10){
                                 refreshModifyUserInfo(name);
                             }else{
                                 showToast(getString(R.string.name_is_too_long));
                             }
                         } else{
                             showToast(getString(R.string.name_is_null));
                         }
                     }
                     @Override
                     public void onCancel() {
                     }
                 });
                 mEditDialog.setTitleAndButton(getString(R.string.update_name), getString(R.string.cancel), getString(R.string.ok));
                 mEditDialog.show();
                 break;
             case R.id.updatetime:
                 mDialog = new BaseDialog(mContext, new OnCallBackToRefresh() {
                     @Override
                     public void onConfirm() {
                         if(FunSupport.getInstance().getLoginType()== FunLoginType.LOGIN_BY_AP||
                                 FunSupport.getInstance().getLoginType()== FunLoginType.LOGIN_BY_LOCAL){
                             Calendar cal = Calendar.getInstance(Locale.getDefault());
                             String sysTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(cal.getTime());
                             OPTimeSetting devtimeInfo = (OPTimeSetting)mFunDevice.checkConfig(OPTimeSetting.CONFIG_NAME);
                             devtimeInfo.setmSysTime(sysTime);
                             showProgressDialog();
                             FunSupport.getInstance().requestDeviceSetConfig(mFunDevice, devtimeInfo);
                         }else{
                             showToast(getString(R.string.device_time_in_AP_or_local_mode));
                         }
                     }

                     @Override
                     public void onCancel() {

                     }
                 });
                 mDialog.setTitleAndButton(getString(R.string.sync_ipc_time), getString(R.string.cancel), getString(R.string.ok));
                 mDialog.show();
                 break;
             case R.id.btnSwitchCameraFlip:
                 CameraParam cameraParam = (CameraParam) mFunDevice.getConfig(CameraParam.CONFIG_NAME);
                 if (null != cameraParam) {
                     showProgressDialog();
                     // 图像上下翻转
                     cameraParam.setPictureFlip(!mDBind.btnSwitchCameraFlip.isSelected());
                     FunSupport.getInstance().requestDeviceSetConfig(mFunDevice, cameraParam);
                 }
                 break;
             case R.id.btnSwitchCameraMirror:
                 CameraParam cameraParam2 = (CameraParam) mFunDevice.getConfig(CameraParam.CONFIG_NAME);
                 if (null != cameraParam2) {
                     showProgressDialog();
                     // 图像上下翻转
                     cameraParam2.setPictureMirror(!mDBind.btnSwitchCameraMirror.isSelected());
                     FunSupport.getInstance().requestDeviceSetConfig(mFunDevice, cameraParam2);
                 }
                 break;

         }
    }

    /**
     * 请求获取系统信息:SystemInfo/Status.NatInfo
     */
    private void requestTimeInfo() {
        // 获取时间
        FunSupport.getInstance().requestDeviceCmdGeneral(mFunDevice, new OPTimeQuery());
    }


    private void refreshSystemInfo() {
        if ( null != mFunDevice ) {

            OPTimeQuery showDevtimeQuery = (OPTimeQuery) mFunDevice
                    .getConfig(OPTimeQuery.CONFIG_NAME);
            if (null != showDevtimeQuery) {
                String mOPTimeQuery = showDevtimeQuery.getOPTimeQuery();
                mDBind.time.setText(mOPTimeQuery);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date;
                try {
                    date = sdf.parse(mOPTimeQuery);
                    mDBind.time.setDevSysTime(date.getTime());
                    mDBind.time.onStartTimer();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @Override
    public void onDeviceLoginSuccess(FunDevice funDevice) {

    }

    @Override
    public void onDeviceLoginFailed(FunDevice funDevice, Integer errCode) {

    }

    @Override
    public void onDeviceGetConfigSuccess(FunDevice funDevice, String configName, int nSeq) {
        if ( null != mFunDevice && funDevice.getId() == mFunDevice.getId()) {
            if(OPTimeQuery.CONFIG_NAME.equals(configName)){
                dismissProgressDialog();
                refreshSystemInfo();
            }else if(isCurrentUsefulConfig(configName)){
                if (isAllConfigGetted()) {
                    dismissProgressDialog();
                }
                refreshCameraConfig();
            }
        }

    }

    @Override
    public void onDeviceGetConfigFailed(FunDevice funDevice, Integer errCode) {
        dismissProgressDialog();
        showToast(FunError.getErrorStr(errCode));
    }

    @Override
    public void onDeviceSetConfigSuccess(FunDevice funDevice, String configName) {
            if (null != mFunDevice && funDevice.getId() == mFunDevice.getId()) {
                if ( OPTimeSetting.CONFIG_NAME.equals(configName) ) {

                    // 重新获取时间
                    FunSupport.getInstance().requestDeviceCmdGeneral(
                            mFunDevice, new OPTimeQuery());
                }else{

                dismissProgressDialog();
                refreshCameraConfig();
              }

        }
    }

    @Override
    public void onDeviceSetConfigFailed(FunDevice funDevice, String configName, Integer errCode) {
        dismissProgressDialog();
        Toast.makeText(this, FunError.getErrorStr(errCode),Toast.LENGTH_LONG).show();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FunSupport.getInstance().removeOnFunDeviceOptListener(this);
    }


    private void refreshModifyUserInfo(String name){
        showProgressDialog();
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
                    modifyUserInfo(name);
                }

                @Override
                public void onRefreshIoTCredentialFailed(IoTCredentialManageError ioTCredentialManageError) {
                    dismissProgressDialog();
                }
            });
        } else {
            modifyUserInfo(name);
        }
    }

    private void modifyUserInfo(String name) {
        CameraContract.IModel mModel = new CameraModel();
        mModel.toUpdateCamera(mCameraBean.getUserId(), name, new onModelCallBack() {
            @Override
            public void onFailure(Exception e) {
                mHandler.post(()->{
                    showToast(e.getMessage());
                    dismissProgressDialog();
                });
            }

            @Override
            public void onResponse(IoTResponse response) {
                mHandler.post(()->{
                    dismissProgressDialog();
                    if (response.getCode() == 200){
                        mDBind.name.setText(name);
                        showToast(getString(R.string.success));
                        mCameraBean.setDeviceName(name);
                        CameraDaoUtil.getInstance().getCameraDao().update(mCameraBean);
                    }else {
                        showToast(response.getLocalizedMsg());
                    }
                });
            }
        });
    }
}
