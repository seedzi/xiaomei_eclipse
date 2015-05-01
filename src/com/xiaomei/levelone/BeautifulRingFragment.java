package com.xiaomei.levelone;

import java.util.List;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaomei.R;
import com.xiaomei.bean.BeautifulRing;
import com.xiaomei.levelone.control.BeautifulRingControl;
import com.xiaomei.leveltwo.BeautifulRingDetailsActivity;
import com.xiaomei.util.ScreenUtils;
import com.xiaomei.widget.TitleBar;
import com.xiaomei.widget.pullrefreshview.PullToRefreshListView;
import com.xiaomei.widget.pullrefreshview.PullToRefreshBase.OnRefreshListener;
import com.yuekuapp.BaseFragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class BeautifulRingFragment extends BaseFragment<BeautifulRingControl> 
	implements OnRefreshListener,OnScrollListener{
	
	private boolean mIsRefresh;
	
	private ViewGroup mRootView;
	
	private TitleBar mTitleBar;
	
	private PullToRefreshListView mPullToRefreshListView;
	
	private ListView mListView;
	
	private RingAdapter mAdapter;
	
	private ViewGroup mRefreshLayout;
	
	private View mLoadingView; 
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(mRootView ==null){
			mRootView = (ViewGroup) inflater.inflate(R.layout.fragment_beautifulring_layout, null);
			setUpView();
			setListener();
			initdata();
		} else {
			((ViewGroup)mRootView.getParent()).removeView(mRootView);
		}
		return mRootView;
	}
	
	private void setUpView(){
		mTitleBar = (TitleBar) mRootView.findViewById(R.id.title_bar);
		mTitleBar.setTitle(getResources().getString(R.string.fragment_beautiful_ring));
		
		mPullToRefreshListView = (PullToRefreshListView) mRootView.findViewById(R.id.list);
		mListView = mPullToRefreshListView.getRefreshableView();
		mAdapter = new RingAdapter(getActivity());
		mListView.setAdapter(mAdapter);
		
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		mRefreshLayout = (ViewGroup) inflater.inflate(R.layout.pull_to_refresh_footer, null);
		
		mLoadingView = mRootView.findViewById(R.id.loading_layout);
	}
	
	private void setListener(){
		mPullToRefreshListView.setOnRefreshListener(this);
		mPullToRefreshListView.setOnScrollListener(this);
	}
	
	private void initdata(){
		mControl.getListDataFromNetAysn();
		showProgress();
	}
	
	@Override
	public void onRefresh() {
		mIsRefresh = true;
		mControl.getListDataFromNetAysn();
	}
	
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		int position = mListView.getLastVisiblePosition();
		if(!mIsRefresh && position == mAdapter.getCount()){
			getMoreData();
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
	}
	
	private void getMoreData(){
		if(mIsRefresh)
			return;
		if(!mRefreshLayout.isShown())
			mRefreshLayout.setVisibility(View.VISIBLE);
		mPullToRefreshListView.addFooterView(mRefreshLayout);
		mControl.getMoreListDataFromNetAysn();
		mIsRefresh = true;
	}
	
	private void showProgress(){
		mLoadingView.setVisibility(View.VISIBLE);
		AnimationDrawable animationDrawable =  (AnimationDrawable) ((ImageView)mLoadingView.findViewById(R.id.iv)).getDrawable();
		if(!animationDrawable.isRunning())
			animationDrawable.start();
		mPullToRefreshListView.setVisibility(View.GONE);
	}
	
	private void dissProgress(){
		mLoadingView.setVisibility(View.GONE);
		mPullToRefreshListView.setVisibility(View.VISIBLE);
	}
	
	
	// ================================== Call back ==========================================
	public void getListDataFromNetAysnCallBack(){
		dissProgress();
		mIsRefresh = false;
		mAdapter.setData(mControl.getData());
		mAdapter.notifyDataSetChanged();
		mPullToRefreshListView.onRefreshComplete();
		Toast.makeText(getActivity(), getResources().getString(R.string.get_data_sucess), 0).show();
	}
	
	public void getListDataFromNetAysnExceptionCallBack(){
		dissProgress();
		mIsRefresh = false;
	}
	
	public void getMoreListDataFromNetAysnCallBack(){
		dissProgress();
		mIsRefresh = false;
		mAdapter.getData().addAll(mControl.getData());
		mAdapter.notifyDataSetChanged();
		mPullToRefreshListView.removeFooterView(mRefreshLayout);
		Toast.makeText(getActivity(), getResources().getString(R.string.get_data_sucess), 0).show();
	}
	
	public void getMoreListDataFromNetAysnExceptionCallBack(){
		dissProgress();
		mIsRefresh = false;
		mPullToRefreshListView.removeFooterView(mRefreshLayout);
	}
	// ================================== Call back ==========================================
	
	private class RingAdapter extends BaseAdapter implements View.OnClickListener{
		
		private LayoutInflater mLayoutInflater;

		private List<BeautifulRing> mData;
		
		public RingAdapter(Context context) {
			mLayoutInflater = LayoutInflater.from(context);
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
				convertView = mLayoutInflater.inflate(R.layout.item_ring_layout, null);
				holder = new Holder();
				holder.userIconIv = (ImageView) convertView.findViewById(R.id.user_icon);
				holder.userNaemTv = (TextView) convertView.findViewById(R.id.person_name);
				holder.titleTv = (TextView) convertView.findViewById(R.id.title);
				holder.timeTv = (TextView) convertView.findViewById(R.id.time);
				holder.bubleSizeTv = (TextView) convertView.findViewById(R.id.buble_size);
				holder.likeSizeTv = (TextView) convertView.findViewById(R.id.like_size);
				holder.shareImg = (ImageView) convertView.findViewById(R.id.share_img);
				holder.descriptionTv = (TextView) convertView.findViewById(R.id.description);
				convertView.setTag(holder);
				convertView.setOnClickListener(this);
			}
			holder = (Holder) convertView.getTag();
			attachDate(holder, mData.get(position));
			return convertView;
		}
		
		private void attachDate(Holder holder,BeautifulRing bean){
			ImageLoader.getInstance().displayImage(bean.getShareFile(), holder.shareImg);
			FrameLayout.LayoutParams ll = new FrameLayout.LayoutParams
			        (LayoutParams.MATCH_PARENT, (int)(ScreenUtils.getScreenWidth(getActivity())/1.7));
			holder.shareImg.setLayoutParams(ll);
			holder.descriptionTv.setText(bean.getShareTitle());
			holder.userNaemTv.setText(bean.getUsername());
			holder.timeTv.setText(bean.getCreatedate());
			holder.bubleSizeTv.setText(bean.getNumComments());
			holder.likeSizeTv.setText(bean.getNumFavors());
		}
		
		private void setData(List<BeautifulRing> data){
			mData = data;
		}
		
		public List<BeautifulRing> getData(){
			return mData;
		}

		@Override
		public void onClick(View v) {
			BeautifulRingDetailsActivity.startActivity(getActivity());
		}
		
		private class Holder{
			private ImageView shareImg;
			private ImageView userIconIv;
			private TextView userNaemTv;
			private TextView timeTv;
			private TextView titleTv;
			private TextView descriptionTv;
			private TextView likeSizeTv;
			private TextView bubleSizeTv;
		}
		
	}


}
