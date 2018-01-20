package com.example.wanhao.tasktool.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wanhao.tasktool.R;
import com.example.wanhao.tasktool.SQLite.UserWordDao;
import com.example.wanhao.tasktool.adapter.WordSectionAdapter;
import com.example.wanhao.tasktool.bean.MyWord;
import com.example.wanhao.tasktool.tool.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class WordListActivity extends TopBarBaseActivity {
    private static final String TAG = "WordListActivity";

    private Button btStudy;
    private Button btHistory;
    private TextView txTotle;
    private RecyclerView recyclerView;
    private StaggeredGridLayoutManager mLayoutManager;
    private WordSectionAdapter adapter;

    private UserWordDao dao;
    private List<List<MyWord>> lists;
    private int totleWord =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initEvent();
    }

    private void initEvent() {
        setTopLeftButton(new OnClickListener() {
            @Override
            public void onClick() {
                WordListActivity.this.finish();
            }
        });

        adapter.setOnItemClickListener(new WordSectionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int section, int position) {
                startWordMessageActivity(lists.get(section).get(position));
            }
        });

        adapter.setOnItemLongClickListener(new WordSectionAdapter.OnItemLongClickListener() {
            @Override
            public void onLongItemClick(int section, int position) {
                showDialog(section,position);
            }
        });

        btStudy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WordListActivity.this,StudyWordActivity.class);
                startActivityForResult(intent,1);
            }
        });

        btHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WordListActivity.this,RandWordActivity.class);
                startActivityForResult(intent,2);
            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_word_list;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        initData();
        btHistory = (Button) findViewById(R.id.ac_word_card_history);
        btStudy = (Button) findViewById(R.id.ac_word_card_study);
        recyclerView =(RecyclerView) findViewById(R.id.ac_word_list_recycler);
        txTotle =(TextView) findViewById(R.id.ac_word_totle);
        txTotle.setText("一共有"+totleWord+"个单词");
        mLayoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        adapter = new WordSectionAdapter(this,lists);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);

    }
/*
    1.获得全部数据
    2.把数据放在对应的list中
    3.删除空list
 */
    private void initData(){
        dao = new UserWordDao(this);
        if(lists == null)
            lists = new ArrayList<>();
        else
            lists.clear();
        for(int x=0;x<26;x++){
            List<MyWord> list = new ArrayList<>();
            lists.add(list);
        }

        List<MyWord> list =dao.alterAllEnglishWod();
        for(int x=0;x<list.size();x++){
            //获取单词的首字母
            char fitst = StringUtil.getStringFirstChar(list.get(x).getWord());

            lists.get(getSetPosition(fitst)).add(list.get(x));
        }
        //删除空链表
        int temp =0;
        for(int x=0;x<26;x++){

            if(lists.get(x-temp).size()==0){
                lists.remove(x-temp);
                temp++;
            }else{
                totleWord+=lists.get(x-temp).size();
            }
        }

    }
    //得到首字母对应的positio
    private int getSetPosition(char s){
        for(int x=0;x<27;x++){
            if(s==(char)(65+x) || s ==(char) (97+x))
                return x;
        }
        return 0;
    }

    private void showDialog(final int section, final int position){
        final MyWord word =lists.get(section).get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(word.getWord());
        builder.setMessage("确定删除？");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });
        builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dao.deleteWord(word.getWord());
                lists.get(section).remove(position);
                //如果该section中没有数据  则移除该list
                if(lists.get(section).size()==0)
                    lists.remove(section);
                //更新textview
                txTotle.setText("一共有"+totleWord--+"个单词");
                adapter.notifyDataSetChanged();
                Toast.makeText(WordListActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNeutralButton("详情", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startWordMessageActivity(word);
            }
        });
        builder.show();
    }

    private void startWordMessageActivity(MyWord word){
        Intent intent = new Intent(this,AddWordActivity.class);
        intent.putExtra("word",word.getWord());
        intent.putExtra("mean",word.getMean());
        intent.putExtra("gq",word.getPast());
        intent.putExtra("gqfc",word.getPastTwo());
        intent.putExtra("ing",word.getIng());
        intent.putExtra("fs",word.getWordss());
        intent.putExtra("example",word.getExample());
        intent.putExtra("id",-1);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "onActivityResult: ");
        super.onActivityResult(requestCode, resultCode, data);
        initData();
        adapter.notifyDataSetChanged();
    }
}
