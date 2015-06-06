package com.xiaomei.yanyu.module.user.center;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.AbstractActivity;
import com.xiaomei.yanyu.api.HttpUrlManager;
import com.xiaomei.yanyu.bean.Goods;
import com.xiaomei.yanyu.leveltwo.GoodsDetailActivity;
import com.xiaomei.yanyu.module.user.center.control.UserCenterControl;
import com.xiaomei.yanyu.widget.TitleBar;
import com.xiaomei.yanyu.widget.pullrefreshview.PullToRefreshListView;
import com.xiaomei.yanyu.widget.pullrefreshview.PullToRefreshBase.OnRefreshListener;

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
	
	private MyAdapter mAdapter;
	
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
        mAdapter = new MyAdapter(CollectionActivity.this);
        mListView.setAdapter(mAdapter);
        mListView.setOnScrollListener(this);
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
	        mPullToRefreshListView.addFooterView(mRefreshLayout);
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
	       mAdapter.setData(mControl.getModel().getGoodsList());
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
	        mAdapter.getData().addAll(mControl.getModel().getGoodsList());
	        mAdapter.notifyDataSetChanged();
	        mPullToRefreshListView.removeFooterView(mRefreshLayout);
	        Toast.makeText(this, getResources().getString(R.string.get_data_sucess), 0).show();
	        dismissDialog();
	        mEdit.setEnabled(true);
	}
	
	public void getUserFavMoreExceptionCallBack(){
	       dissProgress();
	        mIsRefresh = false;
	        mPullToRefreshListView.removeFooterView(mRefreshLayout);
	        dismissDialog();
	        mEdit.setEnabled(true);
	}
	
	public void deleteUserFavCallBack(){
		showEdite = false;
        mEdit.setText("编辑");
        mDelete.setVisibility(View.GONE);
        mCheckedData.clear();
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
	        private class MyAdapter extends BaseAdapter implements View.OnClickListener{
	            
	            private LayoutInflater mLayoutInflater;
	            
	            private List<Goods> mData;
	            
	            public MyAdapter(Context context){
	                mLayoutInflater = LayoutInflater.from(context);
	            }
	            
	            public void setData(List<Goods> data){
	                mData = data;
	            }
	            
	            public List<Goods> getData(){
	                return mData;
	            }
	            
	            @Override
	            public int getCount() {
	                return mData==null ?0 : mData.size();
	            }

	            @Override
	            public Object getItem(int position) {
	                return null;
	            }

	            @Override
	            public long getItemId(int position) {
	                return 0;
	            }

	            @SuppressLint("NewApi")
				@Override
	            public View getView(int position, View convertView, ViewGroup parent) {
	                Holder holder = null;
	                if(convertView == null){
	                    convertView = mLayoutInflater.inflate(R.layout.item_goods_layout, null);
	                    holder = new Holder();
	                    holder.iconIv = (ImageView) convertView.findViewById(R.id.icon);
	                    holder.titleTv = (TextView) convertView.findViewById(R.id.title);
	                    holder.sizeTv = (TextView) convertView.findViewById(R.id.size);
	                    holder.hospitalTv = (TextView) convertView.findViewById(R.id.hospital_name);
	                    holder.localTv = (TextView) convertView.findViewById(R.id.location);
	                    holder.priceTv = (TextView) convertView.findViewById(R.id.price);
	                    holder.localTv = (TextView) convertView.findViewById(R.id.location);
	                    holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);
	                    holder.mark1 = (TextView) convertView.findViewById(R.id.tag_1);
	                    holder.mark2 = (TextView) convertView.findViewById(R.id.tag_2);
	                    holder.mark3 = (TextView) convertView.findViewById(R.id.tag_3);
	                    convertView.setTag(holder);
	                }
	                holder = (Holder) convertView.getTag();
	                Goods goods = mData.get(position);
	                
                    if(showEdite){
                        holder.checkBox.setOnClickListener(this);
                        convertView.setOnClickListener(null);
                    }else{
                        convertView.setOnClickListener(this);
                    }
	                
	                holder.iconIv.setImageResource(R.drawable.goods_list_default);
	                ImageLoader.getInstance().displayImage(goods.getFileUrl(),holder.iconIv );
	                holder.titleTv .setText(goods.getTitle());
	                holder.goodId = goods.getId();
	                holder.sizeTv.setText("销量" + goods.getSales());
	                holder.hospitalTv.setText(goods.getHospName());
	                holder.priceTv.setText(getResources().getString(R.string.ren_ming_bi)+" "+ goods.getPriceXm());
	                holder.localTv.setText(goods.getCityName());
	                holder.checkBox.setTag(position);
	                if(showEdite){
	                    holder.checkBox.setVisibility(View.VISIBLE);
	                    if(mCheckedData.get(goods.getId())!=null){
	                        holder.checkBox.setChecked(true);
	                    }else{
	                        holder.checkBox.setChecked(false);
	                    }
	                }else{
	                    holder.checkBox.setVisibility(View.GONE);
	                }
	                
	                List<Goods.Mark> marks = goods.getMarks();
	                int i = 0;
	                GradientDrawable shapeDrawable  = null;
	                if(marks!=null){
	                    for(Goods.Mark mark:marks){
	                        switch (i) {
	                        case 0:
	                            holder.mark1.setVisibility(View.VISIBLE);
	                            shapeDrawable = new GradientDrawable();
	                            shapeDrawable.setCornerRadius(15);
	                            shapeDrawable.setColor(Color.parseColor(mark.getColor()));
	                            holder.mark1.setBackground(shapeDrawable);
	                            holder.mark1.setText(mark.getLabel());
	                            break;
	                        case 1:
	                            holder.mark2.setVisibility(View.VISIBLE);
	                            shapeDrawable = new GradientDrawable();
	                            shapeDrawable.setCornerRadius(15);
	                            shapeDrawable.setColor(Color.parseColor(mark.getColor()));
	                            holder.mark2.setBackground(shapeDrawable);
	                            holder.mark2.setText(mark.getLabel());
	                            break;
	                        case 2:
	                            holder.mark3.setVisibility(View.VISIBLE);
	                            shapeDrawable = new GradientDrawable();
	                            shapeDrawable.setCornerRadius(15);
	                            shapeDrawable.setColor(Color.parseColor(mark.getColor()));
	                            holder.mark3.setBackground(shapeDrawable);
	                            holder.mark3.setText(mark.getLabel());
	                            break;
	                        default:
	                            break;
	                        }
	                        i++;
	                    }
	                }
	                return convertView;
	            }
	            
	            private class Holder{
	                ImageView iconIv;
	                TextView titleTv; 
	                TextView sizeTv;
	                TextView hospitalTv;
	                String goodId;
	                TextView localTv;
	                TextView priceTv;
	                CheckBox checkBox;
	                TextView mark1;
	                TextView mark2;
	                TextView mark3;
	            }

	            @Override
	            public void onClick(View v) {
	                int id = v.getId();
	                if(id == R.id.checkbox){
	                    CheckBox checkBox = (CheckBox) v;
	                    int position = (Integer) checkBox.getTag();
	                    Goods item = mData.get(position);
	                    if(checkBox.isChecked()){
	                        mCheckedData.put(item.getId(),item.getId());
	                    }else{
	                        mCheckedData.remove(item.getId());
	                    }
	                }else{
	                    Holder holder = (Holder) v.getTag();
//	                  OrderDetailsActivity.startActivity(GoodsListActivity.this,holder.goodId);
//	                  GoodsDetailActivity.startActivity(GoodsListActivity.this,holder.goodId);
//	                  GoodsDetailActivity.startActivity(GoodsListActivity.this,HttpUrlManager.GOODS_DETAIL_URL+"?goods_id="+holder.goodId);
	                      GoodsDetailActivity.startActivity(CollectionActivity.this,HttpUrlManager.GOODS_DETAIL_URL+"?goods_id="+holder.goodId,holder.goodId);
//	                  WebViewActivity.startActivity(GoodsListActivity.this ,"http://drxiaomei.duapp.com/goods.php?goods_id=1015");
	                }
	            }
	            
	        }

    @Override
    public void onRefresh() {
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
        	if(mAdapter.getData()==null || mAdapter.getData().size()==0){
        		return;
        	}
            if(showEdite){
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
