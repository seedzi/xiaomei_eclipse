package com.xiaomei.test;

import org.apache.cordova.DroidGap;

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
		super.loadUrl("file:///android_asset/www/index.html");
	}

}
