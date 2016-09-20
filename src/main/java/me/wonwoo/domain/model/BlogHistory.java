package me.wonwoo.domain.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * Created by wonwoo on 2016. 9. 20..
 */
@Entity
@Data
public class BlogHistory {

  @Id @GeneratedValue
  private Long id;

  private String fullUrl;

  private String url;

  private String ip;

  private String navigation;

  private String referer;

  private LocalDateTime date;

  BlogHistory(){
  }
  public BlogHistory(String url , String fullUrl, String ip, String navigation, String referer){
    this.url = url;
    this.fullUrl = fullUrl;
    this.ip = ip;
    this.navigation = navigation;
    this.referer = referer;
  }
}
