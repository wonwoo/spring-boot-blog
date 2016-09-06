package me.wonwoo.web;

import lombok.RequiredArgsConstructor;
import me.wonwoo.wordpress.WordPress;
import me.wonwoo.wordpress.WordPressClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
  public String findAll(Model model, @PageableDefault(size = 3) Pageable pageable) {
    model.addAttribute("wordPresses", wordPressClient.findAll(pageable));
    return "wordpress/wordPresses";
  }

  //TODO 검새 추가 및 단건? 단건은 일단 보류
//  @GetMapping("test")
//  @ResponseBody
//  public Page<WordPress> findAll(@PageableDefault(size = 3) Pageable pageable) {
//    return wordPressClient.findAll(pageable);
//  }
}
