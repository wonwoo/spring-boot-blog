package me.wonwoo.web;

/**
 * Created by wonwoo on 2016. 9. 6..
 */
public enum Section {
  HOME("Home"),
  GUIDES("Spring Guides"),
  TUTORIAL("Spring Tutorial"),
  NEWS("News"),
  POST("Post"),
  HISTORY("Blog History"),
  GITHUB("GitHub Page"),
  WORDPRESS("WordPress"),
  CATEGORY("Category");

  private String value;

  Section(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
