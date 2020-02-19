package com.ilop.sthome.mvp.present;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.widget.RadioButton;

import com.alibaba.fastjson.JSON;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTResponse;
import com.aliyun.iot.aep.sdk.credential.IotCredentialManager.IoTCredentialListener;
import com.aliyun.iot.aep.sdk.credential.IotCredentialManager.IoTCredentialManageError;
import com.aliyun.iot.aep.sdk.credential.IotCredentialManager.IoTCredentialManageImpl;
import com.aliyun.iot.aep.sdk.credential.data.IoTCredentialData;
import com.aliyun.iot.aep.sdk.login.ILogoutCallback;
import com.aliyun.iot.aep.sdk.login.LoginBusiness;
import com.aliyun.iot.aep.sdk.login.data.UserInfo;
import com.aliyun.iot.push.PushManager;
import com.example.common.base.OnCallBackToRefresh;
import com.example.common.mvp.BasePresenterImpl;
import com.ilop.sthome.data.greenDao.UserInfoBean;
import com.ilop.sthome.mvp.contract.MainContract;
import com.ilop.sthome.mvp.contract.PersonalContract;
import com.ilop.sthome.mvp.model.PersonalModel;
import com.ilop.sthome.mvp.model.common.onModelCallBack;
import com.ilop.sthome.ui.activity.main.StartActivity;
import com.ilop.sthome.ui.dialog.BaseDialog;
import com.ilop.sthome.ui.fragment.DeviceFragment;
import com.ilop.sthome.ui.fragment.MessageFragment;
import com.ilop.sthome.ui.fragment.SceneFragment;
import com.ilop.sthome.utils.greenDao.DeviceDaoUtil;
import com.ilop.sthome.utils.greenDao.SceneDaoUtil;
import com.ilop.sthome.utils.greenDao.UserInfoDaoUtil;
import com.siterwell.familywellplus.R;

import org.json.JSONArray;

import java.util.List;

/**
 * @Author skygge.
 * @Date on 2019-10-09.
 * @Dec:
 */
public class MainPresenter extends BasePresenterImpl<MainContract.IView> implements MainContract.IPresent {

    private Context mContext;
    // 当前Fragment的 角标
    private int mCurrentFragmentIndex;
    private Fragment[] mFragments;
    private int mCurrentCheckedId;
    private PersonalContract.IModel mModel;
    private Handler mHandler;


    public MainPresenter(Context mContext) {
        this.mContext = mContext;
        mFragments = new Fragment[3];
        mModel = new PersonalModel();
        mHandler = new Handler();
    }

    @Override
    public void initHomeFragment() {
        mCurrentFragmentIndex = 0;
        replaceFragment(mCurrentFragmentIndex);
    }

    @Override
    public void initBottomNavigation(RadioButton[] radioButtons) {
        for (int i = 0; i < radioButtons.length; i++) {//循环

            Drawable[] drawables = radioButtons[i].getCompoundDrawables();//通过RadioButton的getCompoundDrawables()方法，拿到图片的drawables,分别是左上右下的图片

            switch (i) {//为每一个drawableTop设置属性setBounds(left,top,right,bottom)
                case 0:
                    drawables[1].setBounds(0, 0, mContext.getResources().getDimensionPixelSize(R.dimen.setting_height),  mContext.getResources().getDimensionPixelSize(R.dimen.setting_height));
                    break;
                case 1:
                    drawables[1].setBounds(0, 0,  mContext.getResources().getDimensionPixelSize(R.dimen.setting_height),  mContext.getResources().getDimensionPixelSize(R.dimen.setting_height));
                    break;
                case 2:
                    drawables[1].setBounds(0, 0,  mContext.getResources().getDimensionPixelSize(R.dimen.setting_height),  mContext.getResources().getDimensionPixelSize(R.dimen.setting_height));
                    break;
                default:
                    break;
            }
            radioButtons[i].setCompoundDrawables(drawables[0], drawables[1], drawables[2], drawables[3]);//将改变了属性的drawable再重新设置回去
        }
        radioButtons[0].setChecked(true);
    }

    @Override
    public int getCurrentCheckedId() {
        return mCurrentCheckedId;
    }

    @Override
    public void replaceFragment(int mCurrentFragmentIndex) {
        for (int i = 0; i < mFragments.length; i++) {
            if (mCurrentFragmentIndex != i) {
                if (mFragments[i] != null) {
                    hideFragment(mFragments[i]);
                }
            }
        }
        Fragment mFragment = mFragments[mCurrentFragmentIndex];
        if (mFragment != null) {
            addAndShowFragment(mFragment);
            setCurChecked(mCurrentFragmentIndex);
        } else {
            newCurrentFragment(mCurrentFragmentIndex);
            setCurChecked(mCurrentFragmentIndex);
        }
    }

    @Override
    public void refreshRequestUserInfo() {
        IoTCredentialManageImpl ioTCredentialManage = IoTCredentialManageImpl.getInstance((Application) mContext.getApplicationContext());
        if (null == ioTCredentialManage) {
            return;
        }
        if (TextUtils.isEmpty(ioTCredentialManage.getIoTToken())) {
            ioTCredentialManage.asyncRefreshIoTCredential(new IoTCredentialListener() {
                @Override
                public void onRefreshIoTCredentialSuccess(IoTCredentialData ioTCredentialData) {
                    onQueryUserInfo(ioTCredentialData.identity);
                }

                @Override
                public void onRefreshIoTCredentialFailed(IoTCredentialManageError ioTCredentialManageError) {
                }
            });
        } else {
            onQueryUserInfo(ioTCredentialManage.getIoTIdentity());
        }
    }

    @Override
    public void onQueryUserInfo(String identifyId) {
        mModel.toQueryUserInfo(identifyId, new onModelCallBack() {
            @Override
            public void onFailure(Exception e) {
            }

            @Override
            public void onResponse(IoTResponse response) {
                mHandler.post(()->{
                    Object data = response.getData();
                    if (data == null) {
                        return;
                    }
                    if (!(data instanceof JSONArray)) {
                        return;
                    }
                    try {
                        List<UserInfoBean> userList = JSON.parseArray(data.toString(), UserInfoBean.class);
                        if (userList.size() > 0) {
                            UserInfoDaoUtil.getInstance().getUserInfoDao().deleteAll();
                            UserInfoDaoUtil.getInstance().getUserInfoDao().insert(userList.get(0));
                            getUserInfo(userList.get(0));
                        }else {
                            getUserInfo(new UserInfoBean());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        });
    }

    @Override
    public void getUserInfo(UserInfoBean userInfoBean) {
        mView.showLoginName(getLoginName());
        mView.showNickName(userInfoBean.getNickName());
        mView.showUserImage(userInfoBean.getAvatarUrl());
    }

    @Override
    public String getLoginName() {
        if (LoginBusiness.isLogin()) {
            UserInfo userInfo = LoginBusiness.getUserInfo();
            String userName = "";
            if (userInfo != null) {
                if (!TextUtils.isEmpty(userInfo.userPhone)) {
                    userName = userInfo.userPhone;
                } else if (!TextUtils.isEmpty(userInfo.userEmail)) {
                    userName = userInfo.userEmail;
                } else {
                    userName = "";
                }
            }
            return userName;
        }
        return null;
    }

    @Override
    public void onLoginOut() {
        BaseDialog mDialog = new BaseDialog(mContext, new OnCallBackToRefresh() {
            @Override
            public void onConfirm() {
                PushManager.getInstance().unbindUser();
                LoginBusiness.logout(new ILogoutCallback() {
                    @Override
                    public void onLogoutSuccess() {
                        UserInfoDaoUtil.getInstance().getUserInfoDao().deleteAll();
                        DeviceDaoUtil.getInstance().deleteAllGateway();
                        Intent intent = new Intent(mContext, StartActivity.class);
                        mView.startActivityByIntent(intent);
                    }

                    @Override
                    public void onLogoutFailed(int code, String error) {
                        mView.showToastMsg(mContext.getString(R.string.failed));
                    }
                });
            }

            @Override
            public void onCancel() {
                mView.closeDrawer();
            }
        });
        mDialog.setTitleAndButton(mContext.getString(R.string.ali_current_account) + ":" + getLoginName(), mContext.getString(R.string.cancel), mContext.getString(R.string.logout));
        mDialog.show();
    }


    // 记录当前 角标
    private void setCurChecked(int mCurrentFragmentIndex) {
        this.mCurrentFragmentIndex = mCurrentFragmentIndex;
        switch (mCurrentFragmentIndex) {
            case 0:
                mCurrentCheckedId = R.id.main_device;
                break;
            case 1:
                mCurrentCheckedId = R.id.main_scene;
                break;
            case 2:
                mCurrentCheckedId = R.id.main_message;
                break;
        }
    }

    //创建 当前 Fragment
    private void newCurrentFragment(int mCurrentFragmentIndex) {
        Fragment fragment = null;
        switch (mCurrentFragmentIndex) {
            case 0:
                fragment = new DeviceFragment();
                break;
            case 1:
                fragment = new SceneFragment();
                break;
            case 2:
                fragment = new MessageFragment();
                break;
        }
        mFragments[mCurrentFragmentIndex] = fragment;
        addAndShowFragment(fragment);
    }

    // 显示 Fragment
    private void addAndShowFragment(Fragment mFragment) {
        if (mFragment.isAdded()) {
            mView.showFragment(mFragment);
        } else {
            mView.addFragment(mFragment);
        }
    }

    // 隐藏Fragment
    private void hideFragment(Fragment mFragment) {
        if (mFragment != null && mFragment.isVisible()) {
            mView.hideFragment(mFragment);
        }
    }
}
