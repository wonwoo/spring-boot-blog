package me.wonwoo.support.message;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by wonwoolee on 2017. 4. 2..
 */
public interface MessageService {

  int MESSAGE_LENGTH = 4000;

  void send(Throwable e);

  void setCallback(ListenableFutureCallback<ResponseEntity<?>> callback);

  default String text(Throwable e) {
    String stackTrace = ExceptionUtils.getStackTrace(e);
    return stackTrace.length() > MESSAGE_LENGTH ? stackTrace.substring(0, MESSAGE_LENGTH) : stackTrace;
  }

  default String markdown(Throwable e) {
    return "```\n" +
        text(e) +
        "\n```";
  }

  default void urlValidator(String url) {
    try {
      new URL(url);
    } catch (MalformedURLException e) {
      throw new InvalidWebHookUrlException(url);
    }
  }
}