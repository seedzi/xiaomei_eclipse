package com.xiaomei.yanyu.levelone.control;

import java.util.List;

import com.xiaomei.yanyu.XiaoMeiApplication;
import com.xiaomei.yanyu.bean.HomeItem;
import com.xiaomei.yanyu.levelone.model.HomeListModel;
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
	/**
	 * 新版获取首页列表
	 */
    @AsynMethod
    public void getHomeListEntityAsyn() {
        try {
            List<HomeItem> data = XiaoMeiApplication.getInstance().getApi().getHomeListFromNet2();
            if (data != null && data.size() > 0) {
                mModel.setList(data);
                sendMessage("getHomeListEntityAsynCallBack");
            } else {
                sendMessage("getHomeListEntityAsynCallBackNull");
            }
            android.util.Log.d("111", "data = " + data.size());
        } catch (Exception e) {
            sendMessage("getHomeListEntityAsynCallBackException");
            e.printStackTrace();
        } finally {
        }
    }
    
    public HomeListModel getModel(){
        return mModel;
    }
}
