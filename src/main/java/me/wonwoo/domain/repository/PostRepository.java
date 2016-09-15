package me.wonwoo.domain.repository;

import me.wonwoo.domain.model.Category;
import me.wonwoo.domain.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by wonwoo on 2016. 8. 15..
 */
public interface PostRepository extends JpaRepository<Post, Long> {
  Post findByIdAndYn(Long id, String yn);

  Page<Post> findByCategoryAndYn(Category category, String y, Pageable pageable);
}
