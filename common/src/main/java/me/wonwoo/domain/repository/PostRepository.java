package me.wonwoo.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import me.wonwoo.domain.model.Post;

/**
 * Created by wonwoo on 2016. 8. 15..
 */
public interface PostRepository extends JpaRepository<Post, Long> {

  Post findByIdAndYn(Long id, String yn);
}
