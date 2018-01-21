package me.wonwoo.security;

/**
 * Created by wonwoo on 2016. 10. 15..
 */
public class UserNotFoundException extends RuntimeException {
  private String id;

  public UserNotFoundException(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }
}
