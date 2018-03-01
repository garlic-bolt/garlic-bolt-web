package com.chanjetpay;

import com.chanjetpay.garlic.api.OfficialService;
import com.chanjetpay.garlic.api.OperatorService;
import com.chanjetpay.garlic.dto.OperatorDto;
import com.chanjetpay.garlic.dto.WxOauth2Dto;
import com.chanjetpay.garlic.dto.WxOfficialSignDto;
import com.chanjetpay.result.GenericResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GarlicBoltWebApplication.class)
public class OfficialServiceFeignTest {

	@Autowired
	private OfficialService officialService;


	private static final Logger logger = LoggerFactory.getLogger(OfficialServiceFeignTest.class);

	@Test
	public void testWxOfficialSign(){

		WxOfficialSignDto wxOfficialSignDto = new WxOfficialSignDto("1234","badc");
		GenericResult<String> result = officialService.wxOfficialSign("1234",wxOfficialSignDto);

		logger.info("" + result);
	}

	@Test
	public void testWxOfficialCode(){
		WxOauth2Dto wxOauth2 = new WxOauth2Dto("1234","abcd");
		GenericResult<WxOauth2Dto> result = officialService.wxOfficialCode("1234",wxOauth2);
		logger.info("" + result);
	}


	@Autowired
	private OperatorService operatorService;

	@Test
	public void testCreateOperator(){
		OperatorDto operatorDto = new OperatorDto();
		operatorDto.setBlockId("1234");
		operatorDto.setOperatorId("zhangsan");
		operatorDto.setPassword("zhangsan123");
		operatorDto.setSalt("abcd");

		operatorService.create("1234",operatorDto);
	}

}
