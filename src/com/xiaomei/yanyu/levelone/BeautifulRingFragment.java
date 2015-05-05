package com.xiaomei.yanyu.levelone;

import java.util.List;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.bean.BeautifulRing;
import com.xiaomei.yanyu.levelone.control.BeautifulRingControl;
import com.xiaomei.yanyu.leveltwo.BeautifulRingDetailsActivity;
import com.xiaomei.yanyu.util.DateUtils;
import com.xiaomei.yanyu.util.ScreenUtils;
import com.xiaomei.yanyu.util.SystemUtils;
import com.xiaomei.yanyu.widget.TitleBar;
import com.xiaomei.yanyu.widget.pullrefreshview.PullToRefreshListView;
import com.xiaomei.yanyu.widget.pullrefreshview.PullToRefreshBase.OnRefreshListener;
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
	
	private View mEmptyView;
	
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
		
		mEmptyView= mRootView.findViewById(R.id.empty_view);
		mEmptyView.findViewById(R.id.reload_button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showProgress();
				initdata();
			}
		});
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
		mEmptyView.setVisibility(View.GONE);
	}
	
	private void dissProgress(){
		mLoadingView.setVisibility(View.GONE);
		mPullToRefreshListView.setVisibility(View.VISIBLE);
		mEmptyView.setVisibility(View.GONE);
	}
	
	private void showEmpty(){
		mLoadingView.setVisibility(View.GONE);
		mPullToRefreshListView.setVisibility(View.GONE);
		mEmptyView.setVisibility(View.VISIBLE);
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
		showEmpty();
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
				holder.userIconIv = (ImageView) convertView.findViewById(R.id.person_icon);
				holder.userNaemTv = (TextView) convertView.findViewById(R.id.person_name);
				holder.titleTv = (TextView) convertView.findViewById(R.id.title);
				holder.timeTv = (TextView) convertView.findViewById(R.id.time);
				holder.bubleSizeTv = (TextView) convertView.findViewById(R.id.buble_size);
				holder.likeSizeTv = (TextView) convertView.findViewById(R.id.like_size);
				holder.shareImg = (ImageView) convertView.findViewById(R.id.share_img);
				holder.descriptionTv = (TextView) convertView.findViewById(R.id.description);
				holder.shareButton = convertView.findViewById(R.id.share);
				convertView.setTag(holder);
				holder.shareImg.setOnClickListener(this);
				holder.shareButton .setOnClickListener(this);
			}
			holder = (Holder) convertView.getTag();
			attachDate(holder, mData.get(position));
			return convertView;
		}
		
		private void attachDate(Holder holder,BeautifulRing bean){
			holder.shareImg.setImageResource(R.drawable.ring_default_img);
			ImageLoader.getInstance().displayImage(bean.getShareFile(), holder.shareImg);
			ImageLoader.getInstance().displayImage(bean.getAvatar(),holder.userIconIv);
			FrameLayout.LayoutParams ll = new FrameLayout.LayoutParams
			        (LayoutParams.MATCH_PARENT, (int)(ScreenUtils.getScreenWidth(getActivity())*516/720));
			holder.shareImg.setLayoutParams(ll);
			holder.descriptionTv.setText(bean.getShareTitle());
			holder.userNaemTv.setText(bean.getUsername());
			holder.bubleSizeTv.setText(bean.getNumComments());
			holder.likeSizeTv.setText(bean.getNumFavors());
			holder.titleTv.setText(bean.getShareMark());
			holder.timeTv.setText(DateUtils.formateDate(Long.valueOf(bean.getCreatedate())*1000));
			holder.shareImg.setTag(holder);
			holder.id = bean.getId();
		}
		
		private void setData(List<BeautifulRing> data){
			mData = data;
		}
		
		public List<BeautifulRing> getData(){
			return mData;
		}

		@Override
		public void onClick(View v) {
			int id = v.getId();
			switch (id) {
			case R.id.share:
				SystemUtils.shareMsg(getActivity(), ""/*getActivity().getClass().getSimpleName()*/, "颜语", "小美医生", null);
				break;
			case R.id.share_img:
				BeautifulRingDetailsActivity.startActivity(getActivity(),((Holder)v.getTag()).id);
				break;
			default:
				break;
			}

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
			private View shareButton;
			private String id;
		}
		
	}


}
