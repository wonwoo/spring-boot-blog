package me.wonwoo.support.sidebar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by wonwoo on 2017. 3. 1..
 */

public class SidebarContentsTests {

  private final SidebarContents sidebarContents = new SidebarContents();

  @Test
  public void sidebarTest() {
    Document doc = Jsoup.parse("<h2>test</h2>");
    final String sidebar = sidebarContents.sidebar(doc);
    assertThat(sidebar).isEqualTo("<div class='right-pane-widget--container'>\n" +
      "<div class='related_resources'>\n" +
      "<h3><a name='table-of-sidebar' class='anchor' href='#table-of-sidebar'></a>Table of sidebar</h3><ul class='sectlevel1'>\n" +
      "<li><a href=\"#test\" name=\"test\">test</a></li></ul></div></div>");
  }
}