package com.ilop.sthome.ui.activity.device;

import android.support.v7.widget.LinearLayoutManager;

import com.example.common.base.BasePActivity;
import com.example.common.utils.LiveDataBus;
import com.example.common.view.refresh.CustomRefreshView;
import com.ilop.sthome.data.bean.SysModelAliBean;
import com.ilop.sthome.data.event.EventRefreshScene;
import com.ilop.sthome.mvp.contract.SceneChangeContract;
import com.ilop.sthome.mvp.present.SceneChangePresenter;
import com.ilop.sthome.ui.adapter.scene.SceneChangeAdapter;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivityChangeSceneBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * @author skygge
 * @date 2020-01-10.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：网关中更换场景
 */
public class ChangeSceneActivity extends BasePActivity<SceneChangePresenter, ActivityChangeSceneBinding> implements SceneChangeContract.IView {

    private String mDeviceName;
    private SceneChangeAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_scene;
    }

    @Override
    protected void initialize() {
        super.initialize();
        EventBus.getDefault().register(this);
        mDeviceName = getIntent().getStringExtra("deviceName");
    }

    @Override
    protected void initPresent() {
        super.initPresent();
        mPresent = new SceneChangePresenter(mContext, mDeviceName);
    }

    @Override
    protected void initView() {
        super.initView();
        mAdapter = new SceneChangeAdapter(mContext);
        mDBind.rvSceneList.getRecyclerView().setLayoutManager(new LinearLayoutManager(mContext));
        mDBind.rvSceneList.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresent.getSceneList();
        LiveDataBus.get().with("scene_change_list", SysModelAliBean.class).observe(this, sysModelAliBean -> {
            mPresent.changeScene(sysModelAliBean);
        });
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDBind.ivSceneBack.setOnClickListener(view -> finish());
        mDBind.rvSceneList.setOnLoadListener(new CustomRefreshView.OnLoadListener() {
            @Override
            public void onRefresh() {
                mPresent.getSceneList();
                mDBind.rvSceneList.complete();
            }

            @Override
            public void onLoadMore() {
                mDBind.rvSceneList.complete();
            }
        });
    }

    @Subscribe
    public  void onEventMainThread(EventRefreshScene event) {
        if (event.getDeviceName().equals(mDeviceName)) {
            mPresent.getSceneList();
        }
        dismissProgressDialog();
    }

    @Override
    public void showSceneList(List<SysModelAliBean> scene) {
        mAdapter.setList(scene);
    }

    @Override
    public void withOutScene() {
        mAdapter.setList(null);
        mDBind.rvSceneList.setEmptyView(getString(R.string.no_data), R.mipmap.device_history_empty);
    }

    @Override
    public void showProgress() {
        showProgressDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
