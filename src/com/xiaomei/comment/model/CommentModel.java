package com.xiaomei.comment.model;

import java.util.List;

import com.xiaomei.bean.CommentItem;

public class CommentModel {
    
    private List<CommentItem> mData;

    private int mCurrentPage;
    
    public void setCommentList(List<CommentItem> data){
        mData = data;
    }
    
    public List<CommentItem>  getCommentList(){
        return mData;
    }
    
    public int getCurrentPage(){
        return mCurrentPage;
    }
    
    public void setCurrentPage(int currentPage){
        mCurrentPage = currentPage;
    }
    
    public void increasePage(){
        mCurrentPage ++;
    }
}
