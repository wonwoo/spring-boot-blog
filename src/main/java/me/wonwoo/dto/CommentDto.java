package me.wonwoo.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by Helloworld
 * User : wonwoo
 * Date : 2016-08-25
 * Time : 오후 1:26
 * desc :
 */
@Data
public class CommentDto {
    @NotNull
    private Long postId;

    @NotBlank
    private String content;
}
