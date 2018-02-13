package com.chanjetpay.garlic.config;

import com.chanjetpay.garlic.common.OperatorInterceptor;
import com.chanjetpay.garlic.common.UserInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 多个拦截器组成一个拦截器链
		// addPathPatterns 用于添加拦截规则
		// excludePathPatterns 用户排除拦截
		registry.addInterceptor(new UserInterceptor())
				.addPathPatterns("/member/**");

		registry.addInterceptor(new OperatorInterceptor())
				.addPathPatterns("/vender/**")
				.excludePathPatterns("/vender/login/**")
				.excludePathPatterns("/vender/logout");

		super.addInterceptors(registry);
	}
}
