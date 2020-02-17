package com.ilop.sthome.utils.greenDao;

import com.ilop.sthome.data.greenDao.SceneRelationBean;
import com.ilop.sthome.data.greenDao.SceneRelationBeanDao;

import java.util.ArrayList;
import java.util.List;

public class SceneRelationDaoUtil {

    private volatile static SceneRelationDaoUtil instance = new SceneRelationDaoUtil();
    private CommonDaoUtils<SceneRelationBean> mSceneRelationUtils;
    private SceneRelationBeanDao mSceneRelationDao;

    public static SceneRelationDaoUtil getInstance(){
        return instance;
    }

    private SceneRelationDaoUtil() {
        DaoManager mManager = DaoManager.getInstance();
        mSceneRelationDao = mManager.getDaoSession().getSceneRelationBeanDao();
        mSceneRelationUtils = new CommonDaoUtils(SceneRelationBean.class, mSceneRelationDao);
    }

    public CommonDaoUtils<SceneRelationBean> getSceneRelationDao(){
        return mSceneRelationUtils;
    }

    /**
     * 插入数据
     * @param relationBean
     * @return
     */
    public void insertSceneRelation(SceneRelationBean relationBean) {
        if(relationBean == null ||relationBean.getSid()<0) {
            return;
        }
        if (!isHasRelation(relationBean.getMid(), relationBean.getSid(), relationBean.getDeviceName())){
            mSceneRelationUtils.insert(relationBean);
        }else {
            mSceneRelationUtils.update(relationBean);
        }
    }

    /**
     * 查询关联列表
     * @param deviceName 设备DeviceName
     * @param sid 场景Id
     * @return
     */
    public List<SceneRelationBean> findRelationsBySid(String deviceName, int sid){
        return mSceneRelationDao.queryBuilder()
                .where(SceneRelationBeanDao.Properties.DeviceName.eq(deviceName),
                        SceneRelationBeanDao.Properties.Sid.eq(sid))
                .orderAsc(SceneRelationBeanDao.Properties.Mid)
                .build()
                .list();
    }

    /**
     * 查询所有自动化ID
     * @param deviceName 设备DeviceName
     * @param sid 场景ID
     * @return
     */
    public List<Integer> findMidBySid(String deviceName, int sid){
        List<SceneRelationBean> mList = findRelationsBySid(deviceName, sid);
        List<Integer> mMid = new ArrayList<>();
        for (SceneRelationBean relation: mList) {
            mMid.add(relation.getMid());
        }
        return mMid;
    }

    /**
     * 查询关联
     * @param mid 自动化ID
     * @param sid 场景Id
     * @param deviceName 设备DeviceName
     * @return
     */
    public SceneRelationBean findRelationByMidAndSid(int mid, int sid, String deviceName){
        return mSceneRelationDao.queryBuilder()
                .where(SceneRelationBeanDao.Properties.Mid.eq(mid),
                        SceneRelationBeanDao.Properties.Sid.eq(sid),
                        SceneRelationBeanDao.Properties.DeviceName.eq(deviceName))
                .build()
                .unique();
    }

    /**
     * 删除某场景下的所有快捷关联
     * @param sid
     * @param deviceName
     */
    public void deleteAllRelation(int sid, String deviceName){
        mSceneRelationUtils.deleteByQuery(
                SceneRelationBeanDao.Properties.Sid.eq(sid),
                SceneRelationBeanDao.Properties.DeviceName.eq(deviceName));
    }

    /**
     * 删除某自动化下的所有快捷关联
     * @param mid
     * @param deviceName
     */
    public void deleteAllRelation2(int mid, String deviceName){
        mSceneRelationUtils.deleteByQuery(
                SceneRelationBeanDao.Properties.Mid.eq(mid),
                SceneRelationBeanDao.Properties.DeviceName.eq(deviceName));
    }

    /**
     * 删除某自动化关联
     * @param mid
     * @param sid
     * @param deviceName
     */
    public void deleteRelation(int mid, int sid, String deviceName){
        mSceneRelationUtils.deleteByQuery(
                SceneRelationBeanDao.Properties.Mid.eq(mid),
                SceneRelationBeanDao.Properties.Sid.eq(sid),
                SceneRelationBeanDao.Properties.DeviceName.eq(deviceName));
    }

    /**
     * 是否存在此关联
     * @param mid
     * @param sid
     * @param deviceName
     * @return
     */
    public boolean isHasRelation(int mid, int sid, String deviceName) {
        return findRelationByMidAndSid(mid, sid, deviceName)!=null;
    }
}
