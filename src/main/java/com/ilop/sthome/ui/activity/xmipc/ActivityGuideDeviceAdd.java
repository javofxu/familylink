package com.ilop.sthome.ui.activity.xmipc;

import android.view.View;

import com.example.common.base.BaseActivity;
import com.ilop.sthome.utils.NetWorkUtil;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivityAddIpcBinding;

/**
 * Created by 许格 on 2019/11/15.
 */

public class ActivityGuideDeviceAdd extends BaseActivity<ActivityAddIpcBinding> implements View.OnClickListener{

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_ipc;
    }

    @Override
    protected void initialize() {
        super.initialize();
        immersionStatusBar(true);
    }

    @Override
    protected void initView() {
        super.initView();
        mDBind.addLocalIpc.setOnClickListener(this);
        mDBind.addNewIpc.setOnClickListener(this);
        mDBind.addShareIpc.setOnClickListener(this);
        mDBind.ivAddCameraBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_add_camera_back:
                finish();
                break;
            case R.id.add_new_ipc:
                skipAnotherActivity(ActivityGuideDeviceBeforeWifiConfig.class);
                break;
            case R.id.add_share_ipc:
                skipAnotherActivity(ActivityGuideDeviceWifiConfig.class);
                break;
            case R.id.add_local_ipc:
                if(NetWorkUtil.getNetWorkType(this)<4){
                    showToast(getString(R.string.no_wifi));
                }else{
                    skipAnotherActivity(ActivityGuideDeviceListLan.class);
                }
                break;
            default:break;
        }
    }
}
