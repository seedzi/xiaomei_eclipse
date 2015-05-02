package com.xiaomei.yanyu.levelone;


import java.util.List;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.bean.Mall;
import com.xiaomei.yanyu.comment.CommentListActivity;
import com.xiaomei.yanyu.levelone.control.MallControl;
import com.xiaomei.yanyu.leveltwo.GoodsListActivity;
import com.xiaomei.yanyu.leveltwo.MallSecondActivity;
import com.xiaomei.yanyu.util.ScreenUtils;
import com.xiaomei.yanyu.widget.TitleBar;
import com.yuekuapp.BaseFragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class MallFragment extends BaseFragment<MallControl> {

	private ViewGroup mRootView;
	
	private GridView mGridView;
	
	private MailAdapter mMailAdapter;
	
	private TitleBar mTitleBar;
	
	private ImageView mTopIcon;
	
	private View mLoadingView; 
	
	private View mScrollview;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mRootView = (ViewGroup) inflater.inflate(R.layout.fragment_mail_layout, null);
		setUpView();
		initData();
		return mRootView;
	}
	
	private void setUpView(){
		mTitleBar = (TitleBar) mRootView.findViewById(R.id.title_bar);
		mTitleBar.setTitle(getResources().getString(R.string.fragment_mall));
		
		mGridView = (GridView) mRootView.findViewById(R.id.grid);
		mMailAdapter = new MailAdapter(getActivity());
		mGridView.setAdapter(mMailAdapter);
		
		mTopIcon = (ImageView) mRootView.findViewById(R.id.top_icon);
		mTopIcon.getLayoutParams().height = ScreenUtils.getScreenWidth(getActivity())*9/14;
//		mTopIcon.setImageResource(R.drawable.meinv);
		mTopIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                CommentListActivity.startActivity(getActivity());
            }
        });
		
		mScrollview = mRootView.findViewById(R.id.scrollview);
		mLoadingView = mRootView.findViewById(R.id.loading_layout);
	}
	
	private void initData(){
		showProgress();
		mControl.getMallListFromNetAsyn();
	}
	
	
	// ============================================  CallBack ==================================================
	public void getMallListFromNetAsynCallBack(){
		dissProgress();
		mMailAdapter.setData(mControl.getModel().getData());
		mMailAdapter.notifyDataSetChanged();
		ImageLoader.getInstance().displayImage(mControl.getModel().getHead().getImage(), mTopIcon);
	}
	
	public void getMallListFromNetAsynExceptionCallBack(){
		Toast.makeText(getActivity(), "网络异常", 0).show();
		dissProgress();
	}
	
	// ============================================  Progress ==================================================
	private void showProgress(){
		mLoadingView.setVisibility(View.VISIBLE);
		AnimationDrawable animationDrawable =  (AnimationDrawable) ((ImageView)mLoadingView.findViewById(R.id.iv)).getDrawable();
		if(!animationDrawable.isRunning())
			animationDrawable.start();
		mScrollview.setVisibility(View.GONE);
	}
	
	private void dissProgress(){
		mLoadingView.setVisibility(View.GONE);
		mScrollview.setVisibility(View.VISIBLE);
	}
	
	// ============================================  MailAdapter ==================================================
	private class MailAdapter extends BaseAdapter implements View.OnClickListener{
		
		private List<Mall> mData;
		
		public List<Mall> getData(){
			return mData;
		}
		
		public void setData(List<Mall> data){
			mData = data;
		}
		
//		private int[] icon_reses = { R.drawable.icon_roudushuchuzou_selector,
//				R.drawable.icon_tichongsuxing_selector,
//				R.drawable.icon_jinfutisheng_selector,
//				R.drawable.icon_roudusumeixing_selector,
//				R.drawable.icon_tianchognsuxing_selector,
//				R.drawable.icon_jiguangmeifu_selector,
//				R.drawable.icon_hanguotesemeirong_selector,
//				R.drawable.icon_jiguangchumao_selector,
//				R.drawable.icon_tixinagtiaosu_selector };
//
//		private int[] txt_reses = { R.string.mall_item_nam_1,
//				R.string.mall_item_nam_2, R.string.mall_item_nam_3,
//				R.string.mall_item_nam_4, R.string.mall_item_nam_5,
//				R.string.mall_item_nam_6, R.string.mall_item_nam_7,
//				R.string.mall_item_nam_8, R.string.mall_item_nam_9 };
		
		private LayoutInflater mLayoutInflater;
		
		public MailAdapter(Context context){
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
			Mall mall = mData.get(position);
			Holder holder = null;
			if(convertView == null){
				convertView = mLayoutInflater.inflate(R.layout.item_mail_layout, null);
				holder = new Holder();
				holder.titleTv = (TextView) convertView.findViewById(R.id.item_text);
				holder.iconIv = (ImageView) convertView.findViewById(R.id.item_icon);
				convertView.setTag(holder);
				holder.iconIv.setOnClickListener(this);
			}
			holder = (Holder) convertView.getTag();
			attachHolder(holder, position,mall);
			return convertView;
		}
		
		private void attachHolder(Holder holder,int position,Mall mall){
			holder.titleTv.setText(mall.getCatName());
			ImageLoader.getInstance().displayImage(mall.getFile(), holder.iconIv);
			holder.iconIv.setTag(position);
		}

		@Override
		public void onClick(View v) {
			Mall mall = mData.get((Integer)v.getTag());
			String catId = mall.getId();
			GoodsListActivity.startActivity(getActivity(),catId,mall.getCatName());
		}
		
		private class Holder{
			private ImageView iconIv;
			private TextView titleTv;
		}
		
	}
}
