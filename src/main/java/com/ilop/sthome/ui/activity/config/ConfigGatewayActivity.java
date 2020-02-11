package com.ilop.sthome.ui.activity.config;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;

import com.example.common.base.BaseActivity;
import com.example.common.utils.InputFilterUtil;
import com.example.common.utils.LiveDataBus;
import com.example.common.utils.SpUtil;
import com.ilop.sthome.data.greenDao.RoomBean;
import com.ilop.sthome.ui.adapter.room.RoomChooseAdapter;
import com.ilop.sthome.utils.greenDao.RoomDaoUtil;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivityConfigGatewayBinding;

import java.util.List;
import java.util.stream.Collectors;


/**
 * @author skygge
 * @date 2020-01-08.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：配置网关和房间信息
 */
public class ConfigGatewayActivity extends BaseActivity<ActivityConfigGatewayBinding> {

    private RoomChooseAdapter mRoomAdapter;
    private List<RoomBean> mRoomList;
    private List<String> mNameList;
    private String mRoomNames;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_config_gateway;
    }

    @Override
    protected void initView() {
        super.initView();
        InputFilterUtil.setEditTextInhibitInputSpaChat(mDBind.etGatewayName);
        InputFilterUtil.setEditTextInhibitInputSpaChat(mDBind.etRoomName);
        mRoomAdapter = new RoomChooseAdapter(mContext);
        mDBind.configRoomList.setLayoutManager(new LinearLayoutManager(mContext));
        mDBind.configRoomList.setAdapter(mRoomAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void initData() {
        super.initData();
        mRoomList = RoomDaoUtil.getInstance().getRoomDao().queryAll();
        Log.i(TAG, "getRoomList: "+ mRoomList.size());
        if (mRoomList.size()>0){
            mRoomAdapter.setRoomList(mRoomList);
        }
        mNameList = mRoomList.stream().map(RoomBean::getRoom_name).collect(Collectors.toList());

        LiveDataBus.get().with("choose_room", Integer.class).observe(this , integer -> {
            mRoomNames = mRoomList.get(integer).getRoom_name();
            mDBind.etRoomName.setText(mRoomNames);
            mRoomAdapter.setPosition(integer);
        });
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDBind.ivConfigBack.setOnClickListener(view -> finish());
        mDBind.etRoomName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().getBytes().length==15){
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

        mDBind.configGatewayNext.setOnClickListener(view -> {
            String mGatewayName = mDBind.etGatewayName.getText().toString();
            String mRoomName = mDBind.etRoomName.getText().toString();
            if (TextUtils.isEmpty(mGatewayName)){
                showToast(getString(R.string.ali_rename_gateway_name));
            }else if (TextUtils.isEmpty(mRoomName)){
                showToast(getString(R.string.ali_rename_room_name));
            }else {
                SpUtil.putString(mContext, "gateway", mGatewayName);
                SpUtil.putString(mContext, "room", mRoomName);
                skipAnotherActivity(ConfigGuideActivity.class);
            }
        });
    }
}
