package com.example.wanhao.tasktool.bean;

import com.example.wanhao.tasktool.R;

/**
 * Created by wanhao on 2017/10/21.
 */

public class MyWord {
    private String word;
    private String past;
    private String pastTwo;
    private String ing;
    private String wordss;
    private String mean;
    private String example;
    private int lv;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getPast() {
        return past;
    }

    public void setPast(String past) {
        this.past = past;
    }

    public String getPastTwo() {
        return pastTwo;
    }

    public void setPastTwo(String pastTwo) {
        this.pastTwo = pastTwo;
    }

    public String getIng() {
        return ing;
    }

    public void setIng(String ing) {
        this.ing = ing;
    }

    public String getWordss() {
        return wordss;
    }

    public void setWordss(String wordss) {
        this.wordss = wordss;
    }

    public String getMean() {
        return mean.replace("<br>","");
    }

    public void setMean(String mean) {
        this.mean = mean;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public int getLv() {
        return lv;
    }

    public void setLv(int lv) {
        this.lv = lv;
    }

    public int getColor(){
        switch (lv){
            case 0:
                return R.color.word_lv_0;
            case 1:
                return R.color.word_lv_1;
            case 2:
                return R.color.word_lv_2;
            case 3:
                return R.color.word_lv_3;
            case 4:
                return R.color.word_lv_4;
        }
        return R.color.word_lv_0;
    }
}
