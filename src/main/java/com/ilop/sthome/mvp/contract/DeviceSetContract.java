package com.ilop.sthome.mvp.contract;

import android.content.Intent;

import com.example.common.mvp.IBasePresenter;
import com.example.common.mvp.IBaseView;
import com.ilop.sthome.data.bean.AlarmNotice;

import java.util.List;

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

        void showNoticeList(List<AlarmNotice> noticeList);

        void withoutNotice();

        void showHasEnabledOpen(boolean open);

        void startActivityByIntent(Intent intent);

        void showToastMsg(String msg);

        void showQRCode(String qrCode);
    }

    interface IPresent extends IBasePresenter<IView>{

        void onRefreshView();

        void showViewByDeviceId(int deviceId);

        void setDeviceName();

        void getDeviceNoticeList();

        void setDeviceFullNoticeEnabled(boolean noticeEnabled);

        void setDeviceNoticeEnabled(String eventId, boolean noticeEnabled);

        void onUnBindDevice();

        void onReplaceDevice();

        void refreshQRCode(String iotId);

        void onRouterToOTA();

        void onDownloadIns();

        void onDeleteSuccess();
    }
}
