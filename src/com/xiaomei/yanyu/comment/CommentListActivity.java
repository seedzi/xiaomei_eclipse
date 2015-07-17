package com.xiaomei.yanyu.comment;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.bean.BeautifulRing;
import com.xiaomei.yanyu.bean.CommentItem;
import com.xiaomei.yanyu.comment.control.CommentListControl;
import com.xiaomei.yanyu.module.user.LoginAndRegisterActivity;
import com.xiaomei.yanyu.util.DateUtils;
import com.xiaomei.yanyu.util.UiUtil;
import com.xiaomei.yanyu.util.UserUtil;
import com.xiaomei.yanyu.widget.TitleBar;
import com.xiaomei.yanyu.widget.pullrefreshview.PullToRefreshListView;
import com.xiaomei.yanyu.widget.pullrefreshview.PullToRefreshBase.OnRefreshListener;
import com.yuekuapp.BaseActivity;

public class CommentListActivity extends BaseActivity<CommentListControl> 
    implements OnRefreshListener,OnScrollListener{
    
    @Deprecated
    public static void startActivity(Context context){
        Intent intent = new Intent(context,CommentListActivity.class);
        context.startActivity(intent);
    }
    @Deprecated
    public static void startActivity(Context context,String type,String typeid){
        Intent intent = new Intent(context,CommentListActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("typeid", typeid);
        context.startActivity(intent);
    }
    @Deprecated
    public static void startActivity(Context context,String type,String typeid,boolean showComment){
        Intent intent = new Intent(context,CommentListActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("typeid", typeid);
        intent.putExtra("showComment", showComment);
        context.startActivity(intent);
    }
    
    public static void startActivity(Context context,String type,String typeid,boolean showComment,boolean isOnFouce){
        Intent intent = new Intent(context,CommentListActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("typeid", typeid);
        intent.putExtra("showComment", showComment);
        intent.putExtra("isOnFouce", isOnFouce);
        context.startActivity(intent);
    }
    
    private PullToRefreshListView mPullToRefreshListView;
    private ListView mListView;
    private View mLoadingView;
    private ViewGroup mRefreshLayout;
    private MyAdapter mAdapter;
    private boolean mIsRefresh;
    
    private View sendButton;
    private EditText commentEdit;
    
    private String type;
    private String typeid;
    
    private final int LOAD_MORE_COUNT = 10;
    
	private View mEmptyView;
	
	private boolean showComment;
	
	private boolean isOnFouce = true;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_list_layout);
        if(getIntent().getExtras()!=null){
            type = getIntent().getExtras().getString("type");
            typeid = getIntent().getExtras().getString("typeid");
            showComment = getIntent().getExtras().getBoolean("showComment");
            isOnFouce = getIntent().getExtras().getBoolean("isOnFouce");
            android.util.Log.d("111", "isOnFouce = " + isOnFouce + ",showComment = " + showComment);
        }
        setUpViews();
        initData();
        
        new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
			  if(isOnFouce){
			    	commentEdit.setFocusable(true);
			        commentEdit.setFocusableInTouchMode(true);
			        commentEdit.requestFocus();
			        InputMethodManager inputManager =
			                    (InputMethodManager)commentEdit.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
			        inputManager.showSoftInput(commentEdit, 0);	
		        }				
			}
		}, 1000);
    }

    private void setUpViews(){
        TitleBar mTitleBar = (TitleBar) findViewById(R.id.title_bar);
        mTitleBar.setBackListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
        mTitleBar.setTitle("评论");
        
        mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.list);
        mListView = mPullToRefreshListView.getRefreshableView();
        mLoadingView = findViewById(R.id.loading_layout);
        
        LayoutInflater inflater = LayoutInflater.from(this);
        mRefreshLayout = (ViewGroup) inflater.inflate(R.layout.pull_to_refresh_footer, null);
        
        mAdapter = new MyAdapter(this);
        mListView.setAdapter(mAdapter);
        mListView.setOnScrollListener(this);
        
        mPullToRefreshListView.setOnRefreshListener(this);
        
        sendButton = findViewById(R.id.send_button);
        commentEdit = (EditText) findViewById(R.id.comment_edit);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	if(UserUtil.getUser()==null){
            		LoginAndRegisterActivity.startActivity(CommentListActivity.this, true);
            		finish();
            	}else{
            		 mControl.actionShareComment(typeid, type, commentEdit.getText().toString());
            	}
            }
        });
        
        mEmptyView= findViewById(R.id.empty_view);
		mEmptyView.findViewById(R.id.reload_button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showProgress();
				initData();
			}
		});
		
		View commentLayout = findViewById(R.id.comment_layout);
		if(showComment){
			commentLayout.setVisibility(View.VISIBLE);
		}else{
			commentLayout.setVisibility(View.GONE);
		}
    }
    
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        int position = mListView.getLastVisiblePosition();
        android.util.Log.d("111", "position = " + position + ",mAdapter.getCount() = " + mAdapter.getCount() + ",mIsRefresh = " + mIsRefresh);
        if(!mIsRefresh && position == mAdapter.getCount()/* && mAdapter.getCount()>=LOAD_MORE_COUNT*/){
            getMoreData();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
            int visibleItemCount, int totalItemCount) {
        
    }

    @Override
    public void onRefresh() {
        mIsRefresh = true;
//        mControl.getCommentListData("1010", "goods");
        mControl.getCommentListData(typeid, type);
    }
    
    private void initData(){
        mIsRefresh = true;
//        mControl.getCommentListData("1010", "goods");
        showProgress();
        mControl.getCommentListData(typeid, type);
    }
    
    private void getMoreData(){
        if(!mRefreshLayout.isShown())
//            mRefreshLayout.setVisibility(View.VISIBLE);
//        mPullToRefreshListView.addFooterView(mRefreshLayout);
        mControl.getCommentListDataMore(typeid, type);
        mIsRefresh = true;
    }
    
    // ====================================== CallBack ============================================
    
    public void getCommentListDataCallBack(){
        mAdapter.clear();
        mAdapter.addAll(mControl.getModel().getCommentList());
        mAdapter.notifyDataSetChanged();
        dissProgress();
        mPullToRefreshListView.onRefreshComplete();
        Toast.makeText(this, "加载完成", 0).show();
        mIsRefresh = false;
    }
    
    public void getCommentListDataCallBackNull(){
    	Toast.makeText(this, "暂无评论", 0).show();
        dissProgress();
        mPullToRefreshListView.onRefreshComplete();
        showNull();
        mIsRefresh = false;
    }
    
    public void getCommentListDataExceptionCallBack(){
        Toast.makeText(this, "网络异常", 0).show();
        dissProgress();
        showEmpty();
        mPullToRefreshListView.onRefreshComplete();
        mIsRefresh = false;
    }
    
    public void getCommentListDataMoreCallBack(){
        mAdapter.addAll(mControl.getModel().getCommentList());
        mAdapter.notifyDataSetChanged();
        mIsRefresh = false;
//        mPullToRefreshListView.removeFooterView(mRefreshLayout);
//        Toast.makeText(this, "加载完成", 0).show();
    }
    
    public void getCommentListDataMoreExceptionCallBack(){
//        Toast.makeText(this, "网络异常", 0).show();
      	mIsRefresh = false;
//  		mPullToRefreshListView.removeFooterView(mRefreshLayout);
    }
    
    public void actionShareCommentCallBack(){
    	Toast.makeText(this, "评论成功", 0).show();
    	initData();
    	commentEdit.setText("");
    	InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);  
    	imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);  
    }
    
    public void actionShareCommentExceptionCallBack(){
    	Toast.makeText(this, "评论失败", 0).show();
    	commentEdit.setText("");
    	InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);  
    	imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);  
    }
    
    // ====================================== loading ============================================
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
	
	private void showNull(){
		mLoadingView.setVisibility(View.GONE);
		mPullToRefreshListView.setVisibility(View.GONE);
		mEmptyView.setVisibility(View.GONE);
	}
    
    // ====================================== Adapter ============================================
    private class MyAdapter extends ArrayAdapter<CommentItem> {
        
        public MyAdapter(Context context) {
            super(context, 0);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView != null ? convertView : LayoutInflater.from(getContext()).inflate(R.layout.item_comment_layout, parent, false);
            if (position != 0) {
                UiUtil.findViewById(itemView, R.id.line).setVisibility(View.GONE);
                UiUtil.findViewById(itemView, R.id.top_layout).setVisibility(View.GONE);
            }
            CommentItem item = getItem(position);
            ImageLoader.getInstance().displayImage(item.getAvatar(), UiUtil.findImageViewById(itemView, R.id.user_icon));
            UiUtil.findTextViewById(itemView, R.id.user_name).setText(item.getUsername());
            UiUtil.findTextViewById(itemView, R.id.create_time).setText(DateUtils.formateDate(Long.valueOf(item.getCreatedate())*1000));
            UiUtil.findTextViewById(itemView, R.id.comment_txt).setText(item.getContent());;
            return itemView;
        }
    }
}
