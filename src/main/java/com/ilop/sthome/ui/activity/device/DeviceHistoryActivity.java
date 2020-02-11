package com.ilop.sthome.ui.activity.device;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.example.common.base.BasePActivity;
import com.example.common.view.refresh.CustomRefreshView;
import com.ilop.sthome.data.bean.DeviceHistoryBean;
import com.ilop.sthome.data.event.EventAnswerOK;
import com.ilop.sthome.data.event.EventRefreshLogs;
import com.ilop.sthome.mvp.contract.DeviceHistoryContract;
import com.ilop.sthome.mvp.present.DeviceHistoryPresenter;
import com.ilop.sthome.network.api.SendCommandAli;
import com.ilop.sthome.ui.adapter.device.DeviceHistoryAdapter;
import com.ilop.sthome.ui.dialog.TipDialog;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivityDeviceHistoryBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * @author skygge
 * @date 2020-01-13.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：设备历史记录
 */

public class DeviceHistoryActivity extends BasePActivity<DeviceHistoryPresenter, ActivityDeviceHistoryBinding> implements DeviceHistoryContract.IView {

    private int mPage = 1;
    private String deviceName;
    private boolean isEnd = false;
    private DeviceHistoryAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_device_history;
    }

    @Override
    protected void initialize() {
        super.initialize();
        deviceName = getIntent().getStringExtra("deviceName");
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initPresent() {
        super.initPresent();
        mPresent = new DeviceHistoryPresenter(mContext);
    }

    @Override
    protected void initView() {
        super.initView();
        mAdapter = new DeviceHistoryAdapter(mContext);
        mDBind.rvWarnList.getRecyclerView().setLayoutManager(new LinearLayoutManager(mContext));
        mDBind.rvWarnList.setAdapter(mAdapter);
        mDBind.rvWarnList.setLoadMoreEnable(true);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresent.getGatewayByName(deviceName);
        mPresent.sendGateWaySync(1);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDBind.tvDeleteLogs.setOnClickListener(view -> {
            TipDialog dialog = new TipDialog(mContext, () -> {
                showProgressDialog();
                mPresent.deleteGatewayAlarm(255);
            });
            dialog.setMsg(getString(R.string.alert_delete_all_history_log));
            dialog.show();
        });
        mDBind.ivWarnBack.setOnClickListener(view -> finish());
        mDBind.rvWarnList.setOnLoadListener(new CustomRefreshView.OnLoadListener() {
            @Override
            public void onRefresh() {
                mPresent.sendGateWaySync(1);
                mDBind.rvWarnList.complete();
            }

            @Override
            public void onLoadMore() {
                if (!isEnd) mPresent.sendGateWaySync(mPage);
                mDBind.rvWarnList.complete();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onResume() {
        super.onResume();
        mPresent.getHistoryList(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Subscribe
    public  void onEventMainThread(EventRefreshLogs event) {
        if (deviceName.equals(event.getDeviceName())){
            if(event.getIs_over()==1){
                mPage = 1;
                isEnd = true;
            }else {
                mPage = event.getPage()+1;
                isEnd = false;
                mDBind.rvWarnList.onNoMore();
            }
            mPresent.getHistoryList(true);
            mDBind.rvWarnList.complete();
        }
    }

    @Subscribe
    public  void onEventMainThread(EventAnswerOK event) {
        dismissProgressDialog();
        if (event.getData_str1().length() == 9) {
            int cmd = Integer.parseInt(event.getData_str1().substring(0, 4), 16);
            if (cmd == SendCommandAli.DELETE_GATEWAY_LIST) {
                if ("OK".equals(event.getData_str2())) {
                    mPresent.deleteHistory(deviceName);
                    showToast(getString(R.string.delete_success));
                } else {
                    showToast(getString(R.string.failed));
                }
            }else {
                showToast(getString(R.string.failed));
            }
        }
    }

    @Override
    public void showHistory(List<DeviceHistoryBean> history) {
        mAdapter.setList(history);
        mDBind.rvWarnList.onNoMore();
    }

    @Override
    public void withoutData() {
        mAdapter.setList(null);
        mDBind.rvWarnList.setEmptyView(getString(R.string.no_data), R.mipmap.device_history_empty);
        mDBind.tvDeleteLogs.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
