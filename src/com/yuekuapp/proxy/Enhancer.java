package com.yuekuapp.proxy;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import android.util.Log;

import com.google.dexmaker.stock.ProxyBuilder;
import com.xiaomei.yanyu.DebugRelease;
import com.yuekuapp.proxy.callback.Interceptor;
import com.yuekuapp.proxy.callbackfilter.InterceptorFilter;

public class Enhancer<T> implements InvocationHandler {

	private Interceptor[] callBacks;
	private InterceptorFilter filter;
	private Class<T> superClazz;
	private Class<?>[] constructorArgTypes;
	private Object[] constructorArgValues;
	private File dir;

	Enhancer(File cacheFileDir, Class<T> superClazz) {
		this.superClazz = superClazz;
		this.dir = cacheFileDir;
	}

	/**
	 * 
	 * @param superClazz
	 * @param clazzes
	 * @param args
	 */
	Enhancer(File cacheFileDir, Class<T> superClazz, Class<?>[] clazzes,
			Object[] args) {
		this.superClazz = superClazz;
		constructorArgTypes = clazzes;
		constructorArgValues = args;
		this.dir = cacheFileDir;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		if (filter == null) {
			return null;
		}
		int accept = filter.accept(method);
		if (accept >= callBacks.length) {
			return null;
		}
		if (method.getName().equals("hashCode")
				|| method.getName().equals("toString")
				||method.getName().equals("clone")
				||method.getName().equals("internalClone")) {
			return ProxyBuilder.callSuper(proxy, method, args);
		}
		return callBacks[accept].intercept(proxy, method, args);
	}

	public void setCacheDir(File file) {
		this.dir = file;
	}

	/*
	 * 
	 */
	public void addCallBacks(Interceptor[] callBacks) {
		this.callBacks = callBacks;
	}

	/**
	 * 
	 * @param filters
	 */
	public void addFilter(InterceptorFilter filter) {
		this.filter = filter;
	}

	/**
	 * 生成代理
	 * 
	 * @return
	 */
	public T create() {

		ProxyBuilder<T> proxy = ProxyBuilder.forClass(superClazz);
		if (constructorArgTypes != null && constructorArgValues != null
				&& constructorArgValues.length == constructorArgTypes.length) {
			proxy.constructorArgTypes(constructorArgTypes)
					.constructorArgValues(constructorArgValues);
		}
		proxy.handler(this);
		proxy.dexCache(dir);
		try {
			return proxy.build();
		} catch (IOException e) {
		    if(DebugRelease.isDebug)
		        Log.i("app", "error" + e.getClass() + "");
			e.printStackTrace();
		}
		return null;
	}
}
