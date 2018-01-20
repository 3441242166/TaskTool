package com.example.wanhao.tasktool.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wanhao.tasktool.R;
import com.example.wanhao.tasktool.bean.MyWord;
import com.example.wanhao.tasktool.tool.StringUtil;
import com.truizlop.sectionedrecyclerview.SectionedRecyclerViewAdapter;

import java.util.List;

/**
 * Created by wanhao on 2017/10/20. CountSectionAdapter
 */

public class WordSectionAdapter extends SectionedRecyclerViewAdapter<CountHeaderViewHolder,
        CountItemViewHolder,
        CountFooterViewHolder> {

    private static final String TAG = "CountSectionAdapter";

    private Context context;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    private List<List<MyWord>> lists;

    public WordSectionAdapter(Context context,List<List<MyWord>> lists){
        this.context = context;
        this.lists = lists;
    }

    //  控制每个section显示的item数量
    @Override
    protected int getItemCountForSection(int section) {
        Log.i(TAG, "getItemCountForSection: "+section);
        return lists.get(section).size();
    }
    //控制section数量
    @Override
    protected int getSectionCount() {
        return lists.size();
    }

    @Override
    protected boolean hasFooterInSection(int section) {
        return true;
    }

    protected LayoutInflater getLayoutInflater(){
        return LayoutInflater.from(context);
    }

    @Override
    protected CountHeaderViewHolder onCreateSectionHeaderViewHolder(ViewGroup parent, int viewType) {
        View view = getLayoutInflater().inflate(R.layout.word_header, parent, false);
        return new CountHeaderViewHolder(view);
    }

    @Override
    protected CountFooterViewHolder onCreateSectionFooterViewHolder(ViewGroup parent, int viewType) {
        View view = getLayoutInflater().inflate(R.layout.word_footer, parent, false);
        return new CountFooterViewHolder(view);
    }

    @Override
    protected CountItemViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = getLayoutInflater().inflate(R.layout.word_item, parent, false);
        return new CountItemViewHolder(view);
    }

    @Override
    protected void onBindSectionHeaderViewHolder(CountHeaderViewHolder holder, int section) {
        holder.render(String.valueOf(StringUtil.getStringFirstChar(lists.get(section).get(0).getWord())));
    }

    @Override
    protected void onBindSectionFooterViewHolder(CountFooterViewHolder holder, int section) {
        holder.render("一个有" + lists.get(section).size()+"个单词");
    }

    @Override
    protected void onBindItemViewHolder(CountItemViewHolder holder, int section, int position) {
        holder.render(section,position,
                lists.get(section).get(position).getWord(),
                lists.get(section).get(position).getMean(),
                lists.get(section).get(position).getColor());

        holder.setOnClickListener(new CountItemViewHolder.OnClickListener() {
            @Override
            public void onClick(int section, int position) {
                if(onItemClickListener!=null)
                    onItemClickListener.onItemClick(section,position);
            }
        });

        holder.setOnLongClickListener(new CountItemViewHolder.OnLongClickListener() {
            @Override
            public void onLongClick(int section, int position) {
                if(onItemLongClickListener!=null)
                    onItemLongClickListener.onLongItemClick(section,position);
            }
        });
    }


    public interface OnItemClickListener {
        public void onItemClick(int section, int position);
    }

    public void setOnItemClickListener(OnItemClickListener on){
        this.onItemClickListener = on;
    }

    public interface OnItemLongClickListener {
        public void onLongItemClick(int section, int position);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener on){
        this.onItemLongClickListener = on;
    }
}