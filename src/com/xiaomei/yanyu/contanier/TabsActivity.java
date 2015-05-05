package com.xiaomei.yanyu.contanier;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.KeyCharacterMap.KeyData;

import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.AbstractActivity;
import com.xiaomei.yanyu.XiaoMeiApplication;
import com.xiaomei.yanyu.api.exception.XiaoMeiCredentialsException;
import com.xiaomei.yanyu.api.exception.XiaoMeiIOException;
import com.xiaomei.yanyu.api.exception.XiaoMeiJSONException;
import com.xiaomei.yanyu.api.exception.XiaoMeiOtherException;
import com.xiaomei.yanyu.comment.CommentListActivity;
import com.xiaomei.yanyu.util.UserUtil;

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
        UmengUpdateAgent.update(this);
        // ================== UM升级 =====================
    }
    
    @Override
    public void onBackPressed() {
    	android.util.Log.d("111", "onBackPressed");
    	super.onBackPressed();
    }
    
}
