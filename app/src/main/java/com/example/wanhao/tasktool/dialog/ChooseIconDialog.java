package com.example.wanhao.tasktool.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.wanhao.tasktool.R;
import com.example.wanhao.tasktool.adapter.IconAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanhao on 2017/10/27.
 */

public class ChooseIconDialog extends Dialog {

    private GridView gridView;
    private IconAdapter adapter;
    private List<Integer> list;
    private Context context;
    private int chooseImg = 0;

    private OnClickListener onClickListener;//确定按钮被点击了的监听器

    public ChooseIconDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_choose_icon);
        //初始化界面控件
        initView();
        //初始化界面控件的事件
        initEvent();
    }

    private void initEvent() {
        //设置确定按钮被点击后，向外界提供监听
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                chooseImg = list.get(position);
                if(onClickListener!=null)
                    onClickListener.onClick();
            }
        });

    }

    private void initView() {
        initData();

        gridView = (GridView) findViewById(R.id.dialog_choose_icon_gridview);

        adapter = new IconAdapter(context,list);

        gridView.setAdapter(adapter);
    }

    public interface OnClickListener {
        public void onClick();
    }

    private void initData(){
        list = new ArrayList<>();
        list.add(R.drawable.timetask_1);
        list.add(R.drawable.timetask_2);
        list.add(R.drawable.timetask_3);
        list.add(R.drawable.timetask_4);
        list.add(R.drawable.timetask_5);
        list.add(R.drawable.timetask_6);
        list.add(R.drawable.timetask_7);
        list.add(R.drawable.timetask_8);
        list.add(R.drawable.timetask_9);

    }

    public int getChooseImg(){
        return chooseImg;
    }
}
