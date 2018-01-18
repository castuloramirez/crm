package com.yunchuang.crm;

import com.yunchuang.crm.config.utils.MyUtils;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class CrmApplicationTests {

	@Test
	public void contextLoads() throws UnsupportedEncodingException, NoSuchAlgorithmException {
		String ccmc = MyUtils.md5("ccmc111");
		System.out.println(ccmc);

	}

}
