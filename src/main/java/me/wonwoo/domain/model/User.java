package me.wonwoo.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wonwoo on 2016. 8. 23..
 */
@Entity
@Getter
@ToString(exclude = {"comments", "post"})
@EqualsAndHashCode(exclude = {"comments", "post"})
public class User implements Serializable {
  @GeneratedValue
  @Id
  private Long id;

  private String email;

  private String name;

  private String github;

  private String avatarUrl;

  private String password;

  private boolean isAdmin;

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  private List<Post> post = new ArrayList<>();

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  private List<Comment> comments = new ArrayList<>();

  @Column
  @Lob
  private String bio;

  public User(String email, String name, String github, String avatarUrl, String password, boolean isAdmin) {
    this.email = email;
    this.name = name;
    this.github = github;
    this.avatarUrl = avatarUrl;
    this.password = password;
    this.isAdmin = isAdmin;
  }

  User() {
  }
}
