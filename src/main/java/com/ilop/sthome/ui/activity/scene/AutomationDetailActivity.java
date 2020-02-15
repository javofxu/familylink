package com.ilop.sthome.ui.activity.scene;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.example.common.base.BasePActivity;
import com.example.common.utils.LiveDataBus;
import com.ilop.sthome.data.bean.DeviceInfoBean;
import com.ilop.sthome.data.bean.SceneAliBean;
import com.ilop.sthome.data.event.EventAnswerOK;
import com.ilop.sthome.mvp.contract.AutomationContract;
import com.ilop.sthome.mvp.present.AutomationPresenter;
import com.ilop.sthome.network.api.SendCommandAli;
import com.ilop.sthome.ui.adapter.scene.TriggerInAdapter;
import com.ilop.sthome.ui.adapter.scene.TriggerOutAdapter;
import com.ilop.sthome.ui.dialog.TipDialog;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivityAutomationDetailBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * @author skygge
 * @date 2020-01-25.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：自动化详情页面
 */
public class AutomationDetailActivity extends BasePActivity<AutomationPresenter, ActivityAutomationDetailBinding> implements AutomationContract.IView {

    private SceneAliBean mSceneAliBean;
    private String mDeviceName;
    private boolean mModify;

    private TriggerInAdapter mInAdapter;
    private TriggerOutAdapter mOutAdapter;

    private List<DeviceInfoBean> inputList;
    private List<DeviceInfoBean> outputList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_automation_detail;
    }

    @Override
    protected void initialize() {
        super.initialize();
        EventBus.getDefault().register(this);
        mDeviceName = getIntent().getStringExtra("deviceName");
        mModify = getIntent().getBooleanExtra("Modify", false);
        mSceneAliBean = (SceneAliBean) getIntent().getSerializableExtra("Scene");
        if (!mModify){
            mDBind.btDeleteAutomation.setVisibility(View.GONE);
        }else {
            mDBind.tvAutomationName.setText(mSceneAliBean.getName());
        }
    }

    @Override
    protected void initPresent() {
        super.initPresent();
        mPresent = new AutomationPresenter(mContext, mDeviceName);
    }

    @Override
    protected void initView() {
        super.initView();
        inputList = new ArrayList<>();
        outputList = new ArrayList<>();
        mInAdapter = new TriggerInAdapter(mContext);
        mOutAdapter = new TriggerOutAdapter(mContext);
        mDBind.triggerList.setLayoutManager(new LinearLayoutManager(mContext));
        mDBind.triggerList.setAdapter(mInAdapter);
        mDBind.executiveList.setLayoutManager(new LinearLayoutManager(mContext));
        mDBind.executiveList.setAdapter(mOutAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresent.isModifyForShow(mModify, mSceneAliBean);
        LiveDataBus.get().with("input_condition",List.class).observe(this, list -> {
            if (inputList.size() == 0){
                inputList.addAll(list);
                mPresent.setInputList(inputList);
            }else{
                if (mPresent.checkInput((List<DeviceInfoBean>) list)){
                    inputList.add(((List<DeviceInfoBean>) list).get(0));
                    mPresent.setInputList(inputList);
                }else {
                    showToast(getString(R.string.input_exist));
                }
            }
        });
        LiveDataBus.get().with("output_condition", List.class).observe(this, list -> {
            outputList.addAll(list);
            mPresent.setOutputList(outputList);
        });
        LiveDataBus.get().with("update_input", List.class).observe(this, list -> {
            mPresent.checkUpdateInput((List<DeviceInfoBean>)list);
        });
        LiveDataBus.get().with("update_output", List.class).observe(this, list -> {
            mPresent.checkUpdateOutput((List<DeviceInfoBean>)list);
        });
        LiveDataBus.get().with("delete_condition", String.class).observe(this, s -> {
            if ("PHONE".equals(s)){
                mPresent.deleteOutput();
            }else {
                mPresent.deleteInput();
            }
        });
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDBind.ivBack.setOnClickListener(v -> finish());
        mDBind.triggerAdd.setOnClickListener(v ->{
            if (mPresent.isTimerOrClick()){
                showToast(getString(R.string.cant_add_conditions));
            }else {
                mPresent.addNewInput();
            }
        });
        mDBind.executiveAdd.setOnClickListener(v -> mPresent.addNewOutput());
        mDBind.tvSaveAutomation.setOnClickListener(v -> {
            mPresent.onSaveAutomation();
            showProgressDialog();
        });

        mDBind.btDeleteAutomation.setOnClickListener(v -> {
            String dsa = String.format(getString(R.string.want_to_delete_confirm_eq),mSceneAliBean.getName());
            TipDialog dialog = new TipDialog(mContext, ()->{
                showProgressDialog();
                mPresent.onDeleteAutomation(mSceneAliBean.getMid());
            });
            dialog.setMsg(dsa);
            dialog.show();
        });

        LiveDataBus.get().with("input_onClick", DeviceInfoBean.class).observe(this, deviceInfoBean -> {
            mPresent.updateInput(deviceInfoBean);
        });

        LiveDataBus.get().with("output_onClick", DeviceInfoBean.class).observe(this, deviceInfoBean -> {
            mPresent.updateOutput(deviceInfoBean);
        });
    }

    @Subscribe
    public  void onEventMainThread(EventAnswerOK event){
        Log.i(TAG, "onEventMainThread: "+ event.getData_str2());
        if(event.getData_str1().length()==9) {
            int cmd = Integer.parseInt(event.getData_str1().substring(0, 4), 16);
            if (cmd == SendCommandAli.INCREACE_SCENE) {
                if ("OK".equals(event.getData_str2())) {
                    mPresent.sendSuccess();
                }
            }else if(cmd == SendCommandAli.DELETE_SCENE){
                if ("OK".equals(event.getData_str2())) {
                    showToast(getString(R.string.delete_success));
                    mPresent.deleteSuccess();
                } else {
                    showToast(getString(R.string.failed));
                }
            }
        }
    }

    @Override
    public void showInputList(List<DeviceInfoBean> deviceList) {
        inputList = deviceList;
        mInAdapter.setList(deviceList);
    }

    @Override
    public void showOutputList(List<DeviceInfoBean> deviceList) {
        outputList = deviceList;
        mOutAdapter.setList(deviceList);
    }

    @Override
    public void startActivityByIntent(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void showToastMsg(String msg) {
        showToast(msg);
    }

    @Override
    public void finishActivity() {
        dismissProgressDialog();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
