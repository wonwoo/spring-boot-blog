package me.wonwoo.exception;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by wonwoolee on 2017. 8. 7..
 */
public class NotFoundExceptionTests {

  @Test
  public void notFoundException() {
    NotFoundException notFoundException = new NotFoundException("1 user not found");
    assertThat(notFoundException.getMessage()).isEqualTo("1 user not found");
  }
}