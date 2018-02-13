package com.chanjetpay.garlic.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 用户登录，用户注销，会员注册，异常页面
 */
@Controller
@RequestMapping({"/",""})
public class IndexController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(RedirectAttributes redirectAttributes) {
		return "redirect:/vender/login/1234";
	}

	@RequestMapping(value = "/no-support", method = RequestMethod.GET)
	public String noSupport() {
		return "no-support";
	}

	//@RequestMapping(value = "/error", method = RequestMethod.GET)
	//public String error() {
	//	return "error";
	//}
}
