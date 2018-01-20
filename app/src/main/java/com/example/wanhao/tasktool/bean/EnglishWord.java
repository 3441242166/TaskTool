package com.example.wanhao.tasktool.bean;

import android.os.Parcel;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

/**
 * Created by wanhao on 2017/10/17.
 */

public class EnglishWord implements SearchSuggestion{
    private String word;
    private String past;
    private String pastTwo;
    private String ing;
    private String wordss;
    private String mean;
    private String example;

    private int id;

    public EnglishWord(){
    }

    public EnglishWord(String word,String mean,String example){
        this.word = word;
        this.mean =mean;
        this.example = example;
    }

    public EnglishWord(String word,String past,String pastTwo,String ing,String wordss,String mean,String example){
        this.word = word;
        this.mean =mean;
        this.example = example;
        this.past = past;
        this.pastTwo = pastTwo;
        this.ing = ing;
        this.wordss = wordss;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWordss() {
        return wordss;
    }

    public void setWordss(String wordss) {
        this.wordss = wordss;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMean() {
        return mean.replace("<br>","");
    }

    public void setMean(String mean) {
        this.mean = mean;
    }

    public String getPast() {
        return past;
    }

    public void setPast(String past) {
        this.past = past;
    }

    public String getIng() {
        return ing;
    }

    public void setIng(String ing) {
        this.ing = ing;
    }

    public String getPastTwo() {
        return pastTwo;
    }

    public void setPastTwo(String pastTwo) {
        this.pastTwo = pastTwo;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    @Override
    public String getBody() {
        return word+"\n"+mean.replace("<br>","");
    }

    @Override
    public int describeContents() {
        return id;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }


}
