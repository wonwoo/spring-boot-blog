package me.wonwoo.web

import me.wonwoo.support.elasticsearch.ElasticService
import me.wonwoo.service.BlogHistoryService
import org.springframework.data.domain.{Pageable, Sort}
import org.springframework.data.web.PageableDefault
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.{GetMapping, RequestMapping}

/**
  * Created by wonwoo on 2016. 9. 21..
  */
@Controller
@Navigation(Section.HISTORY)
@RequestMapping(Array("/histories"))
class BlogHistoryController(val blogHistoryService: BlogHistoryService, val elasticService: ElasticService) {

  @GetMapping
  def findByDateBetween(model: Model, @PageableDefault(sort = Array("date"), direction = Sort.Direction.DESC) pageable: Pageable) = {
    model addAttribute("histories", blogHistoryService findByDateBetween pageable )
    model addAttribute("grouping", elasticService findByGroupByNavigation)
    "history/list"
  }
}
