package me.wonwoo.support.message.slack;

import me.wonwoo.autoconfigure.message.slack.SlackMessageProperties;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.AsyncRestTemplate;

import static org.mockito.Mockito.verify;


/**
 * Created by wonwoo on 2017. 2. 14..
 */
@RunWith(MockitoJUnitRunner.class)
public class SlackMessageServiceTests {

  @Mock
  private AsyncRestTemplate asyncRestTemplate;

  @Spy
  private SlackMessageProperties properties;

  @Test
  public void sendTest() {
    properties.setIconEmoji("1");
    properties.setUsername("wonwoo");
    properties.setChannel("spring-boot-blog");
    properties.setWebHookUrl("http://wonwoo.ml");
    SlackMessageService slackMessageService = new SlackMessageService(asyncRestTemplate, properties);
    NullPointerException e = new NullPointerException();
    slackMessageService.send(e);
    String stackTrace = "```\n" + ExceptionUtils.getStackTrace(e) + "\n```";
    Payload payload = new Payload(properties.getChannel(), properties.getUsername(), stackTrace, properties.getIconEmoji());
    HttpEntity<?> httpEntity = new HttpEntity<>(payload);
    verify(asyncRestTemplate).postForEntity("http://wonwoo.ml", httpEntity, Void.class);
  }
}