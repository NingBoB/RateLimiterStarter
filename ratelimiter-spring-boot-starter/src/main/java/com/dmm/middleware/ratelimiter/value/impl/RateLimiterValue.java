package com.dmm.middleware.ratelimiter.value.impl;

import com.alibaba.fastjson.JSON;
import com.dmm.middleware.ratelimiter.Constants;
import com.dmm.middleware.ratelimiter.annotation.DoRateLimiter;
import com.dmm.middleware.ratelimiter.value.IValueService;
import com.google.common.util.concurrent.RateLimiter;
import org.aspectj.lang.ProceedingJoinPoint;

import java.lang.reflect.Method;

/**
 * @author Mean
 * @date 2025/2/17 14:26
 * @description RateLimiterValue
 */
public class RateLimiterValue implements IValueService {
	@Override
	public Object access(ProceedingJoinPoint jp, Method method, DoRateLimiter doRateLimiter, Object[] args) throws Throwable {
		System.out.println("RateLimiterValue");
		// 判断是否开启, 限速0表示不进行限速
		if (0 == doRateLimiter.permitsPerSecond()) return jp.proceed();

		String clazzName = jp.getTarget().getClass().getName();
		String methodName = method.getName();

		String key = clazzName + "." + methodName;

		if (null == Constants.rateLimiterMap.get(key)) {
			Constants.rateLimiterMap.put(key, RateLimiter.create(doRateLimiter.permitsPerSecond()));
		}

		RateLimiter rateLimiter = Constants.rateLimiterMap.get(key);
		// 运行程序前先获取令牌，若成功获取，则可以接着运行
		if (rateLimiter.tryAcquire()) {
			return jp.proceed();
		}

		return JSON.parseObject(doRateLimiter.returnJson(), method.getReturnType());
	}
}
