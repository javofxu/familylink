package com.ilop.sthome.utils.greenDao;

import com.ilop.sthome.data.greenDao.CameraBean;
import com.ilop.sthome.data.greenDao.CameraBeanDao;

public class CameraDaoUtil {

    private volatile static CameraDaoUtil instance = new CameraDaoUtil();
    private CommonDaoUtils<CameraBean> mCameraUtils;

    public static CameraDaoUtil getInstance(){
        return instance;
    }

    private CameraDaoUtil() {
        DaoManager mManager = DaoManager.getInstance();
        CameraBeanDao mCameraDao= mManager.getDaoSession().getCameraBeanDao();
        mCameraUtils = new CommonDaoUtils(CameraBean.class, mCameraDao);
    }

    public CommonDaoUtils<CameraBean> getCameraDao(){
        return mCameraUtils;
    }
}
