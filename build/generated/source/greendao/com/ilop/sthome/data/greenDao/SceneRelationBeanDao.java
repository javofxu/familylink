package com.ilop.sthome.data.greenDao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "SCENE_RELATION_BEAN".
*/
public class SceneRelationBeanDao extends AbstractDao<SceneRelationBean, Long> {

    public static final String TABLENAME = "SCENE_RELATION_BEAN";

    /**
     * Properties of entity SceneRelationBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Sid = new Property(1, int.class, "sid", false, "SID");
        public final static Property Mid = new Property(2, int.class, "mid", false, "MID");
        public final static Property DeviceName = new Property(3, String.class, "deviceName", false, "DEVICE_NAME");
    }


    public SceneRelationBeanDao(DaoConfig config) {
        super(config);
    }
    
    public SceneRelationBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"SCENE_RELATION_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"SID\" INTEGER NOT NULL ," + // 1: sid
                "\"MID\" INTEGER NOT NULL ," + // 2: mid
                "\"DEVICE_NAME\" TEXT);"); // 3: deviceName
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"SCENE_RELATION_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, SceneRelationBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getSid());
        stmt.bindLong(3, entity.getMid());
 
        String deviceName = entity.getDeviceName();
        if (deviceName != null) {
            stmt.bindString(4, deviceName);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, SceneRelationBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getSid());
        stmt.bindLong(3, entity.getMid());
 
        String deviceName = entity.getDeviceName();
        if (deviceName != null) {
            stmt.bindString(4, deviceName);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public SceneRelationBean readEntity(Cursor cursor, int offset) {
        SceneRelationBean entity = new SceneRelationBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getInt(offset + 1), // sid
            cursor.getInt(offset + 2), // mid
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3) // deviceName
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, SceneRelationBean entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setSid(cursor.getInt(offset + 1));
        entity.setMid(cursor.getInt(offset + 2));
        entity.setDeviceName(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(SceneRelationBean entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(SceneRelationBean entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(SceneRelationBean entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
