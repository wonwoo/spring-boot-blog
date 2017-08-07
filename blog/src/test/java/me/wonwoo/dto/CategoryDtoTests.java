package me.wonwoo.dto;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by wonwoolee on 2017. 8. 7..
 */
public class CategoryDtoTests {

  @Test
  public void category() {
    CategoryDto categoryDto = new CategoryDto();
    categoryDto.setId(1L);
    categoryDto.setName("jpa");
    assertThat(categoryDto.getId()).isEqualTo(1L);
    assertThat(categoryDto.getName()).isEqualTo("jpa");
  }

}