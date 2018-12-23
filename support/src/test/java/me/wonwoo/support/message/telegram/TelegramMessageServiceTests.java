package me.wonwoo.support.message.telegram;

import me.wonwoo.autoconfigure.message.telegram.TelegramProperties;
import me.wonwoo.support.message.MessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.AsyncRestTemplate;

import static org.mockito.Mockito.verify;


/**
 * Created by wonwoolee on 2017. 4. 2..
 */
@RunWith(MockitoJUnitRunner.class)
public class TelegramMessageServiceTests {

  @Mock
  private AsyncRestTemplate asyncRestTemplate;

  @Spy
  private TelegramProperties telegramProperties;

  @Test
  public void sendTest() {
    telegramProperties.setApiUrl("http://api.telegram.com");
    telegramProperties.setChatId("78789999");
    MessageService slackMessageService = new TelegramMessageService(asyncRestTemplate, telegramProperties);
    NullPointerException e = new NullPointerException();
    slackMessageService.send(e);
    verify(asyncRestTemplate).getForEntity(String.format("http://api.telegram.com/sendmessage?chat_id=78789999&text=%s&parse_mode=Markdown", slackMessageService.markdown(e)), Void.class);
  }
}