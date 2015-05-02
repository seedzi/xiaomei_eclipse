package com.xiaomei.yanyu.leveltwo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.cordova.Config;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.api.CordovaInterface;
import org.apache.cordova.api.CordovaPlugin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.AbstractActivity;
import com.xiaomei.yanyu.api.HttpUrlManager;
import com.xiaomei.yanyu.widget.TitleBar;

public class GoodsDetailActivity extends AbstractActivity implements CordovaInterface{
	
	
	public static void startActivity(Context context,String url){
		Intent intent = new Intent(context,GoodsDetailActivity.class);
		intent.putExtra("url", url);
		context.startActivity(intent);
	}
	
	private CordovaWebView mCordovaWebView;
	private CordovaPlugin activityResultCallback;
	private Object activityResultKeepRunning;
	private Object keepRunning;
	private final ExecutorService threadPool =Executors.newCachedThreadPool(); 
	
	private TitleBar mTitleBar;
	/**Loading 控件*/
	private View mLoadingView; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_goods_detail_layout);
		initView();
		initCordova();
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
	}
	
	private void initCordova(){
		mCordovaWebView = (CordovaWebView) findViewById(R.id.tutoria_view);
		String url = getIntent().getStringExtra("url");
        Config.init(this);
        Config.addWhiteListEntry(HttpUrlManager.GOODS_DETAIL_URL, true);
        Config.addWhiteListEntry(HttpUrlManager.MECHANISM_DETAIL_URL, true);
        mCordovaWebView.setWebChromeClient(new MyWebChromeClient());  
        android.util.Log.d("111", "url = " + url);
		mCordovaWebView.loadUrl(url);
	}
	

	@Override
	public Activity getActivity() {
	    return this;  
	}

	@Override
	public ExecutorService getThreadPool() {
		return threadPool;
	}

	@Override
	public Object onMessage(String id, Object data) {
		if ("exit".equals(id)) {
            super.finish();
        }
        return null;
	}
	
	@Override
	public void setActivityResultCallback(CordovaPlugin plugin) {
	    this.activityResultCallback = plugin;
	}

	@Override
	public void startActivityForResult(CordovaPlugin command, Intent intent, int requestCode) {
        this.activityResultCallback = command;
        this.activityResultKeepRunning = this.keepRunning;
        // If multitasking turned on, then disable it for activities that return results
        if (command != null) {
            this.keepRunning = false;
        }
        // Start activity
        super.startActivityForResult(intent, requestCode);
		
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
	    super.onActivityResult(requestCode, resultCode, intent);
	    CordovaPlugin callback = this.activityResultCallback;
	    if (callback != null) {
	        callback.onActivityResult(requestCode, resultCode, intent);
	    }
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();  
		if (mCordovaWebView != null) {  
			mCordovaWebView.handleDestroy();  
		}  
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
		mCordovaWebView.setVisibility(View.GONE);
	}
	
	private void dissProgress(){
		mLoadingView.setVisibility(View.GONE);
		mCordovaWebView.setVisibility(View.VISIBLE);
	}
}
