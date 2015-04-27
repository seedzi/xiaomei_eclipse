package com.xiaomei.levelone.control;

import java.util.List;

import android.util.Log;

import com.android.dx.io.Code.Try;
import com.xiaomei.XiaoMeiApplication;
import com.xiaomei.api.exception.XiaoMeiCredentialsException;
import com.xiaomei.api.exception.XiaoMeiIOException;
import com.xiaomei.api.exception.XiaoMeiJSONException;
import com.xiaomei.api.exception.XiaoMeiOtherException;
import com.xiaomei.bean.Mall;
import com.xiaomei.levelone.model.MallLIstModel;
import com.yuekuapp.BaseControl;
import com.yuekuapp.annotations.AsynMethod;
import com.yuekuapp.proxy.MessageProxy;

public class MallControl extends BaseControl {

	private MallLIstModel mModel;
	
	public MallControl(MessageProxy mMethodCallBack) {
		super(mMethodCallBack);
		mModel = new MallLIstModel();
	}
	
	@AsynMethod
	public void getMallListFromNetAsyn(){
		try {
			List<Mall> list = XiaoMeiApplication.getInstance().getApi().getMallHomeListFromNet();
			Mall head = list.remove(0);
			mModel.setHead(head);
			mModel.setData(list);
			sendMessage("getMallListFromNetAsynCallBack");
			if(list == null)
				sendMessage("getMallListFromNetAsynExceptionCallBack");
		} catch (Exception e) {
			e.printStackTrace();
			sendMessage("getMallListFromNetAsynExceptionCallBack");
		} 
	}
	
	public MallLIstModel getModel(){
		return mModel;
	}
}
