package me.wonwoo.web;

import lombok.RequiredArgsConstructor;
import me.wonwoo.config.PostProperties;
import me.wonwoo.domain.model.Category;
import me.wonwoo.domain.model.Post;
import me.wonwoo.domain.model.Tag;
import me.wonwoo.domain.model.User;
import me.wonwoo.domain.repository.PostRepository;
import me.wonwoo.dto.CommentDto;
import me.wonwoo.dto.PostDto;
import me.wonwoo.dto.SearchForm;
import me.wonwoo.exception.NotFoundException;
import me.wonwoo.service.CategoryService;
import me.wonwoo.service.PostService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;


/**
 * Created by wonwoo on 2016. 8. 15..
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/posts")
@Navigation(Section.POST)
public class PostController {

  private final PostRepository postRepository;

  private final PostService postService;

  private final CategoryService categoryService;

  private final PostProperties postProperties;

  @ModelAttribute("categories")
  public List<Category> categories() {
    return categoryService.findAll();
  }

  @ModelAttribute("theme")
  public String theme() {
    return postProperties.getTheme();
  }


  @GetMapping("/{id}")
  public String findByPost(@PathVariable Long id, Model model, @ModelAttribute CommentDto commentDto) {
    Post post = postRepository.findByIdAndYn(id, "Y");
    if (post == null) {
      throw new NotFoundException(id + " not found");
    }
    model.addAttribute("post", post);
    return "post/post";
  }

  @GetMapping("/new/form")
  public String newPost(PostDto postDto) {
    return "post/new";
  }

  @GetMapping("/edit/{id}")
  public String editPost(@PathVariable Long id, Model model) {
    Post post = postRepository.findByIdAndYn(id, "Y");
    if (post == null) {
      throw new NotFoundException(id + " not found");
    }
    PostDto createPost = new PostDto();

    createPost.setCategoryId(post.getCategoryPost().stream().map(mapper -> mapper.getCategory().getId()).collect(toList()));
    createPost.setCategoryName(post.getCategoryPost().stream().map(mapper -> mapper.getCategory().getName()).collect(toList()));
    createPost.setTitle(post.getTitle());
    createPost.setCode(post.getCode());
    createPost.setContent(post.getContent());
    createPost.setId(id);
    createPost.setTags(post.getTags().stream().map(Tag::getTag).collect(joining(",")));
    model.addAttribute("editPost", createPost);
    return "post/edit";
  }

  @PostMapping
  public String createPost(@ModelAttribute @Valid PostDto createPost, BindingResult bindingResult, @AuthenticationPrincipal User user, Model model) {
    if (bindingResult.hasErrors()) {
      return "post/new";
    }

    Post post = new Post(createPost.getTitle(),
      createPost.getContent(),
      createPost.getCode(),
      "Y",
      createPost.getCategoryId().stream().map(Category::new).collect(toList()),
      user,
      createPost.tags());

    Post newPost = postService.createPost(post);
    model.addAttribute("post", newPost);
    return "redirect:/posts/" + newPost.getId();
  }

  @PostMapping("/{id}/edit")
  public String modifyPost(@PathVariable Long id, @ModelAttribute("editPost") @Valid PostDto createPost, BindingResult bindingResult, @AuthenticationPrincipal User user) {
    if (bindingResult.hasErrors()) {
      return "post/edit";
    }
    final Post post = new Post(
      createPost.getTitle(),
      createPost.getContent(),
      createPost.getCode(),
      "Y",
      createPost.getCategoryId().stream().map(Category::new).collect(toList()),
      user,
      createPost.tags()
    );
    postService.updatePost(id, post);
    return "redirect:/posts/" + id;
  }

  @PostMapping("/{id}/delete")
  public String deletePost(@PathVariable Long id) {
    postService.deletePost(id);
    return "redirect:/#/";
  }

  @GetMapping("/category/{id}")
  public String categoryPost(Model model, @PathVariable Long id, @ModelAttribute SearchForm searchForm, @PageableDefault(size = 3, sort = "regDate", direction = Sort.Direction.DESC) Pageable pageable) {
    model.addAttribute("posts", postRepository.findByPostsForCategory(id, pageable));
    model.addAttribute("show", postProperties.isFull());
    return "index";
  }


  @GetMapping("/tags/{name}")
  public String getTags(@PathVariable String name, @ModelAttribute SearchForm searchForm, Model model, @PageableDefault(size = 3, sort = "regDate", direction = Sort.Direction.DESC) Pageable pageable) {
    model.addAttribute("posts", postRepository.findByPostsForTag(name, pageable));
    model.addAttribute("show", postProperties.isFull());
    return "index";
  }
}
