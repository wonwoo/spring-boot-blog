package me.wonwoo;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import me.wonwoo.setting.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(MappingProperties.class)
public class IndexerApplication {

  public static void main(String[] args) {
    SpringApplication.run(IndexerApplication.class, args);
  }

  @Bean
  public ExecutorService executorService() {
    return Executors.newFixedThreadPool(5);
  }

  @Bean
  IndexSettingsService indexSettingsService(RestTemplateBuilder builder, MappingProperties mappingProperties) {
    return new IndexSettingsServiceComposite(Arrays.asList(
        remoteIndexSettingsService(builder, mappingProperties),
        localIndexSettingsService()
    ));
  }

  private IndexSettingsService localIndexSettingsService() {
    return new LocalIndexSettingsService("/elasticsearch/settings.json", "/elasticsearch/post.json");
  }

  private IndexSettingsService remoteIndexSettingsService(RestTemplateBuilder builder, MappingProperties mappingProperties) {
    return new RemoteIndexSettingsService(builder, mappingProperties.getSettingUrl(), mappingProperties.getMappingUrl());
  }
}
