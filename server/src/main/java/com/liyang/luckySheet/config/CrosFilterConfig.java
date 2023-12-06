package com.liyang.luckySheet.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * <h2>配置跨域过滤器</h2>
 * <p>
 *
 * </p>
 *
 * @author 作者<1 9 2 2 8 0 2 3 5 2 @ qq.com>
 * @since 2023年12月01日 12:28
 */
@Component
public class CrosFilterConfig {
	
	@Bean
	public FilterRegistrationBean corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		// 使用setAllowedOrigin会出现IllegalArgumentException
		config.addAllowedOriginPattern("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		source.registerCorsConfiguration("/**", config);
		// 使用FilterRegistrationBean包装CorsFilter
		FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
		// 设置Filter的优先级，值越小优先级越高
		bean.setOrder(0);
		return bean;
	}
}
