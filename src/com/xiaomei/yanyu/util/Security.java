package com.xiaomei.yanyu.util;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class Security {
	
	private static String MD5_KEY = "UZR$X~?1])5k3|35["; 
	
	private static String DIVISION_CHAR = "&";
	
	private static String EQUAL_CHAR = "=";
	
	public static String get32MD5Str(NameValuePair... values) {
		String str = getMd5Parameters(values);
		
		java.security.MessageDigest messageDigest = null;
		try {
			messageDigest = java.security.MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			return null;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		byte[] byteArray = messageDigest.digest();
		StringBuffer md5StrBuff = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			} else {
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
			}
		}
		return md5StrBuff.toString();
	}
	 
	private static String getMd5Parameters(NameValuePair... values){
		StringBuilder sb = new StringBuilder();
		List<NameValuePair> list = Arrays.asList(values);
		ComparatorUser comparator = new ComparatorUser();
		Collections.sort(list,comparator);
		int i = 0;
		int size = list.size();
		for(NameValuePair value:list){
			sb.append(value.getName());
			sb.append(EQUAL_CHAR);
			sb.append(value.getValue());
			if(i<size-1)
				sb.append(DIVISION_CHAR);
			i++;
		}
		sb.append(MD5_KEY);
		return sb.toString().trim();
	}
	
	private static class ComparatorUser implements Comparator<NameValuePair>{
		@Override
		public int compare(NameValuePair lhs, NameValuePair rhs) {
			return lhs.getName().compareTo(rhs.getName());
		}
	}
	
}
