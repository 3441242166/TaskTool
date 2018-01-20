package com.example.wanhao.tasktool.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.wanhao.tasktool.R;

/**
 * Created by wanhao on 2017/10/4.
 */

public class TaskMessageDialog extends Dialog {

    private Button btDelete;//确定按钮
    private TextView txDateTime;//消息标题文本
    private TextView txContant;//消息提示文本
    private TextView txFinishDate;

    private String endDate;
    private String time;
    private String contant;
    private View view;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContant() {
        return contant;
    }

    public void setContant(String contant) {
        this.contant = contant;
    }

    private OnClickListener onClickListener;//确定按钮被点击了的监听器

    public TaskMessageDialog(@NonNull Context context, String time, String contant,String endDate) {
        super(context);
        this.endDate = endDate;
        this.time = time;
        this.contant =contant;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_task);
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
    }

    private void initView() {
        btDelete = (Button) findViewById(R.id.dialog_task_delete);
        txDateTime = (TextView) findViewById(R.id.dialog_task_date);
        txContant = (TextView) findViewById(R.id.dialog_task_contant);
        txFinishDate = (TextView) findViewById(R.id.dialog_task_finishdate);

        txFinishDate.setText(endDate);
        txDateTime.setText(time);
        txContant.setText(contant);
    }

    public interface OnClickListener {
        public void onClick();
    }

}
