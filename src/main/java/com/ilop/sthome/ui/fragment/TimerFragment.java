package com.ilop.sthome.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.example.common.base.BasePFragment;
import com.example.common.utils.LiveDataBus;
import com.example.common.view.refresh.CustomRefreshView;
import com.ilop.sthome.data.bean.TimerGatewayAliBean;
import com.ilop.sthome.data.event.EventAnswerOK;
import com.ilop.sthome.data.event.EventRefreshTimer;
import com.ilop.sthome.mvp.contract.TimerContract;
import com.ilop.sthome.mvp.present.TimerPresenter;
import com.ilop.sthome.network.api.SendCommandAli;
import com.ilop.sthome.ui.activity.device.AddTimerActivity;
import com.ilop.sthome.ui.adapter.device.TimerAdapter;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.FragmentTimerBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;


/**
 * Created by 许格 on 2019/10/16.
 */

@SuppressLint("ValidFragment")
public class TimerFragment extends BasePFragment<TimerPresenter, FragmentTimerBinding> implements TimerContract.IView {

    private TimerAdapter mAdapter;
    private String deviceName;


    public TimerFragment(String deviceName){
        super();
        this.deviceName = deviceName;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_timer;
    }

    @Override
    protected void initialize() {
        super.initialize();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initPresent() {
        super.initPresent();
        mPresenter = new TimerPresenter(mContext, deviceName);
    }

    @Override
    protected void initView() {
        super.initView();
        mAdapter = new TimerAdapter(mContext);
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(mContext, R.anim.layout_animation_fall_down);
        mDBind.rvTimerRefresh.getSwipeRefreshLayout().setColorSchemeColors(getResources().getColor(R.color.main_color));
        mDBind.rvTimerRefresh.getRecyclerView().setLayoutManager(new LinearLayoutManager(mContext));
        mDBind.rvTimerRefresh.getRecyclerView().setLayoutAnimation(animation);
        mAdapter.setList(null);
        mDBind.rvTimerRefresh.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        LiveDataBus.get().with("timer_switch", Integer.class).observe(this, integer -> {
            mPresenter.switchClick(integer);
        });

        LiveDataBus.get().with("timer_click", Integer.class).observe(this, integer -> {
            Intent intent = new Intent(mContext, AddTimerActivity.class);
            intent.putExtra("deviceName",deviceName);
            intent.putExtra("timerid", integer);
            startActivity(intent);
        });

        LiveDataBus.get().with("timer_longClick", Integer.class).observe(this, integer -> {
            mPresenter.deleteTimer(integer);
        });

    }

    @Override
    protected void initListener() {
        super.initListener();
        mDBind.rvTimerRefresh.setOnLoadListener(new CustomRefreshView.OnLoadListener() {
            @Override
            public void onRefresh() {
                mPresenter.synchronous();
            }

            @Override
            public void onLoadMore() {

            }
        });

        mDBind.llAddFunction.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, AddTimerActivity.class);
            intent.putExtra("deviceName", deviceName);
            startActivity(intent);
        });
    }


    @Override
    public void onResume(){
        super.onResume();
        mPresenter.getTimerList();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
       EventBus.getDefault().unregister(this);
    }


    @Subscribe          //订阅事件Event
    public  void onEventMainThread(EventRefreshTimer event) {
        if(event.getDeviceName().equals(deviceName)){
            mPresenter.getTimerList();
        }
    }

    @Subscribe          //订阅事件FirstEvent
    public  void onEventMainThread(EventAnswerOK event){
        if(event.getData_str1().length()==9){
            int cmd = Integer.parseInt( event.getData_str1().substring(0,4),16);
            if(cmd== SendCommandAli.MODEL_SWITCH_TIMER){
                if ("OK".equals(event.getData_str2())) {
                    mPresenter.refreshSwitch();
                }else {
                    showToast(getString(R.string.failed));
                }
            }else  if(cmd== SendCommandAli.MODEL_TIMER_DEL){
                if("OK".equals(event.getData_str2())){
                    mPresenter.refreshDelete();
                }else {
                    showToast(getString(R.string.failed));
                }
            }
        }
    }

    @Override
    public void getTimerList(List<TimerGatewayAliBean> gatewayTimerList) {
        dismissProgressDialog();
        mDBind.rvTimerRefresh.complete();
        mAdapter.setList(gatewayTimerList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void withoutTimer() {
        dismissProgressDialog();
        mDBind.rvTimerRefresh.complete();
        mAdapter.setList(null);
        mAdapter.notifyDataSetChanged();
        mDBind.rvTimerRefresh.setEmptyView(getString(R.string.ali_no_timer), 0);
    }

    @Override
    public void showToastMsg(String msg) {
        showToast(msg);
    }

    @Override
    public void showProgress() {
        showProgressDialog();
    }
}
