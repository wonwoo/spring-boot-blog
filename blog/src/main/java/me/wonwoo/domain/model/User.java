package me.wonwoo.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by wonwoo on 2016. 8. 23..
 */
@Entity
@Getter
@ToString(exclude = {"comments", "post"})
@EqualsAndHashCode(exclude = {"comments", "post"})
public class User implements Serializable ,UserDetails {
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

  private static Collection<? extends GrantedAuthority> authorities(User user) {
    List<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
    if (user.isAdmin()) {
      authorities.addAll(AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER, ROLE_ADMIN, ROLE_ACTUATOR"));
    }
    return authorities;
  }

  @Column
  @Lob
  private String bio;

  public User(String email, String name, String github, String avatarUrl, String password, boolean isAdmin) {
    super();
    this.email = email;
    this.name = name;
    this.github = github;
    this.avatarUrl = avatarUrl;
    this.password = password;
    this.isAdmin = isAdmin;
  }

  public User() {

  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities(this);
  }

  @Override
  public String getUsername() {
    return this.github;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
