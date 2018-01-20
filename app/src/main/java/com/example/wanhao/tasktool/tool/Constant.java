package com.example.wanhao.tasktool.tool;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by wanhao on 2017/10/5.
 */

public class Constant {
    //----------------------------标记task的变化----------------------------------------------------
    public static boolean ISCHANGE =true;
    public static boolean ISNEEDPASSWORD = false;
    public static boolean ISTODAYINIT = true;
    //----------------------------noitification ID--------------------------------------------------
    public final static int FIRSTINITID = 0;
    public final static int TIMETASKID = 1;
    //----------------------------broadcastreceiver-------------------------------------------------
    public final static String TIMETASK_ACTION ="TIME_TASK_ACTION";
    //----------------------------是否开启密码验证---------------------------------------------------
    public final static String PASSWORD_CODE = "password_code";
    public final static String CODE_FILE_NAME = "is_open_code";

    //----------------------------每日一次的数据检查-------------------------------------------------
    public static final String TODAY_CHECK_CODE = "today_string";
    public static final String TODAY_CHECK_FILE_NAME = "is_check_today";

    //----------------------------APP第一次启动初始化------------------------------------------------
    public static final String FIRST_INIT_CODE = "today_string";
    public static final String FIRST_INIT_FILE_NAME = "is_check_today";

    public static void FirstInit(Context context){
        //初始化是否需要密码
        String token = SaveDataUtil.getValueFromSharedPreferences(context, Constant.CODE_FILE_NAME,Constant.PASSWORD_CODE);
        if(!TextUtils.isEmpty(token)){
            ISNEEDPASSWORD = true;
        }
        //检测 每日初始化是否执行过， 没有则执行每日初始化
        token = SaveDataUtil.getValueFromSharedPreferences(context, Constant.TODAY_CHECK_FILE_NAME,Constant.TODAY_CHECK_CODE);
        if(TextUtils.isEmpty(token)){
            //应用第一次初始化
            SaveDataUtil.saveToSharedPreferences(context, Constant.TODAY_CHECK_FILE_NAME,
                    Constant.TODAY_CHECK_CODE, MyDate.getNowDateString());
            ISTODAYINIT = false;
            Log.i("Constant", "第一次初始化");
        }else{
            if(!token.equals(MyDate.getNowDateString())){
                Log.i("Constant", "每日初始化");
                SaveDataUtil.saveToSharedPreferences(context, Constant.TODAY_CHECK_FILE_NAME,
                        Constant.TODAY_CHECK_CODE, MyDate.getNowDateString());
                ISTODAYINIT = false;
                //今日未执行过每日初始化
            }else{
                ISTODAYINIT = true;
                Log.i("Constant", "今日已经初始化过了");
            }
        }
    }
}
