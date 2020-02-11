package com.ilop.sthome.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ilop.sthome.data.bean.ShortcutAliBean;
import com.litesuits.android.log.Log;

import java.util.ArrayList;
import java.util.List;

/*
@class ShortcutAliDAO
@autor henry
@time 2019/4/18 4:02 PM
@email xuejunju_4595@qq.com
*/
public class ShortcutAliDAO {
    private final String TAG = "ShortcutAliDAO";
    private SysDBAli sys;
    Context context;
    public ShortcutAliDAO(Context context){

        try {
            this.context = context;
            this.sys = new SysDBAli(context);
        }catch (NullPointerException e){
             e.printStackTrace();
        }
    }


    public List<ShortcutAliBean> findShorcutsByeqid(String deviceName, int eqid){
        SQLiteDatabase db = this.sys.getWritableDatabase();
        List<ShortcutAliBean> list = new ArrayList<ShortcutAliBean>();
        try {
            Cursor cursor = db.rawQuery("select * from gs584relationshiptable where deviceName = '"+deviceName+"' and eqid ="+eqid,null);
            while (cursor.moveToNext()){
                ShortcutAliBean eq = new ShortcutAliBean();
                eq.setDelay(cursor.getInt(cursor.getColumnIndex("delay")));
                eq.setEqid(cursor.getInt(cursor.getColumnIndex("eqid")));
                eq.setDes_sid(cursor.getInt(cursor.getColumnIndex("action")));
                eq.setSrc_sid(cursor.getInt(cursor.getColumnIndex("sid")));
                eq.setDeviceName(deviceName);
                list.add(eq);
            }
        }catch (NullPointerException e){
            Log.i(TAG,"no choosed gateway");
        }finally {
            db.close();
            return list;
        }
    }

    public List<ShortcutAliBean> findShorcutsBysid(String deviceName, int sid){
        SQLiteDatabase db = this.sys.getWritableDatabase();
        List<ShortcutAliBean> list = new ArrayList<ShortcutAliBean>();
        try {
            Cursor cursor = db.rawQuery("select * from gs584relationshiptable where deviceName = '"+deviceName+"' and sid ="+sid,null);
            while (cursor.moveToNext()){
                ShortcutAliBean eq = new ShortcutAliBean();
                eq.setDelay(cursor.getInt(cursor.getColumnIndex("delay")));
                eq.setEqid(cursor.getInt(cursor.getColumnIndex("eqid")));
                eq.setDes_sid(cursor.getInt(cursor.getColumnIndex("action")));
                eq.setSrc_sid(cursor.getInt(cursor.getColumnIndex("sid")));
                eq.setDeviceName(deviceName);
                list.add(eq);
            }
        }catch (NullPointerException e){
            Log.i(TAG,"no choosed gateway");
        }finally {
            db.close();
            return list;
        }
    }


    public ShortcutAliBean findShortcutByeqid(int sid, int eqid, String deviceName){
        SQLiteDatabase db = this.sys.getWritableDatabase();
        ShortcutAliBean eq = null;
        try {
            Cursor cursor = db.rawQuery("select a.* from gs584relationshiptable a where a.sid="+sid+" and a.eqid ="+eqid+" and a.deviceName = '"+deviceName+"'",null);
            if(cursor.moveToFirst()) {
                eq = new ShortcutAliBean();
                eq.setDelay(cursor.getInt(cursor.getColumnIndex("delay")));
                eq.setEqid(cursor.getInt(cursor.getColumnIndex("eqid")));
                eq.setDes_sid(cursor.getInt(cursor.getColumnIndex("action")));
                eq.setSrc_sid(cursor.getInt(cursor.getColumnIndex("sid")));
                eq.setDeviceName(cursor.getString(cursor.getColumnIndex("deviceName")));
            }
        }catch (NullPointerException e){
            Log.i(TAG,"no choosed gateway");
        }finally {
            db.close();
            return eq;
        }
    }
    //删除某场景下的某条快捷关联
    public void deleteShortcurtByEqid(String deviceName,int eqid)
    {
        SQLiteDatabase db = this.sys.getWritableDatabase();
        try {
            String where = "deviceName = '"+deviceName+"' and  eqid = "+eqid+"";
            db.delete("gs584relationshiptable", where, null);
        }catch (NullPointerException e){
            Log.i(TAG,"no choosed gateway");
        }finally {
            db.close();
        }
    }

    //删除某场景下的某条快捷关联
    public void deleteShortcurt(int sid,String deviceName,int eqid)
    {
        SQLiteDatabase db = this.sys.getWritableDatabase();
        try {
            String where = "deviceName = '"+deviceName+"' and sid = "+sid+" and eqid ="+eqid;
            db.delete("gs584relationshiptable", where, null);
        }catch (NullPointerException e){
            Log.i(TAG,"no choosed gateway");
        }finally {
            db.close();
        }
    }

    //删除某场景下的所有快捷关联
    public void deleteAllShortcurt(int sid,String deviceName)
    {
        SQLiteDatabase db = this.sys.getWritableDatabase();
        try {
            String where = "deviceName = '"+deviceName+"' and sid = "+sid;
            db.delete("gs584relationshiptable", where, null);
        }catch (NullPointerException e){
            Log.i(TAG,"no choosed gateway");
        }finally {
            db.close();
        }
    }


        /**
     * isHasShorcut:判断是否有该sid的场景
     * 作者：Henry on 2017/3/7 13:02
     * 邮箱：xuejunju_4595@qq.com
     * 参数:sid
     * 返回:boolean
     */
    public boolean isHasShorcut(int sid,int eqid,String deviceName) {

        ShortcutAliBean shortcutBean = findShortcutByeqid(sid,eqid,deviceName);
        return shortcutBean != null;
    }


    /**
     * 更新自动化关联到数据库
     * @param shortcutBean
     * @return
     */
    public long insertShortcut(ShortcutAliBean shortcutBean) {

        if(shortcutBean == null ||shortcutBean.getEqid()<=0) {
            return -1L;
        }
        SQLiteDatabase db = this.sys.getWritableDatabase();
        ContentValues values = null;
        try {

            values = new ContentValues();
            values.put("eqid", shortcutBean.getEqid());
            values.put("sid", shortcutBean.getSrc_sid());
            values.put("deviceName",shortcutBean.getDeviceName());
            values.put("action", shortcutBean.getDes_sid());
            if(!isHasShorcut(shortcutBean.getSrc_sid(), shortcutBean.getEqid(),shortcutBean.getDeviceName())) {
                db = this.sys.getWritableDatabase();
                return db.insert("gs584relationshiptable", null, values);
            } else {
                db = this.sys.getWritableDatabase();
                return db.update("gs584relationshiptable",values , "sid =" + shortcutBean.getSrc_sid()+" and deviceName = '"+shortcutBean.getDeviceName()+"' and eqid = "+ shortcutBean.getEqid(),null);
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

}
