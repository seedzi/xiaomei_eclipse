package com.yuekuapp.proxy;
import android.os.Handler;
import android.os.Message;

public class MessageProxy {

	private Handler mHandler;

	public MessageProxy(Handler handler) {
		this.mHandler = handler;
	}

	public Message ObtionMessage(int what) {
		return mHandler.obtainMessage(what);
	}

	public void sendMessage(Message msg){
		mHandler.sendMessage(msg);
	}

    public void sendMessageDelay(Message msg,long delayMillis){
        mHandler.sendMessageDelayed(msg,delayMillis);
    }

    public void postRunnableDelay(Runnable runnable,long delayMillis){
    	mHandler.postDelayed(runnable, delayMillis);
    }
    
    public void postRunnable(Runnable runnable){
    	mHandler.post(runnable);
    }
    
    public void removeRunnable(Runnable r){
    	mHandler.removeCallbacks(r);
    }
    
	public void clearAllMessage() {
		mHandler.removeMessages(MessageArg.WHAT.UI_MESSAGE);
	}

}