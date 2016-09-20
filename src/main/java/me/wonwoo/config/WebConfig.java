package me.wonwoo.config;

import lombok.RequiredArgsConstructor;
import me.wonwoo.domain.model.BlogHistory;
import me.wonwoo.service.BlogHistoryService;
import me.wonwoo.testing.RequestCountInterceptor;
import me.wonwoo.web.Navigation;
import org.pegdown.PegDownProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
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

  private final BlogHistoryService blogHistoryService;

  private final static String NAV_SECTION = "navSection";

  @Bean
  public PegDownProcessor pegDownProcessor() {
    return new PegDownProcessor(ALL);
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(requestCountInterceptor).addPathPatterns("/**");
    registry.addInterceptor(new HandlerInterceptorAdapter() {

      @Override
      public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String ip = request.getHeader("X-FORWARDED-FOR");
        if (ip == null) {
          ip = request.getRemoteAddr();
        }
        final String requestURL = request.getRequestURL().toString();
        final String requestURI = request.getRequestURI();
        String referer = request.getHeader("REFERER");
        String navigation = (String) request.getSession().getAttribute(NAV_SECTION);
        try {
          blogHistoryService.save(new BlogHistory(requestURI, requestURL, ip, navigation, referer));
        } catch (Exception ignored) {
        }
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
}
