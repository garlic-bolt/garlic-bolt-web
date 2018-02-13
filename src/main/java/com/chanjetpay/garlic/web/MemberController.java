package com.chanjetpay.garlic.web;

import com.chanjetpay.garlic.common.CookieUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("member")
public class MemberController {

	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

	@RequestMapping(value = "/{blockCode}/home")
	public ModelAndView home(@PathVariable("blockCode") String blockCode, HttpServletRequest request) {
		String userId = CookieUtils.getCookieValue(request,CookieUtils.COOKIE_USER_ID);
		logger.info("home userid - {}", userId);

		return new ModelAndView("home");
	}


	@RequestMapping(value = "/{blockCode}/guide")
	public ModelAndView guide(@PathVariable("blockCode") String blockCode, HttpServletRequest request) {
		String userId = CookieUtils.getCookieValue(request,CookieUtils.COOKIE_USER_ID);
		logger.info("guide userid - {}", userId);

		return new ModelAndView("guide");
	}
}
