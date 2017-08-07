package me.wonwoo.domain.model;


import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by wonwoolee on 2017. 8. 7..
 */
public class UserTests {

  @Test
  public void user() {
    User user = new User("test@test.com", "wonwoo", "wonwoo", "http://localhost:8080", "test123", true);
    assertThat(user.getEmail()).isEqualTo("test@test.com");
    assertThat(user.getName()).isEqualTo("wonwoo");
    assertThat(user.getGithub()).isEqualTo("wonwoo");
    assertThat(user.getAvatarUrl()).isEqualTo("http://localhost:8080");
    assertThat(user.getPassword()).isEqualTo("test123");
    assertThat(user.isAccountNonExpired()).isEqualTo(true);
    assertThat(user.isAccountNonLocked()).isEqualTo(true);
    assertThat(user.isCredentialsNonExpired()).isEqualTo(true);
    assertThat(user.isEnabled()).isEqualTo(true);
  }

}