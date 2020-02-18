package com.ilop.sthome.utils.greenDao;

import android.database.Cursor;
import android.util.Log;

import com.ilop.sthome.data.greenDao.AutomationBean;
import com.ilop.sthome.data.greenDao.AutomationBeanDao;

import java.util.ArrayList;
import java.util.List;

public class AutomationDaoUtil {

    private static final String TAG = AutomationDaoUtil.class.getSimpleName();

    private volatile static AutomationDaoUtil instance = new AutomationDaoUtil();
    private CommonDaoUtils<AutomationBean> mAutomationUtils;
    private AutomationBeanDao mAutomationDao;

    public static AutomationDaoUtil getInstance(){
        return instance;
    }

    private AutomationDaoUtil() {
        DaoManager mManager = DaoManager.getInstance();
        mAutomationDao= mManager.getDaoSession().getAutomationBeanDao();
        mAutomationUtils = new CommonDaoUtils(AutomationBean.class, mAutomationDao);
    }

    public CommonDaoUtils<AutomationBean> getAutomationDao(){
        return mAutomationUtils;
    }


    /**
     * 用来初始化默认自动化
     * @param auto
     */
    public void addinitAuto(AutomationBean auto){
        int count =  findAutomationCount(auto.getMid(),auto.getDeviceName());
        if(count > 0){
            return;
        }
        mAutomationUtils.insert(auto);
    }

    /**
     * 更新或插入自动化
     * @param auto
     */
    public void insertAutomation(AutomationBean auto){
        if(auto == null || auto.getMid()<=0) {
            return;
        }
        if(findAutomationCount(auto.getMid(), auto.getDeviceName())<=0) {
            mAutomationUtils.insert(auto);
        }else {
            mAutomationUtils.update(auto);
        }
    }

    /**
     * 查询所有自动化
     * @param sid 场景Id
     * @param deviceName 设备deviceName
     * @return
     */
    public List<AutomationBean> findAllAutoBySid(int sid, String deviceName){
        List<AutomationBean> mList = new ArrayList<>();
        try {
            String sql = "select b.* from SCENE_RELATION_BEAN a inner join  AUTOMATION_BEAN b on a.MID = b.MID where a.SID = "+sid+" and a.DEVICE_NAME = '"+deviceName+"' and b.DEVICE_NAME='"+deviceName+"'";
            Cursor cursor = DaoManager.getInstance().getDaoSession().getDatabase().rawQuery(sql, null);
            while (cursor.moveToNext()){
                int mid = cursor.getInt(cursor.getColumnIndex("MID"));
                if(mid<=128){
                    AutomationBean auto = new AutomationBean();
                    auto.setName(cursor.getString(cursor.getColumnIndex("NAME")));
                    auto.setCode(cursor.getString(cursor.getColumnIndex("CODE")));
                    auto.setMid(cursor.getInt(cursor.getColumnIndex("MID")));
                    auto.setDeviceName(cursor.getString(cursor.getColumnIndex("DEVICE_NAME")));
                    mList.add(auto);
                }
            }
            cursor.close();
        }catch (NullPointerException e){
            Log.e(TAG, "findAllAutoBySid: "+ e.getMessage());
        }
        return mList;
    }

    /**
     * 根据ID查询自动化
     * @param mid
     * @param deviceName
     * @return
     */
    public AutomationBean findAutomationByMid(int mid, String deviceName){
        return mAutomationDao.queryBuilder()
                .where(AutomationBeanDao.Properties.Mid.eq(mid),
                        AutomationBeanDao.Properties.DeviceName.eq(deviceName))
                .build()
                .unique();
    }

    /**
     * 查询所有自动化ID
     * @param deviceName 设备deviceName
     * @return
     */
    public List<Integer> findAllMid(String deviceName){
        List<AutomationBean> mList = mAutomationUtils.queryByQueryBuilder(
                AutomationBeanDao.Properties.DeviceName.eq(deviceName));
        List<Integer> mMid = new ArrayList<>();
        for (AutomationBean auto: mList) {
            mMid.add(auto.getMid());
        }
        return mMid;
    }

    /**
     * 查询所有非默认自动化
     * @param deviceName
     * @return
     */
    public List<AutomationBean> findAllAutoWithoutDefault(String deviceName){
        return mAutomationDao.queryBuilder()
                .where(AutomationBeanDao.Properties.DeviceName.eq(deviceName),
                        AutomationBeanDao.Properties.Code.isNotNull())
                .orderAsc(AutomationBeanDao.Properties.Mid)
                .build()
                .list();
    }

    /**
     * 所有自动化数量
     * @param mid 自动化ID
     * @param deviceName 设备deviceName
     * @return
     */
    public int findAutomationCount(int mid, String deviceName){
        return mAutomationUtils.queryByQueryBuilder(
                AutomationBeanDao.Properties.Mid.eq(mid),
                AutomationBeanDao.Properties.DeviceName.eq(deviceName)
        ).size();
    }

    /**
     * 根据ID删除自动化
     * @param mid
     * @param deviceName
     */
    public void deleteByMid(int mid, String deviceName){
        mAutomationUtils.deleteByQuery(AutomationBeanDao.Properties.Mid.eq(mid),
                AutomationBeanDao.Properties.DeviceName.eq(deviceName));
    }
}
