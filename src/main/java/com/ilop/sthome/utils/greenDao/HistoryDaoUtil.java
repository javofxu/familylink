package com.ilop.sthome.utils.greenDao;

import com.ilop.sthome.data.greenDao.HistoryBean;
import com.ilop.sthome.data.greenDao.HistoryBeanDao;

import java.util.List;

public class HistoryDaoUtil {

    private volatile static HistoryDaoUtil instance = new HistoryDaoUtil();
    private CommonDaoUtils<HistoryBean> mHistoryUtils;

    public static HistoryDaoUtil getInstance(){
        return instance;
    }

    private HistoryDaoUtil() {
        DaoManager mManager = DaoManager.getInstance();
        HistoryBeanDao mHistoryDao= mManager.getDaoSession().getHistoryBeanDao();
        mHistoryUtils = new CommonDaoUtils(HistoryBean.class, mHistoryDao);
    }

    public CommonDaoUtils<HistoryBean> getHistoryDao(){
        return mHistoryUtils;
    }

    public List<HistoryBean> getHistoryByNameAndId(String deviceName, int deviceId){
        return mHistoryUtils.queryByQueryBuilder(HistoryBeanDao.Properties.DeviceName.eq(deviceName),
                HistoryBeanDao.Properties.Device_id.eq(deviceId));
    }

    public void deleteHistoryByNameAndId(String deviceName, int deviceId){
        mHistoryUtils.deleteByQuery(HistoryBeanDao.Properties.DeviceName.eq(deviceName),
                HistoryBeanDao.Properties.Device_id.eq(deviceId));
    }
}
