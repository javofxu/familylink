package com.ilop.sthome.ui.activity.mine;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.LinearInterpolator;

import com.example.common.base.BasePActivity;
import com.example.common.utils.DisplayUtils;
import com.example.common.utils.LiveDataBus;
import com.example.common.view.scroll.HeaderScrollHelper;
import com.ilop.sthome.data.bean.CheckDeviceBean;
import com.ilop.sthome.mvp.contract.AssessContract;
import com.ilop.sthome.mvp.present.AssessPresenter;
import com.ilop.sthome.ui.adapter.main.AssessAdapter;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivityAssessBinding;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @Author skygge.
 * @Date on 2019-10-04.
 * @Dec: 安全评测
 */
public class AssessActivity extends BasePActivity<AssessPresenter, ActivityAssessBinding> implements AssessContract.IView{

    private int mScore;
    private boolean isBottom = false;
    private Timer mTimer;
    private AssessAdapter mAdapter;
    private Handler mHandler;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_assess;
    }

    @Override
    protected void initPresent() {
        super.initPresent();
        mPresent = new AssessPresenter(mContext);
    }

    @Override
    protected void initialize() {
        super.initialize();
        immersionStatusBar(false);
    }

    @Override
    protected void initView() {
        super.initView();
        mTimer = new Timer();
        mHandler = new Handler();
        mAdapter = new AssessAdapter(mContext);
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(mContext, R.anim.layout_animation);
        mDBind.assessList.setLayoutManager(new LinearLayoutManager(mContext));
        mDBind.assessList.setLayoutAnimation(animation);
        mDBind.assessList.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresent.startAnalyze();
        LiveDataBus.get().with("update", Integer.class).observe(this, integer -> {
            mDBind.assessRing.startCountStep(100-(19-mScore)*2);
            mAdapter.notifyDataSetChanged();
            mDBind.assessList.scheduleLayoutAnimation();
            mTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (isBottom){
                        mTimer.cancel();
                        mHandler.post(()->{
                            mDBind.assessMsg.setVisibility(View.VISIBLE);
                            mDBind.assessMsg.setText(getString(R.string.comprehensive_score) + mScore + getString(R.string.points));
                            mDBind.assessRing.setCurrentStep(mScore);
                        });
                    }else {
                        mDBind.assessScroll.smoothScrollBy(0, DisplayUtils.dip2px(mContext, 96));
                    }
                }
            }, 2000, 1000);
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initListener() {
        super.initListener();
        mDBind.assessStart.setOnClickListener(v -> {
            mDBind.assessEmpty.setVisibility(View.GONE);
            mDBind.assessList.setVisibility(View.VISIBLE);
            LiveDataBus.get().with("update").postValue(0);
        });
        mDBind.assessRing.setCallBack(() -> {
            mTimer = new Timer();
            isBottom = false;
            mDBind.assessMsg.setVisibility(View.INVISIBLE);
            LiveDataBus.get().with("update").postValue(0);
        });
        mDBind.assessBack.setOnClickListener(view -> finish());

        mDBind.assessScroll.setOnTouchListener((v, event) -> {
            isBottom = true;
            return false;
        });
    }

    @Override
    public void refresh(int score, List<CheckDeviceBean> deviceList) {
        mScore = score;
        mAdapter.setList(deviceList);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mTimer!=null) mTimer.cancel();
    }
}
