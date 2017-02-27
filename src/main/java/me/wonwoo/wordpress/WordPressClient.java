package me.wonwoo.wordpress;

import me.wonwoo.support.client.Client;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriUtils;

import java.io.UnsupportedEncodingException;
import java.util.Collections;

/**
 * Created by wonwoo on 2016. 9. 6..
 */
@Service
public class WordPressClient extends Client {

  private static final String WP_API = "https://public-api.wordpress.com/rest/v1.1/sites/";
  private static final String MY_SITE = "aoruqjfu.fun25.co.kr/";
  private static final String POST = "posts";

  public WordPressClient(RestTemplate restTemplate) {
    super(restTemplate);
  }

  @Cacheable("wp.posts")
  public Page<WordPress> findAll(Pageable pageable, String q) {
    String url;
    try {
      url = String.format(
        WP_API + MY_SITE + POST + "?number=%s&page=%s&search=%s&fields=ID,content,title,date,author,tags",
        pageable.getPageSize(), pageable.getPageNumber() + 1, UriUtils.encode(q, "UTF-8"));
    } catch (UnsupportedEncodingException e) {
      return new PageImpl<>(Collections.emptyList(), pageable, 0);
    }
    WordPresses body = invoke(createRequestEntity(url), WordPresses.class).getBody();
    return new PageImpl<>(body.getPosts(), pageable, body.getFound());
  }

  // FIXME: 2017. 2. 27. 추후 수정
  @Cacheable("wp.post")
  public WordPress findOne(Long id) {
    String url = String.format(
      WP_API + MY_SITE + POST + "/%s??fields=ID,content,title,date,author,tags", id);
    final WordPress body = invoke(createRequestEntity(url), WordPress.class).getBody();
    final Document parse = Jsoup.parse(body.getContent());
    final String tableOfContents = findTableOfContents(parse);
    body.setContent(parse.select("body").toString());
    String sidebar = "<div class='right-pane-widget--container'>\n" +
      "<div class='related_resources'>\n";

    sidebar += "<h3>" +
      "<a name='table-of-contents' class='anchor' href='#table-of-contents'></a>" +
      "Table of contents</h3><ul class='sectlevel1'>\n";
    sidebar += tableOfContents.replaceAll("h2", "li").replaceAll("h3", "li");
    sidebar += "</ul></div></div>";
    body.setTableOfContent(sidebar);
    return body;
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
