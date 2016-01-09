package com.xiaomei.yanyu.module.user.center;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.xiaomei.yanyu.AbstractActivity;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.bean.UserMessage;
import com.xiaomei.yanyu.module.user.center.control.UserCenterControl;
import com.xiaomei.yanyu.util.UiUtil;
import com.xiaomei.yanyu.widget.TitleBar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MessageActivity extends AbstractActivity<UserCenterControl> implements OnScrollListener{
	
	public static void startActivity(Activity ac){
		Intent intent = new Intent(ac,MessageActivity.class);
		ac.startActivity(intent);
        UiUtil.overridePendingTransition(ac);
	}
	
	private TitleBar mTitleBar;
	
	private MessageAdapter mAdapter;
	
	private PullToRefreshListView mPullToRefreshListView;
	
	private ListView mListView;

	private boolean mIsRefresh = false;
	
	private ViewGroup mRefreshLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activivty_message_layout);
		setUpView();
		initData();
	}

	private void setUpView(){
		mTitleBar = (TitleBar) findViewById(R.id.titlebar);
		mTitleBar.setTitle(getResources().getString(R.string.user_message));
		mTitleBar.setBackListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		mListView = (ListView) findViewById(R.id.list);
		mAdapter = new MessageAdapter(MessageActivity.this);
		mListView.setAdapter(mAdapter);
		mListView.setOnScrollListener(this);
		
		LayoutInflater inflater = LayoutInflater.from(this);
		mRefreshLayout = (ViewGroup) inflater.inflate(R.layout.pull_to_refresh_footer, null);
		
	}
	
	private void initData(){
		mControl.getUserMsg();
	}
	
	// =================================== Scroll ========================================
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		int position = mListView.getLastVisiblePosition();
		if(!mIsRefresh && position == mAdapter.getCount() ){
			getMoreData();
		}
	}
	// =================================== Scroll ========================================
	
	private void getMoreData(){
		if(!mRefreshLayout.isShown())
			mRefreshLayout.setVisibility(View.VISIBLE);
		mPullToRefreshListView.getRefreshableView().addFooterView(mRefreshLayout);
		mControl.getUserMsgMore();
		mIsRefresh = true;
	}
	
	// =================================== CallBack ========================================
	public void getUserMsgCallBack(){
	    mAdapter.clear();
		mAdapter.addAll(mControl.getModel().getUserMessage());
		mAdapter.notifyDataSetChanged();
	}
	
	public void getUserMsgExceptionCallBack(){
		Toast.makeText(this, "获取数据异常", 0).show();
	}
	
	public void getUserMsgMoreCallBack(){
		mIsRefresh = false;
		mPullToRefreshListView.getRefreshableView().removeFooterView(mRefreshLayout);
		mAdapter.addAll(mControl.getModel().getUserMessage());
		mAdapter.notifyDataSetChanged();
	}
	
	public void getUserMsgMoreExceptionCallBack(){
		Toast.makeText(this, "获取数据异常", 0).show();
	}
	// =================================== Adapter ========================================
    private class MessageAdapter extends ArrayAdapter<UserMessage> {

        public MessageAdapter(Context context) {
            super(context, 0);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView != null ? convertView
                    : LayoutInflater.from(getContext()).inflate(R.layout.item_message_layout,
                            parent, false);

            UserMessage messgae = getItem(position);
            UiUtil.findTextViewById(itemView, R.id.title).setText(messgae.getTitle());
            UiUtil.findTextViewById(itemView, R.id.content).setText(messgae.getContent());
            return itemView;
        }
    }
}
