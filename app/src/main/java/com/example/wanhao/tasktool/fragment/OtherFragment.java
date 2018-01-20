package com.example.wanhao.tasktool.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.wanhao.tasktool.R;
import com.example.wanhao.tasktool.activity.AddTaskActivity;
import com.example.wanhao.tasktool.activity.SearchWordActivity;
import com.example.wanhao.tasktool.activity.TimeTaskActivity;


/**
 * Created by wanhao on 2017/10/3.
 */

public class OtherFragment extends Fragment {
    private View view;
    private Button addWord;
    private Button addTask;
    private Button addTip;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_other, container, false);
        initView();
        initEvent();
        return view;
    }

    private void initEvent() {
        addWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchWordActivity.class);
                startActivity(intent);
            }
        });
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddTaskActivity.class);
                startActivity(intent);
            }
        });
        addTip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TimeTaskActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        addWord = (Button) view.findViewById(R.id.fg_addword);
        addTask = (Button) view.findViewById(R.id.fg_addtask);
        addTip = (Button) view.findViewById(R.id.fg_addtip);
    }
}
