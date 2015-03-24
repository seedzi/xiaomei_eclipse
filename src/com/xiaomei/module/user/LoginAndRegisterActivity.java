package com.xiaomei.module.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.xiaomei.BaseActiviy;
import com.xiaomei.R;
import com.xiaomei.XiaoMeiApplication;
import com.xiaomei.api.exception.XiaoMeiCredentialsException;
import com.xiaomei.api.exception.XiaoMeiIOException;
import com.xiaomei.api.exception.XiaoMeiJSONException;
import com.xiaomei.api.exception.XiaoMeiOtherException;
import com.xiaomei.contanier.TabsActivity;
import com.xiaomei.widget.TitleBar;
import com.xiaomei.widget.TitleBar.Listener;


public class LoginAndRegisterActivity extends BaseActiviy
		implements Listener,View.OnClickListener{
	
    public static void startActivity(Context context){
        Intent intent = new Intent(context,LoginAndRegisterActivity.class);
        context.startActivity(intent);
    }

	
	private TitleBar mTitleBar;
	
	private View mLaunchButton;
	
	private ViewGroup mLoginInputLayout, mRegisterInputLayout;
	
	private View qqButton, weixinButton, weiboButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_register_layout);
		new Handler().post(new Runnable() {
			@Override
			public void run() {
				initView();
			}
		});
	}

	private void initView(){
		mLoginInputLayout = (ViewGroup) findViewById(R.id.login_input_layout);
		mRegisterInputLayout = (ViewGroup) findViewById(R.id.register_input_layout);
		mLaunchButton = findViewById(R.id.launch);
		mLaunchButton.setOnClickListener(this);
		mTitleBar = (TitleBar) findViewById(R.id.title_bar_layout);
		mTitleBar.setListener(LoginAndRegisterActivity.this);
		qqButton = findViewById(R.id.qq);
		qqButton.setOnClickListener(this);
		weixinButton = findViewById(R.id.weixin);
		weixinButton.setOnClickListener(this);
		weiboButton = findViewById(R.id.sina_weibo);
		weiboButton.setOnClickListener(this);
	}

	@Override
	public void switchLogin() {
		mLoginInputLayout.setVisibility(View.VISIBLE);
		mRegisterInputLayout.setVisibility(View.GONE);
	}

	@Override
	public void switchRegister() {
		mLoginInputLayout.setVisibility(View.GONE);
		mRegisterInputLayout.setVisibility(View.VISIBLE);
	}

	@Override
	public void onClick(View v) {	
		int id = v.getId();
		switch (id) {
		case R.id.launch:
			// TODO : 点击button
			break;
		case R.id.qq:
			// TODO 
			break;
		case R.id.weixin:
			// TODO 
			break;
		case R.id.sina_weibo:
			// TODO 
			break;
		default:
			break;
		}
	}
	
}
