package com.xiaomei.levelone;

import com.xiaomei.R;
import com.xiaomei.module.user.center.AboutActivity;
import com.xiaomei.module.user.center.HistoryActivity;
import com.xiaomei.module.user.center.UserOrderActivity;
import com.xiaomei.widget.TitleBar;
import com.yuekuapp.BaseFragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

@SuppressLint("NewApi")
public class UserFragment extends BaseFragment implements View.OnClickListener{
	
	private ViewGroup mRootView;
	
	private TitleBar mTitleBar;
	
	private ViewGroup line1,line2,line3,line4,line5,line6;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mRootView = (ViewGroup) inflater.inflate(R.layout.fragment_user_center_layout, null);
		setUpView();
		return mRootView;
	}

	private void setUpView(){
		mRootView.findViewById(R.id.back).setVisibility(View.GONE);
		mRootView.findViewById(R.id.user_icon);
		mTitleBar = (TitleBar) mRootView.findViewById(R.id.titlebar);
		mTitleBar.setTitle(getResources().getString(R.string.user_center));
		
		line1 = (ViewGroup) mRootView.findViewById(R.id.line1);
		setUpUserItem(line1, getResources().getString(R.string.user_order), this);
		line2 = (ViewGroup) mRootView.findViewById(R.id.line2);
		setUpUserItem(line2,  getResources().getString(R.string.user_message), this);
		line3 = (ViewGroup) mRootView.findViewById(R.id.line3);
		setUpUserItem(line3, getResources().getString(R.string.user_collection), this);
		line4 = (ViewGroup) mRootView.findViewById(R.id.line4);
		setUpUserItem(line4, getResources().getString(R.string.user_history), this);
		line5 = (ViewGroup) mRootView.findViewById(R.id.line5);
		setUpUserItem(line5, getResources().getString(R.string.user_feedback), this);
		line6 = (ViewGroup) mRootView.findViewById(R.id.line6);
		setUpUserItem(line6, getResources().getString(R.string.user_about), this);
	}
	
	private void setUpUserItem(ViewGroup rootView,String title,View.OnClickListener clickListener){
		TextView tv = (TextView) rootView.findViewById(R.id.title);
		tv.setText(title);
		rootView.setOnClickListener(clickListener);
		rootView.findViewById(R.id.new_version_prompt).setVisibility(View.GONE);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.line1:  //我的订单
			UserOrderActivity.startActivity(getActivity());
			break;
		case R.id.line2: //我的消息
			
			break;
		case R.id.line3: //我的收藏
			
			break;
		case R.id.line4: //浏览历史
			HistoryActivity.startActivity(getActivity());
			break;
		case R.id.line5: //意见反馈
			
			break;
		case R.id.line6: //关于我们
			AboutActivity.startActivity(getActivity());
			break;
		default:
			break;
		}
	}
	
	

}
