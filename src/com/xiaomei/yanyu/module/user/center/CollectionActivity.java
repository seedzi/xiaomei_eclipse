package com.xiaomei.yanyu.module.user.center;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.AbstractActivity;
import com.xiaomei.yanyu.widget.TitleBar;

public class CollectionActivity extends AbstractActivity {
	
	public static void startActivity(Context context){
		Intent intent = new Intent(context,CollectionActivity.class);
		context.startActivity(intent);
	}

	private TitleBar mTitleBar;
	
	private ListView mListView;
	
	private ColleactionAdapter mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_collection_layout);
		setUpView();
	}
	
	private void setUpView(){
		mTitleBar = (TitleBar) findViewById(R.id.titlebar);
		mTitleBar.setTitle(getResources().getString(R.string.user_collection));
		mTitleBar.setBackListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		mListView = (ListView) findViewById(R.id.list);
		mAdapter = new ColleactionAdapter(CollectionActivity.this);
		mListView.setAdapter(mAdapter);
	}
	
	private class ColleactionAdapter extends BaseAdapter{
		
		private LayoutInflater mLayoutInflater;
		
		public ColleactionAdapter(Context context){
			mLayoutInflater = LayoutInflater.from(context);
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
				convertView = mLayoutInflater.inflate(R.layout.item_history_layout, null);
			return convertView;
		}
		
	}
}
