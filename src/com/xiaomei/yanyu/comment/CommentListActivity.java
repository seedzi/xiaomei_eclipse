package com.xiaomei.yanyu.comment;

import java.util.Date;
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
import android.widget.LinearLayout;
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
import com.xiaomei.yanyu.bean.ShareSubcomment;
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
    private TextView commentSize;
    private String type;
    private String typeid;
    private String mFocusCommentId;
    private String mFocusUserId;
    
    private final int LOAD_MORE_COUNT = 10;
    
	private View mEmptyView;
	
	private boolean showComment;
	
	private boolean isOnFouce = true;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_list_layout);
        if(getIntent()!=null){
            type = getIntent().getStringExtra("type");
            typeid = getIntent().getStringExtra("typeid");
            showComment = getIntent().getBooleanExtra("showComment", false);
            isOnFouce = getIntent().getBooleanExtra("isOnFouce", true);
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
        mListView.addHeaderView(getLayoutInflater().inflate(R.layout.header_comment_list, null));
        commentSize = (TextView) findViewById(R.id.comment_size);
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
            	    if (mFocusCommentId != null) {
            	        mControl.actionShareSubcomment(typeid, mFocusCommentId, mFocusUserId, commentEdit.getText().toString());
            	    } else {
            	        mControl.actionShareComment(typeid, type, commentEdit.getText().toString());
                    }
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
        try {
            commentSize.setText("(" + mControl.getModel().getCommentList().get(0).getTotal()+ ")");
        } catch (Exception e) {
        }
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
    	clearInput();  
    }
    
    public void actionShareCommentExceptionCallBack(){
    	Toast.makeText(this, "评论失败", 0).show();
    	clearInput();  
    }
    
    public void actionShareSubcommentCallBack() {
        Toast.makeText(this, "子评论成功", 0).show();
        initData();
        clearInput();
    }
    
    public void actionShareSubcommentExceptionCallBack() {
        Toast.makeText(this, "子评论失败", 0).show();
        clearInput();
    }
    
    private void clearInput() {
        commentEdit.setText("");
        commentEdit.setHint(R.string.share_comment_hint);
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

            final CommentItem item = getItem(position);
            ImageLoader.getInstance().displayImage(item.getAvatar(), UiUtil.findImageViewById(itemView, R.id.user_icon));
            UiUtil.findTextViewById(itemView, R.id.user_name).setText(item.getUsername());
            UiUtil.findTextViewById(itemView, R.id.create_time).setText(DateUtils.getTextByTime(getContext(), Long.valueOf(item.getCreatedate()), R.string.date_fromate_anecdote));
            UiUtil.findTextViewById(itemView, R.id.comment_txt).setText(item.getContent());;
            LinearLayout subcommentList = UiUtil.<LinearLayout>findById(itemView, R.id.subcomment_list);
            TextView subcomment = UiUtil.findTextViewById(itemView, R.id.subcomment);
            subcomment.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mFocusCommentId = item.getId();
                    mFocusUserId = item.getUserid();
                    commentEdit.setHint(getString(R.string.share_subcomment_hint, item.getUsername()));
                }
            });
            ShareSubcomment[] subcomments = item.getSubcomments();
            subcommentList.removeAllViews();
            if (subcomments != null && subcomments.length > 0) {
                addSubcommentView(subcommentList, subcomments);
                subcomment.setText(String.valueOf(subcomments.length));
            } else {
                subcomment.setText(null);
            }
            return itemView;
        }

        private void addSubcommentView(ViewGroup list, ShareSubcomment[] subcomments) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            for (ShareSubcomment subcomment : subcomments) {
                View itemView = inflater.inflate(R.layout.subcomment_list_item, list, false);
                String subcommentString = String.format("%s：%s", subcomment.from_name, subcomment.comment);
                UiUtil.findTextViewById(itemView, android.R.id.text1).setText(subcommentString);
                list.addView(itemView);
            }
        }
    }
}
