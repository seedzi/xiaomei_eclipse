package com.xiaomei.phonegap;

import org.apache.cordova.CordovaWebView;
import org.apache.cordova.api.CallbackContext;
import org.apache.cordova.api.CordovaInterface;
import org.apache.cordova.api.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;

import com.xiaomei.module.user.center.OrderDetailsActivity;

public class XMCommonUtil extends CordovaPlugin{
	
	private Activity ac;
	
	@Override
	public boolean execute(String action, JSONArray args,
			CallbackContext callbackContext) throws JSONException {
		android.util.Log.d("111", "execute1  action = "  + action + ",args = "  + args);
		return orderSubmit(args);
	}
	
	
	@Override
	public void initialize(CordovaInterface cordova, CordovaWebView webView) {
		ac = cordova.getActivity();
		android.util.Log.d("111", "initialize");
		super.initialize(cordova, webView);
	}


	@Override
	public boolean execute(String action, String rawArgs,
			CallbackContext callbackContext) throws JSONException {
		android.util.Log.d("111", "execute2  action = "  + action + ",rawArgs = " + rawArgs);
		return super.execute(action, rawArgs, callbackContext);
	}
	// ================================= api ========================================
	
	private boolean orderSubmit( JSONArray args){
		String goodsId;
		try {
			goodsId = args.getJSONObject(0).getString("goods_id");
			OrderDetailsActivity.startActivity(ac, goodsId);
		} catch (Exception e) {
			return false;
		}
		return false;
	}
}
