package com.xiaomei.yanyu.levelone.adapter;

import java.util.List;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.bean.UserShare;
import com.xiaomei.yanyu.bean.UserShare.Comment;
import com.xiaomei.yanyu.comment.CommentListActivity;
import com.xiaomei.yanyu.util.DateUtils;
import com.xiaomei.yanyu.util.ScreenUtils;
import com.xiaomei.yanyu.util.UiUtil;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UserPostAdapter extends BaseAdapter implements View.OnClickListener{
    
	private Activity mAc;
	
	public void setActivity(Activity ac){
		mAc = ac;
	}
	
    private List<UserShare> mData;

    public List<UserShare>  getData(){
        return mData;
    }
    
    public void setData(List<UserShare> data){
        mData = data;
    }
    
    @Override
    public int getCount() {
        return mData==null?0:mData.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView != null ? convertView : LayoutInflater.from(mAc).inflate(R.layout.item_user_post_layout, parent, false);
        
        int width = ScreenUtils.getScreenWidth(mAc);
        int imgWidth = (width - ScreenUtils.dip2px(mAc, 63))/3;
        LinearLayout.LayoutParams ll1 = new LinearLayout.LayoutParams
                (imgWidth,imgWidth);
        LinearLayout.LayoutParams ll2 = new LinearLayout.LayoutParams
                (imgWidth,imgWidth);
        ll2.setMargins(ScreenUtils.dip2px(mAc, 8), 0, ScreenUtils.dip2px(mAc, 8), 0);
        LinearLayout.LayoutParams ll3 = new LinearLayout.LayoutParams
                (imgWidth,imgWidth);
        ImageView img1 = UiUtil.findImageViewById(itemView, R.id.img1);
        ImageView img2 = UiUtil.findImageViewById(itemView, R.id.img2);
        ImageView img3 = UiUtil.findImageViewById(itemView, R.id.img3);
        img1.setLayoutParams(ll1);
        img2.setLayoutParams(ll2);
        img3.setLayoutParams(ll3);
        
        UserShare item =mData.get(position);
        
        ImageView userIcon = UiUtil.findImageViewById(itemView, R.id.poster_user_icon);
        userIcon.setImageResource(R.drawable.user_head_default);
        ImageLoader.getInstance().displayImage(item.getAvatar(), userIcon);
        UiUtil.findTextViewById(itemView, R.id.poster_user_name).setText(item.getUsername());
        UiUtil.findTextViewById(itemView, R.id.poster_content).setText(item.getContent());
        UiUtil.findTextViewById(itemView, R.id.poster_user_time).setText(DateUtils.formateDate(Long.valueOf(item.getTime())*1000));
        
        List<String> imgs = item.getImgs();
		img1.setVisibility(View.INVISIBLE);
		img2.setVisibility(View.INVISIBLE);
		img3.setVisibility(View.INVISIBLE);
		img1.setImageResource(R.drawable.tiezi_zhanwei);
		img2.setImageResource(R.drawable.tiezi_zhanwei);
		img3.setImageResource(R.drawable.tiezi_zhanwei);
        if(imgs!=null){
        	int index = 0;
        	for(String img :imgs){
        		switch (index) {
				case 0:
					img1.setVisibility(View.VISIBLE);
			        ImageLoader.getInstance().displayImage(img,img1);
					break;
				case 1:
					img2.setVisibility(View.VISIBLE);
			        ImageLoader.getInstance().displayImage(img,img2);
					break;
				case 2:
					img3.setVisibility(View.VISIBLE);
			        ImageLoader.getInstance().displayImage(img,img3);
					break;	
				default:
					break;
				}
        		index ++;
        	}
        }
        
        View commentLayout1 = UiUtil.findViewById(itemView, R.id.commont_1);
        View commentLayout2 = UiUtil.findViewById(itemView, R.id.commont_2);
        View commentLayout3 = UiUtil.findViewById(itemView, R.id.commont_3);
        commentLayout1.setVisibility(View.GONE);
        commentLayout2.setVisibility(View.GONE);
        commentLayout3.setVisibility(View.GONE);
        List<UserShare.Comment> comments =  item.getCommtents();
        int commentsSize = comments != null ? comments.size() : 0;
        for (int j = 0; j < 3 && j < commentsSize; j++) {
            View commentLayout; 
            switch (j) {
                case 0:
                    commentLayout = commentLayout1;
                    break;
                case 1:
                    commentLayout = commentLayout2;
                    break;
                case 2:
                    commentLayout = commentLayout3;
                    break;
                default:
                    throw new IllegalStateException("More than 3 comments");
            }
            commentLayout.setVisibility(View.VISIBLE);
            ImageView commentUserIcon = UiUtil.findImageViewById(commentLayout, R.id.user_icon);
            Comment comment = comments.get(j);
            ImageLoader.getInstance().displayImage(comment.getAvatar(), commentUserIcon);
            UiUtil.findTextViewById(commentLayout, R.id.user_name)
            .setText(comment.getUsername());
            UiUtil.findTextViewById(commentLayout, R.id.content).setText(comment.getContent());
            UiUtil.findTextViewById(commentLayout, R.id.time)
            .setText(DateUtils.formateDate(Long.valueOf(comment.getTime()) * 1000));
        }
        
        View moreComment = UiUtil.findViewById(itemView, R.id.more_commont);
        moreComment.setVisibility(commentsSize > 3 ? View.VISIBLE : View.GONE);
        moreComment.setOnClickListener(this);
        moreComment.setTag(item.getId());
        
        View commentSize = UiUtil.findViewById(itemView, R.id.comment_size);
        commentSize.setOnClickListener(this);
        commentSize.setTag(item.getId());
        return itemView ;
    }

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.more_commont:
			CommentListActivity.startActivity(mAc,"share",(String)v.getTag(),true,false);
			break;
		case R.id.comment_size:
			CommentListActivity.startActivity(mAc,"share",(String)v.getTag(),true,true);
			break;
		default:
			break;
		}
	}
}
