package com.ilop.sthome.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ilop.sthome.data.bean.SysModelAliBean;
import com.litesuits.android.log.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
@class SysmodelAliDAO
@autor henry
@time 2019/4/18 2:22 PM
@email xuejunju_4595@qq.com
*/
public class SysmodelAliDAO {
    private final String  TAG = "SysmodelAliDAO";
    private SysDBAli sys;
    Context context;
    public SysmodelAliDAO(Context context){
        try {
            this.context = context;
            this.sys = new SysDBAli(context);
        }catch (NullPointerException e){
            e.printStackTrace();
        }


    }
//add system modle
    public int add(SysModelAliBean sy){
        int row = -1;
        SQLiteDatabase db = this.sys.getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            cv.put("color",sy.getColor());
            cv.put("name", sy.getModleName());
            cv.put("choice",sy.getChoice());
            cv.put("deviceName",sy.getDeviceName());
            cv.put("sid",sy.getSid());
            row = (int) db.insert("sysMTable", null, cv);

        }catch (NullPointerException e){
            Log.i(TAG,"no choosed device");
        }finally {
            db.close();
            return row;
        }


    }

    /**
     * methodname:addinit
     * 作者：Henry on 2017/3/8 9:56
     * 邮箱：xuejunju_4595@qq.com
     * 参数:sy
     * 返回:int
     * 用来初始化系统场景数据(在家，离家，睡眠)
     */
    public int addinit (SysModelAliBean sy){
        int row = -1;
        int count =  findInitSysModelCount(sy);

        if(count > 0){
            return row;
        }

        SQLiteDatabase db = this.sys.getWritableDatabase();
    /* ContentValues */
            ContentValues cv = new ContentValues();
            cv.put("name", sy.getModleName());
            cv.put("choice",sy.getChoice());
            cv.put("deviceName",sy.getDeviceName());
            cv.put("sid",sy.getSid());
            cv.put("color",sy.getColor());
            row = (int) db.insert("sysMTable", null, cv);

            db.close();
            return row;


    }


    //find by Choice
    public SysModelAliBean findIdByChoice(String deviceName){

        SQLiteDatabase db = this.sys.getWritableDatabase();
        SysModelAliBean sys2 = null;
        try {
            Cursor cursor = db.rawQuery("select * from sysMTable where choice =? and deviceName = ?",new String[]{String.valueOf(1),deviceName});
            if(cursor.moveToFirst()) {
                sys2 = new SysModelAliBean();
                sys2.setModleName(cursor.getString(cursor.getColumnIndex("name")));
                sys2.setSid(cursor.getInt(cursor.getColumnIndex("sid")));
                sys2.setChoice(cursor.getInt(cursor.getColumnIndex("choice")));
                sys2.setDeviceName(cursor.getString(cursor.getColumnIndex("deviceName")));
                sys2.setColor(cursor.getString(cursor.getColumnIndex("color")));
            }
        }catch (NullPointerException e){
            Log.i(TAG,"no choosed device");
        }finally {
            db.close();
            return sys2;
        }

    }

    //find by name
    public SysModelAliBean findIdByName(String name, String deviceName){

        SQLiteDatabase db = this.sys.getWritableDatabase();
        SysModelAliBean sys2 = new SysModelAliBean();
        try {
            Cursor cursor = db.rawQuery("select * from sysMTable where name =? and deviceName = ?",new String[]{name,deviceName});
            if(cursor.moveToFirst()) {
                sys2.setModleName(cursor.getString(cursor.getColumnIndex("name")));
                sys2.setSid(cursor.getInt(cursor.getColumnIndex("sid")));
                sys2.setChoice(cursor.getInt(cursor.getColumnIndex("choice")));
                sys2.setDeviceName(cursor.getString(cursor.getColumnIndex("deviceName")));
                sys2.setColor(cursor.getString(cursor.getColumnIndex("color")));
            }
        }catch (NullPointerException e){
            Log.i(TAG,"no choosed device");
        }finally {
            db.close();
            return sys2;
        }

    }




    //find by name
    public SysModelAliBean findBySid(int sid, String deviceName){
        SysModelAliBean sys2 = null;
        SQLiteDatabase db = this.sys.getWritableDatabase();
        try {
            Cursor cursor = db.rawQuery("select * from sysMTable where sid =? and deviceName = ?",new String[]{String.valueOf(sid),deviceName});
            if(cursor.moveToFirst()) {
                sys2 = new SysModelAliBean();
                sys2.setModleName(cursor.getString(cursor.getColumnIndex("name")));
                sys2.setSid(cursor.getInt(cursor.getColumnIndex("sid")));
                sys2.setChoice(cursor.getInt(cursor.getColumnIndex("choice")));
                sys2.setDeviceName(cursor.getString(cursor.getColumnIndex("deviceName")));
                sys2.setColor(cursor.getString(cursor.getColumnIndex("color")));
            }

        }catch (NullPointerException e){
            Log.i(TAG,"no choosed device");
        }finally {
            db.close();
            return sys2;
        }
    }

    //find all sysMTable
    public List<SysModelAliBean> findAllSys(String deviceName){
        List<SysModelAliBean> sys2 = new ArrayList<SysModelAliBean>();
        SQLiteDatabase db = this.sys.getWritableDatabase();

        try {
          Cursor cursor = db.rawQuery("select * from sysMTable where deviceName = '"+deviceName+"' order by sid",null);
          while (cursor.moveToNext()){
            SysModelAliBean sb = new SysModelAliBean();
            sb.setModleName(cursor.getString(cursor.getColumnIndex("name")));
            sb.setSid(cursor.getInt(cursor.getColumnIndex("sid")));
            sb.setChoice(cursor.getInt(cursor.getColumnIndex("choice")));
            sb.setDeviceName(cursor.getString(cursor.getColumnIndex("deviceName")));
            sb.setColor(cursor.getString(cursor.getColumnIndex("color")));
            sys2.add(sb);
        }

        }catch (NullPointerException e){
            Log.i(TAG,"no choosed device");
        }finally {
            db.close();
            return sys2;
        }

    }

    //find all sysMTable
    public Map<Integer,String> findAllSysByHash(String deviceName){
        Map<Integer,String> sys2 = new HashMap<Integer,String>();
        SQLiteDatabase db = this.sys.getWritableDatabase();

        try {
            Cursor cursor = db.rawQuery("select * from sysMTable where deviceName = '"+deviceName+"' order by sid",null);
            while (cursor.moveToNext()){
                sys2.put(cursor.getInt(cursor.getColumnIndex("sid")),cursor.getString(cursor.getColumnIndex("name")));
            }

        }catch (NullPointerException e){
            Log.i(TAG,"no choosed device");
        }finally {
            db.close();
            return sys2;
        }

    }

    //find all sysMTable
    public List<String> findAllSysName(String deviceName){
        List<String> sys2 = new ArrayList<String>();
        SQLiteDatabase db = this.sys.getWritableDatabase();

        try {
            Cursor cursor = db.rawQuery("select * from sysMTable where deviceName = '"+deviceName+"' order by sid",null);
            while (cursor.moveToNext()){
                sys2.add(cursor.getString(cursor.getColumnIndex("name")));
            }

        }catch (NullPointerException e){
            Log.i(TAG,"no choosed device");
        }finally {
            db.close();
            return sys2;
        }

    }

    //修改系统组名称操作
    public void updateName(int sid,String name,String deviceName) {
        SQLiteDatabase db =this.sys.getWritableDatabase();
        try {
            String where = "sid = ? and deviceName = ?";
            String[] whereValue = {String.valueOf(sid),deviceName};
            ContentValues cv = new ContentValues();
            cv.put("name", name);
            db.update("sysMTable", cv, where, whereValue);
            Log.i("update sysMTable over", "data " + name);
        }catch (NullPointerException e){
            Log.i(TAG,"no choosed device");
        }finally {
            db.close();
        }

    }
    //修改颜色操作
    public void updateColor(int sid, String color,String deviceName) {
        SQLiteDatabase db =this.sys.getWritableDatabase();
        try {
            String where = "sid = ? and deviceName = ?";
            String[] whereValue = {String.valueOf(sid),deviceName};
            ContentValues cv = new ContentValues();
            cv.put("color", color);
            db.update("sysMTable", cv, where, whereValue);
            Log.i("update sysMTable over", "data " + color);
        }catch (NullPointerException e){
            Log.i(TAG,"no choosed device");
        }finally {
            db.close();
        }

    }


    /**
     * updateChoice:选择该场景为当前场景
     * 作者：Henry on 2017/3/8 10:26
     * 邮箱：xuejunju_4595@qq.com
     * 参数:
     * 返回:
     */
    public void updateChoice(int sid,String deviceName){


        SQLiteDatabase db =this.sys.getWritableDatabase();
        try {
            String where = "deviceName = ?";
            String[] whereValue = {deviceName};
            ContentValues cv = new ContentValues();
            cv.put("choice",0);
            db.update("sysMTable", cv, where, whereValue);

            String where2 = "sid = ? and deviceName = ?";
            String[] whereValue2 = {String.valueOf(sid),deviceName};
            ContentValues cv2 = new ContentValues();
            cv2.put("choice",1);
            db.update("sysMTable", cv2, where2, whereValue2);
        }catch (NullPointerException e){
            Log.i(TAG,"no choosed device");
        }finally {
            db.close();
        }

    }

    /**
     * @param sid
     */
    public void delete(int sid,String deviceName)
    {

        SQLiteDatabase db =this.sys.getWritableDatabase();
        try {
            String where = "sid = ? and deviceName = ?";
            String[] whereValue ={ String.valueOf(sid),deviceName };
            db.delete("sysMTable", where, whereValue);
        }catch (NullPointerException e){
            Log.i(TAG,"no choosed device");
        }finally {
            db.close();
        }


    }

    /**
     * 删除所有场景组
     */
    public void deleteAll(String deviceName) {

        SQLiteDatabase db = this.sys.getWritableDatabase();
        try {

            String where = "deviceName = '"+deviceName+"'";
            db.delete("sysMTable", where, null);
        }catch (NullPointerException e){
            Log.i(TAG,"no choosed device");
        }finally {
            db.close();
        }

    }

    /**
     * @return sid List
     */
    public List<Integer> findAllSysmodelSid(String deviceName){
        SQLiteDatabase db = this.sys.getWritableDatabase();
        List<Integer> list = new ArrayList<Integer>();
        try {
            Cursor cursor = db.rawQuery("select sid from sysMTable where deviceName = '"+deviceName+"' group by sid order by sid",null);
            while (cursor.moveToNext()){
                list.add( cursor.getInt(cursor.getColumnIndex("sid")));
            }
        }catch (NullPointerException e){
            Log.i(TAG,"no choosed device");
        }finally {
            db.close();
            return list;
        }



    }


    /**
     * 更新场景到数据库
     * @param modelBean
     * @return
     */
    public long insertSysmodel(SysModelAliBean modelBean) {

        if(modelBean == null || modelBean.getSid()<0 || modelBean.getColor().indexOf("F")==-1) {
            return -1L;
        }
        SQLiteDatabase db = this.sys.getWritableDatabase();
        ContentValues values = null;
        try {

            values = new ContentValues();
            values.put("name", modelBean.getModleName());
            values.put("sid", modelBean.getSid());
            values.put("deviceName",modelBean.getDeviceName());
            values.put("color",modelBean.getColor());
            if(!isHasSysmodel(modelBean.getSid(),modelBean.getDeviceName())) {
                values.put("choice",modelBean.getChoice());
                db = this.sys.getWritableDatabase();
                return db.insert("sysMTable", null, values);
            } else {
                db = this.sys.getWritableDatabase();
                return db.update("sysMTable",values , "sid =" + modelBean.getSid()+" and deviceName = '"+modelBean.getDeviceName()+"'",null);
            }
        }
          catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (values != null) {
                values.clear();
            }
            db.close();
        }
        return -1L;
    }

    /**
     * isHasSysmodel:判断是否有该sid的场景
     * 作者：Henry on 2017/3/7 13:02
     * 邮箱：xuejunju_4595@qq.com
     * 参数:sid
     * 返回:boolean
     */
    public boolean isHasSysmodel(int sid,String deviceName) {

        List<SysModelAliBean> list = findAllBeanSysmodelSid(sid,deviceName);
        return list != null && list.size() != 0;
    }
    /**
     * @return sid List
     */
    public List<SysModelAliBean> findAllBeanSysmodelSid(int sid, String deviceName){
        List<SysModelAliBean> list = new ArrayList<SysModelAliBean>();
        SQLiteDatabase db = this.sys.getWritableDatabase();
        try {
        Cursor cursor = db.rawQuery("select * from sysMTable where deviceName = '"+deviceName+"' and sid = "+sid+" group by sid order by sid",null);
        while (cursor.moveToNext()){
            SysModelAliBean sb = new SysModelAliBean();
            sb.setModleName(cursor.getString(cursor.getColumnIndex("name")));
            sb.setSid(cursor.getInt(cursor.getColumnIndex("sid")));
            sb.setChoice(cursor.getInt(cursor.getColumnIndex("choice")));
            sb.setDeviceName(cursor.getString(cursor.getColumnIndex("deviceName")));
            sb.setColor(cursor.getString(cursor.getColumnIndex("color")));
            list.add(sb);
        }


        }catch (NullPointerException e){
            Log.i(TAG,"no choosed device");
        }finally {
            db.close();
            return list;
        }
    }


    /**
     * @return 所有的场景数量
     */
    public int findSysModelCount(String deviceName){
        SQLiteDatabase db = this.sys.getWritableDatabase();
        int a = 0;
        try {
            Cursor cursor = db.rawQuery("select count(*) from sysMTable where deviceName = '"+deviceName+"'",null);
            if(cursor.moveToFirst()) {
                a = cursor.getInt(cursor.getColumnIndex("count(*)"));
            }
        }catch (NullPointerException e){
            Log.i(TAG,"no choosed device");
        }finally {
            db.close();
            return a;
        }

    }

    /**
     * @return 所有的场景数量
     */
    public int findInitSysModelCount(SysModelAliBean bean){
        SQLiteDatabase db = this.sys.getWritableDatabase();
        int a = 0;
        try {
            db = this.sys.getWritableDatabase();
            Cursor cursor = db.rawQuery("select count(*) from sysMTable where deviceName = '"+bean.getDeviceName()+"' and sid ="+bean.getSid(),null);
            if(cursor.moveToFirst()) {
                a = cursor.getInt(cursor.getColumnIndex("count(*)"));
            }
        }catch (NullPointerException e){
            Log.i(TAG,"no choosed device");
        }finally {
            db.close();
            return a;
        }

    }
}
