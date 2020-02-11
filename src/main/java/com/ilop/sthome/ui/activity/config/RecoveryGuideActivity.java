package com.ilop.sthome.ui.activity.config;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;

import com.example.common.base.BaseActivity;
import com.ilop.sthome.ui.activity.main.MainActivity;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivityRecoveryGuideBinding;


/**
 * @author skygge
 * @date 2020-01-08.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：网关初始化指导
 */
public class RecoveryGuideActivity extends BaseActivity<ActivityRecoveryGuideBinding>{

    private AnimationDrawable mAnimation;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recovery_guide;
    }

    @Override
    protected void initialize() {
        super.initialize();
        immersionStatusBar(true);
    }

    @Override
    protected void initView() {
        super.initView();
        mDBind.imageView1.setBackgroundResource(R.drawable.reset_animation);
        mAnimation = (AnimationDrawable) mDBind.imageView1.getBackground();
    }

    @Override
    protected void initData() {
        super.initData();
        mDBind.imageView1.post(() -> mAnimation.start());
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDBind.ivInitialzationBack.setOnClickListener(view -> finish());
        mDBind.next.setOnClickListener(view -> {
            Intent intent = new Intent(RecoveryGuideActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });
    }
}
