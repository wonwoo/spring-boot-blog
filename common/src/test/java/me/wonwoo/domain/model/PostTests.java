package me.wonwoo.domain.model;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by wonwoolee on 2017. 8. 7..
 */
public class PostTests {

  @Test
  public void post() {
    Post post =
        new Post("post title", "post content", "post code", "Y",
            new Category("jpa"),
                new User(), Arrays.asList("jpa","spring" ));
    post.setId(1L);
    assertThat(post.getId()).isEqualTo(1);
    assertThat(post.getTitle()).isEqualTo("post title");
    assertThat(post.getContent()).isEqualTo("post content");
    assertThat(post.getCode()).isEqualTo("post code");
    assertThat(post.getYn()).isEqualTo("Y");
    assertThat(post.getCategory()).isEqualTo(new Category("jpa"));
    assertThat(post.getTags()).isEqualTo(Arrays.asList(new Tag("jpa"), new Tag("spring")));
  }

  @Test
  public void post1() {
    Post post = new Post("post title", "Y");
    assertThat(post.getTitle()).isEqualTo("post title");
    assertThat(post.getYn()).isEqualTo("Y");
  }
}