package com.xiaomei.yanyu.api.util;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * md5 cipher
 * 
 *
 */
public class Md5Util {

	/**
	 * md5 message digest algorithm method
	 * @param pws
	 * @return
	 * @throws Md5Error
	 * @throws java.security.NoSuchAlgorithmException
	 */
	public static String md5(String pws){
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
		}
		messageDigest.reset();
		messageDigest.update(pws.getBytes());
		return HexUtil.bytes2HexStr(messageDigest.digest());
	}
}
