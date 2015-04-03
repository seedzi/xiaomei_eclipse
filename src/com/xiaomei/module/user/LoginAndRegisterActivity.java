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
import android.widget.Toast;

import com.xiaomei.BaseActiviy;
import com.xiaomei.R;
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
				mGetVerificationButton.setText(String.valueOf(msg.arg2));
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
    
	private TitleBar mTitleBar;
	
	private View mLaunchButton;
	
	private ViewGroup mLoginInputLayout, mRegisterInputLayout;
	
	private View forgetPassword;
	
	private Button mGetVerificationButton;
	/**注册手机号输入*/
	private EditText mRegisterUserMobileEdit;
	/**验证码输入*/
	private EditText mRegisterUserVerificationEdit;
	/**注册手机号输入*/
	private EditText mRegisterUserPasswordEdit;
	
//	private View qqButton, weixinButton, weiboButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_register_layout);
		new Handler().post(new Runnable() {
			@Override
			public void run() {
				
				initView();
			}
		});
	}

	private void initView(){
		
//        DisplayMetrics metric = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(metric);
//        int width = metric.widthPixels;  // 屏幕宽度（像素）
//        int height = metric.heightPixels;  // 屏幕高度（像素）
//        float density = metric.density;  // 屏幕密度（0.75 / 1.0 / 1.5）
//        int densityDpi = metric.densityDpi;  // 屏幕密度DPI（120 / 160 / 240）
//        Log.d("111", "density = " + density);
		
		mTitleBar = (TitleBar) findViewById(R.id.title_bar_layout);
		mTitleBar.setBackListener(LoginAndRegisterActivity.this);
		mTitleBar.setListener(LoginAndRegisterActivity.this);
		
		
		mLoginInputLayout = (ViewGroup) findViewById(R.id.login_input_layout);
		mRegisterInputLayout = (ViewGroup) findViewById(R.id.register_input_layout);
		mLaunchButton = findViewById(R.id.launch);
		mLaunchButton.setOnClickListener(this);
		
		mGetVerificationButton = (Button) findViewById(R.id.get_verification);
		mGetVerificationButton.setOnClickListener(this);
		
		mRegisterUserMobileEdit = (EditText) findViewById(R.id.register_user_mobile);
		mRegisterUserVerificationEdit = (EditText) findViewById(R.id.register_user_verification);
		mRegisterUserVerificationEdit = (EditText) findViewById(R.id.register_user_password);
		
		forgetPassword = findViewById(R.id.forget_password);
		mTitleBar.findViewById(R.id.login).performClick();
		
//		qqButton = findViewById(R.id.qq);
//		qqButton.setOnClickListener(this);
//		weixinButton = findViewById(R.id.weixin);
//		weixinButton.setOnClickListener(this);
//		weiboButton = findViewById(R.id.sina_weibo);
//		weiboButton.setOnClickListener(this);
	}

	@Override
	public void switchLogin() {
		mLoginInputLayout.setVisibility(View.VISIBLE);
		mRegisterInputLayout.setVisibility(View.GONE);
		forgetPassword.setVisibility(View.VISIBLE);
	}

	@Override
	public void switchRegister() {
		mLoginInputLayout.setVisibility(View.GONE);
		mRegisterInputLayout.setVisibility(View.VISIBLE);
		forgetPassword.setVisibility(View.GONE);
	}

	@Override
	public void onClick(View v) {	
		int id = v.getId();
		switch (id) {
		case R.id.launch:
			clearMessge();
			if(!checkInputData()){
				Toast.makeText(LoginAndRegisterActivity.this, "请输入正确的数据", 0).show();
				return;
			}
			
			mControl.register(mRegisterUserMobileEdit.getText().toString(),
					mRegisterUserPasswordEdit.getText().toString(),
					mRegisterUserVerificationEdit.getEditableText().toString());
			break;
		case	R.id.get_verification:
			getVerification(mRegisterUserMobileEdit.getText().toString());
			break;
//		case R.id.qq:
//			break;
//		case R.id.weixin:
//			break;
//		case R.id.sina_weibo:
//			break;
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
	
	private boolean checkInputData(){
		if(!MobileUtil.isMobileNO(mRegisterUserMobileEdit.getText().toString()))
			return false;
		if(TextUtils.isEmpty(mRegisterUserPasswordEdit.getText().toString()) || TextUtils.isEmpty(mRegisterUserVerificationEdit.getText().toString()) )
			return false;
		return true;
		
	}
	
	// ===============================  Call Back =======================================
	
	public void getVerificationCodeAsynExceptionCallBack(){
		
	}
	public void getVerificationCodeAsynCallBack(){
		
	}
	
	public void registerCallBack(){
		Toast.makeText(LoginAndRegisterActivity.this, "注册成功", 0).show();
	}
	public void registerExceptionCallBack(){
		Toast.makeText(LoginAndRegisterActivity.this, "注册失败", 0).show();
	}
	
	
	
}
