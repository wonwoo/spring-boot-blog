/*
 * ****************************************************************************
 *
 *
 *  Copyright(c) 2015 Helloworld. All rights reserved.
 *
 *  This software is the proprietary information of Helloworld.
 *
 *
 * ***************************************************************************
 */

package me.wonwoo.config;

import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Helloworld
 * User : wonwoo
 * Date : 2016-08-25
 * Time : 오후 3:18
 * desc :
 */
@Configuration
public class DataSourceConfig {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Bean
    public DataSource dataSource() throws URISyntaxException {
        URI dbUri = new URI(System.getenv("CLEARDB_DATABASE_URL"));
        logger.info("db url {} ", dbUri);
        logger.info(dbUri.getHost() + dbUri.getPath());
        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:mysql://" + dbUri.getHost() + dbUri.getPath();
        HikariDataSource basicDataSource = new HikariDataSource();
        basicDataSource.setJdbcUrl(dbUrl);
        logger.info(dbUrl);
        basicDataSource.setUsername(username);
        basicDataSource.setPassword(password);
        return basicDataSource;
    }
}
