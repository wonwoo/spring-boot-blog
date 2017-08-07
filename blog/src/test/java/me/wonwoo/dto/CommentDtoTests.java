package me.wonwoo.dto;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by wonwoolee on 2017. 8. 7..
 */
public class CommentDtoTests {

  @Test
  public void comment() {
    CommentDto commentDto = new CommentDto();
    commentDto.setPostId(100L);
    commentDto.setContent("blabla");
    assertThat(commentDto.getPostId()).isEqualTo(100L);
    assertThat(commentDto.getContent()).isEqualTo("blabla");
  }
}