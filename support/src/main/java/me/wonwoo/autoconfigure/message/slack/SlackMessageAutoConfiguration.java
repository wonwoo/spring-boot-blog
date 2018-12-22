package me.wonwoo.autoconfigure.message.slack;

import me.wonwoo.autoconfigure.message.slack.SlackMessageAutoConfiguration.SlackCondition;
import me.wonwoo.support.message.slack.SlackMessageService;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.StringUtils;
import org.springframework.web.client.AsyncRestTemplate;

/**
 * Created by wonwoo on 2017. 2. 14..
 */
@Configuration
@ConditionalOnBean(AsyncRestTemplate.class)
@EnableConfigurationProperties(SlackMessageProperties.class)
@Conditional(SlackCondition.class)
public class SlackMessageAutoConfiguration {

  @Bean
  public SlackMessageService slackMessageService(AsyncRestTemplate asyncRestTemplate,
      SlackMessageProperties slackMessageProperties) {
    return new SlackMessageService(asyncRestTemplate, slackMessageProperties);
  }

  protected static class SlackCondition extends SpringBootCondition {

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context,
        AnnotatedTypeMetadata metadata) {
      SlackMessageProperties slackMessageProperties = Binder.get(context.getEnvironment())
          .bind("slack.message", SlackMessageProperties.class)
          .orElse(new SlackMessageProperties());
      if (StringUtils.hasLength(slackMessageProperties.getWebHookUrl())
          && StringUtils.hasLength(slackMessageProperties.getChannel())) {
        return ConditionOutcome.match("found webHookUrl and channel property");
      }
      return ConditionOutcome.noMatch("not found webHookUrl and channel property");
    }
  }
}
