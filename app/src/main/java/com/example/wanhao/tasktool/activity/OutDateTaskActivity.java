package com.example.wanhao.tasktool.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.example.wanhao.tasktool.R;
import com.example.wanhao.tasktool.SQLite.TaskDao;
import com.example.wanhao.tasktool.adapter.TaskAdapter;
import com.example.wanhao.tasktool.bean.Task;
import com.example.wanhao.tasktool.dialog.ReStartTaskDialog;
import com.example.wanhao.tasktool.tool.Constant;

import java.util.List;

public class OutDateTaskActivity extends TopBarBaseActivity {

    private RecyclerView mRecyclerView;
    private StaggeredGridLayoutManager mLayoutManager;
    private TaskAdapter adapter;
    private List<Task> taskList;
    private TaskDao taskDao;
    private boolean isChange = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_out_date_task;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        taskDao =new TaskDao(OutDateTaskActivity.this);
        taskList = taskDao.alterTaskOutDate(true);

        mRecyclerView = (RecyclerView) findViewById(R.id.ac_out_date_task_recycle);
        //创建默认的线性LayoutManager
        mLayoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);

        adapter = new TaskAdapter(taskList,true);
        mRecyclerView.setAdapter(adapter);

        initEvent();
    }

    private void initEvent(){
        setTopLeftButton(new OnClickListener() {
            @Override
            public void onClick() {
                Intent intent = new Intent();
                if(isChange) {
                    intent.putExtra("result", 1);
                }
                setResult(1, intent);
                OutDateTaskActivity.this.finish();
            }
        });

        adapter.setOnFinishClickListener(new TaskAdapter.OnFinishClickListener() {
            @Override
            public void OnFinishClick(View view, int position) {
                taskDao.deleteTask(taskList.get(position).getDateTime());
                taskList.remove(position);
                adapter.notifyDataSetChanged();
            }
        });

        adapter.setOnItemClickListener(new TaskAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                Task temp = taskList.get(position);
                final ReStartTaskDialog dialog = new ReStartTaskDialog(OutDateTaskActivity.this,temp.getDateTime(),temp.getContant(),temp.getEndDate());
                dialog.setOnClickListener(new ReStartTaskDialog.OnClickListener() {
                    @Override
                    public void onClick() {
                        isChange = true;
                        Constant.ISCHANGE =true;
                        taskDao.updateTaskEndDate(taskList.get(position).getDateTime(),dialog.getNewDate());
                        taskList.remove(position);
                        adapter.notifyDataSetChanged();
                        dialog.cancel();
                    }
                });
                dialog.show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        if(isChange) {
            intent.putExtra("result", 1);
        }
        setResult(1, intent);
        super.onBackPressed();
    }
}
