package me.wonwoo.autoconfigure.message.telegram;

import static org.assertj.core.api.Assertions.assertThat;

import me.wonwoo.support.message.slack.SlackMessageService;
import me.wonwoo.support.message.telegram.TelegramMessageService;
import org.junit.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.web.client.AsyncRestTemplate;


/**
 * Created by wonwoolee on 2017. 4. 2..
 */
public class TelegramMessageAutoConfigurationTests {

  private final ApplicationContextRunner runner = new ApplicationContextRunner()
      .withConfiguration(AutoConfigurations.of(TelegramMessageAutoConfiguration.class));

  @Test
  public void registerTelegramMessageTest() {
    runner.withPropertyValues("telegram.message.chat-id=2122111", "telegram.message.api-url=http://api.telegram.com")
        .withUserConfiguration(AsyncRestTemplate.class)
        .run(context -> assertThat(context)
            .hasSingleBean(TelegramMessageService.class));
  }

  @Test
  public void notRegisterTelegramMessagePropertiesTest() {
    runner.withUserConfiguration(AsyncRestTemplate.class)
        .run(context -> assertThat(context)
            .doesNotHaveBean(TelegramMessageService.class));
  }

  @Test
  public void notRegisterTelegramMessageAsyncRestTemplateTest() {
    runner.withPropertyValues("telegram.message.chat-id=2122111", "telegram.message.api-url=http://api.telegram.com")
        .run(context -> assertThat(context)
            .doesNotHaveBean(SlackMessageService.class));
  }
}