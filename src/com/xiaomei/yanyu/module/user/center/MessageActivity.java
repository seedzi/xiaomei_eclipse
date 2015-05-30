package com.xiaomei.yanyu.module.user.center;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.AbstractActivity;
import com.xiaomei.yanyu.bean.UserMessage;
import com.xiaomei.yanyu.module.user.center.control.UserCenterControl;
import com.xiaomei.yanyu.widget.TitleBar;
import com.xiaomei.yanyu.widget.pullrefreshview.PullToRefreshListView;

public class MessageActivity extends AbstractActivity<UserCenterControl> implements OnScrollListener{
	
	public static void startActivity(Activity ac){
		Intent intent = new Intent(ac,MessageActivity.class);
		ac.startActivity(intent);
		ac.overridePendingTransition(R.anim.activity_slid_in_from_right, R.anim.activity_slid_out_no_change);
	}
	
	private TitleBar mTitleBar;
	
	private MessageAdapter mAdapter;
	
	private PullToRefreshListView mPullToRefreshListView;
	
	private ListView mListView;

	private boolean mIsRefresh = false;
	
	private ViewGroup mRefreshLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activivty_message_layout);
		setUpView();
		initData();
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
		mListView.setOnScrollListener(this);
		
		LayoutInflater inflater = LayoutInflater.from(this);
		mRefreshLayout = (ViewGroup) inflater.inflate(R.layout.pull_to_refresh_footer, null);
		
	}
	
	private void initData(){
		mControl.getUserMsg();
	}
	
	// =================================== Scroll ========================================
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		int position = mListView.getLastVisiblePosition();
		if(!mIsRefresh && position == mAdapter.getCount() ){
			getMoreData();
		}
	}
	// =================================== Scroll ========================================
	
	private void getMoreData(){
		if(!mRefreshLayout.isShown())
			mRefreshLayout.setVisibility(View.VISIBLE);
		mPullToRefreshListView.addFooterView(mRefreshLayout);
		mControl.getUserMsgMore();
		mIsRefresh = true;
	}
	
	// =================================== CallBack ========================================
	public void getUserMsgCallBack(){
		mAdapter.setData(mControl.getModel().getUserMessage());
		mAdapter.notifyDataSetChanged();
	}
	
	public void getUserMsgExceptionCallBack(){
		Toast.makeText(this, "获取数据异常", 0).show();
	}
	
	public void getUserMsgMoreCallBack(){
		mIsRefresh = false;
		mPullToRefreshListView.removeFooterView(mRefreshLayout);
		mAdapter.getData().addAll(mControl.getModel().getUserMessage());
		mAdapter.notifyDataSetChanged();
	}
	
	public void getUserMsgMoreExceptionCallBack(){
		Toast.makeText(this, "获取数据异常", 0).show();
	}
	// =================================== Adapter ========================================
	private class MessageAdapter extends BaseAdapter{

		private LayoutInflater mLayoutInflater;
		
		private List<UserMessage> data;
		
		public List<UserMessage> getData(){
			return data;
		}
		
		public void setData(List<UserMessage> list){
			data = list;
		}
		
		public MessageAdapter(Context context) {
			mLayoutInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return data == null?0:data.size();
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
			Holder holder = null;
			if(convertView == null){
				convertView = mLayoutInflater.inflate(R.layout.item_message_layout, null);
				holder = new Holder();
				holder.titleTv = (TextView) convertView.findViewById(R.id.title);
				holder.contentTv = (TextView) convertView.findViewById(R.id.content);
				convertView.setTag(holder);
			}
			holder = (Holder) convertView.getTag();
			UserMessage messgae = data.get(position);
			holder.titleTv.setText(messgae.getTitle());
			holder.contentTv.setText(messgae.getContent());
			return convertView;
		}
		
		private class Holder{
			private TextView titleTv;
			private TextView contentTv;
		}
	}

}
