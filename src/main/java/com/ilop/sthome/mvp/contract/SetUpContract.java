package com.ilop.sthome.mvp.contract;

import com.example.common.mvp.IBasePresenter;
import com.example.common.mvp.IBaseView;
import com.ilop.sthome.data.bean.AlarmNotice;
import com.ilop.sthome.mvp.model.common.onModelCallBack;

import java.util.List;

/**
 * @Author skygge.
 * @Date on 2020-02-06.
 * @Dec:
 */
public interface SetUpContract {

    interface IModel {
        /**
         * 拉取设备对应的告警配置列表
         */
        void getDeviceNoticeList(String iotId, onModelCallBack callBack);

        /**
         * 设置设备全量告警的提醒配置
         */
        void setDeviceNoticeReminder(String iotId, boolean noticeEnabled, onModelCallBack callBack);

        /**
         * 设置设备告警提醒配置
         */
        void deviceNoticeSet(String iotId, String eventId,boolean noticeEnabled, onModelCallBack callBack);
    }

    interface IView extends IBaseView{

        void showHasEnabledOpen(boolean open);

        void showNoticeList(List<AlarmNotice> noticeList);

        void withoutNotice();

        void showToastMsg(String msg);
    }

    interface IPresent extends IBasePresenter<IView>{

        void getDeviceNoticeList(String iotId);

        void setDeviceFullNoticeEnabled(String iotId, boolean noticeEnabled);

        void setDeviceNoticeEnabled(String iotId, String eventId, boolean noticeEnabled);

        void modifyPhonePassword();

        void modifyEmailPassword();
    }
}
