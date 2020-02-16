package com.ilop.sthome.utils.greenDao;

import com.ilop.sthome.data.greenDao.SceneBean;
import com.ilop.sthome.data.greenDao.SceneBeanDao;

import java.util.List;

public class SceneDaoUtil {

    private volatile static SceneDaoUtil instance = new SceneDaoUtil();
    private CommonDaoUtils<SceneBean> mSceneUtils;
    private SceneBeanDao mSceneDao;

    public static SceneDaoUtil getInstance(){
        return instance;
    }

    private SceneDaoUtil() {
        DaoManager mManager = DaoManager.getInstance();
        mSceneDao = mManager.getDaoSession().getSceneBeanDao();
        mSceneUtils = new CommonDaoUtils(SceneBean.class, mSceneDao);
    }

    public CommonDaoUtils<SceneBean> getSceneDao(){
        return mSceneUtils;
    }

    /**
     * 用来初始化系统场景数据(在家，离家，睡眠)
     * @param scene
     * @return
     */
    public int addInitScene(SceneBean scene){
        int row = -1;
        int count =  findAllSceneCount(scene);

        if(count > 0){
            return row;
        }
        mSceneUtils.insert(scene);
        return 1;
    }

    public SceneBean findIdByChoice(String deviceName){
        List<SceneBean> sceneList = mSceneUtils.queryByQueryBuilder(SceneBeanDao.Properties.Choice.eq(1),
                SceneBeanDao.Properties.DeviceName.eq(deviceName));
        if (sceneList.size()>0){
            return sceneList.get(0);
        }
        return null;
    }

    /**
     * 获取所有场景数量
     * @param scene 场景
     * @return
     */
    public int findAllSceneCount(SceneBean scene){
        return mSceneDao.queryBuilder()
                .where(SceneBeanDao.Properties.DeviceName.eq(scene.getDeviceName()),
                        SceneBeanDao.Properties.Sid.eq(scene.getSid()))
                .list()
                .size();
    }
}
