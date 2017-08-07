package me.wonwoo.config;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by wonwoolee on 2017. 8. 7..
 */

public class PostPropertiesTests {

  @Test
  public void postPropertiesEmpty() {
    PostProperties postProperties = new PostProperties();
    assertThat(postProperties.getTheme()).isEqualTo("github-gist");
    assertThat(postProperties.isFull()).isEqualTo(false);
  }

  @Test
  public void postProperties() {
    PostProperties postProperties = new PostProperties();
    postProperties.setFull(true);
    postProperties.setTheme("googlecode");
    assertThat(postProperties.getTheme()).isEqualTo("googlecode");
    assertThat(postProperties.isFull()).isEqualTo(true);
  }

}