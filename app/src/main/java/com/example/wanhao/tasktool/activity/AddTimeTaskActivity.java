package com.example.wanhao.tasktool.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.wanhao.tasktool.R;
import com.example.wanhao.tasktool.SQLite.TimeTaskDao;
import com.example.wanhao.tasktool.bean.TimeTask;
import com.example.wanhao.tasktool.dialog.ChooseIconDialog;
import com.example.wanhao.tasktool.listener.EditTextChangeListener;

import java.util.ArrayList;
import java.util.List;

public class AddTimeTaskActivity extends AppCompatActivity {
    private static final String TAG = "AddTimeTaskActivity";

    private EditText ed_1;
    private EditText ed_2;
    private EditText ed_3;
    private EditText ed_4;
    private EditText ed_5;
    private EditText ed_6;
    private List<EditText> editTextList;
    private TimeTaskDao dao;
    private int position;
    private int chooseImageID;

    private EditTextChangeListener editTextChangeListener =null;

    private Button btExit;
    private Button btEnter;
    private EditText txTitle;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_time_task);

        initView();
        initEvent();
    }

    private void initView() {
        btEnter = (Button) findViewById(R.id.ac_add_time_task_finish);
        btExit = (Button) findViewById(R.id.ac_add_time_task_return);
        txTitle = (EditText) findViewById(R.id.ac_add_time_task_title);
        imageView = (ImageView) findViewById(R.id.ac_add_time_task_image);

        ed_1 =(EditText) findViewById(R.id.ac_time_task_ed_1);
        ed_2 =(EditText) findViewById(R.id.ac_time_task_ed_2);
        ed_3 =(EditText) findViewById(R.id.ac_time_task_ed_3);
        ed_4 =(EditText) findViewById(R.id.ac_time_task_ed_4);
        ed_5 =(EditText) findViewById(R.id.ac_time_task_ed_5);
        ed_6 =(EditText) findViewById(R.id.ac_time_task_ed_6);

        editTextList = new ArrayList<>();

        editTextList.add(ed_1);
        editTextList.add(ed_2);
        editTextList.add(ed_3);
        editTextList.add(ed_4);
        editTextList.add(ed_5);
        editTextList.add(ed_6);

        dao = new TimeTaskDao(this);
        chooseImageID = R.drawable.timetask_1;
    }

    private void initEvent() {
        btExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("result",1);
                AddTimeTaskActivity.this.setResult(0, intent);
                AddTimeTaskActivity.this.finish();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ChooseIconDialog dialog = new ChooseIconDialog(AddTimeTaskActivity.this);
                dialog.show();
                dialog.setOnClickListener(new ChooseIconDialog.OnClickListener() {
                    @Override
                    public void onClick() {
                        dialog.cancel();
                        chooseImageID = dialog.getChooseImg();
                        imageView.setImageResource(chooseImageID);
                    }
                });

            }
        });
        editTextChangeListener = new EditTextChangeListener();
        editTextChangeListener.setTextChangeListener(new EditTextChangeListener.OnTextChangeListener() {
            @Override
            public void onTextChange() {

                if(ed_1.hasFocus()){
                    ed_2.requestFocus();
                }

                if(ed_3.hasFocus()){
                    ed_4.requestFocus();
                }

                if(ed_5.hasFocus()){
                    ed_6.requestFocus();
                }
            }
        });

        for(position=0;position<6;position++){
            EditText temp = editTextList.get(position);
            editTextList.get(position).addTextChangedListener(editTextChangeListener);
        }

        btEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddTimeTask();
                Intent intent = new Intent();
                intent.putExtra("result",1);
                AddTimeTaskActivity.this.setResult(0, intent);
                AddTimeTaskActivity.this.finish();
            }
        });

    }

    private void AddTimeTask(){
//        if(Integer.getInteger(ed_5.getText().toString()+ed_6.getText().toString())>60) {
        if(TextUtils.isEmpty(txTitle.getText().toString())){
            Toast.makeText(this,"请输入标题",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(ed_1.getText())&&TextUtils.isEmpty(ed_2.getText())&&TextUtils.isEmpty(ed_3.getText())
                &&TextUtils.isEmpty(ed_4.getText())&&TextUtils.isEmpty(ed_5.getText())&&TextUtils.isEmpty(ed_6.getText())){
            Toast.makeText(this,"请输入有效内容",Toast.LENGTH_SHORT).show();
            return;
        }

        if(!TextUtils.isEmpty(ed_3.getText())&&(Integer.valueOf(ed_3.getText().toString())).intValue()>6){
            Toast.makeText(this,"错误的分钟",Toast.LENGTH_SHORT).show();
            return;
        }
        if(!TextUtils.isEmpty(ed_5.getText())&&(Integer.valueOf(ed_5.getText().toString())).intValue()>6){
            Toast.makeText(this,"错误的秒钟",Toast.LENGTH_SHORT).show();
            return;
        }
        //---------------拼接字符串----------------
        String time ="";
        int temp =0;
        for(int i=0;i<editTextList.size();i++,temp++){
            if(temp%2==0&&temp>0){
                time +=":";
            }
            if(TextUtils.isEmpty(editTextList.get(i).getText())){
                time +="0";
            }else{
                time +=editTextList.get(i).getText().toString();
            }
        }
        Log.i(TAG, "AddTimeTask: time =" + time);
        //-----------------------------------------
        TimeTask task = new TimeTask();
        task.setTime(time);
        task.setTitle(txTitle.getText().toString());
        task.setImageID(chooseImageID);
        dao.addTimeTask(task);
        Toast.makeText(this,"添加成功",Toast.LENGTH_SHORT).show();

    }
}
