package com.yuekuapp;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.yuekuapp.proxy.MessageArg;


public class ActivityHandler extends Handler {
	
	private WeakReference<Activity> mReference;

	public ActivityHandler(Activity baseActivity) {
		mReference = new WeakReference(baseActivity);
	}

	@Override
	public void handleMessage(Message msg) {
		Activity acitivty = mReference.get();
		if (acitivty == null || acitivty.isFinishing()) {
			return;
		} else {
			//Object obj = msg.obj;
			switch (msg.arg1) {
			case MessageArg.ARG1.TOAST_MESSAGE:
				TipTool.onCreateToastDialog(acitivty, msg.obj + "");
				break;
			case MessageArg.ARG1.CALL_BACKMETHOD:
				invokeMethod(acitivty, msg);
				break;
			default:

				break;
			}
		}
	}


	private void invokeMethod(Activity acitivty,
			Message msg) {
		try {
			if (!(msg.obj instanceof String)) {
				TipTool.onCreateToastDialog(acitivty, "Method error:"
						+ msg.obj);
				return;
			}
			Method method;
			try {
				method = acitivty.getClass().getMethod(
						msg.obj + "", new Class[] { Bundle.class });
			} catch (NoSuchMethodException e) {
				invokeNoArgMethod(acitivty,msg);
				return;
			}
			if (method!=null) {
				method.setAccessible(true);
				method.invoke(acitivty, msg.getData());
			}
		} catch (NoSuchMethodException e) {
			TipTool.onCreateToastDialog(acitivty, "NoSuchMethodException"+ e.getMessage());
		} catch (IllegalAccessException e) {
			TipTool.onCreateToastDialog(acitivty, "IllegalAccessException"+ e.getMessage());
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	private void invokeNoArgMethod(Activity acitivty,Message msg) throws NoSuchMethodException,
			IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		Method method = acitivty.getClass().getMethod(msg.obj + "");
		if (method == null) {
			throw new NoSuchMethodException(msg.obj + "");
		}
		method.setAccessible(true);
		method.invoke(acitivty);
	}
}