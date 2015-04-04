package com.xiaomei.levelone;

import java.util.List;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaomei.R;
import com.xiaomei.bean.Hospital;
import com.xiaomei.levelone.control.MechanismControl;
import com.xiaomei.widget.TitleBar;
import com.xiaomei.widget.pullrefreshview.PullToRefreshListView;
import com.xiaomei.widget.pullrefreshview.PullToRefreshBase.OnRefreshListener;
import com.yuekuapp.BaseFragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

@SuppressLint("NewApi")
public class MechanismFragment extends BaseFragment<MechanismControl> implements OnRefreshListener{
	
	private ViewGroup mRootView;
	
	private PullToRefreshListView mPullToRefreshListView;;
	
	private ListView mListView;
	
	private TitleBar mTitleBar;
	
	private MechanismAdapter mAdapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mRootView = (ViewGroup) inflater.inflate(R.layout.fragment_mechanism_layout, null);
		setUpView();
		setListener();
		initData();
		return mRootView;
	}
	
	private void setUpView(){
		mTitleBar = (TitleBar) mRootView.findViewById(R.id.titlebar);
		mTitleBar.setTitle(getResources().getString(R.string.fragment_mechanism));
		mPullToRefreshListView = (PullToRefreshListView) mRootView.findViewById(R.id.list);

		mListView = mPullToRefreshListView.getRefreshableView();
		mAdapter = new MechanismAdapter(getActivity()); 
		mListView.setAdapter(mAdapter);
	}
	
	private void setListener(){
		mPullToRefreshListView.setOnRefreshListener(this);
	}
	
	private void initData(){
		mControl.getMechanismListAsyn();
	}
	
	public void getMechanismLismListCallBack(){
		mAdapter.setData(mControl.getListData());
		mAdapter.notifyDataSetChanged();
	}
	
	public void getMechanismListExceptionCallBack(){
		
	}

	@Override
	public void onRefresh() {
		mControl.getMechanismListAsyn();
	}
	
	private class MechanismAdapter extends BaseAdapter{

		private  List<Hospital> mData;
		
		private LayoutInflater mLayoutInflater;
		
		public MechanismAdapter(Context context){
			mLayoutInflater = LayoutInflater.from(context);
		}
		public void setData(List<Hospital> data){
			mData = data;
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
				convertView = mLayoutInflater.inflate(R.layout.item_mechanism_layout, null);
				holder = new Holder();
				holder.iconIv = (ImageView) convertView.findViewById(R.id.icon);
				convertView.setTag(holder);
			}
			holder = (Holder) convertView.getTag();
			attachData2UI(holder, position);
			return convertView;
		}
		
		private void attachData2UI(Holder holder ,int position){
			Log.d("111", "mData.get(position).getFile() = " + mData.get(position).getFile());
			ImageLoader.getInstance().displayImage(mData.get(position).getFile(), holder.iconIv);
		}
		
		private class Holder{
			private ImageView iconIv;
			private TextView titleTv;
			private TextView hospitalTv;
			private TextView locationTv;
		}
		
	}
	
}
