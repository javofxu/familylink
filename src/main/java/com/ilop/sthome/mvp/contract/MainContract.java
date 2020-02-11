package com.ilop.sthome.mvp.contract;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.widget.RadioButton;

import com.example.common.mvp.IBasePresenter;
import com.example.common.mvp.IBaseView;
import com.ilop.sthome.data.greenDao.UserInfoBean;

/**
 * @Author skygge.
 * @Date on 2019-10-09.
 * @Dec:
 */
public interface MainContract {

    interface IView extends IBaseView{

        void showFragment(Fragment mFragment);

        void addFragment(Fragment mFragment);

        void hideFragment(Fragment mFragment);

        void showLoginName(String loginName);

        void showNickName(String nickname);

        void showUserImage(String uri);

        void showToastMsg(String msg);

        void startActivityByIntent(Intent intent);

        void closeDrawer();
    }

    interface IPresent extends IBasePresenter<IView>{

        void initHomeFragment();

        void initBottomNavigation(RadioButton[] radioButtons);

        int getCurrentCheckedId();

        void replaceFragment(int mCurrentFragmentIndex);

        void refreshRequestUserInfo();

        void onQueryUserInfo(String identifyId);

        void getUserInfo(UserInfoBean userInfoBean);

        String getLoginName();

        void onLoginOut();
    }
}
