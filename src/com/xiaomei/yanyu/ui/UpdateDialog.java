package com.xiaomei.yanyu.ui;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class UpdateDialog extends Dialog {

	public UpdateDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	public UpdateDialog(Context context, int theme) {
		super(context, theme);
	}

	public UpdateDialog(Context context) {
		super(context);
	}

	public UpdateDialog(Context context,int width,int height, View layout,int style){
		super(context,style);
		setContentView(layout);
		Window window = getWindow();
		WindowManager.LayoutParams params = window.getAttributes();
		params.gravity = Gravity.CENTER;
		window.setAttributes(params);
	}
}
