package com.ilop.sthome.utils.greenDao;

import com.ilop.sthome.data.greenDao.WarnBean;
import com.ilop.sthome.data.greenDao.WarnBeanDao;

import java.util.List;

public class WarnDaoUtil {

    private volatile static WarnDaoUtil instance = new WarnDaoUtil();
    private CommonDaoUtils<WarnBean> mWarnUtils;

    public static WarnDaoUtil getInstance(){
        return instance;
    }

    private WarnDaoUtil() {
        DaoManager mManager = DaoManager.getInstance();
        WarnBeanDao mWarnDao= mManager.getDaoSession().getWarnBeanDao();
        mWarnUtils = new CommonDaoUtils(WarnBean.class, mWarnDao);
    }

    public CommonDaoUtils<WarnBean> getWarnDao(){
        return mWarnUtils;
    }

    public List<WarnBean> findWarnByDeviceName(String deviceName){
        return mWarnUtils.queryByQueryBuilder(WarnBeanDao.Properties.DeviceName.eq(deviceName));
    }

    public void deleteWarnByDeviceName(String deviceName){
        mWarnUtils.deleteByQuery(WarnBeanDao.Properties.DeviceName.eq(deviceName));
    }
}
