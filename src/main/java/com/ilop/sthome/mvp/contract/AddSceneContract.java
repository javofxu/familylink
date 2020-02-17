package com.ilop.sthome.mvp.contract;

import com.example.common.mvp.IBasePresenter;
import com.example.common.mvp.IBaseView;
import com.ilop.sthome.data.greenDao.AutomationBean;
import com.ilop.sthome.data.greenDao.DeviceInfoBean;

import java.util.List;

public interface AddSceneContract {

    interface IView extends IBaseView{

        void showGatewayList(List<DeviceInfoBean> deviceList);

        void withoutGateway();

        void showToastMsg(String msg);

        void showProgress();

        void disProgress();
    }

    interface IPresent extends IBasePresenter<IView>{

        void getDefaultColorCode(String code);

        void getGatewayList();

        void onSaveScene(String name, int gateway, List<AutomationBean> bean);

        void onSaveSuccess();

        void onSaveFailed();

        int getSid();

    }
}
