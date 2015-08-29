package com.xiaomei.yanyu.widget;

import com.xiaomei.yanyu.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class DotLayout extends LinearLayout {

	public DotLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setupViews();
	}

	public DotLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		setupViews();
	}

	public DotLayout(Context context) {
		super(context);
		setupViews();
	}
	
	private void setupViews(){
	}
	
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		setSelect(0);
	}
	
	public void setSelect(int position){
		int count = getChildCount();
		View v = null;
		for(int i=0;i<count;i++){
			v = getChildAt(i);
			v.setBackgroundResource(R.drawable.dot_drawable);
		}
		v = getChildAt(position);
		v.setBackgroundResource(R.drawable.dot_selected_drawable);
	}
}
