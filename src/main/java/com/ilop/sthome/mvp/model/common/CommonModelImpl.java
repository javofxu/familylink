package com.ilop.sthome.mvp.model.common;

/**
 * @Author skygge.
 * @Date on 2020-02-08.
 * @Dec:
 */
public interface CommonModelImpl {

    /**
     * 获取网关列表
     * @param callBack 返回信息
     */
    void onGetGatewayList(onModelCallBack callBack);

    /**
     * 网关重命名
     * @param iotId 设备iot
     * @param nickName 昵称
     * @param callBack 返回信息
     */
    void onRenameGateway(String iotId, String nickName, onModelCallBack callBack);

    /**
     * 解除绑定
     * @param iotId 设备iot
     * @param callBack 返回信息
     */
    void onUnbindGateway(String iotId, onModelCallBack callBack);

    /**
     * 刷新网关二维码
     * @param iotId 设备iot
     * @param callBack 返回信息
     */
    void onRefreshQRCode(String iotId, onModelCallBack callBack);

    /**
     * 查询房间列表列表
     * @param pageNo 当前页号，从1开始的页序号
     * @param pageSize 页大小，单页的item数量上限
     * @param callBack 返回信息
     */
    void onQueryRoomList(int pageNo, int pageSize, onModelCallBack callBack);

    /**
     * 创建一个空的房间
     * @param roomId 房间ID
     * @param roomName 房间名称
     * @param callBack 返回信息
     */
    void onCreateRoom(String roomId, String roomName, String gateway, String device, String camera, onModelCallBack callBack);

    /**
     * 修改房间名称
     * @param userId 虚拟用户ID
     * @param roomName 房间名称
     * @param callBack
     */
    void onUpdateRoomName(String userId, String roomName, onModelCallBack callBack);

    /**
     * 修改房间内网关列表信息
     * @param userId 虚拟用户ID
     * @param gatewayList 房间内网关列表
     * @param callBack 返回信息
     */
    void onUpdateRoomByGateway(String userId, String gatewayList, onModelCallBack callBack);

    /**
     * 修改房间内摄像头列表信息
     * @param userId 虚拟用户ID
     * @param cameraList 房间内摄像头列表
     * @param callBack 返回信息
     */
    void onUpdateRoomByCamera(String userId, String cameraList, onModelCallBack callBack);

    /**
     * 修改房间内子设备列表信息
     * @param userId 虚拟用户ID
     * @param subDeviceList 房间内子设备列表
     * @param callBack 返回信息
     */
    void onUpdateRoomBySubDevice(String userId, String subDeviceList, onModelCallBack callBack);

    /**
     * 删除房间
     * @param userId 虚拟用户ID
     * @param callBack 返回信息
     */
    void onDeleteRoom(String userId, onModelCallBack callBack);
}
