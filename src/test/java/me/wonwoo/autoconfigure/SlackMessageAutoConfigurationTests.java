package me.wonwoo.autoconfigure;

import me.wonwoo.autoconfigure.slack.SlackMessageAutoConfiguration;
import me.wonwoo.support.slack.SlackMessageService;
import org.junit.After;
import org.junit.Test;
import org.springframework.boot.test.util.EnvironmentTestUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.client.AsyncRestTemplate;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by wonwoo on 2017. 2. 14..
 */
public class SlackMessageAutoConfigurationTests {

  private AnnotationConfigApplicationContext context;

  @After
  public void tearDown() {
    if (this.context != null) {
      this.context.close();
    }
  }

  @Test
  public void registerSlackMessageTest() {
    load(AsyncRestTemplate.class, "slack.message.channel=#alter", "slack.message.web-hook-url=http://test.com");
    assertThat(this.context.getBeansOfType(SlackMessageService.class)).isNotEmpty();
  }

  @Test
  public void notRegisterSlackMessagePropertiesTest() {
    load(AsyncRestTemplate.class);
    assertThat(this.context.getBeansOfType(SlackMessageService.class)).isEmpty();
  }

  @Test
  public void notRegisterSlackMessageAsyncRestTemplateTest() {
    load(null, "slack.message.channel=#alter", "slack.message.web-hook-url=http://test.com");
    assertThat(this.context.getBeansOfType(SlackMessageService.class)).isEmpty();
  }

  private void load(Class<?> clazz, String... environment) {
    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
    if (clazz != null) {
      context.register(clazz);
    }
    if (environment != null && environment.length > 0) {
      EnvironmentTestUtils.addEnvironment(context, environment);
    }
    context.register(SlackMessageAutoConfiguration.class);
    context.refresh();
    this.context = context;
  }
}