package com.xiaomei.module.user.center;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.xiaomei.R;
import com.xiaomei.widget.TitleBar;
import com.yuekuapp.BaseActivity;

public class UserInfoActivity extends BaseActivity {

	public static void startActivity(Context context){
		Intent intent = new Intent(context,UserInfoActivity.class);
		context.startActivity(intent);
	}
	
	private TitleBar mTitlebar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_info_layout);
		initView();
	}
	
	private void initView(){
		mTitlebar = (TitleBar) findViewById(R.id.titlebar);
		mTitlebar.setTitle("用戶信息");
		mTitlebar.setBackListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
}
