package com.ilop.sthome.mvp.contract;

import android.content.Intent;

import com.example.common.mvp.IBasePresenter;
import com.example.common.mvp.IBaseView;
import com.ilop.sthome.data.bean.DeviceInfoBean;

import java.util.List;

/**
 * @Author skygge.
 * @Date on 2019-10-17.
 * @Dec:
 */
public interface SubDeviceContract {

    interface IView extends IBaseView{

        void getAllSubDevice(List<DeviceInfoBean> device);

        void withOutSubDevice();

        void getDeviceInfo(int id, String nickname);

        void skipActivity(Intent intent);

        void showToastMsg(String msg);

        void showProgress();

        void hideSoftKeyboard();
    }

    interface IPresent extends IBasePresenter<IView>{

        void getAllSubDevice(String deviceName);

        void clickItem(DeviceInfoBean deviceInfoBean);

        void longClickItem(DeviceInfoBean deviceInfoBean);
    }
}
