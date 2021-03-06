package com.example.fyu.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.example.fyu.bean.Staff;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "STAFF".
*/
public class StaffDao extends AbstractDao<Staff, Long> {

    public static final String TABLENAME = "STAFF";

    /**
     * Properties of entity Staff.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property Sex = new Property(2, String.class, "sex", false, "SEX");
        public final static Property Department = new Property(3, String.class, "department", false, "DEPARTMENT");
        public final static Property Salary = new Property(4, double.class, "salary", false, "SALARY");
    }


    public StaffDao(DaoConfig config) {
        super(config);
    }
    
    public StaffDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"STAFF\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"NAME\" TEXT," + // 1: name
                "\"SEX\" TEXT," + // 2: sex
                "\"DEPARTMENT\" TEXT," + // 3: department
                "\"SALARY\" REAL NOT NULL );"); // 4: salary
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"STAFF\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Staff entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        String sex = entity.getSex();
        if (sex != null) {
            stmt.bindString(3, sex);
        }
 
        String department = entity.getDepartment();
        if (department != null) {
            stmt.bindString(4, department);
        }
        stmt.bindDouble(5, entity.getSalary());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Staff entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        String sex = entity.getSex();
        if (sex != null) {
            stmt.bindString(3, sex);
        }
 
        String department = entity.getDepartment();
        if (department != null) {
            stmt.bindString(4, department);
        }
        stmt.bindDouble(5, entity.getSalary());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Staff readEntity(Cursor cursor, int offset) {
        Staff entity = new Staff( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // name
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // sex
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // department
            cursor.getDouble(offset + 4) // salary
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Staff entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setSex(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setDepartment(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setSalary(cursor.getDouble(offset + 4));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Staff entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Staff entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Staff entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
