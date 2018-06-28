package com.example.lee.xinxinniannian.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.lee.xinxinniannian.R;
import com.example.lee.xinxinniannian.ui.MyImageView;
import com.example.lee.xinxinniannian.utils.Data;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by lee on 2017/7/29.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
   private List<Data> slist;
    Context context;



    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }

    public RecyclerAdapter(Context context, List<Data> Starlist) {
        this.slist = Starlist;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // 实例化展示的view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.page_item, parent, false);
        // 实例化viewholder
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Data data = slist.get(position);
        Picasso.with(context).load(data.getImagerUrl()).error(R.drawable.black_bg).fit().centerCrop().into(holder.imageView);
         Log.d("get1"+slist.size(),""+data.getImagerUrl());
        //itme点击事件
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.itemView, position);
                }
            });
        }
        if (mOnItemLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = holder.getLayoutPosition();
                    mOnItemLongClickListener.onItemLongClick(holder.itemView, position);
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return slist.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout starItem;
        MyImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (MyImageView) itemView.findViewById(R.id.imageView);
            starItem = (LinearLayout) itemView.findViewById(R.id.star_item);

        }
    }
}
