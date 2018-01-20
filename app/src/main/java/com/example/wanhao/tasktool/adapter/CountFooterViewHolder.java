package com.example.wanhao.tasktool.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.wanhao.tasktool.R;

/**
 * Created by wanhao on 2017/10/21.
 */

class CountFooterViewHolder extends RecyclerView.ViewHolder {
    private TextView textView;

    public CountFooterViewHolder(View view) {
        super(view);
        textView = (TextView) view.findViewById(R.id.word_item_total);
    }

    public void render(String s) {
        textView.setText(s);
    }
}
