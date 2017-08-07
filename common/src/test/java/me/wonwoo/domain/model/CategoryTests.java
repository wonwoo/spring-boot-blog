package me.wonwoo.domain.model;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by wonwoolee on 2017. 8. 7..
 */
public class CategoryTests {

  @Test
  public void category() {
    Category category = new Category(1L, "jpa", LocalDateTime.now());
    assertThat(category.getId()).isEqualTo(1L);
    assertThat(category.getName()).isEqualTo("jpa");
    assertThat(category.getRegDate()).isNotNull();
  }
}