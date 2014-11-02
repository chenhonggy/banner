package com.banner.main.easybanner;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.banner.main.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hu on 14-10-30.
 */
public class Banner extends RelativeLayout implements ViewPager.OnPageChangeListener{

    //图片下方小圆点
    private LinearLayout point_group;

    //定义常量
    private static final int MSG_GETMESSAGE = 1;

    //定义handler方便banner循环切换
    private Handler handler;

    private Context context;

    // 前一个被选中的点的索引位置 默认情况下为0
    private int preEnablePositon = 0;

    //定义viewPager容器
    private ViewPager viewPager;

    //停止切换图片的线程
    private boolean isStop = false;  //是否停止子线程  不会停止

    //通过图片id来转换图片
    private List<ImageView> imagelist = new ArrayList<ImageView>();

    //定义单个图片
    private ImageView singleImageView;

    //定义一个view
    private View view;

    private LinearLayout.LayoutParams params;

    public Banner(Context context, AttributeSet attrs) {
        super(context, attrs);
        initHandler();
        init(context);
    }

    public Banner(Context context) {
        super(context);
        initHandler();
        init(context);

    }

    public Banner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initHandler();
        init(context);
    }

    private void initHandler(){
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case MSG_GETMESSAGE:
                        int newindex = viewPager.getCurrentItem() + 1;
                        viewPager.setCurrentItem(newindex);
                        break;
                }
            }
        };
    }

    //添加banner
    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.class_banner, this, true);
        viewPager = (ViewPager)findViewById(R.id.viewpager);
        point_group = (LinearLayout)findViewById(R.id.ll_point_group);
        this.context = context;
    }

    public void initImage(List<Integer> imageId) {

        int num = imageId.size();
        for (int i = 0; i < num; i++) {
            singleImageView = new ImageView(context);
            singleImageView.setBackgroundResource(imageId.get(i));
            imagelist.add(singleImageView);
            // 每循环一次添加一个点到现形布局中
            view = new View(context);
            view.setBackgroundResource(R.drawable.point_background);
            params = new LinearLayout.LayoutParams(12, 12);
            params.leftMargin = 5;
            view.setEnabled(false);
            view.setLayoutParams(params);
            point_group.addView(view); // 向线性布局中添加“点”
        }
        viewPager.setAdapter(new MyAdapter());
        viewPager.setOnPageChangeListener(this);
        point_group.getChildAt(0).setEnabled(true);
        int index = (Integer.MAX_VALUE / 2)
                - ((Integer.MAX_VALUE / 2) % imagelist.size());
        viewPager.setCurrentItem(index); // 设置当前viewpager选中的pager页

        Thread myThread = new Thread(new Runnable() {

            @Override
            public void run() {
                while (!isStop) {
                    SystemClock.sleep(3000);
                    handler.obtainMessage(MSG_GETMESSAGE).sendToTarget();
                }
            }
        });
        myThread.start();
    }

    //banner的适配器
    class MyAdapter extends PagerAdapter {
        /**
         * 销毁对象
         *
         * @param position 将要被销毁对象的索引位置
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //销毁的位置为instantiateItem初始化位置+2
            container.removeView(imagelist.get(position % imagelist.size()));
        }

        /**
         * 初始化一个对象
         *
         * @param position 将要被创建的对象的索引位置
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // 先把对象添加到viewpager中，再返回当前对象
            //这样子可以达到viewpager中始终只有3张图片
            //中间的一张为用户能够看见的一张
            //这样子避免了左右拉动看不见图片的问题
            container.addView(imagelist.get(position % imagelist.size()));
            return imagelist.get(position % imagelist.size());
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            //设置一个最大的值方便左右拉动动画的切换
            return Integer.MAX_VALUE;
        }

        /**
         * 复用对象 true 复用对象 false 用的是object
         */
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            // TODO Auto-generated method stub
            return arg0 == arg1;
        }

    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageSelected(int position) {
        int newPositon = position % (imagelist.size());
        point_group.getChildAt(preEnablePositon).setEnabled(false);
        point_group.getChildAt(newPositon).setEnabled(true);
        preEnablePositon = newPositon;

    }
}

