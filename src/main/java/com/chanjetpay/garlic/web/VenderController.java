package com.chanjetpay.garlic.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("vender")
public class VenderController {

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(){
		return "ordering";
	}
}
