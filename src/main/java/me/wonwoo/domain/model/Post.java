package me.wonwoo.domain.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Created by wonwoo on 2016. 8. 15..
 */
@Entity
@Getter
@Setter
public class Post {

  @Id
  @GeneratedValue
  private Long id;

  @NotNull
  private String title;

  @Lob
  @NotNull
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
