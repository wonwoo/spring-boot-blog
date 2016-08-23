package me.wonwoo.domain.model;

import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by wonwoo on 2016. 8. 23..
 */
@Entity
@Getter
public class User implements Serializable {
  @GeneratedValue
  @Id
  private Long id;

  private String email;

  private String name;

  private String github;

  private String avatarUrl;

  @Column
  @Lob
  private String bio;

  public User(String email, String name, String github, String avatarUrl){
    this.email = email;
    this.name = name;
    this.github = github;
    this.avatarUrl = avatarUrl;
  }
  User(){
  }
}
