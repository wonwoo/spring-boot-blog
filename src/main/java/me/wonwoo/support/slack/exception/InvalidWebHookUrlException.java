package me.wonwoo.support.slack.exception;

/**
 * Created by wonwoo on 2017. 2. 26..
 */
public class InvalidWebHookUrlException extends RuntimeException {

  private final String webHookUrl;

  public InvalidWebHookUrlException(String webHookUrl) {
    this.webHookUrl = webHookUrl;
  }

  public String getWebHookUrl() {
    return webHookUrl;
  }
}