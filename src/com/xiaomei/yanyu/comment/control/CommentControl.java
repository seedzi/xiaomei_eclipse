package com.xiaomei.yanyu.comment.control;

import com.xiaomei.yanyu.XiaoMeiApplication;
import com.xiaomei.yanyu.bean.NetResult;
import com.xiaomei.yanyu.util.UserUtil;
import com.yuekuapp.BaseControl;
import com.yuekuapp.annotations.AsynMethod;
import com.yuekuapp.proxy.MessageProxy;

public class CommentControl extends BaseControl {

	public CommentControl(MessageProxy mMethodCallBack) {
		super(mMethodCallBack);
	}

	@AsynMethod
	public void addComment(final String orderId,final String goodsId, final String comment,final String markEffect,
			final String markService, final String markEnvironment) {
		
		try {
			NetResult netResult = XiaoMeiApplication
					.getInstance()
					.getApi()
					.actionShareComment(orderId, goodsId, comment, markService,
							markEffect, markEnvironment,
							UserUtil.getUser().getToken());
			if(netResult != null && "0".equals(netResult.getCode())){
				sendMessage("addCommentCallBack");
			}else{
				sendMessage("addCommentExceptionCallBack");
			}
		} catch (Exception e) {
			sendMessage("addCommentExceptionCallBack");
			e.printStackTrace();
		}

		/*
			new Thread(new Runnable() {
				@Override
				public void run() {
		try {
			NetResult netResult = XiaoMeiApplication
					.getInstance()
					.getApi()
					.actionShareComment(orderId, goodsId, comment, markService,
							markEffect, markEnvironment,
							UserUtil.getUser().getToken());
			if(netResult != null && "0".equals(netResult.getCode())){
				sendMessage("addCommentCallBack");
			}else{
				sendMessage("addCommentExceptionCallBack");
			}
		} catch (Exception e) {
			sendMessage("addCommentExceptionCallBack");
			e.printStackTrace();
		}
					
				}
			}).start();*/

	}
	
}
