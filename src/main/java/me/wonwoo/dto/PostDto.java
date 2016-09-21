package me.wonwoo.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by wonwoo on 2016. 8. 22..
 */
@Data
public class PostDto {

  private Long id;
  @NotBlank
  private String title;
  @NotBlank
  private String content;
  @NotNull
  private Long categoryId;

  private String categoryName;

  private String code;

  private String tags;

  public List<String> tags(){
    if(tags != null && !tags.isEmpty()){
      return Arrays.asList(tags.split(","));
    }
    return Collections.emptyList();
  }
}
