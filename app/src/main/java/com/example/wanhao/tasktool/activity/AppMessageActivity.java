package com.example.wanhao.tasktool.activity;

import android.os.Bundle;

import com.example.wanhao.tasktool.R;

public class AppMessageActivity extends TopBarBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_app_message;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTopLeftButton(new OnClickListener() {
            @Override
            public void onClick() {
                AppMessageActivity.this.finish();
            }
        });
    }
}
