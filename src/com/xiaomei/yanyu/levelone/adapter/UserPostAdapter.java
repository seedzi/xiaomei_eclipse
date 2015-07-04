package com.xiaomei.yanyu.levelone.adapter;

import java.util.List;

import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.bean.UserShare;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
        return 10;
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
            holder.userIcon1 =  (ImageView) viewGroup.findViewById(R.id.user_icon);
            holder.userName1 = (TextView) viewGroup.findViewById(R.id.user_name);
            holder.content1 = (TextView) viewGroup.findViewById(R.id.content);
            holder.time1 = (TextView) viewGroup.findViewById(R.id.time);
            
            viewGroup = (ViewGroup) convertView.findViewById(R.id.commont_2);
            holder.userIcon2 =  (ImageView) viewGroup.findViewById(R.id.user_icon);
            holder.userName2 = (TextView) viewGroup.findViewById(R.id.user_name);
            holder.content2 = (TextView) viewGroup.findViewById(R.id.content);
            holder.time2 = (TextView) viewGroup.findViewById(R.id.time);
            
            viewGroup = (ViewGroup) convertView.findViewById(R.id.commont_3);
            holder.userIcon3 =  (ImageView) viewGroup.findViewById(R.id.user_icon);
            holder.userName3 = (TextView) viewGroup.findViewById(R.id.user_name);
            holder.content3 = (TextView) viewGroup.findViewById(R.id.content);
            holder.time3 = (TextView) viewGroup.findViewById(R.id.time);
            
            holder.moreComment = convertView.findViewById(R.id.more_commont);
            convertView.setTag(holder);
        }
        holder = (Holder) convertView.getTag();
        
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
        private ImageView userIcon1;
        private TextView userName1;
        private TextView content1;
        private TextView time1;
        
        private ImageView userIcon2;
        private TextView userName2;
        private TextView content2;
        private TextView time2;
        
        private ImageView userIcon3;
        private TextView userName3;
        private TextView content3;
        private TextView time3;
        
        private View moreComment;
    }
}
