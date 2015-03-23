package com.xiaomei.api.util;

public class Constant {

	public static class VFromConstant {
		public static final String vFromYouku = "youku";
		public static final String vFromTudou = "tudou";
		public static final String vFromFunshion = "funshion";
		public static final String vFromTengxun = "tengxun";
		public static final String vFromSouhu = "souhu";
		public static final String vFromLetv = "letv";

		public static final Long vFromYoukuId = (long) 0;
		public static final Long vFromTudouId = (long) 1;
		public static final Long vFromFunshionId = (long) 2;
		public static final Long vFromTengxunId = (long) 3;
		public static final Long vFromSouhuId = (long) 4;
		public static final Long vFromLetvId = (long) 5;
	}

	public static class PlayerFromContant {
		public static final String KEY_CAT_ID = "catid";
		public static final String KEY_ID = "id";
		public static final String KEY_USERFROM = "usefrom";
		public static final String KEY_FROM_LIVE = "live";	
		public static final String KEY_FROM_LIVE_URL = "live_url";
		public static final String KEY_FROM_LIVE_Name = "live_name";
	}

	public static class RecommendType {
		public static final String KEY_MOVIE = "vMovie";
		public static final String KEY_TV = "vTv";
		public static final String KEY_CARTOON = "vCartoon";
		public static final String KEY_LIVE = "vLive";
		public static final String KEY_AMUSE = "vLaughter";
		public static final String KEY_AMUSEMENT = "vAmusement";
		public static final String KEY_VARIETY = "vVariety";
	}
	
	public static class VideoConstant{
		public static final int MAX_CON_DOWNLOAD_SIZE = 5;
	}
	
	public static class CategoryCatid{
		public static final String CATID_RE = "0";
		public static final String CATID_MOVIE = "10";
		public static final String CATID_TV = "18";
		public static final String CATID_CARTOON = "12";
		public static final String CATID_LIVE= "16";
		public static final String CATID_LAUGHTER= "13";
		public static final String CATID_VARIETY= "14";
		public static final String CATID_AMUSEMENT= "15";
	}
	
	public static class SettingValue{
		public static final int CACHE_VALUE_BIAOQING = 0;
		public static final int CACHE_VALUE_GAOQING = 1;
		public static final int CACHE_VALUE_CHAOQING = 2;
	}
	
	public static class Setting{
		public static int cacheValue = SettingValue.CACHE_VALUE_BIAOQING;
	}
	
	/** PageType */
	public static class DetailPageType{
		public final static int DETAIL_PAGE_INTRODUCATION = 0;
		public final static int DETAIL_PAGE_SERIES = 1;
		public final static int DETAIL_PAGE_CACHE = 2;
		public final static int DETAIL_PAGE_COMMENT = 3;
	}
}
