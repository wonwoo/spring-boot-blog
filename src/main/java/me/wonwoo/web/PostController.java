package me.wonwoo.web;

import lombok.RequiredArgsConstructor;
import me.wonwoo.domain.model.Category;
import me.wonwoo.domain.model.Post;
import me.wonwoo.domain.model.User;
import me.wonwoo.domain.repository.PostRepository;
import me.wonwoo.dto.CommentDto;
import me.wonwoo.dto.PostDto;
import me.wonwoo.exception.NotFoundException;
import me.wonwoo.service.CategoryService;
import me.wonwoo.service.PostService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

  private final CategoryService categoryService;

  @ModelAttribute("categories")
  public List<Category> categories(){
    return categoryService.findAll();
  }

  @GetMapping("/{id}")
  public String findByPost(@PathVariable Long id, Model model , @ModelAttribute CommentDto commentDto) {
    Post post = postRepository.findByIdAndYn(id, "Y");
    if(post == null){
      throw new NotFoundException(id + " not found");
    }
    model.addAttribute("post", post);
    return "post/post";
  }

  @GetMapping("/new")
  public String newPost(PostDto.CreatePost createPost) {
    return "post/new";
  }

  @GetMapping("/edit/{id}")
  public String editPost(@PathVariable Long id, Model model) {
    Post post = postRepository.findByIdAndYn(id, "Y");
    if(post == null){
      throw new NotFoundException(id + " not found");
    }
    PostDto.CreatePost createPost = new PostDto.CreatePost();

    createPost.setCategoryId(post.getCategory().getId());
    createPost.setCategoryName(post.getCategory().getName());
    createPost.setTitle(post.getTitle());
    createPost.setCode(post.getCode());
    createPost.setContent(post.getContent());
    createPost.setId(id);
    model.addAttribute("editPost", createPost);
    return "post/edit";
  }

  @PostMapping
  public String createPost(@ModelAttribute @Valid PostDto.CreatePost createPost, BindingResult bindingResult, @AuthenticationPrincipal User user, Model model) {
    if(bindingResult.hasErrors()){
      return "post/new";
    }
    Post post = new Post(createPost.getTitle(),
      createPost.getContent(),
      createPost.getCode(),
      "Y",
      new Category(createPost.getCategoryId() == null ? 1L : createPost.getCategoryId()),user);
    Post newPost = postService.createPost(post);
    model.addAttribute("post", newPost);
    return "redirect:/posts/" +  newPost.getId();
  }

  @PostMapping("/{id}/edit")
  public String modifyPost(@PathVariable Long id, @ModelAttribute("editPost") @Valid PostDto.CreatePost createPost, BindingResult bindingResult,@AuthenticationPrincipal User user) {
    if(bindingResult.hasErrors()){
      return "post/edit";
    }
    postService.udpatePost(id, new Post(
      createPost.getTitle(),
        createPost.getContent(),
        createPost.getCode(),
      "Y",
      new Category(createPost.getCategoryId()),
      user
    ));
    return "redirect:/posts/" +  id;
  }

  @PostMapping("{id}/delete")
  public String deletePost(@PathVariable Long id){
    postService.deletePost(id);
    return "redirect:/index";
  }
}
