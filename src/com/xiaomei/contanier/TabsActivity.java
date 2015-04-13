package com.xiaomei.contanier;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.KeyCharacterMap.KeyData;

import com.xiaomei.BaseActiviy;
import com.xiaomei.R;
import com.xiaomei.XiaoMeiApplication;

/**
 * Created by huzhi on 15-2-17.
 */
public class TabsActivity extends  BaseActiviy{


    public static void startActivity(Context context){
        Intent intent = new Intent(context,TabsActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container_layout);
        
        new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					XiaoMeiApplication.getInstance().getApi().showUserMsg();
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		}).start();
    }
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//    	if(keyCode == KeyEvent.KEYCODE_BACK){
//    		finish();
//    		return true;
//    	}
//    		
//    	return super.onKeyDown(keyCode, event);
//    }
    
    @Override
    public void onBackPressed() {
    	android.util.Log.d("111", "onBackPressed");
    	super.onBackPressed();
    }
    
}
