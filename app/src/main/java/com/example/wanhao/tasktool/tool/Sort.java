package com.example.wanhao.tasktool.tool;

import android.util.Log;

import com.example.wanhao.tasktool.bean.Task;

import java.util.List;

/**
 * Created by wanhao on 2017/10/8.
 */

public class Sort {
    public static List<Task> taskByImport(List<Task> list){
        for(int x=0;x<list.size();x++){
            Log.i("list","1111"+list.get(x).getPriority());
        }

        Task temp = new Task();
        for(int i=list.size()-1;i>0;i--){
            for(int j =0;j<i;j++){
                 if(list.get(j+1).getPriority()<list.get(j).getPriority()){
                     temp.copy(list.get(j));
                     list.get(j).copy(list.get(j+1));
                     list.get(j+1).copy(temp);
                 }
            }
        }

        for(int x=0;x<list.size();x++){
            Log.i("list","2222"+list.get(x).getPriority());
        }

        return list;
    }

    public static List<Task> taskByDate(List<Task> list){
        Task temp = new Task();
        for(int i=list.size()-1;i>0;i--){
            for(int j =0;j<i;j++){
                if(MyDate.compareStringByDate(list.get(j+1).getDate(),list.get(j).getDate()) == 1){
                    temp.copy(list.get(j));
                    list.get(j).copy(list.get(j+1));
                    list.get(j+1).copy(temp);
                }
            }
        }
        return list;
    }

    public static List<Task> taskByEndDate(List<Task> list){

        Task temp = new Task();
        for(int i=list.size()-1;i>0;i--){
            for(int j =0;j<i;j++){
                if(MyDate.compareStringByDate(list.get(j+1).getEndDate(),list.get(j).getEndDate()) == -1){
                    temp.copy(list.get(j));
                    list.get(j).copy(list.get(j+1));
                    list.get(j+1).copy(temp);
                }
            }
        }
        return list;
    }


}
