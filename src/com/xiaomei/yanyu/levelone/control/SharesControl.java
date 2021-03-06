package com.xiaomei.yanyu.levelone.control;

import java.util.List;

import com.xiaomei.yanyu.XiaoMeiApplication;
import com.xiaomei.yanyu.bean.RecommendShares;
import com.xiaomei.yanyu.bean.UserShare;
import com.xiaomei.yanyu.levelone.model.SharesModel;
import com.yuekuapp.BaseControl;
import com.yuekuapp.annotations.AsynMethod;
import com.yuekuapp.proxy.MessageProxy;

public class SharesControl extends BaseControl {
	
	private SharesModel mModel;
	
	private final int PERPAGE = 10;

	public SharesControl(MessageProxy mMethodCallBack) {
		super(mMethodCallBack);
		mModel = new SharesModel();
	}
    // =========================================  jingxuan==================================================
	@AsynMethod
	public void getJinghuaListDataFromNetAysn(){
		try {
			mModel.setBeautifulPage(1);
			mModel.setBeautifulData(XiaoMeiApplication.getInstance().getApi().getRecommendSharesFromNet(String.valueOf(mModel.getBeautifulPage()),String.valueOf(PERPAGE)));
			sendMessage("getJinghuaListDataFromNetAysnCallBack");
		} catch (Exception e) {
			sendMessage("getJinghuaListDataFromNetAysnExceptionCallBack");
			e.printStackTrace();
		} finally {
		}
	
	}
	
	@AsynMethod
	public void getJinghuaMoreListDataFromNetAysn(){
		try {
			mModel.increaeBeautifulPage();
			List<RecommendShares>  data = XiaoMeiApplication.getInstance().getApi().getRecommendSharesFromNet(String.valueOf(mModel.getBeautifulPage()),String.valueOf(PERPAGE));
			mModel.setBeautifulData(data);
			if(data==null || data.size() == 0){
				mModel.reduceBeautifulPage();;
				sendMessage("getJinghuaMoreListDataFromNetAysnExceptionCallBack");
			}
		} catch (Exception e) {
			sendMessage("getJinghuaMoreListDataFromNetAysnExceptionCallBack");
			mModel.reduceBeautifulPage();
			e.printStackTrace();
		} finally{
		}
		sendMessage("getJinghuaMoreListDataFromNetAysnCallBack");
	}
	 // =========================================  guanchang==================================================
	@AsynMethod
    public void getGuangchangListDataFromNetAysn(){
        try {
            mModel.setUserSharePage(1);
            mModel.setUserShareData(XiaoMeiApplication.getInstance().getApi().getUserShareListFromNet(String.valueOf(mModel.getUserSharePage()),String.valueOf(PERPAGE)));
            sendMessage("getGuangchangListDataFromNetAysnCallBack");
        } catch (Exception e) {
            sendMessage("getGuangchangListDataFromNetAysnExceptionCallBack");
            e.printStackTrace();
        } finally {
        }
    
    }
    
    @AsynMethod
    public void getGuangchangMoreListDataFromNetAysn(){
        try {
            mModel.increaeUserSharePage();
            List<UserShare>  data = XiaoMeiApplication.getInstance().getApi().getUserShareListFromNet(String.valueOf(mModel.getUserSharePage()),String.valueOf(PERPAGE));
            mModel.setUserShareData(data);
            if(data==null || data.size() == 0){
                mModel.reduceUserSharePage();
                sendMessage("getGuangchangMoreListDataFromNetAysnExceptionCallBack");
            }
        } catch (Exception e) {
            sendMessage("getGuangchangMoreListDataFromNetAysnExceptionCallBack");
            mModel.reduceUserSharePage();
            e.printStackTrace();
        } finally{
        }
        sendMessage("getGuangchangMoreListDataFromNetAysnCallBack");
    }
	
	public SharesModel getModel(){
	    return mModel;
	}
	
}
