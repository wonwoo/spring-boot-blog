package me.wonwoo.domain.model;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wonwoo on 2016. 8. 15..
 */
@Entity
@Getter
public class Category {

  @Id
  @GeneratedValue
  private Long id;

  private String name;

  private LocalDateTime regDate;

  @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
  private List<Post> post = new ArrayList<>();

  Category(){
  }

  public Category(Long id){
    this.id = id;
  }

  public Category(String name){
    this.name = name;
    this.regDate = LocalDateTime.now();
  }
}
