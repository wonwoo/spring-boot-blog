package me.wonwoo.domain.model;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by wonwoolee on 2017. 8. 7..
 */
public class WpPostTests {

  @Test
  public void wpPost() {
    WpPost wpPost = new WpPost();
    wpPost.setId(1);
    wpPost.setPostTitle("post title");
    wpPost.setPostAuthor(1);
    wpPost.setPostContent("post content");
    wpPost.setPostDate(LocalDateTime.now());
    wpPost.setPostType("post");
    wpPost.setPostStatus("publish");
    wpPost.setPostContentFiltered("post content filtered");
    wpPost.setIndexing("Y");
    wpPost.setPostModified(LocalDateTime.now());

    assertThat(wpPost.getId()).isEqualTo(1);
    assertThat(wpPost.getPostTitle()).isEqualTo("post title");
    assertThat(wpPost.getPostAuthor()).isEqualTo(1);
    assertThat(wpPost.getPostContent()).isEqualTo("post content");
    assertThat(wpPost.getPostDate()).isNotNull();
    assertThat(wpPost.getPostType()).isEqualTo("post");
    assertThat(wpPost.getPostStatus()).isEqualTo("publish");
    assertThat(wpPost.getPostContentFiltered()).isEqualTo("post content filtered");
    assertThat(wpPost.getIndexing()).isEqualTo("Y");
    assertThat(wpPost.getPostModified()).isNotNull();

  }
}