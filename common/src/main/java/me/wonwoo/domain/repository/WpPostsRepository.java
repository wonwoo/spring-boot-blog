package me.wonwoo.domain.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import me.wonwoo.domain.model.WpPost;

public interface WpPostsRepository extends JpaRepository<WpPost, Long>{

	Iterable<WpPost> findByPostTypeAndPostStatusAndIndexingIsNull(String type, String status);

}