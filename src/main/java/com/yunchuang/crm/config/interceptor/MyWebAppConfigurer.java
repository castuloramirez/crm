package com.yunchuang.crm.config.interceptor;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author 尹冬飞
 * @create 2017-05-03 9:02
 */
//@Configuration
public class MyWebAppConfigurer extends WebMvcConfigurerAdapter {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LogInterceptor()).addPathPatterns("/**");
		super.addInterceptors(registry);
	}
}
