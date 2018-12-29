package me.wonwoo.config;

import static net.minidev.json.parser.JSONParser.DEFAULT_PERMISSIVE_MODE;
import static org.pegdown.Extensions.ALL;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import me.wonwoo.config.interceptor.CommonHandlerInterceptor;
import me.wonwoo.security.GitProperties;
import me.wonwoo.support.github.GithubClient;
import me.wonwoo.support.weather.WeatherAppProperties;
import net.minidev.json.parser.JSONParser;
import org.pegdown.PegDownProcessor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.dialect.springdata.SpringDataDialect;

/**
 * Created by wonwoo on 2016. 9. 2..
 */
@Configuration
@EnableConfigurationProperties({GitProperties.class, PostProperties.class, WeatherAppProperties.class})
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/login").setViewName("login");
  }

  @Bean
  public PegDownProcessor pegDownProcessor() {
    return new PegDownProcessor(ALL);
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(commonHandlerInterceptor());
  }

  @Bean
  public CommonHandlerInterceptor commonHandlerInterceptor() {
    return new CommonHandlerInterceptor();
  }

  @Bean
  public SpringDataDialect springDataDialect() {
    return new SpringDataDialect();
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
  public JSONParser jsonParser() {
    return new JSONParser(DEFAULT_PERMISSIVE_MODE);
  }

  @Bean
  GithubClient githubClient() {
    return new GithubClient(restTemplate(null));
  }
}
