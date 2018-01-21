package me.wonwoo.autoconfigure.message.telegram;

import me.wonwoo.support.message.MessageService;
import me.wonwoo.support.message.telegram.TelegramMessageService;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.PropertyResolver;
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
      PropertyResolver resolver = new RelaxedPropertyResolver(
          context.getEnvironment(), "telegram.message.");
      String apiUrl = resolver.getProperty("apiUrl");
      String chatId = resolver.getProperty("chatId");
      if (StringUtils.hasLength(apiUrl) && StringUtils.hasLength(chatId)) {
        return ConditionOutcome.match("found apiUrl and chatId property");
      }
      return ConditionOutcome.noMatch("not found apiUrl and chatId property");
    }
  }
}