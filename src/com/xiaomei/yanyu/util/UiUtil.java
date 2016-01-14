package com.xiaomei.yanyu.util;

import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.widget.ProgressDialogFragment;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Handler;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
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

    public static void postToast(final Context context, final int res) {
        new Handler(context.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                showToast(context, res);
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

    public static void hideSoftInput(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);     
        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public static void hideSoftInput(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
    }

    public static void overridePendingTransition(Activity activity) {
        activity.overridePendingTransition(R.anim.activity_slid_in_from_right,
                R.anim.activity_slid_out_no_change);
    }

    public static void setViewEnabled(View view, boolean enabled) {
        if (view instanceof ViewGroup) {
            ViewGroup parent = (ViewGroup)view;
            int count = parent.getChildCount();
            for (int i = 0; i < count; i++) {
                setViewEnabled(parent.getChildAt(i), enabled);
            }
        }
        view.setEnabled(enabled);
    }

    public static void showProgressDialog(Activity activity) {
        FragmentManager fm = activity.getFragmentManager();
        String tag = ProgressDialogFragment.TAG;
        ProgressDialogFragment fragment = (ProgressDialogFragment)fm.findFragmentByTag(tag);
        if (fragment == null) {
            fragment = new ProgressDialogFragment();
        }
        fragment.show(fm, tag);
    }

    public static void dismissProgressDialog(Activity activity) {
        FragmentManager fm = activity.getFragmentManager();
        ProgressDialogFragment fragment = (ProgressDialogFragment)fm.findFragmentByTag(ProgressDialogFragment.TAG);
        if (fragment != null) {
            fragment.dismiss();
        }
    }
}
