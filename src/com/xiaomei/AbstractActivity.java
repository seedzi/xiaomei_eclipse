package com.xiaomei;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.yuekuapp.BaseActivity;
import com.yuekuapp.BaseControl;

/**
 * Created by huzhi on 15-2-17.
 */
public class AbstractActivity<T extends BaseControl> extends BaseActivity<T>{

    private static List<Activity>  activities;
    
    public static synchronized void  clearActivity(){
        if(activities!=null){
            for(Activity ac:activities){
                ac.finish();
            }
            activities.clear();
        }
    }
    
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
	}
	
	

}
