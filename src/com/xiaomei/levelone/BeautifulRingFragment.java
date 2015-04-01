package com.xiaomei.levelone;

import com.xiaomei.R;
import com.xiaomei.bean.BeautifulRing;
import com.xiaomei.levelone.control.BeautifulRingControl;
import com.xiaomei.widget.TitleBar;
import com.xiaomei.widget.pullrefreshview.PullToRefreshListView;
import com.xiaomei.widget.pullrefreshview.PullToRefreshBase.OnRefreshListener;
import com.yuekuapp.BaseFragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

@SuppressLint("NewApi")
public class BeautifulRingFragment extends BaseFragment<BeautifulRingControl> implements OnRefreshListener,OnScrollListener{
	
	private boolean mIsRefresh;
	
	private ViewGroup mRootView;
	
	private TitleBar mTitleBar;
	
	private PullToRefreshListView mPullToRefreshListView;
	
	private ListView mListView;
	
	private RingAdapter mAdapter;
	
	private ViewGroup mRefreshLayout;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mRootView = (ViewGroup) inflater.inflate(R.layout.fragment_beautifulring_layout, null);
		setUpView();
		setListener();
		return mRootView;
	}
	
	private void setUpView(){
		mTitleBar = (TitleBar) mRootView.findViewById(R.id.titlebar);
		mTitleBar.setTitle(getResources().getString(R.string.fragment_beautiful_ring));
		
		mPullToRefreshListView = (PullToRefreshListView) mRootView.findViewById(R.id.list);
		mListView = mPullToRefreshListView.getRefreshableView();
		mAdapter = new RingAdapter(getActivity());
		mListView.setAdapter(mAdapter);
		
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		mRefreshLayout = (ViewGroup) inflater.inflate(R.layout.pull_to_refresh_footer, null);
	}
	
	private void setListener(){
		mPullToRefreshListView.setOnRefreshListener(this);
		mPullToRefreshListView.setOnScrollListener(this);
	}
	
	@Override
	public void onRefresh() {
		mIsRefresh = true;
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				mPullToRefreshListView.onRefreshComplete();
			}
		}, 3000);
	}
	
//	public void get
	

	private class RingAdapter extends BaseAdapter implements View.OnClickListener{
		
		private LayoutInflater mLayoutInflater;

		public RingAdapter(Context context) {
			mLayoutInflater = LayoutInflater.from(context);
		}
		
		public int getDataSize(){
			return 10;
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
			Holder holder = null;
			if(convertView == null){
				convertView = mLayoutInflater.inflate(R.layout.item_ring_layout, null);
				holder = new Holder();
				holder.userIconIv = (ImageView) convertView.findViewById(R.id.user_icon);
				holder.userNaemTv = (TextView) convertView.findViewById(R.id.user_name);
				holder.titleTv = (TextView) convertView.findViewById(R.id.title);
				holder.timeTv = (TextView) convertView.findViewById(R.id.time);
				holder.bubleSizeTv = (TextView) convertView.findViewById(R.id.buble_size);
				holder.likeSizeTv = (TextView) convertView.findViewById(R.id.like_size);
				convertView.setTag(holder);
				convertView.setOnClickListener(this);
			}
			holder = (Holder) convertView.getTag();
			return convertView;
		}
		
		private void setData(Holder holder,BeautifulRing data){
		}

		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			
		}
		
		private class Holder{
			private ImageView userIconIv;
			private TextView userNaemTv;
			private TextView timeTv;
			private TextView titleTv;
			private TextView descriptionTv;
			private TextView likeSizeTv;
			private TextView bubleSizeTv;
		}
		
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		int position = mListView.getLastVisiblePosition();
		if(!mIsRefresh && position == mAdapter.getDataSize()){
			mIsRefresh = true;
			getMoreData();
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
	}
	
	private void getMoreData(){
		if(mRefreshLayout.isShown())
			mRefreshLayout.setVisibility(View.VISIBLE);
		mPullToRefreshListView.addFooterView(mRefreshLayout);
	}

	

	
}
