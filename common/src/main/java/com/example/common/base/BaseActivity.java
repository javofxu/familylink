package com.example.common.base;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.common.mvp.IBaseView;
import com.example.common.utils.ScreenAdapterUtil;
import com.example.common.utils.StatusBar.StatusBarUtil;

/**
 * @Author skygge.
 * @Date on 2019-09-26.
 * @Dec: base
 */
public class BaseActivity<B extends ViewDataBinding> extends AppCompatActivity implements IBaseView {

    protected String TAG = getClass().getSimpleName();
    protected Context mContext;
    protected B mDBind;
    private BaseLoadingDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(getLayoutId());
        mDBind = DataBindingUtil.setContentView(this, getLayoutId());
        screenAdaptation();
        initialize();
        initView();
        initData();
        initListener();
    }

    /**
     * 初始化
     */
    protected void initialize() {
        immersionStatusBar(true);
    }

    /**
     * 获取Layout
     * @return
     */
    protected int getLayoutId(){
        return -1;
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

        //当FitsSystemWindows设置 true 时，会在屏幕最上方预留出状态栏高度的 padding
        StatusBarUtil.setRootViewFitsSystemWindows(this,true);
        //设置状态栏透明
        StatusBarUtil.setTranslucentStatus(this);
        //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
        //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
        if (!StatusBarUtil.setStatusBarDarkTheme(this, isDark)) {
            //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
            //这样半透明+白=灰, 状态栏的文字能看得清
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
        if (dialog == null){
            dialog = new BaseLoadingDialog(mContext);
            dialog.show();
        }
    }

    @Override
    public void dismissProgressDialog() {
        if (dialog!=null){
            dialog.dismiss();
            dialog = null;
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK &&event.getRepeatCount() ==0){
            if (dialog!=null){
                dialog.dismiss();
                dialog = null;
            }
        }
        return super.onKeyDown(keyCode, event);
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
