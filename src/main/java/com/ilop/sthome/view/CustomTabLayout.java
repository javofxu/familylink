package com.ilop.sthome.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.siterwell.familywellplus.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * @author skygge
 * @date 2019-12-30.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：对 support Design 包中的TabLayout包装
 * 主要实现功能：更改indicator 的长度
 */
public class CustomTabLayout extends FrameLayout {
        private TabLayout mTabLayout;
        private List<String> mTabList;
        private List<View> mCustomViewList;
        private int mSelectIndicatorColor;
        private int mSelectTextColor;
        private int mUnSelectTextColor;
        private int mIndicatorHeight;
        private int mIndicatorWidth;
        private int mTabMode;
        private int mTabTextSize;

        public CustomTabLayout(@NonNull Context context) {
            super(context);
            init(context,null);
        }

        public CustomTabLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
            init(context,attrs);
        }

        public CustomTabLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            init(context,attrs);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public CustomTabLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
            init(context,attrs);
        }

        private void readAttr(Context context,AttributeSet attrs){
            TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.CustomTabLayout);
            mSelectIndicatorColor = typedArray.getColor(R.styleable.CustomTabLayout_tabIndicatorColor, Color.parseColor("#4C80F8"));
            mUnSelectTextColor =  typedArray.getColor(R.styleable.CustomTabLayout_tabTextColor, Color.parseColor("#666666"));
            mSelectTextColor = typedArray.getColor(R.styleable.CustomTabLayout_tabSelectTextColor,context.getResources().getColor(R.color.colorAccent));
            mIndicatorHeight = typedArray.getDimensionPixelSize(R.styleable.CustomTabLayout_tabIndicatorHeight,1);
            mIndicatorWidth = typedArray.getDimensionPixelSize(R.styleable.CustomTabLayout_tabIndicatorWidth,0);
            mTabTextSize = typedArray.getDimensionPixelSize(R.styleable.CustomTabLayout_tabTextSize,12);
            mTabMode = typedArray.getInt(R.styleable.CustomTabLayout_tab_Mode,2);
            typedArray.recycle();
        }

        private void init(Context context,AttributeSet attrs){
            readAttr(context,attrs);

            mTabList = new ArrayList<>();
            mCustomViewList = new ArrayList<>();
            View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_custom_tab,this,true);
            mTabLayout = view.findViewById(R.id.enhance_tab_view);

            // 添加属性
            mTabLayout.setTabMode(mTabMode == 1 ? TabLayout.MODE_FIXED : TabLayout.MODE_SCROLLABLE);
            mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    // onTabItemSelected(tab.getPosition());
                    // Tab 选中之后，改变各个Tab的状态
                    for (int i=0;i<mTabLayout.getTabCount();i++){
                        View view = mTabLayout.getTabAt(i).getCustomView();
                        if(view == null){
                            return;
                        }
                        TextView text = view.findViewById(R.id.tab_item_text);
                        View indicator = view.findViewById(R.id.tab_item_indicator);
                        if(i == tab.getPosition()){ // 选中状态
                            text.setTextColor(mSelectTextColor);
                            text.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                            indicator.setBackgroundResource(R.drawable.round_horizontal_bg);
                            indicator.setVisibility(View.VISIBLE);
                        }else{// 未选中状态
                            text.setTextColor(mUnSelectTextColor);
                            text.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                            indicator.setVisibility(View.INVISIBLE);
                        }
                    }

                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        }

        public List<View> getCustomViewList(){
            return mCustomViewList;
        }

        public void addOnTabSelectedListener (TabLayout.OnTabSelectedListener onTabSelectedListener){
            mTabLayout.addOnTabSelectedListener(onTabSelectedListener);
        }

        /**
         * 与TabLayout 联动
         * @param viewPager
         */
        public void setupWithViewPager(@Nullable ViewPager viewPager) {
            mTabLayout.addOnTabSelectedListener(new ViewPagerOnTabSelectedListener(viewPager,this));
        }


        /**
         * retrive TabLayout Instance
         * @return
         */
        public TabLayout getTabLayout(){
            return mTabLayout;
        }

        /**
         * 添加tab
         * @param tab
         */
        public void addTab(String tab){
            mTabList.add(tab);
            View customView = getTabView(getContext(),tab,mIndicatorWidth, mIndicatorHeight, mTabTextSize);
            mCustomViewList.add(customView);
            mTabLayout.addTab(mTabLayout.newTab().setCustomView(customView));
        }

        public void removeTab(){
            mTabLayout.removeAllTabs();
        }

        public static class ViewPagerOnTabSelectedListener implements TabLayout.OnTabSelectedListener{

            private final ViewPager mViewPager;
            private final WeakReference<CustomTabLayout> mTabLayoutRef;

            public ViewPagerOnTabSelectedListener(ViewPager viewPager,CustomTabLayout CustomTabLayout) {
                mViewPager = viewPager;
                mTabLayoutRef = new WeakReference<>(CustomTabLayout);
            }

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                CustomTabLayout mTabLayout = mTabLayoutRef.get();
                List<View> customViewList = mTabLayout.getCustomViewList();
                if(customViewList == null || customViewList.size() ==0){
                    return;
                }
                for (int i=0;i<customViewList.size();i++){
                    View view = customViewList.get(i);
                    if(view == null){
                        return;
                    }
                    TextView text = (TextView) view.findViewById(R.id.tab_item_text);
                    View indicator = view.findViewById(R.id.tab_item_indicator);
                    if(i == tab.getPosition()){ // 选中状态
                        text.setTextColor(mTabLayout.mSelectTextColor);
                        text.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                        indicator.setBackgroundColor(R.drawable.round_horizontal_bg);
                        indicator.setVisibility(View.VISIBLE);
                    }else{// 未选中状态
                        text.setTextColor(mTabLayout.mUnSelectTextColor);
                        text.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                        indicator.setVisibility(View.INVISIBLE);
                    }
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // No-op
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // No-op
            }
        }

        /**
         * 获取Tab 显示的内容
         *
         * @param context
         * @param
         * @return
         */
        public static View getTabView(Context context,String text,int indicatorWidth,int indicatorHeight,int textSize) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_tab_layout, null);
            TextView tabText = view.findViewById(R.id.tab_item_text);
            if(indicatorWidth>0){
                View indicator = view.findViewById(R.id.tab_item_indicator);
                ViewGroup.LayoutParams layoutParams = indicator.getLayoutParams();
                layoutParams.width  = indicatorWidth;
                layoutParams.height = indicatorHeight;
                indicator.setLayoutParams(layoutParams);
            }
            tabText.setTextSize(textSize);
            tabText.setText(text);
            return view;
        }
}
