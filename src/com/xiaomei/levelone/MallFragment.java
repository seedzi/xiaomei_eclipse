package com.xiaomei.levelone;


import com.xiaomei.R;
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

@SuppressLint("NewApi")
public class MallFragment extends BaseFragment {

	
	private ViewGroup mRootView;
	
	private GridView mGridView;
	
	private MailAdapter mMailAdapter;
	
	private TitleBar mTitleBar;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mRootView = (ViewGroup) inflater.inflate(R.layout.fragment_mail_layout, null);
		setUpView();
		return mRootView;
	}
	
	private void setUpView(){
		mTitleBar = (TitleBar) mRootView.findViewById(R.id.titlebar);
		mTitleBar.setTitle(getResources().getString(R.string.fragment_mall));
		
		mGridView = (GridView) mRootView.findViewById(R.id.grid);
		mMailAdapter = new MailAdapter(getActivity());
		mGridView.setAdapter(mMailAdapter);
	}
	
	private class MailAdapter extends BaseAdapter implements View.OnClickListener{
		
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
			if(convertView == null){
				convertView = mLayoutInflater.inflate(R.layout.item_mail_layout, null);
				convertView.setOnClickListener(this);
			}
			return convertView;
		}

		@Override
		public void onClick(View v) {
			
		}
		
	}
}
