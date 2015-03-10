package com.yuekuapp;

import android.os.Bundle;
import android.os.Message;

import com.yuekuapp.proxy.MessageArg;
import com.yuekuapp.proxy.MessageProxy;


public class BaseControl implements Cloneable {

	protected MessageProxy mMethodCallBack;

	public BaseControl(MessageProxy mMethodCallBack) {
		this.mMethodCallBack = mMethodCallBack;
	}

	public void onResume(){
		
	}
	public void onStop() {
		mMethodCallBack.clearAllMessage();
	}

	public void onDestroy() {
		mMethodCallBack.clearAllMessage();
//		mMethodCallBack = null;
	}

	protected void sendToastMessage(String toast) {
		Message msg = mMethodCallBack.ObtionMessage(MessageArg.WHAT.UI_MESSAGE);
		msg.arg1 = MessageArg.ARG1.TOAST_MESSAGE;
		msg.obj = toast;
		mMethodCallBack.sendMessage(msg);
	}

	protected void sendMessage(String method) {
		Message msg = mMethodCallBack.ObtionMessage(MessageArg.WHAT.UI_MESSAGE);
		msg.arg1 = MessageArg.ARG1.CALL_BACKMETHOD;
		msg.obj = method;
		mMethodCallBack.sendMessage(msg);
	}

    protected void sendMessage(String method,long delayMillis) {
        Message msg = mMethodCallBack.ObtionMessage(MessageArg.WHAT.UI_MESSAGE);
        msg.arg1 = MessageArg.ARG1.CALL_BACKMETHOD;
        msg.obj = method;
        mMethodCallBack.sendMessageDelay(msg,delayMillis);
    }
    
	protected void sendMessage(String method, Bundle bundle) {
		Message msg = mMethodCallBack.ObtionMessage(MessageArg.WHAT.UI_MESSAGE);
		msg.arg1 = MessageArg.ARG1.CALL_BACKMETHOD;
		msg.obj = method;
		msg.setData(bundle);
		mMethodCallBack.sendMessage(msg);
	}

    protected void sendMessage(String method, Bundle bundle,long delayMillis) {
        Message msg = mMethodCallBack.ObtionMessage(MessageArg.WHAT.UI_MESSAGE);
        msg.arg1 = MessageArg.ARG1.CALL_BACKMETHOD;
        msg.obj = method;
        msg.setData(bundle);
        mMethodCallBack.sendMessageDelay(msg,delayMillis);
    }

	protected Message getDataMessage(Bundle bundle) {
		Message msg = mMethodCallBack.ObtionMessage(MessageArg.WHAT.UI_MESSAGE);
		msg.arg1 = MessageArg.ARG1.CALL_BACKMETHOD;
		msg.setData(bundle);
		return msg;
	}

	protected Message getDataMessage(String method,Bundle bundle) {
		Message msg = mMethodCallBack.ObtionMessage(MessageArg.WHAT.UI_MESSAGE);
		msg.arg1 = MessageArg.ARG1.CALL_BACKMETHOD;
		msg.obj = method;
		msg.setData(bundle);
		return msg;
	}
	
	protected Message getMessage() {
		Message msg = mMethodCallBack.ObtionMessage(MessageArg.WHAT.UI_MESSAGE);
		msg.arg1 = MessageArg.ARG1.CALL_BACKMETHOD;
		return msg;
	}

	protected Message getMessage(String method){
		Message msg = mMethodCallBack.ObtionMessage(MessageArg.WHAT.UI_MESSAGE);
		msg.arg1 = MessageArg.ARG1.CALL_BACKMETHOD;
		msg.obj = method;
		return msg;
	}
	
    protected Message getToastMessage(String toastMsg) {
        Message msg = mMethodCallBack.ObtionMessage(MessageArg.WHAT.UI_MESSAGE);
        msg.arg1 = MessageArg.ARG1.TOAST_MESSAGE;
        msg.obj = toastMsg;
        return msg;
    }

}
