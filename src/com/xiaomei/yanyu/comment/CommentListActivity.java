package com.xiaomei.yanyu.comment;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
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

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.bean.BeautifulRing;
import com.xiaomei.yanyu.bean.CommentItem;
import com.xiaomei.yanyu.comment.control.CommentListControl;
import com.xiaomei.yanyu.module.user.LoginAndRegisterActivity;
import com.xiaomei.yanyu.util.DateUtils;
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
    public static void startActivity(Context context,String type,String goodsId){
        Intent intent = new Intent(context,CommentListActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("goodsId", goodsId);
        android.util.Log.d("111", "type = " + type + ",goodsId = " + goodsId);
        context.startActivity(intent);
    }
    
    public static void startActivity(Context context,String type,String goodsId,boolean showComment){
        Intent intent = new Intent(context,CommentListActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("goodsId", goodsId);
        intent.putExtra("showComment", showComment);
        android.util.Log.d("111", "type = " + type + ",goodsId = " + goodsId + ",showComment = " + showComment);
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
    private String goodsId;
    
    private final int LOAD_MORE_COUNT = 10;
    
	private View mEmptyView;
	
	private boolean showComment;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_list_layout);
        if(getIntent().getExtras()!=null){
            type = getIntent().getExtras().getString("type");
            goodsId = getIntent().getExtras().getString("goodsId");
            showComment = getIntent().getExtras().getBoolean("showComment");
        }
        setUpViews();
        initData();
        
        // test 
        /*
        new Thread(new Runnable() {
            @Override
            public void run() {
                mControl.actionShareComment(goodsId, type, "哈哈哈哈哈哈");
            }
        }).start();*/
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
            	android.util.Log.d("111", "goodsId = " + goodsId + ",type = " + type + ",comment = " + commentEdit.getText().toString());
            	if(UserUtil.getUser()==null){
            		LoginAndRegisterActivity.startActivity(CommentListActivity.this, true);
            		finish();
            	}else{
            		 mControl.actionShareComment(goodsId, type, commentEdit.getText().toString());
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
        mControl.getCommentListData(goodsId, type);
    }
    
    private void initData(){
        mIsRefresh = true;
//        mControl.getCommentListData("1010", "goods");
        showProgress();
        android.util.Log.d("111", "goodsId = " + goodsId + ",type = " + type);
        mControl.getCommentListData(goodsId, type);
    }
    
    private void getMoreData(){
        if(!mRefreshLayout.isShown())
//            mRefreshLayout.setVisibility(View.VISIBLE);
//        mPullToRefreshListView.addFooterView(mRefreshLayout);
        mControl.getCommentListDataMore(goodsId, type);
        mIsRefresh = true;
    }
    
    // ====================================== CallBack ============================================
    
    public void getCommentListDataCallBack(){
        mAdapter.setData(mControl.getModel().getCommentList());
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
        mAdapter.getData().addAll(mControl.getModel().getCommentList());
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
    private class MyAdapter extends BaseAdapter {
        
        private LayoutInflater mLayoutInflater;

        private List<CommentItem> mData;
        
        public MyAdapter(Context context) {
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
                convertView = mLayoutInflater.inflate(R.layout.item_comment_layout, null);
                holder = new Holder();
                holder.topLayout = convertView.findViewById(R.id.top_layout);
                holder.commentSize = (TextView) convertView.findViewById(R.id.comment_size_tag);
                holder.userIcon = (ImageView) convertView.findViewById(R.id.user_icon);
                holder.userName = (TextView) convertView.findViewById(R.id.user_name);
                holder.userSex = (TextView) convertView.findViewById(R.id.user_sex);
                holder.userLocation = (TextView) convertView.findViewById(R.id.user_location);
                holder.createTime = (TextView) convertView.findViewById(R.id.create_time);
                holder.commentTxt = (TextView) convertView.findViewById(R.id.comment_txt);
                holder.gentieSize = (TextView) convertView.findViewById(R.id.gentie_size);
                holder.line = convertView.findViewById(R.id.line);
                convertView.setTag(holder);
            }
            holder = (Holder) convertView.getTag();
            if(position!=0){
            	holder.line.setVisibility(View.GONE);
                holder.topLayout.setVisibility(View.GONE);
            }
            attachDate(holder, mData.get(position));
            return convertView;
        }
        
        private void attachDate(Holder holder,CommentItem bean){
        	android.util.Log.d("111", "avata = " + bean.getAvatar());
            ImageLoader.getInstance().displayImage(bean.getAvatar(), holder.userIcon);
            holder.userName.setText(bean.getUsername());
            holder.createTime.setText(DateUtils.formateDate(Long.valueOf(bean.getCreatedate())*1000));
            holder.commentTxt.setText(bean.getContent());
        }
        
        private void setData(List<CommentItem> data){
            mData = data;
        }
        
        private List<CommentItem> getData(){
            return mData;
        }
        
        private class Holder{
            private View topLayout; //顶部view 
            private TextView commentSize; //用户评论数
            private ImageView userIcon; //用户头像
            private TextView userName; //用户名
            private TextView userSex; //用户性别
            private TextView userLocation;  //用户地址
            private TextView createTime;  //创建时间
            private TextView commentTxt;//评论文案
            private TextView gentieSize; //跟帖数
            private View line;
        }
        
    }
}
