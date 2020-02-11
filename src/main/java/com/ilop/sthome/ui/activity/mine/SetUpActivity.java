package com.ilop.sthome.ui.activity.mine;

import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;

import com.example.common.base.BasePActivity;
import com.example.common.utils.LiveDataBus;
import com.example.common.utils.SpUtil;
import com.ilop.sthome.data.bean.AlarmNotice;
import com.ilop.sthome.mvp.contract.SetUpContract;
import com.ilop.sthome.mvp.present.SetUpPresenter;
import com.ilop.sthome.ui.adapter.main.SetUpAdapter;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivitySetUpBinding;

import java.util.List;
import java.util.Locale;

/**
 * @author skygge
 * @date 2020-01-07.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：系统设置界面
 */
public class SetUpActivity extends BasePActivity<SetUpPresenter, ActivitySetUpBinding> implements SetUpContract.IView {

    private String mIotId;
    private String mLanguage;
    private boolean mEnabled;
    private SetUpAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set_up;
    }

    @Override
    protected void initPresent() {
        super.initPresent();
        mPresent = new SetUpPresenter(mContext);
    }

    @Override
    protected void initialize() {
        super.initialize();
        Locale locale = getResources().getConfiguration().locale;
        mLanguage = locale.getLanguage();
    }

    @Override
    protected void initView() {
        super.initView();
        mAdapter = new SetUpAdapter(mContext);
        mDBind.rvNoticeList.setLayoutManager(new LinearLayoutManager(mContext));
        mDBind.rvNoticeList.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        mIotId = SpUtil.getString(mContext, "iotId");
        mPresent.getDeviceNoticeList(mIotId);
        LiveDataBus.get().with("alarm_notice", AlarmNotice.class).observe(this, alarmNotice -> {
            assert alarmNotice != null;
            mPresent.setDeviceNoticeEnabled(mIotId, alarmNotice.getEventId(), !alarmNotice.isNoticeEnabled());
            showProgressDialog();
        });
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDBind.ivSetUpBack.setOnClickListener(view -> finish());
        mDBind.setUpAll.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(mIotId)){
                mEnabled = SpUtil.getBoolean(mContext, "noticeEnabled", false);
                mPresent.setDeviceFullNoticeEnabled(mIotId, mEnabled);
                showProgressDialog();
            }else {
                showToast(getString(R.string.ali_device_empty));
            }
        });
        mDBind.setUpPwd.setOnClickListener(v -> {
            if("zh".equals(mLanguage)){
                mPresent.modifyPhonePassword();
            }else {
                mPresent.modifyEmailPassword();
            }
        });
        mDBind.setUpLogOff.setOnClickListener(v -> skipAnotherActivity(CancellationActivity.class));
    }

    @Override
    public void showNoticeList(List<AlarmNotice> noticeList) {
        mAdapter.setList(noticeList);
    }

    @Override
    public void withoutNotice() {
        mAdapter.setList(null);
    }

    @Override
    public void showFullNotice() {
        Log.i(TAG, "showFullNotice: AAA");
        SpUtil.putBoolean(mContext, "noticeEnabled", mEnabled);
        mDBind.setUpAll.setImageResource(mEnabled ? R.mipmap.btn_on_48 : R.mipmap.btn_off_48);
    }

    @Override
    public void showToastMsg(String msg) {
        showToast(msg);
    }

    @Override
    public void disProgressDialog() {
        dismissProgressDialog();
    }
}
