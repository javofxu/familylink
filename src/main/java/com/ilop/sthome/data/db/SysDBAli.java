package com.ilop.sthome.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.common.utils.SpUtil;


/*
@class SysDBAli
@autor henry
@time 2019/4/18 2:24 PM
@email xuejunju_4595@qq.com
*/
public class SysDBAli extends SQLiteOpenHelper {
    //    创建表格
    private String sysMTable = "create table if not exists sysMTable(name varchar(200),modledesc varchar(200),choice integer default(0),sid integer default(0),deviceName varchar(30),color varchar(10),primary key(sid,deviceName))";
    private String pacTable = "create table if not exists pacTable(id integer primary key autoincrement,name varchar(200) not null,packageid integer,desc varchar(200),sort integer,deviceName varchar(30))";
    private String scTable = "create table if not exists scTable(name varchar(200),code varchar(100),desc varchar(100),mid integer default(0),deviceName varchar(30),primary key(mid,deviceName))";
    private String noticeTable = "create table if not exists noticetable(type varchar(5),mid integer default(0),eqid integer default(0),equipmenttype varchar(10),eqstatus varchar(10),activitytime BIGINT default(0),desc varchar(100),deviceName varchar(30),name varchar(150),primary key(desc,deviceName))";
    private String deviceTable = "create table if not exists devicetable(iotId varchar(30),deviceName varchar(100),subDeviceName varchar(100),nickName varchar(100),owned integer default(1),productKey varchar(40),isEdgeGateway integer default(0),status integer default(1),binVersion varchar(50),longtitude varchar(50),latitude varchar(50),groupId varchar(30),eqid integer default(0),equipmenttype varchar(20),sub_status varchar(20),sort integer default (0),autotemp varchar(200),handtemp varchar(200),fangtemp varchar(200),timesee integer,parentId varchar(30),currentmode integer default(-1),open integer default(0), reserve varchar(100),primary key(iotId,deviceName,eqid))";
    private String timerTable = "create table if not exists timertable(id integer primary key autoincrement,timerid integer default(0),enable integer,modeid integer default(0),week varchar(5),hour varchar(5),min varchar(5),code varchar(20),deviceName varchar(30))";
    private String gs584relationshiptable = "create table if not exists gs584relationshiptable(sid integer default(0),eqid integer default(0),action integer default(0),delay integer default(0),deviceName varchar(30),primary key(sid,eqid,action,deviceName))";
    private String scenerelationshiptable = "create table if not exists scenerelationshiptable(sid integer default(0),mid integer default(0),deviceName varchar(30),primary key(sid,mid,deviceName))";
    private String logTable = "create table if not exists logtable(eqid integer default(0),equipmenttype varchar(10),eqstatus varchar(10),activitytime BIGINT default(0),code varchar(100),deviceName varchar(30),reserve varchar(150),reserve2 integer,primary key(code,deviceName))";
    public SysDBAli(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public SysDBAli(Context context){
        super(context, SpUtil.getString(context, "userId") + "_sys_ali_db.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sysMTable);
        db.execSQL(pacTable);
        db.execSQL(scTable);
        db.execSQL(noticeTable);
        db.execSQL(deviceTable);
        db.execSQL(timerTable);
        db.execSQL(gs584relationshiptable);
        db.execSQL(scenerelationshiptable);
        db.execSQL(logTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {





    }

    // 删除字段或则修改字段的方法
    public void alterCloumn(SQLiteDatabase db, String alterTableName,
                            String create_Table_Sql, String copy_Sql) {

        final String DROP_TEMP_TABLE = "drop table if exists tempTable";
        // 重新命名修改的表
        db.execSQL("alter table " + alterTableName + " rename to tempTable");
        // 重新创建修改的表
        db.execSQL(create_Table_Sql);
        // 将临时表里的数据copy到新的数据库中
        db.execSQL(copy_Sql);
        // 最后删掉临时表
        db.execSQL(DROP_TEMP_TABLE);
        Log.i("update", "--------");
    }



}
