package com.chanjetpay.garlic.web;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.WebAsyncTask;

import java.util.concurrent.Callable;

@RestController
@RequestMapping("comet")
public class CometController {

	private final Object BUFFER_LOCK = new Object();


	@RequestMapping("/{testId}/test2")
	public WebAsyncTask<String> test2(@PathVariable final String testId) {
		WebAsyncTask<String> webAsyncTask = new WebAsyncTask<String>(3000,new Callable<String>() {
			@Override
			public String call() throws Exception {

				synchronized (BUFFER_LOCK) {
					BUFFER_LOCK.wait();
					return "测试2 - " + testId + System.currentTimeMillis();
				}
			}
		});

		//客户端超时失败或服务端成功返回数据都执行
		//webAsyncTask.onCompletion(new Runnable() {
		//	@Override
		//	public void run() {
		//		System.out.println("调用完成");
		//	}
		//});

		//服务端超时时执行
		//webAsyncTask.onTimeout(new Callable<String>() {
		//	@Override
		//	public String call() throws Exception {
		//		System.out.println("业务处理超时");
		//		return "调用超时了。。。";
		//	}
		//});

		return webAsyncTask;
	}

	@RequestMapping(value = "/{testId}/test", method = RequestMethod.GET)
	public Callable<String> asyncHold(@PathVariable final String testId) {
		System.out.println(Thread.currentThread().getId() + "请求进来");
		// 这么做的好处避免web server的连接池被长期占用而引起性能问题，
		// 调用后生成一个非web的服务线程来处理，增加web服务器的吞吐量。
		return new Callable<String>() {
			@Override
			public String call() throws Exception {
				synchronized (BUFFER_LOCK) {

					try {
						BUFFER_LOCK.wait(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					return "测试1 - " + testId + System.currentTimeMillis();
				}
			}
		};
	}

	@RequestMapping(value = "/{testId}/release", method = RequestMethod.GET)
	public @ResponseBody String asyncRelease(@PathVariable final String testId){
		synchronized (BUFFER_LOCK) {
			BUFFER_LOCK.notifyAll();
			return "" + System.currentTimeMillis();
		}
	}
}
