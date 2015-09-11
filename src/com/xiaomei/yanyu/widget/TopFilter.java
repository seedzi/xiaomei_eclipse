package com.xiaomei.yanyu.widget;

import java.util.ArrayList;
import java.util.List;

import com.xiaomei.yanyu.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListAdapter;

public class TopFilter extends LinearLayout {

    public TopFilter(Context context) {
        super(context);
    }

    public TopFilter(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TopFilter(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setOrientation(HORIZONTAL);
    }

    public void addAll(ListAdapter[] listAdapters) {
        for (ListAdapter listAdapter : listAdapters) {
            DropMenu menu = (DropMenu) LayoutInflater.from(getContext()).inflate(R.layout.top_filter_item, this, false);
            menu.setAdapter(listAdapter);
            addView(menu);
        }
    }

    public DropMenu getFilter(int position) {
        return (DropMenu) getChildAt(position);
    }
}
