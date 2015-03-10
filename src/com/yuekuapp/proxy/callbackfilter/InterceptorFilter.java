package com.yuekuapp.proxy.callbackfilter;

import java.lang.reflect.Method;

public interface InterceptorFilter {
	public int accept(Method method);
}
