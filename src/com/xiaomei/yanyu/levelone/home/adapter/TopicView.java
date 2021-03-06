package com.xiaomei.yanyu.levelone.home.adapter;

import org.json.JSONObject;

import com.imbryk.viewPager.LoopViewPager;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.viewpagerindicator.PageIndicator;
import com.xiaomei.yanyu.ArrayPagerAdapter;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.XiaoMeiApplication;
import com.xiaomei.yanyu.api.util.Constant;
import com.xiaomei.yanyu.bean.HomeItem;
import com.xiaomei.yanyu.leveltwo.GoodsDetailActivity;
import com.xiaomei.yanyu.leveltwo.TopicDetailSlideActivity;
import com.xiaomei.yanyu.util.ImageUtils;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class TopicView extends BaseView{

    private TopicPageAdapter mTopicPageAdapter = new TopicPageAdapter();
    PageIndicator topicIndicator;
    LoopViewPager mViewPager;

    @Override
    public View setupView() {
        View convertView;
        convertView = mInflater.inflate(R.layout.home_topic_layout, null);
        mViewPager = (LoopViewPager)convertView.findViewById(R.id.pager);
        FrameLayout.LayoutParams flp = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, mScreenWidth*335/720);
        mViewPager.setLayoutParams(flp);
        mViewPager.setBoundaryCaching(true);
        mViewPager.setAutoScrollInterval(Constant.PAGER_SCROLL_INTERVAL);
        topicIndicator = (PageIndicator) convertView.findViewById(R.id.indicator);

        return convertView;
    }

    @Override
    public void refreshUi() {
        mViewPager.setAdapter(mTopicPageAdapter);
        topicIndicator.setViewPager(mViewPager);
        mTopicPageAdapter.clear();
        mTopicPageAdapter.addAll(mData.getmList());
        mTopicPageAdapter.notifyDataSetChanged();
    }

    private class TopicPageAdapter extends ArrayPagerAdapter<HomeItem.Item>{
        DisplayImageOptions options = new DisplayImageOptions.Builder()
        .showImageForEmptyUri(R.drawable.home_topic_item_default)
        .showImageOnLoading(R.drawable.home_topic_item_default)
        .showImageOnFail(R.drawable.home_topic_item_default).build();
        public TopicPageAdapter(){
        }
        @Override
        public Object instantiateItem(ViewGroup paramView, int paramInt) {
             ImageView img = new ImageView(mAc.getApplicationContext());
             ViewGroup.LayoutParams vl = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                     ViewGroup.LayoutParams.MATCH_PARENT);
            img.setLayoutParams(vl);
            img.setScaleType(ScaleType.FIT_XY);
            paramView.addView(img);  
            ImageLoader.getInstance().displayImage(getItem(paramInt).img,img,options);
            img.setTag(getItem(paramInt));
            img.setOnClickListener(mmTopicClickListener);
            ImageUtils.setViewPressState(img);
            return img; 
        }
    }
    
    private View.OnClickListener mmTopicClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View arg0) {
            try {
                HomeItem.Item itme = (HomeItem.Item) arg0.getTag();
                Integer type = Integer.valueOf(itme.type);
                switch (type) {
                case 0://H5形式
                    if(TextUtils.isEmpty(itme.goodsId)){
                        String content = XiaoMeiApplication.getInstance().getResources().getString(R.string.share_default_txt);
                        GoodsDetailActivity.startActivity(mAc,itme.url,itme.title,content,itme.img);
                    }else{
                        String content = XiaoMeiApplication.getInstance().getResources().getString(R.string.share_project_txt);
                        GoodsDetailActivity.startActivity(mAc,itme.url,itme.goodsId,itme.title,content,itme.img);
                    }
                    break;
                case 1: //卡片形式
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
