package com.ilop.sthome.ui.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.example.common.base.BaseFragment;
import com.ilop.sthome.data.enums.DevType;
import com.ilop.sthome.data.event.EventRefreshDevice;
import com.ilop.sthome.data.greenDao.DeviceInfoBean;
import com.ilop.sthome.ui.adapter.main.PagerAdapter;
import com.ilop.sthome.utils.greenDao.DeviceDaoUtil;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.FragmentSceneBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * @author skygge
 * @date 2019-12-30.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：场景页面
 */
public class SceneFragment extends BaseFragment<FragmentSceneBinding> {

    private List<Fragment> mFragment;
    private List<String> mTabName;
    private PagerAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_scene;
    }

    @Override
    protected void initialize() {
        super.initialize();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initView() {
        super.initView();
        mFragment = new ArrayList<>();
        mTabName = new ArrayList<>();
    }

    @Override
    protected void initData() {
        super.initData();
        onCreateView();
        mAdapter = new PagerAdapter(getFragmentManager());
        mAdapter.setTitleAndFragment(mTabName, mFragment);
        mDBind.sceneViewPager.setAdapter(mAdapter);
        mDBind.sceneTabLayout.setupWithViewPager(mDBind.sceneViewPager);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDBind.sceneTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mDBind.sceneViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mDBind.sceneTabLayout.getTabLayout()));
    }

    @Subscribe
    public void onEventMainThread(EventRefreshDevice event){
        Log.i(TAG, "onEventMainThread: A");
        List<DeviceInfoBean> deviceList = DeviceDaoUtil.getInstance().findAllGateway();
        if (deviceList.size() == mTabName.size()){
            for (int i = 0; i < deviceList.size(); i++) {
                if (TextUtils.isEmpty(deviceList.get(i).getNickName())) {
                    if (!mTabName.get(i).equals(String.valueOf(DevType.getType(deviceList.get(i).getProductKey()).getTypeStrId()))){
                        onRefreshView(deviceList);
                    }
                }else {
                    if (!mTabName.get(i).equals(deviceList.get(i).getNickName())){
                        onRefreshView(deviceList);
                    }
                }
            }
        }else {
            onRefreshView(deviceList);
        }
    }


    private void onCreateView(){
        List<DeviceInfoBean> list = DeviceDaoUtil.getInstance().findAllGateway();
        if (list.size()>0){
            mDBind.sceneHas.setVisibility(View.VISIBLE);
            mDBind.sceneWithout.setVisibility(View.GONE);
            for(int i = 0; i < list.size(); i++){
                if (TextUtils.isEmpty(list.get(i).getNickName())) {
                    mDBind.sceneTabLayout.addTab(String.valueOf(DevType.getType(list.get(i).getProductKey()).getTypeStrId()));
                    mTabName.add(String.valueOf(DevType.getType(list.get(i).getProductKey()).getTypeStrId()));
                } else {
                    mDBind.sceneTabLayout.addTab(list.get(i).getNickName());
                    mTabName.add(list.get(i).getNickName());
                }
                mFragment.add(SceneChildFragment.newInstance(list.get(i).getDeviceName()));
            }
        }else {
            mDBind.sceneHas.setVisibility(View.GONE);
            mDBind.sceneWithout.setVisibility(View.VISIBLE);
        }
    }

    private void onRefreshView(List<DeviceInfoBean> deviceList){
        mFragment.clear();
        mTabName.clear();
        mDBind.sceneTabLayout.removeTab();
        for (int i = 0; i < deviceList.size(); i++) {
            if (TextUtils.isEmpty(deviceList.get(i).getNickName())) {
                mDBind.sceneTabLayout.addTab(String.valueOf(DevType.getType(deviceList.get(i).getProductKey()).getTypeStrId()));
                mTabName.add(String.valueOf(DevType.getType(deviceList.get(i).getProductKey()).getTypeStrId()));
            } else {
                mDBind.sceneTabLayout.addTab(deviceList.get(i).getNickName());
                mTabName.add(deviceList.get(i).getNickName());
            }
            mFragment.add(SceneChildFragment.newInstance(deviceList.get(i).getDeviceName()));
        }
        mAdapter.setTitleAndFragment(mTabName, mFragment);
        mDBind.sceneViewPager.setCurrentItem(0);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
