package com.example.wanhao.tasktool.tool;

import android.util.Log;

/**
 * Created by wanhao on 2017/10/10.
 */

public class StringUtil {
    public static String intArToSring(int arr[]){
        // 自定义一个字符缓冲区，
        StringBuilder sb = new StringBuilder();
        //遍历int数组，并将int数组中的元素转换成字符串储存到字符缓冲区中去
        for(int i=0;i<arr.length;i++) {
            sb.append(arr[i]);
        }
        Log.i("6666",sb.toString());
        return sb.toString();
    }

    public static int[] stringToIntAr(String str){
        int ret[] = new int[str.length()];

        for(int x=0;x<str.length();x++){
            ret[x] = Integer.parseInt(String.valueOf(str.charAt(x)));
        }

        return ret;
    }

    public static char getStringFirstChar(String str){
        return Character.toUpperCase(str.charAt(0));
    }

    public static int getSetPosition(char s){
        for(int x=0;x<27;x++){
            if(s==(char)(65+x) || s ==(char) (97+x))
                return x;
        }
        return -1;
    }

    public static boolean isEnglish(String charaString){

        return charaString.matches("^[a-zA-Z]*");

    }
}
