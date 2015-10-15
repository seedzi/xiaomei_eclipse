package com.xiaomei.yanyu.widget;

import com.xiaomei.yanyu.R;

import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

public class PopupDialog {

    private PopupWindow mPopupWindow;

    public void initPopupWindow(View content) {
        mPopupWindow = new PopupWindow(content, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, true);
        mPopupWindow.setBackgroundDrawable(
                new ColorDrawable(content.getResources().getColor(R.color.semi_transparent)));
    }

    public void show(View viewForToken) {
        if (mPopupWindow != null) {
            mPopupWindow.showAtLocation(viewForToken, Gravity.BOTTOM, 0, 0);
        }
    }

    public void dismiss() {
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
        }
    }

}