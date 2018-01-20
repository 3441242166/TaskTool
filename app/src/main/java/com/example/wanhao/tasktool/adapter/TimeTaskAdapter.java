package com.example.wanhao.tasktool.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wanhao.tasktool.R;
import com.example.wanhao.tasktool.bean.TimeTask;

import java.util.List;

/**
 * Created by wanhao on 2017/10/26.
 */

public class TimeTaskAdapter extends RecyclerView.Adapter<TimeTaskAdapter.ViewHolder>{
    private static final String TAG = "WordAdapter";

    List<TimeTask> list ;
    OnItemClickListener onItemClickListener =null;
    OnLongItemClickListener onLongItemClickListener =null;

    View view;

    public TimeTaskAdapter(List<TimeTask> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_time_task, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    public void setData(List<TimeTask> list){
        this.list = list;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        viewHolder.time.setText(list.get(position).getTime());
        viewHolder.title.setText(list.get(position).getTitle());
        viewHolder.imageView.setImageResource(list.get(position).getImageID());
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener!=null){
                    onItemClickListener.onItemClick(v,position);
                }
            }
        });

        viewHolder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(onLongItemClickListener!=null){
                    onLongItemClickListener.onLongItemClick(v,position);
                }
                return false;
            }
        });

        viewHolder.itemView.setTag(position);
    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener = listener;
    }

    public void setOnLongItemClickListener(OnLongItemClickListener listener){
        this.onLongItemClickListener = listener;
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView time;
        public TextView title;
        public ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            time =(TextView) view.findViewById(R.id.ad_time_task_time);
            title = (TextView) view.findViewById(R.id.ad_time_task_title);
            imageView = (ImageView) view.findViewById(R.id.ad_time_task_img);

        }
    }

    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public static interface OnLongItemClickListener {
        void onLongItemClick(View view, int position);
    }
}

