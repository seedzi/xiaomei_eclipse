/*
 * Copyright (c) 2015 Xiamei Inc. All rights reserved.
 */

package com.xiaomei.yanyu.widget;

import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.util.UiUtil;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by sunbreak on 7/19/15.
 */
public class TitleActionBar {

    private int mTextColor;
    private int mTextSizePx;

    private final TextView title;
    private final View home;
    private final FrameLayout action;

    public TitleActionBar(ActionBar actionBar) {
        int mask = ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_HOME_AS_UP;
        actionBar.setDisplayOptions(android.app.ActionBar.DISPLAY_SHOW_CUSTOM, mask);
        actionBar.setCustomView(R.layout.title_action_bar);
        View actionBarView = actionBar.getCustomView();
        title = (TextView) actionBarView.findViewById(android.R.id.title);
        home = actionBarView.findViewById(android.R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UiUtil.hideSoftInput(v);
                ((Activity) v.getContext()).onBackPressed();
            }
        });
        action = (FrameLayout) actionBarView.findViewById(R.id.action);
    }

    public void setTitle(int textRes) {
        title.setText(textRes);
    }

    public void setTitle(CharSequence text) {
        title.setText(text);
    }

    public void setHomeVisibility(int visibility) {
        home.setVisibility(visibility);
    }

    public void setOnHomeClickListener(View.OnClickListener listener) {
        home.setOnClickListener(listener);
    }

    public void setActionVisibility(int visibility) {
        action.setVisibility(visibility);
    }

    public void setImageAction(int imageRes) {
        ImageButton button = (ImageButton) showButton(R.layout.action_bar_image_button);
        button.setImageResource(imageRes);
    }

    public void setImageAction(Drawable imageDrawable) {
        ImageButton button = (ImageButton) showButton(R.layout.action_bar_image_button);
        button.setImageDrawable(imageDrawable);
    }

    public void setTextAction(int textRes) {
        Button button = (Button) showButton(R.layout.action_bar_button);
        button.setText(textRes);
        setTextStyle(button);
    }

    public void setTextAction(CharSequence text) {
        Button button = (Button) showButton(R.layout.action_bar_button);
        button.setText(text);
        setTextStyle(button);
    }

    private void setTextStyle(TextView textView) {
        if (mTextColor != 0) {
            textView.setTextColor(mTextColor);
        }
        if (mTextSizePx > 0) {
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSizePx);
        }
    }

    public void setActionTextColor(int textColor) {
        mTextColor = textColor;
    }

    public void setActionTextSize(int textSizePx) {
        mTextSizePx = textSizePx;
    }

    private View showButton(int layout) {
        action.setVisibility(View.VISIBLE);
        action.removeAllViews();
        View button = LayoutInflater.from(action.getContext()).inflate(layout, action, false);
        action.addView(button);
        return button;
    }

    public void setOnActionClickListener(View.OnClickListener listener) {
        action.setOnClickListener(listener);
    }
}
