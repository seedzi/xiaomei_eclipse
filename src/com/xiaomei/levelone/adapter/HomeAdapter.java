package com.xiaomei.levelone.adapter;

import java.util.ArrayList;
import java.util.List;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaomei.R;
import com.xiaomei.bean.Section;
import com.xiaomei.bean.Section.Entity;
import com.xiaomei.util.ScreenUtils;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeAdapter extends BaseAdapter {

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
				holder.mViewPager = (ViewPager) convertView.findViewById(R.id.view_page);
				break;
			case 1:
				convertView = new View(mContext);
				break;
			case 2:
				convertView = mInflater.inflate(R.layout.home_section_tehui, null);
				holder.mTitle = (TextView) convertView.findViewById(R.id.title);
				holder.itemList = new ArrayList<HomeAdapter.Holder.Item>();
				for(Entity entity : entitys){
					Holder.Item item = new Holder.Item();
					ViewGroup itemMiaoSha = (ViewGroup) mInflater.inflate(R.layout.item_tehui, null);
					item.addrView = (TextView) itemMiaoSha.findViewById(R.id.addr);
					item.imgView = (ImageView) itemMiaoSha.findViewById(R.id.img);
					item.priceMarketView = (TextView) itemMiaoSha.findViewById(R.id.price_market);
					item.priceXmView = (TextView) itemMiaoSha.findViewById(R.id.price_xm);
					item.titleView = (TextView) itemMiaoSha.findViewById(R.id.title);
					entitys.add(entity);
					((ViewGroup)convertView).addView(itemMiaoSha);
				}
				break;
			case 3:
				convertView = new View(mContext);
				break;
			case 4:
				convertView = new View(mContext);
				break;
			default:
				break;
			}
		}else{
			holder = (Holder) convertView.getTag();
			int index = 0;
			switch (type) {
			case 0:
				index = 0;
				break;
			case 1:
				break;
			case 2:
				index = 0;
				for(Holder.Item item :holder.itemList){
					setItemData(entitys.get(index), item);
					index ++;	
				}
				break;
			default:
				break;
			}
		}
		return convertView;
	}

	private void setItemData(Entity entity,Holder.Item holderItem){
		if(entity==null || holderItem == null)
			return;
		if(!TextUtils.isEmpty(entity.getAddr()) && holderItem.addrView!=null)
			holderItem.addrView.setText(entity.getAddr());
		if(!TextUtils.isEmpty(entity.getImg()) && holderItem.imgView!=null){
			holderItem.addrView.setHeight(ScreenUtils.getScreenWidth(mContext)/3);
			mImageLoader.displayImage(entity.getImg(), holderItem.imgView);
		}
		if(!TextUtils.isEmpty(entity.getPrice_market()) && holderItem.priceMarketView!=null)
			holderItem.priceMarketView.setText(entity.getPrice_market());
		if(!TextUtils.isEmpty(entity.getPrice_xm()) && holderItem.priceXmView!=null)
			holderItem.priceXmView.setText(entity.getPrice_xm());
		if(!TextUtils.isEmpty(entity.getTitle()) && holderItem.titleView!=null)
			holderItem.titleView.setText(entity.getTitle());
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
			private TextView saledView;
			private TextView stockView;
			private TextView addrView;
		}
		
	}
	

}
