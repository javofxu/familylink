package com.ilop.sthome.ui.activity.xmipc;

import android.view.View;

import com.example.common.base.BaseActivity;
import com.ilop.sthome.utils.NetWorkUtil;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivityIpcBeforeWifiConfigBinding;

/**
 * Created by 许格 on 2019/11/15.
 */

public class ActivityGuideDeviceBeforeWifiConfig extends BaseActivity<ActivityIpcBeforeWifiConfigBinding> implements View.OnClickListener{

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ipc_before_wifi_config;
    }


    @Override
    protected void initialize() {
        super.initialize();
        immersionStatusBar(true);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDBind.ivAddMyIpcBack.setOnClickListener(view -> finish());
        mDBind.addMyPicNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add_my_pic_next:
                if(NetWorkUtil.getNetWorkType(this)<4){
                    showToast(getString(R.string.no_wifi));
                }else{
                    skipAnotherActivity(ActivityGuideDeviceWifiConfigNew.class);
                }
                break;
        }
    }
}
