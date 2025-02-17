package com.dmm.middleware.ratelimiter.config;

import com.dmm.middleware.ratelimiter.DoRateLimiterPoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Mean
 * @date 2025/2/17 15:03
 * @description RateLimiterAutoConfigure
 */
@Configuration
public class RateLimiterAutoConfigure {

	@Bean
	@ConditionalOnMissingBean
	public DoRateLimiterPoint point() {
		return new DoRateLimiterPoint();
	}

}
