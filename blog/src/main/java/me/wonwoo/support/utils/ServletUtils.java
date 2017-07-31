package me.wonwoo.support.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by wonwoo on 2017. 2. 14..
 */
public class ServletUtils {

  public static String requestIP() {
    HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    String ip = req.getHeader("X-FORWARDED-FOR");
    if (ip == null)
      ip = req.getRemoteAddr();
    return ip;
  }
}
