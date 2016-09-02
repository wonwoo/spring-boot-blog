package me.wonwoo.testing;

import org.hibernate.resource.jdbc.spi.StatementInspector;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by wonwoo on 2016. 9. 2..
 */
public class HibernateInterceptor implements StatementInspector {

  private ThreadLocal<Counter> queryCount = new ThreadLocal<>();

  void start() {
    queryCount.set(new Counter(new AtomicLong(0), System.currentTimeMillis()));
  }

  Counter getCount() {
    return queryCount.get();
  }

  void clear() {
    queryCount.remove();
  }

  @Override
  public String inspect(String sql) {
    queryCount.get().getCount().addAndGet(1);
    return sql;
  }
}