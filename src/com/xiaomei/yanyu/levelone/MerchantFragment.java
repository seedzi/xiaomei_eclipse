package com.xiaomei.yanyu.levelone;

import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.api.HttpUrlManager;
import com.xiaomei.yanyu.bean.Merchant;
import com.xiaomei.yanyu.levelone.control.MechanismControl;
import com.xiaomei.yanyu.leveltwo.GoodsDetailActivity;
import com.xiaomei.yanyu.leveltwo.MechanismDetailActivity;
import com.xiaomei.yanyu.util.ScreenUtils;
import com.xiaomei.yanyu.util.UiUtil;
import com.xiaomei.yanyu.widget.GoodsGrade;
import com.xiaomei.yanyu.widget.StarsView;
import com.xiaomei.yanyu.widget.TitleBar;
import com.xiaomei.yanyu.widget.pullrefreshview.PullToRefreshListView;
import com.xiaomei.yanyu.widget.pullrefreshview.PullToRefreshBase.OnRefreshListener;
import com.yuekuapp.BaseFragment;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class MerchantFragment extends BaseFragment<MechanismControl>
	implements OnRefreshListener,OnScrollListener, OnItemClickListener{
	
	private ViewGroup mRootView;
	
	private PullToRefreshListView mPullToRefreshListView;;
	
	private ListView mListView;
	
	private TitleBar mTitleBar;
	
	private MerchantAdapter mAdapter;
	
	private boolean mIsRefresh;
	
	private ViewGroup mRefreshLayout;
	
	private View mLoadingView; 
	
	private View mEmptyView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(mRootView == null){
			mRootView = (ViewGroup) inflater.inflate(R.layout.fragment_mechanism_layout, null);
			setUpView();
			setListener();
			initData();
		}else{
			try {
				((ViewGroup)mRootView.getParent()).removeView(mRootView);
			} catch (Exception e) {
			}
		}
		return mRootView;
	}
	
	private void setUpView(){
		mTitleBar = (TitleBar) mRootView.findViewById(R.id.title_bar);
		mTitleBar.setTitle(getResources().getString(R.string.fragment_mechanism));
		mPullToRefreshListView = (PullToRefreshListView) mRootView.findViewById(R.id.list);

		mListView = mPullToRefreshListView.getRefreshableView();
		mAdapter = new MerchantAdapter(getActivity()); 
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(this);
		
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		mRefreshLayout = (ViewGroup) inflater.inflate(R.layout.pull_to_refresh_footer, null);
		
		mLoadingView = mRootView.findViewById(R.id.loading_layout);
		
		mEmptyView= mRootView.findViewById(R.id.empty_view);
		mEmptyView.findViewById(R.id.reload_button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showProgress();
				initData();
			}
		});
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        GoodsDetailActivity.startActivity((Activity) view.getContext(), HttpUrlManager.MECHANISM_DETAIL_URL + "?hosp_id=" + id);
    }
	
	// ================================== Progress ==========================================
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
	public void getMechanismLismListCallBack(){
		dissProgress();
		mIsRefresh = false;
		mAdapter.clear();
		mAdapter.addAll(mControl.getModel().getData());
		mAdapter.notifyDataSetChanged();
		mPullToRefreshListView.onRefreshComplete();
		Toast.makeText(getActivity(), getResources().getString(R.string.get_data_sucess), 0).show();
	}
	
	public void getMechanismListExceptionCallBack(){
		dissProgress();
		showEmpty();
		mIsRefresh = false;
		Toast.makeText(getActivity(), "加载数据异常", 0).show();
	}

	public void getMechanismLismListMoreCallBack(){
		dissProgress();
		mIsRefresh = false;
		mPullToRefreshListView.removeFooterView(mRefreshLayout);
		mAdapter.addAll(mControl.getModel().getData());
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
	
    private static class MerchantAdapter extends ArrayAdapter<Merchant> {

        private DisplayImageOptions mImageOptions;

        public MerchantAdapter(Context context) {
            super(context, 0);
            mImageOptions = new DisplayImageOptions.Builder()
                    .showImageForEmptyUri(R.drawable.merchant_image_default)
                    .showImageOnLoading(R.drawable.merchant_image_default)
                    .showImageOnFail(R.drawable.merchant_image_default).build();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView != null ? convertView :
                LayoutInflater.from(getContext()).inflate(R.layout.merchant_list_item, parent, false);

            Merchant merchant = getItem(position);
            ImageView cover = UiUtil.findImageViewById(itemView, R.id.cover);
            ImageLoader.getInstance().displayImage(merchant.getImageLarge(), cover, mImageOptions);
            UiUtil.findTextViewById(itemView, R.id.description).setText(merchant.getDescription());

            UiUtil.findTextViewById(itemView, R.id.name).setText(merchant.getName());
            UiUtil.findTextViewById(itemView, R.id.address).setText(merchant.getAddr());

            UiUtil.<GoodsGrade>findById(itemView, R.id.rate).setGrade(merchant.getRateService());

            UiUtil.findTextViewById(itemView, R.id.service).setText(getContext().getString(R.string.merchant_rate_service, merchant.getRateService()));
            UiUtil.findTextViewById(itemView, R.id.effect).setText(getContext().getString(R.string.merchant_rate_effect, merchant.getRateEffect()));
            UiUtil.findTextViewById(itemView, R.id.environment).setText(getContext().getString(R.string.merchant_rate_environment, merchant.getRateEnvironment()));
            return itemView;
        }

        @Override
        public long getItemId(int position) {
            return getItem(position).getId();
        }
	}
	
}
