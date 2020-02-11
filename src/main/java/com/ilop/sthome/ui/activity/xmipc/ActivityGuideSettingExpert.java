package com.ilop.sthome.ui.activity.xmipc;

import android.content.Intent;
import android.view.View;

import com.example.common.base.BaseActivity;
import com.example.xmpic.support.FunSupport;
import com.example.xmpic.support.models.FunDevice;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivityPicSettingExpertBinding;

/**
 * Created by 许格 on 2019/11/21.
 */

public class ActivityGuideSettingExpert extends BaseActivity<ActivityPicSettingExpertBinding> implements View.OnClickListener{
    private int id;
    private FunDevice mFunDevice;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pic_setting_expert;
    }

    @Override
    protected void initialize() {
        super.initialize();
        immersionStatusBar(true);
        id = getIntent().getIntExtra("FUN_DEVICE_ID", 0);
        mFunDevice = FunSupport.getInstance().findDeviceById(id);
        if ( null == mFunDevice ) {
            finish();
            return;
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDBind.recordsetting.setOnClickListener(this);
        mDBind.ivExpertBack.setOnClickListener(view -> finish());
    }

    @Override
    public void onClick(View view) {
         switch (view.getId()){
             case R.id.recordsetting:
                 Intent i  = new Intent(this,ActivityGuideDeviceSetupRecord.class);
                 i.putExtra("FUN_DEVICE_ID",id);
                 startActivity(i);
                 break;
         }
    }
}
