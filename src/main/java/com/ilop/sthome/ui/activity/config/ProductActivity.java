package com.ilop.sthome.ui.activity.config;


import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;

import com.example.common.base.BaseActivity;
import com.example.common.utils.LiveDataBus;
import com.ilop.sthome.data.bean.ProductBean;
import com.ilop.sthome.data.device.DeviceTypeEnum;
import com.ilop.sthome.data.enums.ProductGroup;
import com.ilop.sthome.data.enums.SmartProduct;
import com.ilop.sthome.ui.activity.xmipc.ActivityGuideDeviceAdd;
import com.ilop.sthome.ui.adapter.config.ProductAdapter;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivityProductBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * @author skygge
 * @date 2020-01-01.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：所有产品列表
 */
public class ProductActivity extends BaseActivity<ActivityProductBinding> {

    private ProductAdapter mAdapter;
    private boolean isSubDevice;

    private List<ProductBean> mProductList;
    private List<String> mGatewayList;
    private List<String> mFireList;
    private List<String> mTheftList;
    private List<String> mEnvironmentalList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_product;
    }

    @Override
    protected void initialize() {
        super.initialize();
        immersionStatusBar(true);
        isSubDevice = getIntent().getBooleanExtra("isSubDevice", false);
    }

    @Override
    protected void initView() {
        super.initView();
        mProductList = new ArrayList<>();
        mGatewayList = new ArrayList<>();
        mFireList = new ArrayList<>();
        mTheftList = new ArrayList<>();
        mEnvironmentalList = new ArrayList<>();
        mAdapter = new ProductAdapter(mContext);
        mDBind.rvProduct.setLayoutManager(new LinearLayoutManager(mContext));
    }

    @Override
    protected void initData() {
        super.initData();
        for (ProductGroup guide : ProductGroup.values()) {
            String deviceType = guide.getDevType();
            if (guide.getDeviceType() == 0){
                mGatewayList.add(deviceType);
            }else if (guide.getDeviceType() == 1){
                mFireList.add(deviceType);
            }else if (guide.getDeviceType() == 2){
                mTheftList.add(deviceType);
            }else if (guide.getDeviceType() == 3){
                mEnvironmentalList.add(deviceType);
            }
        }
        for (int i = 0; i < 4; i++) {
            ProductBean productBean = new ProductBean();
            if (i == DeviceTypeEnum.SMART_DEVICE_GATEWAY){
                if (!isSubDevice) {
                    productBean.setList(mGatewayList);
                    mProductList.add(productBean);
                }
            }else if (i == DeviceTypeEnum.SMART_DEVICE_FIRE){
                productBean.setList(mFireList);
                mProductList.add(productBean);
            }else if (i == DeviceTypeEnum.SMART_DEVICE_THEFT){
                productBean.setList(mTheftList);
                mProductList.add(productBean);
            }else if (i == DeviceTypeEnum.SMART_DEVICE_ENVIRONMENT){
                productBean.setList(mEnvironmentalList);
                mProductList.add(productBean);
            }
        }
        mAdapter.setProduct(mProductList, isSubDevice);
        mDBind.rvProduct.setAdapter(mAdapter);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDBind.ivProductBack.setOnClickListener(v -> finish());
        LiveDataBus.get().with("product", String.class).observe(this, s -> {
            if(SmartProduct.EE_SIMULATE_IPC.getDevType().equals(s)){
                skipAnotherActivity(ActivityGuideDeviceAdd.class);
            }else if(SmartProduct.EE_SIMULATE_GATEWAY.getDevType().equals(s)){
                skipAnotherActivity(AddGatewayActivity.class);
            }else {
                Intent intent = new Intent(this, ConfigDeviceActivity.class);
                intent.putExtra("guide", ProductGroup.getType(s));
                startActivity(intent);
            }
        });
    }
}
