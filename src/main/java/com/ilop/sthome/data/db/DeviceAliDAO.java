package com.ilop.sthome.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.ilop.sthome.data.bean.DeviceInfoBean;
import com.ilop.sthome.data.enums.DevType;

import java.util.ArrayList;
import java.util.List;


/*
@class DeviceAliDAO
@autor henry
@time 2019/4/18 2:22 PM
@email xuejunju_4595@qq.com
*/
public class DeviceAliDAO {
    private final String  TAG = "DeviceAliDAO";
    private SysDBAli sys;
    Context context;
    public DeviceAliDAO(Context context){
        try {
            this.context = context;
            this.sys = new SysDBAli(context);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }



    /**
     * 查找所有的设备
     * @return
     */
    public List<DeviceInfoBean> findAllDevice(){
        List<DeviceInfoBean> list = new ArrayList<DeviceInfoBean>();
        DeviceInfoBean eq = null;
        SQLiteDatabase db = this.sys.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from devicetable where 1 = 1 order by deviceName,eqid",null);
        while (cursor.moveToNext()){
            eq = new DeviceInfoBean();
            String deviceName = cursor.getString(cursor.getColumnIndex("deviceName"));
            String equipmenttype = cursor.getString(cursor.getColumnIndex("equipmenttype"));
            if(!TextUtils.isEmpty(equipmenttype)){
                 if(findGatewayCount(deviceName)>0){
                     eq.setDeviceName(cursor.getString(cursor.getColumnIndex("deviceName")));
                     eq.setIotId(cursor.getString(cursor.getColumnIndex("iotId")));
                     eq.setSubdeviceName(cursor.getString(cursor.getColumnIndex("subDeviceName")));
                     eq.setNickName(cursor.getString(cursor.getColumnIndex("nickName")));
                     eq.setProductKey(cursor.getString(cursor.getColumnIndex("productKey")));
                     eq.setOwned(cursor.getInt(cursor.getColumnIndex("owned")));
                     eq.setIsEdgeGateway(cursor.getInt(cursor.getColumnIndex("isEdgeGateway")));
                     eq.setStatus(cursor.getInt(cursor.getColumnIndex("status")));
                     eq.setDevice_ID(cursor.getInt(cursor.getColumnIndex("eqid")));
                     eq.setDevice_status(cursor.getString(cursor.getColumnIndex("sub_status")));
                     eq.setDevice_type(cursor.getString(cursor.getColumnIndex("equipmenttype")));
                     eq.setIs_open(cursor.getInt(cursor.getColumnIndex("open")));
                     list.add(eq);
                 }
            }else{
                eq.setDeviceName(cursor.getString(cursor.getColumnIndex("deviceName")));
                eq.setIotId(cursor.getString(cursor.getColumnIndex("iotId")));
                eq.setSubdeviceName(cursor.getString(cursor.getColumnIndex("subDeviceName")));
                eq.setNickName(cursor.getString(cursor.getColumnIndex("nickName")));
                eq.setProductKey(cursor.getString(cursor.getColumnIndex("productKey")));
                eq.setOwned(cursor.getInt(cursor.getColumnIndex("owned")));
                eq.setIsEdgeGateway(cursor.getInt(cursor.getColumnIndex("isEdgeGateway")));
                eq.setStatus(cursor.getInt(cursor.getColumnIndex("status")));
                eq.setDevice_ID(cursor.getInt(cursor.getColumnIndex("eqid")));
                eq.setDevice_status(cursor.getString(cursor.getColumnIndex("sub_status")));
                eq.setDevice_type(cursor.getString(cursor.getColumnIndex("equipmenttype")));
                eq.setIs_open(cursor.getInt(cursor.getColumnIndex("open")));
                list.add(eq);
            }

        }
        db.close();
        return list;
    }

    /**
     * 查找所有的设备
     * @return
     */
    public List<DeviceInfoBean> findAllDeviceWithOpen(){
        List<DeviceInfoBean> list = new ArrayList<DeviceInfoBean>();
        DeviceInfoBean eq = null;
        SQLiteDatabase db = this.sys.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from devicetable where 1 = 1 order by deviceName,eqid",null);
        while (cursor.moveToNext()){
            eq = new DeviceInfoBean();
            String deviceName = cursor.getString(cursor.getColumnIndex("deviceName"));
            String equipmenttype = cursor.getString(cursor.getColumnIndex("equipmenttype"));
            int eqid = cursor.getInt(cursor.getColumnIndex("eqid"));
            boolean open = IsOpenThisDevice(deviceName);
            if(eqid!=0){
                if(findGatewayCount(deviceName)>0&&open){
                    eq.setDeviceName(cursor.getString(cursor.getColumnIndex("deviceName")));
                    eq.setIotId(cursor.getString(cursor.getColumnIndex("iotId")));
                    eq.setSubdeviceName(cursor.getString(cursor.getColumnIndex("subDeviceName")));
                    eq.setNickName(cursor.getString(cursor.getColumnIndex("nickName")));
                    eq.setProductKey(cursor.getString(cursor.getColumnIndex("productKey")));
                    eq.setOwned(cursor.getInt(cursor.getColumnIndex("owned")));
                    eq.setIsEdgeGateway(cursor.getInt(cursor.getColumnIndex("isEdgeGateway")));
                    eq.setStatus(cursor.getInt(cursor.getColumnIndex("status")));
                    eq.setDevice_ID(cursor.getInt(cursor.getColumnIndex("eqid")));
                    eq.setDevice_status(cursor.getString(cursor.getColumnIndex("sub_status")));
                    eq.setDevice_type(cursor.getString(cursor.getColumnIndex("equipmenttype")));
                    eq.setIs_open(cursor.getInt(cursor.getColumnIndex("open")));
                    list.add(eq);
                }
            }else{
                eq.setDeviceName(cursor.getString(cursor.getColumnIndex("deviceName")));
                eq.setIotId(cursor.getString(cursor.getColumnIndex("iotId")));
                eq.setSubdeviceName(cursor.getString(cursor.getColumnIndex("subDeviceName")));
                eq.setNickName(cursor.getString(cursor.getColumnIndex("nickName")));
                eq.setProductKey(cursor.getString(cursor.getColumnIndex("productKey")));
                eq.setOwned(cursor.getInt(cursor.getColumnIndex("owned")));
                eq.setIsEdgeGateway(cursor.getInt(cursor.getColumnIndex("isEdgeGateway")));
                eq.setStatus(cursor.getInt(cursor.getColumnIndex("status")));
                eq.setDevice_ID(cursor.getInt(cursor.getColumnIndex("eqid")));
                eq.setDevice_status(cursor.getString(cursor.getColumnIndex("sub_status")));
                eq.setDevice_type(cursor.getString(cursor.getColumnIndex("equipmenttype")));
                eq.setIs_open(cursor.getInt(cursor.getColumnIndex("open")));
                list.add(eq);
            }

        }
        db.close();
        return list;
    }

//    /**
//     * 查找所有的设备
//     * @return
//     */
//    public List<DeviceInfoBean> findAllDevice(String current_deviceName){
//        List<DeviceInfoBean> list = new ArrayList<DeviceInfoBean>();
//        DeviceInfoBean eq = null;
//        SQLiteDatabase db = this.sys.getWritableDatabase();
//        Cursor cursor = db.rawQuery("select * from devicetable where 1 = 1 order by deviceName,eqid",null);
//        while (cursor.moveToNext()){
//            eq = new DeviceInfoBean();
//            String deviceName = cursor.getString(cursor.getColumnIndex("deviceName"));
//            String equipmenttype = cursor.getString(cursor.getColumnIndex("equipmenttype"));
//            if(!TextUtils.isEmpty(equipmenttype)){
//                if(findGatewayCount(deviceName)>0 && !TextUtils.isEmpty(current_deviceName)&& current_deviceName.equals(deviceName)){
//                    eq.setDeviceName(cursor.getString(cursor.getColumnIndex("deviceName")));
//                    eq.setIotId(cursor.getString(cursor.getColumnIndex("iotId")));
//                    eq.setSubdeviceName(cursor.getString(cursor.getColumnIndex("subDeviceName")));
//                    eq.setNickName(cursor.getString(cursor.getColumnIndex("nickName")));
//                    eq.setProductKey(cursor.getString(cursor.getColumnIndex("productKey")));
//                    eq.setOwned(cursor.getInt(cursor.getColumnIndex("owned")));
//                    eq.setIsEdgeGateway(cursor.getInt(cursor.getColumnIndex("isEdgeGateway")));
//                    eq.setStatus(cursor.getInt(cursor.getColumnIndex("status")));
//                    eq.setDevice_ID(cursor.getInt(cursor.getColumnIndex("eqid")));
//                    eq.setDevice_status(cursor.getString(cursor.getColumnIndex("sub_status")));
//                    eq.setDevice_type(cursor.getString(cursor.getColumnIndex("equipmenttype")));
//                    list.add(eq);
//                }
//            }else{
//                eq.setDeviceName(cursor.getString(cursor.getColumnIndex("deviceName")));
//                eq.setIotId(cursor.getString(cursor.getColumnIndex("iotId")));
//                eq.setSubdeviceName(cursor.getString(cursor.getColumnIndex("subDeviceName")));
//                eq.setNickName(cursor.getString(cursor.getColumnIndex("nickName")));
//                eq.setProductKey(cursor.getString(cursor.getColumnIndex("productKey")));
//                eq.setOwned(cursor.getInt(cursor.getColumnIndex("owned")));
//                eq.setIsEdgeGateway(cursor.getInt(cursor.getColumnIndex("isEdgeGateway")));
//                eq.setStatus(cursor.getInt(cursor.getColumnIndex("status")));
//                eq.setDevice_ID(cursor.getInt(cursor.getColumnIndex("eqid")));
//                eq.setDevice_status(cursor.getString(cursor.getColumnIndex("sub_status")));
//                eq.setDevice_type(cursor.getString(cursor.getColumnIndex("equipmenttype")));
//                list.add(eq);
//            }
//
//        }
//        db.close();
//        return list;
//    }

    /**
     * 查找所有的子设备列表
     * @return
     */
    public List<DeviceInfoBean> findAllSubDevice(String deviceName) {
        List<DeviceInfoBean> list = new ArrayList<DeviceInfoBean>();
        SQLiteDatabase db = this.sys.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from devicetable where 1 = 1 and eqid!=0 and deviceName = '" + deviceName + "' order by eqid", null);
        while (cursor.moveToNext()) {
            DeviceInfoBean eq = new DeviceInfoBean();
            eq.setDeviceName(cursor.getString(cursor.getColumnIndex("deviceName")));
            eq.setIotId(cursor.getString(cursor.getColumnIndex("iotId")));
            eq.setSubdeviceName(cursor.getString(cursor.getColumnIndex("subDeviceName")));
            eq.setNickName(cursor.getString(cursor.getColumnIndex("nickName")));
            eq.setProductKey(cursor.getString(cursor.getColumnIndex("productKey")));
            eq.setOwned(cursor.getInt(cursor.getColumnIndex("owned")));
            eq.setIsEdgeGateway(cursor.getInt(cursor.getColumnIndex("isEdgeGateway")));
            eq.setStatus(cursor.getInt(cursor.getColumnIndex("status")));
            eq.setDevice_ID(cursor.getInt(cursor.getColumnIndex("eqid")));
            eq.setDevice_status(cursor.getString(cursor.getColumnIndex("sub_status")));
            eq.setDevice_type(cursor.getString(cursor.getColumnIndex("equipmenttype")));
            list.add(eq);
        }
            db.close();
            return list;

    }

    /**
     * 查找所有的设备
     * @return
     */
    public List<DeviceInfoBean> findAllWifiDevice(){
        List<DeviceInfoBean> list = new ArrayList<DeviceInfoBean>();
        DeviceInfoBean eq = null;
        SQLiteDatabase db = this.sys.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from devicetable where 1 = 1 and eqid = 0 order by deviceName,eqid",null);
        while (cursor.moveToNext()){
            eq = new DeviceInfoBean();
            eq.setDeviceName(cursor.getString(cursor.getColumnIndex("deviceName")));
            eq.setIotId(cursor.getString(cursor.getColumnIndex("iotId")));
            eq.setSubdeviceName(cursor.getString(cursor.getColumnIndex("subDeviceName")));
            eq.setNickName(cursor.getString(cursor.getColumnIndex("nickName")));
            eq.setProductKey(cursor.getString(cursor.getColumnIndex("productKey")));
            eq.setOwned(cursor.getInt(cursor.getColumnIndex("owned")));
            eq.setIsEdgeGateway(cursor.getInt(cursor.getColumnIndex("isEdgeGateway")));
            eq.setStatus(cursor.getInt(cursor.getColumnIndex("status")));
            list.add(eq);

        }
        db.close();
        return list;
    }

    /**
     * 查找所有本分享的设备
     * @return
     */
    public List<DeviceInfoBean> findAllWifiShareDevice(){
        List<DeviceInfoBean> list = new ArrayList<DeviceInfoBean>();
        DeviceInfoBean eq = null;
        SQLiteDatabase db = this.sys.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from devicetable where 1 = 1 and eqid = 0 and owned !=1 order by deviceName,eqid",null);
        while (cursor.moveToNext()){
            eq = new DeviceInfoBean();
            eq.setDeviceName(cursor.getString(cursor.getColumnIndex("deviceName")));
            eq.setIotId(cursor.getString(cursor.getColumnIndex("iotId")));
            eq.setSubdeviceName(cursor.getString(cursor.getColumnIndex("subDeviceName")));
            eq.setNickName(cursor.getString(cursor.getColumnIndex("nickName")));
            eq.setProductKey(cursor.getString(cursor.getColumnIndex("productKey")));
            eq.setOwned(cursor.getInt(cursor.getColumnIndex("owned")));
            eq.setIsEdgeGateway(cursor.getInt(cursor.getColumnIndex("isEdgeGateway")));
            eq.setStatus(cursor.getInt(cursor.getColumnIndex("status")));
            list.add(eq);

        }
        db.close();
        return list;
    }

    /**
     * 查找所有的设备
     * @return
     */
    public List<DeviceInfoBean> findAllGateway(){
        List<DeviceInfoBean> list = new ArrayList<DeviceInfoBean>();
        DeviceInfoBean eq = null;
        SQLiteDatabase db = this.sys.getWritableDatabase();
        String productkey = DevType.EE_GATEWAY.getProductkey();
        Cursor cursor = db.rawQuery("select * from devicetable where 1 = 1 and eqid = 0 and productKey ='"+productkey+"' order by deviceName,eqid",null);
        while (cursor.moveToNext()){
            eq = new DeviceInfoBean();
            eq.setDeviceName(cursor.getString(cursor.getColumnIndex("deviceName")));
            eq.setIotId(cursor.getString(cursor.getColumnIndex("iotId")));
            eq.setSubdeviceName(cursor.getString(cursor.getColumnIndex("subDeviceName")));
            eq.setNickName(cursor.getString(cursor.getColumnIndex("nickName")));
            eq.setProductKey(cursor.getString(cursor.getColumnIndex("productKey")));
            eq.setOwned(cursor.getInt(cursor.getColumnIndex("owned")));
            eq.setIsEdgeGateway(cursor.getInt(cursor.getColumnIndex("isEdgeGateway")));
            eq.setStatus(cursor.getInt(cursor.getColumnIndex("status")));
            list.add(eq);

        }
        db.close();
        return list;
    }

    /**
     * 查找所有的设备
     * @return
     */
    public List<String> findAllGatewayDeviceName(){
        List<String> list = new ArrayList<String>();
        SQLiteDatabase db = this.sys.getWritableDatabase();
        String productkey = DevType.EE_GATEWAY.getProductkey();
        Cursor cursor = db.rawQuery("select * from devicetable where 1 = 1 and eqid = 0 and productKey ='"+productkey+"' order by deviceName,eqid",null);
        while (cursor.moveToNext()){
            list.add(cursor.getString(cursor.getColumnIndex("deviceName")));
        }
        db.close();
        return list;
    }

    //修改打开关闭的标识
    public void updateOpen(String deviceName,int open){
        SQLiteDatabase db = this.sys.getWritableDatabase();
        try {
            String where = "eqid = ? and deviceName = ? ";
            String[] whereValue = {String.valueOf(0),deviceName};
            ContentValues cv = new ContentValues();
            cv.put("open", open);
            cv.put("deviceName",deviceName);
            db.update("devicetable", cv, where, whereValue);
        }catch (NullPointerException e){
        }finally {
            db.close();
        }
    }

    //删除所有网关操作
    public void deleteAll()
    {
        SQLiteDatabase db = this.sys.getWritableDatabase();
        String where = "1 = 1";
        db.delete("devicetable", where, null);
        db.close();
    }

    public void deleteByDeviceName(String deviceName,int eqid){
        SQLiteDatabase db = this.sys.getWritableDatabase();
        String where = "deviceName = ? and eqid = ?";
        String[] whereValue ={ deviceName,String.valueOf(eqid)};
        db.delete("devicetable", where, whereValue);
        db.close();
    }

    public void updateGatewayName(String deviceName,String name){
        SQLiteDatabase db = this.sys.getWritableDatabase();
        String where = "deviceName = ?";
//        String[] whereValue = {Integer.toString(eq.getEquipmentId())};
        String[] whereValue = {deviceName};
        ContentValues cv = new ContentValues();
        cv.put("nickName", name);
        db.update("devicetable", cv, where, whereValue);
        db.close();
    }

    public void updateDeviceStatus(String deviceName,int status){
        SQLiteDatabase db = this.sys.getWritableDatabase();
        String where = "deviceName = ?";
//        String[] whereValue = {Integer.toString(eq.getEquipmentId())};
        String[] whereValue = {deviceName};
        ContentValues cv = new ContentValues();
        cv.put("status", status);
        db.update("devicetable", cv, where, whereValue);
        db.close();
    }


    public void updateGatewayCurrentSid(String deviceName,int sid){
        SQLiteDatabase db = this.sys.getWritableDatabase();
        String where = "deviceName = ? and eqid = ?";
//        String[] whereValue = {Integer.toString(eq.getEquipmentId())};
        String[] whereValue = {deviceName,String.valueOf(0)};
        ContentValues cv = new ContentValues();
        cv.put("currentmode", sid);
        db.update("devicetable", cv, where, whereValue);
        db.close();
    }

    /**
     * 更新网关到数据库
     * @param modelBean
     * @return
     */
    public long insertGateway(DeviceInfoBean modelBean) {

        if(modelBean == null || TextUtils.isEmpty(modelBean.getDeviceName())) {
            return -1L;
        }
        SQLiteDatabase db = this.sys.getWritableDatabase();
        ContentValues values = null;
        try {

            values = new ContentValues();
            values.put("iotId", modelBean.getIotId());
            values.put("deviceName", modelBean.getDeviceName());
            values.put("nickName",modelBean.getNickName());
            values.put("owned",modelBean.getOwned());
            values.put("productKey",modelBean.getProductKey());
            values.put("isEdgeGateway",modelBean.getIsEdgeGateway());
            values.put("status",modelBean.getStatus());
            if(!HasThisDevice(modelBean.getDeviceName(),modelBean.getDevice_ID())) {
                db = this.sys.getWritableDatabase();
                return db.insert("devicetable", null, values);
            } else {
                db = this.sys.getWritableDatabase();
                return db.update("devicetable",values , "eqid =0 and deviceName = '"+modelBean.getDeviceName()+"'",null);
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
     * 更新设备到数据库
     * @param modelBean
     * @return
     */
    public long insertDevice(DeviceInfoBean modelBean) {

        if(modelBean == null || TextUtils.isEmpty(modelBean.getDeviceName())) {
            return -1L;
        }
        SQLiteDatabase db = this.sys.getWritableDatabase();
        ContentValues values = null;
        try {

            values = new ContentValues();
            values.put("deviceName", modelBean.getDeviceName());
            values.put("eqid", modelBean.getDevice_ID());
            values.put("equipmenttype", modelBean.getDevice_type());
            values.put("sub_status", modelBean.getDevice_status());
            if(!HasThisDevice(modelBean.getDeviceName(),modelBean.getDevice_ID())) {
                db = this.sys.getWritableDatabase();
                return db.insert("devicetable", null, values);
            } else {
                db = this.sys.getWritableDatabase();
                return db.update("devicetable",values , "eqid =" + modelBean.getDevice_ID()+" and deviceName = '"+modelBean.getDeviceName()+"'",null);
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

    public void updateDeivceBinversion(String deviceName,String binVer){
        SQLiteDatabase db = this.sys.getWritableDatabase();
        String where = "deviceName = ?";
        String[] whereValue = {deviceName};
        ContentValues cv = new ContentValues();
        cv.put("binVersion",binVer);
        db.update("devicetable", cv, where, whereValue);
        db.close();
    }

    /**
     * 查找特定deviceid的网关设备
     * @return
     */
    public DeviceInfoBean findByDeviceIotId(String iotId){
        DeviceInfoBean eq = null;
        SQLiteDatabase db = this.sys.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from devicetable where iotId =? and eqid = 0",new String[]{iotId});
        if(cursor.moveToFirst()) {
            eq = new DeviceInfoBean();
            eq.setDeviceName(cursor.getString(cursor.getColumnIndex("deviceName")));
            eq.setIotId(cursor.getString(cursor.getColumnIndex("iotId")));
            eq.setSubdeviceName(cursor.getString(cursor.getColumnIndex("subDeviceName")));
            eq.setNickName(cursor.getString(cursor.getColumnIndex("nickName")));
            eq.setProductKey(cursor.getString(cursor.getColumnIndex("productKey")));
            eq.setOwned(cursor.getInt(cursor.getColumnIndex("owned")));
            eq.setIsEdgeGateway(cursor.getInt(cursor.getColumnIndex("isEdgeGateway")));
            eq.setStatus(cursor.getInt(cursor.getColumnIndex("status")));
            eq.setDevice_ID(cursor.getInt(cursor.getColumnIndex("eqid")));
            eq.setDevice_status(cursor.getString(cursor.getColumnIndex("sub_status")));
            eq.setDevice_type(cursor.getString(cursor.getColumnIndex("equipmenttype")));
        }
        db.close();
        return eq;
    }

    /**
     * 查找特定deviceid的网关设备
     * @return
     */
    public DeviceInfoBean findByDeviceid(String deviceName,int eqid){
        DeviceInfoBean eq = null;
        SQLiteDatabase db = this.sys.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from devicetable where deviceName =? and eqid=?",new String[]{deviceName,String.valueOf(eqid)});
        if(cursor.moveToFirst()) {
            eq = new DeviceInfoBean();
            eq.setDeviceName(cursor.getString(cursor.getColumnIndex("deviceName")));
            eq.setIotId(cursor.getString(cursor.getColumnIndex("iotId")));
            eq.setSubdeviceName(cursor.getString(cursor.getColumnIndex("subDeviceName")));
            eq.setNickName(cursor.getString(cursor.getColumnIndex("nickName")));
            eq.setProductKey(cursor.getString(cursor.getColumnIndex("productKey")));
            eq.setOwned(cursor.getInt(cursor.getColumnIndex("owned")));
            eq.setIsEdgeGateway(cursor.getInt(cursor.getColumnIndex("isEdgeGateway")));
            eq.setStatus(cursor.getInt(cursor.getColumnIndex("status")));
            eq.setDevice_ID(cursor.getInt(cursor.getColumnIndex("eqid")));
            eq.setDevice_status(cursor.getString(cursor.getColumnIndex("sub_status")));
            eq.setDevice_type(cursor.getString(cursor.getColumnIndex("equipmenttype")));
        }
        db.close();
        return eq;
    }

    public DeviceInfoBean findDeviceTypeByDeviceid(String deviceName,int eqid){
        DeviceInfoBean eq = null;
        SQLiteDatabase db = this.sys.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from devicetable where deviceName =? and eqid=?",new String[]{deviceName,String.valueOf(eqid)});
        if(cursor.moveToFirst()) {
            eq = new DeviceInfoBean();
            eq.setDevice_type(cursor.getString(cursor.getColumnIndex("equipmenttype")));
            eq.setSubdeviceName(cursor.getString(cursor.getColumnIndex("subDeviceName")));
        }
        db.close();
        return eq;
    }

    /**
     * @return 所有设备的数量
     */
    public int finddeviceCount(String deviceName){
        int a = 0;
        SQLiteDatabase db = this.sys.getWritableDatabase();
            Cursor cursor = db.rawQuery("select count(*) from devicetable where deviceName='"+deviceName+"'",null);
            if(cursor.moveToFirst()) {
                a = cursor.getInt(cursor.getColumnIndex("count(*)"));
            }
            db.close();
        return a;
    }

    /**
     * @return 所有的组的数量
     */
    public int findGatewayCount(String deviceName){
        int a = 0;
        SQLiteDatabase db = this.sys.getWritableDatabase();
        Cursor cursor = db.rawQuery("select count(*) from devicetable where deviceName='"+deviceName+"' and eqid = 0",null);
        if(cursor.moveToFirst()) {
            a = cursor.getInt(cursor.getColumnIndex("count(*)"));
        }
        db.close();
        return a;
    }

    /**
     * @return 查询是否存在该设备
     */
    public boolean HasThisDevice(String deviceName,int eqid){
        SQLiteDatabase db = this.sys.getWritableDatabase();
        int a = 0;
        try {
            db = this.sys.getWritableDatabase();
            Cursor cursor = db.rawQuery("select count(*) from devicetable where deviceName = '"+deviceName+"' and eqid = "+eqid,null);
            if(cursor.moveToFirst()) {
                a = cursor.getInt(cursor.getColumnIndex("count(*)"));
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }finally {
            db.close();
            return a != 0;
        }

    }

    //以下为子设备相关

    /**
     * 判断Id是否存在
     * @param eqid
     * @return
     */
    public int isIdExists(int eqid,String deviceName){
        int a = 0;
        SQLiteDatabase db = this.sys.getWritableDatabase();
        try {
            String Sql = "select count(*) from devicetable where eqid = ? and deviceName = ?";
            Cursor localCursor = db.rawQuery(Sql,new String[]{String.valueOf(eqid),deviceName});
            localCursor.moveToFirst();
            a = localCursor.getInt(localCursor.getColumnIndex("count(*)"));
        }catch (NullPointerException e){
        }finally {
            db.close();
            return a;
        }
    }

    /**
     * @return 查询是否存在该设备
     */
    public boolean IsOpenThisDevice(String deviceName){
        SQLiteDatabase db = this.sys.getWritableDatabase();
        int a = 0;
        try {
            db = this.sys.getWritableDatabase();
            Cursor cursor = db.rawQuery("select open from devicetable where deviceName = '"+deviceName+"' and eqid = 0",null);
            if(cursor.moveToFirst()) {
                a = cursor.getInt(cursor.getColumnIndex("open"));
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }finally {
            db.close();
            return a != 0;
        }

    }



    //删除所有设备操作
    public void deleteAll(String deviceName)
    {
        SQLiteDatabase db = this.sys.getWritableDatabase();
        try {
            String where = "deviceName = '"+deviceName+"'";
            db.delete("devicetable", where, null);
        }catch (NullPointerException e){
        }finally {
            db.close();
        }
    }


    //修改操作用于替换设备
    public void updateWithName(DeviceInfoBean eq)
    {
        SQLiteDatabase db = this.sys.getWritableDatabase();
        try {
            String where = "eqid = ? and deviceName = ?";
            String[] whereValue = {String.valueOf(eq.getDevice_ID()),eq.getDeviceName()};
            ContentValues cv = new ContentValues();
            cv.put("subDeviceName", eq.getSubdeviceName());
            cv.put("eqid", eq.getDevice_ID());
            cv.put("sub_status",eq.getDevice_status());
            cv.put("equipmenttype",eq.getDevice_type());
            db.update("devicetable", cv, where, whereValue);
        }catch (NullPointerException e){
        }finally {
            db.close();
        }
    }

    //修改操作
    public void updateName(DeviceInfoBean eq)
    {
        SQLiteDatabase db = this.sys.getWritableDatabase();
        try {
            String where = "eqid = ? and deviceName = ? ";
            String[] whereValue = {String.valueOf(eq.getDevice_ID()),eq.getDeviceName()};
            ContentValues cv = new ContentValues();
            cv.put("subDeviceName", eq.getSubdeviceName());
            cv.put("deviceName",eq.getDeviceName());
            db.update("devicetable", cv, where, whereValue);
        }catch (NullPointerException e){
        }finally {
            db.close();
        }
    }



    //修改温控器自动温度操作
    public void updateAutoTemp(int eq,String deviceName,String value)
    {
        SQLiteDatabase db = this.sys.getWritableDatabase();
        try {
            String where = "eqid = ? and deviceName = ?";
            String[] whereValue = {String.valueOf(eq),deviceName};
            ContentValues cv = new ContentValues();
            cv.put("autotemp", value);
            db.update("devicetable", cv, where, whereValue);
        }catch (NullPointerException e){
        }finally {
            db.close();
        }
    }

    //修改温控器手动温度操作
    public void updateHandTemp(int eq,String deviceName,String value)
    {
        SQLiteDatabase db = this.sys.getWritableDatabase();
        try {
            String where = "eqid = ? and deviceName = ?";
            String[] whereValue = {String.valueOf(eq),deviceName};
            ContentValues cv = new ContentValues();
            cv.put("handtemp", value);
            db.update("devicetable", cv, where, whereValue);
        }catch (NullPointerException e){
        }finally {
            db.close();
        }
    }

    //修改温控器防冻温度操作
    public void updateFangTemp(int eq,String deviceName,String value)
    {
        SQLiteDatabase db = this.sys.getWritableDatabase();
        try {
            String where = "eqid = ? and deviceName = ?";
            String[] whereValue = {String.valueOf(eq),deviceName};
            ContentValues cv = new ContentValues();
            cv.put("fangtemp", value);
            db.update("devicetable", cv, where, whereValue);
        }catch (NullPointerException e){
        }finally {
            db.close();
        }
    }

    /**
     * 查找输入设备
     * @return
     */
    public List<DeviceInfoBean> findInput(String deviceName){
        SQLiteDatabase db = this.sys.getWritableDatabase();
        List<DeviceInfoBean> list = new ArrayList<DeviceInfoBean>();
        try {
            Cursor cursor = db.rawQuery("select * from devicetable where deviceName = '"+deviceName+"' and (equipmenttype GLOB '*0??' or equipmenttype GLOB '*1??' or equipmenttype GLOB '*3??' or equipmenttype GLOB '*6??' or equipmenttype = '1213') ",null);
            while (cursor.moveToNext()){
                DeviceInfoBean eq = new DeviceInfoBean();
                eq.setSubdeviceName(cursor.getString(cursor.getColumnIndex("subDeviceName")));
                eq.setDeviceName(cursor.getString(cursor.getColumnIndex("deviceName")));
                eq.setDevice_status(cursor.getString(cursor.getColumnIndex("sub_status")));
                eq.setDevice_type(cursor.getString(cursor.getColumnIndex("equipmenttype")));
                eq.setDevice_ID(cursor.getInt(cursor.getColumnIndex("eqid")));
                list.add(eq);
            }
        }catch (NullPointerException e){
        }finally {
            db.close();
            return list;
        }
    }

    /**
     * 查找温湿度传感器设备
     * @return
     */
    public List<DeviceInfoBean> findThChecks(String deviceName){
        SQLiteDatabase db = this.sys.getWritableDatabase();
        List<DeviceInfoBean> list = new ArrayList<DeviceInfoBean>();
        try {
            Cursor cursor = db.rawQuery("select * from devicetable where deviceName = '"+deviceName+"' and (equipmenttype GLOB '?102' ) ",null);
            while (cursor.moveToNext()){
                DeviceInfoBean eq = new DeviceInfoBean();
                eq.setSubdeviceName(cursor.getString(cursor.getColumnIndex("subDeviceName")));
                eq.setDeviceName(cursor.getString(cursor.getColumnIndex("deviceName")));
                eq.setDevice_status(cursor.getString(cursor.getColumnIndex("sub_status")));
                eq.setDevice_type(cursor.getString(cursor.getColumnIndex("equipmenttype")));
                eq.setDevice_ID(cursor.getInt(cursor.getColumnIndex("eqid")));
                list.add(eq);
            }
        }catch (NullPointerException e){
        }finally {
            db.close();
            return list;
        }
    }

    /**
     * 除去361部分
     * @param deviceName
     * @return
     */
    public List<DeviceInfoBean> findOutput(String deviceName){
        SQLiteDatabase db = this.sys.getWritableDatabase();
        List<DeviceInfoBean> list = new ArrayList<DeviceInfoBean>();
        try {
            Cursor cursor = db.rawQuery("select * from devicetable where deviceName = '"+deviceName+"' and equipmenttype GLOB '*2??' and equipmenttype !='1213'",null);
            while (cursor.moveToNext()){
                    DeviceInfoBean eq = new DeviceInfoBean();
                    eq.setSubdeviceName(cursor.getString(cursor.getColumnIndex("subDeviceName")));
                    eq.setDeviceName(cursor.getString(cursor.getColumnIndex("deviceName")));
                    eq.setDevice_status(cursor.getString(cursor.getColumnIndex("sub_status")));
                    eq.setDevice_type(cursor.getString(cursor.getColumnIndex("equipmenttype")));
                    eq.setDevice_ID(cursor.getInt(cursor.getColumnIndex("eqid")));
                    list.add(eq);

            }
        }catch (NullPointerException e){
        }finally {
            db.close();
            return list;
        }
    }


    //find equipment by eqid for scene, no need to get status, the status has been saved before
    public DeviceInfoBean findByeqid(int eqid, String deviceName){
        SQLiteDatabase db = this.sys.getWritableDatabase();
        DeviceInfoBean eq = null;
        try {
            Cursor cursor = db.rawQuery("select subDeviceName,equipmenttype from devicetable where eqid =? and deviceName = ?",new String[]{String.valueOf(eqid),deviceName});
            if(cursor.moveToFirst()) {
                eq = new DeviceInfoBean();
                eq.setSubdeviceName(cursor.getString(cursor.getColumnIndex("subDeviceName")));
                eq.setDevice_type(cursor.getString(cursor.getColumnIndex("equipmenttype")));
            }
        }catch (NullPointerException e){
        }finally {
            db.close();
            return eq;
        }
    }

    /**
     * 查找温控器自动设置温度缓存值
     * @return
     */
    public String findTempByAutoModel(String deviceName,int eqid){

        SQLiteDatabase db = this.sys.getWritableDatabase();
        String eq = null;
        try {
            Cursor cursor = db.rawQuery("select autotemp from devicetable where eqid =? and deviceName = ?",new String[]{String.valueOf(eqid),deviceName});
            if(cursor.moveToFirst()) {
                eq =(cursor.getString(cursor.getColumnIndex("autotemp")));
            }
        }catch (NullPointerException e){
        }finally {
            db.close();
            return eq;
        }

    }

    /**
     * 查找温控器手动设置温度缓存值
     * @return
     */
    public String findTempByHandModel(String deviceName,int eqid){
        SQLiteDatabase db = this.sys.getWritableDatabase();
        String eq = null;
        try {
            Cursor cursor = db.rawQuery("select handtemp from deviceName where eqid =? and deviceName = ?",new String[]{String.valueOf(eqid),deviceName});
            if(cursor.moveToFirst()) {
                eq =(cursor.getString(cursor.getColumnIndex("handtemp")));
            }
        }catch (NullPointerException e){
        }finally {
            db.close();
            return eq;
        }
    }

    /**
     * 查找温控器防冻设置温度缓存值
     * @return
     */
    public String findTempByFangModel(String deviceName,int eqid){
        SQLiteDatabase db = this.sys.getWritableDatabase();
        String eq = null;
        try {
            Cursor cursor = db.rawQuery("select fangtemp from devicetable where eqid =? and deviceName = ?",new String[]{String.valueOf(eqid),deviceName});
            if(cursor.moveToFirst()) {
                eq =(cursor.getString(cursor.getColumnIndex("fangtemp")));
            }
        }catch (NullPointerException e){
        }finally {
            db.close();
            return eq;
        }
    }

    public int findCurrentSidDeviceid(String deviceName){
        int eq = -1;
        SQLiteDatabase db = this.sys.getWritableDatabase();
        Cursor cursor = db.rawQuery("select currentmode from devicetable where deviceName =? ",new String[]{deviceName});
        if(cursor.moveToFirst()) {
            eq = cursor.getInt(cursor.getColumnIndex("currentmode"));
        }
        db.close();
        return eq;
    }

    /**
     * 判断此种类型的设备是否存在
     * @param deviceType
     * @return
     */
    public int isDevTypeExists(String deviceType){
        if(TextUtils.isEmpty(deviceType) || deviceType.length()!=4){
            return 0;
        }

        int a = 0;
        SQLiteDatabase db = this.sys.getWritableDatabase();
        try {
            String Sql = "select count(*) from devicetable where equipmenttype GLOB '*"+deviceType.substring(1)+"'";
            Cursor localCursor = db.rawQuery(Sql,null);
            localCursor.moveToFirst();
            a = localCursor.getInt(localCursor.getColumnIndex("count(*)"));
        }catch (NullPointerException e){
        }finally {
            db.close();
            return a;
        }
    }

}
