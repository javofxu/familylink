package com.ilop.sthome.mvp.contract;

import com.example.common.mvp.IBasePresenter;
import com.example.common.mvp.IBaseView;
import com.ilop.sthome.mvp.model.common.onModelCallBack;

/**
 * @Author skygge.
 * @Date on 2020-02-05.
 * @Dec:
 */
public interface PersonalContract {

    interface IModel{

        void toQueryUserInfo(String identifyId, onModelCallBack callBack);

        void toUpdateUserInfo(String identityId, String accountMetaV2, onModelCallBack callBack);

        void toCancellationAccount(onModelCallBack callBack);
    }

    interface IView extends IBaseView{

        void showNickName(String nickname);

        void showLoginName(String loginName);

        void showPhone(String phone);

        void showEmail(String email);

        void showUserImg(String path);

        void showToastMsg(String msg);

        void finishActivity();
    }

    interface IPresent extends IBasePresenter<IView>{

        void getUserInfo();

        void onSaveUserInfo(String nickname, String phone, String email, String avatarUrl);

        void onDestroyHandler();
    }
}
