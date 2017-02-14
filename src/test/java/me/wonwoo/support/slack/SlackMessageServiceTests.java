package me.wonwoo.support.slack;

import me.wonwoo.autoconfigure.SlackMessageProperties;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.junit.Before;
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
  private SlackMessageProperties slackMessageProperties;

  private SlackMessageService slackMessageService;
  @Before
  public void setup() {
    slackMessageProperties.setIconEmoji("1");
    slackMessageProperties.setUsername("hellowd");
    slackMessageProperties.setChannel("alter");
    slackMessageProperties.setWebHookUrl("http://test.com");
    slackMessageService = new SlackMessageService(asyncRestTemplate, slackMessageProperties);
  }

  @Test
  public void sendTest() {
    NullPointerException e = new NullPointerException();
    slackMessageService.send(e);
    String stackTrace = "```\n" + ExceptionUtils.getStackTrace(e) + "\n```";
    Payload payload = new Payload();
    payload.setChannel(slackMessageProperties.getChannel());
    payload.setUsername(slackMessageProperties.getUsername());
    payload.setIconEmoji(slackMessageProperties.getIconEmoji());
    payload.setText(stackTrace);
    HttpEntity<?> httpEntity = new HttpEntity<>(payload);
    verify(asyncRestTemplate).postForEntity("http://test.com", httpEntity, String.class);
  }
}