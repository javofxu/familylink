package com.ilop.sthome.ui.activity.device;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.example.common.base.BasePActivity;
import com.example.common.base.OnCallBackToRefresh;
import com.example.common.view.refresh.CustomRefreshView;
import com.ilop.sthome.data.bean.SubDeviceHistoryBean;
import com.ilop.sthome.data.event.EventAnswerOK;
import com.ilop.sthome.data.event.EventRefreshSubDeviceLogs;
import com.ilop.sthome.mvp.contract.SubDeviceHistoryContract;
import com.ilop.sthome.mvp.present.SubDeviceHistoryPresenter;
import com.ilop.sthome.network.api.SendCommandAli;
import com.ilop.sthome.ui.adapter.detail.SubHistoryAdapter;
import com.ilop.sthome.ui.dialog.BaseDialog;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivityHistroyDetailBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * @author skygge
 * @date 2019-12-10.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：子设备报警/历史详情
 */
public class SubDeviceHistoryActivity extends BasePActivity<SubDeviceHistoryPresenter, ActivityHistroyDetailBinding> implements SubDeviceHistoryContract.IView {

    private int mPage = 1;
    private boolean isEnd = false;

    private int mDeviceId;
    private String mDeviceName;
    private SubHistoryAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_histroy_detail;
    }

    @Override
    protected void initialize() {
        super.initialize();
        mDeviceName = getIntent().getStringExtra("deviceName");
        mDeviceId = getIntent().getIntExtra("deviceId",-1);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initPresent() {
        super.initPresent();
        mPresent = new SubDeviceHistoryPresenter(mContext);
    }

    @Override
    protected void initView() {
        super.initView();
        mAdapter = new SubHistoryAdapter(mContext);
        mDBind.rvHistoryList.getRecyclerView().setLayoutManager(new LinearLayoutManager(mContext));
        mDBind.rvHistoryList.setAdapter(mAdapter);
        mDBind.rvHistoryList.setLoadMoreEnable(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void initData() {
        super.initData();
        mPresent.getDeviceInfo(mDeviceName, mDeviceId);
        mPresent.getHistoryList();
        mPresent.syncSubAlarm(mPage, mDeviceId);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDBind.clear.setOnClickListener(view -> alertToDelete());
        mDBind.ivHistoryBack.setOnClickListener(view -> finish());
        mDBind.rvHistoryList.setOnLoadListener(new CustomRefreshView.OnLoadListener() {
            @Override
            public void onRefresh() {
                mPresent.syncSubAlarm(1, mDeviceId);
                mDBind.rvHistoryList.complete();
            }

            @Override
            public void onLoadMore() {
                if(!isEnd) mPresent.syncSubAlarm(mPage, mDeviceId);
                mDBind.rvHistoryList.complete();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Subscribe
    public  void onEventMainThread(EventRefreshSubDeviceLogs event){
        if(event.getEqid()== mDeviceId && event.getDeviceName().equals(mDeviceName)){
            mPresent.getHistoryList();
            if(event.getIs_over()==0){
                mPage = event.getPage()+1;
            }else {
                mPage = 1;
                isEnd = true;
                mDBind.rvHistoryList.onNoMore();
            }
            mDBind.rvHistoryList.complete();
        }
    }

    @Subscribe
    public  void onEventMainThread(EventAnswerOK event){
        if (event.getData_str1().length() == 9) {
            int cmd = Integer.parseInt(event.getData_str1().substring(0, 4), 16);
            if (cmd == SendCommandAli.DELETE_SUBDEVICE_ALARM_LIST) {
                if ("OK".equals(event.getData_str2())) {
                    mPresent.deleteHistory();
                    showToast(getString(R.string.delete_success));
                    dismissProgressDialog();
                }
            }
        }
    }


    private void alertToDelete(){
        BaseDialog mDialog = new BaseDialog(mContext, new OnCallBackToRefresh() {
            @Override
            public void onConfirm() {
                showProgressDialog();
                mPresent.deleteSubAlarm(mDeviceId);
            }

            @Override
            public void onCancel() {

            }
        });
        mDialog.setMsg(getString(R.string.delete_or_not));
        mDialog.show();
    }

    @Override
    public void showHistory(List<SubDeviceHistoryBean> historyBean) {
        mAdapter.setList(historyBean);
        mDBind.rvHistoryList.onNoMore();
    }

    @Override
    public void withoutData() {
        mAdapter.setList(null);
        mDBind.rvHistoryList.setEmptyView(getString(R.string.no_data), R.mipmap.device_history_empty);
        mDBind.clear.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
