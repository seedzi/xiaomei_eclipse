package com.xiaomei.leveltwo;

import java.util.concurrent.ExecutorService;

import org.apache.cordova.Config;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.DroidGap;
import org.apache.cordova.api.CordovaInterface;
import org.apache.cordova.api.CordovaPlugin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.xiaomei.R;
import com.xiaomei.widget.TitleBar;
import com.yuekuapp.BaseActivity;
import com.yuekuapp.BaseControl;

public class WebViewActivity extends DroidGap{
//public class WebViewActivity extends CDV {
	

	public static void startActivity(Context context,String url){
		Intent intent = new Intent(context,WebViewActivity.class);
		intent.putExtra("url", url);
		context.startActivity(intent);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		super.loadUrl(getIntent().getStringExtra("url"));
		super.loadUrl("file:///android_asset/www/index.html");
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
