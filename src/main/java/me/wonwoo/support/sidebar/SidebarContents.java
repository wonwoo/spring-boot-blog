package me.wonwoo.support.sidebar;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by wonwoo on 2017. 2. 28..
 */
public class SidebarContents {

  public String sidebar(Document doc) {
    String sidebar = "<div class='right-pane-widget--container'>\n" +
      "<div class='related_resources'>\n";

    sidebar += "<h3>" +
      "<a name='table-of-sidebar' class='anchor' href='#table-of-sidebar'></a>" +
      "Table of sidebar</h3><ul class='sectlevel1'>\n";
    sidebar += findTableOfContents(doc).replaceAll("h2", "li").replaceAll("h3", "li");
    sidebar += "</ul></div></div>";
    return sidebar;
  }

  private String findTableOfContents(Document doc) {
    Elements toc = doc.select("h2, h3");
    toc.forEach(part -> {
      final String text = part.text();
      part.empty();
      final Element a = part.appendElement("a");
      final String s = text.toLowerCase();
      final String replace = s.replaceAll(" ", "-");
      final Element href = a.attr("href", "#" + replace).attr("name", replace).text(text);
      part.removeAttr("text");
      part.appendChild(href);
    });
    return toc.toString();
  }
}
