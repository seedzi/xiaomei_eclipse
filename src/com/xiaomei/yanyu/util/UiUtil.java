package com.xiaomei.yanyu.util;

import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.xiaomei.yanyu.R;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.SparseArray;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
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
    
    public static void postToast(final Context context, final String text) {
        new Handler(context.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                showToast(context, text);
            }
        });
    }
    
    public static <T extends View> T findById(View parent, int id) {
        try {
            SparseArray<View> holder = (SparseArray<View>) parent.getTag();
            if (holder == null) {
                holder = new SparseArray<View>();
                parent.setTag(holder);
            }
            
            View child = holder.get(id);
            if (child == null) {
                child = parent.findViewById(id);
                holder.put(id, child);
            }
            return (T) child;
        } catch (ClassCastException e) {
            return null;
        }
    }

    public static View findViewById(View parent, int id) {
        return UiUtil.<View>findById(parent, id);
    }

    public static TextView findTextViewById(View parent, int id) {
        return UiUtil.<TextView>findById(parent, id);
    }

    public static ImageView findImageViewById(View parent, int id) {
        return UiUtil.<ImageView>findById(parent, id);
    }

    public static void closeBoard(View v) {
        InputMethodManager imm = ( InputMethodManager ) v.getContext( ).getSystemService( Context.INPUT_METHOD_SERVICE );     
        if ( imm.isActive( ) ) {     
            imm.hideSoftInputFromWindow( v.getApplicationWindowToken( ) , 0 );   
        }  
    }

    public static void hideSoftInputFromActivity(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
