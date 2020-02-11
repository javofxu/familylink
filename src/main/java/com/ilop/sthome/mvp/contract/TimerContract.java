package com.ilop.sthome.mvp.contract;

import com.example.common.mvp.IBasePresenter;
import com.example.common.mvp.IBaseView;
import com.ilop.sthome.data.bean.TimerGatewayAliBean;

import java.util.List;

/**
 * @Author skygge.
 * @Date on 2019-10-22.
 * @Dec:
 */
public interface TimerContract {

    interface IView extends IBaseView{

        void getTimerList(List<TimerGatewayAliBean> gatewayTimerList);

        void withoutTimer();

        void showToastMsg(String msg);

        void showProgress();
    }

    interface IPresent extends IBasePresenter<IView>{

        void getTimerList();

        void synchronous();

        void switchClick(int position);

        void refreshSwitch();

        void deleteTimer(int position);

        void refreshDelete();
    }
}
