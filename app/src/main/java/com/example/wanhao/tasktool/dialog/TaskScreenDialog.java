package com.example.wanhao.tasktool.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.wanhao.tasktool.R;

/**
 * Created by wanhao on 2017/10/5.
 */

public class TaskScreenDialog extends Dialog {

    private Button btDelete;//确定按钮

    private RadioGroup radioGroup;
    private RadioButton rbNothing;
    private RadioButton rbImport;
    private RadioButton rbDate;
    private RadioButton rbEndDate;
    private View view;

    private OnClickListener onClickListener;//确定按钮被点击了的监听器
    private OnGroupSelectListener onGroupSelectListener;

    public TaskScreenDialog(@NonNull Context context) {
        super(context);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
    public void setOnGroupSelectListener(OnGroupSelectListener onGroupSelectListener) {
        this.onGroupSelectListener = onGroupSelectListener;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_screen_task);
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

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (onGroupSelectListener != null) {
                    onGroupSelectListener.onSelect(checkedId);
                }
            }
        });
    }

    private void initView() {
        btDelete = (Button) findViewById(R.id.dialog_taskscreen_return);
        rbDate =(RadioButton) findViewById(R.id.dialog_taskscreen_date);
        rbNothing =(RadioButton) findViewById(R.id.dialog_taskscreen_nothing);
        rbEndDate =(RadioButton) findViewById(R.id.dialog_taskscreen_enddate);
        rbImport =(RadioButton) findViewById(R.id.dialog_taskscreen_import);
        radioGroup =(RadioGroup)findViewById(R.id.dialog_taskscreen_group);
    }

    public interface OnClickListener {
        public void onClick();
    }

    public interface OnGroupSelectListener{
        public void onSelect(int checkedId);
    }
}