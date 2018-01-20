package com.example.wanhao.tasktool.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
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
import com.example.wanhao.tasktool.SQLite.TimeTaskDao;
import com.example.wanhao.tasktool.adapter.TimeTaskAdapter;
import com.example.wanhao.tasktool.bean.TimeTask;
import com.example.wanhao.tasktool.broadcastreceiver.TimeTaskBroadcast;
import com.example.wanhao.tasktool.service.TimeTaskService;

import java.util.List;

import static com.example.wanhao.tasktool.tool.Constant.TIMETASK_ACTION;

public class TimeTaskActivity extends TopBarBaseActivity{
    private static final String TAG = "TimeTaskActivity";

    private Button btStart;
    private Button btReset;
    private TextView txTime;
    private TextView txTitle;

    private RecyclerView mRecyclerView;
    private StaggeredGridLayoutManager mLayoutManager;
    private TimeTaskAdapter adapter;
    private List<TimeTask> timeTasks;
    private TimeTaskDao dao;
    private int position;
    private int PHONE_CODE;

    private TimeTaskBroadcast mBroadcastReceiver;
    private IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_time_task;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        btReset = (Button) findViewById(R.id.ac_timetask_reset);
        btStart = (Button) findViewById(R.id.ac_timetask_start);
        txTime = (TextView) findViewById(R.id.ac_time_task_time);
        txTitle = (TextView) findViewById(R.id.ac_time_task_title);
        btReset.setEnabled(false);
        btStart.setEnabled(false);

        mRecyclerView = (RecyclerView) findViewById(R.id.ac_timetask_recycler);
        //创建默认的线性LayoutManager
        mLayoutManager = new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);

        initData();

        initEvent();

    }

    private void initData() {
        dao = new TimeTaskDao(this);
        timeTasks = dao.alterAllTimeTask();

        adapter = new TimeTaskAdapter(timeTasks);
        mRecyclerView.setAdapter(adapter);

        AudioManager audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        int mode = audioManager.getRingerMode();
        switch (mode){
            case AudioManager.RINGER_MODE_NORMAL:
                //普通模式
                PHONE_CODE =AudioManager.RINGER_MODE_NORMAL;
                break;
            case AudioManager.RINGER_MODE_VIBRATE:
                //振动模式
                PHONE_CODE =AudioManager.RINGER_MODE_VIBRATE;
                break;
            case AudioManager.RINGER_MODE_SILENT:
                //静音模式
                PHONE_CODE =AudioManager.RINGER_MODE_SILENT;
                break;
        }
    }

    private void initEvent() {

        setTopLeftButton(new OnClickListener() {
            @Override
            public void onClick() {
                TimeTaskActivity.this.finish();
            }
        });

        setTopRightButton("添加", new OnClickListener() {
            @Override
            public void onClick() {
                Intent intent = new Intent(TimeTaskActivity.this, AddTimeTaskActivity.class);
                startActivityForResult(intent,0);
            }
        });

        adapter.setOnItemClickListener(new TimeTaskAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                TimeTaskActivity.this.position =position;
                btReset.setEnabled(false);
                btStart.setEnabled(true);
                txTime.setText(timeTasks.get(position).getTime());
                txTitle.setText(timeTasks.get(position).getTitle());
            }
        });

        adapter.setOnLongItemClickListener(new TimeTaskAdapter.OnLongItemClickListener() {
            @Override
            public void onLongItemClick(View view, int position) {
                showMessageDialog(position);
            }
        });

        btReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TimeTaskActivity.this, TimeTaskService.class);
                btStart.setEnabled(true);
                stopService(intent);
            }
        });

        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PHONE_CODE == AudioManager.RINGER_MODE_SILENT){
                    Toast.makeText(TimeTaskActivity.this,"手机当前为静音模式",Toast.LENGTH_SHORT).show();
                }else if(PHONE_CODE == AudioManager.RINGER_MODE_VIBRATE){
                    Toast.makeText(TimeTaskActivity.this,"手机当前为震动模式",Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(TimeTaskActivity.this, TimeTaskService.class);
                intent.putExtra("title",timeTasks.get(position).getTitle());
                intent.putExtra("time",timeTasks.get(position).getTime());
                startService(intent);
                btStart.setEnabled(false);
            }
        });

    }
    //                   dialog
    private void showMessageDialog(final int position){

        final TimeTask timeTask =timeTasks.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(timeTask.getTitle());
        builder.setMessage("确定删除？");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int num = dao.deleteTimeTask(timeTask.getDateTime());
                Log.i(TAG, "onClick: num=" +num );
                timeTasks.remove(position);

                adapter.notifyDataSetChanged();
                Toast.makeText(TimeTaskActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
            }
        });

        builder.show();
    }
    //             初始化广播
    @Override
    protected void onResume() {
        super.onResume();
        //实例化BroadcastReceiver子类 &  IntentFilter
        mBroadcastReceiver = new TimeTaskBroadcast();
        intentFilter = new IntentFilter();

        //设置接收广播的类型
        intentFilter.addAction(TIMETASK_ACTION);

        //调用Context的registerReceiver（）方法进行动态注册
        registerReceiver(mBroadcastReceiver, intentFilter);

        mBroadcastReceiver.setDataChange(new TimeTaskBroadcast.onDataChange() {
            @Override
            public void dataChange(String msg,String title) {
                txTime.setText(msg);
                txTitle.setText(title);
                btStart.setEnabled(false);
                btReset.setEnabled(true);
            }
        });

    }
    //             结束广播
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mBroadcastReceiver);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "onActivityResult: ");
        super.onActivityResult(requestCode, resultCode, data);
        timeTasks = dao.alterAllTimeTask();
        adapter.setData(timeTasks);
        adapter.notifyDataSetChanged();
    }
}
