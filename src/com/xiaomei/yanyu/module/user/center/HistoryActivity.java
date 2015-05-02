package com.xiaomei.yanyu.module.user.center;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.AbstractActivity;
import com.xiaomei.yanyu.bean.HistroyItem;
import com.xiaomei.yanyu.widget.TitleBar;

public class HistoryActivity extends AbstractActivity {
	
	public static void startActivity(Context context){
		Intent intent = new Intent(context,HistoryActivity.class);
		context.startActivity(intent);
	}

	private TitleBar mTitleBar;
	
	private ListView mListView;
	
	private HistoryAdapter mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history_layout);
		setUpView();
	}
	
	private void setUpView(){
		mTitleBar = (TitleBar) findViewById(R.id.titlebar);
		mTitleBar.setTitle(getResources().getString(R.string.user_history));
		mTitleBar.setBackListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
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
//			return mData == null ? 0 : mData.size();
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
			Holder holder = null;
			if(convertView == null){
				convertView  = mInflater.inflate(R.layout.item_history_layout, null);
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
