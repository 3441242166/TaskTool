package com.example.wanhao.tasktool.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.wanhao.tasktool.R;
import com.example.wanhao.tasktool.SQLite.EnglishWordDao;
import com.example.wanhao.tasktool.SQLite.UserWordDao;
import com.example.wanhao.tasktool.adapter.EnglishWordAdapter;
import com.example.wanhao.tasktool.adapter.PagingScrollHelper;
import com.example.wanhao.tasktool.bean.EnglishWord;

import java.util.List;

public class RandWordActivity extends TopBarBaseActivity {
    private static final String TAG = "RandWordActivity";

    private Button btAdd;
    private Button btChange;

    private RecyclerView mRecyclerView;
    private StaggeredGridLayoutManager mLayoutManager;
    private EnglishWordAdapter adapter;
    PagingScrollHelper scrollHelper;

    private List<EnglishWord> list;
    private EnglishWordDao dao;
    private UserWordDao userWordDao;
    private int wordPosition =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_rand_word;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        Intent intent = new Intent();
        intent.putExtra("result",0);
        RandWordActivity.this.setResult(0, intent);

        btAdd = (Button) findViewById(R.id.ac_rand_studyword_add);
        btChange = (Button) findViewById(R.id.ac_rand_studyword_next);

        mRecyclerView = (RecyclerView) findViewById(R.id.ac_rand_studyword_recycler);
        //创建默认的线性LayoutManager
        mLayoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);

        scrollHelper = new PagingScrollHelper();
        scrollHelper.setUpRecycleView(mRecyclerView);

        dao = new EnglishWordDao(this);
        userWordDao = new UserWordDao(this);
        list = dao.getRandomWords(10);
        adapter = new EnglishWordAdapter(list);

        initEvent();

        mRecyclerView.setAdapter(adapter);
    }

    private void initEvent() {
        setTopLeftButton(new OnClickListener() {
            @Override
            public void onClick() {
                RandWordActivity.this.finish();
            }
        });

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!userWordDao.alterAllEnglishWod(list.get(wordPosition).getWord())){
                    userWordDao.addEnglishWord(list.get(wordPosition));
                    Toast.makeText(RandWordActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(RandWordActivity.this,"已添加过了",Toast.LENGTH_SHORT).show();
            }
        });

        btChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                list = dao.getRandomWords(10);
                adapter.setData(list);
                adapter.notifyDataSetChanged();
            }
        });

        //设置页面滚动监听
        scrollHelper.setOnPageChangeListener(new PagingScrollHelper.onPageChangeListener() {
            @Override
            public void onPageChange(int index) {
                wordPosition =index;
                Log.i(TAG, "onPageChange: index is "+index);
            }
        });

        adapter.setOnClickListener(new EnglishWordAdapter.onClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(RandWordActivity.this,AddWordActivity.class);
                EnglishWord word = list.get(position);
                intent.putExtra("word",word.getWord());
                intent.putExtra("mean",word.getMean());
                intent.putExtra("gq",word.getPast());
                intent.putExtra("gqfc",word.getPastTwo());
                intent.putExtra("ing",word.getIng());
                intent.putExtra("fs",word.getWordss());
                intent.putExtra("example",word.getExample());

                startActivity(intent);
            }
        });
    }
}
