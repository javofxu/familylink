package com.ilop.sthome.utils.greenDao;

import com.ilop.sthome.data.greenDao.SceneSwitchBean;
import com.ilop.sthome.data.greenDao.SceneSwitchBeanDao;

import java.util.List;

public class SceneSwitchDaoUtil {

    private volatile static SceneSwitchDaoUtil instance = new SceneSwitchDaoUtil();
    private CommonDaoUtils<SceneSwitchBean> mSceneSwitchUtils;

    public static SceneSwitchDaoUtil getInstance(){
        return instance;
    }

    private SceneSwitchDaoUtil() {
        DaoManager mManager = DaoManager.getInstance();
        SceneSwitchBeanDao mSceneSwitchDao= mManager.getDaoSession().getSceneSwitchBeanDao();
        mSceneSwitchUtils = new CommonDaoUtils(SceneSwitchBean.class, mSceneSwitchDao);
    }

    public CommonDaoUtils<SceneSwitchBean> getSceneSwitchDao(){
        return mSceneSwitchUtils;
    }

    public long insertSwitch(SceneSwitchBean sceneSwitch){
        if(sceneSwitch == null ||sceneSwitch.getDeviceId()<=0) {
            return -1L;
        }
        if (!isHasSwitch(sceneSwitch.getSrc_sid(), sceneSwitch.getDeviceId(), sceneSwitch.getDeviceName())){
            mSceneSwitchUtils.insert(sceneSwitch);
        }else {
            mSceneSwitchUtils.update(sceneSwitch);
        }
        return 1L;
    }

    public SceneSwitchBean findSwitchByDeviceId(int sid, int deviceId, String deviceName){
        List<SceneSwitchBean> sceneSwitch = mSceneSwitchUtils.queryByQueryBuilder(
                SceneSwitchBeanDao.Properties.Src_sid.eq(sid),
                SceneSwitchBeanDao.Properties.DeviceId.eq(deviceId),
                SceneSwitchBeanDao.Properties.DeviceName.eq(deviceName));
        if (sceneSwitch.size()>0){
            return sceneSwitch.get(0);
        }
        return null;
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
