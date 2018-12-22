package me.wonwoo.service;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import me.wonwoo.domain.model.Post;
import me.wonwoo.domain.repository.CategoryPostRepository;
import me.wonwoo.domain.repository.PostRepository;
import me.wonwoo.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    savePost.setRegDate(LocalDateTime.now());
    categoryPostRepository.saveAll(savePost.getCategoryPost());
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
    //흠.
    post.setId(oldPost.getId());
    categoryPostRepository.deleteAll(oldPost.getCategoryPost());
    categoryPostRepository.saveAll(post.getCategoryPost());
  }

  public void deletePost(Long id) {
    Post oldPost = postRepository.findByIdAndYn(id, "Y");
    if (oldPost == null) {
      throw new NotFoundException(id + " not found");
    }
    oldPost.delete();
  }
}
