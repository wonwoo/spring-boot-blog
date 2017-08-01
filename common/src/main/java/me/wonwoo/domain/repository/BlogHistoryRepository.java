package me.wonwoo.domain.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

import me.wonwoo.domain.model.BlogHistory;

/**
 * Created by wonwoo on 2017. 2. 14..
 */
public interface BlogHistoryRepository extends ElasticsearchCrudRepository<BlogHistory, Long> {
  Page<BlogHistory> findByDateBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);
}
