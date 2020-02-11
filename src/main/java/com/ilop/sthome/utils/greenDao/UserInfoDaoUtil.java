package com.ilop.sthome.utils.greenDao;

import com.ilop.sthome.data.greenDao.UserInfoBean;
import com.ilop.sthome.data.greenDao.UserInfoBeanDao;

public class UserInfoDaoUtil {

    private volatile static UserInfoDaoUtil instance = new UserInfoDaoUtil();
    private CommonDaoUtils<UserInfoBean> mUserInfoUtils;

    public static UserInfoDaoUtil getInstance(){
        return instance;
    }

    private UserInfoDaoUtil() {
        DaoManager mManager = DaoManager.getInstance();
        UserInfoBeanDao mUserInfoDao= mManager.getDaoSession().getUserInfoBeanDao();
        mUserInfoUtils = new CommonDaoUtils(UserInfoBean.class, mUserInfoDao);
    }

    public CommonDaoUtils<UserInfoBean> getUserInfoDao(){
        return mUserInfoUtils;
    }
}
