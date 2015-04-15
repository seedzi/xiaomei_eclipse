package com.xiaomei.leveltwo;

import com.xiaomei.api.HttpUrlManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class WebViewActivity2 extends Activity {
	
	public static void startActivity(Context context,String id){
		Intent intent = new Intent(context,WebViewActivity2.class);
		intent.putExtra("id", id);
		context.startActivity(intent);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		super.loadUrl(getIntent().getStringExtra("url"));
		String url = HttpUrlManager.GOODS_DETAIL_URL+"?goods_id=" + getIntent().getStringExtra("id");
		android.util.Log.d("111", "url = " + url);
	}
	

}
