package com.ilop.sthome.ui.activity.mine;

import android.os.Handler;

import com.aliyun.iot.aep.sdk.apiclient.callback.IoTResponse;
import com.example.common.base.BaseActivity;
import com.example.common.utils.RxTimerUtil;
import com.ilop.sthome.mvp.contract.PersonalContract;
import com.ilop.sthome.mvp.model.PersonalModel;
import com.ilop.sthome.mvp.model.common.onModelCallBack;
import com.ilop.sthome.ui.activity.main.StartActivity;
import com.ilop.sthome.ui.dialog.TipDialog;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivityCancellationBinding;

/**
 * @author skygge
 * @date 2020-02-06.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：账号注销
 */
public class CancellationActivity extends BaseActivity<ActivityCancellationBinding> {

    private PersonalContract.IModel mModel;
    private Handler mHandler;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cancellation;
    }

    @Override
    protected void initView() {
        super.initView();
        mModel = new PersonalModel();
        mHandler = new Handler();
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDBind.ivCancelBack.setOnClickListener(v -> finish());
        mDBind.cancelOk.setOnClickListener(v -> {
            TipDialog mDialog = new TipDialog(mContext, () -> {
                mModel.toCancellationAccount(new onModelCallBack() {
                    @Override
                    public void onFailure(Exception e) {
                        mHandler.post(()-> showToast(getString(R.string.operation_failed)));
                    }

                    @Override
                    public void onResponse(IoTResponse response) {
                        mHandler.post(()->{
                            dismissProgressDialog();
                            showToast(getString(R.string.operation_success));
                            skipAnotherActivity(StartActivity.class);
                            finish();
                        });
                    }
                });
                showProgressDialog();
            });
            mDialog.setMsg(getString(R.string.confirm_to_cancel));
            mDialog.show();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxTimerUtil.cancel();
    }
}
