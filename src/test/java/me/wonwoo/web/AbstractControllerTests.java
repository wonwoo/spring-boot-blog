package me.wonwoo.web;

import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by wonwoo on 2017. 2. 15..
 */
@RunWith(SpringRunner.class)
@WithMockUser(username = "wonwoo")
public abstract class AbstractControllerTests {


  @SpringBootApplication
  public static class BootTest {

  }
}
