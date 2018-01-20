package com.example.wanhao.tasktool.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.wanhao.tasktool.bean.EnglishWord;
import com.example.wanhao.tasktool.bean.MyWord;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanhao on 2017/10/21.
 */

public class UserWordDao {
    private DatabaseHelper mMyDBHelper;

    public UserWordDao(Context context) {
        mMyDBHelper=new DatabaseHelper(context);
    }
    // 增加的方法吗，返回的的是一个long值
    public long addEnglishWord(EnglishWord word){
        // 增删改查每一个方法都要得到数据库，然后操作完成后一定要关闭
        // getWritableDatabase(); 执行后数据库文件才会生成
        // 数据库文件利用DDMS可以查看，在 data/data/包名/databases 目录下即可查看
        SQLiteDatabase sqLiteDatabase =  mMyDBHelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();

        contentValues.put("word",word.getWord());
        contentValues.put("mean",word.getMean());
        contentValues.put("GQ", word.getPast());
        contentValues.put("GQFC",word.getPastTwo());
        contentValues.put("XZFC", word.getIng());
        contentValues.put("FS", word.getWordss());
        contentValues.put("example", word.getExample());
        contentValues.put("lv", 0);

        long rowid=sqLiteDatabase.insert("USERWORD",null,contentValues);

        sqLiteDatabase.close();
        return rowid;
    }

    // 删除方法，返回值是int
    public int deleteWord(String word){
        SQLiteDatabase sqLiteDatabase = mMyDBHelper.getWritableDatabase();
        int deleteResult = sqLiteDatabase.delete("USERWORD","word=?", new String[]{word});
        sqLiteDatabase.close();
        return deleteResult;
    }

    //变更熟练度
    public int updateWordLv(boolean add,MyWord word){
        SQLiteDatabase sqLiteDatabase = mMyDBHelper.getWritableDatabase();
        ContentValues contentValues =new ContentValues();
        if(add){
            int num = word.getLv()+1;
            if(num>4){
                deleteWord(word.getWord());
                return -1;
            }else {
                contentValues.put("lv", String.valueOf(word.getLv() + 1));
            }
        }else {
            int num = word.getLv()-1;
            if(num<0){
                return -1;
            }else {
                contentValues.put("lv", String.valueOf(word.getLv() - 1));
            }
        }

        int updateResult = sqLiteDatabase.update("USERWORD", contentValues, "word=?", new String[]{word.getWord()});
        sqLiteDatabase.close();
        return updateResult;
    }

    // num==1 为完成  num==0 未完成
    public List<MyWord> alterAllEnglishWod(){

        SQLiteDatabase readableDatabase = mMyDBHelper.getReadableDatabase();

        Cursor cursor = readableDatabase.rawQuery("select * from USERWORD ORDER BY word ASC", new String[]{});

        List<MyWord> list =new ArrayList<MyWord>();

        while (cursor.moveToNext()) {
            MyWord myWord = new MyWord();
            myWord.setWord(cursor.getString(cursor.getColumnIndex("word")));
            myWord.setMean(cursor.getString(cursor.getColumnIndex("mean")));
            myWord.setPast(cursor.getString(cursor.getColumnIndex("GQ")));
            myWord.setPastTwo(cursor.getString(cursor.getColumnIndex("GQFC")));
            myWord.setIng(cursor.getString(cursor.getColumnIndex("XZFC")));
            myWord.setWordss(cursor.getString(cursor.getColumnIndex("FS")));
            myWord.setLv(cursor.getInt(cursor.getColumnIndex("lv")));
            myWord.setExample(cursor.getString(cursor.getColumnIndex("example")));

            list.add(myWord);
        }

        cursor.close(); // 记得关闭 corsor
        readableDatabase.close(); // 关闭数据库
        return list;
    }

    public boolean alterAllEnglishWod(String word){

        SQLiteDatabase readableDatabase = mMyDBHelper.getReadableDatabase();

        Cursor cursor = readableDatabase.rawQuery("select * from USERWORD where word=?", new String[]{word});


        while (cursor.moveToNext()) {
            cursor.close(); // 记得关闭 corsor
            readableDatabase.close(); // 关闭数据库
            return true;
        }

        cursor.close(); // 记得关闭 corsor
        readableDatabase.close(); // 关闭数据库
        return false;
    }
}
