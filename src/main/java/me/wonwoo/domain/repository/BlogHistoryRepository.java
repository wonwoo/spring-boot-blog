package me.wonwoo.domain.repository;

import me.wonwoo.domain.model.BlogHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

/**
 * Created by wonwoo on 2016. 9. 20..
 */
public interface BlogHistoryRepository extends JpaRepository<BlogHistory, Long> {

  Page<BlogHistory> findByDateBetweenOrderByDateDesc(LocalDateTime start, LocalDateTime end, Pageable pageable);
}
