package com.liyang.luckySheet.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Spring获取bean工具类
 */
@Component
public class SpringUtils implements ApplicationContextAware {
	
	private static ApplicationContext applicationContext;
	
	/**
	 * 根据类获取Bean
	 *
	 * @param clazz 例如 String.class
	 */
	public static <T> T getBean(Class<T> clazz) {
		return applicationContext.getBean(clazz);
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		
		SpringUtils.applicationContext = applicationContext;
		System.out.println("注入 ApplicationContext: " + SpringUtils.applicationContext);
	}
	
}
