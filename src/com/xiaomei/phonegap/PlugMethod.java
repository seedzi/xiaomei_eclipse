package com.xiaomei.phonegap;

import org.apache.cordova.CordovaWebView;
import org.apache.cordova.DroidGap;

import com.umeng.socialize.utils.Log;

import android.webkit.WebView;

public class PlugMethod {
	
	private CordovaWebView webView;
	
	private DroidGap droidGap;

	public PlugMethod(DroidGap droidGap,CordovaWebView webView ) {
		super();
		this.webView = webView;
		this.droidGap = droidGap;
	}

	public void updateApp(final String path){
		Log.d("111", "path = " + path);
	}
	

}
