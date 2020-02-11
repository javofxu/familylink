package com.ilop.sthome.mvp.contract;

import com.example.common.mvp.IBasePresenter;
import com.example.common.mvp.IBaseView;
import com.ilop.sthome.data.device.Device;

/**
 * @Author skygge.
 * @Date on 2020-02-10.
 * @Dec:
 */
public interface BindContract {

    interface IView extends IBaseView{

        void bindSuccess();

        void alreadyBind();

        void showToastMsg(String msg);
    }

    interface IPresent extends IBasePresenter<IView>{

        /**
         * 查询产品信息
         * @param device 产品信息
         */
        void queryProductInfo(Device device);

        /**
         * 绑定设备
         * @param device 产品信息
         * @param roomName 房间名称
         */
        void bindDevice(Device device, String roomName, String gatewayName);

        /**
         * 网关重命名
         * @param iotId 设备iot
         * @param gatewayName 网关名称
         */
        void renameGateway(String iotId, String roomName, String gatewayName);

        /**
         * 创建房间
         * @param roomName
         */
        void createRoom(String roomName, String gatewayName);

        /**
         * 获取网关列表
         */
        void getGatewayList();

        /**
         * 更新房间信息
         */
        void updateRoom(String gatewayList);
    }

}
