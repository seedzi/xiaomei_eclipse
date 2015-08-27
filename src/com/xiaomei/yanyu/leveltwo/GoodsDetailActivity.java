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
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.AbstractActivity;
import com.xiaomei.yanyu.api.HttpUrlManager;
import com.xiaomei.yanyu.leveltwo.control.LeveltwoControl;
import com.xiaomei.yanyu.module.user.LoginAndRegisterActivity;
import com.xiaomei.yanyu.util.UserUtil;
import com.xiaomei.yanyu.widget.TitleBar;

public class GoodsDetailActivity extends AbstractActivity<LeveltwoControl> implements CordovaInterface,View.OnClickListener{
	
	
	public static void startActivityWithTitle(Activity ac,String url,String title){
		Intent intent = new Intent(ac,GoodsDetailActivity.class);
		intent.putExtra("url", url);
		intent.putExtra("title", title);
		ac.startActivity(intent);
        ac.overridePendingTransition(R.anim.activity_slid_in_from_right, R.anim.activity_slid_out_no_change);
	}
	
	public static void startActivity(Activity ac,String url){
		Intent intent = new Intent(ac,GoodsDetailActivity.class);
		intent.putExtra("url", url);
		ac.startActivity(intent);
        ac.overridePendingTransition(R.anim.activity_slid_in_from_right, R.anim.activity_slid_out_no_change);
	}
	
	public static void startActivity(Activity ac,String url,String goodsid){
		Intent intent = new Intent(ac,GoodsDetailActivity.class);
		intent.putExtra("url", url);
		intent.putExtra("goodsid", goodsid);
		ac.startActivity(intent);
        ac.overridePendingTransition(R.anim.activity_slid_in_from_right, R.anim.activity_slid_out_no_change);
	}
	
	private CordovaWebView mCordovaWebView;
	private CordovaPlugin activityResultCallback;
	private Object activityResultKeepRunning;
	private Object keepRunning;
	private final ExecutorService threadPool =Executors.newCachedThreadPool(); 
	
	private TitleBar mTitleBar;
	/**Loading 控件*/
	private View mLoadingView; 
	
	private String goodsId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_goods_detail_layout);
		initView();
		initCordova();
		isCollection(goodsId);
	}
	
	/**是否收藏*/
	private void isCollection(String goodsid){
		mControl.isFav(goodsid);
	}
	
	private void initView(){
		mTitleBar = (TitleBar) findViewById(R.id.titlebar);
		mTitleBar.setTitle("详情");
		mTitleBar.setBackListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mCordovaWebView.canGoBack()){
					mCordovaWebView.goBack();
				}else{
					finish();
				}
			}
		});
		String title = getIntent().getStringExtra("title");
		if(!TextUtils.isEmpty(title))
			mTitleBar.setTitle(title);
			
		goodsId = getIntent().getStringExtra("goodsid");
		mTitleBar.findViewById(R.id.right_root).setVisibility(View.VISIBLE);
		mTitleBar.findViewById(R.id.comment).setVisibility(View.GONE);
		mTitleBar.findViewById(R.id.share).setVisibility(View.GONE);
		mTitleBar.findViewById(R.id.fav).setVisibility(View.GONE);
		if(!TextUtils.isEmpty(goodsId)){
			mTitleBar.findViewById(R.id.fav).setVisibility(View.VISIBLE);
			mTitleBar.findViewById(R.id.fav).setOnClickListener(this);
		}
		mLoadingView = findViewById(R.id.loading_layout);
	}
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (url.startsWith("tel:")) { 
                Intent intent = new Intent(Intent.ACTION_DIAL,
                        Uri.parse(url)); 
                startActivity(intent); 
        }else if(url.startsWith("http:") || url.startsWith("https:")) {
            view.loadUrl(url);
        }
        return true;
    }
	private void initCordova(){
		mCordovaWebView = (CordovaWebView) findViewById(R.id.tutoria_view);
		mCordovaWebView.getSettings().setBlockNetworkImage(true);
		mCordovaWebView.setWebViewClient(new WebViewClient(){
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				mCordovaWebView.getSettings().setBlockNetworkImage(false);
			}
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				if (url.startsWith("tel:")) {
					Intent intent = new Intent(Intent.ACTION_DIAL, Uri
							.parse(url));
					startActivity(intent);
					return true;
				} 
				return super.shouldOverrideUrlLoading(view, url);
			}
			
			
		});
		String url = getIntent().getStringExtra("url");
        Config.init(this);
        Config.addWhiteListEntry(HttpUrlManager.GOODS_DETAIL_URL, true);
        Config.addWhiteListEntry(HttpUrlManager.MERCHANT_DETAIL_URL, true);
        mCordovaWebView.setWebChromeClient(new MyWebChromeClient());  
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
	
	// ============================== CallBack ==========================================
	public void isFavCallBack(){
		if(mControl.getModel().ismIsFav()){
			mTitleBar.findViewById(R.id.fav).setActivated(true);;
//			mTitleBar.findViewById(R.id.fav).setOnClickListener(null);
		}else{
			mTitleBar.findViewById(R.id.fav).setActivated(false);
		}
	}
	
	public void actionUserFavCallBack(){
//		mTitleBar.findViewById(R.id.fav).setActivated(true);;
//		mTitleBar.findViewById(R.id.fav).setOnClickListener(null);
		mTitleBar.findViewById(R.id.fav).setEnabled(true);
	}
	
	public void actionUserFavExceptionCallBack(){
		mTitleBar.findViewById(R.id.fav).setEnabled(true);
	}
	
	public void isFavExceptionCallBack(){
		
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
	
	@Override
	public void onClick(View v) {
		if(UserUtil.getUser()==null){
			LoginAndRegisterActivity.startActivity(this, false);
			return;
		}
		int id = v.getId();
		if(id ==R.id.fav){
			if(mControl.getModel().ismIsFav()){
				mControl.actionUserFavRm(goodsId);
			}else{
				mControl.actionUserFavAdd(goodsId);
			}
			v.setEnabled(false);
		}
	}
}
