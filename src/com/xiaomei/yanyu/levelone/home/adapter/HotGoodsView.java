package com.xiaomei.yanyu.levelone.home.adapter;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.bean.HomeItem;
import com.xiaomei.yanyu.bean.HomeItem.Item;
import com.xiaomei.yanyu.leveltwo.GoodsDetailActivity;
import com.xiaomei.yanyu.util.ImageLoaderUtil;
import com.xiaomei.yanyu.util.ImageUtils;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ImageView.ScaleType;

public class HotGoodsView extends BaseView {

    private LinearLayout mHorizontalLayout;
    int spaceHorizontal ;
    
    @Override
    public View setupView() {
        spaceHorizontal = (int) mAc.getResources().getDimension(R.dimen.home_item_space_horizontal);
        View convertView;
        convertView = mInflater.inflate(R.layout.home_hot_items_layout, null);

        mHorizontalLayout= (LinearLayout) convertView.findViewById(R.id.horizontal_layout);
        FrameLayout.LayoutParams ll = new FrameLayout.LayoutParams(mScreenWidth,mScreenWidth*260/720);
        mHorizontalLayout.setLayoutParams(ll);

        return convertView;
    }

    @Override
    public void refreshUi() {
        mHorizontalLayout.removeAllViews();
        DisplayImageOptions options = ImageLoaderUtil.getDisplayOptions(R.drawable.home_hot_default);
        
        for(HomeItem.Item item:mData.getmList()){
            ImageView img = new ImageView(mAc.getApplicationContext());
            LinearLayout.LayoutParams vl = new LinearLayout.LayoutParams((mScreenWidth*260/720)*208/260,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            vl.rightMargin = spaceHorizontal;
           img.setLayoutParams(vl);
           img.setScaleType(ScaleType.FIT_XY);
           img.setTag(item);
           img.setOnClickListener(mHotitemsClickListener);
           ImageUtils.setViewPressState(img);
           mHorizontalLayout .addView(img);   
           ImageLoader.getInstance().displayImage(item.img,img,options);
       }
    }
    
    private View.OnClickListener mHotitemsClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View arg0) {
            HomeItem.Item item = (Item) arg0.getTag();
            GoodsDetailActivity.startActivity(mAc, item.url,item.goodsId);
        }
    };
}
