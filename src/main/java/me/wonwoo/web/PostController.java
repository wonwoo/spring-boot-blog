package me.wonwoo.web;

import lombok.RequiredArgsConstructor;
import me.wonwoo.domain.model.Category;
import me.wonwoo.domain.model.Comment;
import me.wonwoo.domain.model.Post;
import me.wonwoo.domain.model.User;
import me.wonwoo.domain.repository.CategoryRepository;
import me.wonwoo.domain.repository.PostRepository;
import me.wonwoo.dto.CommentDto;
import me.wonwoo.dto.PostDto;
import me.wonwoo.service.PostService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by wonwoo on 2016. 8. 15..
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

  private final PostRepository postRepository;

  private final PostService postService;

  private final CategoryRepository categoryRepository;

  @ModelAttribute("categories")
  public List<Category> categories(){
    return categoryRepository.findAll();
  }

  @GetMapping("/{id}")
  public String findByPost(@PathVariable Long id, Model model , @ModelAttribute CommentDto commentDto) {
    model.addAttribute("post", postRepository.findOne(id));
    return "post";
  }

  @GetMapping
  public String post(PostDto.CreatePost createPost) {
    return "markdown";
  }

  @PostMapping
  public String createPost(@ModelAttribute @Valid PostDto.CreatePost createPost, BindingResult bindingResult, @AuthenticationPrincipal User user, Model model) {
    if(bindingResult.hasErrors()){
      return "markdown";
    }
    Post post = new Post(createPost.getTitle(),
                        createPost.getContent(),
                        new Category(createPost.getCategoryId() == null ? 1L : createPost.getCategoryId()),user);
    Post newPost = postService.createPost(post);
    model.addAttribute("post", newPost);
    return "redirect:/posts/" +  newPost.getId();

  }
}
