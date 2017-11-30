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
public class GarlicWebController {

	@RequestMapping({"/",""})
	public String index(){
		return "index";
	}
}
