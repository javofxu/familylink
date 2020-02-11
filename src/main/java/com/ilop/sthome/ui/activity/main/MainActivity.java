package com.ilop.sthome.ui.activity.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;

import com.aliyun.iot.aep.sdk.login.LoginBusiness;
import com.example.common.base.BasePActivity;
import com.facebook.drawee.generic.RoundingParams;
import com.ilop.sthome.mvp.contract.MainContract;
import com.ilop.sthome.mvp.present.MainPresenter;
import com.ilop.sthome.service.SiterService;
import com.ilop.sthome.ui.activity.scene.AddSceneActivity;
import com.ilop.sthome.ui.activity.config.BindAndUseActivity;
import com.ilop.sthome.ui.activity.mine.AboutActivity;
import com.ilop.sthome.ui.activity.mine.AssessActivity;
import com.ilop.sthome.ui.activity.mine.InstructionActivity;
import com.ilop.sthome.ui.activity.mine.PersonalActivity;
import com.ilop.sthome.ui.activity.mine.SetUpActivity;
import com.ilop.sthome.utils.updateApp.UpdateAppUtil;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivityMainBinding;

public class MainActivity extends BasePActivity<MainPresenter, ActivityMainBinding> implements MainContract.IView {

    private RadioButton[] radioButtons;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initPresent() {
        super.initPresent();
        mPresent = new MainPresenter(mContext);
    }

    @Override
    protected void initView() {
        super.initView();
        mDBind.mainToolbar.setTitle(getString(R.string.device));
        setSupportActionBar(mDBind.mainToolbar);
        UpdateAppUtil mUpdateAppUtil = new UpdateAppUtil(this);
        mUpdateAppUtil.getUpdateInfo();
        radioButtons = new RadioButton[3];
        radioButtons[0] = mDBind.mainDevice;
        radioButtons[1] = mDBind.mainScene;
        radioButtons[2] = mDBind.mainMessage;
    }

    @Override
    protected void initData() {
        super.initData();
        mPresent.initBottomNavigation(radioButtons);
        mPresent.initHomeFragment();
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDBind.drawerLayout.addDrawerListener(drawerListener);
        mDBind.drawerLayout.setScrimColor(Color.TRANSPARENT);
        mDBind.mainRadio.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == mPresent.getCurrentCheckedId()) return;
            switch (checkedId){
                case R.id.main_device:
                    mDBind.mainToolbar.setTitle(getString(R.string.device));
                    mDBind.mainAddScene.setVisibility(View.INVISIBLE);
                    mPresent.replaceFragment(0);
                    break;
                case R.id.main_scene:
                    mDBind.mainToolbar.setTitle(getString(R.string.scenes));
                    mDBind.mainAddScene.setVisibility(View.VISIBLE);
                    mPresent.replaceFragment(1);
                    break;
                case R.id.main_message:
                    mDBind.mainToolbar.setTitle(getString(R.string.message));
                    mDBind.mainAddScene.setVisibility(View.INVISIBLE);
                    mPresent.replaceFragment(2);
                    break;
            }
        });
        mDBind.mainToolbar.setNavigationOnClickListener(view -> mDBind.drawerLayout.openDrawer(GravityCompat.START));

        mDBind.mainAddScene.setOnClickListener(view -> skipAnotherActivity(AddSceneActivity.class));

        mDBind.mainAbout.setOnClickListener(view -> skipAnotherActivity(AboutActivity.class));

        mDBind.mainPerson.setOnClickListener(view -> skipAnotherActivity(PersonalActivity.class));

        mDBind.mainInstructions.setOnClickListener(view -> skipAnotherActivity(InstructionActivity.class));

        mDBind.mainSetting.setOnClickListener(view -> skipAnotherActivity( SetUpActivity.class));

        mDBind.mainAssess.setOnClickListener(view -> skipAnotherActivity(AssessActivity.class));

        mDBind.mainLoginOut.setOnClickListener(view -> mPresent.onLoginOut());
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mDBind.drawerLayout.isDrawerOpen(GravityCompat.START)){
            mDBind.drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!LoginBusiness.isLogin()) {
            Intent intent = new Intent(getApplicationContext(), StartActivity.class);
            startActivityByIntent(intent);
            return;
        }
        mPresent.refreshRequestUserInfo();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            Log.d(TAG, "onActivityResult");
            if (data != null && data.getStringExtra("productKey") != null) {
                Bundle bundle = new Bundle();
                bundle.putString("productKey", data.getStringExtra("productKey"));
                bundle.putString("deviceName", data.getStringExtra("deviceName"));
                bundle.putString("token", data.getStringExtra("token"));
                Intent intent = new Intent(this, BindAndUseActivity.class);
                intent.putExtras(bundle);
                this.startActivity(intent);
            }
        }
    }

    @Override
    public void showFragment(Fragment mFragment) {
        getSupportFragmentManager().beginTransaction().show(mFragment).commit();
    }

    @Override
    public void addFragment(Fragment mFragment) {
        getSupportFragmentManager().beginTransaction().add(R.id.main_container, mFragment).commit();
    }

    @Override
    public void hideFragment(Fragment mFragment) {
        getSupportFragmentManager().beginTransaction().hide(mFragment).commit();
    }

    @Override
    public void showLoginName(String loginName) {
        mDBind.mainLoginName.setText(loginName);
    }

    @Override
    public void showNickName(String nickname) {
        if (nickname!=null){
            mDBind.mainNickName.setText(nickname);
        }else {
            mDBind.mainNickName.setVisibility(View.GONE);
        }
    }

    @Override
    public void showUserImage(String uri) {
        if (!TextUtils.isEmpty(uri)){
            mDBind.mainUserImg.setImageURI(uri);
            mDBind.mainUserImg.getHierarchy().setRoundingParams(RoundingParams.asCircle());
        }else {
            mDBind.mainUserImg.setImageResource(R.mipmap.head_default);
        }
    }

    @Override
    public void showToastMsg(String msg) {
        showToast(msg);
    }

    @Override
    public void startActivityByIntent(Intent intent) {
        startActivity(intent);
        finish();
    }

    @Override
    public void closeDrawer() {
        mDBind.drawerLayout.closeDrawer(GravityCompat.START);
    }

    /**
     * DrawerListener
     */
    DrawerLayout.DrawerListener drawerListener = new DrawerLayout.DrawerListener() {
        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {
            // 得到contentView 实现侧滑界面出现后主界面向右平移避免侧滑界面遮住主界面
            View content = mDBind.drawerLayout.getChildAt(0);
            int offset = (int) (drawerView.getWidth() * slideOffset);
            content.setTranslationX(offset);
        }

        @Override
        public void onDrawerOpened(View drawerView) {
            //打开侧滑界面触发
        }

        @Override
        public void onDrawerClosed(View drawerView) {
            //关闭侧滑界面触发
        }

        @Override
        public void onDrawerStateChanged(int newState) {
            //状态改变时触发
        }
    };

    @Override
    public void onBackPressed() {
        moveTaskToBack(false);
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, SiterService.class));
        super.onDestroy();
    }
}
