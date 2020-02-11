package com.ilop.sthome.mvp.present;

import android.content.Context;
import android.os.Handler;

import com.aliyun.iot.aep.sdk.apiclient.callback.IoTResponse;
import com.example.common.mvp.BasePresenterImpl;
import com.example.common.utils.SpUtil;
import com.ilop.sthome.data.greenDao.UserInfoBean;
import com.ilop.sthome.mvp.contract.PersonalContract;
import com.ilop.sthome.mvp.model.PersonalModel;
import com.ilop.sthome.mvp.model.common.onModelCallBack;
import com.ilop.sthome.utils.greenDao.UserInfoDaoUtil;
import com.siterwell.familywellplus.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @Author skygge.
 * @Date on 2020-02-05.
 * @Dec:
 */
public class PersonalPresenter extends BasePresenterImpl<PersonalContract.IView> implements PersonalContract.IPresent {

    private Context mContext;
    private UserInfoBean mUserInfoBean;
    private PersonalContract.IModel mModel;
    private Handler mHandler = new Handler();

    public PersonalPresenter(Context mContext) {
        this.mContext = mContext;
        mModel = new PersonalModel();
    }

    @Override
    public void getUserInfo() {
        mUserInfoBean = UserInfoDaoUtil.getInstance().getUserInfoDao().queryAll().get(0);
        mView.showNickName(mUserInfoBean.getNickName());
        mView.showLoginName(mUserInfoBean.getLoginName());
        mView.showPhone(mUserInfoBean.getPhone());
        mView.showEmail(mUserInfoBean.getEmail());
        mView.showUserImg(mUserInfoBean.getAvatarUrl());
    }


    @Override
    public void onSaveUserInfo(String nickname, String phone, String email, String avatarUrl) {
        String app_key = SpUtil.getString(mContext, "app_key");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("loginName" , mUserInfoBean.getLoginName());
            jsonObject.put("phone" , phone);
            jsonObject.put("email" , email);
            jsonObject.put("nickName" , nickname);
            jsonObject.put("appKey" , app_key);
            jsonObject.put("avatarUrl" , avatarUrl);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String json = jsonObject.toString();
        mModel.toUpdateUserInfo(mUserInfoBean.getIdentityId(), json, new onModelCallBack() {
            @Override
            public void onFailure(Exception e) {
                mHandler.post(() -> mView.showToastMsg(mContext.getString(R.string.failed)));
            }

            @Override
            public void onResponse(IoTResponse response) {
                mHandler.post(() -> {
                    mView.showToastMsg(mContext.getString(R.string.success));
                    mView.finishActivity();
                });
            }
        });
    }

    @Override
    public void onDestroyHandler() {
        mHandler = null;
    }
}
