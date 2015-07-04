package com.xiaomei.yanyu.levelone.adapter;

import java.util.List;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.bean.UserShare;
import com.xiaomei.yanyu.util.DateUtils;
import com.xiaomei.yanyu.util.ScreenUtils;

import android.app.ActionBar.LayoutParams;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UserPostAdapter extends BaseAdapter {
    
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
        Holder holder = null;
        if(convertView==null){
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.item_user_post_layout, null);
            holder = new Holder();
            holder.posterUserIcon = (ImageView) convertView.findViewById(R.id.poster_user_icon);
            holder.posterUserName = (TextView) convertView.findViewById(R.id.poster_user_name);
            holder.posterUserName.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
            holder.posterUserName.getPaint().setFakeBoldText(true);//加粗
            holder.posterUserTime = (TextView) convertView.findViewById(R.id.poster_user_time);
            holder.posterContent = (TextView) convertView.findViewById(R.id.poster_content);
            holder.img1 = (ImageView) convertView.findViewById(R.id.img1);
            holder.img2 = (ImageView) convertView.findViewById(R.id.img2);
            holder.img3 = (ImageView) convertView.findViewById(R.id.img3);
            
            holder.commentSize = (TextView) convertView.findViewById(R.id.comment_size);
            holder.favSize = (TextView) convertView.findViewById(R.id.fav_size);
            holder.browseSize = (TextView) convertView.findViewById(R.id.browse_size);
            
            ViewGroup viewGroup = null;
            
            viewGroup = (ViewGroup) convertView.findViewById(R.id.commont_1);
            holder.commentLayout1 = viewGroup;
            holder.userIcon1 =  (ImageView) viewGroup.findViewById(R.id.user_icon);
            holder.userName1 = (TextView) viewGroup.findViewById(R.id.user_name);
            holder.content1 = (TextView) viewGroup.findViewById(R.id.content);
            holder.time1 = (TextView) viewGroup.findViewById(R.id.time);
            
            viewGroup = (ViewGroup) convertView.findViewById(R.id.commont_2);
            holder.commentLayout2 = viewGroup;
            holder.userIcon2 =  (ImageView) viewGroup.findViewById(R.id.user_icon);
            holder.userName2 = (TextView) viewGroup.findViewById(R.id.user_name);
            holder.content2 = (TextView) viewGroup.findViewById(R.id.content);
            holder.time2 = (TextView) viewGroup.findViewById(R.id.time);
            
            viewGroup = (ViewGroup) convertView.findViewById(R.id.commont_3);
            holder.commentLayout3 = viewGroup;
            holder.userIcon3 =  (ImageView) viewGroup.findViewById(R.id.user_icon);
            holder.userName3 = (TextView) viewGroup.findViewById(R.id.user_name);
            holder.content3 = (TextView) viewGroup.findViewById(R.id.content);
            holder.time3 = (TextView) viewGroup.findViewById(R.id.time);
            
            holder.moreComment = convertView.findViewById(R.id.more_commont);
            convertView.setTag(holder);
        }
        holder = (Holder) convertView.getTag();
        
        int width = ScreenUtils.getScreenWidth(parent.getContext());
        int imgWidth = (width - ScreenUtils.dip2px(parent.getContext(), 63))/3;
        LinearLayout.LayoutParams ll1 = new LinearLayout.LayoutParams
                (imgWidth,imgWidth);
        LinearLayout.LayoutParams ll2 = new LinearLayout.LayoutParams
                (imgWidth,imgWidth);
        ll2.setMargins(ScreenUtils.dip2px(parent.getContext(), 8), 0, ScreenUtils.dip2px(parent.getContext(), 8), 0);
        LinearLayout.LayoutParams ll3 = new LinearLayout.LayoutParams
                (imgWidth,imgWidth);
        holder.img1.setLayoutParams(ll1);
        holder.img2.setLayoutParams(ll2);
        holder.img3.setLayoutParams(ll3);
        
        UserShare item =mData.get(position);
        
        holder.posterUserIcon.setImageResource(R.drawable.user_head_default);
        ImageLoader.getInstance().displayImage(item.getAvatar(), holder.posterUserIcon);
        holder.posterUserName.setText(item.getUsername());
        holder.posterContent.setText(item.getContent());
        holder.posterUserTime.setText(DateUtils.formateDate(Long.valueOf(item.getTime())*1000));
        
        List<String> imgs = item.getImgs();
		holder.img1.setVisibility(View.INVISIBLE);
		holder.img2.setVisibility(View.INVISIBLE);
		holder.img3.setVisibility(View.INVISIBLE);
		holder.img1.setImageResource(R.drawable.tiezi_zhanwei);
		holder.img2.setImageResource(R.drawable.tiezi_zhanwei);
		holder.img3.setImageResource(R.drawable.tiezi_zhanwei);
        if(imgs!=null){
        	int index = 0;
        	for(String img :imgs){
        		switch (index) {
				case 0:
					holder.img1.setVisibility(View.VISIBLE);
			        ImageLoader.getInstance().displayImage(img,holder.img1);
					break;
				case 1:
					holder.img2.setVisibility(View.VISIBLE);
			        ImageLoader.getInstance().displayImage(img,holder.img2);
					break;
				case 2:
					holder.img3.setVisibility(View.VISIBLE);
			        ImageLoader.getInstance().displayImage(img,holder.img3);
					break;	
				default:
					break;
				}
        		index ++;
        	}
        }
        
        holder.commentLayout1.setVisibility(View.GONE);
        holder.commentLayout2.setVisibility(View.GONE);
        holder.commentLayout3.setVisibility(View.GONE);
        List<UserShare.Comment> comments =  item.getCommtents();
        if(comments!=null){
        	int j = 0;
        	for(UserShare.Comment comment :comments){
        		switch (j) {
				case 0:
					holder.commentLayout1.setVisibility(View.VISIBLE);
					holder.userIcon1.setImageResource(R.drawable.user_head_default);
					ImageLoader.getInstance().displayImage(comment.getAvatar(),holder.userIcon1);
					holder.userName1.setText(comment.getUsername());
					holder.content1.setText(comment.getContent());
					holder.time1.setText(DateUtils.formateDate(Long.valueOf(comment.getTime())*1000));
					break;
				case 1:
					holder.commentLayout2.setVisibility(View.VISIBLE);
					holder.userIcon2.setImageResource(R.drawable.user_head_default);
					ImageLoader.getInstance().displayImage(comment.getAvatar(),holder.userIcon2);
					holder.userName2.setText(comment.getUsername());
					holder.content2.setText(comment.getContent());
					holder.time2.setText(DateUtils.formateDate(Long.valueOf(comment.getTime())*1000));
					break;
				case 2:
					holder.commentLayout3.setVisibility(View.VISIBLE);
					holder.userIcon3.setImageResource(R.drawable.user_head_default);
					ImageLoader.getInstance().displayImage(comment.getAvatar(),holder.userIcon3);
					holder.userName3.setText(comment.getUsername());
					holder.content3.setText(comment.getContent());
					holder.time3.setText(DateUtils.formateDate(Long.valueOf(comment.getTime())*1000));
					break;
				default:
					break;
				}
        		j++;
        	}
        	
        	if(item.getCommentSize()>3){
        		holder.moreComment.setVisibility(View.VISIBLE);
        	}else{
        		holder.moreComment.setVisibility(View.GONE);
        	}
        }
        return convertView;
    }

    private class Holder{
        private ImageView posterUserIcon;
        private TextView posterUserName;
        private TextView posterUserTime;
        private TextView posterContent;
        private ImageView img1;
        private ImageView img2;
        private ImageView img3;
        private TextView commentSize;
        private TextView favSize;
        private TextView browseSize;
        //TODO
        private ViewGroup commentLayout1;
        private ImageView userIcon1;
        private TextView userName1;
        private TextView content1;
        private TextView time1;
        
        private ViewGroup commentLayout2;
        private ImageView userIcon2;
        private TextView userName2;
        private TextView content2;
        private TextView time2;
        
        private ViewGroup commentLayout3;
        private ImageView userIcon3;
        private TextView userName3;
        private TextView content3;
        private TextView time3;
        
        private View moreComment;
    }
}
