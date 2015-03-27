package com.xiaomei.module.user.center;

import android.content.Context;
import android.content.Intent;
import android.graphics.Shader.TileMode;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.xiaomei.BaseActiviy;
import com.xiaomei.R;
import com.xiaomei.widget.TitleBar;

public class AboutActivity extends BaseActiviy{
	
	public static void startActivity(Context context){
		Intent intent = new Intent(context,AboutActivity.class);
		context.startActivity(intent);
	}
	
	private TitleBar mTitleBar;
	
	private Handler mHandler = new Handler();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_layout);
		setUpView();
	}

	private void setUpView(){
		mTitleBar = (TitleBar) findViewById(R.id.titlebar);
		mTitleBar.setTitle(getResources().getString(R.string.user_about));
		mTitleBar.setBackListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
}
