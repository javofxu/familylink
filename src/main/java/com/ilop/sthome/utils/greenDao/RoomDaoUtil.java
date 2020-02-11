package com.ilop.sthome.utils.greenDao;

import com.ilop.sthome.data.greenDao.RoomBean;
import com.ilop.sthome.data.greenDao.RoomBeanDao;

import java.util.List;

public class RoomDaoUtil {

    private volatile static RoomDaoUtil instance = new RoomDaoUtil();
    private CommonDaoUtils<RoomBean> mRoomUtils;

    public static RoomDaoUtil getInstance(){
        return instance;
    }

    private RoomDaoUtil() {
        DaoManager mManager = DaoManager.getInstance();
        RoomBeanDao mRoomDao= mManager.getDaoSession().getRoomBeanDao();
        mRoomUtils = new CommonDaoUtils(RoomBean.class, mRoomDao);
    }

    public CommonDaoUtils<RoomBean> getRoomDao(){
        return mRoomUtils;
    }

    public List<RoomBean> findRoomByName(String name){
        return mRoomUtils.queryByQueryBuilder(RoomBeanDao.Properties.Room_name.eq(name));
    }
}
