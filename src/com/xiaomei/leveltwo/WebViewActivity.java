package com.xiaomei.leveltwo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.xiaomei.R;
import com.yuekuapp.BaseActivity;
import com.yuekuapp.BaseControl;

public class WebViewActivity extends BaseActivity<BaseControl> {
	
	public static void startActivity(Context context,String url){
		Intent intent = new Intent(context,WebViewActivity.class);
		intent.putExtra("url", url);
		context.startActivity(intent);
	}
	
	private WebView mWebView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activtiy_webview);
		mWebView = (WebView) findViewById(R.id.webview);
		mWebView.getSettings().setJavaScriptEnabled(true);  
		mWebView.setWebViewClient(new WebViewClient(){       
            public boolean shouldOverrideUrlLoading(WebView view, String url) {       
            	 view.loadUrl(url);    
                return true;       
            }       
		});   
		mWebView.loadUrl(getIntent().getStringExtra("url"));
	}

}
