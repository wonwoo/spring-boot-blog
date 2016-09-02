package me.wonwoo.testing;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wonwoo on 2016. 9. 2..
 */
@Slf4j
@RequiredArgsConstructor
public class RequestCountInterceptor extends HandlerInterceptorAdapter {

  private final HibernateInterceptor hibernateInterceptor;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    hibernateInterceptor.start();
    return true;
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    Counter counter = hibernateInterceptor.getCount();
    long duration = System.currentTimeMillis() - counter.getTime();
    Long count = counter.getCount().get();
    log.info("time : {}, count : {} , url : {}", duration, count,  request.getRequestURI());
    if(count >= 10){
      log.error("한 request 에 쿼리가 10번 이상 날라갔습니다.  날라간 횟수 : {} ", count);
    }
    hibernateInterceptor.clear();
  }
}
