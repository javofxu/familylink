package com.ilop.sthome.utils.greenDao;

import com.ilop.sthome.data.greenDao.SceneRelationBean;
import com.ilop.sthome.data.greenDao.SceneRelationBeanDao;

import java.util.List;

public class SceneRelationDaoUtil {

    private volatile static SceneRelationDaoUtil instance = new SceneRelationDaoUtil();
    private CommonDaoUtils<SceneRelationBean> mSceneRelationUtils;

    public static SceneRelationDaoUtil getInstance(){
        return instance;
    }

    private SceneRelationDaoUtil() {
        DaoManager mManager = DaoManager.getInstance();
        SceneRelationBeanDao mSceneRelationDao= mManager.getDaoSession().getSceneRelationBeanDao();
        mSceneRelationUtils = new CommonDaoUtils(SceneRelationBean.class, mSceneRelationDao);
    }

    public CommonDaoUtils<SceneRelationBean> getSceneRelationDao(){
        return mSceneRelationUtils;
    }

    public long insertSceneRelation(SceneRelationBean shortcutBean) {
        if(shortcutBean == null ||shortcutBean.getSid()<0) {
            return -1L;
        }
        if (!isHasRelation(shortcutBean.getMid(), shortcutBean.getSid(), shortcutBean.getDeviceName())){
            mSceneRelationUtils.insert(shortcutBean);
        }else {
            mSceneRelationUtils.update(shortcutBean);
        }
        return 1L;
    }

    public SceneRelationBean findSwitchByDeviceId(int mid, int sid, String deviceName){
        List<SceneRelationBean> scene = mSceneRelationUtils.queryByQueryBuilder(
                SceneRelationBeanDao.Properties.Mid.eq(mid),
                SceneRelationBeanDao.Properties.Sid.eq(sid),
                SceneRelationBeanDao.Properties.DeviceName.eq(deviceName));
        if (scene.size()>0){
            return scene.get(0);
        }
        return null;
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
        return findSwitchByDeviceId(mid, sid, deviceName)!=null;
    }
}
