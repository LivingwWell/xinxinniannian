package com.example.lee.xinxinniannian.adapter;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lee.xinxinniannian.R;

import java.util.ArrayList;

/**
 * Created by lee on 2017/7/29.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private ArrayList<String> mData;

    public RecyclerAdapter(ArrayList<String> data) {
        this.mData = data;
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
    public void onBindViewHolder(ViewHolder holder, int position) {
        //绑定数据

        holder.mtv.setText(mData.get(position));

    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mtv;

        public ViewHolder(View itemView) {
            super(itemView);
            mtv = (TextView) itemView.findViewById(R.id.item_tv);
        }
    }
}
