package me.wonwoo.domain.repository;

import me.wonwoo.domain.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by wonwoo on 2016. 8. 15..
 */
public interface PostRepository extends JpaRepository<Post, Long> {

  Post findByIdAndYn(Long id, String yn);

  @Query("select p from Post p inner join p.tags t where t.tag = :name and p.yn = :y")
  Page<Post> findByTagAndYn(@Param("name") String name, @Param("y") String y, Pageable pageable);

  @Query("select p from Post p inner join p.categoryPost cp  inner join cp.category c where cp.category.id = :id and p.yn = :y")
  Page<Post> findByCategoryPostAndYn(@Param("id") Long id, @Param("y")String y, Pageable pageable);
}
