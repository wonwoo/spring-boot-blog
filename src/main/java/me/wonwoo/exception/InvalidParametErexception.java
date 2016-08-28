package me.wonwoo.exception;

/**
 * Created by wonwoo on 2016. 8. 22..
 */
public class InvalidParametErexception extends RuntimeException {
  private final String message;

  public InvalidParametErexception(String message){
    this.message = message;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
