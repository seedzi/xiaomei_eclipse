package com.xiaomei.yanyu.comment.control;

import java.util.List;

import android.os.Handler;

import com.xiaomei.yanyu.XiaoMeiApplication;
import com.xiaomei.yanyu.api.exception.XiaoMeiCredentialsException;
import com.xiaomei.yanyu.api.exception.XiaoMeiIOException;
import com.xiaomei.yanyu.api.exception.XiaoMeiJSONException;
import com.xiaomei.yanyu.api.exception.XiaoMeiOtherException;
import com.xiaomei.yanyu.bean.CommentItem;
import com.xiaomei.yanyu.bean.NetResult;
import com.xiaomei.yanyu.comment.model.CommentModel;
import com.xiaomei.yanyu.util.UserUtil;
import com.yuekuapp.BaseControl;
import com.yuekuapp.annotations.AsynMethod;
import com.yuekuapp.proxy.MessageProxy;

public class CommentListControl extends BaseControl {
    
    private CommentModel mCommentModel;
    
    private final String PER_PAGE = "10";

    public CommentListControl(MessageProxy mMethodCallBack) {
        super(mMethodCallBack);
        mCommentModel = new CommentModel();
    }
    
    @AsynMethod
    public void getCommentListData(String id,String type){
        try {
            List<CommentItem> list = XiaoMeiApplication.getInstance().getApi().showCommentList(
                    id, type, "1", PER_PAGE);
           mCommentModel.setCommentList(list);
           mCommentModel.setCurrentPage(1);
           if(list!=null && list.size()>0){
        	   sendMessage("getCommentListDataCallBack");
        	   android.util.Log.d("111", "getCommentListDataCallBack");
           }else{
        	   sendMessage("getCommentListDataCallBackNull");
        	   android.util.Log.d("111", "getCommentListDataCallBackNull");
           }
        } catch (Exception e) {
        	android.util.Log.d("111", "getCommentListDataExceptionCallBack");
            sendMessage("getCommentListDataExceptionCallBack");
            e.printStackTrace();
        }
    }
    
    @AsynMethod
    public void getCommentListDataMore(String id,String type){
        try {
            List<CommentItem>  list =XiaoMeiApplication.getInstance().getApi().showCommentList(
                    id, type, String.valueOf(mCommentModel.getCurrentPage() +1), PER_PAGE);
            mCommentModel.setCommentList(list);
            if(list!=null && list.size()>0){
                mCommentModel.increasePage();
            }
            sendMessage("getCommentListDataMoreCallBack");
        } catch (Exception e) {
            sendMessage("getCommentListDataMoreExceptionCallBack");
            e.printStackTrace();
        }
    }
    
    @AsynMethod
    public void actionShareComment(String id,String type,String comment){
        try {
        	NetResult netResult = XiaoMeiApplication.getInstance().getApi().actionGoodsComment(UserUtil.getUser().getToken(),
                    id,
                    type, 
                    comment);
        	if(netResult!=null && "0".equals(netResult.getCode())){
        		sendMessage("actionShareCommentCallBack");
        	}else{
        		sendMessage("actionShareCommentExceptionCallBack");
        	}
        } catch (Exception e) {
        	sendMessage("actionShareCommentExceptionCallBack");
            e.printStackTrace();
        } 
    }
    
    public CommentModel getModel(){
        return mCommentModel;
    }
}
