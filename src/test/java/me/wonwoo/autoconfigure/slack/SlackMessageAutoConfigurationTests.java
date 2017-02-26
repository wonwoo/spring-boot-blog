package me.wonwoo.autoconfigure.slack;

import me.wonwoo.support.slack.SlackMessageService;
import me.wonwoo.support.slack.exception.InvalidWebHookUrlException;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.boot.test.util.EnvironmentTestUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.client.AsyncRestTemplate;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by wonwoo on 2017. 2. 14..
 */
public class SlackMessageAutoConfigurationTests {

  @Rule
  public final ExpectedException exception = ExpectedException.none();

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

  @Test
  public void registerSlackUriExceptionMessageTest() {
    exception.expect(BeanCreationException.class);
    load(AsyncRestTemplate.class, "slack.message.channel=#alter", "slack.message.web-hook-url=asdfsadfsa");
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