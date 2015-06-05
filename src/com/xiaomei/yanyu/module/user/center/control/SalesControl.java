package com.xiaomei.yanyu.module.user.center.control;

import java.util.List;

import com.xiaomei.yanyu.XiaoMeiApplication;
import com.xiaomei.yanyu.bean.Goods;
import com.xiaomei.yanyu.leveltwo.model.LevelTwoModel;
import com.xiaomei.yanyu.util.UserUtil;
import com.yuekuapp.BaseControl;
import com.yuekuapp.annotations.AsynMethod;
import com.yuekuapp.proxy.MessageProxy;

public class SalesControl extends BaseControl {

    private final String PER_PAGE = "10";
    
    private LevelTwoModel mModel = new LevelTwoModel();
    
    public SalesControl(MessageProxy mMethodCallBack) {
        super(mMethodCallBack);
    }

    @AsynMethod
    public void getGoodsDataAsyn(){
        try {
            mModel.setPage(1);
            mModel.setGoodsListList(XiaoMeiApplication.getInstance().getApi().myPromotion(String.valueOf(mModel.getPage()),PER_PAGE,UserUtil.getUser().getToken()));
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
    public void getGoodsDataMoreAsyn(){
        try {
            mModel.increaePage();
            List<Goods> data = XiaoMeiApplication.getInstance().getApi().myPromotion(String.valueOf(mModel.getPage()),PER_PAGE,UserUtil.getUser().getToken());
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
    
    public LevelTwoModel getModel(){
        return mModel;
    }
}
