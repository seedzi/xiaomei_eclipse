package com.yuekuapp;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.yuekuapp.proxy.ControlFactory;
import com.yuekuapp.proxy.MessageProxy;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BaseFragment<T extends BaseControl> extends Fragment {

	protected T mControl;
    protected MessageProxy messageProxy;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
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
    				messageProxy = new MessageProxy(new AsyHandler(this));
    				mControl = ControlFactory.getControlInstance(tClass, messageProxy);	
    			}
    		}
    	}
	}
    
    @Override
    public void onResume() {
    	super.onResume();
		if(mControl==null||messageProxy==null){
			controlInit();
		}
    }
    
    @Override
    public void onPause() {
    	super.onPause();
    }
    
    @Override
    public void onDestroy() {
		if (mControl != null) {
			mControl.onDestroy();
		}
    	super.onDestroy();
    }
    
}
