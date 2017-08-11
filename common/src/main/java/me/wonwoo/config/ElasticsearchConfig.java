package me.wonwoo.config;

import java.io.IOException;

import org.elasticsearch.client.Client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.EntityMapper;
import org.springframework.data.elasticsearch.core.geo.CustomGeoModule;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * Created by wonwoo on 2017. 2. 13..
 */
@Configuration
public class ElasticsearchConfig {

  @Bean
  public ElasticsearchTemplate elasticsearchTemplate(Client client, LocalDatetimeEntityMapper localDatetimeEntityMapper) {
    return new ElasticsearchTemplate(client, localDatetimeEntityMapper);
  }

  @Configuration
  public static class LocalDatetimeEntityMapper implements EntityMapper {

    private final ObjectMapper objectMapper;

    public LocalDatetimeEntityMapper() {
      objectMapper = Jackson2ObjectMapperBuilder
        .json()
        .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        .modules(new JavaTimeModule(), new CustomGeoModule())
        .build();
      objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
    }

    @Override
    public String mapToString(Object object) throws IOException {
      return objectMapper.writeValueAsString(object);
    }

    @Override
    public <T> T mapToObject(String source, Class<T> clazz) throws IOException {
      return objectMapper.readValue(source, clazz);
    }
  }
}
