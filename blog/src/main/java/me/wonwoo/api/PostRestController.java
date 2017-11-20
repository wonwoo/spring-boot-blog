package me.wonwoo.api;

import me.wonwoo.support.elasticsearch.PostElasticSearchService;
import me.wonwoo.support.elasticsearch.WpPosts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PostRestController {
  private final PostElasticSearchService postElasticSearchService;

  public PostRestController(PostElasticSearchService postElasticSearchService) {
    this.postElasticSearchService = postElasticSearchService;
  }

  @GetMapping("/posts")
  public Page<WpPosts> posts(@PageableDefault(size = 5, sort = "post_date", direction = Sort.Direction.DESC) Pageable pageable) {
    Page<WpPosts> wpPosts = postElasticSearchService.wpPosts(pageable);
    return wpPosts;
  }

}
