package com.example.wanhao.tasktool.adapter;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wanhao.tasktool.R;
import com.example.wanhao.tasktool.bean.Task;

import java.util.List;

/**
 * Created by wanhao on 2017/10/4.
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> implements View.OnClickListener {
    public String logString ="TaskAdapter";

    private List<Task> list;
    private boolean isHistory;
    private String btnText;

    private OnItemClickListener mOnItemClickListener = null;
    private OnFinishClickListener mOnFinishClickListener = null;
    View view;

    public TaskAdapter(List<Task> list) {
        this.list = list;
    }

    public TaskAdapter(List<Task> list,boolean isHistory) {
        this.list = list;
        this.isHistory = isHistory;
    }

    public void setData(List<Task> list){
        this.list =list;
    }

    public void setButtonText(boolean text){
        this.list.clear();
        isHistory =text;
    }

    public void setButtonText(String text){
        btnText = text;
    }

    //define interface
    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public static interface OnFinishClickListener{
        void OnFinishClick(View view,int position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_task, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        //将创建的View注册点击事件
        view.setOnClickListener(this);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        viewHolder.contant.setText(list.get(position).getContant());
        viewHolder.priority.setBackgroundResource(list.get(position).getPriorityColor());
        if(isHistory){
            viewHolder.finish.setText("删除");
            viewHolder.finish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar.make(viewHolder.finish, "长按删除任务", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            });
        }else {
            viewHolder.finish.setText("完成");
            viewHolder.finish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar.make(viewHolder.finish, "长按完成任务", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            });
        }
        viewHolder.finish.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnFinishClickListener != null) {
                    //注意这里使用getTag方法获取position
                    mOnFinishClickListener.OnFinishClick(v, position);
                }
                return true;
            }
        });

        viewHolder.itemView.setTag(position);
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public void setOnFinishClickListener(OnFinishClickListener listener) {
        this.mOnFinishClickListener = listener;
    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        return list.size();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView contant;
        public Button finish;
        public ImageView priority;

        public ViewHolder(View view) {
            super(view);
            priority =(ImageView) view.findViewById(R.id.ad_task_priority);
            contant = (TextView) view.findViewById(R.id.ad_task_contant);
            finish = (Button) view.findViewById(R.id.ad_task_finish);

        }
    }
}