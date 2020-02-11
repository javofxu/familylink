package com.ilop.sthome.mvp.contract;

import com.example.common.mvp.IBasePresenter;
import com.example.common.mvp.IBaseView;
import com.ilop.sthome.data.bean.CheckDeviceBean;

import java.util.List;

/**
 * @Author skygge.
 * @Date on 2019-10-10.
 * @Dec:
 */
public interface AssessContract {

    interface IView extends IBaseView{

        void refresh(int mScore, List<CheckDeviceBean> deviceInfoBeans);
    }

    interface IPresent extends IBasePresenter<IView>{

        void startAnalyze();

        void detection();
    }
}
