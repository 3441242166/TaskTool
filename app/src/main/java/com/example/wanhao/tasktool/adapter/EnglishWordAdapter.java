package com.example.wanhao.tasktool.adapter;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.wanhao.tasktool.R;
import com.example.wanhao.tasktool.bean.EnglishWord;

import java.util.List;

/**
 * Created by wanhao on 2017/10/25.
 */

public class EnglishWordAdapter extends RecyclerView.Adapter<EnglishWordAdapter.ViewHolder> {
    private static final String TAG = "WordAdapter";

    List<EnglishWord> list ;
    Handler handler;
    private onClickListener listener;

    View view;

    public EnglishWordAdapter(List<EnglishWord> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_word, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    public void setData( List<EnglishWord> list){

        this.list = list;
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
                    viewHolder.btShow.setEnabled(false);
                    viewHolder.mean.setText(list.get(position).getMean().replace("<br>",""));
                    new Thread(){
                        public void run(){
                            try {
                                sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Message msg = new Message();
                            msg.what=0;
                            handler.sendMessage(msg);
                        }
                    }.start();
                    handler = new Handler(){
                        @Override
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);
                            viewHolder.btShow.setEnabled(true);
                            viewHolder.mean.setText("");
                        }
                    };
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