package com.chanjetpay.garlic.web;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.WebAsyncTask;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("comet")
public class CometController {

	private static final Object BUFFER_LOCK = new Object();

	private static final ConcurrentHashMap<String, Object> LOCK_OBJECT_MAP = new ConcurrentHashMap<>();

	@RequestMapping("/{userId}/test2/{tamp}")
	public WebAsyncTask<String> test2(@PathVariable final String userId, @PathVariable final String tamp) {
		WebAsyncTask<String> webAsyncTask = new WebAsyncTask<String>(10000l,new Callable<String>() {
			@Override
			public String call() throws Exception {

				LOCK_OBJECT_MAP.put(userId + tamp, new Object());

				System.out.println("test2 request size:"  + LOCK_OBJECT_MAP.size());

				synchronized (LOCK_OBJECT_MAP.get(userId + tamp)) {
					LOCK_OBJECT_MAP.get(userId + tamp).wait();
					System.out.println("test2 接收通知");
					return "测试2 - " + userId + System.currentTimeMillis();
				}
			}
		});

		//客户端超时失败或服务端成功返回数据都执行
		webAsyncTask.onCompletion(new Runnable() {
			@Override
			public void run() {
				LOCK_OBJECT_MAP.remove(userId + tamp);
				System.out.println("test2 finish size:"  + LOCK_OBJECT_MAP.size());
			}
		});

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

	@RequestMapping(value = "/{userId}/release", method = RequestMethod.GET)
	public @ResponseBody String asyncRelease(@PathVariable final String userId){
		for(String key : LOCK_OBJECT_MAP.keySet()){
			if(key.startsWith(userId)){
				synchronized (LOCK_OBJECT_MAP.get(key)) {
					LOCK_OBJECT_MAP.get(key).notifyAll();
				}
			}
		}
		return "" + System.currentTimeMillis();
	}
}
