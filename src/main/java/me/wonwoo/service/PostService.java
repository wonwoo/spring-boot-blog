package me.wonwoo.service;

import lombok.RequiredArgsConstructor;
import me.wonwoo.domain.model.CategoryPost;
import me.wonwoo.domain.model.Post;
import me.wonwoo.domain.repository.CategoryPostRepository;
import me.wonwoo.domain.repository.PostRepository;
import me.wonwoo.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by wonwoo on 2016. 8. 22..
 */
@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
  private final PostRepository postRepository;
  private final CategoryPostRepository categoryPostRepository;

  public Post createPost(Post post) {
    Post savePost = postRepository.save(post);
    categoryPostRepository.save(savePost.getCategoryPost());
    return savePost;
  }

  public void updatePost(Long id, Post post) {
    Post oldPost = postRepository.findByIdAndYn(id, "Y");
    if (oldPost == null) {
      throw new NotFoundException(id + " not found");
    }
    oldPost.setTitle(post.getTitle());
    oldPost.setTags(post.getTags());
    oldPost.setCode(post.getCode());
    oldPost.setContent(post.getContent());
//    categoryPostRepository.delete(oldPost.getCategoryPost());
//    categoryPostRepository.save(post.getCategoryPost());
  }

  public void deletePost(Long id) {
    Post oldPost = postRepository.findByIdAndYn(id, "Y");
    if (oldPost == null) {
      throw new NotFoundException(id + " not found");
    }
    oldPost.delete();
  }
}
