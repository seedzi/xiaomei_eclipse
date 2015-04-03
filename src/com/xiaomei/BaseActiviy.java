package com.xiaomei;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.yuekuapp.BaseActivity;
import com.yuekuapp.BaseControl;

/**
 * Created by huzhi on 15-2-17.
 */
public class BaseActiviy<T extends BaseControl> extends BaseActivity<T>{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	}

}
