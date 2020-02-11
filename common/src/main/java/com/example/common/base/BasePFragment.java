package com.example.common.base;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.common.mvp.IBasePresenter;
import com.example.common.mvp.IBaseView;
import com.example.common.utils.StatusBar.StatusBarUtil;

/**
 * @Author skygge.
 * @Date on 2019-09-03.
 * @Github https://github.com/javofxu
 * @Dec: Fragment基础类
 * @version: ${VERSION}.
 * @Update :
 */
public class BasePFragment<T extends IBasePresenter, B extends ViewDataBinding> extends Fragment implements IBaseView {

    protected String TAG = getClass().getSimpleName();
    protected Activity mContext;
    protected T mPresenter;
    protected B mDBind;

    private BaseLoadingDialog dialog;
    private View mView;
    private boolean mIsPrepare = false;		//视图还没准备好
    private boolean mIsVisible= false;		//不可见
    private boolean mIsFirstLoad = true;	//第一次加载

    public BasePFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: ");
        mContext = getActivity();
        initialize();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: ");
        if (getLayoutId()>0){
            mDBind = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
            mView = mDBind.getRoot();
        }
        initPresent();
        if (mPresenter!=null){
            mPresenter.attachView(this);
        }
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mIsPrepare = true;
        lazyLoad();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            mIsVisible = true;
            lazyLoad();
        } else {
            mIsVisible = false;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "onActivityCreated: ");
        initView();
        initData();
        initListener();
    }

    private void lazyLoad() {
        //这里进行三个条件的判断，如果有一个不满足，都将不进行加载
        if (!mIsPrepare || !mIsVisible||!mIsFirstLoad) {
            return;
        }
        loadData();
        //数据加载完毕,恢复标记,防止重复加载
        mIsFirstLoad = false;
    }

    protected void loadData() {

    }

    protected int getLayoutId(){
        return -1;
    }

    protected void initialize() {
    }


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
     * 初始化Presenter
     */
    protected void initPresent() {
    }

    /**
     * 沉浸式导航栏
     * @param isDark 深色浅色切换
     */
    protected void immerse(boolean isDark) {
        if(Build.VERSION.SDK_INT>=21){
            Window window=getActivity().getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            StatusBarUtil.setStatusBarDarkTheme(mContext, isDark);
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
        Toast.makeText(getActivity(), content, Toast.LENGTH_SHORT).show();
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
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mIsFirstLoad=true;
        mIsPrepare=false;
        mIsVisible = false;
        if (mView != null) {
            ((ViewGroup) mView.getParent()).removeView(mView);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter!=null){
            mPresenter.detachView();
        }
        Log.i(TAG, "onDestroy: ");
    }
}
