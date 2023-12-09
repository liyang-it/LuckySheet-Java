package com.liyang.luckySheet;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.LocalDateTime;

/**
 * <h2>启动类</h2>
 * <p>
 *
 * </p>
 *
 * @author 作者<1 9 2 2 8 0 2 3 5 2 @ qq.com>
 * @since 2023年12月01日 11:35
 */
@SpringBootApplication(scanBasePackages = "com.liyang.luckySheet")
@MapperScan(basePackages = "com.liyang.luckySheet.mapper", annotationClass = Mapper.class)
@EnableTransactionManagement
public class Application {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(Application.class);
	
	public static void main(String[] args) {
		
		LOGGER.info("=================  服务启动中...  =================");
		
		final ConfigurableApplicationContext applicationContext = SpringApplication.run(Application.class, args);
		
		final Environment environment = applicationContext.getBean(Environment.class);
		
		final String profilesActive = environment.getProperty("spring.profiles.active");
		
		// 使用的配置环境
		LOGGER.info("当前使用的配置环境: 【{}】", profilesActive);
		
		// 服务端口
		final String port = environment.getProperty("server.port");
		
		// 服务访问路径
		final String contextPath = environment.getProperty("server.servlet.context-path");
		
		// 是否开启 Druid监控
		String statViewServlet = environment.getProperty("spring.datasource.druid.stat-view-servlet.enabled");
		if (StringUtils.isBlank(statViewServlet)) {
			statViewServlet = "false";
		}
		LOGGER.info("是否开启 Druid 数据库 监控：【{}】", statViewServlet);
		
		if ("true".equals(statViewServlet)) {
			final String druidUserName = environment.getProperty("spring.datasource.druid.stat-view-servlet.login-username");
			final String druidPassword = environment.getProperty("spring.datasource.druid.stat-view-servlet.login-username");
			LOGGER.info("Druid访问路径：【{}druid/login.html】", contextPath);
			LOGGER.info("Druid 登录用户名：【{}】", druidUserName);
			LOGGER.info("Druid 登录密码：【{}】", druidPassword);
		}
		LOGGER.info("服务访问路径：【{}】", contextPath);
		LOGGER.info("服务访问端口：【{}】", port);
		LOGGER.info("服务启动时间：【{}】", LocalDateTime.now());
		LOGGER.info("=================  服务启动成功  =================");
	}
}
