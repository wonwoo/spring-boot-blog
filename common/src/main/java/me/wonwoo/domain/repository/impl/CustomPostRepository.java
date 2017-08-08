package me.wonwoo.domain.repository.impl;

import me.wonwoo.domain.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by wonwoolee on 2017. 8. 8..
 */
public interface CustomPostRepository {

  Page<Post> findByPostsForCategory(Long id, Pageable pageable);
  Page<Post> findByPostsForTag(String name, Pageable pageable);

}
