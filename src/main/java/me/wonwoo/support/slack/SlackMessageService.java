package me.wonwoo.support.slack;

import me.wonwoo.autoconfigure.SlackMessageProperties;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.AsyncRestTemplate;


/**
 * Created by wonwoo on 2017. 2. 14..
 */
public class SlackMessageService {

  private final AsyncRestTemplate asyncRestTemplate;
  private final SlackMessageProperties slackMessageProperties;

  public SlackMessageService(AsyncRestTemplate asyncRestTemplate, SlackMessageProperties slackMessageProperties) {
    this.asyncRestTemplate = asyncRestTemplate;
    this.slackMessageProperties = slackMessageProperties;
  }

  public void send(Exception e) {
    String stackTrace = ExceptionUtils.getStackTrace(e);
    String substringMessage = stackTrace.length() > 4000 ? stackTrace.substring(0, 4000) : stackTrace;
    String markdown = "```\n" + substringMessage + "\n```";
    Payload payload = new Payload();
    payload.setChannel(slackMessageProperties.getChannel());
    payload.setUsername(slackMessageProperties.getUsername());
    payload.setIconEmoji(slackMessageProperties.getIconEmoji());
    payload.setText(markdown);
    HttpEntity<?> httpEntity = new HttpEntity<>(payload);
    asyncRestTemplate.postForEntity(slackMessageProperties.getWebHookUrl(), httpEntity, String.class);
  }
}
