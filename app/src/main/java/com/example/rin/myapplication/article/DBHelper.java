package com.example.rin.myapplication.article;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Rin on 2018/12/23.
 */

public class DBHelper extends SQLiteOpenHelper {
    public static final String TABLE_ARTICLE="article0";
    public static final String A_ID="_id";
    public static final String A_USERNAME="name";
    public static final String A_TITLE="title";
    public static final String A_CONTENT="contents";
    public static final String A_CATEGORY="category";
    public static final String A_TIME="time";
    public static final String A_IMAGE="img";

    public static final String CREATE_TABLE_ARTICLE="create table "+TABLE_ARTICLE+"("+
            A_ID+" integer primary key autoincrement,"+A_USERNAME+" text not null,"+A_TITLE+" text not null,"+A_CONTENT+
            " text not null,"+A_CATEGORY+" text not null,"+A_TIME+" text not null,"+A_IMAGE+" text default null)";


    public DBHelper(Context context) {
        super(context,"Data.db",null,1);
    }
    public DBHelper(Context context, int version) {
        super(context,"Data.db",null, version);
    }

    @Override
    //数据库不存在，创建数据库时调用,用来创建表
    public void onCreate(SQLiteDatabase db) {
        //创建表只需一次
        db.execSQL(CREATE_TABLE_ARTICLE);
        //Log.i("Test","oncreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("Test",oldVersion+" "+newVersion);
        db.execSQL("drop table if exists "+TABLE_ARTICLE);
        onCreate(db);

    }
}
