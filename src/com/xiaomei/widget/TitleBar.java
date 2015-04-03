package com.xiaomei.widget;

import com.xiaomei.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

public class TitleBar extends FrameLayout implements View.OnClickListener{

	private TextView mTitleView;
	
	private ImageButton mBackButton;
	
	private ViewGroup mCenterContainer;
	
	private View mLoginButton;
	
	private View mRegisterButton;
	
	private Listener mListener;
	
	public TitleBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public TitleBar(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public TitleBar(Context context) {
		super(context);
	}
	
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setupViews();
    }
	
	private void setupViews(){
		mTitleView = (TextView) findViewById(R.id.title);
		mBackButton = (ImageButton)findViewById(R.id.back);
		mCenterContainer = (ViewGroup) findViewById(R.id.center_container);
		mLoginButton =  findViewById(R.id.login);
		mRegisterButton =  findViewById(R.id.register);
		mLoginButton.setOnClickListener(this);
		mRegisterButton.setOnClickListener(this);
	}
	
	public void setBackListener(View.OnClickListener listener){
		mBackButton.setOnClickListener(listener);
		mBackButton.setVisibility(View.VISIBLE);
	}

	/**
	 * 设置titlebar标题
	 */
	public void setTitle(String title){
		mTitleView.setText(title);
		mTitleView.setVisibility(View.VISIBLE);
		mCenterContainer.setVisibility(View.GONE);
	}
	
	/**
	 * 设置titlebar中间控件事件
	 */
	public void setListener(Listener listener){
		mListener = listener;
		mTitleView.setVisibility(View.GONE);
		mCenterContainer.setVisibility(View.VISIBLE);
	}
	
	public static interface Listener{
		public void  switchLogin();
		public void switchRegister();
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.login:
			mLoginButton.setBackgroundResource(R.drawable.login_button_press);
			mRegisterButton.setBackgroundResource(R.drawable.register_button);
			if(mListener!=null)
				mListener.switchLogin();
			break;
		case R.id.register:
			mLoginButton.setBackgroundResource(R.drawable.login_button);
			mRegisterButton.setBackgroundResource(R.drawable.register_button_press);
			if(mListener!=null)
				mListener.switchRegister();
			break;
		default:
			break;
		}
	}
}
