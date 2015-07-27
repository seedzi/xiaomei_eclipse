package com.xiaomei.yanyu.util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;

public class YanYuUtils {
	
	public static boolean isConnect(Context context){
		ConnectivityManager con=(ConnectivityManager)context.getSystemService(Activity.CONNECTIVITY_SERVICE);  
		boolean wifi=con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();  
		boolean internet=con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();  
		return wifi|internet;
	}

}
