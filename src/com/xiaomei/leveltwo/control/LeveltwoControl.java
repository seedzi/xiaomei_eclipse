package com.xiaomei.leveltwo.control;

import com.xiaomei.XiaoMeiApplication;
import com.xiaomei.leveltwo.model.LevelTwoModel;
import com.yuekuapp.BaseControl;
import com.yuekuapp.annotations.AsynMethod;
import com.yuekuapp.proxy.MessageProxy;

public class LeveltwoControl extends BaseControl {

	private LevelTwoModel mModel = new LevelTwoModel();
	
	public LeveltwoControl(MessageProxy mMethodCallBack) {
		super(mMethodCallBack);
	}
	
	public LevelTwoModel getModel(){
		return mModel;
	}
	
	// ============================== 圈子 ====================================
	@AsynMethod
	public void getDataAsyn(){
		try {
			mModel.setBeautifulRingDetail(XiaoMeiApplication.getInstance().getApi().getBeatifulRingDetailFromNet());
			if(mModel.getBeautifulRingDetail()!=null)
				sendMessage("getDataAsynCallBack");
			else
				sendMessage("getDataAsynExceptionCallBack");
		} catch (Exception e) {
			e.printStackTrace();
			sendMessage("getDataAsynExceptionCallBack");
			return;
		}
	}
	
	// ============================== 商品 ====================================
	@AsynMethod
	public void getGoodsDataAsyn(){
		try {
			mModel.setGoodsListList(XiaoMeiApplication.getInstance().getApi().getGoodsListFromNet());
			if(mModel.getGoodsList()!=null)
				sendMessage("getGoodsDataAsynCallBack");
			else
				sendMessage("getGoodsDataAsynExceptionCallBack");
		} catch (Exception e) {
			e.printStackTrace();
			sendMessage("getGoodsDataAsynExceptionCallBack");
			return;
		}
	}
}
