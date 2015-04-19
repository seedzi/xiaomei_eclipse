package com.xiaomei.levelone;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaomei.R;
import com.xiaomei.bean.User;
import com.xiaomei.levelone.control.UserControl;
import com.xiaomei.module.user.center.AboutActivity;
import com.xiaomei.module.user.center.CollectionActivity;
import com.xiaomei.module.user.center.FeedbackActivity;
import com.xiaomei.module.user.center.HistoryActivity;
import com.xiaomei.module.user.center.MessageActivity;
import com.xiaomei.module.user.center.UserInfoActivity;
import com.xiaomei.module.user.center.UserOrderListActivity;
import com.xiaomei.util.UserUtil;
import com.xiaomei.widget.TitleBar;
import com.yuekuapp.BaseFragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("NewApi")
public class UserFragment extends BaseFragment<UserControl> implements View.OnClickListener{
	
	private ViewGroup mRootView;
	
	private TitleBar mTitleBar;
	
	private ViewGroup line1,line2,line3,line4,line5,line6;
	
	private ImageView mUserIconIv;
	
	private TextView mUserNameTv;
	
	private TextView mUserGradeTv;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mRootView = (ViewGroup) inflater.inflate(R.layout.fragment_user_center_layout, null);
		setUpView();
		return mRootView;
	}

	@Override
	public void onResume() {
	    super.onResume();
	    initData();
	}
	
	private void setUpView(){
		mRootView.findViewById(R.id.back).setVisibility(View.GONE);
		mRootView.findViewById(R.id.user_icon);
		mTitleBar = (TitleBar) mRootView.findViewById(R.id.titlebar);
		mTitleBar.setTitle(getResources().getString(R.string.user_center));
		
		line1 = (ViewGroup) mRootView.findViewById(R.id.line1);
		setUpUserItem(line1, getResources().getString(R.string.user_order), this,R.drawable.order);
		line2 = (ViewGroup) mRootView.findViewById(R.id.line2);
		setUpUserItem(line2,  getResources().getString(R.string.user_message), this,R.drawable.news);
		line3 = (ViewGroup) mRootView.findViewById(R.id.line3);
		setUpUserItem(line3, getResources().getString(R.string.user_collection), this,R.drawable.collection);
		line4 = (ViewGroup) mRootView.findViewById(R.id.line4);
		setUpUserItem(line4, getResources().getString(R.string.user_history), this,R.drawable.browsing_history);
		line5 = (ViewGroup) mRootView.findViewById(R.id.line5);
		setUpUserItem(line5, getResources().getString(R.string.user_feedback), this,R.drawable.feedback);
		line6 = (ViewGroup) mRootView.findViewById(R.id.line6);
		setUpUserItem(line6, getResources().getString(R.string.user_about), this,R.drawable.about_us);
		
		
		mUserIconIv = (ImageView) mRootView.findViewById(R.id.user_icon);
		mUserNameTv = (TextView) mRootView.findViewById(R.id.user_name);
		mUserGradeTv = (TextView) mRootView.findViewById(R.id.user_grade);
		
		mRootView.findViewById(R.id.user_info_layout).setOnClickListener(this);
	}
	
	private void initData(){
	    try {
	        User.UserInfo userInfo = UserUtil.getUser().getUserInfo();
	        if(!TextUtils.isEmpty(userInfo.getAvatar()))
	            ImageLoader.getInstance().displayImage(userInfo.getAvatar(), mUserIconIv);
	        mUserNameTv.setText(userInfo.getUsername());   
        } catch (Exception e) {
        }
	}
	
	private void setUpUserItem(ViewGroup rootView,String title,View.OnClickListener clickListener,int drawableRes){
		ImageView iv = (ImageView) rootView.findViewById(R.id.icon);
		iv.setImageResource(drawableRes);
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
			UserOrderListActivity.startActivity(getActivity());
			break;
		case R.id.line2: //我的消息
			MessageActivity.startActivity(getActivity());
			break;
		case R.id.line3: //我的收藏
			CollectionActivity.startActivity(getActivity());
			break;
		case R.id.line4: //浏览历史
			HistoryActivity.startActivity(getActivity());
			break;
		case R.id.line5: //意见反馈
			FeedbackActivity.startActivity(getActivity());
			break;
		case R.id.line6: //关于我们
			AboutActivity.startActivity(getActivity());
			break;
		case R.id.user_info_layout:
			UserInfoActivity.startActivity(getActivity());
			break;
		default:
			break;
		}
	}
	
	

}
