package com.dmm.middleware.ratelimiter;

import com.dmm.middleware.ratelimiter.annotation.DoRateLimiter;
import com.dmm.middleware.ratelimiter.value.IValueService;
import com.dmm.middleware.ratelimiter.value.impl.RateLimiterValue;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * @author Mean
 * @date 2025/2/17 14:27
 * @description 切面逻辑
 */
@Aspect
public class DoRateLimiterPoint {

	@Pointcut("@annotation(com.dmm.middleware.ratelimiter.annotation.DoRateLimiter)")
	public void aopPoint() {}

	@Around("aopPoint() && @annotation(doRateLimiter)")
	public Object doRouter(ProceedingJoinPoint joinPoint, DoRateLimiter doRateLimiter) throws Throwable {
		IValueService valueService = new RateLimiterValue();
		return valueService.access(joinPoint, getMethod(joinPoint), doRateLimiter, joinPoint.getArgs());
	}

	private Method getMethod(ProceedingJoinPoint jp) throws NoSuchMethodException {
		Signature sig = jp.getSignature();
		MethodSignature methodSignature = (MethodSignature) sig;
		return jp.getTarget().getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
	}

}
