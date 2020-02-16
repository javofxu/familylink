package com.ilop.sthome.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.common.base.BasePFragment;
import com.example.common.view.refresh.CustomRefreshView;
import com.ilop.sthome.data.event.EventAnswerOK;
import com.ilop.sthome.data.event.EventRefreshDevice;
import com.ilop.sthome.data.greenDao.DeviceInfoBean;
import com.ilop.sthome.mvp.contract.SubDeviceContract;
import com.ilop.sthome.mvp.present.SubDevicePresenter;
import com.ilop.sthome.network.api.SendCommandAli;
import com.ilop.sthome.ui.adapter.device.SubDeviceAdapter;
import com.ilop.sthome.utils.greenDao.DeviceDaoUtil;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.FragmentSubDeviceBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;



/**
 * Created by 许格 on 2018/10/6.
 */

@SuppressLint("ValidFragment")
public class SubDeviceFragment extends BasePFragment<SubDevicePresenter,FragmentSubDeviceBinding> implements SubDeviceContract.IView , SubDeviceAdapter.refresh{
    private int mDeviceId;
    private String mDeviceName;
    private String mNickname;
    private DeviceInfoBean delete_deviceInfoBean;


    private SubDeviceAdapter mAdapter;

    public SubDeviceFragment(String deviceName){
        super();
        this.mDeviceName = deviceName;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sub_device;
    }

    @Override
    protected void initPresent() {
        super.initPresent();
        mPresenter = new SubDevicePresenter(mContext);
    }

    @Override
    protected void initialize() {
        super.initialize();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initView() {
        super.initView();
        mAdapter = new SubDeviceAdapter(mContext);
        mAdapter.setRefresh(this);
        mDBind.subRefresh.getSwipeRefreshLayout().setColorSchemeColors(getResources().getColor(R.color.main_color));
        mDBind.subRefresh.getRecyclerView().setLayoutManager(new LinearLayoutManager(mContext));
        mDBind.subRefresh.setRefreshing(false);
        mPresenter.getAllSubDevice(mDeviceName);
        mDBind.subRefresh.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDBind.subRefresh.setOnLoadListener(new CustomRefreshView.OnLoadListener() {
            @Override
            public void onRefresh() {
                mPresenter.getAllSubDevice(mDeviceName);
                mDBind.subRefresh.complete();
            }

            @Override
            public void onLoadMore() {

            }
        });

    }

    @Override
    public void onResume(){
        super.onResume();
        mPresenter.getAllSubDevice(mDeviceName);
    }

    @Subscribe
    public  void onEventMainThread(EventRefreshDevice event){
        if(event.getDeviceName().equals(mDeviceName)){
            mPresenter.getAllSubDevice(mDeviceName);
        }
        dismissProgressDialog();
    }

    @Subscribe
    public  void onEventMainThread(EventAnswerOK event) {
        if(event.getData_str1().length()==9) {
            int cmd = Integer.parseInt(event.getData_str1().substring(0, 4), 16);
            if (cmd == SendCommandAli.DELETE_EQUIPMENT) {
                if ("OK".equals(event.getData_str2())) {
                    if(delete_deviceInfoBean!=null){
                        DeviceDaoUtil.getInstance().deleteByDeviceName(delete_deviceInfoBean.getDeviceName(),delete_deviceInfoBean.getDevice_ID());
                        mPresenter.getAllSubDevice(mDeviceName);
                    }
                }else {
                    showToast(getString(R.string.failed));
                }
            }else if(cmd == SendCommandAli.MODIFY_EQUIPMENT_NAME){
                if("OK".equals(event.getData_str2())){
                    if(mDeviceId>0){
                        DeviceInfoBean deviceInfoBean = new DeviceInfoBean();
                        deviceInfoBean.setSubdeviceName(mNickname);
                        deviceInfoBean.setDeviceName(mDeviceName);
                        deviceInfoBean.setDevice_ID(mDeviceId);
                        DeviceDaoUtil.getInstance().getDeviceDao().update(deviceInfoBean);
                        mPresenter.getAllSubDevice(mDeviceName);
                        hideSoftKeyboard();
                        mDeviceId= 0;
                    }
                }else {
                    showToast(getString(R.string.failed));
                }
            }
        }
        dismissProgressDialog();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) this.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if(inputMethodManager != null ) {
            View localView = mContext.getCurrentFocus();
            if(localView != null && localView.getWindowToken() != null ) {
                IBinder windowToken = localView.getWindowToken();
                inputMethodManager.hideSoftInputFromWindow(windowToken, 0);
            }
        }
    }

    @Override
    public void getAllSubDevice(List<DeviceInfoBean> device) {
        mAdapter.setDeviceList(device);
        mAdapter.notifyDataSetChanged();
        mDBind.subRefresh.complete();
    }

    @Override
    public void withOutSubDevice() {
        mAdapter.setDeviceList(null);
        mAdapter.notifyDataSetChanged();
        mDBind.subRefresh.complete();
        mDBind.subRefresh.setEmptyView(getString(R.string.ali_device_empty),0);
    }

    @Override
    public void getDeviceInfo(int id, String nickname) {
        mDeviceId = id;
        mNickname = nickname;
    }

    @Override
    public void skipActivity(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void showToastMsg(String msg) {
        showToast(msg);
    }

    @Override
    public void showProgress() {
        showProgressDialog();
    }

    @Override
    public void clickItem(DeviceInfoBean device) {
        mPresenter.clickItem(device);
    }

    @Override
    public void longClickItem(DeviceInfoBean device) {
        mPresenter.longClickItem(device);
        delete_deviceInfoBean = device;
    }
}
