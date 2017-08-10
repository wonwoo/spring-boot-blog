package me.wonwoo.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import me.wonwoo.domain.model.Post;
import me.wonwoo.domain.repository.impl.CustomPostRepository;

/**
 * Created by wonwoo on 2016. 8. 15..
 */
public interface PostRepository extends JpaRepository<Post, Long>, CustomPostRepository {

  Post findByIdAndYn(Long id, String yn);

  List<Post> findByYnAndIndexingIsNull(String yn);
}
