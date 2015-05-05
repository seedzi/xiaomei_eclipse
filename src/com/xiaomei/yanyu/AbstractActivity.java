package com.xiaomei.yanyu;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.umeng.analytics.MobclickAgent;
import com.yuekuapp.BaseActivity;
import com.yuekuapp.BaseControl;

/**
 * Created by huzhi on 15-2-17.
 */
public class AbstractActivity<T extends BaseControl> extends BaseActivity<T>{

    private static List<Activity>  activities;
    
    protected boolean useAnimation = true;
    
    public static synchronized void  clearActivity(){
        if(activities!=null){
            for(Activity ac:activities){
                ac.finish();
            }
            activities.clear();
        }
    }
    
    private Context mContext;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(activities  == null)
		    activities = new ArrayList<Activity>();
		
		activities.add(this);
        XiaoMeiApplication.getInstance().setCurrentActivity(this);
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//				WindowManager.LayoutParams.FLAG_FULLSCREEN);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        mContext = this;
	}
	
	@Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart( this.getClass().getSimpleName() );
        MobclickAgent.onResume(mContext);
    }
    
    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd( this.getClass().getSimpleName() );
        MobclickAgent.onPause(mContext);
    }

    @Override
    public void finish() {
    	super.finish();
    	if(useAnimation)
    		overridePendingTransition(R.anim.activity_slid_out_no_change, R.anim.activity_slid_out_from_left);
    }
    
}
