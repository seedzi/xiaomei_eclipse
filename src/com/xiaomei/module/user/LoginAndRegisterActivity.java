package com.xiaomei.module.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaomei.BaseActiviy;
import com.xiaomei.R;
import com.xiaomei.contanier.TabsActivity;
import com.xiaomei.module.user.control.UserControl;
import com.xiaomei.util.MobileUtil;
import com.xiaomei.widget.TitleBar;
import com.xiaomei.widget.TitleBar.Listener;


public class LoginAndRegisterActivity extends BaseActiviy<UserControl>
		implements Listener,View.OnClickListener{
	
    public static void startActivity(Context context){
        Intent intent = new Intent(context,LoginAndRegisterActivity.class);
        context.startActivity(intent);
    }
    
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
				Toast.makeText(LoginAndRegisterActivity.this, "请输入正确的数据", 0).show();
				return;
			}
			mControl.registeAsyn(mRegisterUserMobileEdit.getText().toString(),
					mRegisterUserPasswordEdit.getText().toString(),
					mRegisterUserVerificationEdit.getEditableText()
							.toString());
		}
	};
	LaunchListener LAUNCH_LOGIN = new LaunchListener(){
		@Override
		public void onLaunch() {
			clearMessge();
			if(!checkInputData4Login()){
				Toast.makeText(LoginAndRegisterActivity.this, "请输入正确的数据", 0).show();
				return;
			}
			mControl.loginAsyn(mLoginUserMobileEdit.getText().toString(),
					mLoginUserPasswordEdit.getText().toString());
		}
	};
	private LaunchListener mLaunchListener =  LAUNCH_LOGIN;
	
	
	
	private TitleBar mTitleBar;
	
	private View mLaunchButton;
	
	private ViewGroup mLoginInputLayout, mRegisterInputLayout;
	
	private View forgetPassword;
	
	private TextView mGetVerificationButton;
	/**注册手机号输入*/
	private EditText mRegisterUserMobileEdit;
	/**验证码输入*/
	private EditText mRegisterUserVerificationEdit;
	/**注册密码输入*/
	private EditText mRegisterUserPasswordEdit;
	
	/**注册手机号输入*/
	private EditText mLoginUserMobileEdit;
	/**注册密码号输入*/
	private EditText mLoginUserPasswordEdit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_register_layout);
		initView();
	}

	private void initView(){
		
		mTitleBar = (TitleBar) findViewById(R.id.title_bar_layout);
		mTitleBar.setListener(LoginAndRegisterActivity.this);
		
		mLoginInputLayout = (ViewGroup) findViewById(R.id.login_input_layout);
		mRegisterInputLayout = (ViewGroup) findViewById(R.id.register_input_layout);
		mLaunchButton = findViewById(R.id.launch);
		mLaunchButton.setOnClickListener(this);
		
		mGetVerificationButton =  (TextView) findViewById(R.id.get_verification);
		mGetVerificationButton.setOnClickListener(this);
		
		mLoginUserMobileEdit = (EditText) findViewById(R.id.login_user_mobile);
		mLoginUserPasswordEdit = (EditText) findViewById(R.id.login_user_password);
		
		mRegisterUserMobileEdit = (EditText) findViewById(R.id.register_user_mobile);
		mRegisterUserVerificationEdit = (EditText) findViewById(R.id.register_user_verification);
		mRegisterUserPasswordEdit = (EditText) findViewById(R.id.register_user_password);
		
		forgetPassword = findViewById(R.id.forget_password);
		forgetPassword.setOnClickListener(this);
		mTitleBar.findViewById(R.id.login).performClick();
		
	}
	
	@Override
	public void switchLogin() {
		mLoginInputLayout.setVisibility(View.VISIBLE);
		mRegisterInputLayout.setVisibility(View.GONE);
		forgetPassword.setVisibility(View.VISIBLE);
		mLaunchListener  = LAUNCH_LOGIN;
	}

	@Override
	public void switchRegister() {
		mLoginInputLayout.setVisibility(View.GONE);
		mRegisterInputLayout.setVisibility(View.VISIBLE);
		forgetPassword.setVisibility(View.GONE);
		mLaunchListener  = LAUNCH_REGISTER;
	}

	@Override
	public void onClick(View v) {	
		int id = v.getId();
		switch (id) {
		case R.id.forget_password:
			FindPasswordActivity.startActivity(LoginAndRegisterActivity.this);
			break;
		case R.id.launch:
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
			Toast.makeText(LoginAndRegisterActivity.this, "输入的号码格式有误", 0).show();
			return;
		}
		mControl.getVerificationCodeAsyn(mobile);
		Message msg = Message.obtain();
		msg.arg1 = REFRESH_TIEM;
		msg.arg2 = 60;
		mHandler.sendMessage(msg);
	}
	
	private void clearMessge(){
		mHandler.removeMessages(mHandler.obtainMessage().arg1);
		mGetVerificationButton.setText(getResources().getString(R.string.get_verification_code));
		mGetVerificationButton.setEnabled(true);
	}
	
	private boolean checkInputData4Registe(){
		if(!MobileUtil.isMobileNO(mRegisterUserMobileEdit.getText().toString()))
			return false;
		if(TextUtils.isEmpty(mRegisterUserPasswordEdit.getText().toString()) || TextUtils.isEmpty(mRegisterUserVerificationEdit.getText().toString()) )
			return false;
		return true;
	}
	
	private boolean checkInputData4Login(){
		if(!MobileUtil.isMobileNO(mLoginUserMobileEdit.getText().toString()))
			return false;
		if(TextUtils.isEmpty(mLoginUserMobileEdit.getText().toString()) )
			return false;
		return true;
	}
	
	private  interface LaunchListener{
		public void onLaunch();
	}
	
	// ===============================  Call Back =======================================
	
	public void getVerificationCodeAsynExceptionCallBack(){
		
	}
	public void getVerificationCodeAsynCallBack(){
		
	}
	
	public void registeAsynCallBack(){
		Toast.makeText(LoginAndRegisterActivity.this, "注册成功", 0).show();
	}
	public void registeAsynExceptionCallBack(){
		Toast.makeText(LoginAndRegisterActivity.this, "注册失败", 0).show();
	}
	
	public void loginAsynCallBack(){
		Toast.makeText(LoginAndRegisterActivity.this, "登录成功", 0).show();
		TabsActivity.startActivity(getApplicationContext());
	}
	
	public void loginAsynExceptionCallBack(){
		Toast.makeText(LoginAndRegisterActivity.this, "登录失败", 0).show();
	}
	
	
	
}
