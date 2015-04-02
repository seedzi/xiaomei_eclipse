package com.xiaomei.levelone.control;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.xiaomei.XiaoMeiApplication;
import com.xiaomei.bean.Hospital;
import com.yuekuapp.BaseControl;
import com.yuekuapp.annotations.AsynMethod;
import com.yuekuapp.proxy.MessageProxy;

public class MechanismControl extends BaseControl {
	
	private List<Hospital> mData;

	public MechanismControl(MessageProxy mMethodCallBack) {
		super(mMethodCallBack);
		mData = new ArrayList<Hospital>();
	}
	
	@AsynMethod
	public void getMechanismListAsyn(){
		Log.d("111", "MechanismControl = " +Thread.currentThread().getName());
		try {
			mData.clear();
			mData.addAll(XiaoMeiApplication.getInstance().getApi().getMechanismListFromNet());
		} catch (Exception e) {
			sendMessage("getMechanismListExceptionCallBack");
			return;
		}
		sendMessage("getMechanismLismListCallBack");
	}
	
	public List<Hospital> getListData(){
		return mData;
	}

}
