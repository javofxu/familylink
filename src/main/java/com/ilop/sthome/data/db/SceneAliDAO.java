package com.ilop.sthome.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ilop.sthome.data.bean.SceneAliBean;
import com.litesuits.android.log.Log;

import java.util.ArrayList;
import java.util.List;


/*
@class SceneAliDAO
@autor henry
@time 2019/4/18 2:22 PM
@email xuejunju_4595@qq.com
*/
public class SceneAliDAO {
    private final String TAG = "SceneAliDAO";
    private SysDBAli sys;
    Context context;
    public SceneAliDAO(Context context){

        try {
            this.context = context;
            this.sys = new SysDBAli(context);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    /**
     * 修改Bymid
     * @param am
     */
    public void updateByMid(SceneAliBean am){
        SQLiteDatabase db = this.sys.getWritableDatabase();
        try {
        String where = "mid = ? and deviceName = ?";
        String[] whereValue = {String.valueOf(am.getMid()),am.getDeviceName()};
        ContentValues cv = new ContentValues();
        cv.put("name",am.getName());
        cv.put("code",am.getCode());
        db.update("scTable", cv, where, whereValue);
        }catch (NullPointerException e){
            Log.i(TAG,"no choosed gateway");
        }finally {
            db.close();
        }
    }

    /**
     * 修改code Bymid
     * @param code
     * @param mid
     * @param deviceName
     */
    public void updateCodeByMid(String code ,int mid,String deviceName){
        SQLiteDatabase db = this.sys.getWritableDatabase();
        try {
            String where = "mid = ? and deviceName = ?";
            String[] whereValue = {String.valueOf(mid),deviceName};
            ContentValues cv = new ContentValues();
            cv.put("code",code);
            db.update("scTable", cv, where, whereValue);
        }catch (NullPointerException e){
            Log.i(TAG,"no choosed gateway");
        }finally {
            db.close();
        }
    }

    /**
     * @param scenebean
     * @return 生成的数量
     */
    public int addScence(SceneAliBean scenebean)
    {
        int row = 0;
        SQLiteDatabase db = this.sys.getWritableDatabase();
        try {
    /* ContentValues */
        ContentValues cv = new ContentValues();
        cv.put("name", scenebean.getName());
        cv.put("code",scenebean.getCode());
        cv.put("mid",scenebean.getMid());
        cv.put("deviceName",scenebean.getDeviceName());
        row = (int) db.insert("scTable", null, cv);

        }catch (NullPointerException e){
            Log.i(TAG,"no choosed gateway");
        }finally {
            db.close();
            return row;
        }
    }

    /**
     * 更新或插入自动化到数据库
     * @param modelBean
     * @return
     */
    public long insertScene(SceneAliBean modelBean) {

        if(modelBean == null || modelBean.getMid()<=0) {
            return -1L;
        }
        SQLiteDatabase db = this.sys.getWritableDatabase();
        ContentValues values = null;
        try {

            values = new ContentValues();
            values.put("name", modelBean.getName());
            values.put("code", modelBean.getCode());
            values.put("deviceName",modelBean.getDeviceName());
            values.put("mid",modelBean.getMid());
            if(findInitSceneCount(modelBean.getMid(),modelBean.getDeviceName())<=0) {
                db = this.sys.getWritableDatabase();
                return db.insert("scTable", null, values);
            } else {
                db = this.sys.getWritableDatabase();
                return db.update("scTable",values , "mid =" + modelBean.getMid()+" and deviceName = '"+modelBean.getDeviceName()+"'",null);
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


   /*
   @method addinit
   @autor TracyHenry
   @time 2018/8/6 上午10:20
   @email xuejunju_4595@qq.com
   用来初始化默认自动化
   */
    public int addinit (SceneAliBean sy){
        int row = -1;
        int count =  findInitSceneCount(sy.getMid(),sy.getDeviceName());

        if(count > 0){
            return row;
        }

        SQLiteDatabase db = this.sys.getWritableDatabase();
    /* ContentValues */
        ContentValues cv = new ContentValues();
        cv.put("name", sy.getName());
        cv.put("code",sy.getCode());
        cv.put("mid",sy.getMid());
        cv.put("deviceName",sy.getDeviceName());
        row = (int) db.insert("scTable", null, cv);

        db.close();
        return row;


    }

    /**
     * @param deviceName
     * 根据系统组sid删除响应的自动化
     */
    public void deleteBySid(String deviceName)
    {
        SQLiteDatabase db = this.sys.getWritableDatabase();
        try {
        String where = "deviceName = ?";
        String[] whereValue ={ deviceName };
        db.delete("scTable", where, whereValue);
        }catch (NullPointerException e){
            Log.i(TAG,"no choosed gateway");
        }finally {
            db.close();
        }
    }

    /**
     * @param eq
     */
    public void delete(SceneAliBean eq)
    {
        SQLiteDatabase db = this.sys.getWritableDatabase();
        try {
        String where = "mid = ? and deviceName = ?";
        String[] whereValue ={ String.valueOf(eq.getMid()),eq.getDeviceName()};
        int row = db.delete("scTable", where, whereValue);
        Log.i("delete sence list",eq.getMid()+" ============delete"+ row );
        }catch (NullPointerException e){
            Log.i(TAG,"no choosed gateway");
        }finally {
            db.close();
        }
    }


    /**
     * @param eq
     */
    public void deleteBySidAndMid(SceneAliBean eq, String deviceName)
    {
        SQLiteDatabase db = this.sys.getWritableDatabase();
        try {
        String where = "mid = ? and deviceName = ?";
        String[] whereValue ={ String.valueOf(eq.getMid()),deviceName };
        int row = db.delete("scTable", where, whereValue);
        Log.i("delete sence list",eq.getMid()+" ============delete"+ row );
        }catch (NullPointerException e){
            Log.i(TAG,"no choosed gateway");
        }finally {
            db.close();
        }
    }

    /**
     * @param mid
     */
    public void deleteByMid(int mid,String deviceName)
    {
        SQLiteDatabase db = this.sys.getWritableDatabase();
        try {
            String where = "mid = ? and deviceName = ?";
            String[] whereValue ={ String.valueOf(mid),deviceName};
            db.delete("scTable", where, whereValue);
        }catch (NullPointerException e){
            Log.i(TAG,"no choosed gateway");
        }finally {
            db.close();
        }


    }

    /**
     * @param eq
     */
    public void deleteFromsceneGroup(SceneAliBean eq)
    {
        SQLiteDatabase db = this.sys.getWritableDatabase();
        try {
            String where = "mid = ? and deviceName = ?";
            String[] whereValue ={ String.valueOf(eq.getMid()),eq.getDeviceName()};
            db.delete("scTable", where, whereValue);
        }catch (NullPointerException e){
            Log.i(TAG,"no choosed gateway");
        }finally {
            db.close();
        }

    }

    public void deleteAll(String deviceName)
    {
        SQLiteDatabase db = this.sys.getWritableDatabase();
        try {
            String where = "deviceName = '"+deviceName+"'";
            db.delete("scTable", where, null);
        }catch (NullPointerException e){
            Log.i(TAG,"no choosed gateway");
        }finally {
            db.close();
        }

    }

    public List<SceneAliBean> findScenceListBySid(String deviceName){
        SQLiteDatabase db = this.sys.getWritableDatabase();
        List<SceneAliBean> list = new ArrayList<SceneAliBean>();
        try {
        String [] whereValue = {deviceName};
        Cursor cursor = db.rawQuery("select * from scTable where deviceName = ?",whereValue);
        while (cursor.moveToNext()){
            SceneAliBean eq = new SceneAliBean();
            eq.setName(cursor.getString(cursor.getColumnIndex("name")));
            eq.setCode(cursor.getString(cursor.getColumnIndex("code")));
            eq.setMid(cursor.getInt(cursor.getColumnIndex("mid")));
            eq.setDeviceName(cursor.getString(cursor.getColumnIndex("deviceName")));
            list.add(eq);
        }
        }catch (NullPointerException e){
            Log.i(TAG, "no choosed device");
        }finally {
            db.close();
            return list;
        }

    }

    /**
     * 查找在该自动化下是否有该自动化，要是有则查看长度
     * @param sb
     * @return
     */
    public List<SceneAliBean> findScenceListBySidAndMid(SceneAliBean sb){
        SQLiteDatabase db = this.sys.getWritableDatabase();
        List<SceneAliBean> list = new ArrayList<SceneAliBean>();
        try {
        String [] whereValue = {String.valueOf(sb.getMid()),sb.getDeviceName()};
        Cursor cursor = db.rawQuery("select * from scTable where mid =? and deviceName = ?",whereValue);
        while (cursor.moveToNext()){
            SceneAliBean eq = new SceneAliBean();
            eq.setName(cursor.getString(cursor.getColumnIndex("name")));
            eq.setCode(cursor.getString(cursor.getColumnIndex("code")));
            eq.setMid(cursor.getInt(cursor.getColumnIndex("mid")));
            eq.setDeviceName(cursor.getString(cursor.getColumnIndex("deviceName")));
            list.add(eq);
        }
        }catch (NullPointerException e){
            Log.i(TAG, "no choosed device");
        }finally {
            db.close();
            return list;
        }

    }


    /**
     * @param mid
     * @return ScenceBean
     */
    public SceneAliBean findScenceBymid(int mid, String deviceName){
        SQLiteDatabase db = this.sys.getWritableDatabase();
        SceneAliBean eq = null;
        try {
        Cursor cursor = db.rawQuery("select * from scTable where mid =? and deviceName = ?",new String[]{String.valueOf(mid),deviceName});
        if(cursor.moveToFirst()) {
            eq = new SceneAliBean();
            eq.setName(cursor.getString(cursor.getColumnIndex("name")));
            eq.setCode(cursor.getString(cursor.getColumnIndex("code")));
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

    /**
     * @param mid
     * @return ScenceBean
     */
    public SceneAliBean findScenceBymidByOtherGateway(int mid, String deviceName){
        SQLiteDatabase db = this.sys.getWritableDatabase();
        SceneAliBean eq = null;
        try {
            Cursor cursor = db.rawQuery("select * from scTable where mid =? and deviceName = ?",new String[]{String.valueOf(mid),deviceName});
            if(cursor.moveToFirst()) {
                eq = new SceneAliBean();
                eq.setName(cursor.getString(cursor.getColumnIndex("name")));
                eq.setCode(cursor.getString(cursor.getColumnIndex("code")));
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

    /**
     * 按照名称进行查找ID
     * @return
     */
    public SceneAliBean findByName(String name, String deviceName){
        SceneAliBean eq = null;
        SQLiteDatabase db = this.sys.getWritableDatabase();
        try {
        Cursor cursor = db.rawQuery("select * from scTable where name = ? and deviceName = ?",new String[]{name,deviceName});
        if(cursor.moveToFirst()) {
            eq = new SceneAliBean();
            eq.setName(cursor.getString(cursor.getColumnIndex("name")));
            eq.setCode(cursor.getString(cursor.getColumnIndex("code")));
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

    /**
     * @return 所有的自动化数量
     */
    public int findScenceCount(String deviceName){
        SQLiteDatabase db = this.sys.getWritableDatabase();
        int a = 0;
        try {
        Cursor cursor = db.rawQuery("select count(*) from scTable where sid =-1 and deviceName= '"+deviceName+"'",null);
        if(cursor.moveToFirst()) {
            a = cursor.getInt(cursor.getColumnIndex("count(*)"));
        }
        }catch (NullPointerException e){
            Log.i(TAG, "no choosed device");
        }finally {
            db.close();
            return a;
        }
    }



    /**
     * @return mid List
     */
    public List<String> findAllScenceMid(String deviceName){
        List<String> list = new ArrayList<String>();
        SQLiteDatabase db = this.sys.getWritableDatabase();
        try {
        Cursor cursor = db.rawQuery("select mid from scTable where deviceName = '"+deviceName+"' and mid is not null group by mid order by mid",null);
        while (cursor.moveToNext()){
            list.add( cursor.getString(cursor.getColumnIndex("mid")));
        }
        }catch (NullPointerException e){
            Log.i(TAG, "no choosed device");
        }finally {
            db.close();
            return list;
        }
    }

    /**
     * update Mid
     * @param mid
     */
    public void updateScenceMid(String mid,String deviceName){
        SQLiteDatabase db = this.sys.getWritableDatabase();
        String where = "deviceName = ?";
        String[] whereValue = {deviceName};
        ContentValues cv = new ContentValues();
        cv.put("mid",mid);
        db.update("scTable", cv, where, whereValue);
        db.close();
    }


    /**
     * find all scene from scTable;
     * @return
     */
    public List<SceneAliBean> findAllAm(String deviceName){
        SQLiteDatabase db = this.sys.getWritableDatabase();
        List<SceneAliBean> list = new ArrayList<SceneAliBean>();
        try {
        Cursor cursor = db.rawQuery("select * from scTable where deviceName = '"+deviceName+"' and code is not null",null);
        while (cursor.moveToNext()){
            SceneAliBean am = new SceneAliBean();
            am.setName(cursor.getString(cursor.getColumnIndex("name")));
            am.setCode(cursor.getString(cursor.getColumnIndex("code")));
            am.setMid(cursor.getInt(cursor.getColumnIndex("mid")));
            am.setDeviceName(cursor.getString(cursor.getColumnIndex("deviceName")));
            list.add(am);
        }

        }catch (NullPointerException e){
            Log.i(TAG, "no choosed device");
        }finally {
            db.close();
            return list;
        }
    }


    /**
     * find all scene from scTable;
     * @return
     */
    public List<SceneAliBean> findAllAmWithoutDefault(String deviceName){
        SQLiteDatabase db = this.sys.getWritableDatabase();
        List<SceneAliBean> list = new ArrayList<SceneAliBean>();
        try {
            Cursor cursor = db.rawQuery("select * from scTable where deviceName = '"+deviceName+"' and code is not null order by mid",null);
            while (cursor.moveToNext()){
                String mid = cursor.getString(cursor.getColumnIndex("mid"));
                if(Integer.parseInt(mid)<=128) {
                    SceneAliBean am = new SceneAliBean();
                    am.setName(cursor.getString(cursor.getColumnIndex("name")));
                    am.setCode(cursor.getString(cursor.getColumnIndex("code")));
                    am.setMid(cursor.getInt(cursor.getColumnIndex("mid")));
                    am.setDeviceName(cursor.getString(cursor.getColumnIndex("deviceName")));
                    list.add(am);
                }
            }

        }catch (NullPointerException e){
            Log.i(TAG, "no choosed device");
        }finally {
            db.close();
            return list;
        }
    }

    /**
     * find all scene from scTable;
     * @return
     */
    public List<SceneAliBean> findAllDefault(String deviceName){
        SQLiteDatabase db = this.sys.getWritableDatabase();
        List<SceneAliBean> list = new ArrayList<SceneAliBean>();
        try {
            Cursor cursor = db.rawQuery("select * from scTable where deviceName = '"+deviceName+"' and code is not null order by mid",null);
            while (cursor.moveToNext()){
                String mid = cursor.getString(cursor.getColumnIndex("mid"));
                if(Integer.parseInt(mid)>128) {
                    SceneAliBean am = new SceneAliBean();
                    am.setName(cursor.getString(cursor.getColumnIndex("name")));
                    am.setCode(cursor.getString(cursor.getColumnIndex("code")));
                    am.setMid(cursor.getInt(cursor.getColumnIndex("mid")));
                    am.setDeviceName(cursor.getString(cursor.getColumnIndex("deviceName")));
                    list.add(am);
                }
            }

        }catch (NullPointerException e){
            Log.i(TAG, "no choosed device");
        }finally {
            db.close();
            return list;
        }
    }



    /**
     * find all scene from scTable;
     * @return
     */
    public List<SceneAliBean> findAllAmBySid(int sid, String deviceName){
        SQLiteDatabase db = this.sys.getWritableDatabase();
        List<SceneAliBean> list = new ArrayList<SceneAliBean>();
        try {
            Cursor cursor = db.rawQuery("select b.* from scenerelationshiptable a inner join  scTable b on a.mid = b.mid where a.sid = "+sid+" and a.deviceName = '"+deviceName+"' and b.deviceName='"+deviceName+"'",null);
            while (cursor.moveToNext()){
                int mid = cursor.getInt(cursor.getColumnIndex("mid"));
                if(mid<=128){
                SceneAliBean am = new SceneAliBean();
                am.setName(cursor.getString(cursor.getColumnIndex("name")));
                am.setCode(cursor.getString(cursor.getColumnIndex("code")));
                am.setMid(cursor.getInt(cursor.getColumnIndex("mid")));
                am.setDeviceName(cursor.getString(cursor.getColumnIndex("deviceName")));
                list.add(am);
            }
            }

        }catch (NullPointerException e){
            Log.i(TAG, "no choosed device");
        }finally {
            db.close();
            return list;
        }
    }



    /**
     * find all scene from scTable;
     * @return
     */
    public List<Integer> findAllMid(String deviceName){
        SQLiteDatabase db = this.sys.getWritableDatabase();
        List<Integer> list = new ArrayList<Integer>();
        try {
            Cursor cursor = db.rawQuery("select mid from scTable where deviceName = '"+deviceName+"' and mid is not null group by mid order by mid",null);
            while (cursor.moveToNext()){
                list.add( cursor.getInt(cursor.getColumnIndex("mid")));
            }
        }catch (NullPointerException e){
            Log.i(TAG,"no choosed device");
        }finally {
            db.close();
            return list;
        }
    }


    //find distinct id feom actable and the actmodle id is you want
    public List<Integer> findAllSenceBySysId(String deviceName){
        List<Integer> in = new ArrayList<Integer>();
        SQLiteDatabase db = this.sys.getWritableDatabase();
        try {
            Cursor cursor = db.rawQuery("select distinct(mid) from scTable a1 where a1.deviceName = ?",new String[]{deviceName});
            while (cursor.moveToNext()){
                in.add(new Integer(cursor.getString(cursor.getColumnIndex("mid"))));
            }
        }catch (NullPointerException e){
            Log.i(TAG, "no choosed device");
        }finally {
            db.close();
            return in;
        }



    }

    /**
     * @return 查找含有相应字符串的自动化数量
     */
    public int findScenceByNameCount(String name,String deviceName) {
        int a = 0;
        SQLiteDatabase db = this.sys.getWritableDatabase();
        try {
            Cursor cursor = db.rawQuery("select count(*) from scTable where deviceName = '"+deviceName+"' and name ='" + name + "' and code is not null", null);
            if (cursor.moveToFirst()) {
                a = cursor.getInt(cursor.getColumnIndex("count(*)"));
            }
        } catch (NullPointerException e) {
            Log.i(TAG, "no choosed device");
        } finally {
            db.close();
            return a;

        }
    }


    /**
     * @return 查找含有相应字符串的自动化数量,出去相应的mid
     */
    public int findScenceByNameCountWithMid(String name,String deviceName,int mid) {
        int a = 0;
        SQLiteDatabase db = this.sys.getWritableDatabase();
        try {
            Cursor cursor = db.rawQuery("select count(*) from scTable where deviceName = '"+deviceName+"' and name ='" + name + "' and code is not null and mid !="+mid, null);
            if (cursor.moveToFirst()) {
                a = cursor.getInt(cursor.getColumnIndex("count(*)"));
            }
        } catch (NullPointerException e) {
            Log.i(TAG, "no choosed device");
        } finally {
            db.close();
            return a;

        }
    }

    /**
     * @return 所有的场景数量
     */
    public int findInitSceneCount(int mid,String deviceName){
        SQLiteDatabase db = this.sys.getWritableDatabase();
        int a = 0;
        try {
            db = this.sys.getWritableDatabase();
            Cursor cursor = db.rawQuery("select count(*) from scTable where deviceName = '"+deviceName+"' and mid = "+mid,null);
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
