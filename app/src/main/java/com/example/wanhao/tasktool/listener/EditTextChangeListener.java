package com.example.wanhao.tasktool.listener;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by wanhao on 2017/10/10.
 */

public class EditTextChangeListener implements TextWatcher {

    private OnTextChangeListener textChangeListener =null;
    /**
     * 编辑框的内容发生改变之前的回调方法
     */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }
    /**
     * 编辑框的内容正在发生改变时的回调方法 >>用户正在输入
     * 我们可以在这里实时地 通过搜索匹配用户的输入
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(textChangeListener!=null){
            textChangeListener.onTextChange();
        }
    }
    /**
     * 编辑框的内容改变以后,用户没有继续输入时 的回调方法
     */
    @Override
    public void afterTextChanged(Editable s) {

    }

    public void setTextChangeListener(OnTextChangeListener listener){
        this.textChangeListener = listener;
    }

    public static interface OnTextChangeListener {
        void onTextChange();
    }
}