package com.xiaomei;

import android.app.Application;

/**
 * Created by huzhi on 15-2-16.
 */
public class XiaoMeiApplication extends Application{

    private static XiaoMeiApplication instance;

    public synchronized static XiaoMeiApplication getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

}
