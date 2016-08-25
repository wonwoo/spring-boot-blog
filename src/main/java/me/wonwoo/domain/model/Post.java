package me.wonwoo.domain.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by wonwoo on 2016. 8. 15..
 */
@Entity
@Getter
@EntityListeners(value = AuditingEntityListener.class)
public class Post {

  @Id
  @GeneratedValue
  private Long id;

  @NotNull
  private String title;

  @Lob
  @NotNull
  private String content;

  @CreatedDate
  private LocalDateTime regDate;

  @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
  private List<Comment> comments;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "USER_ID")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "CATEGORY_ID")
  private Category category;

  Post(){
  }

  public Post(Long id){
    this.id = id;
  }

  public Post(String title, String content, Category category, User user){
    this.title = title;
    this.content = content;
    this.category = category;
    this.user = user;
  }
}
