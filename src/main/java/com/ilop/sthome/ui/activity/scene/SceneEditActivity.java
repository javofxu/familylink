package com.ilop.sthome.ui.activity.scene;

import android.support.v7.widget.GridLayoutManager;
import android.util.Log;

import com.example.common.base.BasePActivity;
import com.example.common.utils.LiveDataBus;
import com.ilop.sthome.data.event.EventAnswerOK;
import com.ilop.sthome.mvp.contract.SceneEditContract;
import com.ilop.sthome.mvp.present.SceneEditPresenter;
import com.ilop.sthome.network.api.SendCommandAli;
import com.ilop.sthome.ui.adapter.scene.ChoseColorAdapter;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivitySceneEditBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * @author skygge
 * @date 2020-01-03.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：场景设置
 */
public class SceneEditActivity extends BasePActivity<SceneEditPresenter, ActivitySceneEditBinding> implements SceneEditContract.IView {

    private int mSceneId;
    private String mDeviceName;
    private int mPosition;
    private ChoseColorAdapter mColorAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_scene_edit;
    }

    @Override
    protected void initialize() {
        super.initialize();
        mDeviceName = getIntent().getStringExtra("deviceName");
        mSceneId = getIntent().getIntExtra("sceneId", -1);
        Log.i(TAG, "initialize: "+ mSceneId);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initPresent() {
        super.initPresent();
        mPresent = new SceneEditPresenter(mContext, mDeviceName, mSceneId);
    }

    @Override
    protected void initView() {
        super.initView();
        mColorAdapter = new ChoseColorAdapter(mContext);
        mDBind.sceneColorList.setLayoutManager(new GridLayoutManager(mContext, 5));
        mDBind.sceneColorList.setAdapter(mColorAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresent.refreshName();
        mColorAdapter.setColor(mPosition);
    }

    @Override
    protected void initListener() {
        super.initListener();
        LiveDataBus.get().with("chose_color", Integer.class).observe(this, integer -> {
            mPosition = integer;
            mColorAdapter.setColor(mPosition);
            mPresent.setSceneColor(mPosition);
        });

        mDBind.ivSettingBack.setOnClickListener(view -> finish());

        mDBind.tvSceneSave.setOnClickListener(view -> {
            showProgressDialog();
            mPresent.onSaveScene();
        });

        mDBind.sceneDelete.setOnClickListener(view -> {
            if (mSceneId < 4){
                showToast(getString(R.string.system_mode_not_allow_delete));
            }else {
                mPresent.deleteScene();
            }
        });
    }

    @Override
    public void showSceneName(String name) {
        mDBind.etSceneName.setText(name);
    }

    @Override
    public void showSceneColor(int position) {
        this.mPosition = position;
    }

    @Override
    public void onSuccess() {
        showToast(getString(R.string.success));
        finish();
    }

    @Override
    public void showToastMsg(String msg) {
        showToast(msg);
    }

    @Override
    public void showProgress() {
        showProgressDialog();
    }

    @Subscribe
    public  void onEventMainThread(EventAnswerOK event) {
        if(event.getData_str1().length()==9) {
            int cmd = Integer.parseInt(event.getData_str1().substring(0, 4), 16);
            if (cmd == SendCommandAli.INCREACE_SCENE_GROUP) {
                if ("OK".equals(event.getData_str2())) {
                    mPresent.onSaveSuccess();
                }else {
                    showToast(getString(R.string.EE_AS_SEND_EMAIL_FOR_CODE4));
                }
            }else if (cmd == SendCommandAli.SCENE_GROUP_DELETE) {
                if ("OK".equals(event.getData_str2())) {
                    mPresent.deleteSceneSuccess(mSceneId);
                    LiveDataBus.get().with("delete_scene_success").setValue(0);
                } else {
                    showToast(getString(R.string.failed));
                }
            }
        }
        dismissProgressDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
