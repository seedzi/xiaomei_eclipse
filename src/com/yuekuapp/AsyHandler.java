package com.yuekuapp;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.yuekuapp.proxy.MessageArg;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;

public class AsyHandler extends Handler {

	private WeakReference<Fragment> mReference;

	public AsyHandler(Fragment fragment) {
		mReference = new WeakReference<Fragment>(fragment);
	}

	@Override
	public void handleMessage(Message msg) {
		Fragment fragment = mReference.get();
		if (fragment == null || fragment.isRemoving()) {
			return;
		} else {
			//Object obj = msg.obj;
			switch (msg.arg1) {
			case MessageArg.ARG1.TOAST_MESSAGE:
				TipTool.onCreateToastDialog(fragment.getActivity(), msg.obj + "");
				break;
			case MessageArg.ARG1.CALL_BACKMETHOD:
				invokeMethod(fragment, msg);
				break;
			default:

				break;
			}
		}
	}


	private void invokeMethod(Fragment fragment,
			Message msg) {
		try {
			if (!(msg.obj instanceof String)) {
				TipTool.onCreateToastDialog(fragment.getActivity(), "Method error:"
						+ msg.obj);
				return;
			}
			Method method;
			try {
				method = fragment.getClass().getMethod(
						msg.obj + "", new Class[] { Bundle.class });
			} catch (NoSuchMethodException e) {
				invokeNoArgMethod(fragment,msg);
				return;
			}
			if (method!=null) {
				method.setAccessible(true);
				method.invoke(fragment, msg.getData());
			}
		} catch (NoSuchMethodException e) {
			TipTool.onCreateToastDialog(fragment.getActivity(), "NoSuchMethodException"+ e.getMessage());
		} catch (IllegalAccessException e) {
			TipTool.onCreateToastDialog(fragment.getActivity(), "IllegalAccessException"+ e.getMessage());
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	private void invokeNoArgMethod(Fragment fragment,Message msg) throws NoSuchMethodException,
			IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		Method method = fragment.getClass().getMethod(msg.obj + "");
		if (method == null) {
			throw new NoSuchMethodException(msg.obj + "");
		}
		method.setAccessible(true);
		method.invoke(fragment);
	}
}
