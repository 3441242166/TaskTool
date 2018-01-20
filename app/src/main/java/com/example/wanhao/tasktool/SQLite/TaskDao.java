package com.example.wanhao.tasktool.SQLite;

/**
 * Created by wanhao on 2017/8/9.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.wanhao.tasktool.bean.Task;
import com.example.wanhao.tasktool.tool.MyDate;

import java.util.ArrayList;
import java.util.List;

public class TaskDao {
    private DatabaseHelper mMyDBHelper;

    public TaskDao(Context context) {
        mMyDBHelper=new DatabaseHelper(context);
    }
    // 增加的方法吗，返回的的是一个long值
    public long addTask(String datetime,String contant,String enddate,int priority){
        // 增删改查每一个方法都要得到数据库，然后操作完成后一定要关闭
        // getWritableDatabase(); 执行后数据库文件才会生成
        // 数据库文件利用DDMS可以查看，在 data/data/包名/databases 目录下即可查看
        SQLiteDatabase sqLiteDatabase =  mMyDBHelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();

        contentValues.put("DATETIME",datetime);
        contentValues.put("FINISHDATE"," ");
        contentValues.put("DATE", MyDate.getDateString(datetime));
        contentValues.put("TIME","06:00:00");
        contentValues.put("CONTANT", contant);
        contentValues.put("ENDDATE", enddate);
        contentValues.put("PRIORITY", priority);
        contentValues.put("ISFINISH", "n");

        // 返回,显示数据添加在第几行
        // 加了现在连续添加了3行数据,突然删掉第三行,然后再添加一条数据返回的是4不是3
        // 因为自增长
        long rowid=sqLiteDatabase.insert("TASK",null,contentValues);

        sqLiteDatabase.close();
        return rowid;
    }

    // 删除方法，返回值是int
    public int deleteTask(String date){
        SQLiteDatabase sqLiteDatabase = mMyDBHelper.getWritableDatabase();
        int deleteResult = sqLiteDatabase.delete("TASK","DATETIME=?", new String[]{date});
        sqLiteDatabase.close();
        return deleteResult;
    }
    //变更为完成
    public int updateTaskFinish(String date){
        SQLiteDatabase sqLiteDatabase = mMyDBHelper.getWritableDatabase();
        ContentValues contentValues =new ContentValues();
        contentValues.put("ISFINISH","y");
        contentValues.put("FINISHDATE",MyDate.getNowDateString());
        int updateResult = sqLiteDatabase.update("TASK", contentValues, "DATETIME=?", new String[]{date});
        sqLiteDatabase.close();
        return updateResult;
    }
    public int updateTaskEndDate(String date,String newEndDate){
        SQLiteDatabase sqLiteDatabase = mMyDBHelper.getWritableDatabase();
        ContentValues contentValues =new ContentValues();
        contentValues.put("ENDDATE",newEndDate);
        int updateResult = sqLiteDatabase.update("TASK", contentValues, "DATETIME=?", new String[]{date});
        sqLiteDatabase.close();
        return updateResult;
    }

    // num==1 为完成  num==0 未完成
    public List<Task> alterTaskDateFinish(String num){

        SQLiteDatabase readableDatabase = mMyDBHelper.getReadableDatabase();
        Cursor cursor = null;
        // 查询比较特别,涉及到 cursor
        if(num.equals("all")){
            Log.i("TaskDao","alter All");
            cursor = readableDatabase.rawQuery("select * from TASK ", null);
        }else {
            Log.i("TaskDao","alter by limite");
            cursor = readableDatabase.rawQuery("select * from TASK where ISFINISH=?", new String[]{num});
        }
        List<Task> list =new ArrayList<Task>();

        while (cursor.moveToNext()) {
            Task task = new Task();
            task.setFinishDate(cursor.getString(cursor.getColumnIndex("FINISHDATE")));
            task.setContant(cursor.getString(cursor.getColumnIndex("CONTANT")));
            task.setDateTime(cursor.getString(cursor.getColumnIndex("DATETIME")));
            task.setEndDate(cursor.getString(cursor.getColumnIndex("ENDDATE")));
            task.setDate(cursor.getString(cursor.getColumnIndex("DATE")));
            task.setTime(cursor.getString(cursor.getColumnIndex("TIME")));
            task.setPriority(cursor.getInt(cursor.getColumnIndex("PRIORITY")));
            if(cursor.getString(cursor.getColumnIndex("ISFINISH")).equals("y")){
                task.setFinish(true);
            }
            else {
                task.setFinish(false);
            }
            list.add(task);
        }

        cursor.close(); // 记得关闭 corsor
        readableDatabase.close(); // 关闭数据库
        return list;
    }

    public List<Task> alterTaskOutDate(boolean is) {
        SQLiteDatabase readableDatabase = mMyDBHelper.getReadableDatabase();

        List<Task> list = alterTaskDateFinish("n");
        List<Task> chooseList = new ArrayList<>();

        if(is) {
            for (int x = 0; x < list.size(); x++) {
                if (MyDate.compareStringByDate(list.get(x).getEndDate(), MyDate.getNowDateString()) == -1) {
                    chooseList.add(list.get(x));
                }
            }
        }else{
            for (int x = 0; x < list.size(); x++) {
                if (MyDate.compareStringByDate(list.get(x).getEndDate(), MyDate.getNowDateString())>= 0) {
                    chooseList.add(list.get(x));
                }
            }
        }

        readableDatabase.close(); // 关闭数据库
        return chooseList;
    }

}
