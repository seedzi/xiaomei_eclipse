package com.xiaomei.phonegap;

import org.apache.cordova.CordovaWebView;
import org.apache.cordova.api.CallbackContext;
import org.apache.cordova.api.CordovaInterface;
import org.apache.cordova.api.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

public class XMCommonUtil extends CordovaPlugin{
	@Override
	public boolean execute(String action, JSONArray args,
			CallbackContext callbackContext) throws JSONException {
		android.util.Log.d("111", "execute1  action = "  + action);
		return super.execute(action, args, callbackContext);
	}
	
	
	@Override
	public void initialize(CordovaInterface cordova, CordovaWebView webView) {
		android.util.Log.d("111", "initialize");
		super.initialize(cordova, webView);
	}


	@Override
	public boolean execute(String action, String rawArgs,
			CallbackContext callbackContext) throws JSONException {
		android.util.Log.d("111", "execute2  action = "  + action);
		return super.execute(action, rawArgs, callbackContext);
	}
}
