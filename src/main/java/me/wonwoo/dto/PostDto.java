package me.wonwoo.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by wonwoo on 2016. 8. 22..
 */
public class PostDto {

  @Data
  public static class CreatePost {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
//    @NotBlank 지금은 생략
    private Long categoryId;
  }
}
