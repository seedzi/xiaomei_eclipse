package com.xiaomei.Payment;

import android.text.TextUtils;

public class PayUtils {

    /**
     * 检查用户输入
     * @param username
     * @param mobile
     * @param passport
     * @return
     */
    public static boolean checkoutInputData(String username,String mobile ,String passport){
        if(TextUtils.isEmpty(username) || TextUtils.isEmpty(mobile) || TextUtils.isEmpty(passport))
            return false;
        return true;
    }
}
