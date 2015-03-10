package com.yuekuapp.proxy.callbackfilter;

import java.lang.reflect.Method;

public class AsynMethodFilter implements InterceptorFilter {

	@Override
	public int accept(Method method) {
		return 0;
	}

}
