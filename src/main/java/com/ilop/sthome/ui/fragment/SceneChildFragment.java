package com.ilop.sthome.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.example.common.base.BasePFragment;
import com.example.common.utils.LiveDataBus;
import com.example.common.view.refresh.CustomRefreshView;
import com.ilop.sthome.data.bean.SysModelAliBean;
import com.ilop.sthome.data.event.EventRefreshScene;
import com.ilop.sthome.mvp.contract.SceneChangeContract;
import com.ilop.sthome.mvp.present.SceneChangePresenter;
import com.ilop.sthome.ui.adapter.scene.SceneChildAdapter;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.FragmentSceneChildBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * @author skygge
 * @date 2019-12-30.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：
 */
public class SceneChildFragment extends BasePFragment<SceneChangePresenter, FragmentSceneChildBinding> implements SceneChangeContract.IView {

    private String mDeviceName;
    private SceneChildAdapter mAdapter;

    public static SceneChildFragment newInstance(String deviceName) {
        Bundle args = new Bundle();
        args.putString("deviceName", deviceName);
        SceneChildFragment fragment = new SceneChildFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_scene_child;
    }

    @Override
    protected void initialize() {
        super.initialize();
        if (getArguments()!=null){
            mDeviceName = getArguments().getString("deviceName");
        }
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initPresent() {
        super.initPresent();
        mPresenter = new SceneChangePresenter(mContext, mDeviceName);
    }

    @Override
    protected void initView() {
        super.initView();
        mAdapter = new SceneChildAdapter(mContext);
        mDBind.sceneChildRecycle.getRecyclerView().setLayoutManager(new LinearLayoutManager(mContext));
        mDBind.sceneChildRecycle.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getSceneList();
    }

    @Override
    protected void initListener() {
        super.initListener();
        LiveDataBus.get().with("scene_change", SysModelAliBean.class).observe(this, sysModelAliBean -> {
            mPresenter.changeScene(sysModelAliBean);
        });

        mDBind.sceneChildRecycle.setOnLoadListener(new CustomRefreshView.OnLoadListener() {
            @Override
            public void onRefresh() {
                mPresenter.getSceneList();
                mDBind.sceneChildRecycle.complete();
            }

            @Override
            public void onLoadMore() {

            }
        });
    }

    @Subscribe
    public  void onEventMainThread(EventRefreshScene event) {
        if (event.getDeviceName().equals(mDeviceName)) {
            mPresenter.getSceneList();
        }
        dismissProgressDialog();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getSceneList();
    }

    @Override
    public void showSceneList(List<SysModelAliBean> scene) {
        mAdapter.setList(scene);
    }

    @Override
    public void withOutScene() {
        mAdapter.setList(null);
        mDBind.sceneChildRecycle.setEmptyView(getString(R.string.without_scene),R.mipmap.scene_empty);
    }

    @Override
    public void showProgress() {
        showProgressDialog();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
