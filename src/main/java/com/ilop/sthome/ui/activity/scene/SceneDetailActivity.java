package com.ilop.sthome.ui.activity.scene;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.example.common.base.BasePActivity;
import com.example.common.utils.LiveDataBus;
import com.ilop.sthome.data.bean.SceneAliBean;
import com.ilop.sthome.data.event.EventAnswerOK;
import com.ilop.sthome.mvp.contract.SceneDetailContract;
import com.ilop.sthome.mvp.present.SceneDetailPresenter;
import com.ilop.sthome.network.api.SendCommandAli;
import com.ilop.sthome.ui.adapter.scene.DefaultAutoAdapter;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivitySceneDetailBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * @author skygge
 * @date 2020-01-02.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：场景详情页面
 */
public class SceneDetailActivity extends BasePActivity<SceneDetailPresenter, ActivitySceneDetailBinding> implements SceneDetailContract.IView {

    private int mSceneId;
    private String mDeviceName;
    private DefaultAutoAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_scene_detail;
    }

    @Override
    protected void initialize() {
        super.initialize();
        mSceneId = getIntent().getIntExtra("scene_id", 0);
        mDeviceName = getIntent().getStringExtra("deviceName");
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initPresent() {
        super.initPresent();
        mPresent = new SceneDetailPresenter(mContext, mDeviceName, mSceneId);
    }

    @Override
    protected void initView() {
        super.initView();
        mAdapter = new DefaultAutoAdapter(mContext);
        mDBind.sceneRecycle.setLayoutManager(new LinearLayoutManager(mContext));
        mDBind.sceneRecycle.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresent.getSceneName();

        LiveDataBus.get().with("auto_item_click", SceneAliBean.class).observe(this, sceneAliBean -> mPresent.skipActivityToUpdate(sceneAliBean));

        LiveDataBus.get().with("change_scene_auto",Integer.class).observe(this, integer -> {
            showProgressDialog();
            mPresent.onSaveScene(mAdapter.getSceneBeanList());
        });

        LiveDataBus.get().with("delete_scene_success", Integer.class).observe(this, integer -> finish());
    }

    @Override
    protected void initListener() {
        super.initListener();

        mDBind.ivSceneBack.setOnClickListener(view -> finish());

        mDBind.sceneAddAuto.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setClass(mContext, AutomationDetailActivity.class);
            intent.putExtra("deviceName", mDeviceName);
            startActivity(intent);
        });

        mDBind.addAuto.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(mContext, AutomationDetailActivity.class);
            intent.putExtra("deviceName", mDeviceName);
            startActivity(intent);
        });

        mDBind.tvSceneSetting.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, SceneEditActivity.class);
            intent.putExtra("deviceName", mDeviceName);
            intent.putExtra("sceneId", mSceneId);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresent.getAutoList();
    }

    @Subscribe
    public  void onEventMainThread(EventAnswerOK event){
        if(event.getData_str1().length()==9){
            int cmd = Integer.parseInt(event.getData_str1().substring(0, 4), 16);
            if (cmd == SendCommandAli.INCREACE_SCENE_GROUP) {
                if ("OK".equals(event.getData_str2())) {
                    mPresent.onSaveSuccess(mAdapter.getSceneBeanList());
                }else {
                    showToast(getString(R.string.EE_AS_SEND_EMAIL_FOR_CODE4));
                }
            }
        }
        dismissProgressDialog();
    }

    @Override
    public void showSceneName(String name) {
        mDBind.sceneModeName.setText(name);
    }

    @Override
    public void showAutoList(List<SceneAliBean> mSceneList, List<Integer> mBeforeList) {
        mDBind.sceneWithoutAuto.setVisibility(View.GONE);
        mDBind.sceneHasAuto.setVisibility(View.VISIBLE);
        mAdapter.setList(mSceneList);
        mAdapter.setSelected(mBeforeList);
    }

    @Override
    public void withoutAuto() {
        mAdapter.setList(null);
        mDBind.sceneHasAuto.setVisibility(View.GONE);
        mDBind.sceneWithoutAuto.setVisibility(View.VISIBLE);
    }

    @Override
    public void skipActivity(Intent intent) {
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
