package me.wonwoo.web;

import lombok.RequiredArgsConstructor;
import me.wonwoo.domain.repository.PostRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by wonwoo on 2016. 8. 15..
 */
@Controller
@RequiredArgsConstructor
public class IndexController {

  private final PostRepository postRepository;

  @GetMapping("/")
  public String home(Model model){
    model.addAttribute("posts", postRepository.findAll());
    return "index";
  }

  @GetMapping("/about")
  public String about(){
    return "about";
  }

  @GetMapping("/contact")
  public String contact(){
    return "contact";
  }
}
