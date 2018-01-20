package com.example.wanhao.tasktool.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.wanhao.tasktool.listener.EditTextChangeListener;
import com.example.wanhao.tasktool.R;

/**
 * Created by wanhao on 2017/10/10.
 */

public class PasswordDialog extends Dialog {
    private static String ar[] = {"1","2","3","4","5","6","7","8","9","enter","0","back"};
    private String title;
    private EditTextChangeListener editTextChangeListener =null;
    private OnCancelListener onCancelListener =null;
    private int[] nums = new int[4];
    private boolean isOk ;

    private EditText et_1;
    private EditText et_2;
    private EditText et_3;
    private EditText et_4;
    private Button btEnter;
    private Button btReturn;
    private TextView txTitle;
    private View view;

    private OnClickListener onClickListener;//确定按钮被点击了的监听器

    public PasswordDialog(@NonNull Context context,String title) {
        super(context);
        this.title = title;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setOnCancelListener(OnCancelListener onCancelListener) {
        this.onCancelListener = onCancelListener;
    }

    @Override
    public void cancel() {
        if(onCancelListener!=null){
            onCancelListener.onCancel();
        }
        super.cancel();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_password);
        //初始化界面控件
        initView();
        //初始化界面控件的事件
        initEvent();
    }

    private void initEvent() {
        //设置确定按钮被点击后，向外界提供监听
        btEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null && !TextUtils.isEmpty(et_1.getText())
                        && !TextUtils.isEmpty(et_2.getText()) &&!TextUtils.isEmpty(et_3.getText()) &&!TextUtils.isEmpty(et_4.getText())) {
                    nums[0] = Integer.parseInt(et_1.getText().toString());
                    nums[1] = Integer.parseInt(et_2.getText().toString());
                    nums[2] = Integer.parseInt(et_3.getText().toString());
                    nums[3] = Integer.parseInt(et_4.getText().toString());
                    isOk = true;
                    onClickListener.onClick();
                }
            }
        });

        btReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PasswordDialog.this.cancel();
            }
        });

        editTextChangeListener.setTextChangeListener(new EditTextChangeListener.OnTextChangeListener() {
            @Override
            public void onTextChange() {
                if(et_3.hasFocus()){
                    et_4.requestFocus();
                }
                if(et_2.hasFocus()){
                    et_3.requestFocus();
                }
                if(et_1.hasFocus()){
                    et_2.requestFocus();
                }

            }
        });
        et_1.addTextChangedListener(editTextChangeListener);
        et_2.addTextChangedListener(editTextChangeListener);
        et_3.addTextChangedListener(editTextChangeListener);
    }

    private void initView() {
        isOk = false;
        editTextChangeListener = new EditTextChangeListener();

        et_1 = (EditText) findViewById(R.id.dialog_password_tx_1);
        et_2 = (EditText) findViewById(R.id.dialog_password_tx_2);
        et_3 = (EditText) findViewById(R.id.dialog_password_tx_3);
        et_4 = (EditText) findViewById(R.id.dialog_password_tx_4);
        btEnter = (Button)findViewById(R.id.dialog_password_enter);
        btReturn = (Button)findViewById(R.id.dialog_password_return);
        txTitle =(TextView)findViewById(R.id.dialog_password_title);

        txTitle.setText(title);
    }

    public interface OnClickListener {
        public void onClick();
    }

    public interface OnCancelListener {
        public void onCancel();
    }

    public boolean isOk() {
        return isOk;
    }

    public boolean isAlike(int ar[]){
        for(int x=0;x<4;x++){
            if(ar[x]!=nums[x])
                return false;
        }
        return true;
    }

    public int[] getNums() {
        return nums;
    }

}
