package com.ilop.sthome.ui.activity.xmipc;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.aliyun.iot.aep.sdk.apiclient.callback.IoTResponse;
import com.aliyun.iot.aep.sdk.credential.IotCredentialManager.IoTCredentialListener;
import com.aliyun.iot.aep.sdk.credential.IotCredentialManager.IoTCredentialManageError;
import com.aliyun.iot.aep.sdk.credential.IotCredentialManager.IoTCredentialManageImpl;
import com.aliyun.iot.aep.sdk.credential.data.IoTCredentialData;
import com.aliyun.iot.aep.sdk.login.LoginBusiness;
import com.example.common.base.BaseActivity;
import com.example.common.base.OnCallBackToRefresh;
import com.example.xmpic.support.FunSupport;
import com.example.xmpic.support.models.FunDevice;
import com.ilop.sthome.data.greenDao.CameraBean;
import com.ilop.sthome.data.greenDao.CameraBeanDao;
import com.ilop.sthome.mvp.contract.CameraContract;
import com.ilop.sthome.mvp.model.CameraModel;
import com.ilop.sthome.mvp.model.common.onModelCallBack;
import com.ilop.sthome.ui.dialog.BaseDialog;
import com.ilop.sthome.utils.greenDao.CameraDaoUtil;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivityPicSettingMainBinding;

import java.util.List;

/**
 * Created by Administrator on 2017/8/30.
 */

public class ActivityGuideSettingMain extends BaseActivity<ActivityPicSettingMainBinding> implements View.OnClickListener{
   private final static String TAG = ActivityGuideSettingMain.class.getName();
    private int id;
    private FunDevice mFunDevice;
    private String mDeviceSn;
    private BaseDialog mBaseDialog;
    private List<CameraBean> mList;
    private CameraBean mCameraBean;
    private CameraContract.IModel mModel;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pic_setting_main;
    }

    @Override
    protected void initialize() {
        super.initialize();
        immersionStatusBar(true);
        id = getIntent().getIntExtra("deviceId", 0);
        mDeviceSn = getIntent().getStringExtra("deviceSn");
        mFunDevice = FunSupport.getInstance().findDeviceById(id);
        if ( null == mFunDevice ) {
            finish();
        }
    }

    @Override
    protected void initView() {
        super.initView();
        mModel = new CameraModel();
        mDBind.ivSettingBack.setOnClickListener(this);
        mDBind.codesetting.setOnClickListener(this);
        mDBind.common.setOnClickListener(this);
        mDBind.expertsetting.setOnClickListener(this);
        mDBind.storage.setOnClickListener(this);
        mDBind.tongyong.setOnClickListener(this);
        mDBind.tuichu.setOnClickListener(view -> {
            mBaseDialog = new BaseDialog(mContext, new OnCallBackToRefresh() {
                @Override
                public void onConfirm() {
                    refreshModifyUserInfo();
                }

                @Override
                public void onCancel() {

                }
            });
            mBaseDialog.setTitleAndButton(getString(R.string.confirm_delete_monitor), getString(R.string.cancel), getString(R.string.ok));
            mBaseDialog.show();
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mList = CameraDaoUtil.getInstance().getCameraDao().queryByQueryBuilder(CameraBeanDao.Properties.DeviceId.eq(mDeviceSn));
        mCameraBean = mList.get(0);
        Log.i(TAG, "initData: "+mCameraBean.getUserId());
    }

    private void refreshModifyUserInfo(){
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
                    modifyUserInfo();
                }

                @Override
                public void onRefreshIoTCredentialFailed(IoTCredentialManageError ioTCredentialManageError) {
                    showToast(getString(R.string.failed));
                }
            });
        } else {
            modifyUserInfo();
        }
    }

    private void modifyUserInfo() {
        showProgressDialog();
        mModel.toDeleteCamera(mCameraBean.getUserId(), new onModelCallBack() {
            @Override
            public void onFailure(Exception e) {
                runOnUiThread(()->{
                    dismissProgressDialog();
                    showToast(getString(R.string.failed));
                });
            }

            @Override
            public void onResponse(IoTResponse response) {
                Log.i(TAG, "onResponseBBB: ");
                runOnUiThread(() -> {
                    if (response.getCode() == 200) {
                        showToast(getString(R.string.success));
                        CameraDaoUtil.getInstance().getCameraDao().delete(mCameraBean);
                        Intent intent = new Intent(ActivityGuideSettingMain.this, ActivityGuideDeviceCamera.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }else {
                        showToast(response.getLocalizedMsg());
                    }
                    dismissProgressDialog();
                });
            }
        });
    }

    @Override
    public void onClick(View view) {
         switch (view.getId()){
             case R.id.iv_setting_back:
                 finish();
                 break;
             case R.id.codesetting:
                 Intent h = new Intent(this, ActivityGuideDeviceChangePwd.class);
                 h.putExtra("FUN_DEVICE_ID",id);
                 startActivity(h);
                 break;
             case R.id.common:
                 Intent h2 = new Intent(this, ActivityGuideSettingCommon.class);
                 h2.putExtra("FUN_DEVICE_ID",id);
                 h2.putExtra("FUN_DEVICE_SN",mDeviceSn);
                 startActivity(h2);
                 break;
             case R.id.storage:
                 Intent h3 = new Intent(this,ActivityGuideDeviceSetupStorage.class);
                 h3.putExtra("FUN_DEVICE_ID",id);
                 startActivity(h3);
                 break;
             case R.id.expertsetting:
                 Intent j = new Intent(this,ActivityGuideSettingExpert.class);
                 j.putExtra("FUN_DEVICE_ID",id);
                 startActivity(j);
                 break;
             case R.id.tongyong:
                 Intent h4 = new Intent(this,ActivityGuideSetingInfo.class);
                 h4.putExtra("FUN_DEVICE_ID",id);
                 startActivity(h4);
                 break;
         }
    }
}
