package me.wonwoo.blog;


import org.springframework.data.jpa.repository.JpaRepository;

public interface WpPostsRepository extends JpaRepository<WpPost, Long>{

	Iterable<WpPost> findByPostTypeAndPostStatusAndIndexingIsNull(String type, String status);

}