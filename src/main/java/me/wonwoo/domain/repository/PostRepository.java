package me.wonwoo.domain.repository;

import me.wonwoo.domain.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by wonwoo on 2016. 8. 15..
 */
public interface PostRepository extends JpaRepository<Post, Long> {
  Page<Post> findByYn(String ym, Pageable pageable);

  Post findByIdAndYn(Long id, String yn);
}
