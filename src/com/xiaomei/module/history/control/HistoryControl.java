package com.xiaomei.module.history.control;

import com.yuekuapp.BaseControl;
import com.yuekuapp.annotations.AsynMethod;
import com.yuekuapp.proxy.MessageProxy;

public class HistoryControl extends BaseControl {

	public HistoryControl(MessageProxy mMethodCallBack) {
		super(mMethodCallBack);
		
	}
	
	@AsynMethod
	public void getHistoryListAsyn(){
		// TODO
		return ;
	}
	
}
