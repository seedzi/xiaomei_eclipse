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
import com.xiaomei.yanyu.util.DateUtils;
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
    
    public static void startActivity(Context context,String type,String goodsId){
        Intent intent = new Intent(context,CommentListActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("goodsId", goodsId);
        android.util.Log.d("111", "type = " + type + ",goodsId = " + goodsId);
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
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_list_layout);
        if(getIntent().getExtras()!=null){
            type = getIntent().getExtras().getString("type");
            goodsId = getIntent().getExtras().getString("goodsId");
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
        
        mPullToRefreshListView.setOnRefreshListener(this);
        
        sendButton = findViewById(R.id.send_button);
        commentEdit = (EditText) findViewById(R.id.comment_edit);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mControl.actionShareComment(goodsId, type, commentEdit.getText().toString());
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
    }
    
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        int position = mListView.getLastVisiblePosition();
        if(!mIsRefresh && position == mAdapter.getCount() && mAdapter.getCount()>=LOAD_MORE_COUNT){
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
        if(mIsRefresh)
            return;
        if(!mRefreshLayout.isShown())
            mRefreshLayout.setVisibility(View.VISIBLE);
        mPullToRefreshListView.addFooterView(mRefreshLayout);
        mControl.getCommentListDataMore("1010", "goods");
        mIsRefresh = true;
    }
    
    // ====================================== CallBack ============================================
    
    public void getCommentListDataCallBack(){
        mAdapter.setData(mControl.getModel().getCommentList());
        mAdapter.notifyDataSetChanged();
        dissProgress();
        mPullToRefreshListView.onRefreshComplete();
        Toast.makeText(this, "加载完成", 0).show();
    }
    
    public void getCommentListDataExceptionCallBack(){
        Toast.makeText(this, "网络异常", 0).show();
        dissProgress();
        showEmpty();
        mPullToRefreshListView.onRefreshComplete();
    }
    
    public void getCommentListDataMoreCallBack(){
        mAdapter.getData().addAll(mControl.getModel().getCommentList());
        mAdapter.notifyDataSetChanged();
        Toast.makeText(this, "加载完成", 0).show();
    }
    
    public void getCommentListDataMoreExceptionCallBack(){
        Toast.makeText(this, "网络异常", 0).show();
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
