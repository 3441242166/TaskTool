package com.example.wanhao.tasktool.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.wanhao.tasktool.R;
import com.example.wanhao.tasktool.tool.MyDate;

/**
 * Created by wanhao on 2017/10/13.
 */

public class ReStartTaskDialog extends Dialog {

    private Button btDelete;//确定按钮
    private TextView txDateTime;//消息标题文本
    private TextView txContant;//消息提示文本
    private Spinner txEndDate;
    private Context context;

    private String stratDate;
    private String contant;
    private String endDate;
    private String newDate;

    private String[] ar = {"延迟一天","延迟三天","延迟一星期","延迟半个月","延迟一个月"};

    public String getStratDate() {
        return stratDate;
    }

    public void setStratDate(String stratDate) {
        this.stratDate = stratDate;
    }

    public String getContant() {
        return contant;
    }

    public void setContant(String contant) {
        this.contant = contant;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getNewDate() {
        return newDate;
    }

    public void setNewDate(String newDate) {
        this.newDate = newDate;
    }

    private OnClickListener onClickListener;//确定按钮被点击了的监听器

    public ReStartTaskDialog(@NonNull Context context, String stratDate, String contant,String endDate) {
        super(context);
        this.context = context;
        this.stratDate = stratDate;
        this.endDate = endDate;
        this.contant =contant;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_restart_task);
        //初始化界面控件
        initView();
        //初始化界面控件的事件
        initEvent();
    }

    private void initEvent() {
        //设置确定按钮被点击后，向外界提供监听
        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    onClickListener.onClick();
                }
            }
        });

        txEndDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        newDate = MyDate.getAddDayString(MyDate.getNowDateString(),1);
                        break;
                    case 1:
                        newDate = MyDate.getAddDayString(MyDate.getNowDateString(),3);
                        break;
                    case 2:
                        newDate = MyDate.getAddDayString(MyDate.getNowDateString(),7);
                        break;
                    case 3:
                        newDate = MyDate.getAddDayString(MyDate.getNowDateString(),15);
                        break;
                    case 4:
                        newDate = MyDate.getAddDayString(MyDate.getNowDateString(),30);
                        break;
                }
                Log.i("newDate",newDate);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initView() {

        btDelete = (Button) findViewById(R.id.dialog_restart_task_delete);
        txDateTime = (TextView) findViewById(R.id.dialog_restart_task_date);
        txContant = (TextView) findViewById(R.id.dialog_restart_task_contant);
        txEndDate = (Spinner) findViewById(R.id.dialog_restart_task_spinner);

        ArrayAdapter adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item,ar);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        txEndDate.setAdapter(adapter);
        txDateTime.setText(stratDate);
        txContant.setText(contant);


    }

    public interface OnClickListener {
        public void onClick();
    }
}
