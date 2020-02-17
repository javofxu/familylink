package com.ilop.sthome.ui.activity.scene;

import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.example.common.base.BasePActivity;
import com.example.common.utils.LiveDataBus;
import com.ilop.sthome.data.event.EventAnswerOK;
import com.ilop.sthome.data.greenDao.AutomationBean;
import com.ilop.sthome.data.greenDao.DeviceInfoBean;
import com.ilop.sthome.mvp.contract.AddSceneContract;
import com.ilop.sthome.mvp.present.AddScenePresenter;
import com.ilop.sthome.network.api.SendCommandAli;
import com.ilop.sthome.ui.activity.config.ConfigGatewayActivity;
import com.ilop.sthome.ui.adapter.device.GatewayListAdapter;
import com.ilop.sthome.ui.adapter.scene.ChoseColorAdapter;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivityAddSceneBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * @author skygge
 * @date 2020-01-12.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：添加场景页面
 */

public class AddSceneActivity extends BasePActivity<AddScenePresenter, ActivityAddSceneBinding> implements AddSceneContract.IView {

    private int mGateway = -1;
    private ChoseColorAdapter mColorAdapter;
    private GatewayListAdapter mGatewayAdapter;
    private List<AutomationBean> mAutoList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_scene;
    }

    @Override
    protected void initialize() {
        super.initialize();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initPresent() {
        super.initPresent();
        mPresent = new AddScenePresenter(mContext);
    }

    @Override
    protected void initView() {
        super.initView();
        mAutoList = new ArrayList<>();
        mDBind.etSceneName.setText(getString(R.string.system_scene)+mPresent.getSid());
        mColorAdapter = new ChoseColorAdapter(mContext);
        mGatewayAdapter = new GatewayListAdapter(mContext);
        mDBind.rvColorList.setLayoutManager(new GridLayoutManager(mContext, 5));
        mDBind.rvColorList.setAdapter(mColorAdapter);
        mDBind.sceneChooseGateway.setLayoutManager(new GridLayoutManager(mContext, 2));
        mDBind.sceneChooseGateway.setAdapter(mGatewayAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        mColorAdapter.setColor(-1);
        mPresent.getGatewayList();
        LiveDataBus.get().with("chose_color", Integer.class).observe(this, integer -> {
            mColorAdapter.setColor(integer);
            mPresent.getDefaultColorCode(String.valueOf(integer));
        });
        LiveDataBus.get().with("choose_gateway",Integer.class).observe(this, integer -> {
            mGatewayAdapter.setPosition(integer);
            mGateway = integer;
        });
     }

    @Override
    protected void initListener() {
        super.initListener();
        mDBind.ivSceneBack.setOnClickListener(view -> finish());
        mDBind.tvAddScene.setOnClickListener(view -> {
            String mSceneName = mDBind.etSceneName.getText().toString();
            if (mGateway < 0){
                showToastMsg(getString(R.string.please_choose_device));
            }else if (TextUtils.isEmpty(mSceneName)){
                showToastMsg(getString(R.string.input_scene_name));
            }else {
                mPresent.onSaveScene(mSceneName, mGateway, mAutoList);
            }
        });
        mDBind.ivAddGateway.setOnClickListener(v -> {
            skipAnotherActivity(ConfigGatewayActivity.class);
            finish();
        });
    }

    @Subscribe          //订阅事件FirstEvent
    public  void onEventMainThread(EventAnswerOK event){
        if(event.getData_str1().length()==9) {
            int cmd = Integer.parseInt(event.getData_str1().substring(0, 4), 16);
            if (cmd == SendCommandAli.INCREACE_SCENE_GROUP) {
                if ("OK".equals(event.getData_str2())) {
                    hideSoftKeyboard();
                    mPresent.onSaveSuccess();
                    finish();
                }else {
                    mPresent.onSaveFailed();
                }
            }
        }else {
            mPresent.onSaveFailed();
        }
    }

    @Override
    public void showGatewayList(List<DeviceInfoBean> deviceList) {
        mGatewayAdapter.setList(deviceList);
    }

    @Override
    public void withoutGateway() {
        mGatewayAdapter.setList(null);
        mDBind.llWithout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showToastMsg(String msg) {
        showToast(msg);
    }

    @Override
    public void showProgress() {
        showProgressDialog();
    }

    @Override
    public void disProgress() {
        dismissProgressDialog();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
