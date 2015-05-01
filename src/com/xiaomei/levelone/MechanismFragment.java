package com.xiaomei.levelone;

import java.util.List;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaomei.R;
import com.xiaomei.api.HttpUrlManager;
import com.xiaomei.bean.Hospital;
import com.xiaomei.levelone.control.MechanismControl;
import com.xiaomei.leveltwo.GoodsDetailActivity;
import com.xiaomei.leveltwo.MechanismDetailActivity;
import com.xiaomei.util.ScreenUtils;
import com.xiaomei.widget.TitleBar;
import com.xiaomei.widget.pullrefreshview.PullToRefreshListView;
import com.xiaomei.widget.pullrefreshview.PullToRefreshBase.OnRefreshListener;
import com.yuekuapp.BaseFragment;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
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
public class MechanismFragment extends BaseFragment<MechanismControl>
	implements OnRefreshListener,OnScrollListener{
	
	private ViewGroup mRootView;
	
	private PullToRefreshListView mPullToRefreshListView;;
	
	private ListView mListView;
	
	private TitleBar mTitleBar;
	
	private MechanismAdapter mAdapter;
	
	private boolean mIsRefresh;
	
	private ViewGroup mRefreshLayout;
	
	private View mLoadingView; 
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(mRootView == null){
			mRootView = (ViewGroup) inflater.inflate(R.layout.fragment_mechanism_layout, null);
			setUpView();
			setListener();
			initData();
		}else{
			((ViewGroup)mRootView.getParent()).removeView(mRootView);
		}
		return mRootView;
	}
	
	private void setUpView(){
		mTitleBar = (TitleBar) mRootView.findViewById(R.id.title_bar);
		mTitleBar.setTitle(getResources().getString(R.string.fragment_mechanism));
		mPullToRefreshListView = (PullToRefreshListView) mRootView.findViewById(R.id.list);

		mListView = mPullToRefreshListView.getRefreshableView();
		mAdapter = new MechanismAdapter(getActivity()); 
		mListView.setAdapter(mAdapter);
		
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		mRefreshLayout = (ViewGroup) inflater.inflate(R.layout.pull_to_refresh_footer, null);
		
		mLoadingView = mRootView.findViewById(R.id.loading_layout);
	}
	
	private void setListener(){
		mPullToRefreshListView.setOnRefreshListener(this);
//		mPullToRefreshListView.setOnScrollListener(this);
	}
	
	private void initData(){
		showProgress();
		mIsRefresh = true;
		mControl.getMechanismListAsyn();
	}
	
	private void getMoreData(){
		if(mIsRefresh)
			return;
		if(!mRefreshLayout.isShown())
			mRefreshLayout.setVisibility(View.VISIBLE);
		mPullToRefreshListView.addFooterView(mRefreshLayout);
		mControl.getMechanismListMoreAsyn();
		mIsRefresh = true;
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
	
	
	// ================================== Progress ==========================================
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
	public void getMechanismLismListCallBack(){
		dissProgress();
		mIsRefresh = false;
		mAdapter.setData(mControl.getModel().getData());
		mAdapter.notifyDataSetChanged();
		mPullToRefreshListView.onRefreshComplete();
		Toast.makeText(getActivity(), getResources().getString(R.string.get_data_sucess), 0).show();
	}
	
	public void getMechanismListExceptionCallBack(){
		dissProgress();
		mIsRefresh = false;
		Toast.makeText(getActivity(), "加载数据异常", 0).show();
	}

	public void getMechanismLismListMoreCallBack(){
		dissProgress();
		mIsRefresh = false;
		mPullToRefreshListView.removeFooterView(mRefreshLayout);
		mAdapter.getData().addAll(mControl.getModel().getData());
		mAdapter.notifyDataSetChanged();
		Toast.makeText(getActivity(), "加载完成", 0).show();
	}
	
	public void getMechanismListMoreExceptionCallBack(){
		dissProgress();
		mIsRefresh = false;
		Toast.makeText(getActivity(), "加载数据异常", 0).show();
	}
	// ================================== Call back ==========================================
	@Override
	public void onRefresh() {
		mControl.getMechanismListAsyn();
		mIsRefresh = true;
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
		
		public List<Hospital> getData(){
			return mData;
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
				holder.titleTv = (TextView) convertView.findViewById(R.id.title);
				holder.locationTv = (TextView) convertView.findViewById(R.id.location);
				holder.hospitalTv = (TextView) convertView.findViewById(R.id.hospital);
				holder.iconIv = (ImageView) convertView.findViewById(R.id.icon);
				holder.serverTv = (TextView) convertView.findViewById(R.id.server);
				holder.effectTv = (TextView) convertView.findViewById(R.id.effect);
				holder.environmentalTv = (TextView) convertView.findViewById(R.id.environmental);
				convertView.setTag(holder);
				convertView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Holder holder = (Holder) v.getTag();
						GoodsDetailActivity.startActivity(getActivity(), HttpUrlManager.MECHANISM_DETAIL_URL + "?hosp_id=" + holder.id);
					}
				});
			}
			holder = (Holder) convertView.getTag();
			attachData2UI(holder, position);
			return convertView;
		}
		
		private void attachData2UI(Holder holder ,int position){
			Hospital hospital = mData.get(position);
			ImageLoader.getInstance().displayImage(mData.get(position).getFile(), holder.iconIv);
			RelativeLayout.LayoutParams ll = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, (int)(ScreenUtils.getScreenWidth(getActivity())*3/5));
			holder.iconIv.setLayoutParams(ll);
			holder.id = mData.get(position).getId();
			holder.hospitalTv.setText(hospital.getHospName());
			holder.locationTv.setText(hospital.getAddr());
			holder.titleTv.setText(hospital.getHospDes());
			holder.serverTv .setText("服务" + hospital.getRateService());
			holder.environmentalTv.setText("环境" + hospital.getRateEnvironment());
			holder.effectTv.setText("效果" + hospital.getRateEffect());
		}
		
		private class Holder{
			private ImageView iconIv;
			private TextView titleTv;
			private TextView hospitalTv;
			private TextView locationTv;
			private TextView serverTv;
			private TextView effectTv;
			private TextView environmentalTv;
			private String id;
		}
		
	}
	
}
