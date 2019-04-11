package com.example.rin.myapplication.login;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Rin on 2018/12/20.
 */

public class DBHelper extends SQLiteOpenHelper {

    /*private final static int DATABASE_VERSION = 1;
    private final static String DATABASE_NAME = "Data.db";
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }*/
    public DBHelper(Context context) {
        super(context,"Data.db",null,1);
    }
    public DBHelper(Context context, int version) {
        super(context, "Data.db", null, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建表
        String usersInfo_table = "create table usertable" +
                "(id integer primary key autoincrement, username text," +
                "password text)";
        db.execSQL(usersInfo_table);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("alter table usertable add column other string");
    }
}
