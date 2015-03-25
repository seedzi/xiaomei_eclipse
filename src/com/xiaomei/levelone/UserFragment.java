package com.xiaomei.levelone;

import com.xiaomei.R;
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
public class UserFragment extends BaseFragment {
	
	private ViewGroup mRootView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mRootView = (ViewGroup) inflater.inflate(R.layout.fragment_user_center_layout, null);
		return mRootView;
	}
	
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		new Handler().post(new Runnable() {
			@Override
			public void run() {
				setUpView();
			}
		});
	}



	private void setUpView(){
		mRootView.findViewById(R.id.back).setVisibility(View.GONE);
		mRootView.findViewById(R.id.user_icon);
	}
	
	

}
