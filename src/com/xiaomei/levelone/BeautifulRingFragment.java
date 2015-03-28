package com.xiaomei.levelone;

import com.xiaomei.R;
import com.xiaomei.widget.TitleBar;
import com.xiaomei.widget.pullrefreshview.PullToRefreshListView;
import com.yuekuapp.BaseFragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

@SuppressLint("NewApi")
public class BeautifulRingFragment extends BaseFragment {
	
	private ViewGroup mRootView;
	
	private TitleBar mTitleBar;
	
	private PullToRefreshListView mPullToRefreshListView;
	
	private ListView mListView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mRootView = (ViewGroup) inflater.inflate(R.layout.fragment_beautifulring_layout, null);
		setUpView();
		return mRootView;
	}
	
	private void setUpView(){
		mTitleBar = (TitleBar) mRootView.findViewById(R.id.titlebar);
		mTitleBar.setTitle(getResources().getString(R.string.fragment_beautiful_ring));
		
		mPullToRefreshListView = (PullToRefreshListView) mRootView.findViewById(R.id.list);
		mListView = mPullToRefreshListView.getRefreshableView();
		mListView.setAdapter(new RingAdapter(getActivity()));
	}

	private class RingAdapter extends BaseAdapter{
		
		private LayoutInflater mLayoutInflater;

		public RingAdapter(Context context) {
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
			if(convertView == null){
				convertView = mLayoutInflater.inflate(R.layout.item_ring_layout, null);
			}
			return convertView;
		}
	
		
	}
	
}
