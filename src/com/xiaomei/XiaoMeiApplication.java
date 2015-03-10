package com.xiaomei;

import com.xiaomei.api.XiaoMeiApi;
import com.yuekuapp.proxy.ControlFactory;

import android.app.Application;

/**
 * Created by huzhi on 15-2-16.
 */
public class XiaoMeiApplication extends Application{

    private static XiaoMeiApplication instance;

    public synchronized static XiaoMeiApplication getInstance(){
        return instance;
    }

    private XiaoMeiApi api;
    
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        api = new XiaoMeiApi("XiaoMei1.0", this);
        ControlFactory.init(this);
    }

    public XiaoMeiApi getApi(){
    	return api;
    }
}
