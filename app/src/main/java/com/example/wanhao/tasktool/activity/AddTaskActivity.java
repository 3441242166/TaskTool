package com.example.wanhao.tasktool.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.wanhao.tasktool.R;
import com.example.wanhao.tasktool.SQLite.TaskDao;
import com.example.wanhao.tasktool.tool.Constant;
import com.example.wanhao.tasktool.tool.MyDate;

import java.util.Calendar;

public class AddTaskActivity extends TopBarBaseActivity {
    public String logString = "AddTaskActivity";

    private EditText edContant;
    private Spinner spPriority;
    private Button btEndday;
    private TaskDao taskDao;

    private int startyear , startmonth , startday;
    private int select = 0;

    private String[] arPriority = {"十分重要","重要","一般","次要","不重要"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_add_task;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        edContant =(EditText)findViewById(R.id.ac_addtask_ed);
        spPriority =(Spinner)findViewById(R.id.ac_addtask_priority);
        btEndday =(Button) findViewById(R.id.ac_addtask_date);

        taskDao = new TaskDao(this);

        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,arPriority);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPriority.setAdapter(adapter);

        btEndday.setText(MyDate.getNowDateString());

        Calendar c = Calendar.getInstance();
        startyear = c.get(Calendar.YEAR);
        startmonth = c.get(Calendar.MONTH) ;
        startday = c.get(Calendar.DAY_OF_MONTH);

        initEvent();
    }

    protected void initEvent(){
        btEndday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddTaskActivity.this, new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
                    {
                        startyear = year;
                        startmonth = monthOfYear;
                        startday = dayOfMonth;
                        btEndday.setText(  year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                }, startyear, startmonth, startday);
                datePickerDialog.show();
            }
        });

        setTopLeftButton(new OnClickListener() {
            @Override
            public void onClick() {
                Intent intent = new Intent();
                intent.putExtra("result",0);
                AddTaskActivity.this.setResult(0, intent);
                AddTaskActivity.this.finish();
            }
        });

        setTopRightButton("完成", new OnClickListener() {
            @Override
            public void onClick() {
                if(isLegal()) {
                    addTask();
                    Intent intent = new Intent();
                    intent.putExtra("result",1);
                    AddTaskActivity.this.setResult(0, intent);
                    AddTaskActivity.this.finish();
                }
            }
        });

        spPriority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                select = position;
                Log.i(logString,"selecte   "+position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private boolean isLegal(){
        if(TextUtils.isEmpty(edContant.getText())){
            Toast.makeText(this,"内容不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }if(MyDate.compareStringByDate(MyDate.getNowDateString(),btEndday.getText().toString())==1){
            Toast.makeText(this,"截至日期不能小于当前日期",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void addTask(){
        Constant.ISCHANGE = true;
        long num = taskDao.addTask(MyDate.getNowDateTimeString(),edContant.getText().toString(),btEndday.getText().toString(),select);
        Log.i(logString,"addTask()   "+num);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("result",1);
        AddTaskActivity.this.setResult(0, intent);
        super.onBackPressed();
    }
}
