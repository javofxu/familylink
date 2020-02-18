package com.ilop.sthome.mvp.contract;

import com.example.common.mvp.IBasePresenter;
import com.example.common.mvp.IBaseView;

/**
 * @Author skygge.
 * @Date on 2020-02-06.
 * @Dec:
 */
public interface SetUpContract {

    interface IView extends IBaseView{

        void showToastMsg(String msg);
    }

    interface IPresent extends IBasePresenter<IView>{

        void modifyPhonePassword();

        void modifyEmailPassword();
    }
}
