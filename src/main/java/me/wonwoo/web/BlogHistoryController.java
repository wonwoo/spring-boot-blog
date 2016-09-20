package me.wonwoo.web;

import lombok.RequiredArgsConstructor;
import me.wonwoo.domain.model.BlogHistory;
import me.wonwoo.service.BlogHistoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
@RequestMapping("/history")
public class BlogHistoryController {

  private final BlogHistoryService blogHistoryService;

  @GetMapping
  public String findByDateBetween(Model model, Pageable pageable){
    model.addAttribute("histories", blogHistoryService.findByDateBetween(pageable));
    return "history/list";
  }
}
