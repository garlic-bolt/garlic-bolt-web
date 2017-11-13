package com.chanjetpay.garlic.web;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by libaoa on 2017/11/9.
 */
@RestController
public class EnrollController extends AbstractJsonpResponseBodyAdvice {

	public EnrollController(){
		super("callback","callback");
	}

	@ResponseBody
	@RequestMapping(value = "/reg",method = RequestMethod.POST,consumes="application/json")
	public Map<String, Object> reg(@RequestParam("token") String token, @RequestBody UserDto user){
		System.out.println(token + user.getName() + user.getEmail());

		Map<String, Object> result = new HashMap<>();
		result.put("code","success");
		return result;
	}
}
