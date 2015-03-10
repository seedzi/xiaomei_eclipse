package com.yuekuapp.proxy;

import java.io.File;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import com.yuekuapp.BaseControl;
import com.yuekuapp.proxy.callback.AsynMethodInterceptor;
import com.yuekuapp.proxy.callback.Interceptor;
import com.yuekuapp.proxy.callbackfilter.AsynMethodFilter;

public class ControlFactory {

	private static File mCacheDir;

	public static void init(Context context) {
		int version = getVersionName(context);
		for(int i=1; i < version; i++){
			File file=context.getDir(i+"", Context.MODE_PRIVATE);
			deleteDirection(file);
		}
		mCacheDir = context.getDir(
				version+"", Context.MODE_PRIVATE);
	}
	
	private static int getVersionName(Context context) {
		int version=0;
		try{
			PackageManager packageManager = context.getPackageManager();
		    PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(),0);
		    version=packInfo.versionCode;
		}catch(Exception exception){
			
		}
	    return version;
	 }
	
	private static boolean deleteDirection(File file){
		if(file.exists()&&file.isDirectory()){
			File[]  files = file.listFiles();
			int  length=files.length;
			for(int i=0;i<length;i++){
				files[i].delete();
			}
			file.delete();
		}
		return false;
	}
	/**
	 * @param clazz
	 * @param handler
	 * @return
	 */
	public static <T extends BaseControl> T getControlInstance(Class<T> clazz,
			MessageProxy mMethodCallBack) {
		Enhancer<T> enhancer;
		if (mMethodCallBack != null) {
			enhancer = new Enhancer<T>(mCacheDir, clazz,
					new Class[] { mMethodCallBack.getClass() },
					new Object[] { mMethodCallBack });
		} else {
			enhancer = new Enhancer<T>(mCacheDir, clazz);
		}

		enhancer.addCallBacks(new Interceptor[] { new AsynMethodInterceptor(
				mMethodCallBack) });
		enhancer.addFilter(new AsynMethodFilter());
		return enhancer.create();
	}

	public static <T extends BaseControl> T getControlInstance(Class<T> clazz) {
		return getControlInstance(clazz, null);
	}
}
