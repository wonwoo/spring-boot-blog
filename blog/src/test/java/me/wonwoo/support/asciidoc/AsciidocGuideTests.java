package me.wonwoo.support.asciidoc;

import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by wonwoolee on 2017. 8. 9..
 */
public class AsciidocGuideTests {

  @Test
  public void asciidocGuide() {
    Set<String> tags = new HashSet<>();
    tags.add("spring");
    tags.add("spring boot");
    Set<String> projects = new HashSet<>();
    projects.add("spring-boot");
    projects.add("spring-test");

    AsciidocGuide asciidocGuide =
        new AsciidocGuide("test content", tags, projects, "left table", new HashMap<>());

    assertThat(asciidocGuide.getContent()).isEqualTo("test content");
    assertThat(asciidocGuide.getTags()).contains("spring", "spring boot");
    assertThat(asciidocGuide.getProjects()).contains("spring-boot", "spring-test");
    assertThat(asciidocGuide.getTableOfContents()).isEqualTo("left table");

  }
}