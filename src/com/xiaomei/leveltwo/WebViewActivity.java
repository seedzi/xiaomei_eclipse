package com.xiaomei.leveltwo;

import java.util.concurrent.ExecutorService;

import org.apache.cordova.CordovaWebView;
import org.apache.cordova.DroidGap;
import org.apache.cordova.api.CordovaInterface;
import org.apache.cordova.api.CordovaPlugin;

import com.xiaomei.api.HttpUrlManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


public class WebViewActivity extends DroidGap{
//public class WebViewActivity extends CDV {
	

	public static void startActivity(Context context,String id){
		Intent intent = new Intent(context,WebViewActivity.class);
		intent.putExtra("id", id);
		context.startActivity(intent);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		  super.init();    
//		super.loadUrl(getIntent().getStringExtra("url"));
		String url = HttpUrlManager.GOODS_DETAIL_URL+"?goods_id=" + getIntent().getStringExtra("id");
		android.util.Log.d("111", "url = " + url);
		super.loadUrl(url);
	}
	
	/*
	private CordovaWebView mWebView;
	
	private TitleBar mTitleBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activtiy_webview);
		
		mTitleBar = (TitleBar) findViewById(R.id.title_bar);
		mTitleBar.setTitle("详情");
		mTitleBar.setBackListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		mWebView = (CordovaWebView) findViewById(R.id.tutorialView);
		Config.init(this);
		mWebView.loadUrl(Config.getStartUrl());
		/*
		mWebView.getSettings().setJavaScriptEnabled(true);  
		mWebView.setWebViewClient(new WebViewClient(){       
            public boolean shouldOverrideUrlLoading(WebView view, String url) {       
            	 view.loadUrl(url);    
                return true;       
            }       
		});   
		mWebView.loadUrl(getIntent().getStringExtra("url"));
		
	}
*/
/*
	@Override
	public Activity getActivity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ExecutorService getThreadPool() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object onMessage(String arg0, Object arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setActivityResultCallback(CordovaPlugin arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startActivityForResult(CordovaPlugin arg0, Intent arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}*/

}
