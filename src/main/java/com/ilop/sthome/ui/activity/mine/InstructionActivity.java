package com.ilop.sthome.ui.activity.mine;

import android.support.v7.widget.LinearLayoutManager;

import com.example.common.base.BaseActivity;
import com.ilop.sthome.data.enums.ProductGroup;
import com.ilop.sthome.ui.adapter.main.InstructionAdapter;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivityInstructionBinding;

import java.util.Arrays;
import java.util.List;


/**
 * @author skygge
 * @date 2020-01-16.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：产品说明书页面
 */
public class InstructionActivity extends BaseActivity<ActivityInstructionBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_instruction;
    }

    @Override
    protected void initView() {
        super.initView();
        List<ProductGroup> mList = Arrays.asList(ProductGroup.values());
        InstructionAdapter mAdapter = new InstructionAdapter(this, mList);
        mDBind.rvInstructionList.setLayoutManager(new LinearLayoutManager(mContext));
        mDBind.rvInstructionList.setAdapter(mAdapter);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDBind.ivInstructionBack.setOnClickListener(view -> finish());
    }
}
