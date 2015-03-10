package com.yuekuapp;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.yuekuapp.proxy.MessageArg;

public class AsyObjHandler extends Handler {

	private WeakReference<Object> mReference;

	public AsyObjHandler(Object fragment) {
		mReference = new WeakReference<Object>(fragment);
	}

	@Override
	public void handleMessage(Message msg) {
		Object fragment = mReference.get();
		if (fragment == null) {
			return;
		} else {
			switch (msg.arg1) {
			case MessageArg.ARG1.CALL_BACKMETHOD:
				invokeMethod(fragment, msg);
				break;
			default:

				break;
			}
		}
	}


	private void invokeMethod(Object fragment,
			Message msg) {
		try {
			if (!(msg.obj instanceof String)) {
				throw new Exception("message error msg.obj not an string object");
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
			e.printStackTrace();
//			TipTool.onCreateToastDialog(fragment.getActivity(), "NoSuchMethodException"+ e.getMessage());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
//			TipTool.onCreateToastDialog(fragment.getActivity(), "IllegalAccessException"+ e.getMessage());
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void invokeNoArgMethod(Object fragment,Message msg) throws NoSuchMethodException,
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
