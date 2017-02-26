package me.wonwoo.support.slack;

import me.wonwoo.autoconfigure.slack.SlackMessageProperties;
import me.wonwoo.support.slack.exception.InvalidWebHookUrlException;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.AsyncRestTemplate;

import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by wonwoo on 2017. 2. 14..
 */
public class SlackMessageService {

  private final AsyncRestTemplate asyncRestTemplate;
  private final String channel;
  private final String username;
  private final String iconEmoji;
  private final String webHookUrl;


  public SlackMessageService(AsyncRestTemplate asyncRestTemplate, SlackMessageProperties properties) {
    this.asyncRestTemplate = asyncRestTemplate;
    this.channel = properties.getChannel();
    this.username = properties.getUsername();
    this.iconEmoji = properties.getIconEmoji();
    this.webHookUrl = properties.getWebHookUrl();
    urlValidator(this.webHookUrl);
  }

  public void send(Exception e) {
    String stackTrace = ExceptionUtils.getStackTrace(e);
    String substringMessage = stackTrace.length() > 4000 ? stackTrace.substring(0, 4000) : stackTrace;
    String markdown = "```\n" +
      substringMessage +
      "\n```";
    HttpEntity<?> httpEntity = new HttpEntity<>(new Payload(channel, username, markdown, iconEmoji));
    asyncRestTemplate.postForEntity(webHookUrl, httpEntity, String.class);
  }

  private void urlValidator(String webHookUrl) {
    try {
      new URL(webHookUrl);
    } catch (MalformedURLException e) {
      throw new InvalidWebHookUrlException(webHookUrl);
    }
  }
}