package com.xiaomei.yanyu.module.user.center;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaomei.yanyu.AbstractActivity;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.api.HttpUrlManager;
import com.xiaomei.yanyu.bean.Goods;
import com.xiaomei.yanyu.leveltwo.GoodsDetailActivity;
import com.xiaomei.yanyu.module.user.center.control.SalesControl;
import com.xiaomei.yanyu.widget.TitleBar;
import com.xiaomei.yanyu.widget.pullrefreshview.PullToRefreshListView;
import com.xiaomei.yanyu.widget.pullrefreshview.PullToRefreshBase.OnRefreshListener;

public class SalesMessageActivity extends AbstractActivity<SalesControl> implements OnScrollListener,OnRefreshListener{
    
    public static void startActivity(Activity ac){
        Intent intent = new Intent(ac,SalesMessageActivity.class);
        ac.startActivity(intent);
         ac.overridePendingTransition(R.anim.activity_slid_in_from_right, R.anim.activity_slid_out_no_change);
    }

    private TitleBar mTitleBar;
    
    private PullToRefreshListView mPullToRefreshListView;
    
    private MyAdapter mAdapter;
    
    private ListView mListView;
    
    private boolean mIsRefresh;
    
    
    private View mLoadingView; 
    
    private ViewGroup mRefreshLayout;
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_list_layout);
        initView();
        initData();
    };
    
    private void initView() {
        mTitleBar = (TitleBar) findViewById(R.id.titlebar);
        mTitleBar.setTitle("促销消息");
        mTitleBar.setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        mTitleBar.findViewById(R.id.right_root).setVisibility(View.VISIBLE);
//        mTitleBar.findViewById(R.id.comment).setVisibility(View.GONE);
//        mTitleBar.findViewById(R.id.share).setVisibility(View.GONE);
//        mTitleBar.findViewById(R.id.fav).setVisibility(View.GONE);
        
        mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.list);
        mPullToRefreshListView.setOnRefreshListener(this);
        mAdapter = new MyAdapter(this);
        mPullToRefreshListView.getRefreshableView().setAdapter(mAdapter);
        mPullToRefreshListView.setOnScrollListener(this);
        mListView = mPullToRefreshListView.getRefreshableView();
        mListView.setEmptyView(findViewById(R.id.empty));
        mLoadingView = findViewById(R.id.loading_layout);
        mRefreshLayout = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.pull_to_refresh_footer, null);
    }
    
    private void initData(){
        showProgress();
        mIsRefresh = true;
        mControl.getGoodsDataAsyn();
    }
    
    @Override
    public void onRefresh() {
        mIsRefresh = true;
        mControl.getGoodsDataAsyn();
    }
    
    private void getMoreData(){
        if(mIsRefresh)
            return;
        if(!mRefreshLayout.isShown())
            mRefreshLayout.setVisibility(View.VISIBLE);
        mPullToRefreshListView.addFooterView(mRefreshLayout);
        mControl.getGoodsDataMoreAsyn();
        mIsRefresh = true;
    }
    
    // ============================== OnScroll ==========================================
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        int position = mListView.getLastVisiblePosition();
        if(!mIsRefresh && position == mAdapter.getCount()){
            getMoreData();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
            int visibleItemCount, int totalItemCount) {
    }
    
    // ============================== ProgressDialog ==========================================
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
    
    // ============================== CallBack ==========================================
    public void getGoodsDataAsynCallBack(){
        mAdapter.setData(mControl.getModel().getGoodsList());
        mAdapter.notifyDataSetChanged();
        mIsRefresh = false;
        dissProgress();
        mPullToRefreshListView.onRefreshComplete();
        Toast.makeText(this, "加载成功", 0).show();
    }
    
    public void getGoodsDataAsynExceptionCallBack(){
        Toast.makeText(this, "加载数据错误", 0).show();
        mIsRefresh = false;
        dissProgress();
        mPullToRefreshListView.onRefreshComplete();
    }
    
    public void getGoodsDataMoreAsynCallBack(){
        dissProgress();
        mIsRefresh = false;
        mAdapter.getData().addAll(mControl.getModel().getGoodsList());
        mAdapter.notifyDataSetChanged();
        mPullToRefreshListView.removeFooterView(mRefreshLayout);
        Toast.makeText(this, getResources().getString(R.string.get_data_sucess), 0).show();
    }
    
    public void getGoodsDataMoreAsynExceptionCallBack(){
        dissProgress();
        mIsRefresh = false;
        mPullToRefreshListView.removeFooterView(mRefreshLayout);
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
                holder.mark1 = (TextView) convertView.findViewById(R.id.tag_1);
                holder.mark2 = (TextView) convertView.findViewById(R.id.tag_2);
                holder.mark3 = (TextView) convertView.findViewById(R.id.tag_3);
                convertView.setOnClickListener(this);
                convertView.setTag(holder);
            }
            holder = (Holder) convertView.getTag();
            Goods goods = mData.get(position);
            holder.iconIv.setImageResource(R.drawable.goods_list_default);
            ImageLoader.getInstance().displayImage(goods.getFileUrl(),holder.iconIv );
            holder.titleTv .setText(goods.getTitle());
            holder.goodId = goods.getId();
            holder.sizeTv.setText("销量" + goods.getSales());
            holder.hospitalTv.setText(goods.getHospName());
            holder.priceTv.setText(getResources().getString(R.string.ren_ming_bi)+" "+ goods.getPriceXm());
            holder.localTv.setText(goods.getCityName());
            
            holder.mark1.setVisibility(View.GONE);
            holder.mark2.setVisibility(View.GONE);
            holder.mark3.setVisibility(View.GONE);
            List<Goods.Mark> marks = goods.getMarks();
            int i = 0;
            GradientDrawable shapeDrawable  = null;
            holder.mark1.setVisibility(View.GONE);
            holder.mark2.setVisibility(View.GONE);
            holder.mark3.setVisibility(View.GONE);
            if(marks!=null){
                for(Goods.Mark mark:marks){
                    switch (i) {
                    case 0:
                        holder.mark1.setVisibility(View.VISIBLE);
                        shapeDrawable = new GradientDrawable();
                        shapeDrawable.setCornerRadius(8);
                        shapeDrawable.setColor(Color.parseColor(mark.getColor()));
                        holder.mark1.setBackground(shapeDrawable);
                        holder.mark1.setText(mark.getLabel());
                        break;
                    case 1:
                        holder.mark2.setVisibility(View.VISIBLE);
                        shapeDrawable = new GradientDrawable();
                        shapeDrawable.setCornerRadius(8);
                        shapeDrawable.setColor(Color.parseColor(mark.getColor()));
                        holder.mark2.setBackground(shapeDrawable);
                        holder.mark2.setText(mark.getLabel());
                        break;
                    case 2:
                        holder.mark3.setVisibility(View.VISIBLE);
                        shapeDrawable = new GradientDrawable();
                        shapeDrawable.setCornerRadius(8);
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
//          holder.sizeTv.setText(goods.getPriceMarket());
//          holder.hospitalTv.setText(goods.ge);
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
            TextView mark1;
            TextView mark2;
            TextView mark3;
        }

        @Override
        public void onClick(View v) {
            Holder holder = (Holder) v.getTag();
            GoodsDetailActivity.startActivity(SalesMessageActivity.this,HttpUrlManager.GOODS_DETAIL_URL+"?goods_id="+holder.goodId,holder.goodId);
        }
        
    }
}
