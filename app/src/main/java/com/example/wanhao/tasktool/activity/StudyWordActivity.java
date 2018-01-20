package com.example.wanhao.tasktool.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.wanhao.tasktool.R;
import com.example.wanhao.tasktool.SQLite.UserWordDao;
import com.example.wanhao.tasktool.adapter.PagingScrollHelper;
import com.example.wanhao.tasktool.adapter.WordAdapter;
import com.example.wanhao.tasktool.bean.MyWord;

import java.util.List;

public class StudyWordActivity extends TopBarBaseActivity {
    private static final String TAG = "StudyWordActivity";

    private Button btRemember;
    private Button btBlur;
    private Button btFogect;

    private RecyclerView mRecyclerView;
    private StaggeredGridLayoutManager mLayoutManager;
    private WordAdapter adapter;
    PagingScrollHelper scrollHelper;

    private List<MyWord> list;
    private UserWordDao dao;
    private int wordPosition =0;
    private boolean model = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_study_word;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        Intent intent = new Intent();
        intent.putExtra("result",0);
        StudyWordActivity.this.setResult(0, intent);

        btBlur = (Button) findViewById(R.id.ac_studyword_blur);
        btFogect = (Button) findViewById(R.id.ac_studyword_fogect);
        btRemember = (Button) findViewById(R.id.ac_studyword_remember);

        mRecyclerView = (RecyclerView) findViewById(R.id.ac_studyword_recycler);
        //创建默认的线性LayoutManager
        mLayoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);

        scrollHelper = new PagingScrollHelper();
        scrollHelper.setUpRecycleView(mRecyclerView);

        initData();
        adapter = new WordAdapter(list);
        initEvent();

        mRecyclerView.setAdapter(adapter);
    }

    private void initData() {
        dao = new UserWordDao(this);
        list = dao.alterAllEnglishWod();
    }

    private void initEvent() {

        setTopLeftButton(new OnClickListener() {
            @Override
            public void onClick() {
                StudyWordActivity.this.finish();
            }
        });

        btBlur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(list.size()==0)
                    return;
                list.remove(wordPosition);
                adapter.notifyDataSetChanged();
            }
        });

        btFogect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(list.size()==0)
                    return;
                dao.updateWordLv(false,list.get(wordPosition));
                list.remove(wordPosition);
                adapter.notifyDataSetChanged();
            }
        });

        btRemember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(list.size()==0)
                    return;
                dao.updateWordLv(true,list.get(wordPosition));
                list.remove(wordPosition);
                adapter.notifyDataSetChanged();
            }
        });

//        mRecyclerView.setOnFlingListener(new RecyclerView.OnFlingListener() {
//            @Override
//            public boolean onFling(int velocityX, int velocityY) {
//                Log.i(TAG, "onScrollChange: velocityX is "+velocityX+"  velocityY is "+velocityY);
//                Log.i(TAG, "onFling: ");
//                btFogect.setEnabled(false);
//                btRemember.setEnabled(false);
//                btBlur.setEnabled(false);
//                return false;
//            }
//        });

        //设置页面滚动监听
        scrollHelper.setOnPageChangeListener(new PagingScrollHelper.onPageChangeListener() {
            @Override
            public void onPageChange(int index) {
                wordPosition =index;
                Log.i(TAG, "onPageChange: index is "+index);
            }
        });

        //mRecyclerView.smoothScrollToPosition(3);

//        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                if (!recyclerView.canScrollHorizontally(-1)) {        //canScrollVertically
//                    onScrolledToTop();
//                } else if (!recyclerView.canScrollHorizontally(1)) {
//                    onScrolledToBottom();
//                }
//            };
//        });

        adapter.setOnClickListener(new WordAdapter.onClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(StudyWordActivity.this,AddWordActivity.class);
                MyWord word = list.get(position);
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

    private void onScrolledToTop(){
        //Snackbar.make(mRecyclerView, "已经是第一个啦", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
        Log.i(TAG, "onScrolledToTop: ");
    }

    private void onScrolledToBottom(){
        Log.i(TAG, "onScrolledToBottom: ");
        Snackbar.make(mRecyclerView, "到底啦，没有啦", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
    }
}


