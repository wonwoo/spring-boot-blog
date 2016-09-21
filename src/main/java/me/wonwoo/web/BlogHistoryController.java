package me.wonwoo.web;

import lombok.RequiredArgsConstructor;
import me.wonwoo.domain.model.BlogHistory;
import me.wonwoo.elasticsearch.ElasticService;
import me.wonwoo.service.BlogHistoryService;
import org.elasticsearch.bootstrap.Elasticsearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static me.wonwoo.web.Section.HISTORY;

/**
 * Created by wonwoo on 2016. 9. 20..
 */

@RequiredArgsConstructor
@Controller
@Navigation(HISTORY)
@RequestMapping("/histories")
public class BlogHistoryController {

  private final BlogHistoryService blogHistoryService;
  private final ElasticService elasticService;


  //TODO public?
  @GetMapping
  public String findByDateBetween(Model model, @PageableDefault(sort = "date", direction = Sort.Direction.DESC) Pageable pageable){
    model.addAttribute("histories", blogHistoryService.findByDateBetween(pageable));
    model.addAttribute("grouping", elasticService.findByGroupByNavigation());
    return "history/list";
  }
}
