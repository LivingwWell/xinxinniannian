package com.example.lee.xinxinniannian.LikeFragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.lee.xinxinniannian.R;
import com.example.lee.xinxinniannian.adapter.ItemDecoration;
import com.example.lee.xinxinniannian.adapter.RecyclerAdapter;
import com.example.lee.xinxinniannian.ui.WebActivty;
import com.example.lee.xinxinniannian.utils.Data;
import com.example.lee.xinxinniannian.utils.Stars;
import com.example.lee.xinxinniannian.utils.StarsDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lee on 2017/7/26.
 */

public class FragmentDaily extends Fragment{
    View view;
    Context context;

    private Stars stars;
    @Nullable
    private List<Data> satrlist=new ArrayList<>();
    private void init(){
        stars=new Stars(getActivity());
        SQLiteDatabase sd=stars.getReadableDatabase();
        Cursor cursor=sd.query("StarDB",null,"fenlei=?",new String[]{"DAILY NEWCESSITIES/日用品"},null,null,null);
        while (cursor.moveToNext()){
            String imagerurl=cursor.getString(cursor.getColumnIndex("imagerUrl"));
            String weburl=cursor.getString(cursor.getColumnIndex("webUrl"));
            String fenlei=cursor.getString(cursor.getColumnIndex("fenlei"));
            Data data=new Data(imagerurl,weburl,fenlei);
            satrlist.add(data);
        }
        sd.close();
        cursor.close();

    }
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_daily, container, false);
        init();

        final RecyclerAdapter adapter=new RecyclerAdapter(context,satrlist);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(context);
        RecyclerView recyclerView= (RecyclerView) view.findViewById(R.id.grid_recycler_one);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(context,3));
        recyclerView.addItemDecoration(new ItemDecoration(0));
        adapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), WebActivty.class);
                intent.putExtra("urls", satrlist.get(position).getWebUrl());
                startActivity(intent);
                //   Toast.makeText(getContext(), "click " + satrlist.get(position), Toast.LENGTH_SHORT).show();
            }
        });
        adapter.setOnItemLongClickListener(new RecyclerAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                SQLiteDatabase sd=stars.getWritableDatabase();
                sd.delete("StarDB","imagerUrl=?",new String[]{satrlist.get(position).getImagerUrl()});
                sd.close();
                satrlist.remove(position);
                adapter.notifyDataSetChanged();
                // Toast.makeText(getContext(), "长按 " + position, Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
