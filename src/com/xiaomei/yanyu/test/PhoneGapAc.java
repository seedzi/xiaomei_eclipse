package com.xiaomei.yanyu.test;

import org.apache.cordova.DroidGap;
import org.apache.cordova.api.PluginManager;

import com.xiaomei.yanyu.phonegap.PlugMethod;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class PhoneGapAc extends DroidGap {
	
	public static void startActivity(Context context){
		Intent intent = new Intent(context,PhoneGapAc.class);
		context.startActivity(intent);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.init();
		appView.getSettings().setJavaScriptEnabled(true);
		appView.addJavascriptInterface(new PlugMethod(this, appView), "SM");
		super.loadUrl("file:///android_asset/www/index.html",2000);
	}

}
