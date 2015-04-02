package com.xiaomei.levelone.control;

import android.util.Log;

import com.android.dx.io.Code.Try;
import com.xiaomei.XiaoMeiApplication;
import com.yuekuapp.BaseControl;
import com.yuekuapp.annotations.AsynMethod;
import com.yuekuapp.proxy.MessageProxy;

public class MallControl extends BaseControl {

	public MallControl(MessageProxy mMethodCallBack) {
		super(mMethodCallBack);
	}
	
	@AsynMethod
	public void getMallListFromNetAsyn(){
		Log.d("111", "MallControl = " + Thread.currentThread().getName());
	}
}
