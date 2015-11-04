package com.xiaomei.yanyu.levelone.home.adapter;

import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.XiaoMeiApplication;
import com.xiaomei.yanyu.api.HttpUrlManager;
import com.xiaomei.yanyu.bean.HomeItem;
import com.xiaomei.yanyu.leveltwo.GoodsDetailActivity;
import com.xiaomei.yanyu.leveltwo.RecommendSharesDetailActivity;
import com.xiaomei.yanyu.util.ImageLoaderUtil;
import com.xiaomei.yanyu.util.ImageUtils;
import com.xiaomei.yanyu.util.ScreenUtils;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HomeShareView extends BaseView {

    Holder holder = new Holder();
    @Override
    public View setupView() {
        ViewGroup convertView;
        convertView = (ViewGroup) mInflater.inflate(R.layout.home_share_layout, null);
        setupHomeShare(holder, convertView);
        return convertView;
    }

    @Override
    public void refreshUi() {
        // TODO Auto-generated method stub
        setupItem(holder.item1, mData.getmList(), 0);
        setupItem(holder.item2, mData.getmList(), 1);
        setupItem(holder.item3, mData.getmList(), 2);
        setupItem(holder.item4, mData.getmList(), 3);
        setupItem(holder.item5, mData.getmList(), 4);
        setupItem(holder.item6, mData.getmList(), 5);
        setupItem(holder.item7, mData.getmList(), 6);
        setupItem(holder.item8, mData.getmList(), 7);
    }
    
    private void setupHomeShare(Holder holder,ViewGroup convertView){
        holder.item1 = (ViewGroup) convertView.findViewById(R.id.item1);
        holder.item2 = (ViewGroup) convertView.findViewById(R.id.item2);
        holder.item3 = (ViewGroup) convertView.findViewById(R.id.item3);
        holder.item4 = (ViewGroup) convertView.findViewById(R.id.item4);
        holder.item5 = (ViewGroup) convertView.findViewById(R.id.item5);
        holder.item6 = (ViewGroup) convertView.findViewById(R.id.item6);
        holder.item7 = (ViewGroup) convertView.findViewById(R.id.item7);
        holder.item8 = (ViewGroup) convertView.findViewById(R.id.item8);
        
        int img_width = (mScreenWidth-60)/2;
        
        RelativeLayout.LayoutParams rl = new RelativeLayout.LayoutParams(
                img_width,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        rl.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        rl.leftMargin = 20;
        rl.rightMargin = 10;
        rl.bottomMargin = 20;
        holder.item1.setLayoutParams(rl);
        
        rl = new RelativeLayout.LayoutParams(
                img_width,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        rl.addRule(RelativeLayout.RIGHT_OF,R.id.item1);
        rl.leftMargin = 10;
        rl.rightMargin = 20;
        rl.bottomMargin = 20;
        holder.item2.setLayoutParams(rl);
        
        rl = new RelativeLayout.LayoutParams(
                img_width,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        rl.addRule(RelativeLayout.BELOW,R.id.item1);
        rl.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        rl.leftMargin = 20;
        rl.rightMargin = 10;
        rl.bottomMargin = 20;
        holder.item3.setLayoutParams(rl);
        
        rl = new RelativeLayout.LayoutParams(
                img_width,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        rl.addRule(RelativeLayout.BELOW,R.id.item1);
        rl.addRule(RelativeLayout.RIGHT_OF,R.id.item3);
        rl.leftMargin = 10;
        rl.rightMargin = 20;
        rl.bottomMargin = 20;
        holder.item4.setLayoutParams(rl);
        
        rl = new RelativeLayout.LayoutParams(
                img_width,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        rl.addRule(RelativeLayout.BELOW,R.id.item3);
        rl.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        rl.leftMargin = 20;
        rl.rightMargin = 10;
        rl.bottomMargin = 20;
        holder.item5.setLayoutParams(rl);
    
        rl = new RelativeLayout.LayoutParams(
                img_width,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        rl.addRule(RelativeLayout.RIGHT_OF,R.id.item5);
        rl.addRule(RelativeLayout.BELOW,R.id.item3);
        rl.leftMargin = 10;
        rl.rightMargin = 20;
        rl.bottomMargin = 20;
        holder.item6.setLayoutParams(rl);
        
        rl = new RelativeLayout.LayoutParams(
                img_width,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        rl.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        rl.addRule(RelativeLayout.BELOW,R.id.item6);
        rl.leftMargin = 20;
        rl.rightMargin = 10;
        holder.item7.setLayoutParams(rl);
        
        rl = new RelativeLayout.LayoutParams(
                img_width,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        rl.addRule(RelativeLayout.RIGHT_OF,R.id.item7);
        rl.addRule(RelativeLayout.BELOW,R.id.item6);
        rl.leftMargin = 10;
        rl.rightMargin = 20;
        holder.item8.setLayoutParams(rl);
        
    }
    
    private void setupItem(ViewGroup viewGroup , List<HomeItem.Item> list,int position){
        DisplayImageOptions options = ImageLoaderUtil.getDisplayOptions(R.drawable.home_share_default);
        int img_width = (mScreenWidth-60)/2;
        int img_height = img_width*370/330;
        LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(img_width, img_height);
        ImageView img =  (ImageView) viewGroup.findViewById(R.id.img);
        img.setLayoutParams(ll);
        
        TextView description = (TextView) viewGroup.findViewById(R.id.description);
        ImageView icon = (ImageView) viewGroup.findViewById(R.id.icon);
        TextView username = (TextView) viewGroup.findViewById(R.id.user_name);
        TextView commentSize = (TextView) viewGroup.findViewById(R.id.size);
        ImageLoader.getInstance().displayImage(list.get(position).img,img,options);
        description.setText(list.get(position).title);
        username.setText(list.get(position).user);
        commentSize.setText(list.get(position).comments);
        img.setTag(list.get(position));
        img.setOnClickListener(mShareItemsClickListener);
        ImageUtils.setViewPressState(img);
        
        DisplayImageOptions options1 = ImageLoaderUtil.getDisplayOptions(R.drawable.user_head_default);
        ImageLoader.getInstance().displayImage(list.get(position).avatar,icon,options1);
    }
    
    private View.OnClickListener mShareItemsClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View arg0) {
              try {
                    HomeItem.Item itme = (HomeItem.Item) arg0.getTag();
                    Integer type = Integer.valueOf(itme.type);
                    switch (type) {
                    case 0://H5形式
                    	  String url = "http://z.drxiaomei.com/share.php?shareid=" + itme.shareId;
                    	  String content = XiaoMeiApplication.getInstance().getResources().getString(R.string.share_circles_txt);
                          GoodsDetailActivity.startActivity(mAc,url,itme.title,content,itme.image);
                        break;
                    case 1: //卡片形式
                            RecommendSharesDetailActivity.startActivity(mAc, itme.shareId);
                        break;
                    default:
                        break;
                    }
                } catch (Exception e) {
                }
        }
    };
    
    private class Holder{
        private ViewGroup item1;
        private ViewGroup item2;
        private ViewGroup item3;
        private ViewGroup item4;
        private ViewGroup item5;
        private ViewGroup item6;
        private ViewGroup item7;
        private ViewGroup item8;
    }
}
