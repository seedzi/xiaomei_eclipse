package com.xiaomei.yanyu.levelone;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.bean.User;
import com.xiaomei.yanyu.contanier.TabsActivity;
import com.xiaomei.yanyu.levelone.control.UserControl;
import com.xiaomei.yanyu.module.user.LoginAndRegisterActivity;
import com.xiaomei.yanyu.module.user.center.AboutActivity;
import com.xiaomei.yanyu.module.user.center.CollectionActivity;
import com.xiaomei.yanyu.module.user.center.FeedbackActivity;
import com.xiaomei.yanyu.module.user.center.HistoryActivity;
import com.xiaomei.yanyu.module.user.center.UserInfoActivity;
import com.xiaomei.yanyu.module.user.center.UserMessageActivity;
import com.xiaomei.yanyu.module.user.center.UserOrderListActivity;
import com.xiaomei.yanyu.util.UserUtil;
import com.xiaomei.yanyu.widget.TitleActionBar;
import com.yuekuapp.BaseFragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
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
	
	private ViewGroup mUserOrder,mUserMessage,mUserCollection,mUserHistory,mUserFeedback,mUserAbout,mOurContact;
	
	private ImageView mUserIconIv;
	
	private TextView mUserNameTv;
	
	private TextView mUserGradeTv;
	
	private TextView mLoginButton;
	
	private TextView mRegisterButton;
	
	private View mUserInfoLayout;
	
	private View mNoLoginLayout;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mRootView = (ViewGroup) inflater.inflate(R.layout.fragment_user_center_layout, null);
		setUpView();
	    initData();
		return mRootView;
	}

	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TitleActionBar titleBar = ((TabsActivity) getActivity()).getTitleBar();
        titleBar.setTitle(R.string.fragment_user);
        titleBar.setActionVisibility(View.GONE);
    }

	@Override
	public void onResume() {
	    super.onResume();
	    initData();
	}
	
	private void setUpView(){
		mRootView.findViewById(R.id.user_icon);
		
        mUserOrder = (ViewGroup)mRootView.findViewById(R.id.user_order);
		setUpUserItem(mUserOrder, getResources().getString(R.string.user_order), this,R.drawable.order);
		mUserMessage = (ViewGroup) mRootView.findViewById(R.id.user_message);
		setUpUserItem(mUserMessage,  getResources().getString(R.string.user_message), this,R.drawable.news);
		mUserCollection = (ViewGroup) mRootView.findViewById(R.id.user_collection);
		setUpUserItem(mUserCollection, getResources().getString(R.string.user_collection), this,R.drawable.collection);
		mUserHistory = (ViewGroup) mRootView.findViewById(R.id.user_history);
		setUpUserItem(mUserHistory, getResources().getString(R.string.user_history), this,R.drawable.browsing_history);
		mUserFeedback = (ViewGroup) mRootView.findViewById(R.id.user_feedback);
		setUpUserItem(mUserFeedback, getResources().getString(R.string.user_feedback), this,R.drawable.feedback);
		mUserAbout = (ViewGroup) mRootView.findViewById(R.id.user_about);
		setUpUserItem(mUserAbout, getResources().getString(R.string.user_about), this,R.drawable.about_us);
		mOurContact = (ViewGroup) mRootView.findViewById(R.id.our_contact);
		setUpUserItem(mOurContact, "400-667-0190", this, R.drawable.ouer_dianhua);
		
		mUserIconIv = (ImageView) mRootView.findViewById(R.id.user_icon);
		mUserNameTv = (TextView) mRootView.findViewById(R.id.user_name);
		mUserGradeTv = (TextView) mRootView.findViewById(R.id.user_grade);
		
		mLoginButton = (TextView) mRootView.findViewById(R.id.login_button);
		mRegisterButton = (TextView) mRootView.findViewById(R.id.register_button);
		mUserInfoLayout = mRootView.findViewById(R.id.user_info_layout);
		mNoLoginLayout = mRootView.findViewById(R.id.no_login_layout);
		
		mLoginButton.setOnClickListener(this);
		mRegisterButton.setOnClickListener(this);
		mUserInfoLayout.setOnClickListener(this);
	}
	
	private void initData(){
		User user = UserUtil.getUser();
		if(user == null){
			mNoLoginLayout.setVisibility(View.VISIBLE);
			mUserInfoLayout.setVisibility(View.GONE);
			return;
		}else{
			mNoLoginLayout.setVisibility(View.GONE);
			mUserInfoLayout.setVisibility(View.VISIBLE);
		}
		
	    try {
	        User.UserInfo userInfo = UserUtil.getUser().getUserInfo();
	        if(!TextUtils.isEmpty(userInfo.getAvatar()))
	            ImageLoader.getInstance().displayImage(userInfo.getAvatar(), mUserIconIv);
	        mUserNameTv.setText(userInfo.getUsername());   
	        switch (Integer.valueOf(userInfo.getUserType())) {
			case 1:
				mUserGradeTv.setText("普通会员");
				break;
			case 2:
				mUserGradeTv.setText("银牌会员");
				break;
			case 3:
				mUserGradeTv.setText("金牌会员");
				break;
			case 4:
				mUserGradeTv.setText("砖石会员");
				break;
			default:
				break;
			}
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
		case R.id.login_button://登录
//	        Intent intent = new Intent(getActivity(),LoginAndRegisterActivity.class);
//	        intent.putExtra("is_login", true);
//	        startActivityForResult(intent, 1);
			LoginAndRegisterActivity.startActivity(getActivity(), true);
			break;
		case R.id.register_button://注册
//	        Intent intent1 = new Intent(getActivity(),LoginAndRegisterActivity.class);
//	        intent1.putExtra("is_login", false);
//	        startActivityForResult(intent1, 1);
			LoginAndRegisterActivity.startActivity(getActivity(), false);
			break;
        case R.id.user_order: // 我的订单
			if(UserUtil.getUser()==null){
				LoginAndRegisterActivity.startActivity(getActivity(), false);
				return;
			}
			UserOrderListActivity.startActivity(getActivity());
			break;
		case R.id.user_message: //我的消息
			if(UserUtil.getUser()==null){
				LoginAndRegisterActivity.startActivity(getActivity(), false);
				return;
			}
		    UserMessageActivity.startActivity(getActivity());
			break;
		case R.id.user_collection: //我的收藏
			if(UserUtil.getUser()==null){
				LoginAndRegisterActivity.startActivity(getActivity(), false);
				return;
			}
			CollectionActivity.startActivity(getActivity());
			break;
		case R.id.user_history: //浏览历史
			HistoryActivity.startActivity(getActivity());
			break;
		case R.id.user_feedback: //意见反馈
            if (UserUtil.getUser() == null) {
                LoginAndRegisterActivity.startActivity(getActivity(), false);
                return;
            }
            FeedbackActivity.startActivity(getActivity());
			break;
		case R.id.user_about: //关于我们
			AboutActivity.startActivity(getActivity());
			break;
		case R.id.our_contact: 
			showProgressDialog();
			break;
		case R.id.user_info_layout:
			UserInfoActivity.startActivity(getActivity());
			break;
		default:
			break;
		}
	}
	
	private void showProgressDialog(){
        AlertDialog.Builder builder = new Builder(getActivity());
        builder.setTitle("400-667-0190");
        builder.setPositiveButton("呼叫", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            	Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:4006670190")); 
            	startActivity(intent);  
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
	}

}
