package com.chanjetpay.garlic.web;

import com.chanjetpay.garlic.common.CookieUtils;
import com.chanjetpay.garlic.common.OperatorLogin;
import com.chanjetpay.garlic.common.ResourceBundleUtils;
import com.chanjetpay.garlic.form.LoginForm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("vender")
public class VenderController {

	private static final Logger logger = LoggerFactory.getLogger(VenderController.class);

	@RequestMapping(value = "/login/{blockCode}", method = {RequestMethod.POST, RequestMethod.GET})
	public String login(@PathVariable("blockCode") String blockCode, @ModelAttribute LoginForm loginForm ,Model model,
						HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {

		if("GET".equals(request.getMethod())){
			return "login";
		}


		//验证用户名和密码
		if(!loginForm.getLoginName().equals("zhangsan")){
			//认证不通过
			model.addAttribute("message","用户登录失败，请检查密码");
			return "login";
		}

		//记录登录信息到cookie
		ObjectMapper mapper = new ObjectMapper();
		mapper.setDateFormat(new SimpleDateFormat("yyyyMMddHHmmSS"));

		OperatorLogin operatorLogin = new OperatorLogin();
		operatorLogin.setBlockCode(blockCode);
		operatorLogin.setLogonTime(new Date());
		operatorLogin.setExpiresIn(CookieUtils.COOKIE_MAX_AGE);
		operatorLogin.setOperatorId("zhangsan");
		operatorLogin.setOperatorName("啊啊啊啊");

		String operatorLoginStr = mapper.writeValueAsString(operatorLogin);
		CookieUtils.addCookie(response, CookieUtils.COOKIE_OPERATOR_ID, operatorLoginStr, ResourceBundleUtils.getDomain());

		return "redirect:/vender/" + blockCode + "/home";
	}

	@RequestMapping(value = "/{blockCode}/home", method = RequestMethod.GET)
	public String home(){
		return "ordering";
	}

	@RequestMapping(value = "/logout",method= RequestMethod.GET)
	public String logout(){
		//清楚session
		return "redirect:/index";
	}
}
