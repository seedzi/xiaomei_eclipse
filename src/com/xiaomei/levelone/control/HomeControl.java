package com.xiaomei.levelone.control;

import java.util.List;

import android.util.Log;

import com.xiaomei.XiaoMeiApplication;
import com.xiaomei.bean.Section;
import com.xiaomei.levelone.model.HomeListModel;
import com.yuekuapp.BaseControl;
import com.yuekuapp.annotations.AsynMethod;
import com.yuekuapp.proxy.MessageProxy;

public class HomeControl extends BaseControl {

	private HomeListModel mModel;
	
	public HomeControl(MessageProxy mMethodCallBack) {
		super(mMethodCallBack);
		mModel = new HomeListModel();
	}
	
	@AsynMethod
	public void getHomeListEntityAsyn(){
		Log.d("111", "HomeControl =" +Thread.currentThread().getName());
		try {
			List<Section> listNet = XiaoMeiApplication.getInstance().getApi().getHomeListFromNet();
			if(listNet!=null && listNet.size()>0){
				mModel.setList(listNet);
				sendMessage("getHomeListEntityAsynCallBack");
			}else{
				sendMessage("getHomeListEntityAsynCallBackNull");
			}
		} catch (Exception e) {
			sendMessage("getHomeListEntityAsynCallBackException");
			e.printStackTrace();
		} finally {
		}
	}
	
	@AsynMethod
	public void getMoreListDataFromNetAysn(){
		try {
			XiaoMeiApplication.getInstance().getApi().getBeatifulRingListFromNet();
			List<Section> listNet = XiaoMeiApplication.getInstance().getApi().getHomeListFromNet();
			if(listNet!=null && listNet.size()>0){
				mModel.setList(listNet);
				sendMessage("getHomeListEntityMoreAsynCallBack");
			}else{
				sendMessage("getHomeListEntityMoreAsynCallBackException");
			}
		} catch (Exception e) {
			sendMessage("getHomeListEntityMoreAsynCallBackException");
			e.printStackTrace();
		} finally {
		}
	}
	
	public List<Section> getSectionList(){
		return mModel.getList();
	}
	
	

}
