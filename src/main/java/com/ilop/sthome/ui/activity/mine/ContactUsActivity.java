package com.ilop.sthome.ui.activity.mine;

import com.example.common.base.BaseActivity;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivityContactUsBinding;

/**
 * @author skygge
 * @date 2020-02-19.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：联系我们
 */
public class ContactUsActivity extends BaseActivity<ActivityContactUsBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_contact_us;
    }

    @Override
    protected void initView() {
        super.initView();
        mDBind.contactEmailName.setText(getString(R.string.person_email)+ ":");
    }

    @Override
    protected void initData() {
        super.initData();
        String[] ad = getResources().getStringArray(R.array.lianxi);
        mDBind.contactTel.setText(ad[0]);
        mDBind.contactEmail.setText(ad[1]);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDBind.ivContactBack.setOnClickListener(view -> finish());
    }
}
