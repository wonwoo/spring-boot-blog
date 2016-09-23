package me.wonwoo.wordpress.repostiry;


import me.wonwoo.wordpress.domain.WpPosts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WpPostsRepository extends JpaRepository<WpPosts, Long> {

    @Query("select w from WpPosts w where postStatus = :status and postType = :type" )
    Page<WpPosts> findByPostStatus(Pageable pageable,  @Param("status") String publish, @Param("type") String type);

    WpPosts findByIdAndPostStatusAndPostType(Long id, String publish, String post);

    @Query(value = "select wt.name From wp_posts wp " +
            "inner join wp_term_relationships wtr " +
            "inner join wp_terms wt " +
            "inner join wp_term_taxonomy wtt " +
            "on wp.ID = wtr.object_id " +
            "and wtr.term_taxonomy_id = wt.term_id " +
            "and wt.term_id = wtt.term_id " +
            "where wp.ID = :wpId " +
            "and taxonomy = :type" ,nativeQuery = true)
    List<String> findByTags(@Param("wpId") Long id, @Param("type") String type);
}
