package com.example.lee.xinxinniannian.LikeFragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lee.xinxinniannian.R;
import com.example.lee.xinxinniannian.adapter.RecyclerAdapter;

import java.util.ArrayList;


/**
 * Created by lee on 2017/7/26.
 */

public class FragmentAll extends android.support.v4.app.Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_all, container, false);
        initData();
        initView();
        return view;
    }


    private void initData() {
       mLayoutManager = new GridLayoutManager(this.getActivity(),3,OrientationHelper.VERTICAL,false);
       // mRecyclerView.setLayoutManager(new GridLayoutManager(FragmentAll.this,4));
        mAdapter = new RecyclerAdapter(getData());
    }

    private void initView() {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.grid_recycler_one);
        // 设置布局管理器
        mRecyclerView.setLayoutManager(mLayoutManager);
        // 设置adapter
        mRecyclerView.setAdapter(mAdapter);
    }


    private ArrayList<String> getData() {
        ArrayList<String> data = new ArrayList<>();
        String temp = " item";
        for (int i = 0; i < 20; i++) {
            data.add(i + temp);
            if (i+1%3==0){

            }else if (i+2%3==0){

            }else{

            }
        }
        return data;
    }
}
