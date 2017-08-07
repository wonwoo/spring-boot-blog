package me.wonwoo.domain.model;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by wonwoolee on 2017. 8. 7..
 */
public class PostTests {

  @Test
  public void post() {
    Post post =
        new Post("post title", "post content", "post code", "Y",
            Collections.singletonList(new Category("jpa")),
                new User(), Arrays.asList("jpa","spring" ));
    post.setId(1L);
    assertThat(post.getId()).isEqualTo(1);
    assertThat(post.getTitle()).isEqualTo("post title");
    assertThat(post.getContent()).isEqualTo("post content");
    assertThat(post.getCode()).isEqualTo("post code");
    assertThat(post.getYn()).isEqualTo("Y");
    assertThat(post.getCategoryPost()).isEqualTo(Collections.singletonList(new CategoryPost(new Category("jpa"), post)));
    assertThat(post.getTags()).isEqualTo(Arrays.asList(new Tag("jpa"), new Tag("spring")));




  }
}