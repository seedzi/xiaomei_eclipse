package com.xiaomei.yanyu.levelone.control;

import java.util.List;

import com.xiaomei.yanyu.XiaoMeiApplication;
import com.xiaomei.yanyu.bean.Merchant;
import com.xiaomei.yanyu.levelone.model.MerchantModel;
import com.yuekuapp.BaseControl;
import com.yuekuapp.annotations.AsynMethod;
import com.yuekuapp.proxy.MessageProxy;

public class MerchantControl extends BaseControl {
	
	private final String PERPAGE = "10";
	
	private MerchantModel mModel;

	public MerchantControl(MessageProxy mMethodCallBack) {
		super(mMethodCallBack);
		mModel = new MerchantModel();
	}
	
	@AsynMethod
	public void getMerchantListAsyn(String country, String special){
		try {
			mModel.setData(XiaoMeiApplication.getInstance().getApi().getMerchantListFromNet(country, special, "1",PERPAGE));
			sendMessage("getMerchantLismListCallBack");
		} catch (Exception e) {
			sendMessage("getMerchantListExceptionCallBack");
			return;
		}
	}
	
	@AsynMethod
	public void getMerchantListMoreAsyn(String country, String special){
		try {
			mModel.increaePage();
			List<Merchant> data = XiaoMeiApplication.getInstance().getApi().getMerchantListFromNet(country, special, String.valueOf(mModel.getPage()),PERPAGE);
			if(data==null || data.size()==0){
				mModel.reducePage();
			}
			mModel.setData(data );
		} catch (Exception e) {
			mModel.reducePage();
			sendMessage("getMerchantListMoreExceptionCallBack");
			return;
		}
		sendMessage("getMerchantLismListMoreCallBack");
	}
	
	public MerchantModel getModel(){
		return mModel;
	}

}
