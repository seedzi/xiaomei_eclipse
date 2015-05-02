package com.xiaomei.yanyu.api.util;

import android.net.Uri;
import android.util.Log;

import com.xiaomei.yanyu.api.exception.XiaoMeiIOException;

import org.apache.http.client.methods.HttpRequestBase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * string util
 */
public class StringUtil {

    private static int temp;

    /**
     * transform the url to local file name
     *
     * @param httpBase
     * @return
     */
    public static String urlToFileName(HttpRequestBase httpBase) {
        Uri uri = Uri.parse(httpBase.getURI().toString());
        if (!uri.isHierarchical()) {
            return "" + temp++;
        }
        return Md5Util.md5(uri.toString());
    }

    /**
     * convert stream to string
     *
     * @param is
     * @return
     */
    public static String convertStreamToString(InputStream is) throws XiaoMeiIOException {
    	StringBuilder sb = new StringBuilder();
        String line = null;
    	try {
	        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"utf-8"));
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                throw new XiaoMeiIOException(e);
            }
        }
        Log.i("network",sb.toString());
        return sb.toString();
    }

}
