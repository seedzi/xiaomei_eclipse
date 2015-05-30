package com.xiaomei.yanyu;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.xiaomei.yanyu.module.user.center.OrderDetailsActivity;
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
        
        MobclickAgent.setDebugMode(true);
        MobclickAgent.openActivityDurationTrack(false);
        MobclickAgent.updateOnlineConfig(this);
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
    
	// ===========================  Toast ====================================
    private Toast mToast;
    private final int AIRPLAY_TOAST_DISPLAY_TIME = 1;
    
    public void showToast(String txt){
    	if(mToast == null){
    		mToast = Toast.makeText(this, txt, 0);
    	}else{
    		mToast.setText(txt);
    		mToast.setDuration(0);
    	}
    	mToast.show();
    	Message msg = mToastHandler.obtainMessage(AIRPLAY_TOAST_DISPLAY_TIME);
    	mToastHandler.sendMessageDelayed(msg, 1000);
    }
    
    private void cancelToat(){
    	if(mToast!=null)
    		mToast.cancel();
    }
    private Handler mToastHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				
				break;
			case AIRPLAY_TOAST_DISPLAY_TIME:
				cancelToat();
				break;
			default:
				break;
			}
		}
    };
    
	// ===========================  Toast ====================================
    
}
