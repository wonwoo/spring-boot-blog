package me.wonwoo;

import me.wonwoo.config.PostProperties;
import me.wonwoo.security.GitProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.dialect.springdata.SpringDataDialect;

import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

@SpringBootApplication
@EntityScan(basePackageClasses = {SpringBootBlogApplication.class, Jsr310JpaConverters.class})
@EnableConfigurationProperties({GitProperties.class, PostProperties.class})
@EnableJpaAuditing
@EnableCaching
public class SpringBootBlogApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringBootBlogApplication.class, args);
  }


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
      cm.createCache("github.user", initConfiguration(Duration.ONE_HOUR));
    };
  }

  private MutableConfiguration<Object, Object> initConfiguration(Duration duration) {
    return new MutableConfiguration<>()
      .setStoreByValue(false)
      .setStatisticsEnabled(true)
      .setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(duration));
  }

  @Bean
  public RestTemplate restTemplate(GitProperties gitProperties) {
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.setInterceptors(Collections.singletonList(
      new GithubAppTokenInterceptor(gitProperties.getGithub().getToken())));
    return restTemplate;
  }

  private static class GithubAppTokenInterceptor implements ClientHttpRequestInterceptor {

    private final String token;

    GithubAppTokenInterceptor(String token) {
      this.token = token;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes,
                                        ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
      if (StringUtils.hasText(this.token)) {
        byte[] basicAuthValue = this.token.getBytes(StandardCharsets.UTF_8);
        httpRequest.getHeaders().set(HttpHeaders.AUTHORIZATION,
          "Basic " + Base64Utils.encodeToString(basicAuthValue));
      }
      return clientHttpRequestExecution.execute(httpRequest, bytes);
    }
  }

  @Bean
  public SpringDataDialect springDataDialect() {
    return new SpringDataDialect();
  }
}
