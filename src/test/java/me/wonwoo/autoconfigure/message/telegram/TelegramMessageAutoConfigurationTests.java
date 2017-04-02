package me.wonwoo.autoconfigure.message.telegram;

import me.wonwoo.support.message.telegram.TelegramMessageService;
import org.junit.After;
import org.junit.Test;
import org.springframework.boot.test.util.EnvironmentTestUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.client.AsyncRestTemplate;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by wonwoolee on 2017. 4. 2..
 */
public class TelegramMessageAutoConfigurationTests {

  private AnnotationConfigApplicationContext context;

  @After
  public void tearDown() {
    if (this.context != null) {
      this.context.close();
    }
  }

  @Test
  public void registerTelegramMessageTest() {
    load(AsyncRestTemplate.class, "telegram.message.chat-id=2122111", "telegram.message.api-url=http://api.telegram.com");
    assertThat(this.context.getBeansOfType(TelegramMessageService.class)).isNotEmpty();
  }

  @Test
  public void notRegisterTelegramMessagePropertiesTest() {
    load(AsyncRestTemplate.class);
    assertThat(this.context.getBeansOfType(TelegramMessageService.class)).isEmpty();
  }

  @Test
  public void notRegisterTelegramMessageAsyncRestTemplateTest() {
    load(null, "telegram.message.chat-id=2122111", "telegram.message.api-url=http://api.telegram.com");
    assertThat(this.context.getBeansOfType(TelegramMessageService.class)).isEmpty();
  }

  private void load(Class<?> clazz, String... environment) {
    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
    if (clazz != null) {
      context.register(clazz);
    }
    if (environment != null && environment.length > 0) {
      EnvironmentTestUtils.addEnvironment(context, environment);
    }
    context.register(TelegramMessageAutoConfiguration.class);
    context.refresh();
    this.context = context;
  }
}