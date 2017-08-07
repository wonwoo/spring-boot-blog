package me.wonwoo.dto;


import org.junit.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by wonwoolee on 2017. 8. 7..
 */
public class PostDtoTests {

  @Test
  public void post() {
    PostDto postDto = new PostDto();

    postDto.setId(120L);
    postDto.setTitle("test title");
    postDto.setContent("test content");
    postDto.setCategoryId(Arrays.asList(100L, 120L));
    postDto.setCategoryName(Arrays.asList("jpa", "spring"));
    postDto.setCode("test content");
    postDto.setTags("jpa,spring,blog");

    assertThat(postDto.getId()).isEqualTo(120L);
    assertThat(postDto.getTitle()).isEqualTo("test title");
    assertThat(postDto.getContent()).isEqualTo("test content");
    assertThat(postDto.getCategoryId()).isEqualTo(Arrays.asList(100L, 120L));
    assertThat(postDto.getCategoryName()).isEqualTo(Arrays.asList("jpa", "spring"));
    assertThat(postDto.getCode()).isEqualTo("test content");
    assertThat(postDto.tags()).isEqualTo(Arrays.asList("jpa", "spring", "blog"));
    assertThat(postDto.getTags()).isEqualTo("jpa,spring,blog");
  }

}