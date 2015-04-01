package com.xiaomei.levelone.control;

import com.xiaomei.XiaoMeiApplication;
import com.yuekuapp.BaseControl;
import com.yuekuapp.annotations.AsynMethod;
import com.yuekuapp.proxy.MessageProxy;

public class BeautifulRingControl extends BaseControl {

	public BeautifulRingControl(MessageProxy mMethodCallBack) {
		super(mMethodCallBack);
	}

	@AsynMethod
	public void getListDataFromNetAysn(){
		try {
			XiaoMeiApplication.getInstance().getApi().getBeatifulRingListFromNet();
		} catch (Exception e) {
			e.printStackTrace();
		}
		sendMessage("xx");
	}
	
}
