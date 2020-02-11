package com.ilop.sthome.ui.activity.config;

import android.content.Intent;

import com.example.common.base.BaseActivity;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivitySetApBinding;

/**
 * Created by 许格 on 2019/10/19.
 */

public class SetApActivity extends BaseActivity<ActivitySetApBinding>{
    private String mSid;
    private String mPsw;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set_ap;
    }

    @Override
    protected void initialize() {
        super.initialize();
        immersionStatusBar(true);
        mSid = getIntent().getStringExtra("ssid");
        mPsw = getIntent().getStringExtra("psw");
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDBind.ivSetApBack.setOnClickListener(view -> finish());

        mDBind.btnConfirm.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, ConnectNetActivity.class);
            intent.putExtra("ssid", mSid);
            intent.putExtra("psw", mPsw);
            intent.putExtra("ap", true);
            startActivity(intent);
        });
    }
}
