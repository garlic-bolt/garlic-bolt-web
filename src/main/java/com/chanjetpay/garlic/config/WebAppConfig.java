package com.chanjetpay.garlic.config;

import com.chanjetpay.garlic.common.OperatorInterceptor;
import com.chanjetpay.garlic.common.UserInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {

	@Bean
	public UserInterceptor userInterceptor(){
		return new UserInterceptor();
	}

	@Bean
	public OperatorInterceptor operatorInterceptor(){
		return new OperatorInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 多个拦截器组成一个拦截器链
		// addPathPatterns 用于添加拦截规则
		// excludePathPatterns 用户排除拦截
		registry.addInterceptor(userInterceptor())
				.addPathPatterns("/member/**");

		registry.addInterceptor(operatorInterceptor())
				.addPathPatterns("/vender/**")
				.excludePathPatterns("/vender/login/**")
				.excludePathPatterns("/vender/logout");

		super.addInterceptors(registry);
	}
}
