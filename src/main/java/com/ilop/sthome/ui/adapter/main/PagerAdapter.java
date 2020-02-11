package com.ilop.sthome.ui.adapter.main;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author skygge
 * @date 2019-12-31.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：
 */
public class PagerAdapter extends FragmentPagerAdapter {

    private List<String> mTitle;
    private List<Fragment> fragmentList;

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setTitleAndFragment(List<String> mTitle, List<Fragment> fragmentList) {
        this.mTitle = mTitle;
        this.fragmentList = fragmentList;
        notifyDataSetChanged();
    }

    public void setTitle(List<String> mTitle) {
        this.mTitle = mTitle;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int i) {
        return fragmentList.get(i);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle.get(position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
    }
}
