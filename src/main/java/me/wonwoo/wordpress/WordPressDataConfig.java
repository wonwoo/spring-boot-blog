package me.wonwoo.wordpress;

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
@EnableJpaRepositories(entityManagerFactoryRef = "wordPressEntityManagerFactory",
        transactionManagerRef = "wordPressTransactionManager")
@RequiredArgsConstructor
class WordPressDataConfig {

    private final WordPressProperties wordPressProperties;

    @Bean
    PlatformTransactionManager wordPressTransactionManager() {
        return new JpaTransactionManager(wordPressEntityManagerFactory().getObject());
    }

    @Bean
    LocalContainerEntityManagerFactoryBean wordPressEntityManagerFactory() {
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setGenerateDdl(false);
        jpaVendorAdapter.setShowSql(true);

        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();

        Properties properties = new Properties();
        properties.setProperty("hibernate.implicit_naming_strategy","org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy");
        properties.setProperty("hibernate.physical_naming_strategy","org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy");

        factoryBean.setPersistenceUnitName("wordPress");

        factoryBean.setJpaProperties(properties);
        factoryBean.setDataSource(wordPressDataSource());
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        factoryBean.setPackagesToScan(WordPressDataConfig.class.getPackage().getName());
        return factoryBean;
    }

    @Bean
    DataSource wordPressDataSource() {
        HikariConfig config = new HikariConfig();
        config.setPoolName("wordPress-pool");
        config.setDataSourceClassName(wordPressProperties.getDriverClassName());
        config.setMinimumIdle(wordPressProperties.getMinIdle());
        config.setMaximumPoolSize(wordPressProperties.getMaxPoolSize());
        config.addDataSourceProperty("url", wordPressProperties.getUrl());
        config.addDataSourceProperty("user", wordPressProperties.getUsername());
        config.addDataSourceProperty("password", wordPressProperties.getPassword());
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        return new HikariDataSource(config);
    }

    @Configuration
    protected static class JpaWordPressWebConfiguration {

        // Defined as a nested config to ensure WebMvcConfigurerAdapter is not read when
        // not on the classpath
        @Configuration
        protected static class JpaWordPressWebMvcConfiguration extends WebMvcConfigurerAdapter {

            @Bean
            public OpenEntityManagerInViewInterceptor openWordPressEntityManagerInViewInterceptor() {
                OpenEntityManagerInViewInterceptor openEntityManagerInViewInterceptor = new OpenEntityManagerInViewInterceptor();
                openEntityManagerInViewInterceptor.setPersistenceUnitName("wordPress");
                return openEntityManagerInViewInterceptor;
            }

            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addWebRequestInterceptor(openWordPressEntityManagerInViewInterceptor());
            }

        }
    }
}
