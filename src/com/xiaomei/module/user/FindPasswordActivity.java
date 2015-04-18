package com.xiaomei.module.user;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaomei.AbstractActivity;
import com.xiaomei.R;
import com.xiaomei.module.user.control.UserControl;
import com.xiaomei.util.InputUtils;
import com.xiaomei.util.MobileUtil;
import com.xiaomei.widget.TitleBar;


public class FindPasswordActivity extends AbstractActivity<UserControl>
	implements View.OnClickListener{
	
	public static void startActivity(Context context){
		Intent intent = new Intent(context,FindPasswordActivity.class);
		context.startActivity(intent);
	}
	
	private TitleBar mTitleBar;
	
    private final int REFRESH_TIEM = 1;
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.arg1) {
			case REFRESH_TIEM:
				if(msg.arg2<=0){
					mGetVerificationButton.setText(getResources().getString(R.string.get_verification_code));
					mGetVerificationButton.setEnabled(true);
					return;
				}
				mGetVerificationButton.setText(String.valueOf(msg.arg2) + "s");
				Message msgNew = Message.obtain();
				msgNew.arg1 = REFRESH_TIEM;
				mGetVerificationButton.setEnabled(false);
				msgNew.arg2 = msg.arg2 -1;
				mHandler.sendMessageDelayed(msgNew,1000);
				break;
			default:
				break;
			}
		};
	};
	
	LaunchListener LAUNCH_REGISTER = new LaunchListener(){
		@Override
		public void onLaunch() {
			clearMessge();
			if(!checkInputData4Registe()){
				Toast.makeText(FindPasswordActivity.this, "请输入正确的数据", 0).show();
				return;
			}
			mControl.findPasswordAsyn(mRegisterUserMobileEdit.getText().toString(),
					mRegisterUserPasswordEdit.getText().toString(),
					mRegisterUserVerificationEdit.getEditableText()
							.toString());
		}
	};
	private LaunchListener mLaunchListener =  LAUNCH_REGISTER;
	
	/**注册手机号输入*/
	private EditText mRegisterUserMobileEdit;
	/**验证码输入*/
	private EditText mRegisterUserVerificationEdit;
	/**注册密码输入*/
	private EditText mRegisterUserPasswordEdit;
	
	private TextView mGetVerificationButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_find_password_layout);
		setUpView();
	}
	
	private void setUpView(){
		mTitleBar = (TitleBar) findViewById(R.id.title_bar_layout);
		mTitleBar.setBackListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		mTitleBar.setTitle("找回密码");
		
		mGetVerificationButton =  (TextView) findViewById(R.id.get_verification);
		mGetVerificationButton.setOnClickListener(this);
		
		mRegisterUserMobileEdit = (EditText) findViewById(R.id.register_user_mobile);
		mRegisterUserVerificationEdit = (EditText) findViewById(R.id.register_user_verification);
		mRegisterUserPasswordEdit = (EditText) findViewById(R.id.register_user_password);
		
		findViewById(R.id.launch).setOnClickListener(this);
	}
	
	private boolean checkInputData4Registe(){
		if(!MobileUtil.isMobileNO(mRegisterUserMobileEdit.getText().toString()))
			return false;
		if(TextUtils.isEmpty(mRegisterUserPasswordEdit.getText().toString()) || TextUtils.isEmpty(mRegisterUserVerificationEdit.getText().toString()) )
			return false;
		return true;
	}
	
	private void clearMessge(){
		mHandler.removeMessages(mHandler.obtainMessage().arg1);
		mGetVerificationButton.setText(getResources().getString(R.string.get_verification_code));
		mGetVerificationButton.setEnabled(true);
	}
	
	@Override
	public void onClick(View v) {	
		int id = v.getId();
		switch (id) {
		case R.id.launch:
			InputUtils.hidSoftInput(FindPasswordActivity.this);
			showProgressDialog("加载...");
			mLaunchListener.onLaunch();
			break;
		case	R.id.get_verification:
			getVerification(mRegisterUserMobileEdit.getText().toString());
			break;
		default:
			break;
		}
	}
	
	
	private void getVerification(String mobile){
		if(!MobileUtil.isMobileNO(mobile)){
			Toast.makeText(FindPasswordActivity.this, "输入的号码格式有误", 0).show();
			return;
		}
		mControl.getVerificationCodeAsyn(mobile);
		Message msg = Message.obtain();
		msg.arg1 = REFRESH_TIEM;
		msg.arg2 = 60;
		mHandler.sendMessage(msg);
	}
	
	
	private  interface LaunchListener{
		public void onLaunch();
	}
	
	// =========================================== ProgressDialog ===================================================
	
	private ProgressDialog mProgressDialog;
	private void showProgressDialog(String message){
		if(mProgressDialog!=null && mProgressDialog.isShowing())
			mProgressDialog.dismiss();
		mProgressDialog = new ProgressDialog(this);
		mProgressDialog.setTitle("提示");
		mProgressDialog.setMessage(message);
		mProgressDialog.setCancelable(false);
		mProgressDialog.show();
	}
	
	private void dismissDialog(){
		if(mProgressDialog!=null && mProgressDialog.isShowing())
			mProgressDialog.dismiss();
	}
	
	// ===============================  Call Back =======================================
	
		public void getVerificationCodeAsynExceptionCallBack(){
			dismissDialog();
		}
		public void getVerificationCodeAsynCallBack(){
			dismissDialog();
		}
		
		public void findPasswordAsynCallBack(){
			dismissDialog();
			Toast.makeText(FindPasswordActivity.this, "找回密码成功", 0).show();
		}
		public void findPasswordAsynExceptionCallBack(){
			dismissDialog();
			Toast.makeText(FindPasswordActivity.this, "找回密码失败", 0).show();
		}

}
