package com.xiaomei.module.user.center;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.xiaomei.BaseActiviy;
import com.xiaomei.R;
import com.xiaomei.widget.TitleBar;

public class OrderDetailsActivity extends BaseActiviy implements View.OnClickListener{
	
	public static void startActivity(Context context){
		Intent intent = new Intent(context,OrderDetailsActivity.class);
		context.startActivity(intent);
	}
	
	private TitleBar mTitlBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_order_details_layout);
		setUpView();
	}
	
	private void setUpView(){
		mTitlBar = (TitleBar) findViewById(R.id.titlebar);
		mTitlBar.setTitle(getResources().getString(R.string.order_details));
		mTitlBar.setBackListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case 1:
			
			break;

		default:
			break;
		}
	}
}
