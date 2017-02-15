package me.wonwoo.config.interceptor;

import lombok.extern.slf4j.Slf4j;
import me.wonwoo.web.Navigation;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static me.wonwoo.support.utils.ServletUtils.requestIP;

/**
 * Created by wonwoo on 2017. 2. 14..
 */
@Slf4j
public class CommonHandlerInterceptor extends HandlerInterceptorAdapter  {

  private final static String NAV_SECTION = "navSection";

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
}