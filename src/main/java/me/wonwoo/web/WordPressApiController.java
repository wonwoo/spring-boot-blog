package me.wonwoo.web;

import lombok.RequiredArgsConstructor;
import me.wonwoo.dto.SearchForm;
import me.wonwoo.wordpress.WordPressClient;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by wonwoo on 2017. 2. 26..
 */
@RequiredArgsConstructor
@Controller
@RequestMapping("/wordPress")
@Navigation(Section.WORDPRESS_API)
public class WordPressApiController {

  private final WordPressClient wordPressClient;

  @GetMapping("/api")
  public String findAllApi(Model model, @PageableDefault(size = 3) Pageable pageable, @ModelAttribute SearchForm searchForm) {
    model.addAttribute("wordPresses", wordPressClient.findAll(pageable, searchForm.getQ()));
    return "wordpress/api/wordPresses";
  }

  @GetMapping("/api/{id}")
  public String findOneApi(@PathVariable Long id, Model model) {
    model.addAttribute("wordPress", wordPressClient.findOne(id));
    return "wordpress/api/wordPress";
  }
}
