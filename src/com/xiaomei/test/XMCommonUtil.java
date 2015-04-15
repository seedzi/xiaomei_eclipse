package com.xiaomei.test;

import org.apache.cordova.DroidGap;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.webkit.WebView;
import android.widget.Toast;

public class XMCommonUtil {
    private WebView mAppView;
    private DroidGap mGap;
    public XMCommonUtil(DroidGap gap, WebView view)
    {
        mAppView = view;
        mGap = gap;
    }   
    public String orderSubmit(String s,String ss){
    	android.util.Log.d("111", "orderSubmit");
    	Toast.makeText(mAppView.getContext(), "ssss", 0).show();
    	TelephonyManager tm = (TelephonyManager) mGap.getSystemService(Context.TELEPHONY_SERVICE);
        String imeiId = tm.getDeviceId();      
        return imeiId;
    }
}
