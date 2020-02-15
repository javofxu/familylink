package com.ilop.sthome.utils.greenDao;


import com.ilop.sthome.data.greenDao.DeviceInfoBean;
import com.ilop.sthome.data.greenDao.DeviceInfoBeanDao;

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

    public List<DeviceInfoBean> findAllDevice(){
        return mDeviceDao.queryBuilder()
                .orderAsc(DeviceInfoBeanDao.Properties.Device_ID)
                .list();
    }

    public List<DeviceInfoBean> findAllSubDevice(String deviceName){
        return mDeviceDao.queryBuilder()
                .where(DeviceInfoBeanDao.Properties.DeviceName.eq(deviceName),
                    DeviceInfoBeanDao.Properties.Device_ID.notIn(0))
                .orderAsc(DeviceInfoBeanDao.Properties.Device_ID)
                .list();
    }
}
