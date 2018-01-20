package com.example.wanhao.tasktool.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.example.wanhao.tasktool.R;
import com.example.wanhao.tasktool.SQLite.EnglishWordDao;
import com.example.wanhao.tasktool.bean.EnglishWord;
import com.example.wanhao.tasktool.tool.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class SearchWordActivity extends AppCompatActivity {
    private static final String TAG = "SearchWordActivity";
    private FloatingSearchView searchView;

    private List<EnglishWord> wordList;
    private EnglishWordDao dao;
    InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_word);

        init();
        initEvent();

    }

    private void init() {
        searchView = (FloatingSearchView) findViewById(R.id.ac_search_word_search);

        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        wordList = new ArrayList<>();
        dao = new EnglishWordDao(this);
    }

    private void initEvent() {
        searchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {
                Log.i(TAG, "newQuery: "+ newQuery);
                Log.i(TAG, "oldQuery: "+ oldQuery);
                if(newQuery.isEmpty()){
                    wordList.clear();
                    searchView.swapSuggestions(wordList);
                    return;
                }
                wordList = getWordList(newQuery);
                searchView.swapSuggestions(wordList);
            }
        });

        searchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
                EnglishWord word = (EnglishWord)searchSuggestion;
                Intent intent = new Intent(SearchWordActivity.this,AddWordActivity.class);
                intent.putExtra("id",word.describeContents());
                startActivity(intent);
                if (imm != null) {
                    View v = new View(SearchWordActivity.this);
                    ViewGroup g1 = (ViewGroup)getWindow().getDecorView();
                    ViewGroup g2 = (ViewGroup)g1.getChildAt(0);
                    g2.addView(v);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                SearchWordActivity.this.finish();
            }

            @Override
            public void onSearchAction(String currentQuery) {
                Log.i(TAG, "onSearchAction:"+currentQuery);
            }
        });

        searchView.setOnFocusChangeListener(new FloatingSearchView.OnFocusChangeListener() {
            @Override
            public void onFocus() {
                Log.i(TAG, "onFocus: ");
            }

            @Override
            public void onFocusCleared() {
                Log.i(TAG, "onFocusCleared: ");
            }
        });
    }

    private List<EnglishWord> getWordList(String str){
        if(!StringUtil.isEnglish(str)){
            return dao.alterWordByChinese(str);
        }
        return dao.alterWord(str);
    }

}
