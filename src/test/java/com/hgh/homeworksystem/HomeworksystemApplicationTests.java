package com.hgh.homeworksystem;

import com.hgh.homeworksystem.util.PasswordUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HomeworksystemApplicationTests {

	@Test
	public void contextLoads() {
		System.out.println(PasswordUtil.generate("123456"));
	}

}
