package com.dmm.middleware.ratelimiter.value;

import com.dmm.middleware.ratelimiter.annotation.DoRateLimiter;
import org.aspectj.lang.ProceedingJoinPoint;

import java.lang.reflect.Method;

/**
 * @author Mean
 * @date 2025/2/17 14:26
 * @description IValueService
 */
public interface IValueService {
	Object access(ProceedingJoinPoint jp, Method method, DoRateLimiter doRateLimiter, Object[] args) throws Throwable;
}
