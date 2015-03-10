package com.xiaomei.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class ScreenUtils {
	
	
	public static int[] getScreenSize(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(
				Context.WINDOW_SERVICE);
		DisplayMetrics  dm = new DisplayMetrics();     
		wm.getDefaultDisplay().getMetrics(dm);     
		int width = dm.widthPixels;               
		int height = dm.heightPixels;
		int[] size = new int[]{width,height};
		return size;
	}
	
	public static int getScreenWidth(Context context) {
		return getScreenSize(context)[0];
	}

	public static int getScreenHeight(Context context) {
		return getScreenSize(context)[1];
	}
}
