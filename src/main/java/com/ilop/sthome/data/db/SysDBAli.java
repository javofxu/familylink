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
    private String scTable = "create table if not exists scTable(name varchar(200),code varchar(100),desc varchar(100),mid integer default(0),deviceName varchar(30),primary key(mid,deviceName))";
    private String gs584relationshiptable = "create table if not exists gs584relationshiptable(sid integer default(0),eqid integer default(0),action integer default(0),delay integer default(0),deviceName varchar(30),primary key(sid,eqid,action,deviceName))";
    private String scenerelationshiptable = "create table if not exists scenerelationshiptable(sid integer default(0),mid integer default(0),deviceName varchar(30),primary key(sid,mid,deviceName))";

    public SysDBAli(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public SysDBAli(Context context){
        super(context, SpUtil.getString(context, "userId") + "_sys_ali_db.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sysMTable);
        db.execSQL(scTable);
        db.execSQL(gs584relationshiptable);
        db.execSQL(scenerelationshiptable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
