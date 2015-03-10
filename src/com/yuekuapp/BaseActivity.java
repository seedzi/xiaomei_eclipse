package com.yuekuapp;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.yuekuapp.proxy.ControlFactory;
import com.yuekuapp.proxy.MessageProxy;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;


public class BaseActivity<T extends BaseControl> extends FragmentActivity {

	protected T mControl;
    
    protected MessageProxy messageProxy;
    
    @Override
	protected void onCreate(Bundle savedInstanceState) {
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    	super.onCreate(savedInstanceState);
		controlInit();
    }

    private void controlInit() {
    	Type type=getClass().getGenericSuperclass();
    	if(type instanceof ParameterizedType){
    		ParameterizedType p = (ParameterizedType)type;
    		Type[] arrayClasses=p.getActualTypeArguments();
    		for (Type item : arrayClasses) {
    			@SuppressWarnings("unchecked")
				Class<T> tClass = (Class<T>) item;
    			if(tClass.getSuperclass().equals(BaseControl.class)||tClass.equals(BaseControl.class)){
    				messageProxy = new MessageProxy(new ActivityHandler(this));
    				mControl = ControlFactory.getControlInstance(tClass, messageProxy);	
    			}
    		}
    	}
	}
    
	@Override
	protected void onDestroy() {
		if (mControl != null) {
			mControl.onDestroy();
		}
		super.onDestroy();
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onStop() {
		if (mControl != null) {
			mControl.onStop();
		}
		super.onStop();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if(mControl==null||messageProxy==null){
			controlInit();
		}
	}
}
