package com.chanjetpay.garlic.config;

import com.chanjetpay.garlic.api.*;
import feign.*;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource({"classpath:feign.properties"})
public class FeginConfig {

	private static final Logger log = LoggerFactory.getLogger(FeginConfig.class);

	@Value("${feign.url}")
	private String feignUrl;

	@Value("${feign.token}")
	private String feignToken;

	@Bean(name = "merchantService")
	public MerchantService merchantService() {
		log.info("merchantService started feignUrl: " + feignUrl);
		return Feign.builder()
				.encoder(new JacksonEncoder())
				.decoder(new JacksonDecoder())
				.options(new Request.Options(1000, 3500))
				.retryer(new Retryer.Default(5000, 5000, 3))
				.target(MerchantService.class, feignUrl);
	}

	@Bean(name = "userInfoService")
	public UserService userInfoService() {
		log.info("userInfoService started feignUrl: " + feignUrl);
		return Feign.builder()
				.encoder(new JacksonEncoder())
				.decoder(new JacksonDecoder())
				.options(new Request.Options(1000, 3500))
				.retryer(new Retryer.Default(5000, 5000, 3))
				.requestInterceptor(requestInterceptor)
				.target(UserService.class, feignUrl);
	}

	@Bean(name = "officialService")
	public OfficialService officialService() {
		log.info("officialService started feignUrl: " + feignUrl);
		return Feign.builder()
				.encoder(new JacksonEncoder())
				.decoder(new JacksonDecoder())
				.options(new Request.Options(1000, 3500))
				.retryer(new Retryer.Default(5000, 5000, 3))
				.requestInterceptor(requestInterceptor)
				.target(OfficialService.class, feignUrl);
	}

	@Bean(name = "memberService")
	public MemberService memberService() {
		log.info("memberService started feignUrl: " + feignUrl);
		return Feign.builder()
				.encoder(new JacksonEncoder())
				.decoder(new JacksonDecoder())
				.options(new Request.Options(1000, 3500))
				.retryer(new Retryer.Default(5000, 5000, 3))
				.requestInterceptor(requestInterceptor)
				.target(MemberService.class, feignUrl);
	}

	private RequestInterceptor requestInterceptor = new RequestInterceptor() {
		@Override
		public void apply(RequestTemplate requestTemplate) {
			requestTemplate.header("token", feignToken);
		}
	};
}