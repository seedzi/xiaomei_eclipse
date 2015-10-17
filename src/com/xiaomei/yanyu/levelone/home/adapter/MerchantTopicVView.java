package com.xiaomei.yanyu.levelone.home.adapter;

import org.json.JSONObject;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.activity.TopicListActivity;
import com.xiaomei.yanyu.api.HttpUrlManager;
import com.xiaomei.yanyu.bean.HomeItem;
import com.xiaomei.yanyu.leveltwo.GoodsDetailActivity;
import com.xiaomei.yanyu.leveltwo.TopicDetailSlideActivity;
import com.xiaomei.yanyu.util.ImageLoaderUtil;
import com.xiaomei.yanyu.util.ImageUtils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MerchantTopicVView extends BaseView {

    ImageView img;
    ImageView mRecite;
    
    @Override
    public View setupView() {
        View convertView;
        
        convertView = mInflater.inflate(R.layout.home_hospital_intr_layout, null);
        
        convertView.findViewById(R.id.top).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                TopicListActivity.startActivity((Activity) context, context.getString(R.string.title_merchant_topic), HttpUrlManager.MERCHANT_TOPIC_LIST);
            }
        });
        

     
            img = (ImageView) convertView.findViewById(R.id.img);
            LinearLayout.LayoutParams ll2 = new LinearLayout.LayoutParams(mScreenWidth, mScreenWidth*730/720);
            img.setLayoutParams(ll2);
           
            img.setOnClickListener(mHOSPItemClickListener);

            
            
            mRecite = (ImageView) convertView.findViewById(R.id.recite);
            mRecite.setOnClickListener(this);
   
        return convertView;
    }

    @Override
    public void refreshUi() {
        DisplayImageOptions options = ImageLoaderUtil.getDisplayOptions(R.drawable.home_pro_hos_intr_default);
        ImageLoader.getInstance().displayImage(mData.getmList().get(0).image, img,options);
        ImageUtils.setViewPressState(img);
        img.setTag(mData.getmList().get(0));
        
    	LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(mScreenWidth, mScreenWidth*150/1242);
    	mRecite.setLayoutParams(ll);
        DisplayImageOptions reciteOptions = ImageLoaderUtil.getDisplayOptions(R.drawable.hospital_recite);
        ImageLoader.getInstance().displayImage(mData.getRecite().img, mRecite,reciteOptions);
        mRecite.setTag(mData.getRecite().jump);
        ImageUtils.setViewPressState(mRecite);
    }

    private View.OnClickListener mHOSPItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                HomeItem.Item itme = (HomeItem.Item) v.getTag();
                Integer type = Integer.valueOf(itme.type);
                switch (type) {
                case 0: //h5
                    GoodsDetailActivity.startActivity(mAc,itme.url,itme.title);
                    break;
                case 1:
                    JSONObject jsonObject = new JSONObject(itme.list);
                    String viewcount = itme.viewcount;
                    String title =itme.title;
                    String des = jsonObject.optString("des");
                    String img = itme.img;
                    TopicDetailSlideActivity.startActivity(mAc,itme.list, title ,des, img, viewcount);
                    break;
                default:
                    break;
                }
            } catch (Exception e) {
            }
        }
    };
}
