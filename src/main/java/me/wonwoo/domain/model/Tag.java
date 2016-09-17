package me.wonwoo.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

/**
 * Created by wonwoo on 2016. 9. 17..
 */
@Entity
@Data
@ToString(exclude = "post")
@EqualsAndHashCode(exclude = "post")
public class Tag {
  @Id @GeneratedValue
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "POST_ID")
  private Post post;

  private String tag;

  Tag(){
  }

  public Tag(Post post, String tag){
    this.post = post;
    this.tag = tag;
  }
}
