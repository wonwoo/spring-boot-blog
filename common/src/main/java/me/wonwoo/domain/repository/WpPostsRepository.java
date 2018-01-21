package me.wonwoo.domain.repository;


import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;

import me.wonwoo.domain.model.WpPost;

public interface WpPostsRepository extends JpaRepository<WpPost, Long> {

  Iterable<WpPost> findByPostTypeAndPostStatusAndIndexingIsNull(String type, String status);

  Iterable<WpPost> findByPostTypeAndPostStatusAndIndexingAndPostModifiedAfter(
      String type, String status, String indexing, LocalDateTime localDateTime);

}