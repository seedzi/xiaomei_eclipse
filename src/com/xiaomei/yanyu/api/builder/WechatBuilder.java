package com.xiaomei.yanyu.api.builder;

import org.json.JSONException;
import org.json.JSONObject;

import com.xiaomei.yanyu.DebugRelease;
import com.xiaomei.yanyu.bean.WechatBean;

public class WechatBuilder extends AbstractJSONBuilder<WechatBean> {

	@Override
	protected WechatBean builder(JSONObject jsonObject) throws JSONException {
	    if(DebugRelease.isDebug)
	        android.util.Log.d("json", jsonObject.toString());
		if(!jsonObject.has("code") || jsonObject.getInt("code")!=0){
		    if(DebugRelease.isDebug)
		        if(DebugRelease.isDebug)
		            android.util.Log.d("111", "null");
			return null;
		}
		if(jsonObject.has("msg"))
			jsonObject = jsonObject.getJSONObject("msg");
		WechatBean wechatBean = new WechatBean();
		if(jsonObject.has("sign"))
			wechatBean.setSign(jsonObject.getString("sign"));
		if(jsonObject.has("timestamp"))
			wechatBean.setTimestamp(jsonObject.getString("timestamp"));
		if(jsonObject.has("package"))
			wechatBean.setPackageTxt(jsonObject.getString("package"));
		if(jsonObject.has("noncestr"))
			wechatBean.setNoncestr(jsonObject.getString("noncestr"));
		if(jsonObject.has("partnerid"))
			wechatBean.setPartnerid(jsonObject.getString("partnerid"));
		if(jsonObject.has("appid"))
			wechatBean.setAppid(jsonObject.getString("appid"));
		if(jsonObject.has("prepayid"))
			wechatBean.setPrepayid(jsonObject.getString("prepayid"));
		if(DebugRelease.isDebug)
		    android.util.Log.d("111", "wechatBean");
		return wechatBean;
	}
/*
{
    "msg": {
        "sign": "AF2544440E3062E94D66814F47C33ECD",
        "timestamp": 1430550279,
        "package": "Sign=WXPay",
        "noncestr": "bxlk4pvmnyvagk0zkcxynrd5z6q7xiwh",
        "partnerid": "1237015302",
        "appid": "wx67f54f6d2c0d66c8",
        "prepayid": "wx2015050215044035dd3556680046729158"
    },
    "code": 0
}
		 */
}
