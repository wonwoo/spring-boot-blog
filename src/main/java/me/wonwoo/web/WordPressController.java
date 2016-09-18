package me.wonwoo.web;

import lombok.RequiredArgsConstructor;
import me.wonwoo.wordpress.WordPress;
import me.wonwoo.wordpress.WordPressClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Created by wonwoo on 2016. 9. 6..
 */
@RequiredArgsConstructor
@Controller
@RequestMapping("/wordPress")
@Navigation(Section.WORDPRESS)
public class WordPressController {

  private final WordPressClient wordPressClient;

  @GetMapping
  public String findAll(Model model, @PageableDefault(size = 3) Pageable pageable, @RequestParam(required = false, defaultValue = "") String q) {
    model.addAttribute("wordPresses", wordPressClient.findAll(pageable, q));
    return "wordpress/wordPresses";
  }

  @GetMapping("/{id}")
  public String findAll(@PathVariable Long id, Model model) {
    model.addAttribute("wordPress", wordPressClient.findOne(id));
    return "wordpress/wordPress";
  }
}
