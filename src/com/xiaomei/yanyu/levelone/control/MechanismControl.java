package com.xiaomei.yanyu.levelone.control;

import java.util.List;

import com.xiaomei.yanyu.XiaoMeiApplication;
import com.xiaomei.yanyu.bean.Merchant;
import com.xiaomei.yanyu.levelone.model.MechanismModel;
import com.yuekuapp.BaseControl;
import com.yuekuapp.annotations.AsynMethod;
import com.yuekuapp.proxy.MessageProxy;

public class MechanismControl extends BaseControl {
	
	private final String PERPAGE = "10";
	
	private MechanismModel mModel;

	public MechanismControl(MessageProxy mMethodCallBack) {
		super(mMethodCallBack);
		mModel = new MechanismModel();
	}
	
	@AsynMethod
	public void getMechanismListAsyn(){
		try {
			mModel.setData(XiaoMeiApplication.getInstance().getApi().getMechanismListFromNet("1",PERPAGE));
			sendMessage("getMechanismLismListCallBack");
		} catch (Exception e) {
			sendMessage("getMechanismListExceptionCallBack");
			return;
		}
	}
	
	@AsynMethod
	public void getMechanismListMoreAsyn(){
		try {
			mModel.increaePage();
			List<Merchant> data = XiaoMeiApplication.getInstance().getApi().getMechanismListFromNet(String.valueOf(mModel.getPage()),PERPAGE);
			if(data==null || data.size()==0){
				mModel.reducePage();
			}
			mModel.setData(data );
		} catch (Exception e) {
			mModel.reducePage();
			sendMessage("getMechanismListMoreExceptionCallBack");
			return;
		}
		sendMessage("getMechanismLismListMoreCallBack");
	}
	
	public MechanismModel getModel(){
		return mModel;
	}

}
