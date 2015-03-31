package com.xiaomei.levelone.control;

import com.yuekuapp.BaseControl;
import com.yuekuapp.annotations.AsynMethod;
import com.yuekuapp.proxy.MessageProxy;

public class BeautifulRingControl extends BaseControl {

	public BeautifulRingControl(MessageProxy mMethodCallBack) {
		super(mMethodCallBack);
	}

	@AsynMethod
	public void getListDataFromNetAysn(){
		// TODO
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		sendMessage("xx");
	}
	
}
