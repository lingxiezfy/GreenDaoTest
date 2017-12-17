package com.example.fyu.application;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.example.fyu.dao.DaoMaster;
import com.example.fyu.dao.DaoSession;

/**
 * Created by fyu on 2017-12-12.
 */

public class MyApplication extends Application{
    private DaoMaster.DevOpenHelper helper;
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    public static MyApplication instances;

    @Override
    public void onCreate() {
        super.onCreate();
        initDatabase();
        instances = this;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
    private void initDatabase() {
        helper = new DaoMaster.DevOpenHelper(this,"test.db",null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }
    public static MyApplication getInstances(){
        return instances;
    }
}
