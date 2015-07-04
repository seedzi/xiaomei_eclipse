package com.xiaomei.yanyu.contanier;

import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;
import com.xiaomei.yanyu.AbstractActivity;
import com.xiaomei.yanyu.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by huzhi on 15-2-17.
 */
public class TabsActivity extends  AbstractActivity{


    public static void startActivity(Activity ac){
        Intent intent = new Intent(ac,TabsActivity.class);
        ac.startActivity(intent);
        ac.overridePendingTransition(R.anim.activity_slid_in_from_right, R.anim.activity_slid_out_no_change);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	useAnimation = false;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container_layout);
        
        //  (1)
        // ================== UM统计 =====================
        MobclickAgent.setDebugMode(true);
//      SDK在统计Fragment时，需要关闭Activity自带的页面统计，
//      然后在每个页面中重新集成页面统计的代码(包括调用了 onResume 和 onPause 的Activity)。
        MobclickAgent.openActivityDurationTrack(false);
//      MobclickAgent.setAutoLocation(true);
//      MobclickAgent.setSessionContinueMillis(1000);
        MobclickAgent.updateOnlineConfig(this);
        // ================== UM统计 =====================
        
       //  (2)
       // ================== UM升级 =====================
        UmengUpdateAgent.setDefault();
//        UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
//			@Override
//			public void onUpdateReturned(int arg0, UpdateResponse updateInfo) {
//				android.util.Log.d("222", "updateInfo = " + updateInfo + ",arg0  = " + arg0);
//				switch (arg0) {
//				case UpdateStatus.Yes:
//					
//					break;
//
//				default:
//					break;
//				}
//			}
//		});
        new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
		        UmengUpdateAgent.update(TabsActivity.this);
			}
		}, 500);

        // ================== UM升级 =====================
    }
    
    @Override
    public void onBackPressed() {
    	android.util.Log.d("111", "onBackPressed");
    	super.onBackPressed();
    }
    
}
