package com.example.common.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.List;

/**
 * @Author skygge.
 * @Date on 2019-10-06.
 * @Github https://github.com/javofxu
 * @Dec: 自定义轮播图
 * @version: ${VERSION}.
 * @Update :
 */
public class CarouselViewPager extends ViewPager {

    private static final String TAG = "CarouselViewPager";
    private Context mContext;
    //滚动的页面
    private List<FrameLayout> mViews;

    //滚动的页面数量
    private int count;

    //自动滚动信号
    private final int SCROLL = 100002;

    //创建一个滚动线程
    private Thread thread;

    //目前线程状态
    private boolean NORMAL = true;

    private int mCurrentPosition;

    private boolean mHasCamera;

    private OnViewpagerListener mListener;

    public CarouselViewPager(@NonNull Context context) {
        super(context);
        mContext = context;
    }

    public CarouselViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            setCurrentItem(getCurrentItem()+1);
        }
    };

    public void initViewPager(List<FrameLayout> mImg, boolean hasCamera, OnViewpagerListener listener){
        this.mViews = mImg;
        mHasCamera = hasCamera;
        this.count = mImg.size();
        Log.i(TAG, "initViewPager: "+ mHasCamera +"--"+count);
        mListener = listener;
        setAdapter(new BannerPager());
        setCurrentItem(0);
        thread = new Thread(() -> {
            while (true){
                try {
                    NORMAL = true;
                    Thread.sleep(5*1000);
                    mHandler.sendEmptyMessage(SCROLL);
                }catch (InterruptedException e){
                    NORMAL = false;
                    try {
                        Thread.sleep(Integer.MAX_VALUE);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        //if (count>1) thread.start();

        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                Log.i("AAA", "onPageSelected: "+i);
                listener.onChange(i % count);
                mCurrentPosition = i % count;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if ((!NORMAL && state != 1) || (NORMAL && state == 1)){
                    thread.interrupt();
                }
            }
        });
    }

    public class BannerPager extends PagerAdapter {

        @Override
        public int getCount() {
            return count == 1 ? count : Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            int temp = position % count;
            FrameLayout image = mViews.get(temp);
            if (mListener!=null){
                image.getChildAt(0).setOnClickListener(v -> {
                    if (mHasCamera){
                        mListener.onImageClick(mCurrentPosition);
                    }else {
                        mListener.onNoCamera();
                    }
                });
            }
            if (image.getParent() == container){
                container.removeView(image);
            }
            container.addView(image);
            return image;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        }
    }

    public interface OnViewpagerListener {

        void onChange(int currentPage);

        void onImageClick(int position);

        void onNoCamera();
    }
}
