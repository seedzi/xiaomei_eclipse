package com.xiaomei.levelone;

import com.xiaomei.R;
import com.xiaomei.widget.TitleBar;
import com.xiaomei.widget.pullrefreshview.PullToRefreshExpandableListView;
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
public class MechanismFragment extends BaseFragment {
	
	private ViewGroup mRootView;
	
	private PullToRefreshExpandableListView mPullToRefreshExpandableListView;
	
	private ListView mListView;
	
	private TitleBar mTitleBar;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mRootView = (ViewGroup) inflater.inflate(R.layout.fragment_mechanism_layout, null);
		setUpView();
		return mRootView;
	}
	
	private void setUpView(){
		mTitleBar = (TitleBar) mRootView.findViewById(R.id.titlebar);
		mTitleBar.setTitle(getResources().getString(R.string.fragment_mechanism));
		mPullToRefreshExpandableListView = (PullToRefreshExpandableListView) mRootView.findViewById(R.id.list);
		mListView = mPullToRefreshExpandableListView.getRefreshableView();
	}
	
	private class MechanismAdapter extends BaseAdapter{

		private LayoutInflater mLayoutInflater;
		
		public MechanismAdapter(Context context){
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
				
			}
			return convertView;
		}
		
	}
	
}
