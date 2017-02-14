package me.wonwoo.support.github.page;

import lombok.Data;

@Data
public class GithubPage {
    private String name;
    private String path;
    private Long size;
    private String content;

}
