package me.wonwoo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by wonwoo on 2016. 8. 29..
 */
@ConfigurationProperties("post")
@Data
public class PostProperties {
  private boolean full;
  private String theme = "github-gist";
}
