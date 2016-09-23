package me.wonwoo.wordpress;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "wordpress.datasource")
@Data
public class WordPressProperties {
    private String driverClassName;
    private String url;
    private String username;
    private String password;
    private int minIdle;
    private int maxPoolSize;
}
