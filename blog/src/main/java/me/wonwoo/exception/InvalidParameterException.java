package me.wonwoo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by wonwoo on 2016. 8. 22..
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidParameterException extends RuntimeException {
  private final String message;

  public InvalidParameterException(String message) {
    this.message = message;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
