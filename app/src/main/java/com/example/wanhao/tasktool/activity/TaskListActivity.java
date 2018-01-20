package com.example.wanhao.tasktool.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.wanhao.tasktool.R;
import com.example.wanhao.tasktool.SQLite.TaskDao;
import com.example.wanhao.tasktool.adapter.TaskAdapter;
import com.example.wanhao.tasktool.bean.Task;
import com.example.wanhao.tasktool.dialog.TaskMessageDialog;
import com.example.wanhao.tasktool.dialog.TaskScreenDialog;
import com.example.wanhao.tasktool.tool.Constant;
import com.example.wanhao.tasktool.tool.Sort;

import java.util.List;

/*
    finish 有bug    java.lang.IndexOutOfBoundsException: Invalid index 6, size is 6
 */

public class TaskListActivity extends TopBarBaseActivity {
    private static final String TAG = "TaskListActivity";

    private Button btnScreen;
    private Button btnHishtory;
    private FloatingActionButton floatingActionButton;
    private RecyclerView mRecyclerView;
    private StaggeredGridLayoutManager mLayoutManager;
    private TaskAdapter adapter;
    private List<Task> taskList;

    private TaskDao taskDao;
    private TaskMessageDialog taskMessageDialog;
    private TaskScreenDialog taskScreenDialog;
    //false 为 未完成界面  true 为 历史记录
    private boolean isUnfinish = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_task_list;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("未完成");
        taskDao =new TaskDao(TaskListActivity.this);
        taskList = taskDao.alterTaskOutDate(false);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.ac_task_actionbutton);
        btnScreen = (Button) findViewById(R.id.ac_task_screen) ;
        btnHishtory = (Button) findViewById(R.id.ac_task_history) ;
        mRecyclerView = (RecyclerView) findViewById(R.id.ac_task_recycle);
        //创建默认的线性LayoutManager
        mLayoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);

        adapter = new TaskAdapter(taskList);
        mRecyclerView.setAdapter(adapter);

        initEvent();
    }

    private void initEvent() {
        setTopLeftButton(new OnClickListener() {
            @Override
            public void onClick() {
                TaskListActivity.this.finish();
            }
        });
        setTopRightButton("过期任务", new OnClickListener() {
            @Override
            public void onClick() {
                Intent intent = new Intent(TaskListActivity.this,OutDateTaskActivity.class);
                startActivityForResult(intent,1);
            }
        });
        //---------------------------------------item buton 单击事件 ----------------------------------------------------
        adapter.setOnFinishClickListener(new TaskAdapter.OnFinishClickListener() {
            @Override
            public void OnFinishClick(View view, int position) {
                Constant.ISCHANGE = true;
                if(isUnfinish) {
                    Snackbar.make(view, "恭喜完成任务", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    taskDao.updateTaskFinish(taskList.get(position).getDateTime());
                }else{
                    Snackbar.make(view, "删除成功", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    taskDao.deleteTask(taskList.get(position).getDateTime());
                }
                taskList.remove(position);
                adapter.notifyDataSetChanged();
            }
        });
        //---------------------------------------item  任务详细信息 ----------------------------------------------------
        adapter.setOnItemClickListener(new TaskAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view,final int position) {

                taskMessageDialog = new TaskMessageDialog(TaskListActivity.this,taskList.get(position).getDateTime(),taskList.get(position).getContant(),taskList.get(position).getEndDate());
                taskMessageDialog.setOnClickListener(new TaskMessageDialog.OnClickListener() {
                    @Override
                    public void onClick() {
                        Constant.ISCHANGE = true;
                        taskDao.deleteTask(taskList.get(position).getDateTime());
                        taskList.remove(position);
                        adapter.notifyDataSetChanged();
                        taskMessageDialog.cancel();
                    }
                });
                taskMessageDialog.show();
            }
        });
        //---------------------------------------history 单击事件 ----------------------------------------------------
        btnHishtory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"btnHistory  "+btnHishtory.getText().toString());
                taskList.clear();
                adapter.notifyDataSetChanged();
                if(!isUnfinish){
                    Log.i(TAG,"to history");

                    taskList = taskDao.alterTaskOutDate(false);
                    getSupportActionBar().setTitle("未完成");
                    btnHishtory.setText("历史记录");
                    adapter.setButtonText(false);
                    adapter.setData(taskList);
                    isUnfinish = true;
                }else{
                    Log.i(TAG,"to unfinish");

                    taskList = taskDao.alterTaskDateFinish("y");
                    getSupportActionBar().setTitle("历史记录");
                    btnHishtory.setText("未完成");
                    adapter.setButtonText(true);
                    adapter.setData(taskList);
                    isUnfinish = false;
                }
            }
        });
        //--------------------------------------- 筛选 单击事件 ----------------------------------------------------
        btnScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskScreenDialog = new TaskScreenDialog(TaskListActivity.this);
                taskScreenDialog.setOnClickListener(new TaskScreenDialog.OnClickListener() {
                    @Override
                    public void onClick() {
                        taskScreenDialog.cancel();
                    }
                });
                taskScreenDialog.setOnGroupSelectListener(new TaskScreenDialog.OnGroupSelectListener() {
                    @Override
                    public void onSelect(int checkedId) {
                        switch (checkedId) {
                            case R.id.dialog_taskscreen_date:
                                taskList = Sort.taskByDate(taskList);
                                adapter.setData(taskList);
                                adapter.notifyDataSetChanged();
                                break;
                            case R.id.dialog_taskscreen_nothing:
                                if(isUnfinish){
                                    taskList = taskDao.alterTaskOutDate(false);
                                }else{
                                    taskList = taskDao.alterTaskDateFinish("y");
                                }
                                adapter.setData(taskList);
                                adapter.notifyDataSetChanged();
                                break;
                            case R.id.dialog_taskscreen_enddate:
                                taskList = Sort.taskByEndDate(taskList);
                                adapter.setData(taskList);
                                adapter.notifyDataSetChanged();
                                break;
                            case R.id.dialog_taskscreen_import:
                                taskList = Sort.taskByImport(taskList);
                                adapter.setData(taskList);
                                adapter.notifyDataSetChanged();
                                break;
                            default:
                                break;
                        }
                    }
                });
                taskScreenDialog.show();
            }
        });
        //悬浮按钮点击事件
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TaskListActivity.this,AddTaskActivity.class);
                startActivityForResult(intent,0);
            }
        });
    }
    //-------------返回更新actiity---------------------------
    // 回调方法，从第二个页面回来的时候会执行这个方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //如果添加数据后返回
        Log.i(TAG,"ActivityResult  "+data.getIntExtra("result",0));     //  没有result值 默认取 0
        if(data.getIntExtra("result",0) == 1){
            if(isUnfinish) {
                Log.i(TAG,"ActivityResult add ");
                //taskList.clear();
                //adapter.notifyDataSetChanged();
                taskList = taskDao.alterTaskOutDate(false);
                adapter.setData(taskList);
                adapter.notifyDataSetChanged();
            }
        }
    }
}
