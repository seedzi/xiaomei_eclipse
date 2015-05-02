package com.xiaomei.yanyu.util;

import java.util.TimeZone;


public class SimpleDataFormater {

    /**
     */
    public static int convertTimeMillisToSecond(long millisSecond) {
        return Long.valueOf(millisSecond / 1000).intValue();
    }

    /**
     */
    public static long convertTimeSecondToMillis(long second) {
        return second * 1000;
    }

    /**
     * @return
     */
    public static String convertTimeToShow(long createTimeStamp, long nowTimeStamp) {
        String ret = "";
        TimeZone timeZone = TimeZone.getDefault();
        DateTime createTime = DateTime.forInstant(createTimeStamp, timeZone);
        DateTime now = DateTime.forInstant(nowTimeStamp, timeZone);
        boolean isSameDay = createTime.isSameDayAs(now);
        if (isSameDay) {
            long result = createTime.numSecondsFrom(now);
            result = result / 60;
            if (result <= 3) {
                ret = "刚刚";
            } else {
                ret = createTime.format("hh:mm");
            }
        } else {
            int result = createTime.numDaysFrom(now);
            if (result == 1) {
                ret = "昨天" + createTime.format("hh:mm");
            } else if (result > 1 && result <= 30) {
                ret = result + "天前";
            } else if (result > 30) {
                result = result / 30;
                if (result >= 6) {
                    ret = "半年前";
                } else {
                    ret = result + "月前";
                }
            }
        }
        return ret;
    }

    /**
     * @return
     */
    public static String convertTime2Show(long createTimeStamp, long nowTimeStamp) {
        String ret = "";
        TimeZone timeZone = TimeZone.getDefault();
        DateTime createTime = DateTime.forInstant(createTimeStamp, timeZone);
        DateTime now = DateTime.forInstant(nowTimeStamp, timeZone);
        if (createTime.isSameDayAs(now)) {
            ret = "今天" + createTime.format("hh:mm");
        } else {
            int result = createTime.numDaysFrom(now);
            if (result == 1) {
                ret = "昨天" + createTime.format("hh:mm");
            } else if (result == 2) {
                ret = "前天" + createTime.format("hh:mm");
            } else {
                ret = createTime.format("YYYY-MM-DD hh:mm");
            }
        }
        return ret;
    }

    /**
     * @return
     */
    public static String bubbleConvertTimeToShow(long createTimeStamp, long nowTimeStamp) {
        String ret = "";
        TimeZone timeZone = TimeZone.getDefault();
        DateTime createTime = DateTime.forInstant(createTimeStamp, timeZone);
        DateTime now = DateTime.forInstant(nowTimeStamp, timeZone);
        boolean isSameDay = createTime.isSameDayAs(now);
        if (isSameDay) {
            long second = createTime.numSecondsFrom(now);
            long minter = second / 60;
            if (minter <= 1) {
                ret = "刚刚";
            } else {
                long hour = minter / 60;
                if (hour < 1) {
                    ret = minter + "分钟前";
                } else {
                    long temp = minter % 60;
                    if (temp != 0) {
                        ret = hour + "小时前";
                    } else {
                        ret = hour + "小时前";
                    }
                }
            }
        } else {
            int result = createTime.numDaysFrom(now);
            if (result <= 30) {
                if (result % 7 == 0) {
                    ret = result / 7 + "周前";
                } else {
                    ret = result + "天前";
                }
            } else if (result > 30) {
                result = result / 30;
                if (result >= 6) {
                    ret = "半年前";
                } else {
                    ret = result + "月前";
                }
            }
        }
        return ret;
    }

    public static String getTimeInterval(long timeStamp, long nowTimeStamp) {
        String ret = "";
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        long ns = 1000;
        long diff;
        diff = timeStamp - nowTimeStamp;
        long day = diff / nd;
        long hour = diff % nd / nh;
        long min = diff % nd % nh / nm;
        long sec = diff % nd % nh % nm / ns;

        ret = getTimeString(day, hour, min, sec);
        return ret;
    }

    public static String getTimeString(long day, long hour, long min, long sec) {
        sec = sec < 0 ? 0 : sec;
        min = min < 0 ? 0 : min;
        hour = hour < 0 ? 0 : hour;
        day = day < 0 ? 0 : day;
        String ret = sec + "秒";
        if (min > 0) {
            ret = min + "分钟";
        }

        if (hour > 0) {
            if (min > 0) {
                ret = hour + "小时" + ret;
            } else {
                ret = hour + "小时";
            }
        }
        if (day > 0) {
            ret = day + "天";
        }
        return ret;
    }
}