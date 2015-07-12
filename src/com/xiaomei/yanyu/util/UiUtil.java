package com.xiaomei.yanyu.util;

import com.xiaomei.yanyu.R;

import android.content.Context;
import android.widget.Toast;

public class UiUtil {

    private UiUtil() {
        
    }

    public static void showToast(Context context, String string) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show();;
    }

    public static void showToast(Context context, int res) {
        showToast(context, context.getResources().getString(res));
    }
}
