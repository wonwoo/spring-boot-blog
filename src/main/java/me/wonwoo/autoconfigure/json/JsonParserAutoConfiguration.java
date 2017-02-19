package me.wonwoo.autoconfigure.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import me.wonwoo.support.json.GsonParser;
import me.wonwoo.support.json.JacksonParser;
import me.wonwoo.support.json.JsonSmartParser;
import net.minidev.json.parser.JSONParser;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by wonwoo on 2017. 2. 19..
 */
@AutoConfigureAfter({GsonAutoConfiguration.class, JacksonAutoConfiguration.class})
@Configuration
public class JsonParserAutoConfiguration {

  @ConditionalOnClass(ObjectMapper.class)
  private static class JacksonJsonConfiguration {

    @Bean
    @ConditionalOnBean(ObjectMapper.class)
    public JacksonParser jacksonParser(ObjectMapper objectMapper) {
      return new JacksonParser(objectMapper);
    }
  }

  @ConditionalOnClass(Gson.class)
  private static class GsonJsonConfiguration {

    @Bean
    @ConditionalOnBean(Gson.class)
    public GsonParser gsonParser(Gson gson) {
      return new GsonParser(gson);
    }
  }

  @ConditionalOnClass(JSONParser.class)
  private static class SmartJsonConfiguration {

    @Bean
    @ConditionalOnBean(JSONParser.class)
    public JsonSmartParser jsonSmartParser(JSONParser jsonParser) {
      return new JsonSmartParser(jsonParser);
    }
  }
}
