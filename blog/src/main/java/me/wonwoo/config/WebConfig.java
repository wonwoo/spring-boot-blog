package me.wonwoo.config;

import static org.pegdown.Extensions.ALL;

import me.wonwoo.config.interceptor.CommonHandlerInterceptor;
import org.pegdown.PegDownProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.dialect.springdata.SpringDataDialect;

/**
 * Created by wonwoo on 2016. 9. 2..
 */
@Configuration
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
}
