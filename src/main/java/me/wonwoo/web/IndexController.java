package me.wonwoo.web;

import lombok.RequiredArgsConstructor;
import me.wonwoo.config.PostProperties;
import me.wonwoo.domain.model.Category;
import me.wonwoo.domain.model.Post;
import me.wonwoo.domain.repository.CategoryRepository;
import me.wonwoo.domain.repository.PostRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static org.springframework.data.domain.ExampleMatcher.matching;

/**
 * Created by wonwoo on 2016. 8. 15..
 */
@Controller
@RequiredArgsConstructor
@Navigation(Section.HOME)
public class IndexController {

  private final PostRepository postRepository;
  private final PostProperties postProperties;
  private final CategoryRepository categoryRepository;

  @ModelAttribute("theme")
  public String theme(){
    return postProperties.getTheme();
  }

  @ModelAttribute("categories")
  public List<Category> categories(){
    return categoryRepository.findAll();
  }

  @GetMapping({"/", "index"})
  public String home(@RequestParam(required = false) String q, Model model, @PageableDefault(size = 5, sort = "regDate", direction = Sort.Direction.DESC) Pageable pageable) {

    Example<Post> post = Example.of(new Post(q, "Y"),
      matching()
        .withMatcher("title", ExampleMatcher.GenericPropertyMatcher::contains));
    model.addAttribute("posts", postRepository.findAll(post, pageable));
    model.addAttribute("show", postProperties.isFull());
    return "index";
  }
}
