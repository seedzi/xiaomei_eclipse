package com.xiaomei.module.history;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.xiaomei.BaseActiviy;
import com.xiaomei.R;
import com.xiaomei.bean.HistroyItem;
import com.xiaomei.module.history.control.HistoryControl;

public class HistoryActivity extends BaseActiviy<HistoryControl> {

	private ListView mListView;
	
	private HistoryAdapter mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history_layout);
		initView();
	}
	
	private void initView(){
		mListView = (ListView) findViewById(R.id.list);
		mAdapter = new HistoryAdapter(this);
		mListView.setAdapter(mAdapter);
	}
	
	private class HistoryAdapter extends BaseAdapter{

		private List<HistroyItem>  mData;
		
		public void setData(List<HistroyItem> data){
			mData = data;
		}
		
		private LayoutInflater mInflater;
		
		public HistoryAdapter(Context context){
			mInflater = LayoutInflater.from(context);
		}
		
		@Override
		public int getCount() {
			return mData == null ? 0 : mData.size();
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
			Holder holder = null;
			if(convertView == null){
				convertView  = mInflater.inflate(R.layout.item_history, null);
				holder = new Holder();
				holder.titleView = (TextView) convertView.findViewById(R.id.title);
				holder.iconView = (ImageView) convertView.findViewById(R.id.icon);
				holder.starView = (ImageButton) convertView.findViewById(R.id.and_star);
				convertView.setTag(holder);
			}
			holder = (Holder) convertView.getTag();
			
			return convertView;
		}
		
		private class Holder{
			private TextView titleView;
			private ImageView iconView;
			private ImageButton deleteView;
			private ImageButton starView;
		}
		
		
	}
	
}
