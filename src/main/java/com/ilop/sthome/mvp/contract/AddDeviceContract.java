package com.ilop.sthome.mvp.contract;

import com.example.common.mvp.IBasePresenter;
import com.example.common.mvp.IBaseView;
import com.ilop.sthome.data.event.EventRefreshDevice;

/**
 * @author skygge
 * @Date on 2020-02-12.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：添加子设备
 */
public interface AddDeviceContract {

    interface IView extends IBaseView{

        void showGuideText(String text1, String text2);

        void showAddSuccess();

        void showAddFailed();

        void finishActivity();
    }

    interface IPresent extends IBasePresenter<IView>{

        void onInsertDevice();

        void onReplaceDevice(int deviceID);

        void onAddSuccess(EventRefreshDevice event);

        void onModifyDevice(int deviceId);

        void onModifySuccess();

        void createRoom(String roomName, String deviceList);

        void updateRoom(String deviceList);

        void onCancel();
    }
}
