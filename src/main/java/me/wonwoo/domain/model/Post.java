package me.wonwoo.domain.model;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by wonwoo on 2016. 8. 15..
 */
@Entity
@Getter
public class Post {

  @Id
  @GeneratedValue
  private Long id;

  private String title;

  @Lob
  private String content;

  private LocalDateTime regDate;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "CATEGORY_ID")
  private Category category;

  Post(){
  }

  public Post(String title, String content, Category category){
    this.title = title;
    this.content = content;
    this.category = category;
    this.regDate = LocalDateTime.now();
  }
}
