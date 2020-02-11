package com.ilop.sthome.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ilop.sthome.data.bean.TimerGatewayAliBean;
import com.litesuits.android.log.Log;

import java.util.ArrayList;
import java.util.List;

/*
@class TimerAliDAO
@autor henry
@time 2019/4/18 2:23 PM
@email xuejunju_4595@qq.com
*/
public class TimerAliDAO {
    private final String  TAG = "TimerAliDAO";
    private SysDBAli sys;
    Context context;
    public TimerAliDAO(Context context){

        try {
            this.context = context;
            this.sys = new SysDBAli(context);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }
//add system modle
    public int add(TimerGatewayAliBean sy){
        int row = -1;
        SQLiteDatabase db = this.sys.getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            cv.put("timerid", sy.getTimerid());
            cv.put("enable", sy.getEnable());
            cv.put("modeid",sy.getModeid());
            cv.put("week",sy.getWeek());
            cv.put("hour",sy.getHour());
            cv.put("min",sy.getMin());
            cv.put("code",sy.getCode());
            cv.put("deviceName",sy.getDeviceName());
            row = (int) db.insert("timertable", null, cv);

        }catch (NullPointerException e){
            Log.i(TAG,"no choosed device");
        }finally {
            db.close();
            return row;
        }


    }

    /**
     * methodname:findAllTimer
     * 作者：Henry on 2017/5/15 15:24
     * 邮箱：xuejunju_4595@qq.com
     * 参数:
     * 返回:获取定时列表
     */
    public List<TimerGatewayAliBean> findAllTimer(String deviceName){
        List<TimerGatewayAliBean> sys2 = new ArrayList<TimerGatewayAliBean>();
        SQLiteDatabase db = this.sys.getWritableDatabase();

        try {
            Cursor cursor = db.rawQuery("select a.*,b.name from timertable a left join sysMTable b on a.modeid = b.sid where a.deviceName = '"+deviceName+"' and b.deviceName = '"+deviceName+"' order by a.timerid",null);
            while (cursor.moveToNext()){
                TimerGatewayAliBean sb = new TimerGatewayAliBean();
                sb.setTimerid(cursor.getInt(cursor.getColumnIndex("timerid")));
                sb.setEnable(cursor.getInt(cursor.getColumnIndex("enable")));
                sb.setModeid(cursor.getInt(cursor.getColumnIndex("modeid")));
                sb.setWeek(cursor.getString(cursor.getColumnIndex("week")));
                sb.setHour(cursor.getString(cursor.getColumnIndex("hour")));
                sb.setMin(cursor.getString(cursor.getColumnIndex("min")));
                sb.setCode(cursor.getString(cursor.getColumnIndex("code")));
                sb.setModename(cursor.getString(cursor.getColumnIndex("name")));
                sys2.add(sb);
            }

        }catch (NullPointerException e){
            Log.i(TAG,"no choosed device");
        }finally {
            db.close();
            return sys2;
        }

    }


    /**
     * methodname:findAllTimerOrderByTime
     * 作者：Henry on 2017/5/15 15:24
     * 邮箱：xuejunju_4595@qq.com
     * 参数:
     * 返回:获取定时列表(按照时间顺序)
     */
    public List<TimerGatewayAliBean> findAllTimerOrderByTime(String deviceName){
        List<TimerGatewayAliBean> sys2 = new ArrayList<TimerGatewayAliBean>();
        SQLiteDatabase db = this.sys.getWritableDatabase();

        try {
            Cursor cursor = db.rawQuery("select a.*,b.name from timertable a left join sysMTable b on a.modeid = b.sid where a.deviceName = '"+deviceName+"' and b.deviceName = '"+deviceName+"' order by a.hour,a.min",null);
            while (cursor.moveToNext()){
                TimerGatewayAliBean sb = new TimerGatewayAliBean();
                sb.setTimerid(cursor.getInt(cursor.getColumnIndex("timerid")));
                sb.setEnable(cursor.getInt(cursor.getColumnIndex("enable")));
                sb.setModeid(cursor.getInt(cursor.getColumnIndex("modeid")));
                sb.setWeek(cursor.getString(cursor.getColumnIndex("week")));
                sb.setHour(cursor.getString(cursor.getColumnIndex("hour")));
                sb.setMin(cursor.getString(cursor.getColumnIndex("min")));
                sb.setCode(cursor.getString(cursor.getColumnIndex("code")));
                sb.setModename(cursor.getString(cursor.getColumnIndex("name")));
                sb.setDeviceName(cursor.getString(cursor.getColumnIndex("deviceName")));
                sys2.add(sb);
            }

        }catch (NullPointerException e){
            Log.i(TAG,"no choosed device");
        }finally {
            db.close();
            return sys2;
        }

    }

    /**
     * methodname:findAllTimer
     * 作者：Henry on 2017/5/15 15:24
     * 邮箱：xuejunju_4595@qq.com
     * 参数:
     * 返回:获取定时列表
     */
    public List<TimerGatewayAliBean> findAllTimerByTid(int tid, String deviceName){
        List<TimerGatewayAliBean> sys2 = new ArrayList<TimerGatewayAliBean>();
        SQLiteDatabase db = this.sys.getWritableDatabase();

        try {
            Cursor cursor = db.rawQuery("select * from timertable where deviceName = '"+deviceName+"' and timerid = "+tid+" order by timerid",null);
            while (cursor.moveToNext()){
                TimerGatewayAliBean sb = new TimerGatewayAliBean();
                sb.setTimerid(cursor.getInt(cursor.getColumnIndex("timerid")));
                sb.setEnable(cursor.getInt(cursor.getColumnIndex("enable")));
                sb.setModeid(cursor.getInt(cursor.getColumnIndex("modeid")));
                sb.setWeek(cursor.getString(cursor.getColumnIndex("week")));
                sb.setHour(cursor.getString(cursor.getColumnIndex("hour")));
                sb.setMin(cursor.getString(cursor.getColumnIndex("min")));
                sb.setCode(cursor.getString(cursor.getColumnIndex("code")));
                sys2.add(sb);
            }

        }catch (NullPointerException e){
            Log.i(TAG,"no choosed device");
        }finally {
            db.close();
            return sys2;
        }

    }


    /**
     * methodname:findAllTimerByTime
     * 作者：Henry on 2017/6/3 14:32
     * 邮箱：xuejunju_4595@qq.com
     * 参数:hour,min
     * 返回:获取特定时间的定时星期数据
     */
    public List<String> findAllTimerByTime(String hour,String min,String deviceName){
        List<String> sys2 = new ArrayList<String>();
        SQLiteDatabase db = this.sys.getWritableDatabase();

        try {
            Cursor cursor = db.rawQuery("select * from timertable where deviceName = '"+deviceName+"' and hour = '"+hour+"' and min = '"+min+"' order by timerid",null);
            while (cursor.moveToNext()){
                sys2.add(cursor.getString(cursor.getColumnIndex("week")));
            }

        }catch (NullPointerException e){
            Log.i(TAG,"no choosed device");
        }finally {
            db.close();
            return sys2;
        }

    }

    /**
     * methodname: findByTid
     * 作者：Henry on 2017/5/16 9:08
     * 邮箱：xuejunju_4595@qq.com
     * 参数:
     * 返回:
     */
    public TimerGatewayAliBean findByTid(int tid, String deviceName){
        SQLiteDatabase db = this.sys.getWritableDatabase();
        TimerGatewayAliBean sb = new TimerGatewayAliBean();
        try {
            Cursor cursor = db.rawQuery("select * from timertable where timerid =? and deviceName = ?",new String[]{String.valueOf(tid),deviceName});
            if(cursor.moveToFirst()) {
                sb.setTimerid(cursor.getInt(cursor.getColumnIndex("timerid")));
                sb.setEnable(cursor.getInt(cursor.getColumnIndex("enable")));
                sb.setModeid(cursor.getInt(cursor.getColumnIndex("modeid")));
                sb.setWeek(cursor.getString(cursor.getColumnIndex("week")));
                sb.setHour(cursor.getString(cursor.getColumnIndex("hour")));
                sb.setMin(cursor.getString(cursor.getColumnIndex("min")));
                sb.setCode(cursor.getString(cursor.getColumnIndex("code")));
                sb.setDeviceName(cursor.getString(cursor.getColumnIndex("deviceName")));
            }
        }catch (NullPointerException e){
            Log.i(TAG,"no choosed gateway");
        }finally {
            db.close();
            return sb;
        }
    }

    /**
     * isHasTimer:判断是否有该tid的定时
     * 作者：Henry on 2017/3/7 13:02
     * 邮箱：xuejunju_4595@qq.com
     * 参数:sid
     * 返回:boolean
     */
    public boolean isHasTimer(int tid,String deviceName) {

        List<TimerGatewayAliBean> list = findAllTimerByTid(tid,deviceName);
        return list != null && list.size() != 0;
    }

    /**
     * 更新Timer到数据库
     * @param modelBean
     * @return
     */
    public long insertTimer(TimerGatewayAliBean modelBean) {

        if(modelBean == null || modelBean.getTimerid()<0) {
            return -1L;
        }
        SQLiteDatabase db = this.sys.getWritableDatabase();
        ContentValues values = null;
        try {

            values = new ContentValues();
            values.put("timerid", modelBean.getTimerid());
            values.put("enable", modelBean.getEnable());
            values.put("modeid",modelBean.getModeid());
            values.put("week",modelBean.getWeek());
            values.put("hour",modelBean.getHour());
            values.put("min",modelBean.getMin());
            values.put("code",modelBean.getCode());
            values.put("deviceName",modelBean.getDeviceName());
            if(!isHasTimer(modelBean.getTimerid(),modelBean.getDeviceName())) {
                db = this.sys.getWritableDatabase();
                return db.insert("timertable", null, values);
            } else {
                db = this.sys.getWritableDatabase();
                return db.update("timertable",values , "timerid =" + modelBean.getTimerid()+" and deviceName = '"+modelBean.getDeviceName()+"'",null);
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
     * @param tid
     */
    public void delete(int tid,String deviceName)
    {

        SQLiteDatabase db =this.sys.getWritableDatabase();
        try {
            String where = "timerid = ? and deviceName = ?";
            String[] whereValue ={ String.valueOf(tid),deviceName };
            db.delete("timertable", where, whereValue);
        }catch (NullPointerException e){
            Log.i(TAG,"no choosed device");
        }finally {
            db.close();
        }


    }


    //修改定时器使能
    public void updateEnable(TimerGatewayAliBean beana)
    {
        SQLiteDatabase db = this.sys.getWritableDatabase();
        try {
            String where = "timerid = ? and deviceName = ?";
            String[] whereValue = {String.valueOf(beana.getTimerid()),beana.getDeviceName()};
            ContentValues cv = new ContentValues();
            cv.put("enable",beana.getEnable());
            db.update("timertable", cv, where, whereValue);
        }catch (NullPointerException e){
            Log.i(TAG,"no choosed gateway");
        }finally {
            db.close();
        }
    }


    /**
     * @return sid List
     */
    public List<Integer> findAllTimerTid(String deviceName){
        SQLiteDatabase db = this.sys.getWritableDatabase();
        List<Integer> list = new ArrayList<Integer>();
        try {
            Cursor cursor = db.rawQuery("select timerid from timertable where deviceName = '"+deviceName+"' order by timerid,id",null);
            while (cursor.moveToNext()){
                list.add( cursor.getInt(cursor.getColumnIndex("timerid")));
            }
        }catch (NullPointerException e){
            Log.i(TAG,"no choosed device");
        }finally {
            db.close();
            return list;
        }



    }

}
