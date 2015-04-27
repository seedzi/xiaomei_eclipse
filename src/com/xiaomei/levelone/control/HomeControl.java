package com.xiaomei.levelone.control;

import java.util.List;
import com.xiaomei.XiaoMeiApplication;
import com.xiaomei.bean.Section;
import com.xiaomei.levelone.model.HomeListModel;
import com.yuekuapp.BaseControl;
import com.yuekuapp.annotations.AsynMethod;
import com.yuekuapp.proxy.MessageProxy;

public class HomeControl extends BaseControl {
	
	private final String PERPAGE = "10";
	

	private HomeListModel mModel;
	
	public HomeControl(MessageProxy mMethodCallBack) {
		super(mMethodCallBack);
		mModel = new HomeListModel();
	}
	
	@AsynMethod
	public void getHomeListEntityAsyn(){
		try {
			List<Section> listNet = XiaoMeiApplication.getInstance().getApi().getHomeListFromNet("1",PERPAGE);
			if(listNet!=null && listNet.size()>0){
				mModel.setList(listNet);
				mModel.setPageNum(1); //设置当前页面为1
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
			mModel.increasePageNum();//将当前页面++
			List<Section> listNet = XiaoMeiApplication.getInstance().getApi().getHomeListFromNet(String.valueOf(mModel.getPageNum()),PERPAGE);
			if(listNet!=null && listNet.size()>0){
				mModel.setList(listNet);
				sendMessage("getHomeListEntityMoreAsynCallBack");
			}else{
				mModel.reducePageNum();
				sendMessage("getHomeListEntityMoreAsynCallBackException");
			}
		} catch (Exception e) {
			mModel.reducePageNum();
			sendMessage("getHomeListEntityMoreAsynCallBackException");
			e.printStackTrace();
		} finally {
		}
	}
	
	public List<Section> getSectionList(){
		return mModel.getList();
	}

}
