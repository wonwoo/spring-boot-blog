package me.wonwoo.dto;

import javax.validation.constraints.NotBlank;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CommentDto {
  @NotNull
  private Long postId;

  @NotBlank
  private String content;
}
