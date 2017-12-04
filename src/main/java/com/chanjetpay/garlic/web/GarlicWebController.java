package com.chanjetpay.garlic.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by libaoa on 2017/11/8.
 */
@Controller
public class GarlicWebController {

	@RequestMapping({"/",""})
	public String index(){
		return "index1";
	}
}
