package com.example.wanhao.tasktool.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wanhao.tasktool.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by wanhao on 2017/10/21.
 */

class CountItemViewHolder extends RecyclerView.ViewHolder {
    private TextView txWord;
    private TextView txMean;
    private LinearLayout layout;
    private CircleImageView imageView;


    private int section;
    private int position;
    private OnClickListener onClickListener = null;
    private OnLongClickListener onLongClickListener = null;


    public CountItemViewHolder(View view) {
        super(view);
        layout = (LinearLayout) view.findViewById(R.id.word_item_layout);
        txWord = (TextView) view.findViewById(R.id.word_item_word);
        txMean = (TextView) view.findViewById(R.id.word_item_mean);
        imageView = (CircleImageView) view.findViewById(R.id.word_item_color);
    }

    public void render( final int section, final int position,String word,String mean,int colorId) {
        this.position = position;
        this.section = section;
        imageView.setImageResource(colorId);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onClickListener!=null){
                    onClickListener.onClick(section,position);
                }
            }
        });

        layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(onLongClickListener!=null)
                    onLongClickListener.onLongClick(section,position);
                return true;
            }
        });

        txWord.setText(word);
        txMean.setText(mean.replace("<br>",""));
    }

    public void setOnClickListener(OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        public void onClick(int section,int position);
    }

    public void setOnLongClickListener(OnLongClickListener onLongClickListener){
        this.onLongClickListener = onLongClickListener;
    }

    public interface OnLongClickListener {
        public void onLongClick(int section,int position);
    }
}
