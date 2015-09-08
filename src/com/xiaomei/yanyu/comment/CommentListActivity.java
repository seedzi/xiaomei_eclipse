package com.xiaomei.yanyu.comment;

import java.util.List;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.XiaoMeiApplication;
import com.xiaomei.yanyu.api.XiaoMeiApi;
import com.xiaomei.yanyu.bean.CommentItem;
import com.xiaomei.yanyu.bean.NetResult;
import com.xiaomei.yanyu.bean.ShareSubcomment;
import com.xiaomei.yanyu.module.user.LoginAndRegisterActivity;
import com.xiaomei.yanyu.util.DateUtils;
import com.xiaomei.yanyu.util.UiUtil;
import com.xiaomei.yanyu.util.UserUtil;
import com.xiaomei.yanyu.widget.TitleActionBar;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CommentListActivity extends Activity 
    implements OnRefreshListener,OnScrollListener{
    
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
    
    private View sendButton;
    private EditText commentEdit;
    private TextView commentSize;
    private String type;
    private String typeid;
    private String mFocusCommentId;
    private String mFocusUserId;
    private View headView;
    
    private View mEmptyView;
	
    private RequestCommentListTask mRequestTask;

	private boolean showComment;
	
	private boolean isOnFouce = true;
    
	private boolean isInit = false;

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
    }

    private void setUpViews(){
        TitleActionBar titleBar = new TitleActionBar(getActionBar());
        titleBar.setTitle(R.string.activity_comment_list);
        
        mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.list);
        mListView = mPullToRefreshListView.getRefreshableView();
        headView = getLayoutInflater().inflate(R.layout.header_comment_list, null);
        mListView.addHeaderView(headView);
        commentSize = (TextView) headView.findViewById(R.id.comment_size);
        mLoadingView = findViewById(R.id.loading_layout);
        
        LayoutInflater inflater = LayoutInflater.from(this);
        mRefreshLayout = (ViewGroup) inflater.inflate(R.layout.pull_to_refresh_footer, null);
        
        mAdapter = new MyAdapter(this);
        mListView.setAdapter(mAdapter);
        mListView.setOnScrollListener(this);
        
        mPullToRefreshListView.setOnRefreshListener(this);
        
        sendButton = findViewById(R.id.send_button);
        commentEdit = (EditText) findViewById(R.id.comment_edit);
        commentEdit.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			@Override
			public void afterTextChanged(Editable s) {
				if(TextUtils.isEmpty(commentEdit.getText().toString())){
					sendButton.setEnabled(false);
					sendButton.getBackground().setAlpha(255/2);
				}else{
					sendButton.setEnabled(true);				
					sendButton.getBackground().setAlpha(255);
				}
			}
		});
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	if(UserUtil.getUser()==null){
            		LoginAndRegisterActivity.startActivity(CommentListActivity.this, true);
            		finish();
            	} else if (!TextUtils.isEmpty(commentEdit.getText())) {
                    new PostCommentTask().execute();
                }
            }
        });
		sendButton.getBackground().setAlpha(255/2);
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
        if(!isRequesting() && position == mAdapter.getCount() && !mRefreshLayout.isShown()){
            requestCommentList(true);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
            int visibleItemCount, int totalItemCount) {
        
    }

    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        requestCommentList();
    }

    private void initData(){
        showProgress();
        requestCommentList();
    }
    
    public void requestCommentList() {
        requestCommentList(false);
    }

    private void requestCommentList(boolean append) {
        if (!isRequesting()) {
            mRequestTask = new RequestCommentListTask();
            mRequestTask.execute(append);
        }
    }

    private boolean isRequesting() {
        return mRequestTask != null && mRequestTask.getStatus() != AsyncTask.Status.FINISHED;
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
	
    private class RequestCommentListTask extends AsyncTask<Object, Void, Object> {

        private final int PER_PAGE = 10;

        private boolean append;

        private int currentCount;

        @Override
        protected Object doInBackground(Object... params) {
            append = (Boolean) params[0];
            currentCount = mAdapter.getCount();
            int nextPage = currentCount % PER_PAGE == 0 ? currentCount / PER_PAGE + 1 : currentCount / PER_PAGE + 2;
            String requestPage = String.valueOf(append ? nextPage : 1);
            try {
                return XiaoMeiApplication.getInstance().getApi().showCommentList(typeid, type, requestPage, String.valueOf(PER_PAGE));
            } catch (Exception e) {
                e.printStackTrace();
                return e;
            }
        }

        @Override
        protected void onPostExecute(Object result) {
            if (result instanceof Exception) {
                Toast.makeText(CommentListActivity.this, "网络异常", 0).show();
                if (!append) {
                    dissProgress();
                    showEmpty();
                    mPullToRefreshListView.onRefreshComplete();
                }
                return;
            }

            List<CommentItem> list = (List<CommentItem>) result;
            int size = list != null ? list.size() : 0;
            if (!append) {
                mAdapter.clear();
            }
            if (size > 0) {
                mAdapter.addAll(list);
            }

            commentSize.setText("(" + mAdapter.getCount() + ")");
            if (!append) {
                dissProgress();
                mPullToRefreshListView.onRefreshComplete();
                Toast.makeText(CommentListActivity.this, size > 0 ? "加载完成" : "暂无评论", 0).show();
                if (!isInit) {
                    if (isOnFouce) {
                        commentEdit.setFocusable(true);
                        commentEdit.setFocusableInTouchMode(true);
                        commentEdit.requestFocus();
                        InputMethodManager inputManager = (InputMethodManager)commentEdit
                                .getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                        inputManager.showSoftInput(commentEdit,
                                InputMethodManager.SHOW_IMPLICIT);
                    }
                    isInit = true;
                }
            }
        }
    }

    public class PostCommentTask extends AsyncTask<Object, Void, Object> {

        private boolean subComment;

        @Override
        protected void onPreExecute() {
            clearInput();
        }

        @Override
        protected Object doInBackground(Object... params) {
            XiaoMeiApi httpApi = XiaoMeiApplication.getInstance().getApi();
            String comment = commentEdit.getText().toString();
            String token = UserUtil.getUser().getToken();
            subComment = mFocusCommentId != null;
            try {
                if (subComment) {
                    return httpApi.actionShareSubcomment(token, typeid, mFocusCommentId, mFocusUserId, comment);
                } else {
                    return httpApi.actionGoodsComment(token, typeid, type, comment);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return e;
            }
        }

        @Override
        protected void onPostExecute(Object result) {
            if (result instanceof NetResult) {
                NetResult netResult = (NetResult) result;
                if (netResult != null && "0".equals(netResult.getCode())) {
                    Toast.makeText(CommentListActivity.this, subComment ? "子评论成功" : "评论成功", 0).show();
                    initData();
                    return;
                }
            }
            
            Toast.makeText(CommentListActivity.this, subComment ? "子评论失败" : "评论失败", 0).show();
        }

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
            if(TextUtils.isEmpty(item.getAvatar())){
            	UiUtil.findImageViewById(itemView, R.id.user_icon).setImageResource(R.drawable.user_head_default);
            }else{
                ImageLoader.getInstance().displayImage(item.getAvatar(), UiUtil.findImageViewById(itemView, R.id.user_icon));
            }
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
            	itemView.findViewById(R.id.divider).setVisibility(View.VISIBLE);
            	subcommentList.setVisibility(View.VISIBLE);
                addSubcommentView(subcommentList, subcomments);
                subcomment.setText(String.valueOf(subcomments.length));
            } else {
                subcomment.setText(null);
            	itemView.findViewById(R.id.divider).setVisibility(View.GONE);
            	subcommentList.setVisibility(View.GONE);
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
