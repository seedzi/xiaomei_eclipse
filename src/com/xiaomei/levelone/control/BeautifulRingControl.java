package com.xiaomei.levelone.control;

import java.util.List;


import com.xiaomei.XiaoMeiApplication;
import com.xiaomei.bean.BeautifulRing;
import com.xiaomei.levelone.model.BeautifulRingModel;
import com.yuekuapp.BaseControl;
import com.yuekuapp.annotations.AsynMethod;
import com.yuekuapp.proxy.MessageProxy;

public class BeautifulRingControl extends BaseControl {
	
	private BeautifulRingModel mModel;

	public BeautifulRingControl(MessageProxy mMethodCallBack) {
		super(mMethodCallBack);
		mModel = new BeautifulRingModel();
	}

	@AsynMethod
	public void getListDataFromNetAysn(){
		try {
			mModel.setData(XiaoMeiApplication.getInstance().getApi().getBeatifulRingListFromNet());
		} catch (Exception e) {
			sendMessage("getListDataFromNetAysnExceptionCallBack");
			e.printStackTrace();
		} finally {
		}
		sendMessage("getListDataFromNetAysnCallBack");
	}
	
	@AsynMethod
	public void getMoreListDataFromNetAysn(){
		try {
			mModel.getData().addAll(XiaoMeiApplication.getInstance().getApi().getBeatifulRingListFromNet());
		} catch (Exception e) {
			sendMessage("getMoreListDataFromNetAysnExceptionCallBack");
			e.printStackTrace();
		} finally{
			
		}
		sendMessage("getMoreListDataFromNetAysnCallBack");
	}
	
	public List<BeautifulRing> getData(){
		return mModel.getData();
	}
	
}
