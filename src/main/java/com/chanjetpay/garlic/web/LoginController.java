package com.chanjetpay.garlic.web;

import com.chanjetpay.garlic.form.LoginForm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@RequestMapping({"/",""})
	public String home(){
		return "redirect:index";
	}

	@RequestMapping("/index")
	public String index(Model model){
		model.addAttribute("link1","http://www.baidu.com");
		return "home";
	}

	/**
	 * shiro框架登录
	 */
	@RequestMapping(value = "/login", method = {RequestMethod.POST, RequestMethod.GET})
	public String login(HttpServletRequest request, HttpSession session, @ModelAttribute LoginForm loginForm, RedirectAttributes redirectAttributes){

		if("GET".equals(request.getMethod())){
			return "login";
		}

		logger.info("session id:" + session.getId());

		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(loginForm.getLoginName(), loginForm.getPassword());
		try {
			//执行认证操作.
			subject.login(token);
		}catch (AuthenticationException e) {
			logger.info("login:" + e);
			redirectAttributes.addFlashAttribute("message", e.getMessage());
			return "redirect:/login";
		}

		return "redirect:/index";
	}

	@RequestMapping(value = "/logout",method= RequestMethod.GET)
	public String logout(){
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return "redirect:/index";
	}

	@RequestMapping(value = "/error",method= RequestMethod.GET)
	public String error(){
		return "error";
	}
}
