package com.ilop.sthome.mvp.contract;

import android.content.Intent;

import com.example.common.mvp.IBasePresenter;
import com.example.common.mvp.IBaseView;
import com.ilop.sthome.data.greenDao.DeviceInfoBean;

import java.util.List;

/**
 * @author skygge
 * @date 2020-01-02.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：
 */
public interface GatewayContract {

    interface IView extends IBaseView{

        void refreshSubList(List<DeviceInfoBean> list);

        void refreshNoList();

        void skipActivity(Intent intent);

        void refreshState(String name, int state, String mode);

    }

    interface IPresent extends IBasePresenter<IView> {

        void findAllSubDevice();

        void getDeviceState();

        void clickItem(DeviceInfoBean deviceInfoBean);
    }

}
