package com.ilop.sthome.ui.activity.config;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import com.example.common.base.BaseActivity;
import com.example.common.utils.LiveDataBus;
import com.example.common.utils.SpUtil;
import com.ilop.sthome.data.bean.DeviceInfoBean;
import com.ilop.sthome.data.db.DeviceAliDAO;
import com.ilop.sthome.data.enums.ProductGroup;
import com.ilop.sthome.data.greenDao.RoomBean;
import com.ilop.sthome.ui.adapter.device.GatewayListAdapter;
import com.ilop.sthome.ui.adapter.room.RoomChooseAdapter;
import com.ilop.sthome.utils.greenDao.RoomDaoUtil;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivityConfigDeviceBinding;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author skygge
 * @date 2019-10-28.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：配置设备和房间信息
 */

public class ConfigDeviceActivity extends BaseActivity<ActivityConfigDeviceBinding> {

    private DeviceAliDAO deviceAliDAO;
    private ProductGroup mInsGuide;
    private GatewayListAdapter mGatewayAdapter;
    private RoomChooseAdapter mRoomAdapter;
    private List<RoomBean> mRoomList;
    private List<DeviceInfoBean> mGatewayList;
    private List<String> mNameList;
    private String mDeviceNames;
    private String mRoomNames;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_config_device;
    }

    @Override
    protected void initialize() {
        super.initialize();
        mInsGuide = (ProductGroup)getIntent().getSerializableExtra("guide");
    }

    @Override
    protected void initView() {
        super.initView();
        deviceAliDAO = new DeviceAliDAO(this);
        mGatewayAdapter = new GatewayListAdapter(mContext);
        mRoomAdapter = new RoomChooseAdapter(mContext);
        mDBind.chooseGatewayList.setLayoutManager(new GridLayoutManager(mContext, 2));
        mDBind.chooseGatewayList.setAdapter(mGatewayAdapter);
        mDBind.chooseRoomList.setLayoutManager(new LinearLayoutManager(mContext));
        mDBind.chooseRoomList.setAdapter(mRoomAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void initData() {
        super.initData();
        getGatewayList();
        getRoomList();
        LiveDataBus.get().with("choose_gateway",Integer.class).observe(this, integer -> {
            mDeviceNames = mGatewayList.get(integer).getDeviceName();
            mGatewayAdapter.setPosition(integer);
        });

        LiveDataBus.get().with("choose_room", Integer.class).observe(this ,integer -> {
            mRoomNames = mRoomList.get(integer).getRoom_name();
            mDBind.etRoomName.setText(mRoomNames);
            mRoomAdapter.setPosition(integer);
        });
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDBind.ivGatewayBack.setOnClickListener(v -> finish());
        mDBind.ivAddGateway.setOnClickListener(view -> {
            skipAnotherActivity(ConfigGatewayActivity.class);
            finish();
        });
        mDBind.etRoomName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().getBytes().length>15){
                    showToast(getString(R.string.name_is_too_long));
                }
                if (!TextUtils.equals(charSequence.toString(), mRoomNames)){
                    mRoomAdapter.setPosition(-1);
                }
                for (int j = 0; j < mNameList.size(); j++) {
                    if (TextUtils.equals(charSequence.toString(), mNameList.get(j))){
                        mRoomAdapter.setPosition(j);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mDBind.configDeviceNext.setOnClickListener(view -> {
            String mDeviceName = mDBind.etDeviceName.getText().toString();
            String mRoomName = mDBind.etRoomName.getText().toString();
            if (TextUtils.isEmpty(mDeviceName)){
                showToast(getString(R.string.ali_rename_device_name));
            }else if(TextUtils.isEmpty(mRoomName)){
                showToast(getString(R.string.ali_rename_room_name));
            }else if (TextUtils.isEmpty(mDeviceNames)){
                showToast(getString(R.string.please_choose_device));
            }else {
                try {
                    if(mDeviceName.getBytes("GBK").length<=15){
                        SpUtil.putString(mContext, "device", mDeviceName);
                        SpUtil.putString(mContext, "room", mRoomName);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("guide", mInsGuide);
                        bundle.putString("deviceName", mDeviceNames);
                        skipAnotherActivity(bundle, AddDeviceGuideActivity.class);
                        finish();
                    }else{
                        showToast(getString(R.string.name_is_too_long));
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private void getGatewayList(){
        mGatewayList = deviceAliDAO.findAllGateway();
        if (mGatewayList.size()>0){
            mGatewayAdapter.setList(mGatewayList);
        }else {
            mDBind.slHasGateway.setVisibility(View.GONE);
            mDBind.llNoGateway.setVisibility(View.VISIBLE);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getRoomList(){
        mRoomList = RoomDaoUtil.getInstance().getRoomDao().queryAll();
        Log.i(TAG, "getRoomList: "+ mRoomList.size());
        if (mRoomList.size()>0){
            mRoomAdapter.setRoomList(mRoomList);
        }
        mNameList = mRoomList.stream().map(RoomBean::getRoom_name).collect(Collectors.toList());
    }
}
