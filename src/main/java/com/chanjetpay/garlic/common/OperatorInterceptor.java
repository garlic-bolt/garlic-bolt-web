package com.chanjetpay.garlic.common;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import sun.nio.cs.FastCharsetProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OperatorInterceptor implements HandlerInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(OperatorInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {

		String blockCode = RequestAgentTool.getBlockByUri(request.getRequestURI());

		//如果没有登录则调到登录页
		String loginInfo = CookieUtils.getCookieValue(request,CookieUtils.COOKIE_OPERATOR_ID);
		if(StringUtils.isEmpty(loginInfo)){
			logger.info("操作员未登录");
			response.sendRedirect("/vender/" + blockCode + "/login");
			return false;
		}

		ObjectMapper mapper = new ObjectMapper();
		mapper.setDateFormat(new SimpleDateFormat("yyyyMMddHHmmSS"));
		OperatorLogin operatorLogin = mapper.readValue(loginInfo, OperatorLogin.class);
		Long diffTime = (new Date()).getTime() - operatorLogin.getLogonTime().getTime();

		if(diffTime > operatorLogin.getExpiresIn()){
			logger.info("操作员登录过期");
			response.sendRedirect("/vender/" + blockCode + "/login");
			return false;
		}

		//只有返回true才会继续向下执行，返回false取消当前请求
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

	}
}
