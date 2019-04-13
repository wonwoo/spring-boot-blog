package me.wonwoo.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;

/**
 * Created by wonwoo on 2016. 8. 24..
 */
@Data
public class CategoryDto {
  private Long id;
  @NotBlank
  private String name;
}
