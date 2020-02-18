package com.ilop.sthome.ui.activity.mine;

import com.example.common.base.BasePActivity;
import com.ilop.sthome.mvp.contract.SetUpContract;
import com.ilop.sthome.mvp.present.SetUpPresenter;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivitySetUpBinding;

import java.util.Locale;

/**
 * @author skygge
 * @date 2020-01-07.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：系统设置界面
 */
public class SetUpActivity extends BasePActivity<SetUpPresenter, ActivitySetUpBinding> implements SetUpContract.IView {

    private String mLanguage;

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
    protected void initListener() {
        super.initListener();
        mDBind.ivSetUpBack.setOnClickListener(view -> finish());

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
    public void showToastMsg(String msg) {
        showToast(msg);
    }

}
