package me.wonwoo.domain;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewInterceptor;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories
@RequiredArgsConstructor
class BlogDataConfig {

    private final BlogProperties blogProperties;

    @Bean
    PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager(entityManagerFactory().getObject());
    }

    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setGenerateDdl(false);
        jpaVendorAdapter.setShowSql(true);
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();

        Properties properties = new Properties();
        properties.setProperty("hibernate.implicit_naming_strategy","org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy");
        properties.setProperty("hibernate.physical_naming_strategy","org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy");

        factoryBean.setPersistenceUnitName("blog");

        factoryBean.setJpaProperties(properties);
        factoryBean.setDataSource(blogDataSource());
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        factoryBean.setPackagesToScan(BlogDataConfig.class.getPackage().getName());
        return factoryBean;
    }

    @Bean
    DataSource blogDataSource() {
        HikariConfig config = new HikariConfig();
        config.setPoolName("blog-pool");
        config.setDataSourceClassName(blogProperties.getDriverClassName());
        config.setMinimumIdle(blogProperties.getMinIdle());
        config.setMaximumPoolSize(blogProperties.getMaxPoolSize());
        config.addDataSourceProperty("url", blogProperties.getUrl());
        config.addDataSourceProperty("user", blogProperties.getUsername());
        config.addDataSourceProperty("password", blogProperties.getPassword());
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        return new HikariDataSource(config);
    }

    @Configuration
    protected static class JpaBlogWebConfiguration {

        // Defined as a nested config to ensure WebMvcConfigurerAdapter is not read when
        // not on the classpath
        @Configuration
        protected static class JpaBlogsWebMvcConfiguration extends WebMvcConfigurerAdapter {

            @Bean
            public OpenEntityManagerInViewInterceptor openBlogEntityManagerInViewInterceptor() {
                OpenEntityManagerInViewInterceptor openEntityManagerInViewInterceptor = new OpenEntityManagerInViewInterceptor();
                openEntityManagerInViewInterceptor.setPersistenceUnitName("blog");
                return openEntityManagerInViewInterceptor;
            }

            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addWebRequestInterceptor(openBlogEntityManagerInViewInterceptor());
            }

        }
    }

}
