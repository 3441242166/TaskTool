package com.example.wanhao.tasktool.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.wanhao.tasktool.R;
import com.example.wanhao.tasktool.bean.MyWord;

import java.util.List;

/**
 * Created by wanhao on 2017/10/22.
 */

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.ViewHolder> {
    private static final String TAG = "WordAdapter";

    List<MyWord> list ;
    private onClickListener listener;

    View view;

    public WordAdapter(List<MyWord> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_word, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        viewHolder.word.setText(list.get(position).getWord());
        viewHolder.word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null)
                    listener.onClick(position);
            }
        });
        viewHolder.btShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(viewHolder.mean.getText())) {
                    viewHolder.btShow.setText("隐藏释义");
                    viewHolder.mean.setText(list.get(position).getMean());
                }else{
                    viewHolder.btShow.setText("显示释义");
                    viewHolder.mean.setText("");
                }
            }
        });
        viewHolder.itemView.setTag(position);
    }

    public void setOnClickListener(onClickListener listener){
        this.listener =listener;
    }

    public interface onClickListener{
        void onClick(int position);
    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        return list.size();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView word;
        public TextView mean;
        public Button btShow;

        public ViewHolder(View view) {
            super(view);
            word =(TextView) view.findViewById(R.id.ad_word_word);
            mean = (TextView) view.findViewById(R.id.ad_word_mean);
            btShow = (Button) view.findViewById(R.id.ad_word_show);

        }
    }
}