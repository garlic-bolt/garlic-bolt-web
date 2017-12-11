package com.chanjetpay.garlic.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by libaoa on 2017/11/8.
 */
@Controller
public class GarlicWebController {

	@RequestMapping({"/",""})
	public String index(Model model){
		model.addAttribute("link1","http://www.baidu.com");
		return "index";
	}
}
