package com.xiaomei.yanyu.module.user;


import java.util.Map;
import java.util.Set;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.UMAuthListener;
import com.umeng.socialize.controller.listener.SocializeListeners.UMDataListener;
import com.umeng.socialize.exception.SocializeException;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.xiaomei.yanyu.AbstractActivity;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.XiaoMeiApplication;
import com.xiaomei.yanyu.bean.User;
import com.xiaomei.yanyu.module.user.control.UserControl;
import com.xiaomei.yanyu.util.InputUtils;
import com.xiaomei.yanyu.util.MobileUtil;
import com.xiaomei.yanyu.util.UiUtil;
import com.xiaomei.yanyu.util.UserUtil;
import com.xiaomei.yanyu.util.YanYuUtils;
import com.xiaomei.yanyu.widget.TitleBar;
import com.xiaomei.yanyu.widget.TitleBar.Listener;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class LoginAndRegisterActivity extends AbstractActivity<UserControl>
		implements Listener,View.OnClickListener{
	
	private boolean isFromRegister = false;
	@Deprecated
    public static void startActivity(Context context){
        Intent intent = new Intent(context,LoginAndRegisterActivity.class);
        context.startActivity(intent);
    }
    
    public static void startActivity(Activity ac,boolean isLogin){
        Intent intent = new Intent(ac,LoginAndRegisterActivity.class);
        intent.putExtra("is_login", isLogin);
        ac.startActivity(intent);
        UiUtil.overridePendingTransition(ac);
    }
    
    private final int REFRESH_TIEM = 1;
	private Handler mHandler = new Handler(){
		@Override
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
			showProgressDialog("注册中...");
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
			showProgressDialog("登录中...");
			mControl.loginAsyn(mLoginUserMobileEdit.getText().toString(),
					mLoginUserPasswordEdit.getText().toString());
		}
	};
	private LaunchListener mLaunchListener =  LAUNCH_LOGIN;
	
	
	
	private TitleBar mTitleBar;
	
	private TextView mLaunchButton;
	
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
	
	// ====================  sns 分享   ========================
	private ImageView mQqLogin;
	private ImageView mWeixinLogin;
	private ImageView mSinaLogin;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_register_layout);
		initView();
		initSns();
	}

	private void initView(){
		
		mTitleBar = (TitleBar) findViewById(R.id.title_bar_layout);
		mTitleBar.setListener(LoginAndRegisterActivity.this);
		
		mLoginInputLayout = (ViewGroup) findViewById(R.id.login_input_layout);
		mRegisterInputLayout = (ViewGroup) findViewById(R.id.register_input_layout);
		mLaunchButton = (TextView) findViewById(R.id.launch);
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
		if(getIntent().getBooleanExtra("is_login", true))
			mTitleBar.findViewById(R.id.login).performClick();
		else 
			mTitleBar.findViewById(R.id.register).performClick();
		
		mTitleBar.setBackListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	@Override
	public void switchLogin() {
		mLoginInputLayout.setVisibility(View.VISIBLE);
		mRegisterInputLayout.setVisibility(View.GONE);
		forgetPassword.setVisibility(View.VISIBLE);
		mLaunchListener  = LAUNCH_LOGIN;
		mLaunchButton.setText("登录");
	}

	@Override
	public void switchRegister() {
		mLoginInputLayout.setVisibility(View.GONE);
		mRegisterInputLayout.setVisibility(View.VISIBLE);
		forgetPassword.setVisibility(View.GONE);
		mLaunchListener  = LAUNCH_REGISTER;
		mLaunchButton.setText("注册");
	}

	@Override
	public void onClick(View v) {	
		int id = v.getId();
		switch (id) {
		case R.id.forget_password:
			FindPasswordActivity.startActivity(LoginAndRegisterActivity.this);
			break;
		case R.id.launch:
			InputUtils.hidSoftInput(LoginAndRegisterActivity.this);//强制关闭软键盘
			mLaunchListener.onLaunch();
			break;
		case	R.id.get_verification:
			getVerification(mRegisterUserMobileEdit.getText().toString());
			break;
		case R.id.qq_sns:	
		case R.id.login_qq:
			loginQq(this);
			break;
		case R.id.login_weixin:
			loginWeixin(this);
			break;
		case R.id.login_sina:
			loginSina(this);
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
		if(!YanYuUtils.isConnect(this)){
			Toast.makeText(LoginAndRegisterActivity.this, "网络异常", 0).show();
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
//		dismissDialog();
//		Toast.makeText(LoginAndRegisterActivity.this, "注册成功", 0).show();
		mControl.loginAsyn(mRegisterUserMobileEdit.getText().toString(),
				mRegisterUserPasswordEdit.getText().toString());
		isFromRegister = true;
	}
	public void registeAsynExceptionCallBack(){
		dismissDialog();
		String msg = mControl.getModel().getRegisterFailureMsg();
		if(TextUtils.isEmpty(msg)){
			msg = "注册失败";
		}
		Toast.makeText(LoginAndRegisterActivity.this, msg, 0).show();
	}
	
	public void loginAsynCallBack(){
		dismissDialog();
		if(isFromRegister){
			Toast.makeText(LoginAndRegisterActivity.this, "注册成功", 0).show();
			isFromRegister = false;
		}else{
			Toast.makeText(LoginAndRegisterActivity.this, "登录成功", 0).show();
		}
//		TabsActivity.startActivity(LoginAndRegisterActivity.this);
		setResult(RESULT_OK);
		finish();
	}
	
	public void loginAsynExceptionCallBack(){
		dismissDialog();
		if(isFromRegister){
			Toast.makeText(LoginAndRegisterActivity.this, "注册失败", 0).show();
			isFromRegister = false;
		}else{
			Toast.makeText(LoginAndRegisterActivity.this, "登录失败", 0).show();
		}
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
	
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		if(arg0==1 && arg1== RESULT_OK){
//			TabsActivity.startActivity(LoginAndRegisterActivity.this);
			setResult(RESULT_OK);
			finish();
		}
		UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(arg0);
	    if(ssoHandler != null){
	       ssoHandler.authorizeCallBack(arg0, arg1, arg2);
	    }
	}
	// ===============================  Sns qq登录=======================================
	private void initSns(){
		  //参数1为当前Activity， 参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
		UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(LoginAndRegisterActivity.this, "1104506536",
	                    "mHZPDXRIOLTUjVji");
	    qqSsoHandler.addToSocialSDK();
	    
		// 添加微信平台
		UMWXHandler wxHandler = new UMWXHandler(LoginAndRegisterActivity.this,"wx67f54f6d2c0d66c8","912a0d27dd139295d96cd4b63977d22c");
		wxHandler.addToSocialSDK();
	    
	    mQqLogin = (ImageView) findViewById(R.id.login_qq);
	    mWeixinLogin = (ImageView) findViewById(R.id.login_weixin);
	    mSinaLogin = (ImageView) findViewById(R.id.login_sina); 
	    mQqLogin.setOnClickListener(this);
	    mWeixinLogin.setOnClickListener(this);
	    mSinaLogin.setOnClickListener(this);
	    
	    findViewById(R.id.qq_sns).setOnClickListener(this);
	}
	
	UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.login");
	private void loginQq(final Context mContext){
		mController.doOauthVerify(mContext, SHARE_MEDIA.QQ, new UMAuthListener() {
		    @Override
		    public void onStart(SHARE_MEDIA platform) {
		        Toast.makeText(mContext, "授权开始", Toast.LENGTH_SHORT).show();
		    }
		    @Override
		    public void onError(SocializeException e, SHARE_MEDIA platform) {
		        Toast.makeText(mContext, "授权错误", Toast.LENGTH_SHORT).show();
		    }
		    @Override
		    public void onComplete(Bundle value, SHARE_MEDIA platform) {
		        Toast.makeText(mContext, "授权完成", Toast.LENGTH_SHORT).show();
		        openid = value.getString("openid");
		        access_token = value.getString("access_token");
		        //获取相关授权信息
		        mController.getPlatformInfo(LoginAndRegisterActivity.this, SHARE_MEDIA.QQ, new UMDataListener() {
				    @Override
				    public void onStart() {
				        Toast.makeText(LoginAndRegisterActivity.this, "获取平台数据开始...", Toast.LENGTH_SHORT).show();
				    }                                              
				    @Override
			        public void onComplete(int status, Map<String, Object> info) {
						if (status == 200 && info != null) {
							username = (String) info.get("screen_name");
							avatar = (String) info.get("profile_image_url");
							
							StringBuilder sb = new StringBuilder();
							Set<String> keys = info.keySet();
							for (String key : keys) {
								sb.append(key
										+ "="
										+ info.get(key)
												.toString()
										+ "\r\n");
							}
							Log.d("TestData", sb.toString());
							new Thread(new Runnable() {
								@Override
								public void run() {
									try {
										User user = XiaoMeiApplication.getInstance().getApi().thirdLogin(openid, "qq", access_token, username, avatar, sex);
										if(user != null && UserUtil.isUserValid(user)){
											User.save(user);
//											TabsActivity.startActivity(LoginAndRegisterActivity.this);
											finish();
										}else{
											mHandler.post(new Runnable() {
												@Override
												public void run() {
											        Toast.makeText(mContext, "网络异常", Toast.LENGTH_SHORT).show();
												}
											});
										}
									} catch (Exception e) {
										mHandler.post(new Runnable() {
											@Override
											public void run() {
										        Toast.makeText(mContext, "网络异常", Toast.LENGTH_SHORT).show();
											}
										});
										e.printStackTrace();
									}
								}
							}).start();
						} else {
							Log.d("TestData", "发生错误：" + status);
						}
			        }
				});
		    }
		    @Override
		    public void onCancel(SHARE_MEDIA platform) {
		        Toast.makeText(mContext, "授权取消", Toast.LENGTH_SHORT).show();
		    }
		} );
		
	}
	// ===============================  Sns  微信登录 =======================================	
	private void loginWeixin(final Context mContext){
		mController.doOauthVerify(mContext, SHARE_MEDIA.WEIXIN, new UMAuthListener() {
		    @Override
		    public void onStart(SHARE_MEDIA platform) {
		        Toast.makeText(mContext, "授权开始", Toast.LENGTH_SHORT).show();
		    }
		    @Override
		    public void onError(SocializeException e, SHARE_MEDIA platform) {
		        Toast.makeText(mContext, "授权错误", Toast.LENGTH_SHORT).show();
		    }
		    @Override
		    public void onComplete(Bundle value, SHARE_MEDIA platform) {
		        Toast.makeText(mContext, "授权完成", Toast.LENGTH_SHORT).show();
                openid = value.getString("openid");
                access_token = value.getString("access_token");
		        //获取相关授权信息
		        mController.getPlatformInfo(LoginAndRegisterActivity.this, SHARE_MEDIA.WEIXIN, new UMDataListener() {
		    @Override
		    public void onStart() {
		        Toast.makeText(LoginAndRegisterActivity.this, "获取平台数据开始...", Toast.LENGTH_SHORT).show();
		    }                                              
		    @Override
		        public void onComplete(int status, Map<String, Object> info) {
		            if(status == 200 && info != null){
		                StringBuilder sb = new StringBuilder();
		                Set<String> keys = info.keySet();
		                for(String key : keys){
		                   sb.append(key+"="+info.get(key).toString()+"\r\n");
		                   if("nickname".equals(key.trim()))
		                       username = info.get(key).toString();
		                   else if("headimgurl".equals(key.trim()))
		                       avatar = info.get(key).toString();
		                }
		                
		                new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    User user = XiaoMeiApplication.getInstance().getApi().thirdLogin(openid, "weixin", access_token, username, avatar, sex);
                                    if(user != null && UserUtil.isUserValid(user)){
                                        User.save(user);
//                                        TabsActivity.startActivity(LoginAndRegisterActivity.this);
                                        finish();
                                    }else{
                                        mHandler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(mContext, "网络异常", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                } catch (Exception e) {
                                    mHandler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(mContext, "网络异常", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    e.printStackTrace();
                                }
                            }
                        }).start();
		                
		            }else{
		               Log.d("TestData","发生错误："+status);
		           }
		        }
		});
		    }
		    @Override
		    public void onCancel(SHARE_MEDIA platform) {
		        Toast.makeText(mContext, "授权取消", Toast.LENGTH_SHORT).show();
		    }
		} );
	}
	
	/**第三方登录数据*/
	private String openid;
	private String access_token;
	private String username;
	private String avatar;
	private String sex;
	// ===============================  Sns  微博登录 =======================================	
    private void loginSina(final Context mContext) {
		mController.getConfig().setSsoHandler(new SinaSsoHandler());
        mController.doOauthVerify(mContext, SHARE_MEDIA.SINA, new UMAuthListener() {

            @Override
            public void onStart(SHARE_MEDIA platform) {
		        Toast.makeText(mContext, "授权开始", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(SocializeException e, SHARE_MEDIA platform) {
		        Toast.makeText(mContext, "授权错误", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onComplete(Bundle value, SHARE_MEDIA platform) {
		        Toast.makeText(mContext, "授权完成", Toast.LENGTH_SHORT).show();
                String uid = value.getString("uid");
                openid = value.getString("openid");
                access_token = value.getString("access_token");
                if (!TextUtils.isEmpty(uid)) {
                	mController.getPlatformInfo(LoginAndRegisterActivity.this, SHARE_MEDIA.SINA, new UMDataListener() {
						@Override
						public void onComplete(int status,
								Map<String, Object> info) {
							  if(status == 200 && info != null){
					                StringBuilder sb = new StringBuilder();
					                Set<String> keys = info.keySet();
					                for(String key : keys){
					                   if("screen_name".equals(key.trim()))
					                       username = info.get(key).toString();
					                   else if("profile_image_url".equals(key.trim()))
					                       avatar = info.get(key).toString();
					                }
					                new Thread(new Runnable() {
			                            @Override
			                            public void run() {
			                                try {
			                                    User user = XiaoMeiApplication.getInstance().getApi().thirdLogin(openid, "weibo", access_token, username, avatar, sex);
			                                    if(user != null && UserUtil.isUserValid(user)){
			                                        User.save(user);
			                                        finish();
			                                    }else{
			                                        mHandler.post(new Runnable() {
			                                            @Override
			                                            public void run() {
			                                                Toast.makeText(mContext, "网络异常", Toast.LENGTH_SHORT).show();
			                                            }
			                                        });
			                                    }
			                                } catch (Exception e) {
			                                    mHandler.post(new Runnable() {
			                                        @Override
			                                        public void run() {
			                                            Toast.makeText(mContext, "网络异常", Toast.LENGTH_SHORT).show();
			                                        }
			                                    });
			                                    e.printStackTrace();
			                                }
			                            }
			                        }).start();
					                
					            }else{
					               Log.d("TestData","发生错误："+status);
					           }
						}
						@Override
						public void onStart() {
						}
                	});
                } else {
                    Toast.makeText(mContext, "授权失败...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancel(SHARE_MEDIA platform) {
            }
        });
    }

}
