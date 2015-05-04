package com.xiaomei.yanyu.util;

import java.text.SimpleDateFormat;


public class DateUtils {
	
	public static String formateDate(long time){
		android.util.Log.d("111", "time = " + time + ",System = " + System.currentTimeMillis());
		SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd"); //格式化当前系统日期
		String dateTime = dateFm.format(time);
		return dateTime;
	}


}
