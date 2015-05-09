package com.xiaomei.yanyu.util;

import com.xiaomei.yanyu.R;

import android.content.Context;
import android.provider.CalendarContract.Instances;
import android.view.LayoutInflater;
import android.view.View;

public class UpdateManager {
	
	private static UpdateManager mInstance;
	
	public static UpdateManager getInstance(){
		if(mInstance == null)
			mInstance = new UpdateManager();
		return mInstance;
	}
	
	private UpdateManager(){}
	
	public void showUpdateDialog(Context context){
		View view = LayoutInflater.from(context).inflate(0, null);
		
	}

}
