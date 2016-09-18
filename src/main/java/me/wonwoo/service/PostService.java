package me.wonwoo.service;

import lombok.RequiredArgsConstructor;
import me.wonwoo.domain.model.Post;
import me.wonwoo.domain.repository.PostRepository;
import me.wonwoo.domain.repository.TagRepository;
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
  private final TagRepository tagRepository;

  public Post createPost(Post post) {
    return postRepository.save(post);
  }

  public void updatePost(Long id, Post post) {
    Post oldPost = postRepository.findByIdAndYn(id, "Y");
    if (oldPost == null) {
      throw new NotFoundException(id + " not found");
    }

    tagRepository.delete(oldPost.getTags());
    post.setId(id);
    post.setRegDate(oldPost.getRegDate());
    postRepository.save(post);
  }

  public void deletePost(Long id) {
    Post oldPost = postRepository.findByIdAndYn(id, "Y");
    if (oldPost == null) {
      throw new NotFoundException(id + " not found");
    }
    oldPost.delete();
  }
}
