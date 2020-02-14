package com.ilop.sthome.ui.activity.detail;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.common.base.BaseLoadingDialog;
import com.example.common.mvp.IBaseView;
import com.example.common.utils.ScreenAdapterUtil;
import com.example.common.utils.StatusBar.StatusBarUtil;
import com.ilop.sthome.data.bean.DeviceInfoBean;
import com.ilop.sthome.data.db.DeviceAliDAO;
import com.ilop.sthome.network.api.SendEquipmentDataAli;
import com.ilop.sthome.ui.activity.device.DeviceSettingActivity;
import com.ilop.sthome.ui.activity.device.SubDeviceHistoryActivity;

/**
 * @author skygge
 * @date 2019-12-10.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：详情页BASE类
 */
public class BaseDetailActivity<B extends ViewDataBinding> extends AppCompatActivity implements IBaseView{

    protected String TAG = getClass().getSimpleName();
    protected Context mContext;
    protected B mDBind;
    private BaseLoadingDialog mDialog;

    protected DeviceAliDAO mDeviceDAO;
    protected DeviceInfoBean mDevice;
    protected SendEquipmentDataAli mSendEquipment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(getLayoutId());
        mDBind = DataBindingUtil.setContentView(this, getLayoutId());
        immersionStatusBar(true);
        initialize();
        initView();
        initData();
        initListener();
    }

    /**
     * 获取Layout
     * @return
     */
    protected int getLayoutId(){
        return -1;
    }

    /**
     * 初始化
     */
    private void initialize() {
        mDeviceDAO = new DeviceAliDAO(mContext);
        String deviceName = getIntent().getStringExtra("deviceName");
        int deviceId = getIntent().getIntExtra("eqid",-1);
        mDevice = mDeviceDAO.findByDeviceid(deviceName, deviceId);
        DeviceInfoBean deviceInfoBean = mDeviceDAO.findByDeviceid(deviceName,0);
        mSendEquipment = new SendEquipmentDataAli(this, deviceInfoBean);
    }


    public void finish(View view){
        finish();
    }

    public void manager(View view){
        Intent intent = new Intent(this, DeviceSettingActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("deviceId", mDevice.getDevice_ID());
        bundle.putString("deviceName", mDevice.getDeviceName());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void history(View view){
        Intent intent = new Intent(this, SubDeviceHistoryActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("deviceId", mDevice.getDevice_ID());
        bundle.putString("deviceName", mDevice.getDeviceName());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void mode(View view){
        Intent intent = new Intent(this, AddModeSwitchActivity.class);
        intent.putExtra("deviceName", mDevice.getDeviceName());
        intent.putExtra("deviceId", mDevice.getDevice_ID());
        startActivity(intent);
    }

    /**
     * 初始化控件
     */
    protected void initView() {
    }

    /**
     * 初始化数据
     */
    protected void initData() {

    }

    /**
     * 监听事件写在这
     */
    protected void initListener() {
    }

    /**
     * 屏幕适配
     */
    private void screenAdaptation() {
        ScreenAdapterUtil.setCustomDensity(this, getApplication());
    }

    /**
     * 沉浸式导航栏
     * @param isDark 深色浅色切换
     */
    protected void immersionStatusBar(boolean isDark){
        StatusBarUtil.setRootViewFitsSystemWindows(this,true);
        StatusBarUtil.setTranslucentStatus(this);
        if (!StatusBarUtil.setStatusBarDarkTheme(this, isDark)) {
            StatusBarUtil.setStatusBarColor(this,0x55000000);
        }
    }

    protected void skipAnotherActivity(Class<?> mClass){
        startActivity(new Intent(mContext, mClass));
    }

    protected void skipAnotherActivity(Bundle bundle, Class<?> clazz) {
        Intent intent = new Intent(mContext, clazz);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    protected void  showToast(String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSuccess() {

    }

    @Override
    public void showFailed() {

    }

    @Override
    public void showProgressDialog() {
        mDialog = new BaseLoadingDialog(mContext);
        mDialog.show();
    }

    @Override
    public void dismissProgressDialog() {
        if (mDialog!=null){
            mDialog.dismiss();
            mDialog = null;
        }
    }

    /**
     * hide inputMethod
     */
    protected void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        if(inputMethodManager != null ) {
            View localView = this.getCurrentFocus();
            if(localView != null && localView.getWindowToken() != null ) {
                IBinder windowToken = localView.getWindowToken();
                inputMethodManager.hideSoftInputFromWindow(windowToken, 0);
            }
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
    }

}
