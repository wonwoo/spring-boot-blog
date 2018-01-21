package me.wonwoo.config.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import lombok.extern.slf4j.Slf4j;
import me.wonwoo.web.Navigation;

/**
 * Created by wonwoo on 2017. 2. 14..
 */
@Slf4j
public class CommonHandlerInterceptor extends HandlerInterceptorAdapter {

  private final static String NAV_SECTION = "navSection";

  @Override
  public boolean preHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler) throws Exception {

    try {
      HandlerMethod handlerMethod = (HandlerMethod) handler;
//      final String requestURL = request.getRequestURL().toString();
//      final String requestURI = request.getRequestURI();
//      String referer = request.getHeader("REFERER");
//      String navigation = (String) request.getSession().getAttribute(NAV_SECTION);
//      try {
//        blogHistoryRepository.save(new BlogHistory(requestURI, requestURL, requestIP(), navigation, referer, LocalDateTime.now()));
//      } catch (Exception ignored) {
//        System.out.println(ignored);
//      }
      logger.info("Current Request Handler : uri : {}, queryString : {}, ip : {}",
          request.getRequestURI(),
          request.getQueryString(),
          requestIp(request)
      );
      return true;
    } catch (Exception ignored) {
    }
    return true;
  }

  private String requestIp(HttpServletRequest request) {
    String ip = request.getHeader("X-FORWARDED-FOR");
    if (ip == null) {
      return request.getRemoteAddr();
    }
    return ip;
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
}
