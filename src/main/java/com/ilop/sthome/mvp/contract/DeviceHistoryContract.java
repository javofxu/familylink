package com.ilop.sthome.mvp.contract;

import com.example.common.mvp.IBasePresenter;
import com.example.common.mvp.IBaseView;
import com.ilop.sthome.data.bean.DeviceHistoryBean;

import java.util.List;

/**
 * @author skygge
 * @date 2020-01-14.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：
 */
public interface DeviceHistoryContract {

    interface IView extends IBaseView{

        void showHistory(List<DeviceHistoryBean> history);

        void withoutData();
    }

    interface present extends IBasePresenter<IView>{

        void getAllGateway();

        void getGatewayByName(String deviceName);

        void getHistoryList(boolean isGateway);

        void deleteGatewayAlarm(int num);

        void deleteHistory(String deviceName);

        void sendSync(int num);

        void sendGateWaySync(int num);
    }
}
