package com.ilop.sthome.mvp.contract;

import com.example.common.mvp.IBasePresenter;
import com.example.common.mvp.IBaseView;
import com.ilop.sthome.data.bean.SubDeviceHistoryBean;

import java.util.List;

/**
 * @author skygge
 * @date 2020-01-15.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：
 */
public interface SubDeviceHistoryContract {

    interface IView extends IBaseView{

        void showHistory(List<SubDeviceHistoryBean> historyBean);

        void withoutData();

    }

    interface present extends IBasePresenter<IView>{

        void getDeviceInfo(String deviceName, int deviceId);

        void getHistoryList();

        void syncSubAlarm(int page, int deviceId);

        void deleteSubAlarm(int deviceId);

        void deleteHistory();
    }
}
