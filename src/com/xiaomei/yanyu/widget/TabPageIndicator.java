package com.xiaomei.yanyu.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

public class TabPageIndicator extends com.viewpagerindicator.TabPageIndicator {

    public TabPageIndicator(Context context) {
        super(context);
    }

    public TabPageIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        ViewGroup layout = (ViewGroup)getChildAt(0);
        if (layout.getChildCount() == 1) {
            layout.getChildAt(0).setBackgroundColor(getResources().getColor(android.R.color.white));
        }
    }

}
