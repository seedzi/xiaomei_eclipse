package com.xiaomei.levelone;

import com.xiaomei.R;
import com.yuekuapp.BaseFragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

@SuppressLint("NewApi")
public class UserFragment extends BaseFragment {
	
	private ViewGroup mRootView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mRootView = (ViewGroup) inflater.inflate(R.layout.fragment_user_layout, null);
		
		return mRootView;
	}
	
	

}
