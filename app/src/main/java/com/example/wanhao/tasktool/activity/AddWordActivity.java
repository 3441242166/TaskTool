package com.example.wanhao.tasktool.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wanhao.tasktool.R;
import com.example.wanhao.tasktool.SQLite.EnglishWordDao;
import com.example.wanhao.tasktool.SQLite.UserWordDao;
import com.example.wanhao.tasktool.bean.EnglishWord;
/*
    toobar 显示单词
 */

public class AddWordActivity extends AppCompatActivity {
    private static final String TAG = "AddWordActivity";

    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private Button btReturn;
    private ImageView btSearch;
    private FloatingActionButton actionButton;

    private TextView txWord;
    private TextView txMean;
    private TextView txPast;
    private TextView txPastTwo;
    private TextView txIng;
    private TextView txWords;
    private TextView txExemple;


    private EnglishWord word;
    private UserWordDao dao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word_search);

        initView();
        initEvent();
    }

    private void initEvent() {
        btReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddWordActivity.this.finish();
            }
        });

        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddWordActivity.this,SearchWordActivity.class);
                startActivity(intent);
                AddWordActivity.this.finish();
            }
        });

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "actionButton  onClick: ");
                if(dao.alterAllEnglishWod(word.getWord())){
                    Toast.makeText(AddWordActivity.this, "请不要重复添加", Toast.LENGTH_SHORT).show();
                }else {
                    dao.addEnglishWord(word);
                    Toast.makeText(AddWordActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initView() {
        dao = new UserWordDao(this);

        actionButton =(FloatingActionButton) findViewById(R.id.ac_add_word_add_bt) ;
        btReturn = (Button) findViewById(R.id.ac_add_word_return);
        btSearch =(ImageView) findViewById(R.id.ac_add_word_search);
        toolbar = (Toolbar) findViewById(R.id.ac_add_word_toolbar);
        appBarLayout =(AppBarLayout) findViewById(R.id.ac_add_word_appbarlayout);
        txWord =(TextView) findViewById(R.id.ac_add_word_txword) ;
        txMean =(TextView) findViewById(R.id.ac_add_word_txmean);
        txPast =(TextView) findViewById(R.id.ac_add_word_txpast) ;
        txPastTwo =(TextView) findViewById(R.id.ac_add_word_txpasttwo);
        txIng =(TextView) findViewById(R.id.ac_add_word_txing) ;
        txWords =(TextView) findViewById(R.id.ac_add_word_txwords);
        txExemple =(TextView) findViewById(R.id.ac_add_word_txexemple) ;

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        showMessage();
    }


    private void showMessage(){
            Intent getIntent= getIntent();
            //如果是从主界面启动，则判断
            if(getIntent.getIntExtra("id",-1)!=-1){
                EnglishWordDao dao = new EnglishWordDao(this);
                word =dao.alterWord(getIntent.getIntExtra("id",-1));
                txWord.setText(word.getWord());
                txMean.setText(word.getMean().replace("<br>",""));

                txPast.setText(word.getPast());
                txPastTwo.setText(word.getPastTwo());
                txIng.setText(word.getIng());
                txWords.setText(word.getWordss());
                txExemple.setText(word.getExample().replace("/r","\r").replace("/n","\n"));
            }
            if(TextUtils.isEmpty(getIntent.getStringExtra("word")))
                return;
            txWord.setText(getIntent.getStringExtra("word"));
            txMean.setText(getIntent.getStringExtra("mean").replace("<br>",""));

            txPast.setText(getIntent.getStringExtra("gq"));
            txPastTwo.setText(getIntent.getStringExtra("gqfc"));
            txIng.setText(getIntent.getStringExtra("ing"));
            txWords.setText(getIntent.getStringExtra("fs"));
            txExemple.setText(getIntent.getStringExtra("example").replace("/r","\r").replace("/n","\n"));

            word = new EnglishWord();
            word.setWord(getIntent.getStringExtra("word"));
        }

}
