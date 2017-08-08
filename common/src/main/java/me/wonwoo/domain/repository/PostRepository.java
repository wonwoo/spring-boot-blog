package me.wonwoo.domain.repository;

import me.wonwoo.domain.repository.impl.CustomPostRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import me.wonwoo.domain.model.Post;

/**
 * Created by wonwoo on 2016. 8. 15..
 */
public interface PostRepository extends JpaRepository<Post, Long>, CustomPostRepository {

  Post findByIdAndYn(Long id, String yn);
}
