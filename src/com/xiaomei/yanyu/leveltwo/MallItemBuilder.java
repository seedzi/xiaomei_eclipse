package com.xiaomei.yanyu.leveltwo;

import java.util.HashMap;

import com.xiaomei.yanyu.R;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class MallItemBuilder {
	
	private HashMap<String, Object> mChannelDataCache;
	
	private ListView mListView;
	
	private Handler mHandler = new Handler();
	
	private ViewGroup mAttachView;
	
	private ProductAdapter mAdapter;
	
	public MallItemBuilder(ViewGroup attachView){
		mAttachView = attachView;
		mListView = (ListView) attachView.findViewById(R.id.list_view);

	}
	
	public void show(Context context){
		mAdapter = new ProductAdapter(context);
		mListView.setAdapter(mAdapter);
	}
	
	/**列表adapter*/
	private class ProductAdapter extends BaseAdapter{
		
		private LayoutInflater inflater;
		
		public ProductAdapter(Context context){
			inflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return 10;
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
			if(convertView == null)
				convertView = inflater.inflate(R.layout.item_mall_channel_item_layout, null);
			return convertView;
		}
		
	}

}
