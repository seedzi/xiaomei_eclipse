package com.xiaomei.yanyu.leveltwo.control;

import java.util.List;

import com.xiaomei.yanyu.XiaoMeiApplication;
import com.xiaomei.yanyu.api.exception.XiaoMeiCredentialsException;
import com.xiaomei.yanyu.api.exception.XiaoMeiIOException;
import com.xiaomei.yanyu.api.exception.XiaoMeiJSONException;
import com.xiaomei.yanyu.api.exception.XiaoMeiOtherException;
import com.xiaomei.yanyu.bean.Goods;
import com.xiaomei.yanyu.bean.GoodsOption;
import com.xiaomei.yanyu.leveltwo.model.LevelTwoModel;
import com.xiaomei.yanyu.util.UserUtil;
import com.yuekuapp.BaseControl;
import com.yuekuapp.annotations.AsynMethod;
import com.yuekuapp.proxy.MessageProxy;

public class LeveltwoControl extends BaseControl {

	private final String PER_PAGE = "10";
	
	private LevelTwoModel mModel = new LevelTwoModel();
	
	public LeveltwoControl(MessageProxy mMethodCallBack) {
		super(mMethodCallBack);
	}
	
	public LevelTwoModel getModel(){
		return mModel;
	}
	
	// ============================== 圈子 ====================================
	@AsynMethod
	public void getDataAsyn(String id ){
		try {
			mModel.setBeautifulRingDetail(XiaoMeiApplication.getInstance().getApi().getBeatifulRingDetailFromNet(id));
			if(mModel.getBeautifulRingDetail()!=null)
				sendMessage("getDataAsynCallBack");
			else
				sendMessage("getDataAsynExceptionCallBack");
		} catch (Exception e) {
			android.util.Log.d("111", "e = " + e.getMessage() + ",e = " + e);
			e.printStackTrace();
			sendMessage("getDataAsynExceptionCallBack");
			return;
		}
	}
	
	// ============================== 商品 ====================================
	@AsynMethod
	public void getGoodsDataAsyn(String catId, String subCat, String originPlace, String priceOrder){
		try {
			mModel.setPage(1);
			mModel.setGoodsListList(XiaoMeiApplication.getInstance().getApi().getGoodsListFromNet(catId,String.valueOf(mModel.getPage()),PER_PAGE,subCat, originPlace, priceOrder));
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
	
	@AsynMethod
	public void getGoodsDataMoreAsyn(String catId, String subCat, String originPlace, String priceOrder){
		try {
			mModel.increaePage();
			List<Goods> data = XiaoMeiApplication.getInstance().getApi().getGoodsListFromNet(catId,String.valueOf(mModel.getPage()),PER_PAGE,subCat, originPlace, priceOrder);
			if(data==null || data.size() == 0){
				mModel.reducePage();
				sendMessage("getGoodsDataMoreAsynExceptionCallBack");
			}else{
				mModel.setGoodsListList(data);
				sendMessage("getGoodsDataMoreAsynCallBack");
			}
		} catch (Exception e) {
			e.printStackTrace();
			sendMessage("getGoodsDataMoreAsynExceptionCallBack");
			return;
		}
	}
	
	@AsynMethod
	public void getGoodsOptionAsyn(String catId) {
	    try {
            List<GoodsOption> data = XiaoMeiApplication.getInstance().getApi().getGoodsOptionFromNet(catId);
            if(data==null || data.size() == 0){
                sendMessage("getGoodsOptionAsynExceptionCallBack");
            }else{
                mModel.setGoodsOptions(data);
                sendMessage("getGoodsOptionAsynCallBack");
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendMessage("getGoodsOptionAsynExceptionCallBack");
            return;
        }
	}
	
	
	// =======================================================================================
	// 收藏
	// =======================================================================================
	@AsynMethod
	public void actionUserFavAdd(String goodsid){sendMessage("isFavCallBack");
		actionUserFav("add", goodsid);
	}
	public void actionUserFavRm(String goodsid){
		actionUserFav("rm", goodsid);
	}
	private void actionUserFav(String action,String goodsid){
		try {
			 XiaoMeiApplication.getInstance().getApi().actionFav(action, goodsid,UserUtil.getUser().getToken());
			 sendMessage("actionUserFavCallBack");
		} catch (Exception e) {
			e.printStackTrace();
			sendMessage("actionUserFavExceptionCallBack");
			return;
		}
	}
	/**是否收藏*/
	@AsynMethod
	public void isFav(String goodsid){
		try {
			boolean  isFav = XiaoMeiApplication.getInstance().getApi().isFav(goodsid, UserUtil.getUser().getUserInfo().getUserid(), UserUtil.getUser().getToken());
			mModel.setmIsFav(isFav);
			sendMessage("isFavCallBack");
		} catch (Exception e) {
			e.printStackTrace();
			sendMessage("isFavExceptionCallBack");
		} 
	}
}
