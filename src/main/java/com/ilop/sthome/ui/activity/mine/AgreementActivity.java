package com.ilop.sthome.ui.activity.mine;

import android.content.Intent;
import android.widget.CompoundButton;

import com.aliyun.iot.aep.sdk.login.ILogoutCallback;
import com.aliyun.iot.aep.sdk.login.LoginBusiness;
import com.aliyun.iot.push.PushManager;
import com.example.common.base.BaseActivity;
import com.example.common.base.OnCallBackToRefresh;
import com.example.common.utils.SpUtil;
import com.ilop.sthome.ui.activity.main.StartActivity;
import com.ilop.sthome.ui.dialog.BaseDialog;
import com.ilop.sthome.utils.greenDao.DeviceDaoUtil;
import com.ilop.sthome.utils.greenDao.SceneDaoUtil;
import com.ilop.sthome.utils.greenDao.UserInfoDaoUtil;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivityAgreementBinding;

/**
 * Created by 许格 on 2019/10/25.
 */

public class AgreementActivity extends BaseActivity<ActivityAgreementBinding> {

    private boolean isAgree;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_agreement;
    }


    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void initData() {
        super.initData();
        isAgree = SpUtil.getBoolean(mContext, "isAgree", false);
        mDBind.cbAgree.setChecked(isAgree);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDBind.ivDefaultBack.setOnClickListener(view -> finish());
        mDBind.cbAgree.setOnCheckedChangeListener((compoundButton, b) -> {
            if (!b){
                BaseDialog mDialog = new BaseDialog(mContext, new OnCallBackToRefresh() {
                    @Override
                    public void onConfirm() {
                        SpUtil.putBoolean(mContext, "isAgree", false);
                        PushManager.getInstance().unbindUser();
                        LoginBusiness.logout(new ILogoutCallback() {
                            @Override
                            public void onLogoutSuccess() {
                                UserInfoDaoUtil.getInstance().getUserInfoDao().deleteAll();
                                DeviceDaoUtil.getInstance().deleteAllGateway();
                                skipAnotherActivity(StartActivity.class);
                            }

                            @Override
                            public void onLogoutFailed(int code, String error) {
                                showToast(mContext.getString(R.string.failed));
                            }
                        });
                    }

                    @Override
                    public void onCancel() {
                        mDBind.cbAgree.setChecked(true);
                    }
                });
                mDialog.setTitleAndButton(getString(R.string.exit_agreement), getString(R.string.dialog_btn_cancel), getString(R.string.dialog_btn_confim));
                mDialog.show();
            }
        });
    }
}
