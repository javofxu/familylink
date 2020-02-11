package com.ilop.sthome.ui.activity.main;

import android.view.View;

import com.example.common.base.BaseActivity;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivityExplainBinding;

/**
 * Created by 许格 on 2019/10/25.
 */

public class ExplainActivity extends BaseActivity<ActivityExplainBinding> {

    private int mid;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_explain;
    }

    @Override
    protected void initialize() {
        super.initialize();
        mid = getIntent().getIntExtra("mid",129);
    }

    @Override
    protected void initData() {
        super.initData();
        switch (mid){
            case 129:
                mDBind.titleIns.setText( getResources().getString(R.string.pir_default_scene));
                mDBind.content.setText(getResources().getString(R.string.pir_default_scene_hint));
                break;
            case 130:
                mDBind.titleIns.setText( getResources().getString(R.string.door_default_scene));
                mDBind.content.setText(getResources().getString(R.string.door_default_scene_hint));
                break;
            case 131:
                mDBind.titleIns.setText( getResources().getString(R.string.old_man_default_scene));
                mDBind.content.setText(getResources().getString(R.string.old_man_default_scene_hint));
                break;
            case 666:
                mDBind.tvDefaultTitle.setText(getString(R.string.user_agreement));
                mDBind.titleIns.setVisibility(View.GONE);
                mDBind.content.setText(getString(R.string.server_intro));
                break;
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDBind.ivDefaultBack.setOnClickListener(view -> finish());
    }
}
