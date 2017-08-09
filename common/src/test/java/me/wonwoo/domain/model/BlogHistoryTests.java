package me.wonwoo.domain.model;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by wonwoolee on 2017. 8. 9..
 */
public class BlogHistoryTests {

  @Test
  public void blogHistory() {
    BlogHistory blogHistory = new BlogHistory("/accounts", "http://localhost:8080/accounts", "127.0.0.1", "USER", null, LocalDateTime.now());
    assertThat(blogHistory.getUrl()).isEqualTo("/accounts");
    assertThat(blogHistory.getFullUrl()).isEqualTo("http://localhost:8080/accounts");
    assertThat(blogHistory.getIp()).isEqualTo("127.0.0.1");
    assertThat(blogHistory.getNavigation()).isEqualTo("USER");
    assertThat(blogHistory.getDate()).isNotNull();
  }
}