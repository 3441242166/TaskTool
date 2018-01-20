package com.example.wanhao.tasktool.activity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.wanhao.tasktool.R;

public class MemoListActivity extends TopBarBaseActivity {
    @Override
    protected int getContentView() {
        return R.layout.activity_memo_list;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTopLeftButton(new OnClickListener() {
            @Override
            public void onClick() {
                MemoListActivity.this.finish();
            }
        });
        setTopRightButton("添加", new OnClickListener() {
            @Override
            public void onClick() {
                Toast.makeText(MemoListActivity.this,"add",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
