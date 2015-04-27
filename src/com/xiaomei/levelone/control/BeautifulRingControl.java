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
	
	private final int PERPAGE = 10;

	public BeautifulRingControl(MessageProxy mMethodCallBack) {
		super(mMethodCallBack);
		mModel = new BeautifulRingModel();
	}

	@AsynMethod
	public void getListDataFromNetAysn(){
		try {
			mModel.setPage(1);
			mModel.setData(XiaoMeiApplication.getInstance().getApi().getBeatifulRingListFromNet(String.valueOf(mModel.getPage()),String.valueOf(PERPAGE)));
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
			mModel.increaePage();
			List<BeautifulRing>  data = XiaoMeiApplication.getInstance().getApi().getBeatifulRingListFromNet(String.valueOf(mModel.getPage()),String.valueOf(PERPAGE));
			mModel.getData().addAll(data);
			if(data==null || data.size() == 0)
				mModel.reducePage();
		} catch (Exception e) {
			sendMessage("getMoreListDataFromNetAysnExceptionCallBack");
			mModel.reducePage();
			e.printStackTrace();
		} finally{
		}
		sendMessage("getMoreListDataFromNetAysnCallBack");
	}
	
	public List<BeautifulRing> getData(){
		return mModel.getData();
	}
	
}
