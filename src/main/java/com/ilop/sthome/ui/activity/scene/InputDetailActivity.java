package com.ilop.sthome.ui.activity.scene;

import com.example.common.base.BaseActivity;
import com.example.common.utils.LiveDataBus;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivityInputDetailBinding;

/**
 * @author skygge
 * @date 2020-02-15.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：点击执行/定时执行的详情
 */
public class InputDetailActivity extends BaseActivity<ActivityInputDetailBinding> {

    private String mDeviceType;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_input_detail;
    }

    @Override
    protected void initialize() {
        super.initialize();
        mDeviceType = getIntent().getStringExtra("deviceType");
    }

    @Override
    protected void initView() {
        super.initView();
        if ("CLICK".equals(mDeviceType)){
            mDBind.tvInputName.setText(getString(R.string.clickTo));
            mDBind.ivInputImg.setImageResource(R.mipmap.click_execution);
            mDBind.tvInputMsg.setText(getString(R.string.perform_action));
        }else {
            mDBind.tvInputName.setText(getString(R.string.phone));
            mDBind.ivInputImg.setImageResource(R.mipmap.cell_phone_notification);
            mDBind.tvInputMsg.setText(getString(R.string.push_message));
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDBind.ivBack.setOnClickListener(view -> finish());
        mDBind.btDeleteInput.setOnClickListener(view -> {
            LiveDataBus.get().with("delete_condition").setValue(mDeviceType);
            finish();
        });
    }
}
