package me.wonwoo.web;

import lombok.RequiredArgsConstructor;
import me.wonwoo.domain.repository.PostRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
  public String home(Model model, @PageableDefault(size = 5, sort = "regDate" ,direction = Sort.Direction.DESC)  Pageable pageable){
    model.addAttribute("posts", postRepository.findAll(pageable));
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
