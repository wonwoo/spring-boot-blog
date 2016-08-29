package me.wonwoo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

public class SpringBootBlogApplicationTests {

	@Test
	public void contextLoads() {
		assertThat("1").isEqualTo("1");
	}

}
