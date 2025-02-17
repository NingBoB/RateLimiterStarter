package com.dmm.middleware.ratelimiter;

import com.google.common.util.concurrent.RateLimiter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mean
 * @date 2025/2/17 14:34
 * @description Constants
 */
public class Constants {
	public static Map<String , RateLimiter> rateLimiterMap = Collections.synchronizedMap(new HashMap<String, RateLimiter>());
}
