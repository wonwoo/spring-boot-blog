package me.wonwoo.web;

import lombok.RequiredArgsConstructor;
import me.wonwoo.domain.model.Post;
import me.wonwoo.domain.repository.PostRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by wonwoo on 2016. 8. 15..
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

  private final PostRepository postRepository;

  @GetMapping("{id}")
  public String findByPost(@PathVariable Long id, Model model){
    model.addAttribute("post" ,postRepository.findOne(id));
    return "post";
  }

  @GetMapping
  public String post(){
    return "markdown";
  }

}
