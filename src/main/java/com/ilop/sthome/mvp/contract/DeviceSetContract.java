package com.ilop.sthome.mvp.contract;

import android.content.Intent;

import com.example.common.mvp.IBasePresenter;
import com.example.common.mvp.IBaseView;

/**
 * @author skygge
 * @Date on 2020-02-13.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：
 */
public interface DeviceSetContract {

    interface IView extends IBaseView{

        void isGatewayView();

        void isSubDeviceView();

        void showDeviceName(String name);

        void startActivityByIntent(Intent intent);

        void showToastMsg(String msg);

        void showQRCode(String qrCode);

        void hideSoftBoard();
    }

    interface IPresent extends IBasePresenter<IView>{

        void onRefreshView();

        void showViewByDeviceId(int deviceId);

        void setDeviceName(int deviceId);

        void renameGateway(String itoId, String nickName);

        void onUnBindDevice();

        void onReplaceDevice();

        void refreshQRCode(String iotId);

        void onRouterToOTA();

        void onDownloadIns();

        void onModifySuccess();

        void onDeleteSuccess();
    }
}
