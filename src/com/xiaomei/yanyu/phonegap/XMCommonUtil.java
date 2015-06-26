package com.xiaomei.yanyu.phonegap;

import org.apache.cordova.CordovaWebView;
import org.apache.cordova.api.CallbackContext;
import org.apache.cordova.api.CordovaInterface;
import org.apache.cordova.api.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.widget.Toast;

import com.xiaomei.yanyu.XiaoMeiApplication;
import com.xiaomei.yanyu.comment.CommentListActivity;
import com.xiaomei.yanyu.leveltwo.BuildOrderActivity;
import com.xiaomei.yanyu.module.user.LoginAndRegisterActivity;
import com.xiaomei.yanyu.module.user.center.OrderDetailsActivity;
import com.xiaomei.yanyu.util.UserUtil;

public class XMCommonUtil extends CordovaPlugin{
	
	private Activity ac;
//	04-23 12:10:07.475: D/111(22804): execute2  action = openCommentView,rawArgs = [{"itemid":"1055","type":"goods"}]
//	        04-23 12:10:07.480: D/111(22804): execute1  action = openCommentView,args = [{"type":"goods","itemid":"1055"}]
	@Override
	public boolean execute(String action, JSONArray args,
			CallbackContext callbackContext) throws JSONException {
		android.util.Log.d("111", "execute1  action = "  + action + ",args = "  + args);
		if(action.equals("orderSubmit")){
		      return orderSubmit(args);
		}else {
		    openCommentView(args);
		    return false;
		}

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
	          if(UserUtil.getUser()==null){
	        	  LoginAndRegisterActivity.startActivity(ac, true);
	        	  ac.finish();
	                return false;
	          }
			goodsId = args.getJSONObject(0).getString("itemid");
			BuildOrderActivity.startActivity(ac, goodsId);
//			OrderDetailsActivity.startActivity(ac, goodsId);
		} catch (Exception e) {
			return false;
		}
		return false;
	}
	
	public void openCommentView( JSONArray args){
	       String itemid;
	       String type;
	        try {
	            itemid = args.getJSONObject(0).getString("itemid");
	            type = args.getJSONObject(0).getString("type");
	            if("goods".equals(type)){
	            	CommentListActivity.startActivity(XiaoMeiApplication.getInstance().getCurrentActivity(),type, itemid,false);
	            }else{
	            	CommentListActivity.startActivity(XiaoMeiApplication.getInstance().getCurrentActivity(),type, itemid,true);
	            }
	        } catch (Exception e) {
	        }
	}
}
