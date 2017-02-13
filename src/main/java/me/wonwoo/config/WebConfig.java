package me.wonwoo.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.wonwoo.web.Navigation;
import org.pegdown.PegDownProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
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
@Slf4j
@EnableJpaAuditing
public class WebConfig extends WebMvcConfigurerAdapter {

  private final static String NAV_SECTION = "navSection";

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
    registry.addInterceptor(new HandlerInterceptorAdapter() {

      @Override
      public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        try {
          HandlerMethod handlerMethod = (HandlerMethod) handler;
          log.info("Current Request Handler : {}.{}() , uri : {}, query : {}, ip : {}",
            handlerMethod.getBeanType().getCanonicalName(),
            handlerMethod.getMethod().getName(),
            request.getRequestURI(),
            request.getQueryString(),
            requestIP()
          );
          return true;
        } catch (Exception ignored) {
        }
        return true;
      }

      @Override
      public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                             ModelAndView modelAndView) throws Exception {

        if (handler instanceof HandlerMethod) {
          HandlerMethod handlerMethod = (HandlerMethod) handler;
          Navigation navSection = handlerMethod.getBean().getClass().getAnnotation(Navigation.class);
          if (navSection != null && modelAndView != null) {
            request.getSession().setAttribute(NAV_SECTION, navSection.value().getValue());
            modelAndView.addObject(NAV_SECTION, navSection.value().getValue());
          }
        }
      }
    });
  }

  public static String requestIP() {
    HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    String ip = req.getHeader("X-FORWARDED-FOR");
    if (ip == null)
      ip = req.getRemoteAddr();
    return ip;
  }
}
