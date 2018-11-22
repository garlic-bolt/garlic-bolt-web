package com.chanjetpay.garlic.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("get")
public class SimpleRestController {
	private static final Logger logger = LoggerFactory.getLogger(SimpleRestController.class);

	/**
	 * 不带参数的get请求
	 * @return
	 */
	@RequestMapping(value = "/get01", method = RequestMethod.GET)
	public String get01() {
		String result = "【get01】连接模拟的get服务端成功";
		logger.info(result);
		return result;
	}

	/**
	 * 带有请求参数的get请求
	 * 笔记：
	 *  1 get请求参数类型：
	 *      》 url中？后面的请求参数，格式以 key=value 的形式传递；后台需要用@RequestParam注解
	 *          如果前端的 key 和 后台方法的参数名称一致时可以不用@RequestParam注解【因为@RequestParam注解时默认的参数注解】
	 *      》 url中的路径参数
	 *          需要配合@RequestMapping和@PathVariable一起使用
	 * @param username 请求参数
	 * @return
	 */
	@RequestMapping(value = "/get02", method = {RequestMethod.GET, RequestMethod.POST})
	public String get02(@RequestParam(value = "name", required = false, defaultValue = "王杨帅") String username) {
		String result = "【get02】获取到的请求参数为：name = " + username;
		logger.info(result);
		return result;
	}

	/**
	 * 带有路径参数的get请求
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/get03/{id}", method = RequestMethod.GET)
	public String get03(@PathVariable(value = "id") Integer userId) {
		String result = "【get03】获取到的路径参数为：userId = " + userId;
		logger.info(result);
		return result;
	}

	/**
	 * 既有路径参数又有请求参数的get请求
	 * 笔记：
	 *  1 @PathVariable和@RequestParam都可以设定是否必传【默认必传】
	 *  2 @PathVariable不可以设定默认值，@RequestParam可以设定默认值【默认值就是不传入的时候代替的值】
	 *  3 @PathVariable如果设置必传为true，前端不传入时就会报错【技巧：开启必传】
	 *  4 @RequestParam如果设置必传为true，前端不传入还是也会报错【技巧：关闭必传，开启默认值】
	 *  5 @PathVariable可以设置正则表达式【详情参见：https://www.cnblogs.com/NeverCtrl-C/p/8185576.html】
	 * @param userId
	 * @param username
	 * @return
	 */
	@RequestMapping(value = "/get04/{id}", method = RequestMethod.GET)
	public String get04(@PathVariable(value = "id") Integer userId, @RequestParam(value = "name", required = false, defaultValue = "王杨帅") String username
	) {
		String result = "【get04】获取到的路径参数为：userId = " + userId + " 获取到的请求参数为：" + username;
		logger.info(result);
		return result;
	}
}
