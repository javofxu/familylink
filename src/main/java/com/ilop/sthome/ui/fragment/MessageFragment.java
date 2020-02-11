package com.ilop.sthome.ui.fragment;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;

import com.example.common.base.BasePFragment;
import com.example.common.view.refresh.CustomRefreshView;
import com.ilop.sthome.data.bean.DeviceHistoryBean;
import com.ilop.sthome.data.event.EventRefreshLogs;
import com.ilop.sthome.mvp.contract.DeviceHistoryContract;
import com.ilop.sthome.mvp.present.DeviceHistoryPresenter;
import com.ilop.sthome.ui.adapter.device.DeviceHistoryAdapter;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.FragmentMessageBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * @author skygge
 * @date 2019-12-31.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：网关消息页面
 */
public class MessageFragment extends BasePFragment<DeviceHistoryPresenter, FragmentMessageBinding> implements DeviceHistoryContract.IView{

    private int mPage = 1;
    private boolean isEnd = false;
    private DeviceHistoryAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    protected void initialize() {
        super.initialize();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initPresent() {
        super.initPresent();
        mPresenter = new DeviceHistoryPresenter(mContext);
    }

    @Override
    protected void initView() {
        super.initView();
        mAdapter = new DeviceHistoryAdapter(mContext);
        mDBind.rvMessage.getRecyclerView().setLayoutManager(new LinearLayoutManager(mContext));
        mDBind.rvMessage.setAdapter(mAdapter);
        mDBind.rvMessage.setLoadMoreEnable(true);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getAllGateway();
        mPresenter.sendSync(1);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDBind.rvMessage.setOnLoadListener(new CustomRefreshView.OnLoadListener() {
            @Override
            public void onRefresh() {
                mPresenter.sendSync(1);
                mDBind.rvMessage.complete();
            }

            @Override
            public void onLoadMore() {
                if (!isEnd) mPresenter.sendSync(mPage);
                mDBind.rvMessage.complete();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getHistoryList(false);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Subscribe
    public  void onEventMainThread(EventRefreshLogs event) {
        if(event.getIs_over()==1){
            mPage = 1;
            isEnd = true;
        }else {
            mPage = event.getPage()+1;
            isEnd = false;
            mDBind.rvMessage.onNoMore();
        }
        mPresenter.getHistoryList(false);
        mDBind.rvMessage.complete();
    }

    @Override
    public void showHistory(List<DeviceHistoryBean> history) {
        mAdapter.setList(history);
        mDBind.rvMessage.onNoMore();
    }

    @Override
    public void withoutData() {
        mAdapter.setList(null);
        mDBind.rvMessage.setEmptyView(getString(R.string.no_data), R.mipmap.device_history_empty);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
