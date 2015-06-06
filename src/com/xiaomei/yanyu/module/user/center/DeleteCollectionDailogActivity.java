package com.xiaomei.yanyu.module.user.center;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.xiaomei.yanyu.AbstractActivity;
import com.xiaomei.yanyu.R;

public class DeleteCollectionDailogActivity extends AbstractActivity implements View.OnClickListener{
	
	public static void startActivity(Activity ac,int count ){
		Intent intent = new Intent(ac, DeleteCollectionDailogActivity.class);
		intent.putExtra("count", String.valueOf(count));
		ac.startActivityForResult(intent, 1);
	}
	
	private View mCancel;
	
	private TextView mConfim;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dialog_layout);
		setUpView();
	}

	private void setUpView(){
		String count = getIntent().getStringExtra("count");
		mCancel = findViewById(R.id.cancel);
		mConfim = (TextView) findViewById(R.id.confim);
		mConfim.setText("确定删除"+ count +"个收藏");
		mCancel.setOnClickListener(this);
		mConfim.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.cancel:
			finish();
			break;
		case R.id.confim:
			setResult(RESULT_OK);
			finish();
			break;
		default:
			break;
		}
	}
}
