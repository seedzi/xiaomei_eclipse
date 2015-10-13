package com.xiaomei.yanyu.levelone.home.adapter;

import com.imbryk.viewPager.LoopViewPager;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.viewpagerindicator.PageIndicator;
import com.xiaomei.yanyu.ArrayPagerAdapter;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.api.util.Constant;
import com.xiaomei.yanyu.bean.HomeItem;
import com.xiaomei.yanyu.leveltwo.GoodsDetailActivity;
import com.xiaomei.yanyu.util.ImageLoaderUtil;
import com.xiaomei.yanyu.util.ImageUtils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

public class ConsultationView extends BaseView {
    private ConsultationPageAdapter mConsultationPageAdapter = new ConsultationPageAdapter();

    LoopViewPager mViewPager;
    ImageView mRecite;
    PageIndicator consultationIndicator;
    @Override
    public View setupView() {
        View convertView;
        convertView = mInflater.inflate(R.layout.home_consultation_layout, null);
        
        mViewPager = (LoopViewPager)convertView.findViewById(R.id.pager);
        FrameLayout.LayoutParams fl = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, mScreenWidth*335/720);
        mViewPager.setLayoutParams(fl);
        mViewPager.setBoundaryCaching(true);
        mViewPager.setAutoScrollInterval(Constant.PAGER_SCROLL_INTERVAL);
        consultationIndicator = (PageIndicator) convertView.findViewById(R.id.indicator);
        
        mRecite = (ImageView) convertView.findViewById(R.id.recite);
        mRecite.setOnClickListener(this);

        return convertView;
    }

    @Override
    public void refreshUi() {

    	LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(mScreenWidth, mScreenWidth*150/1242);
    	mRecite.setLayoutParams(ll);
    	
        DisplayImageOptions reciteOptions = ImageLoaderUtil.getDisplayOptions(R.drawable.consultation_recite);
        ImageLoader.getInstance().displayImage(mData.getRecite().img, mRecite,reciteOptions);
        mRecite.setTag(mData.getRecite().jump);
        ImageUtils.setViewPressState(mRecite);
        mConsultationPageAdapter.clear();
        mConsultationPageAdapter.addAll(mData.getmList());
        mViewPager.setAdapter(mConsultationPageAdapter);
        consultationIndicator.setViewPager(mViewPager);
        mConsultationPageAdapter.notifyDataSetChanged();
    }
    
    private class ConsultationPageAdapter extends ArrayPagerAdapter<HomeItem.Item>{
        DisplayImageOptions options = ImageLoaderUtil.getDisplayOptions(R.drawable.home_consultation_default);
        public ConsultationPageAdapter(){
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
            img.setTag(getItem(paramInt).jump);
            ImageUtils.setViewPressState(img);
            img.setOnClickListener(mConsultationClickListener);
            return img; 
        }
    }
    
    private View.OnClickListener mConsultationClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View arg0) {
            String jump = (String) arg0.getTag();
            GoodsDetailActivity.startActivity(mAc, jump);
        }
    };
}
