package com.xiaomei.leveltwo;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.xiaomei.BaseActiviy;
import com.xiaomei.R;
import com.xiaomei.widget.TitleBar;

public class MechanismDetailActivity extends BaseActiviy {

	public static void startActivity(Context context,String url){
		Intent intent = new Intent(context,MechanismDetailActivity.class);
		intent.putExtra("url", url);
		context.startActivity(intent);
	}
	
	private WebView mWebView;
	private TitleBar mTitleBar;
	/**Loading 控件*/
	private View mLoadingView; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mechanism_detail_layout);
		initView();
	}
	
	private void initView(){
		mTitleBar = (TitleBar) findViewById(R.id.titlebar);
		mTitleBar.setTitle("详情");
		mTitleBar.setBackListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		mLoadingView = findViewById(R.id.loading_layout);
		
		mWebView = (WebView) findViewById(R.id.webview);
		String url = getIntent().getStringExtra("url");
		if(!url.contains("http://"))
			url = "http://"+url;
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new MyWebChromeClient());  
        mWebView.setWebViewClient(new WebViewClient(){
        	@Override
        	public boolean shouldOverrideUrlLoading(WebView view,
        			String url) {
        		mWebView.loadUrl(url);
        		return true;
        	}
        });
        mWebView.loadUrl(url);
	}
	
	
	// ============================== 进度条 ==========================================
	private class MyWebChromeClient extends WebChromeClient {
		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			if (newProgress < 100) {
				showProgress();
			} else {
				dissProgress();
			}
		}

		@Override
		public void onReceivedTitle(WebView view, String title) {
			super.onReceivedTitle(view, title);
		}
	}
	
	private void showProgress(){
		mLoadingView.setVisibility(View.VISIBLE);
		AnimationDrawable animationDrawable =  (AnimationDrawable) ((ImageView)mLoadingView.findViewById(R.id.iv)).getDrawable();
		if(!animationDrawable.isRunning())
			animationDrawable.start();
		mWebView.setVisibility(View.GONE);
	}
	
	private void dissProgress(){
		mLoadingView.setVisibility(View.GONE);
		mWebView.setVisibility(View.VISIBLE);
	}
}
