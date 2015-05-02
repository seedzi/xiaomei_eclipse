package com.xiaomei.yanyu.levelone.control;

import java.util.List;

import android.util.Log;

import com.android.dx.io.Code.Try;
import com.xiaomei.yanyu.XiaoMeiApplication;
import com.xiaomei.yanyu.api.exception.XiaoMeiCredentialsException;
import com.xiaomei.yanyu.api.exception.XiaoMeiIOException;
import com.xiaomei.yanyu.api.exception.XiaoMeiJSONException;
import com.xiaomei.yanyu.api.exception.XiaoMeiOtherException;
import com.xiaomei.yanyu.bean.Mall;
import com.xiaomei.yanyu.levelone.model.MallLIstModel;
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
			android.util.Log.d("111", "e = " + e.getMessage());
			sendMessage("getMallListFromNetAsynExceptionCallBack");
		} 
	}
	
	public MallLIstModel getModel(){
		return mModel;
	}
}
