package me.wonwoo.autoconfigure.message.slack;

import static org.assertj.core.api.Assertions.assertThat;

import me.wonwoo.support.message.slack.SlackMessageService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.client.AsyncRestTemplate;


/**
 * Created by wonwoo on 2017. 2. 14..
 */
public class SlackMessageAutoConfigurationTests {

  @Rule
  public final ExpectedException exception = ExpectedException.none();

  private final ApplicationContextRunner runner = new ApplicationContextRunner()
      .withConfiguration(AutoConfigurations.of(SlackMessageAutoConfiguration.class));


  @Test
  public void registerSlackMessageTest() {
    runner.withPropertyValues("slack.message.channel=#alter", "slack.message.web-hook-url=http://test.com")
        .withUserConfiguration(AsyncRestTemplate.class)
        .run(context -> assertThat(context)
            .hasSingleBean(SlackMessageService.class));
  }

  @Test
  public void notRegisterSlackMessagePropertiesTest() {
    runner.withUserConfiguration(AsyncRestTemplate.class)
        .run(context -> assertThat(context)
            .doesNotHaveBean(SlackMessageService.class));
  }

  @Test
  public void notRegisterSlackMessageAsyncRestTemplateTest() {
    runner.withPropertyValues("slack.message.channel=#alter", "slack.message.web-hook-url=http://test.com")
        .run(context -> assertThat(context)
            .doesNotHaveBean(SlackMessageService.class));
  }

  @Test
  public void registerSlackUriExceptionMessageTest() {
    runner.withPropertyValues("slack.message.channel=#alter", "slack.message.web-hook-url=asdfsadfsa")
        .withUserConfiguration(AsyncRestTemplate.class)
        .run(context -> assertThat(context)
            .isNotExactlyInstanceOf(BeanCreationException.class));
  }
}