package me.wonwoo.testing;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by wonwoo on 2016. 9. 2..
 */
@Configuration
@RequiredArgsConstructor
public class WebConfig extends WebMvcConfigurerAdapter {

  private final RequestCountInterceptor requestCountInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(requestCountInterceptor).addPathPatterns("/**");

  }
}
