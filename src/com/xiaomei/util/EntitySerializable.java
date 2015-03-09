package com.xiaomei.util;

import java.io.Serializable;
import java.text.SimpleDateFormat;
public interface EntitySerializable extends Serializable {
	public static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	public static SimpleDateFormat SDF3 = new SimpleDateFormat("MM-dd HH:mm");
	public static SimpleDateFormat SDF4 = new SimpleDateFormat("HH:mm:ss");
	public static SimpleDateFormat SDF5 = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat SDF6 = new SimpleDateFormat("HH:mm");
	public static SimpleDateFormat SDF7 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static SimpleDateFormat SDF_BUILD_VERSION = new SimpleDateFormat("yyyy.MM.dd.HH");
}
