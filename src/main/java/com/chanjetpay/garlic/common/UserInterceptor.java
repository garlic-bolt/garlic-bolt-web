package com.chanjetpay.garlic.common;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

public class UserInterceptor implements HandlerInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(UserInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {

		String blockCode = RequestAgentTool.getBlockByUri(request.getRequestURI());

		if (StringUtils.isEmpty(CookieUtils.getCookieValue(request, CookieUtils.COOKIE_USER_ID))) { //cookie中无用户为访问记录，跳转到oauth2地址
			if (RequestAgentTool.isWeChat(request)) { //微信内置浏览器
				CookieUtils.addCookie(response, CookieUtils.COOKIE_USER_TYPE, "weixin", ResourceBundleUtils.getDomain());
				//跳转到获取微信open id的url
				String redirectUrl = "/official-web/" + blockCode + "/wx-oauth2-code?fromUri=" + URLEncoder.encode(request.getRequestURI(), "utf-8");
				response.sendRedirect(redirectUrl);
				return false;
			} else if (RequestAgentTool.isAlipay(request)) { //支付宝内置浏览器
				CookieUtils.addCookie(response, CookieUtils.COOKIE_USER_TYPE, "alipay", ResourceBundleUtils.getDomain());
				//跳转到获取支付宝user id的url

				response.sendRedirect("/official-web/" + blockCode + "/alipay-oauth2-code?fromUri=" + URLEncoder.encode(request.getRequestURI(), "utf-8"));
				return false;
			} else { //其他浏览器
				CookieUtils.addCookie(response, CookieUtils.COOKIE_USER_TYPE, "other", ResourceBundleUtils.getDomain());
				CookieUtils.addCookie(response, CookieUtils.COOKIE_USER_ID, request.getSession().getId(), ResourceBundleUtils.getDomain());
				return true;
			}
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
