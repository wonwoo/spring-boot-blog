package me.wonwoo.domain.model;

import java.time.LocalDateTime;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by wonwoolee on 2017. 8. 7..
 */
public class CategoryTests {

  @Test
  public void category() {
    Category category = new Category(1L, "jpa", LocalDateTime.now());
    category.setName("spring");
    assertThat(category.getId()).isEqualTo(1L);
    assertThat(category.getName()).isEqualTo("spring");
    assertThat(category.getRegDate()).isNotNull();
  }

  @Test
  public void category1() {
    Category category = new Category(1L, "jpa");
    assertThat(category.getId()).isEqualTo(1L);
    assertThat(category.getName()).isEqualTo("jpa");
  }

  @Test
  public void category2() {
    Category category = new Category(1L);
    assertThat(category.getId()).isEqualTo(1L);
  }
}