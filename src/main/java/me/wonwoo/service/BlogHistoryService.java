package me.wonwoo.service;

import lombok.RequiredArgsConstructor;
import me.wonwoo.domain.model.BlogHistory;
import me.wonwoo.domain.repository.BlogHistoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Created by wonwoo on 2016. 9. 20..
 */
@Service
@RequiredArgsConstructor
@Transactional
public class BlogHistoryService {
  private final BlogHistoryRepository blogHistoryRepository;

  public void save(BlogHistory blogHistory) {
    blogHistory.setDate(LocalDateTime.now());
    blogHistoryRepository.save(blogHistory);
  }

  public Page<BlogHistory> findByDateBetween(Pageable pageable) {
    LocalDateTime start = LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0));
    LocalDateTime end = LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59, 59));
    return blogHistoryRepository.findByDateBetweenOrderByDateDesc(start, end, pageable);
  }
}
