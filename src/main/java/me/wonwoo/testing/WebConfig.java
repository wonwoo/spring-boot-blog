package me.wonwoo.testing;

import lombok.RequiredArgsConstructor;
import me.wonwoo.web.Navigation;
import org.pegdown.PegDownProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static org.pegdown.Extensions.ALL;

/**
 * Created by wonwoo on 2016. 9. 2..
 */
@Configuration
@RequiredArgsConstructor
public class WebConfig extends WebMvcConfigurerAdapter {

  private final RequestCountInterceptor requestCountInterceptor;

  @Bean
  public PegDownProcessor pegDownProcessor(){
    return new PegDownProcessor(ALL);
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(requestCountInterceptor).addPathPatterns("/**");
    registry.addInterceptor(new HandlerInterceptorAdapter() {
      @Override
      public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                             ModelAndView modelAndView) throws Exception {

        if (handler instanceof HandlerMethod) {
          HandlerMethod handlerMethod = (HandlerMethod) handler;
          Navigation navSection = handlerMethod.getBean().getClass().getAnnotation(Navigation.class);
          if (navSection != null && modelAndView != null) {
            modelAndView.addObject("navSection", navSection.value().getValue());
          }
        }
      }
    });
  }
}
