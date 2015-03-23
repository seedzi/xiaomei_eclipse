package com.xiaomei.widget;

import com.xiaomei.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

public class TitleBar extends FrameLayout {

	private TextView mTitleView;
	
	private ImageButton mBackButton;
	
	public TitleBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public TitleBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public TitleBar(Context context) {
		super(context);
		init(context);
	}
	
	private void init(Context context){
		mTitleView = (TextView) findViewById(R.id.title);
		mBackButton = (ImageButton)findViewById(R.id.back);
	}
	
	public void setBackListener(View.OnClickListener listener){
		mBackButton.setOnClickListener(listener);
	}

	public void setTitle(String title){
		mTitleView.setText(title);
	}
	
}
