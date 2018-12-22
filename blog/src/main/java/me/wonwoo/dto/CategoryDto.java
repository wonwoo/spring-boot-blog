package me.wonwoo.dto;

import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Created by wonwoo on 2016. 8. 24..
 */
@Data
public class CategoryDto {
  private Long id;
  @NotBlank
  private String name;
}
