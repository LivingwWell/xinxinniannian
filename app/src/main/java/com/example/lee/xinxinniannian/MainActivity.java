package com.example.lee.xinxinniannian;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.lee.xinxinniannian.adapter.MirrorTransformation;
import com.example.lee.xinxinniannian.ui.LikeActvity;
import com.example.lee.xinxinniannian.ui.WebActivty;
import com.example.lee.xinxinniannian.utils.Data;
import com.example.lee.xinxinniannian.utils.SHOP;
import com.example.lee.xinxinniannian.utils.StarsDao;
import com.example.lee.xinxinniannian.utils.StaticClass;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;


public class MainActivity extends Activity {
    private ViewPager viewPager;
    private long fristtime = 0;
    private List<ImageView> imageViewList;
    private TextView name, money, fenlei;
    private ImageButton imageButton;
    private ImageView tietle;
    private String dizhi, starfenlei, starurl;
    private SmartRefreshLayout smartRefreshLayout;
    private StarsDao mdao;
    //小圆点
    private ImageView pint1, pint2, pint3, pint4, pint5, pint6, pint7, pint8, pint9, pint10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //去状态栏和标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mdao=new StarsDao(MainActivity.this);
        //初始化Bmob
        Bmob.initialize(this, StaticClass.BMOB_APP_KEY);
        geturl();

    }

    //双击返回键退出软件
    public boolean onKeyUp(int KeyCode, KeyEvent event) {
        switch (KeyCode) {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - fristtime > 2000) {
                    Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
                    fristtime = secondTime;
                    return true;
                } else {
                    System.exit(0);
                }
                break;
        }
        return super.onKeyUp(KeyCode, event);
    }

    private List<SHOP> shops = null;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                shops = (List<SHOP>) msg.obj;
                Log.d("urls", shops.get(0).toString());
                init();
            }
        }
    };

    //获取网络图片的地址
    private void geturl() {
        final BmobQuery<SHOP> query = new BmobQuery<>();
        query.findObjects(new FindListener<SHOP>() {
            @Override
            public void done(List<SHOP> list, BmobException e) {
                if (e == null) {
                    Log.d("urls", "查询成功： " + list.get(0).getUrl());
                    Message msg = new Message();
                    msg.what = 1;
                    msg.obj = list;
                    mHandler.sendMessage(msg);
                } else {
                    Log.d("urls", "查询失败 " + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });

    }

    //自动加载首页
    public void Setdata(final int i) {
        BmobQuery<SHOP> query = new BmobQuery<>();
        query.findObjects(new FindListener<SHOP>() {
            @Override
            public void done(final List<SHOP> list, BmobException e) {
                if (e == null) {
                    Log.d("bmob", "查询成功： " + list.get(i).getObjectId() + list.get(i).getName() + list.get(i).getFenlei() + list.get(i).getUrl());
                    name.setText(list.get(i).getName());
                    fenlei.setText(list.get(i).getFenlei());
                    money.setText(list.get(i).getMomey());
                    dizhi = list.get(i).getDizhi();
                    starurl = list.get(i).getUrl();
                    starfenlei = list.get(i).getFenlei();
                    imageButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Data data = new Data();
                            data.setImagerUrl(list.get(i).getUrl());
                            data.setWebUrl(list.get(i).getDizhi());
                            data.save(new SaveListener<String>() {
                                @Override
                                public void done(String s, BmobException e) {
                                    if (e == null) {
                                        Toast.makeText(MainActivity.this, "已收藏", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                                    }
                                }
                            });
                            mdao.add(starurl,dizhi,starfenlei);
                            Intent intent = new Intent();
                            intent.setClass(MainActivity.this, LikeActvity.class);
                            startActivity(intent);
                        }
                    });
                } else {
                    Log.d("bmob", "查询失败 " + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    private void init() {
        imageViewList = new ArrayList<>();
        Log.d("urls", "传递成功： " + shops.get(0).getUrl());
        for (SHOP shop : shops) {
            ImageView img = new ImageView(MainActivity.this);
            Picasso.with(MainActivity.this).load(shop.getUrl()).placeholder(R.drawable.dark_bg).resize(500, 500).transform(new MirrorTransformation()).into(img);
            imageViewList.add(img);
        }
        initView();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initView() {
        pint1 = (ImageView) findViewById(R.id.pint1);
        pint2 = (ImageView) findViewById(R.id.pint2);
        pint3 = (ImageView) findViewById(R.id.pint3);
        pint4 = (ImageView) findViewById(R.id.pint4);
        pint5 = (ImageView) findViewById(R.id.pint5);
        pint6 = (ImageView) findViewById(R.id.pint6);
        pint7 = (ImageView) findViewById(R.id.pint7);
        pint8 = (ImageView) findViewById(R.id.pint8);
        pint9 = (ImageView) findViewById(R.id.pint9);
        pint10 = (ImageView) findViewById(R.id.pint10);
        name = (TextView) findViewById(R.id.name);
        money = (TextView) findViewById(R.id.momey);
        fenlei = (TextView) findViewById(R.id.fenlei);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tietle = (ImageView) findViewById(R.id.tietle);
        imageButton = (ImageButton) findViewById(R.id.imageButton);
        smartRefreshLayout = (SmartRefreshLayout) findViewById(R.id.smartRefreshLayout);
        //是否监听列表惯性滚动到底部时触发加载事件,显示下拉箭头
        smartRefreshLayout.setEnableAutoLoadMore(false);
        //下拉刷新
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                smartRefreshLayout.finishRefresh(2000);
                init();
            }
        });
        //上滑加载详情页
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                smartRefreshLayout.finishLoadMore(2000);
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, WebActivty.class);
                intent.putExtra("urls", dizhi);
                startActivity(intent);
            }
        });

/*        tietle.setOnClickListener(this);
        imageButton.setOnClickListener(this);*/

        Setdata(0);
        //设置动画效果
        viewPager.setPageTransformer(true, new GalleyTransFormer());
        //设置小圆点,商品信息
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            public void loadDate(final int i) {
                BmobQuery<SHOP> query = new BmobQuery<>();
                query.findObjects(new FindListener<SHOP>() {
                    @Override
                    public void done(final List<SHOP> list, BmobException e) {
                        if (e == null) {
                            Log.d("bmob", "查询成功： " + list.get(i).getObjectId() + list.get(i).getName() + list.get(i).getFenlei() + list.get(i).getUrl() + list.get(i).getDizhi());
                            name.setText(list.get(i).getName());
                            fenlei.setText(list.get(i).getFenlei());
                            money.setText(list.get(i).getMomey());
                            dizhi = list.get(i).getDizhi();
                            starurl = list.get(i).getUrl();
                            starfenlei = list.get(i).getFenlei();
                            imageButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    final Data data = new Data();
                                    data.setImagerUrl(list.get(i).getUrl());
                                    data.setWebUrl(list.get(i).getDizhi());
                                    data.setFenleil(list.get(i).getFenlei());
                                    data.save(new SaveListener<String>() {
                                        @Override
                                        public void done(String s, BmobException e) {
                                            if (e == null) {

                                            } else {
                                                Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                                            }
                                        }
                                    });

                                 mdao.add(starurl,dizhi,starfenlei);

                                    Intent intent = new Intent();
                                    intent.setClass(MainActivity.this, LikeActvity.class);
                                    startActivity(intent);
                                }
                            });
                        } else {
                            Log.d("bmob", "查询失败 " + e.getMessage() + "," + e.getErrorCode());
                        }
                    }
                });
            }




            @Override
            public void onPageSelected(int position) {
                Log.d("tag", String.valueOf(position));

                switch (position) {
                    case 0:
                        loadDate(0);
                        setPintImg(true, false, false, false, false, false, false, false, false, false);
                        break;
                    case 1:
                        loadDate(1);
                        setPintImg(false, true, false, false, false, false, false, false, false, false);
                        break;
                    case 2:
                        loadDate(2);
                        setPintImg(false, false, true, false, false, false, false, false, false, false);
                        break;
                    case 3:
                        loadDate(3);
                        setPintImg(false, false, false, true, false, false, false, false, false, false);
                        break;
                    case 4:
                        loadDate(4);
                        setPintImg(false, false, false, false, true, false, false, false, false, false);
                        break;
                    case 5:
                        loadDate(5);
                        setPintImg(false, false, false, false, false, true, false, false, false, false);
                        break;
                    case 6:
                        loadDate(6);
                        setPintImg(false, false, false, false, false, false, true, false, false, false);
                        break;
                    case 7:
                        loadDate(7);
                        setPintImg(false, false, false, false, false, false, false, true, false, false);
                        break;
                    case 8:
                        loadDate(8);
                        setPintImg(false, false, false, false, false, false, false, false, true, false);
                        break;
                    case 9:
                        loadDate(9);
                        setPintImg(false, false, false, false, false, false, false, false, false, true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //间距
        viewPager.setPageMargin(-118);
        //限定预加载的页面个数
        viewPager.setOffscreenPageLimit(3);
        //viewpager适配器
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return imageViewList == null ? 0 : imageViewList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            public Object instantiateItem(ViewGroup container, int position) {
                ImageView imageView = imageViewList.get(position);
                container.addView(imageView);
                return imageView;
            }

            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(imageViewList.get(position));
            }


        });
        //初始view页
        viewPager.setCurrentItem(0);
         /*设置点击ViewPager之外的部位，ViewPager跟着滑动*/
        RelativeLayout parent = (RelativeLayout) viewPager.getParent();
        parent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return viewPager.dispatchTouchEvent(event);
            }
        });
    }

    //点击收藏按钮，进入收藏夹
    public boolean setStar(boolean star) {

        return false;
    }

    //监听事件
/*    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButton:


        }
    }*/
//     public void queryData(){
//
//
//     }


    //动画效果
    static class GalleyTransFormer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.9f;
        private static final float MIN_ALPHA = 0.5f;
        private static float defaultScale = 0.9f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);
                view.setScaleX(defaultScale);
                view.setScaleY(defaultScale);
            } else if (position <= 1) { // [-1,1]
                // Modify the default slide transition to shrink the page as well
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }

                // Scale the page down (between MIN_SCALE and 1)
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                // Fade the page relative to its size.
                view.setAlpha(MIN_ALPHA +
                        (scaleFactor - MIN_SCALE) /
                                (1 - MIN_SCALE) * (1 - MIN_ALPHA));

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
                view.setScaleX(defaultScale);
                view.setScaleY(defaultScale);
            }
        }
    }

    //设置小圆点图片
    private void setPintImg(boolean isCheck1, boolean isCheck2, boolean isCheck3, boolean isCheck4, boolean isCheck5, boolean isCheck6, boolean isCheck7, boolean isCheck8, boolean isCheck9, boolean isCheck10) {
        if (isCheck1) {
            pint1.setImageResource(R.drawable.pint);
        } else {
            pint1.setImageResource(R.drawable.spint);
        }
        if (isCheck2) {
            pint2.setImageResource(R.drawable.pint);
        } else {
            pint2.setImageResource(R.drawable.spint);
        }
        if (isCheck3) {
            pint3.setImageResource(R.drawable.pint);
        } else {
            pint3.setImageResource(R.drawable.spint);
        }
        if (isCheck4) {
            pint4.setImageResource(R.drawable.pint);
        } else {
            pint4.setImageResource(R.drawable.spint);
        }
        if (isCheck5) {
            pint5.setImageResource(R.drawable.pint);
        } else {
            pint5.setImageResource(R.drawable.spint);
        }
        if (isCheck6) {
            pint6.setImageResource(R.drawable.pint);
        } else {
            pint6.setImageResource(R.drawable.spint);
        }
        if (isCheck7) {
            pint7.setImageResource(R.drawable.pint);
        } else {
            pint7.setImageResource(R.drawable.spint);
        }
        if (isCheck8) {
            pint8.setImageResource(R.drawable.pint);
        } else {
            pint8.setImageResource(R.drawable.spint);
        }
        if (isCheck9) {
            pint9.setImageResource(R.drawable.pint);
        } else {
            pint9.setImageResource(R.drawable.spint);
        }
        if (isCheck10) {
            pint10.setImageResource(R.drawable.pint);
        } else {
            pint10.setImageResource(R.drawable.spint);
        }
    }

}
