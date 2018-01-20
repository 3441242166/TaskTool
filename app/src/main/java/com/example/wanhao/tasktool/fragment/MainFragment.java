package com.example.wanhao.tasktool.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.wanhao.tasktool.R;
import com.example.wanhao.tasktool.SQLite.TaskDao;
import com.example.wanhao.tasktool.activity.AddTaskActivity;
import com.example.wanhao.tasktool.activity.SearchWordActivity;
import com.example.wanhao.tasktool.activity.TaskListActivity;
import com.example.wanhao.tasktool.activity.WordListActivity;
import com.example.wanhao.tasktool.bean.Task;
import com.example.wanhao.tasktool.tool.Constant;
import com.example.wanhao.tasktool.tool.MyDate;

import java.util.List;


/**
 * Created by wanhao on 2017/10/3.
 */

public class MainFragment extends Fragment {

    private View view;

    private Button btNewTask;
    private Button btAddWord;

    private CardView taskCard;
    private CardView wordCard;
    private TextView newTask;
    private TextView unfinishTask;
    private TextView finishTask;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);

        initView();
        initEvent();

        return view;
    }

    private void initData() {
        Task task;
        String nowDate = MyDate.getNowDateString();
        int sumNewTask,sumUnfinishTask,sumFinish;
        sumNewTask = sumUnfinishTask = sumFinish = 0;
        TaskDao taskDao= new TaskDao(getActivity());
        List<Task> taskList = taskDao.alterTaskDateFinish("all");

        for(int x=0;x<taskList.size();x++){
            task = taskList.get(x);

            if(!task.isFinish() && MyDate.compareStringByDate(task.getEndDate(),MyDate.getNowDateString())!= -1)
                sumUnfinishTask++;

            if(task.getDate().equals(nowDate))
                sumNewTask++;

            if(task.getFinishDate().equals(nowDate))
                sumFinish++;
        }
        newTask.setText(String.valueOf(sumNewTask));
        unfinishTask.setText(String.valueOf(sumUnfinishTask));
        finishTask.setText(String.valueOf(sumFinish));

    }

    private void initEvent() {
        taskCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TaskListActivity.class);
                startActivity(intent);
            }
        });

        wordCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WordListActivity.class);
                startActivity(intent);
            }
        });

        btAddWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchWordActivity.class);
                startActivity(intent);
            }
        });

        btNewTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddTaskActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        btAddWord = (Button) view.findViewById(R.id.fg_task_newword);
        btNewTask = (Button) view.findViewById(R.id.fg_task_newtask);
        taskCard = (CardView) view.findViewById(R.id.fg_task_card);
        wordCard =(CardView) view.findViewById(R.id.fg_word_card);
        newTask = (TextView) view.findViewById(R.id.today_new_task);
        unfinishTask = (TextView) view.findViewById(R.id.today_unfinish_task);
        finishTask = (TextView) view.findViewById(R.id.today_finish_task);
    }

    @Override
    public void onResume() {
        if(Constant.ISCHANGE) {
            Constant.ISCHANGE =false;
            initData();
        }
        super.onResume();
    }
}
