package me.wonwoo.security;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by wonwoolee on 2017. 8. 7..
 */
public class UserNotFoundExceptionTest {

  @Test
  public void userNotFoundException() {
    UserNotFoundException userNotFoundException = new UserNotFoundException("1");
    assertThat(userNotFoundException.getId()).isEqualTo("1");
  }
}