package com.xiaomei.yanyu.Payment;


import android.app.Activity;

import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.xiaomei.yanyu.bean.WechatBean;

public class WeiXinPayManager {
	
	private static WeiXinPayManager mInstance;
	
	public static WeiXinPayManager getInstance(){
		if(mInstance == null)
			mInstance = new WeiXinPayManager();
		return mInstance;
	}
	
	private WeiXinPayManager(){}
	
	private String appId;
	
	public String getAppId(){
		return appId;
	}
	
	public void pay(WechatBean wechatBean,Activity ac){
		final IWXAPI msgApi = WXAPIFactory.createWXAPI(ac, null);
		PayReq	req = new PayReq();
		appId =  wechatBean.getAppid();
		req.appId = wechatBean.getAppid();
		req.partnerId = wechatBean.getPartnerid();
		req.prepayId = wechatBean.getPrepayid();
		req.packageValue = wechatBean.getPackageTxt();
		req.nonceStr = wechatBean.getNoncestr();
		req.timeStamp = wechatBean.getTimestamp();
		req.sign = wechatBean.getSign();
		
		msgApi.registerApp(wechatBean.getAppid());
		msgApi.sendReq(req);
		android.util.Log.d("111", "wechatBean = " + wechatBean);
	}
	

}
