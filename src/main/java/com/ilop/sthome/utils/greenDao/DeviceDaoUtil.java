package com.ilop.sthome.utils.greenDao;


import android.text.TextUtils;

import com.ilop.sthome.data.enums.DevType;
import com.ilop.sthome.data.greenDao.DeviceInfoBean;
import com.ilop.sthome.data.greenDao.DeviceInfoBeanDao;

import java.util.ArrayList;
import java.util.List;

public class DeviceDaoUtil {

    private volatile static DeviceDaoUtil instance = new DeviceDaoUtil();

    private DeviceInfoBeanDao mDeviceDao;
    private CommonDaoUtils<DeviceInfoBean> mDeviceUtils;

    public static DeviceDaoUtil getInstance(){
        return instance;
    }

    private DeviceDaoUtil() {
        DaoManager mManager = DaoManager.getInstance();
        mDeviceDao= mManager.getDaoSession().getDeviceInfoBeanDao();
        mDeviceUtils = new CommonDaoUtils(DeviceInfoBean.class, mDeviceDao);
    }

    public CommonDaoUtils<DeviceInfoBean> getDeviceDao(){
        return mDeviceUtils;
    }

    /**
     * 查找所有设备
     * @return list
     */
    public List<DeviceInfoBean> findAllDevice(){
        return mDeviceDao.queryBuilder()
                .orderAsc(DeviceInfoBeanDao.Properties.DeviceName, DeviceInfoBeanDao.Properties.Device_ID)
                .list();
    }

    /**
     * 查找网关设备
     * @return list
     */
    public List<DeviceInfoBean> findAllGateway(){
        String productKey = DevType.EE_GATEWAY.getProductkey();
        return mDeviceDao.queryBuilder()
                .where(DeviceInfoBeanDao.Properties.Device_ID.eq(0),
                        DeviceInfoBeanDao.Properties.ProductKey.eq(productKey))
                .orderAsc(DeviceInfoBeanDao.Properties.DeviceName, DeviceInfoBeanDao.Properties.Device_ID)
                .build()
                .list();
    }

    /**
     * 查找网关设备
     * @return DeviceInfoBean
     */
    public DeviceInfoBean findGatewayByDeviceName(String deviceName){
        return mDeviceDao.queryBuilder()
                .where(DeviceInfoBeanDao.Properties.DeviceName.eq(deviceName),
                        DeviceInfoBeanDao.Properties.Device_ID.eq(0))
                .build()
                .unique();

    }

    /**
     * 查找设备
     * @return DeviceInfoBean
     */
    public DeviceInfoBean findByDeviceId(String deviceName, int deviceId){
        return  mDeviceDao.queryBuilder()
                .where(DeviceInfoBeanDao.Properties.DeviceName.eq(deviceName),
                        DeviceInfoBeanDao.Properties.Device_ID.eq(deviceId))
                .build()
                .unique();
    }

    /**
     * 查找所有子设备
     * @return list
     */
    public List<DeviceInfoBean> findAllSubDevice(String deviceName){
        return mDeviceDao.queryBuilder()
                .where(DeviceInfoBeanDao.Properties.Device_ID.notEq(0)
                        ,DeviceInfoBeanDao.Properties.DeviceName.eq(deviceName))
                .orderAsc(DeviceInfoBeanDao.Properties.Device_ID)
                .build()
                .list();
    }

    /**
     * 查找网上的网关设备
     * @return list
     */
    public List<DeviceInfoBean> findAllWifiDevice(){
        return mDeviceDao.queryBuilder()
                .where(DeviceInfoBeanDao.Properties.Device_ID.eq(0))
                .orderAsc(DeviceInfoBeanDao.Properties.DeviceName, DeviceInfoBeanDao.Properties.Device_ID)
                .build()
                .list();
    }

    /**
     * 查找所有本分享的设备
     * @return list
     */
    public List<DeviceInfoBean> findAllWifiShareDevice(){
        return mDeviceDao.queryBuilder()
                .where(DeviceInfoBeanDao.Properties.Device_ID.eq(0), DeviceInfoBeanDao.Properties.Owned.notEq(1))
                .orderAsc(DeviceInfoBeanDao.Properties.DeviceName, DeviceInfoBeanDao.Properties.Device_ID)
                .build()
                .list();
    }

    /**
     * 查找所有网关设备的deviceName
     * @return deviceName
     */
    public List<String> findAllGatewayDeviceName(){
        List<DeviceInfoBean> mList = mDeviceDao.queryBuilder()
                .where(DeviceInfoBeanDao.Properties.Device_ID.eq(0), DeviceInfoBeanDao.Properties.Owned.notEq(1))
                .orderAsc(DeviceInfoBeanDao.Properties.DeviceName, DeviceInfoBeanDao.Properties.Device_ID)
                .build()
                .list();
        List<String> deviceName = new ArrayList<>();
        for (DeviceInfoBean device:mList) {
            deviceName.add(device.getDeviceName());
        }
        return deviceName;
    }

    /**
     * 查找输入设备
     * @param deviceName
     * @return
     */
    public List<DeviceInfoBean> findOutputDevice(String deviceName){
        return mDeviceDao.queryBuilder()
                .where(DeviceInfoBeanDao.Properties.DeviceName.eq(deviceName))
                .whereOr(DeviceInfoBeanDao.Properties.Device_type.like("%0__"),
                        DeviceInfoBeanDao.Properties.Device_type.like("%1__"),
                        DeviceInfoBeanDao.Properties.Device_type.like("%2__"),
                        DeviceInfoBeanDao.Properties.Device_type.like("%6__"),
                        DeviceInfoBeanDao.Properties.Device_type.eq("1213"))
                .build()
                .list();
    }

    /**
     * 查找输出设备
     * @param deviceName
     * @return
     */
    public List<DeviceInfoBean> findInputDevice(String deviceName){
        return mDeviceDao.queryBuilder()
                .where(DeviceInfoBeanDao.Properties.DeviceName.eq(deviceName),
                        DeviceInfoBeanDao.Properties.Device_type.notEq("1213"),
                        DeviceInfoBeanDao.Properties.Device_type.like("%2__"))
                .build()
                .list();
    }

    /**
     * 根据条件删除
     * @param deviceName
     * @param deviceId
     */
    public void deleteByDeviceName(String deviceName, int deviceId){
        mDeviceUtils.deleteByQuery(DeviceInfoBeanDao.Properties.DeviceName.eq(deviceName),
                DeviceInfoBeanDao.Properties.Device_ID.eq(deviceId));
    }

    /**
     * 更新设备昵称
     * @param deviceName
     * @param nickname
     */
    public void updateGatewayName(String deviceName, String nickname){
        List<DeviceInfoBean> deviceList = mDeviceDao.queryBuilder()
                .where(DeviceInfoBeanDao.Properties.DeviceName.eq(deviceName),
                        DeviceInfoBeanDao.Properties.Device_ID.eq(0))
                .orderAsc(DeviceInfoBeanDao.Properties.DeviceName, DeviceInfoBeanDao.Properties.Device_ID)
                .build()
                .list();
        if (deviceList.size()>0){
            DeviceInfoBean deviceInfo = deviceList.get(0);
            deviceInfo.setNickName(nickname);
            mDeviceUtils.update(deviceInfo);
        }
    }

    /**
     * 更新当前场景
     * @param deviceName
     * @param sid 场景ID
     */
    public void updateGatewayCurrentSid(String deviceName, int sid){
        DeviceInfoBean deviceInfo = findGatewayByDeviceName(deviceName);
        deviceInfo.setScene_id(sid);
        mDeviceUtils.update(deviceInfo);
    }

    /**
     * 获取当前场景
     * @param deviceName
     * @return scene-id
     */
    public int findGatewayCurrentSid(String deviceName){
        DeviceInfoBean deviceInfo = findGatewayByDeviceName(deviceName);
        return deviceInfo.getScene_id();
    }

    /**
     * 插入或更新已存在的网关
     * @param deviceInfo
     */
    public void insertGateway(DeviceInfoBean deviceInfo) {
        if (!HasThisDevice(deviceInfo.getDeviceName(), deviceInfo.getDevice_ID())){
            mDeviceUtils.insert(deviceInfo);
        }else {
            mDeviceUtils.update(deviceInfo);
        }
    }

    /**
     * 插入或更新已存在的子设备
     * @param deviceInfo
     */
    public void insertSubDevice(DeviceInfoBean deviceInfo) {
        if (!HasThisDevice(deviceInfo.getDeviceName(), deviceInfo.getDevice_ID())){
            mDeviceUtils.insert(deviceInfo);
        }else {
            mDeviceUtils.update(deviceInfo);
        }
    }

    /**
     * 查询是否存在该设备
     * @param deviceName
     * @param deviceId
     * @return
     */
    public boolean HasThisDevice(String deviceName, int deviceId){
        List<DeviceInfoBean> mDevice = mDeviceUtils.queryByQueryBuilder(
                DeviceInfoBeanDao.Properties.DeviceName.eq(deviceName),
                DeviceInfoBeanDao.Properties.Device_ID.eq(deviceId)
        );
        return mDevice.size()>0;
    }

    /**
     * 判断此种类型的设备是否存在
     * @param deviceType 设备类型
     * @return int
     */
    public int isDevTypeExists(String deviceType){
        if(TextUtils.isEmpty(deviceType) || deviceType.length()!=4){
            return 0;
        }
        String where = "%" + deviceType.substring(1);
        return mDeviceDao.queryBuilder()
                .where(DeviceInfoBeanDao.Properties.Device_type.like(where))
                .list()
                .size();
    }
}
