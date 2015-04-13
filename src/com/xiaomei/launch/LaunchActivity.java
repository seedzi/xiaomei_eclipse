package com.xiaomei.launch;


import android.os.Bundle;
import android.os.Handler;

import com.xiaomei.BaseActiviy;
import com.xiaomei.R;
import com.xiaomei.XiaoMeiApplication;
import com.xiaomei.Payment.ZhifubaoPayManager;
import com.xiaomei.api.exception.XiaoMeiCredentialsException;
import com.xiaomei.api.exception.XiaoMeiIOException;
import com.xiaomei.api.exception.XiaoMeiJSONException;
import com.xiaomei.api.exception.XiaoMeiOtherException;
import com.xiaomei.contanier.TabsActivity;
import com.xiaomei.launch.control.LaunchControl;
import com.xiaomei.module.user.LoginAndRegisterActivity;
import com.xiaomei.test.PhoneGapAc;
import com.xiaomei.util.UserUtil;

public class LaunchActivity extends  BaseActiviy<LaunchControl>{

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
    	
//    	LoginAndRegisterActivity.startActivity(LaunchActivity.this);
//    	TabsActivity.startActivity(LaunchActivity.this);
    	/*
    	 * */
    	if(UserUtil.isUserValid()){
    		TabsActivity.startActivity(LaunchActivity.this);
    	}else{
    		LoginAndRegisterActivity.startActivity(LaunchActivity.this);
    	}
    	
    	
//    	PhoneGapAc.startActivity(LaunchActivity.this);
    	
//    	finish();	
    	
//    	ZhifubaoPayManager.getInstance().setCurrentActivity(this);
//    	ZhifubaoPayManager.getInstance().pay();
    }

}
