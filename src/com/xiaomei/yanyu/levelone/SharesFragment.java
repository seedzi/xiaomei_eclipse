package com.xiaomei.yanyu.levelone;


import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.viewpagerindicator.PageIndicator;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.contanier.TabsActivity;
import com.xiaomei.yanyu.levelone.adapter.RecommendSharesAdapter;
import com.xiaomei.yanyu.levelone.adapter.UserShareAdapter;
import com.xiaomei.yanyu.levelone.control.SharesControl;
import com.xiaomei.yanyu.leveltwo.ComposeUserShareActivity;
import com.xiaomei.yanyu.module.user.LoginAndRegisterActivity;
import com.xiaomei.yanyu.util.UserUtil;
import com.xiaomei.yanyu.view.LayoutPagerAdapter;
import com.xiaomei.yanyu.widget.TitleActionBar;
import com.yuekuapp.BaseFragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class SharesFragment extends BaseFragment<SharesControl> 
        implements OnRefreshListener, OnLastItemVisibleListener, OnPageChangeListener {

    private static final int REQUEST_NEW_POST = 0;

    private ViewGroup mRootView;
	
	private TitleActionBar mTitleBar;
	private ViewPager mViewPager;

	private SharesPagerAdapter mPagerAdapter;
    private RecommendSharesAdapter mRecommendSharesAdapter;
    private UserShareAdapter mUserSharesAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPagerAdapter = new SharesPagerAdapter();
        mRecommendSharesAdapter = new RecommendSharesAdapter(getActivity());
        mUserSharesAdapter = new UserShareAdapter(getActivity());
    }	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(mRootView ==null){
			mRootView = (ViewGroup) inflater.inflate(R.layout.fragment_shares, null);
			setUpView();
			initData();
		} else {
			try {
				((ViewGroup)mRootView.getParent()).removeView(mRootView);
			} catch (Exception e) {
			}
		}
		return mRootView;
	}
	
	private void setUpView(){
	    mTitleBar = ((TabsActivity) getActivity()).getTitleBar();

	    mViewPager = (ViewPager) mRootView.findViewById(R.id.viewpager);
	    mPagerAdapter = new SharesPagerAdapter();
	    mViewPager.setAdapter(mPagerAdapter);
        PageIndicator indicator = (PageIndicator) mRootView.findViewById(R.id.indicator);
        indicator.setViewPager(mViewPager);
        indicator.setOnPageChangeListener(this);
	}

	private void initData() {
	    mControl.getJinghuaListDataFromNetAysn();
	    mControl.getGuangchangListDataFromNetAysn();
	}

	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mTitleBar.setTitle(R.string.fragment_shares);
        mTitleBar.setImageAction(R.drawable.btn_new_post);
        mTitleBar.setActionVisibility(mViewPager.getCurrentItem() == SharesPagerAdapter.POSITION_USER_SHARES ?
                View.VISIBLE : View.INVISIBLE);
        mTitleBar.setOnActionClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserUtil.getUser() == null) {
                    LoginAndRegisterActivity.startActivity(getActivity(), true);
                } else {
                    startActivityForResult(new Intent(getActivity(), ComposeUserShareActivity.class), REQUEST_NEW_POST);
                }
            }
        });
    }

    public void initData(final int position) {
        showProgress(getHolder(position));
        if (position == SharesPagerAdapter.POSITION_RECOMMEND_SHARES) {
            mControl.getJinghuaListDataFromNetAysn();
        } else {
            mControl.getGuangchangListDataFromNetAysn();
        }
    }

    private ViewHolder getHolder(int position) {
        return (ViewHolder) mViewPager.getChildAt(position).getTag();
    }

	@Override
	public void onRefresh(PullToRefreshBase refreshView) {
	    int position = mViewPager.getCurrentItem();
	    ViewHolder holder = getHolder(position);
	    if(position == SharesPagerAdapter.POSITION_RECOMMEND_SHARES){
	        mControl.getJinghuaListDataFromNetAysn();
	    }else{
	        mControl.getGuangchangListDataFromNetAysn();
	    }
	}

    @Override
	public void onLastItemVisible() {
	    int position = mViewPager.getCurrentItem();
	    ViewHolder holder = getHolder(position);
	    if (position == SharesPagerAdapter.POSITION_RECOMMEND_SHARES) {
	        mControl.getJinghuaMoreListDataFromNetAysn();
	    } else {
	        mControl.getGuangchangMoreListDataFromNetAysn();
	    }
	}

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int arg0) {
        mTitleBar.setActionVisibility(
                mViewPager.getCurrentItem() == SharesPagerAdapter.POSITION_USER_SHARES
                        ? View.VISIBLE : View.INVISIBLE);
    }

    private void showProgress(ViewHolder viewHolder) {
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
	    mRecommendSharesAdapter.clear();
	    mRecommendSharesAdapter.addAll(mControl.getModel().getBeautifulData());
	    ViewHolder holder = getHolder(SharesPagerAdapter.POSITION_RECOMMEND_SHARES);
        dissProgress(holder);
		holder.mPullToRefreshListView.onRefreshComplete();
		Toast.makeText(getActivity(), getResources().getString(R.string.get_data_sucess), 0).show();
	}
	
	public void getJinghuaListDataFromNetAysnExceptionCallBack(){
	    ViewHolder holder = getHolder(SharesPagerAdapter.POSITION_RECOMMEND_SHARES);
		dissProgress(holder);
		showEmpty(holder);
		holder.mPullToRefreshListView.onRefreshComplete();
	}
	
	public void getJinghuaMoreListDataFromNetAysnExceptionCallBack(){
        ViewHolder holder = getHolder(SharesPagerAdapter.POSITION_RECOMMEND_SHARES);
		dissProgress(holder);
		holder.mPullToRefreshListView.onRefreshComplete();
	}
	
	public void getJinghuaMoreListDataFromNetAysnCallBack(){
	    mRecommendSharesAdapter.addAll(mControl.getModel().getBeautifulData());
	    ViewHolder holder = getHolder(SharesPagerAdapter.POSITION_RECOMMEND_SHARES);
		dissProgress(holder);
		holder.mPullToRefreshListView.onRefreshComplete();
	}
	
	   // ================================== Guchang Call back ==========================================
    public void getGuangchangListDataFromNetAysnCallBack(){
        mUserSharesAdapter.clear();
        mUserSharesAdapter.addAll(mControl.getModel().getUserShareData());
        ViewHolder holder = getHolder(SharesPagerAdapter.POSITION_USER_SHARES);
        dissProgress(holder);
        holder.mPullToRefreshListView.onRefreshComplete();
        Toast.makeText(getActivity(), getResources().getString(R.string.get_data_sucess), 0).show();
    }
    
    public void getGuangchangListDataFromNetAysnExceptionCallBack(){
        ViewHolder holder = getHolder(SharesPagerAdapter.POSITION_USER_SHARES);
        dissProgress(holder);
        showEmpty(holder);
        holder.mPullToRefreshListView.onRefreshComplete();
    }
    
    public void getGuangchangMoreListDataFromNetAysnCallBack(){
        mUserSharesAdapter.addAll(mControl.getModel().getUserShareData());
        ViewHolder holder = getHolder(SharesPagerAdapter.POSITION_USER_SHARES);
        dissProgress(holder);
        holder.mPullToRefreshListView.onRefreshComplete();
    }
    
    public void getGuangchangMoreListDataFromNetAysnExceptionCallBack(){
        ViewHolder holder = getHolder(SharesPagerAdapter.POSITION_USER_SHARES);
        dissProgress(holder);
        holder.mPullToRefreshListView.onRefreshComplete();
    }

    private class SharesPagerAdapter extends LayoutPagerAdapter {

        private static final int PAGE_COUNT = 2;
        public static final int POSITION_RECOMMEND_SHARES = 0;
        public static final int POSITION_USER_SHARES = 1;

        public SharesPagerAdapter() {
            super(PAGE_COUNT);
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            Context context = container.getContext();
            View itemView = LayoutInflater.from(context).inflate(R.layout.list_layout, container, false);

            ViewHolder holder = new ViewHolder(itemView);
            itemView.setTag(holder);
            PullToRefreshListView pullView = holder.mPullToRefreshListView;
            pullView.setOnRefreshListener(SharesFragment.this);
            pullView.setOnLastItemVisibleListener(SharesFragment.this);
            pullView.getRefreshableView().setAdapter(position == POSITION_RECOMMEND_SHARES ? mRecommendSharesAdapter : mUserSharesAdapter);

            View emptyView = holder.mEmptyView;
            emptyView.findViewById(R.id.reload_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    initData(position);
                }
            });

            container.addView(itemView);
            return itemView;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getString(position == POSITION_RECOMMEND_SHARES ? R.string.indicator_recommend_shares : R.string.indicator_user_shares);
        }
    }

	private static class ViewHolder{

	    public ViewHolder(View itemView) {
	        mPullToRefreshListView = (PullToRefreshListView) itemView.findViewById(R.id.list);
	        mEmptyView = itemView.findViewById(R.id.empty_view);
	        mLoadingView = itemView.findViewById(R.id.loading_layout);
        }

	    private PullToRefreshListView mPullToRefreshListView;
	    private View mLoadingView; 
	    private View mEmptyView;
	}
}
