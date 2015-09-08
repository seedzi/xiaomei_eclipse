package com.xiaomei.yanyu.module.user.center;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaomei.yanyu.AbstractActivity;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.api.HttpUrlManager;
import com.xiaomei.yanyu.bean.Goods;
import com.xiaomei.yanyu.bean.Goods.Mark;
import com.xiaomei.yanyu.leveltwo.GoodsAdapter;
import com.xiaomei.yanyu.leveltwo.GoodsDetailActivity;
import com.xiaomei.yanyu.module.user.center.control.UserCenterControl;
import com.xiaomei.yanyu.util.UiUtil;
import com.xiaomei.yanyu.widget.TitleBar;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class CollectionActivity extends AbstractActivity<UserCenterControl> implements OnScrollListener,OnRefreshListener,View.OnClickListener{
	
    private Map< String, String> mCheckedData = new HashMap<String, String>();

	public static void startActivity(Activity ac){
		Intent intent = new Intent(ac,CollectionActivity.class);
		ac.startActivity(intent);
		ac.overridePendingTransition(R.anim.activity_slid_in_from_right, R.anim.activity_slid_out_no_change);
	}

	private boolean showEdite = false ;
	
	private TitleBar mTitleBar;
	
	private ListView mListView;
	
	private CollectionAdapter mAdapter;
	
	private boolean mIsRefresh;
	
	private View mLoadingView; 
	
	private ViewGroup mRefreshLayout;
	
	private PullToRefreshListView mPullToRefreshListView;
	
	private TextView mEdit;
	
	private TextView mDelete;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_collection_layout);
		setUpView();
		initData();
	}
	
	private void setUpView(){
		mTitleBar = (TitleBar) findViewById(R.id.titlebar);
		mTitleBar.setTitle(getResources().getString(R.string.user_collection));
		mTitleBar.setBackListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		mTitleBar.findViewById(R.id.right_root).setVisibility(View.VISIBLE);
		mTitleBar.findViewById(R.id.comment).setVisibility(View.GONE);
		mTitleBar.findViewById(R.id.share).setVisibility(View.GONE);
		mTitleBar.findViewById(R.id.fav).setVisibility(View.GONE);
		mTitleBar.findViewById(R.id.edit).setVisibility(View.VISIBLE);
		
		mEdit = (TextView) findViewById(R.id.edit);
		mEdit.setOnClickListener(this);
		mEdit.setText("编辑");
		
		mDelete = (TextView) findViewById(R.id.delete);
		mDelete.setOnClickListener(this);
		
		mLoadingView = findViewById(R.id.loading_layout);
        mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.list);
        mPullToRefreshListView.setOnRefreshListener(this);
		mRefreshLayout = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.pull_to_refresh_footer, null);
        mListView = mPullToRefreshListView.getRefreshableView();
        mAdapter = new CollectionAdapter(CollectionActivity.this);
        mListView.setAdapter(mAdapter);
        mListView.setOnScrollListener(this);
        mListView.setOnItemClickListener(new GoodsAdapter.GoodsItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (showEdite) {
                    Goods goods = (Goods) parent.getItemAtPosition(position);
                    String goodsId = goods.getId();
                    boolean checked = mCheckedData.get(goodsId) != null;
                    UiUtil.<CheckBox>findById(view, R.id.checkbox).setChecked(!checked);
                    if (checked) {
                        mCheckedData.remove(goodsId);
                    } else {
                        mCheckedData.put(goodsId, goodsId);
                    }
                    return;
                }

                super.onItemClick(parent, view, position, id);
            }
        });
	}
	
	private void initData(){
		mEdit.setEnabled(false);
        showProgress();
        mIsRefresh = true;
        mControl.getUserFav();
	}
	
	private void getMoreData(){
	       if(mIsRefresh)
	            return;
	        if(!mRefreshLayout.isShown())
	            mRefreshLayout.setVisibility(View.VISIBLE);
	        mPullToRefreshListView.getRefreshableView().addFooterView(mRefreshLayout);
	        mControl.getUserFavMore();
	        mIsRefresh = true;
	}
	
  // ============================== Progressbar ==========================================
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
	
	// ============================== Callback ==========================================
	public void getUserFavCallBack(){
	    mAdapter.clear();
	       mAdapter.addAll(mControl.getModel().getGoodsList());
	        mAdapter.notifyDataSetChanged();
	        mIsRefresh = false;
	        dissProgress();
	        mPullToRefreshListView.onRefreshComplete();
	        Toast.makeText(this, "加载成功", 0).show();
	        dismissDialog();
	        mEdit.setEnabled(true);
	}
	
	public void getUserFavExceptionCallBack(){
	       Toast.makeText(this, "加载数据错误", 0).show();
	        mIsRefresh = false;
	        dissProgress();
	        mPullToRefreshListView.onRefreshComplete();
	        dismissDialog();
	        mEdit.setEnabled(true);
	}
	
	public void getUserFavMoreCallBack(){
	       dissProgress();
	        mIsRefresh = false;
	        mAdapter.addAll(mControl.getModel().getGoodsList());
	        mAdapter.notifyDataSetChanged();
	        mPullToRefreshListView.getRefreshableView().removeFooterView(mRefreshLayout);
	        Toast.makeText(this, getResources().getString(R.string.get_data_sucess), 0).show();
	        dismissDialog();
	        mEdit.setEnabled(true);
	}
	
	public void getUserFavMoreExceptionCallBack(){
	       dissProgress();
	        mIsRefresh = false;
	        mPullToRefreshListView.getRefreshableView().removeFooterView(mRefreshLayout);
	        dismissDialog();
	        mEdit.setEnabled(true);
	}
	
	public void deleteUserFavCallBack(){
//		showEdite = false;
//        mEdit.setText("编辑");
//        mDelete.setVisibility(View.GONE);
//        mCheckedData.clear();
        dismissDialog();
        Toast.makeText(this, "删除成功", 0).show();
        initData();
	}
	
	public void deleteUserFavExceptionCallBack(){
		showEdite = false;
        mEdit.setText("编辑");
        mDelete.setVisibility(View.GONE);
        mCheckedData.clear();
        dismissDialog();
        Toast.makeText(this, "删除失败", 0).show();
        mEdit.setEnabled(true);
	}
	// =========================================== ProgressDialog ==========================================
	private ProgressDialog mProgressDialog;
	private void showProgressDialog(String message){
		if(mProgressDialog!=null && mProgressDialog.isShowing())
			mProgressDialog.dismiss();
		mProgressDialog = new ProgressDialog(this);
		mProgressDialog.setTitle("提示");
		mProgressDialog.setMessage(message);
		mProgressDialog.setCancelable(false);
		mProgressDialog.show();
	}
	
	private void dismissDialog(){
		if(mProgressDialog!=null && mProgressDialog.isShowing())
			mProgressDialog.dismiss();
	}
	// ============================== Adapter ==========================================
    private class CollectionAdapter extends GoodsAdapter {

        public CollectionAdapter(Context context) {
            super(context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = super.getView(position, convertView, parent);
            
            CheckBox checkBox = UiUtil.<CheckBox> findById(itemView, R.id.checkbox);
            checkBox.setVisibility(showEdite ? View.VISIBLE : View.GONE);
            checkBox.setChecked(mCheckedData.get(getItem(position).getId()) != null);

            return itemView;
        }
    }

    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
    	mEdit.setEnabled(false);
        mIsRefresh = true;
        mControl.getUserFav();

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        int position = mListView.getLastVisiblePosition();
        if (!mIsRefresh && position == mAdapter.getCount()) {
            getMoreData();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
            int visibleItemCount, int totalItemCount) {
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
        case R.id.delete:
        	DeleteCollectionDailogActivity.startActivity(this,mCheckedData.size());
            break;
        case R.id.edit:
        	if(mAdapter.isEmpty()){
        		return;
        	}
            if(showEdite){
            	  showEdite = false;
                  mEdit.setText("编辑");
                  mDelete.setVisibility(View.GONE);
                  mCheckedData.clear();
            }else{
                showEdite = true;
                mEdit.setText("完成");
                mDelete.setVisibility(View.VISIBLE);
            }
            mAdapter.notifyDataSetChanged();
            break;
        default:
            break;
        }
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if(requestCode == 1 && resultCode == RESULT_OK){
    		StringBuilder goodsIds = new StringBuilder();
    		Set<String> set = mCheckedData.keySet();
    		int i = 0;
    		for(String str : set){
    			goodsIds.append(str);
    			if(i!=mCheckedData.size()-1){
    				goodsIds.append(",");
    			}
    			i++;
    		}
    		showProgressDialog("删除中...");
    		mControl.deleteUserFav(goodsIds.toString());
    	}
    	super.onActivityResult(requestCode, resultCode, data);
    }
    
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
    	if(keyCode==KeyEvent.KEYCODE_BACK){
    		if(showEdite){
    			showEdite = false;
    	        mEdit.setText("编辑");
                mDelete.setVisibility(View.GONE);
                mCheckedData.clear();
                return true;
    		}
    	}
    	return super.onKeyUp(keyCode, event);
    }
    
}
