package com.xiaomei.yanyu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class ArrayPagerAdapter<T> extends PagerAdapter {

    private List<T> mItems = new ArrayList<T>();

    public T getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public void addAll(T[] items) {
        mItems.addAll(Arrays.asList(items));
        notifyDataSetChanged();
    }

    public void addAll(Collection<T> items) {
        mItems.addAll(items);
        notifyDataSetChanged();
    }

    public void clear() {
        mItems.clear();
    }
}
