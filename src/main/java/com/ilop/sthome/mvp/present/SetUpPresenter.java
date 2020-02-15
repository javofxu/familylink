package com.ilop.sthome.mvp.present;

import android.content.Context;
import android.os.Handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.sdk.android.openaccount.OpenAccountSDK;
import com.alibaba.sdk.android.openaccount.callback.LoginCallback;
import com.alibaba.sdk.android.openaccount.model.OpenAccountSession;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIService;
import com.alibaba.sdk.android.openaccount.ui.callback.EmailResetPasswordCallback;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTResponse;
import com.example.common.mvp.BasePresenterImpl;
import com.ilop.sthome.data.bean.AlarmNotice;
import com.ilop.sthome.mvp.contract.SetUpContract;
import com.ilop.sthome.mvp.model.SetUpModel;
import com.ilop.sthome.mvp.model.common.onModelCallBack;
import com.ilop.sthome.ui.activity.login.EmailResetAliActivity;
import com.ilop.sthome.ui.activity.login.PhoneResetAliActivity;
import com.siterwell.familywellplus.R;

import org.json.JSONArray;

import java.util.List;

/**
 * @Author skygge.
 * @Date on 2020-02-06.
 * @Dec:
 */
public class SetUpPresenter extends BasePresenterImpl<SetUpContract.IView> implements SetUpContract.IPresent {

    private Context mContext;
    private SetUpContract.IModel mModel;
    private Handler mHandler;

    public SetUpPresenter(Context mContext) {
        this.mContext = mContext;
        mModel = new SetUpModel();
        mHandler = new Handler();
    }

    @Override
    public void getDeviceNoticeList(String iotId) {
        mModel.getDeviceNoticeList(iotId, new onModelCallBack() {
            @Override
            public void onFailure(Exception e) {

            }

            @Override
            public void onResponse(IoTResponse response) {
                Object data = response.getData();
                if (data == null) {
                    return;
                }
                if (!(data instanceof JSONArray)) {
                    return;
                }
                mHandler.post(()->{
                    try {
                        List<AlarmNotice> mNoticeList = JSON.parseArray(data.toString(), AlarmNotice.class);
                        if (mNoticeList.size() > 0) {
                            mView.showNoticeList(mNoticeList);
                        }else {
                            mView.withoutNotice();
                        }
                        for (AlarmNotice notice: mNoticeList) {
                            if (notice.isNoticeEnabled()){
                                mView.showHasEnabledOpen(true);
                                return;
                            }else {
                                mView.showHasEnabledOpen(false);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        });
    }

    @Override
    public void setDeviceFullNoticeEnabled(String iotId, boolean noticeEnabled) {
        mModel.setDeviceNoticeReminder(iotId, noticeEnabled, new onModelCallBack() {
            @Override
            public void onFailure(Exception e) {
                mHandler.post(()-> mView.showToastMsg(mContext.getString(R.string.operation_failed)));
            }

            @Override
            public void onResponse(IoTResponse response) {
                int code = response.getCode();
                mHandler.post(()->{
                    if (code == 200){
                        getDeviceNoticeList(iotId);
                        mView.showToastMsg(mContext.getString(R.string.operation_success));
                    }else {
                        mView.showToastMsg(response.getMessage());
                    }
                });
            }
        });
    }

    @Override
    public void setDeviceNoticeEnabled(String iotId, String eventId, boolean noticeEnabled) {
        mModel.deviceNoticeSet(iotId, eventId, noticeEnabled, new onModelCallBack() {
            @Override
            public void onFailure(Exception e) {
                mHandler.post(()-> mView.showToastMsg(mContext.getString(R.string.operation_failed)));
            }

            @Override
            public void onResponse(IoTResponse response) {
                mHandler.post(()->{
                    if (response.getCode() ==200){
                        getDeviceNoticeList(iotId);
                        mView.showToastMsg(mContext.getString(R.string.operation_success));
                    }else {
                        mView.showToastMsg(response.getMessage());
                    }
                });
            }
        });
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
