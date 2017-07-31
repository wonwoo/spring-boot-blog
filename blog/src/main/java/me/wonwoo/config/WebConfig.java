package me.wonwoo.config;

import me.wonwoo.config.interceptor.CommonHandlerInterceptor;
import me.wonwoo.domain.repository.BlogHistoryRepository;
import org.pegdown.PegDownProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.dialect.springdata.SpringDataDialect;

import static org.pegdown.Extensions.ALL;

/**
 * Created by wonwoo on 2016. 9. 2..
 */
@Configuration
@EnableJpaAuditing
public class WebConfig extends WebMvcConfigurerAdapter {

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
    registry.addInterceptor(commonHandlerInterceptor(null));
  }

  @Bean
  public CommonHandlerInterceptor commonHandlerInterceptor(BlogHistoryRepository blogHistoryRepository) {
    return new CommonHandlerInterceptor(blogHistoryRepository);
  }

  @Bean
  public SpringDataDialect springDataDialect() {
    return new SpringDataDialect();
  }
}
