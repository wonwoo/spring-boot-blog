package me.wonwoo.web;

import java.util.List;

import org.pegdown.PegDownProcessor;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import me.wonwoo.config.PostProperties;
import me.wonwoo.domain.model.Category;
import me.wonwoo.domain.model.Post;
import me.wonwoo.domain.repository.CategoryRepository;
import me.wonwoo.domain.repository.PostRepository;
import me.wonwoo.dto.SearchForm;
import me.wonwoo.support.elasticsearch.PostElasticSearchService;
import me.wonwoo.support.elasticsearch.WpPosts;
import me.wonwoo.support.sidebar.SidebarContents;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang.StringEscapeUtils.unescapeHtml;
import static org.springframework.data.domain.ExampleMatcher.matching;

/**
 * Created by wonwoo on 2016. 8. 15..
 */
@Controller
@RequiredArgsConstructor
@Navigation(Section.HOME)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class IndexController {

  PostProperties postProperties;
  CategoryRepository categoryRepository;
  PostRepository postRepository;
  PegDownProcessor pegDownProcessor;
  PostElasticSearchService postElasticSearchService;
  SidebarContents sidebarContents;


  @ModelAttribute("theme")
  public String theme(){
    return postProperties.getTheme();
  }

  @ModelAttribute("categories")
  public List<Category> categories(){
    return categoryRepository.findAll();
  }

  @ModelAttribute("show")
  public boolean show(){
    return postProperties.isFull();
  }

  @GetMapping({"/", "index"})
  public String home(SearchForm searchForm, Model model, @PageableDefault(size = 3, sort = "regDate", direction = Sort.Direction.DESC) Pageable pageable) {

    Example<Post> post = Example.of(new Post(searchForm.getQ(), "Y"),
            matching()
                    .withMatcher("title", ExampleMatcher.GenericPropertyMatcher::contains));
    model.addAttribute("posts", postRepository.findAll(post, pageable));
    model.addAttribute("show", postProperties.isFull());
    return "index";
  }

  @GetMapping("/search")
  public String search(Model model, @PageableDefault(size = 3) Pageable pageable, @ModelAttribute SearchForm searchForm) {
    Page<WpPosts> wpPostses = postElasticSearchService.searchWpPosts(searchForm.getQ(), pageable);
    List<WpPosts> wpPosts = wpPostses.getContent().stream().peek(content -> {
      String postContent = pegDownProcessor.markdownToHtml(unescapeHtml(content.getHighlightedContent()));
      String em1 = postContent.replace("&lt;highlight&gt;", "<highlight>");
      String em2 = em1.replace("&lt;/highlight&gt;", "</highlight>");
      content.setPostContent(em2);
    }).collect(toList());
    model.addAttribute("posts",new PageImpl<>(wpPosts, pageable, wpPostses.getTotalElements()));
    return "search";
  }


}
