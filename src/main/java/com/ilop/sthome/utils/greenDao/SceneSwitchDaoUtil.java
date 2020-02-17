package com.ilop.sthome.utils.greenDao;

import com.ilop.sthome.data.greenDao.SceneSwitchBean;
import com.ilop.sthome.data.greenDao.SceneSwitchBeanDao;

import java.util.List;

public class SceneSwitchDaoUtil {

    private volatile static SceneSwitchDaoUtil instance = new SceneSwitchDaoUtil();
    private CommonDaoUtils<SceneSwitchBean> mSceneSwitchUtils;
    private SceneSwitchBeanDao mSceneSwitchDao;

    public static SceneSwitchDaoUtil getInstance(){
        return instance;
    }

    private SceneSwitchDaoUtil() {
        DaoManager mManager = DaoManager.getInstance();
        mSceneSwitchDao= mManager.getDaoSession().getSceneSwitchBeanDao();
        mSceneSwitchUtils = new CommonDaoUtils(SceneSwitchBean.class, mSceneSwitchDao);
    }

    public CommonDaoUtils<SceneSwitchBean> getSceneSwitchDao(){
        return mSceneSwitchUtils;
    }

    /**
     * 插入或更新已存在的场景开关
     * @param sceneSwitch
     * @return
     */
    public void insertSwitch(SceneSwitchBean sceneSwitch){
        if(sceneSwitch == null ||sceneSwitch.getDeviceId()<=0) {
            return;
        }
        if (!isHasSwitch(sceneSwitch.getSrc_sid(), sceneSwitch.getDeviceId(), sceneSwitch.getDeviceName())){
            mSceneSwitchUtils.insert(sceneSwitch);
        }else {
            mSceneSwitchUtils.update(sceneSwitch);
        }
    }

    /**
     * 根据deviceId查询场景开关
     * @param sid
     * @param deviceName
     * @return
     */
    public List<SceneSwitchBean> findSwitchBySid(int sid, String deviceName){
        return  mSceneSwitchUtils.queryByQueryBuilder(
                SceneSwitchBeanDao.Properties.Src_sid.eq(sid),
                SceneSwitchBeanDao.Properties.DeviceName.eq(deviceName));
    }

    /**
     * 根据deviceId查询场景开关
     * @param sid
     * @param deviceId
     * @param deviceName
     * @return
     */
    public SceneSwitchBean findSwitchByDeviceId(int sid, int deviceId, String deviceName){
        return mSceneSwitchDao.queryBuilder()
                .where(SceneSwitchBeanDao.Properties.Src_sid.eq(sid),
                        SceneSwitchBeanDao.Properties.DeviceId.eq(deviceId),
                        SceneSwitchBeanDao.Properties.DeviceName.eq(deviceName))
                .build()
                .unique();
    }

    /**
     * 删除某场景下的所有场景开关
     * @param sid
     * @param deviceName
     */
    public void deleteAllSwitch(int sid, String deviceName) {
        mSceneSwitchUtils.deleteByQuery(
                SceneSwitchBeanDao.Properties.Src_sid.eq(sid),
                SceneSwitchBeanDao.Properties.DeviceName.eq(deviceName)
        );
    }

    public boolean isHasSwitch(int sid, int deviceId, String deviceName) {
        return findSwitchByDeviceId(sid, deviceId, deviceName)!=null;
    }
}
