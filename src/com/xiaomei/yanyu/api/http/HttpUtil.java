package com.xiaomei.yanyu.api.http;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class HttpUtil {

    public static String CONSULT_URL = "http://chat.meiqia.com/widget/phone/ticket.html?unitid=55bf09304eae353c4600000d&theme=rosered";

    public static final String QUERY_TOKEN = "token";
    public static final String QUERY_UPTIME = "uptime";
    public static final String QUERY_FIG = "fig";
    public static final String QUERY_ACTION = "action";

    public static final String QUERY_CODE = "code";

    public static final String QUERY_AREA_ID = "cityid";
    public static final String QUERY_COUNTRY = "country";
    public static final String QUERY_SPECIAL = "special";

    public static final String QUERY_CURPAGE = "curpage";
    public static final String QUERY_PERPAGE = "perpage";

    public static final String QUERY_GOODS_ID = "goods_id";
    public static final String QUERY_USERID = "userid";
    public static final String QUERY_ORDERID = "orderid";
    public static final String QUERY_SUBDATE = "subdate";
    public static final String QUERY_USERNAME = "username";
    public static final String QUERY_MOBILE = "mobile";
    public static final String QUERY_PASSPORT = "passport";
    public static final String QUERY_COUPONID = "couponid";

    public static final String ACTION_UPDATE = "update";

    private HttpUtil() {}

    public static Map<String, String> signParams(Map<String, String> unsigned) {
        if (unsigned == null || unsigned.isEmpty()) {
            return null;
        }
        NameValuePair[] unsignedPairs = formatPairs(unsigned);
        NameValuePair[] signedPairs = AbstractHttpApi.signValuePairs(unsignedPairs);
        return formatMap(signedPairs);
    }

    public static NameValuePair[] formatPairs(Map<String, String> map) {
        NameValuePair[] pairs = new NameValuePair[map.size()];
        int i = 0;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            pairs[i] = new BasicNameValuePair(entry.getKey(), entry.getValue());
            i++;
        }
        return pairs;
    }

    public static Map<String, String> formatMap(NameValuePair[] pairs) {
        HashMap<String, String> map = new HashMap<String, String>();
        for (NameValuePair pair : pairs) {
            map.put(pair.getName(), pair.getValue());
        }
        return map;
    }
}
