package com.xiaomei.yanyu.module.user.center;

import com.xiaomei.yanyu.AbstractActivity;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.module.user.control.UserControl;
import com.xiaomei.yanyu.util.MobileUtil;
import com.xiaomei.yanyu.util.UiUtil;
import com.xiaomei.yanyu.widget.TitleBar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class FeedbackActivity extends AbstractActivity<UserControl> implements View.OnClickListener{
	
	
	public static void startActivity(Activity ac){
		Intent intent = new Intent(ac,FeedbackActivity.class);
		ac.startActivity(intent);
        UiUtil.overridePendingTransition(ac);
	}
	
	private TitleBar mTitleBar;
	private TextView mTxtContent;
	private TextView mMobileTv;
	private View mSubmit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback_layout);
		setUpView();
		mSubmit = findViewById(R.id.submit);
		mSubmit.setOnClickListener(this);
		mTxtContent = (TextView) findViewById(R.id.txt_content);
		mMobileTv = (TextView) findViewById(R.id.mobile);
	}
	
	private void setUpView(){
		mTitleBar = (TitleBar) findViewById(R.id.titlebar);
		mTitleBar.setTitle(getResources().getString(R.string.user_feedback));
		mTitleBar.setBackListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Override
	public void onClick(View v) {
		if(TextUtils.isEmpty(mTxtContent.getText()) /*|| TextUtils.isEmpty(mMobileTv.getText())*/){
			Toast.makeText(this, "反馈内容不能为空", 0).show();
			return;
		}
		if(TextUtils.isEmpty(mMobileTv.getText().toString())||!MobileUtil.isMobileNO(mMobileTv.getText().toString())){
	          Toast.makeText(this, "请输入正确的手机号码", 0).show();
		}
		mSubmit.setEnabled(false);
		mControl.feedback(mTxtContent.getText().toString(),mMobileTv.getText().toString());
	}
	
	// ====================================  CallBack =============================================
	public void feedbackAsynCallBack(){
		Toast.makeText(this, "反馈成功", 0).show();
		mSubmit.setEnabled(true);
	}
	
	public void feedbackExceptionCallBack(){
		Toast.makeText(this, "反馈失败", 0).show();
		mSubmit.setEnabled(true);
	}

}
