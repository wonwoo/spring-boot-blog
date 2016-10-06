package me.wonwoo.wordpress.repository;

import me.wonwoo.wordpress.domain.WpPosts;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

public interface WpPostsRepository extends ElasticsearchCrudRepository<WpPosts, Long> {
}
