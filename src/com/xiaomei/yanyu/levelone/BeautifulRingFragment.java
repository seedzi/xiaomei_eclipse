package com.xiaomei.yanyu.levelone;


import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.levelone.adapter.RingAdapter;
import com.xiaomei.yanyu.levelone.adapter.UserPostAdapter;
import com.xiaomei.yanyu.levelone.control.BeautifulRingControl;
import com.xiaomei.yanyu.widget.TitleBar;
import com.xiaomei.yanyu.widget.pullrefreshview.PullToRefreshListView;
import com.xiaomei.yanyu.widget.pullrefreshview.PullToRefreshBase.OnRefreshListener;
import com.yuekuapp.BaseFragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class BeautifulRingFragment extends BaseFragment<BeautifulRingControl> 
	implements OnRefreshListener,OnScrollListener,OnClickListener{
	
	private ViewGroup mRootView;
	
	private TitleBar mTitleBar;
	
	private ViewHolder mJinghuaViewHolder;
	private ViewHolder mGuangchangViewHolder;
	
	private ViewGroup mJinghua;
	private ViewGroup mGuangchang;
	
	private ViewGroup mJinghuaLayout;
	private ViewGroup mGuangchangLayout;
	
	private int mCurrentState = STATE_JINGHUA;
	private static int STATE_JINGHUA = 0;
	private static int STATE_GUANGCHANG = 1;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(mRootView ==null){
			mRootView = (ViewGroup) inflater.inflate(R.layout.fragment_beautifulring_layout, null);
			setUpView();
			mJinghua.performClick();
		} else {
			try {
				((ViewGroup)mRootView.getParent()).removeView(mRootView);
			} catch (Exception e) {
			}
		}
		setViewHeight();
		return mRootView;
	}
	
	private void setUpView(){
		mTitleBar = (TitleBar) mRootView.findViewById(R.id.title_bar);
		mTitleBar.setTitle(getResources().getString(R.string.fragment_beautiful_ring));

        mJinghua = (ViewGroup) mRootView.findViewById(R.id.jing_hua);
        mJinghua.setOnClickListener(this);
        mGuangchang = (ViewGroup) mRootView.findViewById(R.id.guang_chang);
        mGuangchang.setOnClickListener(this);
		
        mJinghuaLayout = (ViewGroup) mRootView.findViewById(R.id.jing_hua_layout);
        mGuangchangLayout = (ViewGroup) mRootView.findViewById(R.id.guang_chang_layout);
        
		mJinghuaViewHolder = new ViewHolder();
		setUpViewHolder(mJinghuaViewHolder, mJinghuaLayout);
		mGuangchangViewHolder = new ViewHolder();
	    setUpViewHolder(mGuangchangViewHolder, mGuangchangLayout);
	}
	
	/** setup layout height*/
	private void setViewHeight() {
		View root = mRootView.findViewById(R.id.root);
		LinearLayout.LayoutParams ll = (LayoutParams) root.getLayoutParams();

		WindowManager wm = (WindowManager) getActivity().getSystemService(
				Context.WINDOW_SERVICE);

		int height = wm.getDefaultDisplay().getHeight();
		ll.height = (int) (height - 2*getResources().getDimension(R.dimen.title_bar_heigt));
	}
	
	private void setUpViewHolder(final ViewHolder viewHolder,ViewGroup root){
	    LayoutInflater inflater = LayoutInflater.from(getActivity());
	    viewHolder.mPullToRefreshListView = (PullToRefreshListView) root.findViewById(R.id.list);
	    viewHolder.mListView = viewHolder.mPullToRefreshListView.getRefreshableView();
	    if(viewHolder == mJinghuaViewHolder){
	    	viewHolder.mAdapter = new RingAdapter(getActivity());
	    }else{
	    	viewHolder.mAdapter = new UserPostAdapter();
	    }
	    viewHolder.mListView.setAdapter(viewHolder.mAdapter);
        

	    viewHolder.mRefreshLayout = (ViewGroup) inflater.inflate(R.layout.pull_to_refresh_footer, null);
	    viewHolder.mLoadingView = root.findViewById(R.id.loading_layout);
	    viewHolder.mEmptyView= root.findViewById(R.id.empty_view);
	    viewHolder.mEmptyView.findViewById(R.id.reload_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgress(viewHolder);
                initdata();
            }
        });
	    viewHolder.mPullToRefreshListView.setOnRefreshListener(this);
	    viewHolder.mPullToRefreshListView.setOnScrollListener(this);
	}
	
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
        case R.id.jing_hua:
            mJinghua.getChildAt(1).setVisibility(View.VISIBLE);
            mGuangchang.getChildAt(1).setVisibility(View.GONE);
            mJinghuaLayout.setVisibility(View.VISIBLE);
            mGuangchangLayout.setVisibility(View.GONE);
            mCurrentState = STATE_JINGHUA;
            initdata();
            break;
        case R.id.guang_chang:
            mJinghua.getChildAt(1).setVisibility(View.GONE);
            mGuangchang.getChildAt(1).setVisibility(View.VISIBLE);
            mJinghuaLayout.setVisibility(View.GONE);
            mGuangchangLayout.setVisibility(View.VISIBLE);
            mCurrentState = STATE_GUANGCHANG;
            initdata();
            break;
        default:
            break;
        }
    }
	
	private void initdata(){
	    if(mCurrentState == STATE_JINGHUA){
	    	if(mControl.getModel().getBeautifulData()==null || mControl.getModel().getBeautifulData().size()==0){
		        mControl.getJinghuaListDataFromNetAysn();
		        showProgress(mJinghuaViewHolder);
	    	}
	    }else {
           mControl.getGuangchangListDataFromNetAysn();
            showProgress(mGuangchangViewHolder);
	    }

	}
	
	@Override
	public void onRefresh() {
	    if(mCurrentState == STATE_JINGHUA){
	        mJinghuaViewHolder.mIsRefresh = true;
	        mControl.getJinghuaListDataFromNetAysn();
	    }else{
	        mGuangchangViewHolder.mIsRefresh = true;
	        mControl.getGuangchangListDataFromNetAysn();
	    }
	}
	
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
	    int position = 0;
	    if(mCurrentState == STATE_JINGHUA){
	        position = mJinghuaViewHolder.mListView.getLastVisiblePosition();
    		if(!mJinghuaViewHolder.mIsRefresh && position == mJinghuaViewHolder.mAdapter.getCount()){
    			getMoreData();
    		}
	    }else{
           position = mGuangchangViewHolder.mListView.getLastVisiblePosition();
            if(!mGuangchangViewHolder.mIsRefresh && position == mGuangchangViewHolder.mAdapter.getCount()){
                getMoreData();
            }
	    }
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
	}
	
	private void getMoreData(){
       if(mCurrentState == STATE_JINGHUA){
    		if(mJinghuaViewHolder.mIsRefresh)
    			return;
    		if(!mJinghuaViewHolder.mRefreshLayout.isShown())
    		    mJinghuaViewHolder.mRefreshLayout.setVisibility(View.VISIBLE);
    		mJinghuaViewHolder.mPullToRefreshListView.addFooterView(mJinghuaViewHolder.mRefreshLayout);
    		mControl.getJinghuaMoreListDataFromNetAysn();
    		mJinghuaViewHolder.mIsRefresh = true;
       }else{
           if(mGuangchangViewHolder.mIsRefresh)
               return;
           if(!mGuangchangViewHolder.mRefreshLayout.isShown())
               mGuangchangViewHolder.mRefreshLayout.setVisibility(View.VISIBLE);
           mGuangchangViewHolder.mPullToRefreshListView.addFooterView(mGuangchangViewHolder.mRefreshLayout);
           mControl.getGuangchangMoreListDataFromNetAysn();
           mGuangchangViewHolder.mIsRefresh = true; 
       }
	}
	
	private void showProgress(ViewHolder viewHolder){
        viewHolder.mLoadingView.setVisibility(View.VISIBLE);
		AnimationDrawable animationDrawable =  (AnimationDrawable) ((ImageView)viewHolder.mLoadingView.findViewById(R.id.iv)).getDrawable();
		if(!animationDrawable.isRunning())
			animationDrawable.start();
		viewHolder.mPullToRefreshListView.setVisibility(View.GONE);
		viewHolder.mEmptyView.setVisibility(View.GONE);
	}
	
	private void dissProgress(ViewHolder viewHolder){
	    viewHolder.mLoadingView.setVisibility(View.GONE);
	    viewHolder.mPullToRefreshListView.setVisibility(View.VISIBLE);
	    viewHolder.mEmptyView.setVisibility(View.GONE);
	}
	
	private void showEmpty(ViewHolder viewHolder){
	    viewHolder.mLoadingView.setVisibility(View.GONE);
	    viewHolder.mPullToRefreshListView.setVisibility(View.GONE);
	    viewHolder.mEmptyView.setVisibility(View.VISIBLE);
	}
	
	// ================================== Jingxuan Call back ==========================================
	public void getJinghuaListDataFromNetAysnCallBack(){
		dissProgress(mJinghuaViewHolder);
		mJinghuaViewHolder.mIsRefresh = false;
		((RingAdapter)mJinghuaViewHolder.mAdapter).setData(mControl.getModel().getBeautifulData());
		mJinghuaViewHolder.mAdapter.notifyDataSetChanged();
		mJinghuaViewHolder.mPullToRefreshListView.onRefreshComplete();
		Toast.makeText(getActivity(), getResources().getString(R.string.get_data_sucess), 0).show();
	}
	
	public void getJinghuaListDataFromNetAysnExceptionCallBack(){
		dissProgress(mJinghuaViewHolder);
		showEmpty(mJinghuaViewHolder);
		mJinghuaViewHolder.mIsRefresh = false;
	}
	
	public void getJinghuaMoreListDataFromNetAysnExceptionCallBack(){
		dissProgress(mJinghuaViewHolder);
		mJinghuaViewHolder.mIsRefresh = false;
		((RingAdapter)mJinghuaViewHolder.mAdapter).getData().addAll(mControl.getModel().getBeautifulData());
		mJinghuaViewHolder.mAdapter.notifyDataSetChanged();
		mJinghuaViewHolder.mPullToRefreshListView.removeFooterView(mJinghuaViewHolder.mRefreshLayout);
	}
	
	public void getJinghuaMoreListDataFromNetAysnCallBack(){
		dissProgress(mJinghuaViewHolder);
		mJinghuaViewHolder.mIsRefresh = false;
		mJinghuaViewHolder.mPullToRefreshListView.removeFooterView(mJinghuaViewHolder.mRefreshLayout);
	}
	
	   // ================================== Guchang Call back ==========================================
    public void getGuangchangListDataFromNetAysnCallBack(){
        dissProgress(mGuangchangViewHolder);
        mGuangchangViewHolder.mIsRefresh = false;
        ((UserPostAdapter)mGuangchangViewHolder.mAdapter).setData(mControl.getModel().getUserShareData());
        mGuangchangViewHolder.mAdapter.notifyDataSetChanged();
        mGuangchangViewHolder.mPullToRefreshListView.onRefreshComplete();
        Toast.makeText(getActivity(), getResources().getString(R.string.get_data_sucess), 0).show();
    }
    
    public void getGuangchangListDataFromNetAysnExceptionCallBack(){
        dissProgress(mGuangchangViewHolder);
        showEmpty(mGuangchangViewHolder);
        mGuangchangViewHolder.mIsRefresh = false;
    }
    
    public void getGuangchangMoreListDataFromNetAysnCallBack(){
        dissProgress(mGuangchangViewHolder);
        mGuangchangViewHolder.mIsRefresh = false;
        ((UserPostAdapter)mGuangchangViewHolder.mAdapter).getData().addAll(mControl.getModel().getUserShareData());
        mGuangchangViewHolder.mAdapter.notifyDataSetChanged();
        mGuangchangViewHolder.mPullToRefreshListView.removeFooterView(mGuangchangViewHolder.mRefreshLayout);
    }
    
    public void getGuangchangMoreListDataFromNetAysnExceptionCallBack(){
        dissProgress(mJinghuaViewHolder);
        mGuangchangViewHolder.mIsRefresh = false;
        mGuangchangViewHolder.mPullToRefreshListView.removeFooterView(mGuangchangViewHolder.mRefreshLayout);
    }

	private class ViewHolder{
	    private boolean mIsRefresh;
	    private PullToRefreshListView mPullToRefreshListView;
	    private ListView mListView;
	    private BaseAdapter mAdapter;
	    private ViewGroup mRefreshLayout;
	    private View mLoadingView; 
	    private View mEmptyView;
	}
	//RingAdapter
}
