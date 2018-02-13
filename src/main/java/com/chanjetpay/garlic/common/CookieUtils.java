package com.chanjetpay.garlic.common;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {

	//cookie的有效期默认为30天
	public final static int COOKIE_MAX_AGE=60*60*24*30;
	public static final String COOKIE_USER_ID = "access_user_id";
	public static final String COOKIE_USER_TYPE = "access_user_type";

	public static final String COOKIE_OPERATOR_ID = "access_operator_id";

	/**
	 * 添加一个新Cookie
	 * @param response
	 * @param cookie
	 */
	public static void addCookie(HttpServletResponse response, Cookie cookie) {
		if (cookie != null) {
			response.addCookie(cookie);
		}
	}

	/**
	 * 添加一个新Cookie
	 * @param response
	 * @param cookieName
	 * @param cookieValue
	 * @param domain
	 * @param httpOnly
	 * @param maxAge
	 * @param path
	 * @param secure
	 */
	public static void addCookie(HttpServletResponse response, String cookieName, String cookieValue,
								 String domain, boolean httpOnly, int maxAge, String path, boolean secure) {
		if (cookieName != null && !cookieName.equals("")) {
			if (cookieValue == null)
				cookieValue = "";

			Cookie newCookie = new Cookie(cookieName, cookieValue);
			if (domain != null)
				newCookie.setDomain(domain);

			newCookie.setHttpOnly(httpOnly);

			if (maxAge > 0)
				newCookie.setMaxAge(maxAge);

			if (path == null)
				newCookie.setPath("/");
			else
				newCookie.setPath(path);

			newCookie.setSecure(secure);

			addCookie(response, newCookie);
		}
	}

	/**
	 * 添加一个新Cookie
	 * @param response
	 * @param cookieName
	 * @param cookieValue
	 * @param domain
	 */
	public static void addCookie(HttpServletResponse response,String cookieName,String cookieValue,String domain){
		addCookie(response, cookieName, cookieValue, domain, true, COOKIE_MAX_AGE, "/", false);
	}

	/**
	 * 根据Cookie名获取对应的Cookie
	 * @param request
	 * @param cookieName
	 * @return
	 */
	public static Cookie getCookie(HttpServletRequest request, String cookieName){
		Cookie[]cookies=request.getCookies();

		if(cookies==null||cookieName==null||cookieName.equals(""))
			return null;

		for(Cookie c:cookies){
			if(c.getName().equals(cookieName))
				return(Cookie)c;
		}
		return null;
	}

	/**
	 * 根据Cookie名获取对应的Cookie值
	 * @param request
	 * @param cookieName
	 * @return
	 */
	public static String getCookieValue(HttpServletRequest request, String cookieName) {
		Cookie cookie = getCookie(request, cookieName);
		if (cookie == null)
			return null;
		else
			return cookie.getValue();
	}

	/**
	 * 删除指定Cookie
	 * @param response
	 * @param cookie
	 */
	public static void delCookie(HttpServletResponse response, Cookie cookie) {
		if (cookie != null) {
			cookie.setPath("/");
			cookie.setMaxAge(0);
			cookie.setValue(null);

			response.addCookie(cookie);
		}
	}

	/**
	 * 根据cookie名删除指定的cookie
	 * @param request
	 * @param response
	 * @param cookieName
	 */
	public static void delCookie(HttpServletRequest request, HttpServletResponse response, String cookieName) {
		Cookie c = getCookie(request, cookieName);
		if (c != null && c.getName().equals(cookieName)) {
			delCookie(response, c);
		}
	}

	/**
	 * 根据cookie名修改指定的cookie
	 * @param request
	 * @param response
	 * @param cookieName
	 * @param cookieValue
	 * @param domain
	 */
	public static void editCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
								  String cookieValue, String domain) {
		Cookie c = getCookie(request, cookieName);
		if (c != null && cookieName != null && !cookieName.equals("") && c.getName().equals(cookieName)) {
			addCookie(response, cookieName, cookieValue, domain);
		}
	}
}
