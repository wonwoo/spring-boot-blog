package me.wonwoo.autoconfigure.message.telegram;

import me.wonwoo.support.message.MessageService;
import me.wonwoo.support.message.telegram.TelegramMessageService;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
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
 * Created by wonwoolee on 2017. 4. 2..
 */
@ConditionalOnClass(AsyncRestTemplate.class)
@Configuration
@EnableConfigurationProperties(TelegramProperties.class)
@Conditional(TelegramMessageAutoConfiguration.TelegramCondition.class)
public class TelegramMessageAutoConfiguration {

  @Bean
  @ConditionalOnBean(AsyncRestTemplate.class)
  @ConditionalOnMissingBean
  public MessageService telegramMessageService(AsyncRestTemplate asyncRestTemplate,
                                               TelegramProperties telegramProperties) {
    return new TelegramMessageService(asyncRestTemplate, telegramProperties);
  }

  protected static class TelegramCondition extends SpringBootCondition {

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context,
        AnnotatedTypeMetadata metadata) {
      TelegramProperties telegramProperties = Binder.get(context.getEnvironment())
          .bind("telegram.message", TelegramProperties.class)
          .orElse(new TelegramProperties());
      if (StringUtils.hasLength(telegramProperties.getApiUrl())
          && StringUtils.hasLength(telegramProperties.getChatId())) {
        return ConditionOutcome.match("found apiUrl and chatId property");
      }
      return ConditionOutcome.noMatch("not found apiUrl and chatId property");
    }
  }
}