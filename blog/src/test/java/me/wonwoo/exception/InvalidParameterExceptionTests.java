package me.wonwoo.exception;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by wonwoolee on 2017. 8. 7..
 */
public class InvalidParameterExceptionTests {

  @Test
  public void invalidParameterException() {
    InvalidParameterException invalidParameterException = new InvalidParameterException("title parameters not empty");
    assertThat(invalidParameterException.getMessage()).isEqualTo("title parameters not empty");
  }
}