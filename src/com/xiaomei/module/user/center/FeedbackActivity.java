package com.xiaomei.module.user.center;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.xiaomei.BaseActiviy;
import com.xiaomei.R;
import com.xiaomei.widget.TitleBar;

public class FeedbackActivity extends BaseActiviy {
	
	
	public static void startActivity(Context context){
		Intent intent = new Intent(context,FeedbackActivity.class);
		context.startActivity(intent);
	}
	
	private TitleBar mTitleBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback_layout);
		setUpView();
	}
	
	private void setUpView(){
		mTitleBar = (TitleBar) findViewById(R.id.titlebar);
		mTitleBar.setTitle(getResources().getString(R.string.user_feedback));
		mTitleBar.setBackListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

}
