package com.chanjetpay;

import com.chanjetpay.garlic.api.OfficialService;
import com.chanjetpay.garlic.api.OperatorService;
import com.chanjetpay.garlic.dto.OperatorDto;
import com.chanjetpay.garlic.dto.WxOauth2Dto;
import com.chanjetpay.garlic.dto.WxOfficialSignDto;
import com.chanjetpay.result.BasicResult;
import com.chanjetpay.result.GenericResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.expression.spel.ast.Operator;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GarlicBoltWebApplication.class)
public class OperatorControllerTest {

	@Autowired
	private OperatorService operatorService;

	@Test
	public void testCreateOperator(){
		OperatorDto operatorDto = new OperatorDto();
		operatorDto.setBlockCode("1234");
		operatorDto.setOperatorId("zhangsan");
		operatorDto.setPassword("zhangsan123");
		operatorDto.setSalt("abcd");

		operatorService.create("1234",operatorDto);
	}

	@Test
	public void testResetPasswd(){
		OperatorDto operatorDto = new OperatorDto();
		operatorDto.setOperatorId("10101");
		operatorDto.setPassword("123");
		BasicResult result = operatorService.reset("101", operatorDto);

		System.out.println(result);

	}

}
