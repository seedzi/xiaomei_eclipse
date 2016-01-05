package com.xiaomei.yanyu.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import com.xiaomei.yanyu.R;

import android.content.Context;
import android.text.TextUtils;


public class DateUtils {
	
    private static final long DATE_QUERY_FACTOR = 1000;

    public static String formateDate(long time) {
		android.util.Log.d("111", "time = " + time + ",System = " + System.currentTimeMillis());
		SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd"); //格式化当前系统日期
		String dateTime = dateFm.format(time);
		return dateTime;
	}

    public static String formateDate(String queryParameter) {
        return formateDate(Long.valueOf(queryParameter) * DATE_QUERY_FACTOR);
    }

    public static String formatQueryParameter(long timeMillis) {
        return String.valueOf(timeMillis / DATE_QUERY_FACTOR);
    }

    public static long getTimeInMillis(int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthOfYear, dayOfMonth);
        return calendar.getTimeInMillis();
    }

	public static String getTextByTime(Context context, Long time,
            int formatId) {
        return getTextByTime(context, String.valueOf(time), formatId);
    }

    public static String getTextByTime(Context context, String time,
            int formatId) {
        long tranTimeWithLocalZone = tranTimeWithLocalZone(time);
        long currentLocalZoneTime = getCurrentLocalZoneTime();
        long diff = currentLocalZoneTime - tranTimeWithLocalZone;
        if (diff < 0) {
            return getMessageByDate(context, new Date(tranTimeWithLocalZone),
                    formatId);
        }
        if (TextUtils.isEmpty(time)) {
            return getMessageByDate(context, new Date(currentLocalZoneTime),
                    formatId);
        }
        if(isSameDay(tranTimeWithLocalZone, currentLocalZoneTime)||diff<3600000){//如果是当天或者是2个时间小于1小时
            if (diff > 3600000) {
                return (int) Math.floor(diff / 3600000)
                        + context.getResources().getString(
                                R.string.rss_list_time_text_hour);
            } else if (diff > 60000) {
                return (int) Math.floor(diff / 60000)
                        + context.getResources().getString(
                                R.string.rss_list_time_text_min);
            } else {
                return context.getResources().getString(
                                R.string.rss_list_time_text_just);
            }
        }
        return getMessageByDate(context, new Date(tranTimeWithLocalZone),
                formatId);
    }

    /**
     * 将服务器时间戳转换为本地时区时间戳
     *
     * @param time
     * @return
     */
    private static long tranTimeWithLocalZone(String time) {
        return transTimeWithLocalTimeZone(Long.parseLong(time) * 1000);
    }

    /**
     * 获取当前时区的时间戳
     *
     * @return
     */
    public static long getCurrentLocalZoneTime() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTimeInMillis();
    }

    public static long transTimeWithLocalTimeZone(long time) {
        return time - getDiffTimeZoneRawOffset(TimeZone.getDefault().getID());
    }

    private static int getDiffTimeZoneRawOffset(String timeZoneId) {
        return TimeZone.getDefault().getRawOffset()
                - TimeZone.getTimeZone(timeZoneId).getRawOffset();
    }

    public static String getMessageByDate(Context context, Date date,
            int strFormatId) {
        SimpleDateFormat simple = new SimpleDateFormat(context.getResources()
                .getString(strFormatId));
        return simple.format(date);
    }
    
    public static boolean isSameDay(long time1, long time2) {
        return isSameDay(new Date(time1), new Date(time2));
    }

    public static boolean isSameDay(Date day1, Date day2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String ds1 = sdf.format(day1);
        String ds2 = sdf.format(day2);
        if (ds1.equals(ds2)) {
            return true;
        } else {
            return false;
        }
    }
}
