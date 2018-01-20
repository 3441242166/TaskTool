package com.example.wanhao.tasktool.SQLite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.wanhao.tasktool.R;
import com.example.wanhao.tasktool.bean.EnglishWord;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanhao on 2017/10/19.
 */

public class EnglishWordDao {
    private static final String TAG = "EnglishWordDao";
    private static  SQLiteDatabase database;
    Context context;

    public EnglishWordDao(Context context) {
        this.context = context;
    }

    public List<EnglishWord> alterWord(String str) {
        if(database==null){
            database =  openDatabase(context);
        }
        List<EnglishWord> chooseList = new ArrayList<>();
        Cursor cursor = database.rawQuery("select * from \"words(5)\" where Word like \""+str+"%\" LIMIT 15", new String[]{});
        while (cursor.moveToNext()) {
            EnglishWord word = new EnglishWord();
            word.setWord(cursor.getString(cursor.getColumnIndex("Word")));
            word.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            word.setPast(cursor.getString(cursor.getColumnIndex("GQS")));
            word.setPastTwo(cursor.getString(cursor.getColumnIndex("GQFC")));
            word.setIng(cursor.getString(cursor.getColumnIndex("XZFC")));
            word.setWordss(cursor.getString(cursor.getColumnIndex("FS")));
            word.setMean(cursor.getString(cursor.getColumnIndex("meaning")));
            word.setExample(cursor.getString(cursor.getColumnIndex("lx")));
            chooseList.add(word);
        }
        return chooseList;
    }

    public List<EnglishWord> alterWordByChinese(String str) {
        if(database==null){
            database =  openDatabase(context);
        }
        List<EnglishWord> chooseList = new ArrayList<>();
        Cursor cursor = database.rawQuery("select * from \"words(5)\" where meaning like \"%"+str+"%\" LIMIT 15", new String[]{});
        while (cursor.moveToNext()) {
            EnglishWord word = new EnglishWord();
            word.setWord(cursor.getString(cursor.getColumnIndex("Word")));
            word.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            word.setPast(cursor.getString(cursor.getColumnIndex("GQS")));
            word.setPastTwo(cursor.getString(cursor.getColumnIndex("GQFC")));
            word.setIng(cursor.getString(cursor.getColumnIndex("XZFC")));
            word.setWordss(cursor.getString(cursor.getColumnIndex("FS")));
            word.setMean(cursor.getString(cursor.getColumnIndex("meaning")));
            word.setExample(cursor.getString(cursor.getColumnIndex("lx")));
            chooseList.add(word);
        }
        return chooseList;
    }

    public EnglishWord alterWord(int id) {
        if(database==null){
            database =  openDatabase(context);
        }
        EnglishWord word = new EnglishWord();
        Cursor cursor = database.rawQuery("select * from \"words(5)\" where id=?", new String[]{String.valueOf(id)});
        while (cursor.moveToNext()) {
            word.setWord(cursor.getString(cursor.getColumnIndex("Word")));
            word.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            word.setPast(cursor.getString(cursor.getColumnIndex("GQS")));
            word.setPastTwo(cursor.getString(cursor.getColumnIndex("GQFC")));
            word.setIng(cursor.getString(cursor.getColumnIndex("XZFC")));
            word.setWordss(cursor.getString(cursor.getColumnIndex("FS")));
            word.setMean(cursor.getString(cursor.getColumnIndex("meaning")));
            word.setExample(cursor.getString(cursor.getColumnIndex("lx")));
        }
        return word;
    }

    private static SQLiteDatabase openDatabase(Context context) {

        try {
            // 获得文件的绝对路径
            String databaseFilename = context.getFilesDir().getPath() + "sqliteword.db";
            File dir = new File(context.getFilesDir().getPath());

            if (!dir.exists()) {
                dir.mkdir();
            };

            if (!(new File(databaseFilename)).exists()) {
                InputStream is = context.getResources().openRawResource(R.raw.sqliteword);
                FileOutputStream fos = new FileOutputStream(databaseFilename);
                byte[] buffer = new byte[8192];
                int count = 0;
                // 开始复制文件
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                fos.close();
                is.close();
            }

            SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(databaseFilename, null);
            return database;
        } catch (Exception e) {
            Log.i("open error", e.getMessage());
        }
        return null;
    }

    public void closeSQLDatabase(){
        database.close();
    }

    public List<EnglishWord> getRandomWords(int sum){
        if(database==null){
            database =  openDatabase(context);
        }
        int []ar = new int[sum];
        for(int x=0;x<sum;x++){
            ar[x] =(int) (Math.random()*15328);
        }
        String sql ="select * from \"words(5)\" where ";
        for(int x=0;x<sum;x++){
            if(x!=sum-1){
                sql +="id =" +ar[x] +" or ";
            }else{
                sql+="id = "+ar[x]+" ";
            }
        }
        Log.i(TAG, "getRandomWords: sql =" + sql);
        List<EnglishWord> chooseList = new ArrayList<>();
        Cursor cursor = database.rawQuery(sql, new String[]{});
        while (cursor.moveToNext()) {
            EnglishWord word = new EnglishWord();
            word.setWord(cursor.getString(cursor.getColumnIndex("Word")));
            word.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            word.setPast(cursor.getString(cursor.getColumnIndex("GQS")));
            word.setPastTwo(cursor.getString(cursor.getColumnIndex("GQFC")));
            word.setIng(cursor.getString(cursor.getColumnIndex("XZFC")));
            word.setWordss(cursor.getString(cursor.getColumnIndex("FS")));
            word.setMean(cursor.getString(cursor.getColumnIndex("meaning")));
            word.setExample(cursor.getString(cursor.getColumnIndex("lx")));
            chooseList.add(word);
        }
        return chooseList;
    }
}
