package me.wonwoo.service;

import lombok.RequiredArgsConstructor;
import me.wonwoo.domain.model.Post;
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

  public Post createPost(Post post){
    return postRepository.save(post);
  }

  public Post udpatePost(Long id, Post post) {
    Post oldPost = postRepository.getOne(id);
    if(oldPost == null){
      throw new NotFoundException(id + " not found");
    }

    oldPost.setContent(post.getContent());
    oldPost.setCode(post.getCode());
    oldPost.setTitle(post.getTitle());
    oldPost.setCategory(post.getCategory());
    return oldPost;
  }

  public void deletePost(Long id) {
    Post oldPost = postRepository.getOne(id);
    if(oldPost == null){
      throw new NotFoundException(id + " not found");
    }
    oldPost.setYn("N");
  }
}
