package me.wonwoo.domain;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "blog.datasource")
@Data
public class BlogProperties {
    private String driverClassName;
    private String url;
    private String username;
    private String password;
    private int minIdle;
    private int maxPoolSize;
}
