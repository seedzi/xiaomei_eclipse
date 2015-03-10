package com.yuekuapp;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.yuekuapp.proxy.ControlFactory;
import com.yuekuapp.proxy.MessageProxy;

public class BaseAsyObject<T extends BaseControl> {

	protected T mControl;
    protected MessageProxy messageProxy;
	
	public BaseAsyObject() {
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
    				messageProxy = new MessageProxy(new AsyObjHandler(this));
    				mControl = ControlFactory.getControlInstance(tClass, messageProxy);	
    			}
    		}
    	}
	}
    
    
    public void onDestroy() {
		if (mControl != null) {
			mControl.onDestroy();
		}
    }
    
}
