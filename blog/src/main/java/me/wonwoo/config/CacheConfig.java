package me.wonwoo.config;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;

/**
 * Created by wonwoo on 2017. 2. 14..
 */
@Configuration
public class CacheConfig {


  @Bean
  public JCacheManagerCustomizer cacheManagerCustomizer() {
    return cm -> {
      cm.createCache("wp.posts", initConfiguration(Duration.ONE_DAY));
      cm.createCache("wp.post", initConfiguration(Duration.ONE_DAY));
      cm.createCache("spring.gss", initConfiguration(Duration.ONE_DAY));
      cm.createCache("spring.gs", initConfiguration(Duration.ONE_DAY));
      cm.createCache("spring.tuts", initConfiguration(Duration.ONE_DAY));
      cm.createCache("spring.tut", initConfiguration(Duration.ONE_DAY));
      cm.createCache("spring.blog.category", initConfiguration(Duration.ONE_MINUTE));
      cm.createCache("github.commits", initConfiguration(Duration.ONE_MINUTE));
      cm.createCache("github.page", initConfiguration(Duration.ONE_DAY));
      cm.createCache("weather", initConfiguration(Duration.THIRTY_MINUTES));
      cm.createCache("github.user", initConfiguration(Duration.ONE_HOUR));
    };
  }

  private MutableConfiguration<Object, Object> initConfiguration(Duration duration) {
    return new MutableConfiguration<>()
        .setStoreByValue(false)
        .setStatisticsEnabled(true)
        .setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(duration));
  }

}
