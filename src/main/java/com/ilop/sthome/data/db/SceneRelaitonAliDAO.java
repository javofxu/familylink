package com.ilop.sthome.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ilop.sthome.data.bean.SceneRelationBean;
import com.litesuits.android.log.Log;

import java.util.ArrayList;
import java.util.List;

/*
@class SceneRelaitonAliDAO
@autor henry
@time 2019/4/18 3:42 PM
@email xuejunju_4595@qq.com
*/
public class SceneRelaitonAliDAO {
    private final String TAG = "SceneRelaitonAliDAO";
    private SysDBAli sys;
    Context context;
    public SceneRelaitonAliDAO(Context context){

        try {
            this.context = context;
            this.sys = new SysDBAli(context);
        }catch (NullPointerException e){
             e.printStackTrace();
        }
    }


    public List<SceneRelationBean> findRelationsBysid(String deviceName, int sid){
        SQLiteDatabase db = this.sys.getWritableDatabase();
        List<SceneRelationBean> list = new ArrayList<SceneRelationBean>();
        try {
            Cursor cursor = db.rawQuery("select * from scenerelationshiptable where deviceName = '"+deviceName+"' and sid ="+sid+" order by mid",null);
            while (cursor.moveToNext()){
                SceneRelationBean eq = new SceneRelationBean();
                eq.setMid(cursor.getInt(cursor.getColumnIndex("mid")));
                eq.setSid(cursor.getInt(cursor.getColumnIndex("sid")));
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

    public List<Integer> findRelationsBysid2(String deviceName, int sid){
        SQLiteDatabase db = this.sys.getWritableDatabase();
        List<Integer> list = new ArrayList<Integer>();
        try {
            Cursor cursor = db.rawQuery("select * from scenerelationshiptable where deviceName = '"+deviceName+"' and sid ="+sid+" order by mid",null);
            while (cursor.moveToNext()){
                list.add(cursor.getInt(cursor.getColumnIndex("mid")));
            }
        }catch (NullPointerException e){
            Log.i(TAG,"no choosed gateway");
        }finally {
            db.close();
            return list;
        }
    }


    public SceneRelationBean findRelationBymidAndSid(int mid,int sid, String deviceName){
        SQLiteDatabase db = this.sys.getWritableDatabase();
        SceneRelationBean eq = null;
        try {
            Cursor cursor = db.rawQuery("select * from scenerelationshiptable where mid =? and sid = ? and deviceName = ?",new String[]{String.valueOf(mid),String.valueOf(sid),deviceName});
            if(cursor.moveToFirst()) {
                eq = new SceneRelationBean();
                eq.setSid(cursor.getInt(cursor.getColumnIndex("sid")));
                eq.setMid(cursor.getInt(cursor.getColumnIndex("mid")));
                eq.setDeviceName(cursor.getString(cursor.getColumnIndex("deviceName")));
            }
        }catch (NullPointerException e){
            Log.i(TAG, "no choosed device");
        }finally {
            db.close();
            return eq;
        }
    }

    //删除某场景下的所有快捷关联
    public void deleteAllShortcurt(int sid,String deviceName)
    {
        SQLiteDatabase db = this.sys.getWritableDatabase();
        try {
            String where = "deviceName = '"+deviceName+"' and sid = "+sid;
            db.delete("scenerelationshiptable", where, null);
        }catch (NullPointerException e){
            Log.i(TAG,"no choosed gateway");
        }finally {
            db.close();
        }
    }


    //删除某自动化下的所有快捷关联
    public void deleteAllShortcurt2(int mid,String deviceName)
    {
        SQLiteDatabase db = this.sys.getWritableDatabase();
        try {
            String where = "deviceName = '"+deviceName+"' and mid = "+mid;
            db.delete("scenerelationshiptable", where, null);
        }catch (NullPointerException e){
            Log.i(TAG,"no choosed gateway");
        }finally {
            db.close();
        }
    }

    //删除某自动化关联
    public void deleteRelation(int mid,int sid,String deviceName)
    {
        SQLiteDatabase db = this.sys.getWritableDatabase();
        try {
            String where = "deviceName = '"+deviceName+"' and mid = "+mid+" and sid ="+sid;
            db.delete("scenerelationshiptable", where, null);
        }catch (NullPointerException e){
            Log.i(TAG,"no choosed gateway");
        }finally {
            db.close();
        }
    }

    /**
     * 更新自动化关联到数据库
     * @param shortcutBean
     * @return
     */
    public long insertSceneRelation(SceneRelationBean shortcutBean) {

        if(shortcutBean == null ||shortcutBean.getSid()<0) {
            return -1L;
        }
        SQLiteDatabase db = this.sys.getWritableDatabase();
        ContentValues values = null;
        try {

            values = new ContentValues();
            values.put("mid", shortcutBean.getMid());
            values.put("sid", shortcutBean.getSid());
            values.put("deviceName",shortcutBean.getDeviceName());
            if(!isHasRelation(shortcutBean.getMid(),shortcutBean.getSid(),shortcutBean.getDeviceName())){
                db = this.sys.getWritableDatabase();
                return db.insert("scenerelationshiptable", null, values);
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


    public boolean isHasRelation(int mid,int sid,String deviceName) {

        SceneRelationBean shortcutBean = findRelationBymidAndSid(mid,sid,deviceName);
        return shortcutBean != null;
    }
}
