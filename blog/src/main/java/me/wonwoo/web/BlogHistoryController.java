package me.wonwoo.web;

import me.wonwoo.service.BlogHistoryService;
import me.wonwoo.support.elasticsearch.ElasticService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by wonwoo on 2017. 2. 14..
 */
@Controller
@Navigation(Section.HISTORY)
@RequestMapping("/histories")
public class BlogHistoryController {

  private final BlogHistoryService blogHistoryService;
  private final ElasticService elasticService;

  public BlogHistoryController(BlogHistoryService blogHistoryService, ElasticService elasticService) {
    this.blogHistoryService = blogHistoryService;
    this.elasticService = elasticService;
  }

  @GetMapping
  public String findByDateBetween(Model model, @PageableDefault(sort = "date", direction = Sort.Direction.DESC) Pageable pageable) {
    model.addAttribute("histories", blogHistoryService.findByDateBetween(pageable));
    model.addAttribute("grouping", elasticService.findByGroupByNavigation());
    return "history/list";
  }
}
