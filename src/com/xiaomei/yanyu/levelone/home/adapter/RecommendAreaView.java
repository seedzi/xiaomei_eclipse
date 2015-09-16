package com.xiaomei.yanyu.levelone.home.adapter;

import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.activity.AreaDetailActivity;
import com.xiaomei.yanyu.activity.AreaListActivity;
import com.xiaomei.yanyu.bean.HomeItem;
import com.xiaomei.yanyu.bean.HomeItem.Item;
import com.xiaomei.yanyu.util.ImageLoaderUtil;
import com.xiaomei.yanyu.util.ImageUtils;
import com.xiaomei.yanyu.util.IntentUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class RecommendAreaView extends BaseView {

    private static final int AREA_COUNT = 4;
    private   View[] thumbs;
    private ImageView mRecite; 
    
    @Override
    public View setupView() {
        
        View convertView;
        convertView = mInflater.inflate(R.layout.home_recommend_area, null);
        View itemTitle = convertView.findViewById(R.id.home_item_title);
        itemTitle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                context.startActivity(new Intent(context, AreaListActivity.class));
            }
        });
        ((TextView) itemTitle.findViewById(android.R.id.title)).setText(R.string.home_item_recommend_area);
        
        thumbs = new View[]{convertView.findViewById(R.id.thumb1), convertView.findViewById(R.id.thumb2),
                convertView.findViewById(R.id.thumb3), convertView.findViewById(R.id.thumb4)};
        
        mRecite = (ImageView) convertView.findViewById(R.id.recite);

        return convertView;
    }

    @Override
    public void refreshUi() {
        
        ImageLoader imageLoader = ImageLoader.getInstance();
        DisplayImageOptions options = ImageLoaderUtil.getDisplayOptions(R.drawable.home_area_default);
        List<Item>  areaList = mData.getmList();
        for (int i = 0; i < AREA_COUNT; i++) {
            Item item = areaList.get(i);
            View areaItemThumb = thumbs[i];
            ImageView image = (ImageView) areaItemThumb.findViewById(R.id.image);
            imageLoader.displayImage(item.img, image, options);
            setOnAreaClickListener(image, item.cityId, item.city, item.top_img, item.desc);
            ((TextView) areaItemThumb.findViewById(R.id.name)).setText(item.city);
            ((TextView) areaItemThumb.findViewById(R.id.goods_count)).setText(mAc.getResources().getString(R.string.area_goods_count, item.count));
        }
        ImageLoader.getInstance().displayImage(mData.getRecite().img, mRecite,
                ImageLoaderUtil.getDisplayOptions(R.drawable.recommended_area_recite));
        mRecite.setOnClickListener(this);
        ImageUtils.setViewPressState(mRecite);
        mRecite.setTag(mData.getRecite().jump);
    }

    public void setOnAreaClickListener(View itemView, final String cityId, final String city, final String image, final String description) {
        ImageUtils.setViewPressState(itemView);
        itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = (Activity) v.getContext();
                AreaDetailActivity.startActivity(activity, Long.valueOf(cityId), city, image, description);
            }
        });
    }
}
