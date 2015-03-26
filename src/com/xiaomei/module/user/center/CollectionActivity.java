package com.xiaomei.module.user.center;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.xiaomei.BaseActiviy;
import com.xiaomei.R;

public class CollectionActivity extends BaseActiviy {
	
	public static void startActivity(Context context){
		Intent intent = new Intent(context,CollectionActivity.class);
		context.startActivity(intent);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_collection_layout);
	}
	
	
	
}
