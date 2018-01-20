package com.example.wanhao.tasktool.bean;

import com.example.wanhao.tasktool.R;

/**
 * Created by wanhao on 2017/10/4.
 */

public class Task {
    private String dateTime;    //创建时间
    private String date;    //
    private String time;    //
    private String endDate;    //截止日期
    private String contant;    //任务内容
    private String finishDate;    //完成日期
    private int priority;    //优先级
    private boolean isFinish;    //是否完成

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContant() {
        return contant;
    }

    public void setContant(String contant) {
        this.contant = contant;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isFinish() {
        return isFinish;
    }

    public void setFinish(boolean finish) {
        isFinish = finish;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getPriorityColor(){
        switch (priority){
            case 0:
                return R.color.lv_0;
            case 1:
                return R.color.lv_1;
            case 2:
                return R.color.lv_2;
            case 3:
                return R.color.lv_3;
            case 4:
                return R.color.lv_4;
        }
        return R.color.lv_4;
    }

    public void copy(Task task){
        this.dateTime = task.getDateTime();
        this.date = task.getDate();
        this.time= task.getTime();
        this.endDate= task.getEndDate();
        this.contant= task.getContant();
        this.finishDate= task.getFinishDate();
        this.priority= task.getPriority();
        this.isFinish= task.isFinish();
    }
}
