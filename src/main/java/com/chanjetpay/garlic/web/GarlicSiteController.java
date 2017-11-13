package com.chanjetpay.garlic.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;
import java.util.UUID;

/**
 * Created by libaoa on 2017/11/8.
 */
@Controller
public class GarlicSiteController {

	@RequestMapping({"/",""})
	public String index(){
		return "index";
	}

	@RequestMapping("/about")
	public String about(Model model){
		model.addAttribute("title","关于蒜苔网");
		model.addAttribute("token", UUID.randomUUID());
		return "about";
	}

	@RequestMapping("/feedback")
	public String feedback(){
		return "feedback";
	}

	@RequestMapping("/tellme")
	public String tellme(){
		return "tellme";
	}
}
