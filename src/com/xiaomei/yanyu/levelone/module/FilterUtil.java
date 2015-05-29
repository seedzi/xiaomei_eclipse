package com.xiaomei.yanyu.levelone.module;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;





import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.XiaoMeiApplication;

import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FilterUtil {
	
	
	public static final int FilterUtil_MOVIE = 0;
	public static final int FilterUtil_TV = 1;
	public static final int FilterUtil_CARTOON = 2;
	
	private static String[] MOVIE_TYPES ;
	private static String[] MOVIE_AREOS ;
	private static String[] TV_TYPES ;
	private static String[] TV_AREOS ;
	private static String[] CARTOON_TYPES ;
	private static String[] CARTOON_AREOS ;
	private static String[] YEARS;
	
	private static boolean isInit = false;
	
	private void init(){
		Resources res = XiaoMeiApplication.getInstance().getResources();
		MOVIE_TYPES = res.getStringArray(R.array.filter_movie_type);
		MOVIE_AREOS = res.getStringArray(R.array.filter_movie_areo);
		TV_TYPES = res.getStringArray(R.array.filter_tv_type);
		TV_AREOS = res.getStringArray(R.array.filter_tv_areo);
		CARTOON_TYPES = res.getStringArray(R.array.filter_cartoon_type);
		CARTOON_AREOS = res.getStringArray(R.array.filter_cartoon_areo);
		
		ArrayList<String> years = new ArrayList<String>();
		years.add(res.getString(R.string.all));
		for(int year = Calendar.getInstance().get(Calendar.YEAR),i=1;year>=2002;year--){
			years.add(year+"");			
			i++;
		}
		YEARS = years.toArray(new String[years.size()]);
	}
	

	private String[] types ;
	private String[] areos ;
	private String[] years ;
	
	private ViewGroup filer_layout;
	
	private boolean isShow = false;
	
	private Filter mFilter;
	
	private List<TextView> type_textViews = new ArrayList<TextView>();
	private List<TextView> areo_textViews = new ArrayList<TextView>();
	private List<TextView> year_textViews = new ArrayList<TextView>();

	
	private View.OnClickListener mOnItemListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Tag tag = (Tag) v.getTag();
			switch (tag.data_type) {
				case DATA_TYPE_TYPE:
					changeTextColor(type_textViews);
					mFilter.type = tag.data;
					break;
				case DATA_TYPE_AREO:
					changeTextColor(areo_textViews);
					mFilter.areo = tag.data;
					break;
				case DATA_TYPE_YEAR:
					changeTextColor(year_textViews);
					mFilter.year = tag.data;
					break;
				default:
					break;
			}
			((TextView)v.findViewById(R.id.text)).setTextColor(Color.parseColor("#ff1b96d5"));
			if(mListener!=null){
				mListener.onItemSelected(mFilter);
			}
		}
	};
	
	private void changeTextColor(List<TextView> list){
		for(TextView tv:list){
			tv.setTextColor(Color.parseColor("#ff393939")); 
		}
	}
	
	public Filter getFilter(){
		return mFilter;
	}
	
	public FilterUtil(ViewGroup continer,LayoutInflater mInflater,int type){
		if(!isInit){
			init();
			isInit = true;
		}
		
		mFilter = new Filter();
		switch (type) {
		case FilterUtil_MOVIE:
			types = MOVIE_TYPES;
			areos = MOVIE_AREOS;
			break;
		case FilterUtil_TV:
			types = TV_TYPES;
			areos = TV_AREOS;
			break;
		case FilterUtil_CARTOON:
			types = CARTOON_TYPES;
			areos = CARTOON_AREOS;
			break;
		default:
			break;
		}
		years = YEARS;
		continer.addView(createfilterLayout(mInflater));
	}
	
	public ViewGroup createfilterLayout(LayoutInflater mInflater){
		
		filer_layout = (LinearLayout) mInflater.inflate(R.layout.filter_layout, null);
		ViewGroup filer_body = (ViewGroup) filer_layout.findViewById(R.id.filter_body);
		
		ViewGroup item1 = (ViewGroup) mInflater.inflate(R.layout.filter_layout_item, null);
		ViewGroup item2 = (ViewGroup) mInflater.inflate(R.layout.filter_layout_item, null);
		ViewGroup item3 = (ViewGroup) mInflater.inflate(R.layout.filter_layout_item, null);

		filer_body.addView(item1);
		filer_body.addView(item2);
		filer_body.addView(item3);
		item3.findViewById(R.id.divilder).setVisibility(View.GONE);
		
		initItemLayout(mInflater, (ViewGroup)item1.findViewById(R.id.filter_options), types , DATA_TYPE_TYPE);
		initItemLayout(mInflater, (ViewGroup)item2.findViewById(R.id.filter_options), areos , DATA_TYPE_AREO);
		initItemLayout(mInflater, (ViewGroup)item3.findViewById(R.id.filter_options), years , DATA_TYPE_YEAR); 
		
		filer_layout.setVisibility(View.INVISIBLE);
		return filer_layout;
	}  
	
	private void initItemLayout(LayoutInflater mInflater,ViewGroup layout ,String[] strs , int dataType){
		for(String str : strs){
			ViewGroup item = (ViewGroup) mInflater.inflate(R.layout.filter_options_item, null);
			TextView text = (TextView) item.findViewById(R.id.text);
			text.setText(str);
			switch (dataType) {
				case DATA_TYPE_TYPE:
					if(type_textViews.size()==0)
						text.setTextColor(Color.parseColor("#ff1b96d5"));
					type_textViews.add(text);
					break;
				case DATA_TYPE_AREO:
					if(areo_textViews.size()==0)
						text.setTextColor(Color.parseColor("#ff1b96d5"));
					areo_textViews.add(text);
					break;
				case DATA_TYPE_YEAR:
					if(year_textViews.size()==0)
						text.setTextColor(Color.parseColor("#ff1b96d5"));
					year_textViews.add(text);
					break;
				default:
					break;
			}
			Tag tag = new Tag();
			tag.data_type = dataType;
			tag.data = str;
			item.setTag(tag);
			item.setOnClickListener(mOnItemListener);
			layout.addView(item);
		}
	}
	
	public void show(){
		TranslateAnimation tAnimation = null;
		tAnimation = 
				new TranslateAnimation(0,0,-filer_layout.getHeight(),0);
		tAnimation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation arg0) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onAnimationRepeat(Animation arg0) {
				// TODO Auto-generated method stub		
			}				
			@Override
			public void onAnimationEnd(Animation arg0) {
				// TODO Auto-generated method stub	
				filer_layout.setVisibility(View.VISIBLE);
			}
		});
		tAnimation.setDuration(300);
		filer_layout.startAnimation(tAnimation);
		isShow = true;	
		if(mHideShowListener!=null)
		    mHideShowListener.onShow();
	}
	
	public void hide(){
		TranslateAnimation tAnimation = null;
		tAnimation = 
				new TranslateAnimation(0,0,0,-filer_layout.getHeight());
		tAnimation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation arg0) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onAnimationRepeat(Animation arg0) {
				// TODO Auto-generated method stub		
			}				
			@Override
			public void onAnimationEnd(Animation arg0) {
				// TODO Auto-generated method stub	
				filer_layout.setVisibility(View.INVISIBLE);
			}
		});
		tAnimation.setDuration(300);
		filer_layout.startAnimation(tAnimation);
		isShow = false;	
		  if(mHideShowListener!=null)
	            mHideShowListener.onHide();
	}
	
	public boolean isShow(){
		return isShow;
	}
	
	private final static int DATA_TYPE_TYPE = 0;
	private final static int DATA_TYPE_AREO = 1;
	private final static int DATA_TYPE_YEAR = 2;
	
	private class Tag{
		private int data_type;
		private String data;
	}
		
	public static class Filter{
		private static final String DEFAULT_TYPE = XiaoMeiApplication.getInstance().getResources().getString(R.string.all);
		private static final String DEFAULT_AREO = XiaoMeiApplication.getInstance().getResources().getString(R.string.all);
		private static final String DEFAULT_YEAR = XiaoMeiApplication.getInstance().getResources().getString(R.string.all);	
		public String type = DEFAULT_TYPE;
		public String areo = DEFAULT_AREO;
		public String year = DEFAULT_YEAR;
	}
	
	private OnItemSelectedListener mListener;
	
	public interface OnItemSelectedListener{
		public void onItemSelected(Filter mFilter);
	}
	
	public void setItemSelectedListener(OnItemSelectedListener listener){
		mListener = listener;
	}
	
	private Listener mHideShowListener;
	public void setListener(Listener listener){
	    mHideShowListener = listener;
	}
	public interface Listener{
	    public void onHide();
	    public void onShow();;
	}
}
