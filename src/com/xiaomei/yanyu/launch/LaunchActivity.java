package com.xiaomei.yanyu.launch;


import android.os.Bundle;
import android.os.Handler;

import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.AbstractActivity;
import com.xiaomei.yanyu.XiaoMeiApplication;
import com.xiaomei.yanyu.Payment.ZhifubaoPayManager;
import com.xiaomei.yanyu.api.exception.XiaoMeiCredentialsException;
import com.xiaomei.yanyu.api.exception.XiaoMeiIOException;
import com.xiaomei.yanyu.api.exception.XiaoMeiJSONException;
import com.xiaomei.yanyu.api.exception.XiaoMeiOtherException;
import com.xiaomei.yanyu.comment.CommentsActivity;
import com.xiaomei.yanyu.contanier.TabsActivity;
import com.xiaomei.yanyu.launch.control.LaunchControl;
import com.xiaomei.yanyu.module.user.LoginAndRegisterActivity;
import com.xiaomei.yanyu.test.PhoneGapAc;
import com.xiaomei.yanyu.util.UserUtil;

public class LaunchActivity extends  AbstractActivity<LaunchControl>{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        /*
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
//					XiaoMeiApplication.getInstance().getApi().userRegister("huzhi", "123456");
//					XiaoMeiApplication.getInstance().getApi().getVerificationCode("15010768102");
//					XiaoMeiApplication.getInstance().getApi().userRegister("15010768102", "123456" ,"6394");
					XiaoMeiApplication.getInstance().getApi().userLogin("15010768102", "123456");
//					XiaoMeiApplication.getInstance().getApi().findPassword("15010768102", "abcdef", "10422");
				} catch (XiaoMeiCredentialsException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (XiaoMeiIOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (XiaoMeiJSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (XiaoMeiOtherException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();*/
        
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            	init();
            }
        },1000);
    }
    
    private void  init(){
//        CommentsActivity.startActivity(this);
//    	LoginAndRegisterActivity.startActivity(LaunchActivity.this);
//    	TabsActivity.startActivity(LaunchActivity.this);
    	/*
    	 * 
    	 */
  		TabsActivity.startActivity(LaunchActivity.this);
  		/*
    	if(UserUtil.isUserValid()){
    		TabsActivity.startActivity(LaunchActivity.this);
    	}else{
    		LoginAndRegisterActivity.startActivity(LaunchActivity.this);
    	}
    	*/
//    	PhoneGapAc.startActivity(LaunchActivity.this);
    	
//    	finish();	
    	
//    	ZhifubaoPayManager.getInstance().setCurrentActivity(this);
//    	ZhifubaoPayManager.getInstance().pay();
    }

}
