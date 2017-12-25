package com.chanjetpay;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = GarlicBoltWebApplication.class)
public class GarlicBoltWebApplicationTests {

	@Test
	public void testMd5() {
		String hashAlgorithmName = "MD5";
		String credentials = "123456";
		String salt = "xyz1";
		int hashIterations = 1024;
		SimpleHash obj = new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations);
		System.out.println(obj.toHex());
	}

	@Test
	public void test2(){
		String algorithmName = "md5";
		String username = "admin";
		String password = "123";
		String salt1 = "xyz1";
		int hashIterations = 2;

		SimpleHash hash = new SimpleHash(algorithmName, password, salt1, hashIterations);
		String encodedPassword = hash.toHex();
	}

}
