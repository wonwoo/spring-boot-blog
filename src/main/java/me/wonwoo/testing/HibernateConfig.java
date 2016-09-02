package me.wonwoo.testing;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.sql.DataSource;
import java.util.Map;

/**
 * Created by wonwoo on 2016. 9. 2..
 */
@Configuration
public class HibernateConfig extends HibernateJpaAutoConfiguration {

  public HibernateConfig(DataSource dataSource, JpaProperties jpaProperties, ObjectProvider<JtaTransactionManager> jtaTransactionManagerProvider) {
    super(dataSource, jpaProperties, jtaTransactionManagerProvider);
  }

  @Override
  protected void customizeVendorProperties(Map<String, Object> vendorProperties) {
    vendorProperties.put("hibernate.session_factory.statement_inspector", hibernateInterceptor());
    super.customizeVendorProperties(vendorProperties);
  }

  @Bean
  public HibernateInterceptor hibernateInterceptor() {
    return new HibernateInterceptor();
  }

  @Bean
  public RequestCountInterceptor requestCountInterceptor() {
    return new RequestCountInterceptor(hibernateInterceptor());
  }
}