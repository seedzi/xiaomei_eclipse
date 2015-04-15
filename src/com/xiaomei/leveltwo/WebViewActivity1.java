package com.xiaomei.leveltwo;

import org.apache.cordova.DroidGap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.xiaomei.api.HttpUrlManager;
import com.xiaomei.test.XMCommonUtil;

public class WebViewActivity1 extends DroidGap {


	public static void startActivity(Context context,String id){
		Intent intent = new Intent(context,WebViewActivity1.class);
		intent.putExtra("id", id);
		context.startActivity(intent);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.init();     
		String url = HttpUrlManager.GOODS_DETAIL_URL+"?goods_id=" + getIntent().getStringExtra("id");
		android.util.Log.d("111", "url = " + url);
		XMCommonUtil cna = new XMCommonUtil(this, appView);     
	    appView.addJavascriptInterface(cna, "XMCommonUtil");       
		super.loadUrl(url);
	}
}
