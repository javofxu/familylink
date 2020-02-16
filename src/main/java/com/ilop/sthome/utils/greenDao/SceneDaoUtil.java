package com.ilop.sthome.utils.greenDao;

import com.ilop.sthome.data.greenDao.SceneBean;
import com.ilop.sthome.data.greenDao.SceneBeanDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public long insertScene(SceneBean scene){
        if(scene == null || scene.getSid()<0 || !scene.getColor().contains("F")) {
            return -1L;
        }
        if(!isHasScene(scene.getSid(), scene.getDeviceName())) {
            mSceneUtils.insert(scene);
            return 1L;
        }else {
            mSceneUtils.update(scene);
            return 1L;
        }
    }

    /**
     * 查询所有场景
     * @param deviceName 设备deviceName
     * @return 场景列表
     */
    public List<SceneBean> findAllScene(String deviceName){
        return mSceneDao.queryBuilder()
                .where(SceneBeanDao.Properties.DeviceName.eq(deviceName))
                .orderAsc(SceneBeanDao.Properties.Sid)
                .list();
    }

    /**
     * 通过场景ID查询所有场景
     * @param deviceName 设备deviceName
     * @return 场景列表
     */
    public List<SceneBean> findAllSceneBySid(int sid, String deviceName){
        return mSceneDao.queryBuilder()
                .where(SceneBeanDao.Properties.DeviceName.eq(deviceName),
                        SceneBeanDao.Properties.Sid.eq(sid))
                .orderAsc(SceneBeanDao.Properties.Sid)
                .list();
    }

    /**
     * find by chose
     * @param deviceName 设备deviceName
     * @return 场景
     */
    public SceneBean findSceneByChoice(String deviceName){
        List<SceneBean> sceneList = mSceneUtils.queryByQueryBuilder(SceneBeanDao.Properties.Choice.eq(1),
                SceneBeanDao.Properties.DeviceName.eq(deviceName));
        if (sceneList.size()>0){
            return sceneList.get(0);
        }
        return null;
    }

    /**
     * 通过场景ID获取场景
     * @param sid 场景Id
     * @param deviceName 设备deviceName
     * @return 场景
     */
    public SceneBean findSceneBySid(int sid, String deviceName){
        List<SceneBean> sceneList = mSceneUtils.queryByQueryBuilder(SceneBeanDao.Properties.Sid.eq(sid),
                SceneBeanDao.Properties.DeviceName.eq(deviceName));
        if (sceneList.size()>0){
            return sceneList.get(0);
        }
        return null;
    }

    /**
     * 获取所有场景数量
     * @param scene 场景
     * @return 场景数量
     */
    public int findAllSceneCount(SceneBean scene){
        return mSceneDao.queryBuilder()
                .where(SceneBeanDao.Properties.DeviceName.eq(scene.getDeviceName()),
                        SceneBeanDao.Properties.Sid.eq(scene.getSid()))
                .list()
                .size();
    }

    /**
     * 获取场景开关状态
     * @param deviceName 设备deviceName
     * @return
     */
    public Map<Integer,String> findAllSceneByMap(String deviceName){
        Map<Integer,String> scene = new HashMap<>();
        List<SceneBean> sceneList = findAllScene(deviceName);
        for (SceneBean mScene: sceneList) {
            scene.put(mScene.getSid(), mScene.getModleName());
        }
        return scene;
    }

    /**
     * 获取所有场景名称
     * @param deviceName 设备deviceName
     * @return
     */
    public List<String> findAllSceneName(String deviceName){
        List<String> name = new ArrayList<>();
        List<SceneBean> sceneList = findAllScene(deviceName);
        for (SceneBean mScene: sceneList) {
            name.add(mScene.getModleName());
        }
        return name;
    }

    /**
     * 查询所有场景ID
     * @param deviceName 设备deviceName
     * @return 场景ID
     */
    public List<Integer> findAllSceneId(String deviceName){
        List<SceneBean> mList = findAllScene(deviceName);
        List<Integer> mScene = new ArrayList<>();
        for (SceneBean scene:mList) {
            mScene.add(scene.getSid());
        }
        return mScene;
    }

    /**
     * 更新场景颜色
     * @param sid 场景ID
     * @param color 场景颜色
     * @param deviceName 设备deviceName
     */
    public void updateColor(int sid, String color, String deviceName) {
        List<SceneBean> mList = mSceneUtils.queryByQueryBuilder(
                SceneBeanDao.Properties.Sid.eq(sid),
                SceneBeanDao.Properties.DeviceName.eq(deviceName)
        );
        if (mList.size()>0){
            SceneBean mScene = mList.get(0);
            mScene.setColor(color);
            mSceneUtils.update(mScene);
        }
    }

    /**
     * 选择该场景为当前场景
     * @param sid 场景ID
     * @param deviceName 设备deviceName
     */
    public void updateChoice(int sid, String deviceName){
        List<SceneBean> mList = mSceneUtils.queryByQueryBuilder(
                SceneBeanDao.Properties.DeviceName.eq(deviceName)
        );
        for (SceneBean scene : mList) {
            scene.setChoice(0);
            mSceneUtils.update(scene);
        }
        List<SceneBean> mList2 = mSceneUtils.queryByQueryBuilder(
                SceneBeanDao.Properties.Sid.eq(sid),
                SceneBeanDao.Properties.DeviceName.eq(deviceName)
        );
        if (mList2.size()>0){
            SceneBean mScene = mList2.get(0);
            mScene.setChoice(1);
            mSceneUtils.update(mScene);
        }
    }

    /**
     * 删除选中场景
     * @param sid 场景ID
     * @param deviceName 设备deviceName
     */
    public void deleteBySid(int sid, String deviceName){
        mSceneUtils.deleteByQuery(SceneBeanDao.Properties.Sid.eq(sid),
                SceneBeanDao.Properties.DeviceName.eq(deviceName));
    }

    /**
     * 判断是否有该场景
     * @param sid 场景ID
     * @param deviceName 设备deviceName
     * @return 布偶值
     */
    public boolean isHasScene(int sid,String deviceName) {
        List<SceneBean> sceneList = findAllSceneBySid(sid, deviceName);
        return sceneList.size()>0;
    }
}
