package com.xiaomei.levelone;


import com.xiaomei.R;
import com.xiaomei.levelone.control.MallControl;
import com.xiaomei.leveltwo.GoodsListActivity;
import com.xiaomei.leveltwo.MallSecondActivity;
import com.xiaomei.util.ScreenUtils;
import com.xiaomei.widget.TitleBar;
import com.yuekuapp.BaseFragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("NewApi")
public class MallFragment extends BaseFragment<MallControl> {

	private ViewGroup mRootView;
	
	private GridView mGridView;
	
	private MailAdapter mMailAdapter;
	
	private TitleBar mTitleBar;
	
	private ImageView mTopIcon;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mRootView = (ViewGroup) inflater.inflate(R.layout.fragment_mail_layout, null);
		setUpView();
		initData();
		return mRootView;
	}
	
	private void setUpView(){
		mTitleBar = (TitleBar) mRootView.findViewById(R.id.titlebar);
		mTitleBar.setTitle(getResources().getString(R.string.fragment_mall));
		
		mGridView = (GridView) mRootView.findViewById(R.id.grid);
		mMailAdapter = new MailAdapter(getActivity());
		mGridView.setAdapter(mMailAdapter);
		
		mTopIcon = (ImageView) mRootView.findViewById(R.id.top_icon);
		mTopIcon.getLayoutParams().height = ScreenUtils.getScreenWidth(getActivity())/2;
		mTopIcon.setImageResource(R.drawable.meinv);
	}
	
	private void initData(){
		mControl.getMallListFromNetAsyn();
	}
	
	public void getMallListFromAsynCallBack(){
		
	}
	
	public void getMallListFromAsynExceptionCallBack(){
		
	}
	
	private class MailAdapter extends BaseAdapter implements View.OnClickListener{
		
		private int[] icon_reses = { R.drawable.icon_roudushuchuzou_selector,
				R.drawable.icon_tichongsuxing_selector,
				R.drawable.icon_jinfutisheng_selector,
				R.drawable.icon_roudusumeixing_selector,
				R.drawable.icon_tianchognsuxing_selector,
				R.drawable.icon_jiguangmeifu_selector,
				R.drawable.icon_hanguotesemeirong_selector,
				R.drawable.icon_jiguangchumao_selector,
				R.drawable.icon_tixinagtiaosu_selector };

		private int[] txt_reses = { R.string.mall_item_nam_1,
				R.string.mall_item_nam_2, R.string.mall_item_nam_3,
				R.string.mall_item_nam_4, R.string.mall_item_nam_5,
				R.string.mall_item_nam_6, R.string.mall_item_nam_7,
				R.string.mall_item_nam_8, R.string.mall_item_nam_9 };
		
		private LayoutInflater mLayoutInflater;
		
		public MailAdapter(Context context){
			mLayoutInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return 9;
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
				convertView = mLayoutInflater.inflate(R.layout.item_mail_layout, null);
				holder = new Holder();
				holder.titleTv = (TextView) convertView.findViewById(R.id.item_text);
				holder.iconIv = (ImageView) convertView.findViewById(R.id.item_icon);
				convertView.setTag(holder);
				holder.iconIv.setOnClickListener(this);
			}
			holder = (Holder) convertView.getTag();
			attachHolder(holder, position);
			return convertView;
		}
		
		private void attachHolder(Holder holder,int position){
			holder.titleTv.setText(getResources().getString(txt_reses[position]));
			holder.iconIv.setImageResource(icon_reses[position]);
		}

		@Override
		public void onClick(View v) {
			GoodsListActivity.startActivity(getActivity());
		}
		
		private class Holder{
			private ImageView iconIv;
			private TextView titleTv;
		}
		
	}
}
