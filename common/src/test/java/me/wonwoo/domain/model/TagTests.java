package me.wonwoo.domain.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by wonwoolee on 2017. 8. 7..
 */
public class TagTests {

  @Test
  public void tag() {
    Tag tag1 = new Tag("jpa");
    Tag tag2 = new Tag("jpa");
    assertThat(tag1).isEqualTo(tag2);
  }
}