package com.xiaomei.yanyu.util;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;

public class InputUtils {
	
	/**隐藏软键盘**/
	public static void hidSoftInput(Context context){
		InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);    
		//得到InputMethodManager的实例  
		if (imm.isActive()) {  
			//如果开启  
			imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);   
			//关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的  
		} 
	}
	
}
