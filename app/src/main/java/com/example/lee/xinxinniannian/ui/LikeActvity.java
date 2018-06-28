package com.example.lee.xinxinniannian.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ContentFrameLayout;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.lee.xinxinniannian.LikeFragment.FragmenDigital;
import com.example.lee.xinxinniannian.LikeFragment.FragmentAll;
import com.example.lee.xinxinniannian.LikeFragment.FragmentCioth;
import com.example.lee.xinxinniannian.LikeFragment.FragmentDaily;
import com.example.lee.xinxinniannian.LikeFragment.FragmentShoes;
import com.example.lee.xinxinniannian.R;
import com.example.lee.xinxinniannian.adapter.RecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.example.lee.xinxinniannian.R.id.mViewPager;

/**
 * Created by lee on 2017/7/18.
 */

public class LikeActvity extends AppCompatActivity implements View.OnClickListener{
    private TabLayout mTabLayout;
    private ViewPager mViewpager;
    private List<Fragment>mFragment;
    private List<String>mTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去状态栏和标题
        setContentView(R.layout.acrivity_like);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initDate();
        initview();


    }
    //初始化数据
    private void initDate(){
        mTitle=new ArrayList<>();
        mTitle.add("ALL全部");
        mTitle.add("DAILY日用品");
        mTitle.add("CIOTH衣物");
        mTitle.add("DIGITAL数码");
        mTitle.add("SHOES鞋子");

        mFragment=new ArrayList<>();
        mFragment.add(new FragmentAll());
        mFragment.add(new FragmentDaily());
        mFragment.add(new FragmentCioth());
        mFragment.add(new FragmenDigital());
        mFragment.add(new FragmentShoes());
    }
    //初始化view
    private void initview(){
        mTabLayout = (TabLayout) findViewById(R.id.mTabLayout);
        mViewpager= (ViewPager) findViewById(R.id.mViewPager);
        //预加载
        mViewpager.setOffscreenPageLimit(mFragment.size());
      //设置适配器
        mViewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
           //选中的item
            @Override
            public Fragment getItem(int position) {
                return mFragment.get(position);
            }
          //返回item个数
            @Override
            public int getCount() {
                return mFragment.size();
            }
         //设置标题
            public CharSequence getPageTitle(int position){
                return  mTitle.get(position);
            }
        });
       //绑定
        mTabLayout.setupWithViewPager(mViewpager);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    @Override
    public void onClick(View v) {

    }
}
