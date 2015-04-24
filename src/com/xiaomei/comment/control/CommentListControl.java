package com.xiaomei.comment.control;

import java.util.List;

import android.os.Handler;

import com.xiaomei.XiaoMeiApplication;
import com.xiaomei.api.exception.XiaoMeiCredentialsException;
import com.xiaomei.api.exception.XiaoMeiIOException;
import com.xiaomei.api.exception.XiaoMeiJSONException;
import com.xiaomei.api.exception.XiaoMeiOtherException;
import com.xiaomei.bean.CommentItem;
import com.xiaomei.comment.model.CommentModel;
import com.xiaomei.util.UserUtil;
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
            List<CommentItem> list = XiaoMeiApplication.getInstance().getApi().showCommentList(UserUtil.getUser().getToken(),
                    id, type, "1", PER_PAGE);
           mCommentModel.setCommentList(list);
           mCommentModel.setCurrentPage(1);
           sendMessage("getCommentListDataCallBack");
            android.util.Log.d("111", "list.size() = " + list.size());
        } catch (Exception e) {
            sendMessage("getCommentListDataExceptionCallBack");
            e.printStackTrace();
        }
    }
    
    @AsynMethod
    public void getCommentListDataMore(String id,String type){
        try {
            List<CommentItem>  list =XiaoMeiApplication.getInstance().getApi().showCommentList(UserUtil.getUser().getToken(),
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
            XiaoMeiApplication.getInstance().getApi().actionGoodsComment(UserUtil.getUser().getToken(),
                    id,
                    type, 
                    comment);
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
    
    public CommentModel getModel(){
        return mCommentModel;
    }
}
