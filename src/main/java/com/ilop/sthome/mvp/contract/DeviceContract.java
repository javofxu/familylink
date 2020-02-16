package com.ilop.sthome.mvp.contract;

import android.content.Intent;

import com.example.common.mvp.IBasePresenter;
import com.example.common.mvp.IBaseView;
import com.ilop.sthome.data.greenDao.DeviceInfoBean;
import com.ilop.sthome.data.greenDao.RoomBean;

import java.util.List;

/**
 * @Author skygge.
 * @Date on 2019-10-10.
 * @Dec:
 */
public interface DeviceContract {

    interface IView extends IBaseView{

        void showRoomList(List<RoomBean> room);

        void noDeviceList();

        void skipActivity(Intent intent);

        void showMsg(String msg);

    }

    interface IPresent extends IBasePresenter<IView>{

        /**
         * 获取网关列表
         */
        void getGatewayListByAccount();

        /**
         * 获取房间列表
         * @param pageNo
         * @param pageSize
         */
        void getRoomListByAccount(int pageNo, int pageSize);

        /**
         * 处理数据（来自房间）
         * @param gateway 网关列表
         * @param camera 摄像头列表
         * @param subDevice 子设备列表
         */
        void processingData(String userId, int roomId, String roomName, String gateway, String camera, String subDevice);

        /**
         * 获取房间信息列表
         */
        void getRoomList();

        /**
         * 子设备点击事件
         * @param deviceInfo
         */
        void clickChildItem(DeviceInfoBean deviceInfo);

    }
}
