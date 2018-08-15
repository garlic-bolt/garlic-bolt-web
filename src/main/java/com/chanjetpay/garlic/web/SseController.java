package com.chanjetpay.garlic.web;

import com.chanjetpay.garlic.config.PayCompletedEvent;
import com.chanjetpay.garlic.config.PayCompletedListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/sse")
public class SseController {

	@Autowired
	ApplicationContext applicationContext;
	@Autowired
	PayCompletedListener payCompletedListener;

	private static final Logger logger = LoggerFactory.getLogger(SseController.class);

	@RequestMapping(value = "/check-state", method = {RequestMethod.POST, RequestMethod.GET})
	public SseEmitter push(@RequestParam Long payRecordId){
		final SseEmitter emitter = new SseEmitter();
		try {
			payCompletedListener.addSseEmitters(payRecordId,emitter);
		}catch (Exception e){
			emitter.completeWithError(e);
		}

		return emitter;
	}

	@RequestMapping(value = "/pay-callback", method = {RequestMethod.POST, RequestMethod.GET})
	public String payCallback(@RequestParam Long payRecordId){
		applicationContext.publishEvent(new PayCompletedEvent(null,payRecordId));
		return "请到监听处查看消息";
	}


	@RequestMapping("/testCallable")
	public Callable<String> testCallable() {
		logger.info("Controller开始执行！");
		Callable<String> callable = new Callable<String>() {
			@Override
			public String call() throws Exception {
				Thread.sleep(5000);

				logger.info("实际工作执行完成！");

				return "succeed!";
			}
		};

		logger.info("Controller执行结束！");
		return callable;
	}


	private static final ConcurrentHashMap<String, DeferredResult<String>> deferredMap = new ConcurrentHashMap<>();

	//private DeferredResult<String> deferredResult = new DeferredResult<>(10000l);

	/**
	 * 返回DeferredResult对象
	 *
	 * @return
	 */
	@RequestMapping("/testDeferredResult/{id}")
	public DeferredResult<String> testDeferredResult(@PathVariable final String id) {
		DeferredResult<String> deferredResult = new DeferredResult<>(10000l);

		if(!deferredMap.containsKey(id)) {

			deferredResult.onCompletion(new Runnable() {
				@Override
				public void run() {
					if(deferredMap.containsKey(id)){
						deferredMap.remove(id);
						logger.info("complete deferred:" + id);
					}

					logger.info("complete");
				}
			});

			deferredMap.put(id, deferredResult);
			logger.info("put deferred:" + id);

			return deferredResult;
		}


		return null;
	}

	/**
	 * 对DeferredResult的结果进行设置
	 * @return
	 */
	@RequestMapping("/setDeferredResult/{id}")
	public String setDeferredResult(@PathVariable final String id) {

		if(deferredMap.containsKey(id)){
			deferredMap.get(id).setResult(id + "test result");
			logger.info("set deferred:" + id);
		}

		logger.info("deferred size:" + deferredMap.size());

		return "succeed";
	}

	@RequestMapping("/download")
	public StreamingResponseBody handle() {
		return new StreamingResponseBody() {
			@Override
			public void writeTo(OutputStream outputStream) throws IOException {
				// write...
			}
		};
	}
}
