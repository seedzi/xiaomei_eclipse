package com.xiaomei.yanyu.util;

import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class SystemUtils {
	
	
	/** 
     * 分享功能 
     *  
     * @param context 
     *            上下文 
     * @param activityTitle 
     *            Activity的名字 
     * @param msgTitle 
     *            消息标题 
     * @param msgText 
     *            消息内容 
     * @param imgPath 
     *            图片路径，不分享图片则传null 
     */  
    public static void shareMsg(Context context,String activityTitle, String msgTitle, String msgText,  
            String imgPath) {  
        Intent intent = new Intent(Intent.ACTION_SEND);  
        if (imgPath == null || imgPath.equals("")) {  
            intent.setType("text/plain"); // 纯文本  
        } else {  
            File f = new File(imgPath);  
            if (f != null && f.exists() && f.isFile()) {  
                intent.setType("image/jpg");  
                Uri u = Uri.fromFile(f);  
                intent.putExtra(Intent.EXTRA_STREAM, u);  
            }  
        }  
        intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);  
        intent.putExtra(Intent.EXTRA_TEXT, msgText);  
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
        context.startActivity(Intent.createChooser(intent, activityTitle));  
    }  

}
