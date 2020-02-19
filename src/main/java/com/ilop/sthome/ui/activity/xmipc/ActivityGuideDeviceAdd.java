package com.ilop.sthome.ui.activity.xmipc;

import com.example.common.base.BaseActivity;
import com.ilop.sthome.utils.NetWorkUtil;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivityAddIpcBinding;

/**
 * Created by 许格 on 2019/11/15.
 */

public class ActivityGuideDeviceAdd extends BaseActivity<ActivityAddIpcBinding>{

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_ipc;
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDBind.ivAddCameraBack.setOnClickListener(view -> finish());
        mDBind.addNewIpc.setOnClickListener(view -> skipAnotherActivity(ActivityGuideDeviceBeforeWifiConfig.class));
        mDBind.addShareIpc.setOnClickListener(view -> skipAnotherActivity(ActivityGuideDeviceWifiConfig.class));
        mDBind.addLocalIpc.setOnClickListener(view -> {
            if(NetWorkUtil.getNetWorkType(this)<4){
                showToast(getString(R.string.no_wifi));
            }else{
                skipAnotherActivity(ActivityGuideDeviceListLan.class);
            }
        });
    }
}
