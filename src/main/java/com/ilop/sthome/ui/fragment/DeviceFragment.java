package com.ilop.sthome.ui.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.example.common.base.BasePFragment;
import com.example.common.utils.LiveDataBus;
import com.example.common.view.refresh.CustomRefreshView;
import com.ilop.sthome.data.bean.DeviceInfoBean;
import com.ilop.sthome.data.event.EventRefreshDevice;
import com.ilop.sthome.data.greenDao.RoomBean;
import com.ilop.sthome.mvp.contract.DeviceContract;
import com.ilop.sthome.mvp.present.DevicePresenter;
import com.ilop.sthome.ui.activity.config.ProductActivity;
import com.ilop.sthome.ui.activity.device.GatewayMainActivity;
import com.ilop.sthome.ui.activity.device.RoomManageActivity;
import com.ilop.sthome.ui.adapter.room.RoomAdapter;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.FragmentDeviceBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * @author skygge
 * @date 2019-10-10.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：设备页面
 */
public class DeviceFragment extends BasePFragment<DevicePresenter, FragmentDeviceBinding> implements DeviceContract.IView{

    private RoomAdapter mRoomAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_device;
    }

    @Override
    protected void initialize() {
        super.initialize();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initPresent() {
        super.initPresent();
        mPresenter = new DevicePresenter(mContext);
    }

    @Override
    protected void initView() {
        super.initView();
        mRoomAdapter = new RoomAdapter(mContext);
        LayoutAnimationController mAnimation = AnimationUtils.loadLayoutAnimation(mContext, R.anim.layout_animation_fall_down);
        mDBind.deviceList.getRecyclerView().setLayoutAnimation(mAnimation);
        mDBind.deviceList.setAdapter(mRoomAdapter);
        mDBind.deviceList.setRefreshing(false);
    }

    @Override
    protected void initData() {
        super.initData();
        //mPresenter.getRoomList();
        LiveDataBus.get().with("refresh", Integer.class).observe(this, integer -> {
            Log.i(TAG, "initData: 刷新页面");
            mPresenter.getGatewayListByAccount();
        });

        LiveDataBus.get().with("edit_room", Integer.class).observe(this, integer -> {
            skipAnotherActivity(RoomManageActivity.class);
        });

        LiveDataBus.get().with("ChildItemClick", DeviceInfoBean.class).observe(this, deviceInfoBean -> {
            assert deviceInfoBean != null;
            if(!TextUtils.isEmpty(deviceInfoBean.getProductKey())) {
                Intent intent = new Intent(mContext, GatewayMainActivity.class);
                intent.putExtra("deviceName", deviceInfoBean.getDeviceName());
                startActivity(intent);
            }else {
                mPresenter.clickChildItem(deviceInfoBean);
            }
        });
    }

    @Override
    protected void initListener() {
        super.initListener();

        mDBind.deviceAdd.setOnClickListener(view -> skipAnotherActivity(ProductActivity.class));

        mDBind.deviceList.setOnLoadListener(new CustomRefreshView.OnLoadListener() {
            @Override
            public void onRefresh() {
                mPresenter.getRoomList();
                mDBind.deviceList.complete();
            }

            @Override
            public void onLoadMore() {

            }
        });
    }

    @Subscribe
    public void onEventMainThread(EventRefreshDevice event){
        Log.i(TAG, "onEventMainThread: "+ event.getType());
        mPresenter.getRoomList();
    }

    @Override
    public void showRoomList(List<RoomBean> room) {
        mRoomAdapter.setList(room);
        mDBind.deviceAdd.setVisibility(View.VISIBLE);
    }

    @Override
    public void noDeviceList() {
        mRoomAdapter.setList(null);
        mDBind.deviceAdd.setVisibility(View.GONE);
        mDBind.deviceList.setEmptyView(getString(R.string.ali_device_empty), R.drawable.device_empty_add);
        mDBind.deviceList.setOnEmptyListener(() -> skipAnotherActivity(ProductActivity.class));
    }

    @Override
    public void skipActivity(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void showMsg(String msg) {
        showToast(msg);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getRoomListByAccount(1, 10);
        mPresenter.getGatewayListByAccount();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
