package me.wonwoo.domain.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by wonwoolee on 2017. 8. 7..
 */
public class CommentTests {

  @Test
  public void comment() {
    Comment comment = new Comment("test comment", new Post(), new User());
    assertThat(comment.getContent()).isEqualTo("test comment");
    assertThat(comment.getPost()).isNotNull();
    assertThat(comment.getUser()).isNotNull();
  }
}