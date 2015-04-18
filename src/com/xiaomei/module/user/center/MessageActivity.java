package com.xiaomei.module.user.center;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.xiaomei.AbstractActivity;
import com.xiaomei.R;
import com.xiaomei.widget.TitleBar;
import com.xiaomei.widget.pullrefreshview.PullToRefreshListView;

public class MessageActivity extends AbstractActivity {
	
	public static void startActivity(Context context){
		Intent intent = new Intent(context,MessageActivity.class);
		context.startActivity(intent);
	}
	
	private TitleBar mTitleBar;
	
	private MessageAdapter mAdapter;
	
	private PullToRefreshListView mPullToRefreshListView;
	
	private ListView mListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activivty_message_layout);
		setUpView();
	}

	
	private void setUpView(){
		mTitleBar = (TitleBar) findViewById(R.id.titlebar);
		mTitleBar.setTitle(getResources().getString(R.string.user_message));
		mTitleBar.setBackListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		mListView = (ListView) findViewById(R.id.list);
		mAdapter = new MessageAdapter(MessageActivity.this);
		mListView.setAdapter(mAdapter);
	}
	
	private class MessageAdapter extends BaseAdapter{

		private LayoutInflater mLayoutInflater;
		
		public MessageAdapter(Context context) {
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
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(convertView == null)
				convertView = mLayoutInflater.inflate(R.layout.item_message_layout, null);
			return convertView;
		}
		
	}
}
