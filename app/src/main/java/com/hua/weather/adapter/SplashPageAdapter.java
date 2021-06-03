package com.hua.weather.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

/**
 * Create by Spring on 2020/9/1 13:40
 */
public class SplashPageAdapter extends PagerAdapter {
    private List<ImageView> list;

    public SplashPageAdapter(List<ImageView> list) {
        this.list = list;
    }

    //实例化条目
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = list.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = list.get(position);
        container.removeView(view);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}