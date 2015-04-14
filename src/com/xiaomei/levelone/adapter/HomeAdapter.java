package com.xiaomei.levelone.adapter;

import java.util.List;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaomei.R;
import com.xiaomei.bean.Section;
import com.xiaomei.leveltwo.WebViewActivity;
import com.xiaomei.util.ScreenUtils;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("NewApi")
public class HomeAdapter extends BaseAdapter implements View.OnClickListener{

	private List<Section> mData;
	
	private LayoutInflater mInflater;
	
	private Context mContext;
	
	public HomeAdapter(List<Section> data,Context context,ImageLoader imageLoader){
		mData = data;
		mInflater = LayoutInflater.from(context);
		mContext = context;
	}
	
	public void setData(List<Section> data){
		mData = data;
	}
	
	public List<Section> getData(){
		return mData;
	}
	
	@Override
	public int getCount() {
		return mData == null ?0:mData.size();
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
		int type = getItemViewType(position);
		Section section = mData.get(position);
		Holder holder = null;
		if(convertView == null){
			holder = new Holder();
			switch (type) {
			case 0:  // 热点
				convertView = mInflater.inflate(R.layout.section_topic, null);
				convertView.setOnClickListener(this);
				attachView2Holder(holder, (ViewGroup)convertView, 0, 0);
				break;
			case 1:  // 导航
				convertView = mInflater.inflate(R.layout.section_nav, null);
				convertView.findViewById(R.id.youhui).setOnClickListener(this);
				convertView.findViewById(R.id.youhui).setTag(section.getList().get(0).getUrl());
				convertView.findViewById(R.id.payment).setOnClickListener(this);
				convertView.findViewById(R.id.payment).setTag(section.getList().get(1).getUrl());
				convertView.findViewById(R.id.riji).setOnClickListener(this);
				convertView.findViewById(R.id.riji).setTag(section.getList().get(2).getUrl());
				convertView.findViewById(R.id.jiangli).setOnClickListener(this);
				convertView.findViewById(R.id.jiangli).setTag(section.getList().get(3).getUrl());
				break;
			case 2:  // 商品精选		if(holder.commontNumView!=null && en)
				convertView = mInflater.inflate(R.layout.section_jingxuan, null);
				convertView.setOnClickListener(this);
				attachView2Holder(holder, (ViewGroup)convertView, 0, 0);
				break;
			case 3:  // 分享
				convertView =  mInflater.inflate(R.layout.section_share, null);
				convertView.setOnClickListener(this);
				attachView2Holder(holder, (ViewGroup)convertView, 0, 0);
				break;
			case 4:   //会员活动
				convertView =  mInflater.inflate(R.layout.section_vip, null);
				convertView.setOnClickListener(this);
				attachView2Holder(holder, (ViewGroup)convertView, 0, 0);
				break;
			default:
				break;
			}
			convertView.setTag(holder);
		}
		holder = (Holder) convertView.getTag();
		attachData2Holder(position, section, holder);	
		switch (type) {
		case 0:
			layoutView(convertView,1);
			break;
		default:
			break;
		}
		return convertView;
	}

	private void attachData2Holder(int position ,Section section ,Holder holder){
		Section.Entity entity = null;
		if(section==null || holder == null)
			return;
		switch (position) {
		case 1: // 导航
			
			break;
		case 3:  //分享
			entity =  section.getList().get(0);
			if(holder.personIconView!=null && !TextUtils.isEmpty( entity.getNumcomment()))
//				ImageLoader.getInstance().displayImage(entity.get, holder.personIconView); //TODO
			if(holder.personnameView!=null && !TextUtils.isEmpty( entity.getNumfavorite()))
				holder.personnameView.setText(entity.getNumfavorite());
			if(holder.timeView!=null && !TextUtils.isEmpty( entity.getDate()))
				holder.timeView.setText(entity.getDate());
			if(holder.imgView!=null && !TextUtils.isEmpty( entity.getImg()))
				ImageLoader.getInstance().displayImage(entity.getImg(), holder.imgView);
			if(holder.titleView!=null && !TextUtils.isEmpty( entity.getTitle()))
				holder.titleView.setText(entity.getTitle());
			if(holder.commontNumView!=null && !TextUtils.isEmpty( entity.getNumcomment()))
				holder.commontNumView.setText(entity.getNumcomment());
			if(holder.favoriteNumView!=null && !TextUtils.isEmpty( entity.getNumfavorite()))
				holder.favoriteNumView.setText(entity.getNumfavorite());
				holder.url = entity.getUrl();
			break;
		case 0:
		case 2:
		case 4:
			entity =  section.getList().get(0);
//			if(holder.titleView!=null && !TextUtils.isEmpty(section.getTitle()))
//				holder.titleView.setText(section.getTitle()); //TODO
			if(holder.imgView!=null && !TextUtils.isEmpty(entity.getImg()))
				ImageLoader.getInstance().displayImage(entity.getImg(), holder.imgView);
			holder.url = entity.getUrl();
			break;
		default:
			break;
		}
	}
	
	@Override
	public int getViewTypeCount() {
		return 5;
	}
	
	@Override
	public int getItemViewType(int position) {
		Section section = mData.get(position);
		if(section.getKey().equals("topic"))
			return 0;
		if(section.getKey().equals("nav"))
			return 1;
		if(section.getKey().equals("jingxua"))
			return 2;
		if(section.getKey().equals("share"))
			return 3;
		if(section.getKey().equals("vip"))
			return 4;
		return 4;
	}
	
	
	private void attachView2Holder(Holder holder,ViewGroup vGroup,int imageWidth,int imageHeight){
		holder.personIconView =  (ImageView) vGroup.findViewById(R.id.person_icon);
		holder.personnameView = (TextView) vGroup.findViewById(R.id.person_name);
		holder.timeView = (TextView) vGroup.findViewById(R.id.time);
		holder.imgView = (ImageView) vGroup.findViewById(R.id.icon);
		holder.titleView = (TextView) vGroup.findViewById(R.id.title);
		holder.commontNumView = (TextView) vGroup.findViewById(R.id.like_size);
		//TODO :处理图片大小
//		holder.favoriteNumView.getLayoutParams().width = imageWidth;
	}
	
	private static class Holder{
//		List<Item> itemList;
//		static class Item{
			private ImageView personIconView; //用户icon
			private TextView personnameView;  //用户名
			private TextView timeView;  //时间
			private ImageView imgView;  //icon图
			private TextView titleView; //标题
			private TextView commontNumView;  //评论数
			private TextView favoriteNumView; //点赞
			private String url ;//详情页url
//		}
	}

	@Override
	public void onClick(View v) {
		Holder holder = (Holder) v.getTag();
		WebViewActivity.startActivity(mContext, holder.url);
	}
	
	private void layoutView(View view,int proportion){
		AbsListView.LayoutParams ll = new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, ScreenUtils.getScreenWidth(mContext)/proportion);
		view.setLayoutParams(ll);
	}
}
