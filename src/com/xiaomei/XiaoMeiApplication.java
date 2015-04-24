package com.xiaomei;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.xiaomei.api.XiaoMeiApi;
import com.yuekuapp.proxy.ControlFactory;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

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
        initImageLoader(this);
    }

    
    private void initImageLoader(Context context) {
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
        .cacheInMemory(true)
        .cacheOnDisc(true)
        .considerExifParams(true)
		.bitmapConfig(Bitmap.Config.RGB_565)
        .build();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs() // Remove for release app
				.defaultDisplayImageOptions(defaultOptions)
				.memoryCacheExtraOptions(480, 1600)
				.build();
		ImageLoader.getInstance().init(config);
	}
    
    public XiaoMeiApi getApi(){
    	return api;
    }
    
    private  Activity mCurrentActivity;
    
    public void setCurrentActivity(Activity ac){
        mCurrentActivity = ac;
    }
    
    public Activity getCurrentActivity(){
        return mCurrentActivity;
    } 
}
