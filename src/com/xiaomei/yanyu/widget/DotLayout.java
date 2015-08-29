package com.xiaomei.yanyu.widget;

import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.util.ScreenUtils;

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
	}
	
	public void setSize(int size){
	    for(int i= 0;i<size;i++){
	        View v = new View(getContext());
	        LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(ScreenUtils.dip2px(getContext(), 8), ScreenUtils.dip2px(getContext(), 8));
	        if(i!=0){
	            ll.leftMargin =  ScreenUtils.dip2px(getContext(), 8);
	        }
	        v.setLayoutParams(ll);
	        v.setBackgroundResource(R.drawable.dot_drawable);
	        addView(v);
	    }
	}
	
	public void setSelect(int position){
		int count = getChildCount();
		if(count==0)
		    return;
		View v = null;
		for(int i=0;i<count;i++){
			v = getChildAt(i);
			v.setBackgroundResource(R.drawable.dot_drawable);
		}
		v = getChildAt(position);
		v.setBackgroundResource(R.drawable.dot_selected_drawable);
	}
}
