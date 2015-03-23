package com.xiaomei.levelone.adapter;

import java.util.ArrayList;
import java.util.List;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaomei.R;
import com.xiaomei.bean.Section;
import com.xiaomei.bean.Section.Entity;
import com.xiaomei.util.ScreenUtils;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

public class HomeBannerAdapter extends PagerAdapter {

	private Section mSection;
	
	private Context mContext;
	
	private List<View> mListViews = new ArrayList<View>();
	
	private LayoutInflater mInflater;
	
	public HomeBannerAdapter(Context context){
		mContext = context;
		mInflater = LayoutInflater.from(context);
	}
	
	public Section getSection() {
		return mSection;
	}
	
	public void setSection(Section section) {
		this.mSection = section;
		for(Entity item : mSection.getList()){
			final ViewGroup v = (ViewGroup) mInflater.inflate(R.layout.item_type_banner, null);
			ViewGroup.LayoutParams vl = new LayoutParams(LayoutParams.MATCH_PARENT, ScreenUtils.getScreenWidth(mContext)/2);
			v.setLayoutParams(vl);
			ImageView img = (ImageView) v.findViewById(R.id.imageView);
			v.setTag(img);
			mListViews.add(v);
		}
	}
	
	public List<View> getListViews(){
		return mListViews;
	}

	@Override
	public int getCount() {
		if(mSection==null)
			return 0;
		return mSection.getList() == null? 0:mSection.getList().size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public Object instantiateItem(View container, int position) {
		try {
			((ViewGroup)container).addView(mListViews.get(position),0);
			((View) mListViews.get(position).getTag()).setOnClickListener(null);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return mListViews.get(position);
	}

	
	public static class BannerOnPageChangeListener implements OnPageChangeListener{

		private ImageLoader mImageLoader;
		
		private Section mSection;
		
		private List<View> mListViews;
		
		public void setListViews(List<View> listViews){
			mListViews = listViews;
		}
		
		public Section getSection() {
			return mSection;
		}
		
		public void setSection(Section mSection) {
			this.mSection = mSection;
		}
		public BannerOnPageChangeListener(ImageLoader imgeLoader ){
			mImageLoader = imgeLoader;
		}
		
		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageSelected(int arg0) {
			mImageLoader.displayImage(mSection.getList().get(arg0).getImg(),(ImageView) mListViews.get(arg0).getTag());
		}
		
	}

	
}
