package com.example.wanhao.tasktool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.wanhao.tasktool.R;

import java.util.List;

/**
 * Created by wanhao on 2017/10/27.
 */

public class IconAdapter extends BaseAdapter {

    List<Integer> list;
    LayoutInflater inflater;

    public IconAdapter(Context context,List<Integer> list){
        inflater = LayoutInflater.from(context);
        this.list = list;
    }
    public void setList(List<Integer> list){
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }
    @Override
    public Object getItem(int i) {
        return list.get(i);
    }
    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolderTask holder = null;

        if (view == null) {
            view = inflater.inflate(R.layout.adapter_icon, null);
            holder = new ViewHolderTask();
            holder.imageView = (ImageView) view.findViewById(R.id.ad_icon_img);
            view.setTag(holder);//讲ViewHolder存储在View中
        } else {
            holder = (ViewHolderTask) view.getTag();//重获取viewHolder
        }
        holder.imageView.setImageResource(list.get(i));

        return view;
    }
    class ViewHolderTask {
        ImageView imageView;
    }
}