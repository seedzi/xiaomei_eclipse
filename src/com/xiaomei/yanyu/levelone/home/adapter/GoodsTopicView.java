package com.xiaomei.yanyu.levelone.home.adapter;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.activity.TopicListActivity;
import com.xiaomei.yanyu.api.HttpUrlManager;
import com.xiaomei.yanyu.bean.HomeItem;
import com.xiaomei.yanyu.leveltwo.GoodsDetailActivity;
import com.xiaomei.yanyu.leveltwo.TopicDetailSlideActivity;
import com.xiaomei.yanyu.util.ImageUtils;
/**
 * 产品推荐
 */
public class GoodsTopicView extends BaseView implements View.OnClickListener{

    private ImageView mImg;
    private ImageView mRecite;
    
    
    @Override
    public View setupView() {
        View convertView;
        convertView = mInflater
                .inflate(R.layout.home_product_intr_layout, null);

        convertView.findViewById(R.id.top).setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Context context = v.getContext();
                        TopicListActivity.startActivity((Activity) context,
                                context.getString(R.string.title_goods_topic),
                                HttpUrlManager.GOODS_TOPIC_LIST);
                    }
                });
        mImg = (ImageView) convertView.findViewById(R.id.img);
        mRecite = (ImageView) convertView.findViewById(R.id.recite);
        

        LinearLayout.LayoutParams ll1 = new LinearLayout.LayoutParams(
                mScreenWidth, mScreenWidth * 730 / 720);
        mImg.setLayoutParams(ll1);

        mImg.setOnClickListener(mProductItemClickListener);
        mRecite.setOnClickListener(this);
        return convertView;
    }

    @Override
    public void refreshUi() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
        .showImageForEmptyUri(R.drawable.home_pro_hos_intr_default)
        .showImageOnLoading(R.drawable.home_pro_hos_intr_default)
        .showImageOnFail(R.drawable.home_pro_hos_intr_default).build();
        
        mImg.setTag(mData.getmList().get(0).goodsId);
        ImageLoader.getInstance().displayImage(
                mData.getmList().get(0).image, mImg, options);
        ImageUtils.setViewPressState(mImg);
        mImg.setTag(mData.getmList().get(0));
        
        DisplayImageOptions reciteOptions2 = new DisplayImageOptions.Builder()
        .showImageForEmptyUri(R.drawable.product_recite)
        .showImageOnLoading(R.drawable.product_recite)
        .showImageOnFail(R.drawable.product_recite).build();
        
    	LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(mScreenWidth, mScreenWidth*150/1242);
    	mRecite.setLayoutParams(ll);
        ImageLoader.getInstance().displayImage(
                mData.getRecite().img, mRecite, reciteOptions2);
        ImageUtils.setViewPressState(mRecite);
        mRecite.setTag(mData.getRecite().jump);
    }

    /**
     * 产品item 的点击事件
     */
    private View.OnClickListener mProductItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                HomeItem.Item itme = (HomeItem.Item) v.getTag();
                Integer type = Integer.valueOf(itme.type);
                switch (type) {
                case 0: //h5
                    GoodsDetailActivity.startActivity(mAc,itme.url,itme.goodsId,itme.title);
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
