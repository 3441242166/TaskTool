package com.example.wanhao.tasktool.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wanhao on 2017/8/9.
 */

public class DatabaseHelper extends SQLiteOpenHelper{

    public DatabaseHelper(Context context) {
        super(context, "mySQLite.db", null, 8);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //日期时间  日期  时间  内容  结束日期  优先级 是否完成(1 为 完成 0 为未完成)
        db.execSQL("create table TASK (DATETIME text primary key, CONTANT text,DATE text,TIME text" +
                ",ENDDATE text,FINISHDATE text,PRIORITY integer,ISFINISH text)");
        db.execSQL("create table USERWORD (word text primary key, mean text,GQ text,GQFC text" +
                ",XZFC text,FS text,example text,lv text)");

        db.execSQL("create table TIMETASK (datetime text primary key, image text,title INTEGER,time text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //用户生词库
//        if(newVersion == 8) {
//            db.execSQL("drop table TIMETASK");
//            db.execSQL("create table TIMETASK (datetime text primary key, image text,title INTEGER,time text)");
//        }
    }
}
