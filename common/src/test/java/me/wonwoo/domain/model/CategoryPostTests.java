package me.wonwoo.domain.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by wonwoolee on 2017. 8. 7..
 */
public class CategoryPostTests {

  @Test
  public void categoryPost() {
    CategoryPost categoryPost = new CategoryPost(new Category("spring"), new Post(1L));
    assertThat(categoryPost.getCategory()).isEqualTo(new Category("spring"));
    assertThat(categoryPost.getPost()).isEqualTo(new Post(1L));
  }

  @Test
  public void categoryPost1() {
    CategoryPost categoryPost = new CategoryPost(new Category("spring"));
    assertThat(categoryPost.getCategory()).isEqualTo(new Category("spring"));
  }
}