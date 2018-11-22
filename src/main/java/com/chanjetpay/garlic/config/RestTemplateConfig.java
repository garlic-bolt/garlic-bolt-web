package com.chanjetpay.garlic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class RestTemplateConfig {
	@Bean
	public RestTemplate restTemplate() {
		SimpleClientHttpRequestFactory httpRequestFactory = new SimpleClientHttpRequestFactory();
		httpRequestFactory.setReadTimeout(30000);
		httpRequestFactory.setConnectTimeout(5000);

		List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
		messageConverters.add(new ByteArrayHttpMessageConverter());
		/** 解决乱码的converter */
		StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter(Charset.forName
				("UTF-8"));
		messageConverters.add(stringHttpMessageConverter);
		messageConverters.add(new ResourceHttpMessageConverter());
		messageConverters.add(new SourceHttpMessageConverter());
		messageConverters.add(new AllEncompassingFormHttpMessageConverter());
		RestTemplate restTemplate = new RestTemplate(httpRequestFactory);
		restTemplate.setMessageConverters(messageConverters);

		return restTemplate;
	}
}
