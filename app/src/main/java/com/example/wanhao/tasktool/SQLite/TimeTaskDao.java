package com.example.wanhao.tasktool.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.wanhao.tasktool.bean.TimeTask;
import com.example.wanhao.tasktool.tool.MyDate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanhao on 2017/10/27.
 */

public class TimeTaskDao {
    private DatabaseHelper mMyDBHelper;

    public TimeTaskDao(Context context) {
        mMyDBHelper=new DatabaseHelper(context);
    }
    // 增加的方法吗，返回的的是一个long值
    public long addTimeTask(TimeTask timeTask){
        // 增删改查每一个方法都要得到数据库，然后操作完成后一定要关闭
        // getWritableDatabase(); 执行后数据库文件才会生成
        // 数据库文件利用DDMS可以查看，在 data/data/包名/databases 目录下即可查看
        SQLiteDatabase sqLiteDatabase =  mMyDBHelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();

        contentValues.put("datetime", MyDate.getNowDateTimeString());
        contentValues.put("image",timeTask.getImageID());
        contentValues.put("title", timeTask.getTitle());
        contentValues.put("time",timeTask.getTime());

        long rowid=sqLiteDatabase.insert("TIMETASK",null,contentValues);

        sqLiteDatabase.close();
        return rowid;
    }

    // 删除方法，返回值是int
    public int deleteTimeTask(String datetime){
        SQLiteDatabase sqLiteDatabase = mMyDBHelper.getWritableDatabase();
        int deleteResult = sqLiteDatabase.delete("TIMETASK","datetime=?", new String[]{datetime});
        sqLiteDatabase.close();
        return deleteResult;
    }

    //变更熟练度
    public int updateTime(String datetime,String newTime){
        SQLiteDatabase sqLiteDatabase = mMyDBHelper.getWritableDatabase();
        ContentValues contentValues =new ContentValues();
        contentValues.put("time", newTime);

        int updateResult = sqLiteDatabase.update("TIMETASK", contentValues, "datetime=?", new String[]{datetime});
        sqLiteDatabase.close();
        return updateResult;
    }

    public List<TimeTask> alterAllTimeTask(){

        SQLiteDatabase readableDatabase = mMyDBHelper.getReadableDatabase();

        Cursor cursor = readableDatabase.rawQuery("select * from TIMETASK", new String[]{});

        List<TimeTask> list =new ArrayList<TimeTask>();

        while (cursor.moveToNext()) {
            TimeTask myWord = new TimeTask();
            myWord.setDateTime(cursor.getString(cursor.getColumnIndex("datetime")));
            myWord.setTime(cursor.getString(cursor.getColumnIndex("time")));
            myWord.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            myWord.setImageID(cursor.getInt(cursor.getColumnIndex("image")));

            list.add(myWord);
        }

        cursor.close(); // 记得关闭 corsor
        readableDatabase.close(); // 关闭数据库
        return list;
    }
}