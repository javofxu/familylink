package com.ilop.sthome.mvp.present;

import android.content.Context;
import android.os.Handler;

import com.alibaba.sdk.android.openaccount.OpenAccountSDK;
import com.alibaba.sdk.android.openaccount.callback.LoginCallback;
import com.alibaba.sdk.android.openaccount.model.OpenAccountSession;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIService;
import com.alibaba.sdk.android.openaccount.ui.callback.EmailResetPasswordCallback;
import com.example.common.mvp.BasePresenterImpl;
import com.ilop.sthome.mvp.contract.SetUpContract;
import com.ilop.sthome.mvp.model.CommonModel;
import com.ilop.sthome.mvp.model.common.CommonModelImpl;
import com.ilop.sthome.ui.activity.login.EmailResetAliActivity;
import com.ilop.sthome.ui.activity.login.PhoneResetAliActivity;
import com.siterwell.familywellplus.R;

/**
 * @Author skygge.
 * @Date on 2020-02-06.
 * @Dec:
 */
public class SetUpPresenter extends BasePresenterImpl<SetUpContract.IView> implements SetUpContract.IPresent {

    private Context mContext;
    private Handler mHandler;
    private CommonModelImpl mModel;

    public SetUpPresenter(Context mContext) {
        this.mContext = mContext;
        mModel = new CommonModel();
        mHandler = new Handler();
    }

    @Override
    public void modifyPhonePassword() {
        OpenAccountUIService openAccountUIService = OpenAccountSDK.getService(OpenAccountUIService.class);
        openAccountUIService.showResetPassword(mContext, PhoneResetAliActivity.class, new LoginCallback() {
            @Override
            public void onSuccess(OpenAccountSession openAccountSession) {
                mView.showToastMsg(mContext.getString(R.string.success));
            }

            @Override
            public void onFailure(int i, String s) {
            }
        });
    }

    @Override
    public void modifyEmailPassword() {
        OpenAccountUIService openAccountUIService = OpenAccountSDK.getService(OpenAccountUIService.class);
        openAccountUIService.showEmailResetPassword(mContext, EmailResetAliActivity.class, new EmailResetPasswordCallback() {
            @Override
            public void onEmailSent(String s) {

            }

            @Override
            public void onSuccess(OpenAccountSession openAccountSession) {
                mView.showToastMsg(mContext.getString(R.string.success));
            }

            @Override
            public void onFailure(int i, String s) {
            }
        });
    }
}
