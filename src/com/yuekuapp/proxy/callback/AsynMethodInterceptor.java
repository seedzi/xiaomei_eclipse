package com.yuekuapp.proxy.callback;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import android.os.Message;

import com.google.dexmaker.stock.ProxyBuilder;
import com.yuekuapp.annotations.AsynMethod;
import com.yuekuapp.annotations.AsynMethod.ArgeType;
import com.yuekuapp.log.Logger;
import com.yuekuapp.proxy.MessageArg;
import com.yuekuapp.proxy.MessageProxy;


public class AsynMethodInterceptor implements Interceptor {
	private Logger logger=new Logger("sps");
	private static ExecutorService pool = Executors.newCachedThreadPool();
	private MessageProxy mMethodCallBack;
	private ConcurrentHashMap<String,Integer> collection;
	private AtomicInteger count;

	public AsynMethodInterceptor(MessageProxy mMethodCallBack) {
		this.mMethodCallBack = mMethodCallBack;
		count=new AtomicInteger(0);
		collection=new ConcurrentHashMap<String, Integer>();
	}

	
	@Override
	public Object intercept(final Object proxy, final Method method,
			final Object[] args) throws Throwable {
		final AsynMethod asynMethod = method.getAnnotation(AsynMethod.class);
		if (asynMethod != null) {
			pool.execute(new Runnable() {
				@Override
				public void run() {
					try {
						Object result=null;
						ArgeType type=asynMethod.methodType();
						switch (type) {
						case normal:
							result = ProxyBuilder.callSuper(proxy, method,
									args);
							break;
						case atom:
							logger.i("method:"+method+" boolean:"+collection.containsKey(method));
							if(collection.get(method.getName()) != null){
								return;
							}else{
								collection.put(method.getName(), count.addAndGet(1));
							}
							result = ProxyBuilder.callSuper(proxy, method,
									args);
							collection.remove(method.getName());
							count.decrementAndGet();
							break;
						default:
							break;
						}
						if (result!=null&&result.getClass() == Message.class) {
							Message msg = (Message) result;
							switch (msg.arg1) {
							case MessageArg.ARG1.TOAST_MESSAGE:
								break;
							case MessageArg.ARG1.CALL_BACKMETHOD:
								if (msg.obj == null
										|| !(msg.obj instanceof String)
										|| "".equals(msg.obj.toString().trim())) {
									msg.obj = method.getName() + "CallBack";
								}
							default:
								break;	
							}
							mMethodCallBack.sendMessage(msg);
						}
					} catch (Throwable e) {
						e.printStackTrace();
					}
				}
			});
			return null;
		} else {
			return ProxyBuilder.callSuper(proxy, method, args);
		}
	}
}
