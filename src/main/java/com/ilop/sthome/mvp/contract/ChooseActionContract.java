package com.ilop.sthome.mvp.contract;

import android.content.Intent;

import com.example.common.mvp.IBasePresenter;
import com.example.common.mvp.IBaseView;
import com.ilop.sthome.data.greenDao.DeviceInfoBean;

import java.util.List;

/**
 * @Author skygge.
 * @Date on 2019-10-23.
 * @Dec:
 */
public interface ChooseActionContract {

    interface IView extends IBaseView{

        void getDeviceList(List<DeviceInfoBean> device);

        void withoutData();

        void startActivityByIntent(Intent intent);

        void finishActivity();
    }

    interface IPresent extends IBasePresenter<IView>{

        void getDeviceInList(String deviceName);

        void getDeviceOutList(String deviceName);

        void onItemClick(int position, boolean isInput, boolean isUpdate);
    }
}
