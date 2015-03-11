package com.xiaomei.levelone.adapter;

import java.util.ArrayList;
import java.util.List;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaomei.R;
import com.xiaomei.bean.Section;
import com.xiaomei.bean.Section.Entity;
import com.xiaomei.leveltwo.WebViewActivity;
import com.xiaomei.util.ScreenUtils;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class HomeAdapter extends BaseAdapter implements View.OnClickListener{

	private List<Section> mData;
	
	private LayoutInflater mInflater;
	
	private Context mContext;
	
	private ImageLoader mImageLoader;
	
	public HomeAdapter(List<Section> data,Context context,ImageLoader imageLoader){
		mData = data;
		mInflater = LayoutInflater.from(context);
		mContext = context;
		mImageLoader = imageLoader;
	}
	
	public void setData(List<Section> data){
		mData = data;
	}
	
	@Override
	public int getCount() {
		return mData == null ?0:mData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		int type = getItemViewType(position);
		Section section = mData.get(position);
		List<Entity> entitys = section.getList();
		Holder holder = null;
		if(convertView == null){
			holder = new Holder();
			switch (type) {
			case 0:
				convertView = mInflater.inflate(R.layout.home_item_banner, null);
//				holder.mViewPager = (ViewPager) convertView.findViewById(R.id.view_page);
				break;
			case 1:
				convertView = new View(mContext);
				break;
			case 2:
				convertView = mInflater.inflate(R.layout.section_tehui, null);
				holder.mTitle = (TextView) convertView.findViewById(R.id.title);
				holder.itemList = new ArrayList<HomeAdapter.Holder.Item>();
				for(Entity entity : entitys){
					Holder.Item item = new Holder.Item();
					ViewGroup itemMiaoSha = (ViewGroup) mInflater.inflate(R.layout.item_type_tehui, null);
					attachView2Holder(item, itemMiaoSha,ScreenUtils.getScreenWidth(mContext) ,ScreenUtils.getScreenWidth(mContext)/2 );
					holder.itemList.add(item);
					((ViewGroup)convertView).addView(itemMiaoSha);
					itemMiaoSha.setTag(entity.getUrl());
					itemMiaoSha.setOnClickListener(this);
				}
				break;
			case 3:
				convertView =  mInflater.inflate(R.layout.section_duxiang, null);
				holder.mTitle = (TextView) convertView.findViewById(R.id.title);
				holder.itemList = new ArrayList<HomeAdapter.Holder.Item>();
				for(Entity entity : entitys){
					Holder.Item item = new Holder.Item();
					ViewGroup itemDuXiang = (ViewGroup) mInflater.inflate(R.layout.item_type_duxiang, null);
					attachView2Holder(item, itemDuXiang,ScreenUtils.getScreenWidth(mContext) ,ScreenUtils.getScreenWidth(mContext)/2 );
					holder.itemList.add(item);
					((ViewGroup)convertView).addView(itemDuXiang);
					itemDuXiang.setTag(entity.getUrl());
					itemDuXiang.setOnClickListener(this);
				}
				break;
			case 4:
				convertView =  mInflater.inflate(R.layout.section_jingxuan, null);
				holder.mTitle = (TextView) convertView.findViewById(R.id.title);
				holder.itemList = new ArrayList<HomeAdapter.Holder.Item>();
				for(Entity entity : entitys){
					Holder.Item item = new Holder.Item();
					ViewGroup itemJingXuan = (ViewGroup) mInflater.inflate(R.layout.item_type_jingxuan, null);
					attachView2Holder(item, itemJingXuan,(int)mContext.getResources().getDimension(R.dimen.item_icon_size) ,(int)mContext.getResources().getDimension(R.dimen.item_icon_size));
					holder.itemList.add(item);
					((ViewGroup)convertView).addView(itemJingXuan);
					itemJingXuan.setTag(entity.getUrl());
					itemJingXuan.setOnClickListener(this);
				}
				break;
			default:
				break;
			}
			convertView.setTag(holder);
		}
		holder = (Holder) convertView.getTag();
		int index = 0;
		switch (type) {
		case 0:
			index = 0;
			break;
		case 1:
			break;
		case 2:
			holder.mTitle.setText("每日优惠");
			index = 0;
			for(Holder.Item item :holder.itemList){
				setItemData(entitys.get(index), item);
				index ++;	
			}
			break;
		case 3:
			holder.mTitle.setText("新用户独享");
			index = 0;
			for(Holder.Item item :holder.itemList){
				setItemData(entitys.get(index), item);
				index ++;	
			}
			break;
		case 4:
			holder.mTitle.setText("精选");
			index = 0;
			for(Holder.Item item :holder.itemList){
				setItemData(entitys.get(index), item);
				index ++;	
			}
			break;
		default:
			break;
		}
		return convertView;
	}

	private void setItemData(Entity entity,Holder.Item holderItem){
		if(entity==null || holderItem == null)
			return;
		if(!TextUtils.isEmpty(entity.getAddr()) && holderItem.addrView!=null)
			holderItem.addrView.setText(entity.getAddr());
		if(!TextUtils.isEmpty(entity.getImg()) && holderItem.imgView!=null){
			mImageLoader.displayImage(entity.getImg(), holderItem.imgView);
		}
		if(!TextUtils.isEmpty(entity.getPrice_market()) && holderItem.priceMarketView!=null)
			holderItem.priceMarketView.setText(entity.getPrice_market());
		if(!TextUtils.isEmpty(entity.getPrice_xm()) && holderItem.priceXmView!=null)
			holderItem.priceXmView.setText(entity.getPrice_xm());
		if(!TextUtils.isEmpty(entity.getTitle()) && holderItem.titleView!=null)
			holderItem.titleView.setText(entity.getTitle());
		if(!TextUtils.isEmpty(entity.getPrice_market_h()) && holderItem.priceMarketHView!=null)
			holderItem.priceMarketHView.setText(entity.getPrice_market());
	}
	
	private void attachView2Holder(Holder.Item item,ViewGroup vGroup,int imageWidth,int imageHeight){
		item.titleView = (TextView) vGroup.findViewById(R.id.title);
		item.addrView = (TextView) vGroup.findViewById(R.id.addr);
		item.imgView = (ImageView) vGroup.findViewById(R.id.img);
		item.priceMarketView = (TextView) vGroup.findViewById(R.id.price_market);
		item.priceMarketHView = (TextView) vGroup.findViewById(R.id.price_market_h);
		item.priceXmView = (TextView) vGroup.findViewById(R.id.price_xm);
		item.imgView.getLayoutParams().width = imageWidth;
		item.imgView.getLayoutParams().height = imageHeight;
	}
	
	
	@Override
	public int getViewTypeCount() {
		return 5;
	}
	
	@Override
	public int getItemViewType(int position) {
		return position;
	}
	
	private static class Holder{
		
		private ViewPager mViewPager;
		private TextView mTitle;
		private List<Item> itemList;
		
		static class Item{
			private ImageView imgView;
			private TextView titleView;
			private TextView priceXmView;
			private TextView priceMarketView;
			private TextView priceMarketHView;
			private TextView saledView;
			private TextView stockView;
			private TextView addrView;
		}
		
	}

	@Override
	public void onClick(View v) {
		String url = (String) v.getTag();
		WebViewActivity.startActivity(mContext, url);
	}
	

}
