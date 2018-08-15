package com.chanjetpay;

import com.chanjetpay.garlic.api.MemberService;
import com.chanjetpay.garlic.dto.MemberDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GarlicBoltWebApplication.class)
public class LoginTest {

	@Autowired
	private MemberService memberService;

	@Test
	public void testVerify(){
		MemberDto memberDto = new MemberDto("aa","123456");
		memberService.verifyPassword("100",memberDto);
	}
}
