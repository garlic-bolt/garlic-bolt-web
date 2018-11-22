package com.chanjetpay;


import org.codehaus.groovy.runtime.powerassert.SourceText;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GarlicBoltWebApplication.class)
@WebAppConfiguration
public class SimpleRestControllerTest {

	private static final Logger log = LoggerFactory.getLogger(SimpleRestControllerTest.class);

	@Resource
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		log.info("set mockmvc");
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		// mockMvc = MockMvcBuilders.standaloneSetup(webApplicationContext).build();
	}

	@Test
	public void query() throws Exception {
		mockMvc.perform(get("/get/get01"))
				.andExpect(status().isOk())
				.andExpect(content().string("【get01】连接模拟的get服务端成功"));
	}

	@Test
	public void test() throws ParseException {
		Date dae = new Date();
		System.out.println(dae.getTime());//js getTime 1534915014120
											//java getTime 1534915797187
		System.out.println(dae);

		long times = 1534915014120L;
		Date date = new Date(times);
		System.out.println(date);

		System.out.println(1534915014120L/1000/60);

	}

	@Autowired
	private RestTemplate restTemplate;

	@Test
	public void testDemo() throws Exception {
/*		String url = "http://localhost:8080/json";
		JSONObject json = restTemplate.getForEntity(url, JSONObject.class).getBody();
		return json.toJSONString();*/
	}

	/**
	 * 请求无参数的get请求
	 * @return
	 */
	@Test
	public void get01() throws Exception {
		String forObject = restTemplate.getForObject(
				"http://127.0.0.1:8082/get/get01",
				String.class
		);
		log.info("响应数据为：" + forObject);
	}

	/**
	 * 请求有请求参数的get请求：利用占位符进行请求参数传递
	 * @return
	 */
	@Test
	public void get0201() throws Exception {
		String forObject = restTemplate.getForObject(
				"http://127.0.0.1:8082/get/get02?name={1}",
				String.class,
				"三少"
		);
		log.info("响应数据为：" + forObject);
	}

	@Test
	public void post0201() throws Exception {
		String forObject = restTemplate.postForObject("http://127.0.0.1:8082/get/get02?name={1}",null,String.class,"三少");
		log.info("响应数据为：" + forObject);
	}

	/**
	 * 请求有请求参数的get请求：利用Map进行请求参数传递
	 * @return
	 */
	@Test
	public void get0202() throws Exception {
		Map<String, Object> params = new HashMap<>();
		params.put("name", "四少");
		String forObject = restTemplate.getForObject(
				"http://127.0.0.1:8082/get/get02?name={name}",
				String.class,
				params
		);
		log.info("响应数据为：" + forObject);
	}

	/**
	 * 请求有路径参数的get请求：
	 * @return
	 */
	@Test
	public void get03() throws Exception {
		String forObject = restTemplate.getForObject(
				"http://127.0.0.1:8082/get/get03/88888888",
				String.class);
		log.info("响应数据为：" + forObject);
	}

	/**
	 * 请求既有路径参数又有请求参数逇get请求
	 * @return
	 */
	@Test
	public void get04() throws Exception {
		String forObject = restTemplate.getForObject(
				"http://127.0.0.1:8082/get/get04/99999?name={1}",
				String.class,
				"fury"
		);
		log.info("响应数据为：" + forObject);
	}

	/**
	 * 请求无参数的get请求
	 * @return
	 */
	@Test
	public void get01_e() throws Exception {
		ResponseEntity<String> forObject = restTemplate.getForEntity(
				"http://127.0.0.1:8082/get/get01",
				String.class
		);
		log.info("状态码：" + forObject.getStatusCode());
		log.info("状态值：" + forObject.getStatusCodeValue());
		log.info("响应数据为：" + forObject);
	}

	/**
	 * 请求有请求参数的get请求：利用占位符进行请求参数传递
	 * @return
	 */
	@Test
	public void get0201_e() throws Exception {
		ResponseEntity<String> forObject = restTemplate.getForEntity(
				"http://127.0.0.1:8082/get/get02?name={1}",
				String.class,
				"三少"
		);
		log.info("响应数据为：" + forObject);
	}

	/**
	 * 请求有请求参数的get请求：利用Map进行请求参数传递
	 * @return
	 */
	@Test
	public void get0202_e() throws Exception {
		Map<String, Object> params = new HashMap<>();
		params.put("name", "warrior");
		ResponseEntity<String> forObject = restTemplate.getForEntity(
				"http://127.0.0.1:8082/get/get02?name={name}",
				String.class,
				(Map<String, ?>) params);
		log.info("响应数据为：" + forObject);
	}

	/**
	 * 请求有路径参数的get请求：
	 * @return
	 */
	@Test
	public void get03_e() throws Exception {
		ResponseEntity<String> forObject = restTemplate.getForEntity(
				"http://127.0.0.1:8082/get/get03/888888",
				String.class
		);
		log.info("响应数据为：" + forObject);
	}

	@Test
	public void get04_e() throws Exception {
	}

	/**
	 * 请求既有路径参数又有请求参数逇get请求
	 * @return
	 */
	@Test
	public void test01() throws Exception {
		ResponseEntity<String> forObject = restTemplate.getForEntity(
				"http://127.0.0.1:8082/get/get04/99999?name={1}",
				String.class,
				"fury"
		);
		log.info("响应数据为：" + forObject);
	}
}
