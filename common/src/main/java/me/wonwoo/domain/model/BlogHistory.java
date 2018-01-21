package me.wonwoo.domain.model;

import java.time.LocalDateTime;

import javax.persistence.Id;

import org.springframework.data.elasticsearch.annotations.Document;

import lombok.Data;

/**
 * Created by wonwoo on 2016. 9. 20..
 */
@Document(indexName = "blog", type = "history", shards = 1, replicas = 0, refreshInterval = "-1")
@Data
public class BlogHistory {

  @Id
  private Long id;

  private String fullUrl;

  private String url;

  private String ip;

  private String navigation;

  private String referer;

  private LocalDateTime date;

  BlogHistory() {
  }

  public BlogHistory(String url, String fullUrl, String ip, String navigation, String referer, LocalDateTime date) {
    this.url = url;
    this.fullUrl = fullUrl;
    this.ip = ip;
    this.navigation = navigation;
    this.referer = referer;
    this.date = date;
  }
}
