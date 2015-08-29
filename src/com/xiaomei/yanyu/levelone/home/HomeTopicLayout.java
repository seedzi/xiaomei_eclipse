package com.xiaomei.yanyu.levelone.home;

import com.xiaomei.yanyu.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

public class HomeTopicLayout extends FrameLayout {

	public HomeTopicLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setUpView();
	}

	public HomeTopicLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		setUpView();
	}

	public HomeTopicLayout(Context context) {
		super(context);
		setUpView();
	}

	private void setUpView(){
		LayoutInflater inflater = LayoutInflater.from(getContext());
		addView(inflater.inflate(R.layout.home_topic_layout, null));
	}
}
