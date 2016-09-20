package me.wonwoo.service;

import lombok.RequiredArgsConstructor;
import me.wonwoo.domain.model.BlogHistory;
import me.wonwoo.domain.repository.BlogHistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Created by wonwoo on 2016. 9. 20..
 */
@Service
@RequiredArgsConstructor
@Transactional
public class BlogHistoryService {
  private final BlogHistoryRepository blogHistoryRepository;

  public void save(BlogHistory blogHistory){
    blogHistory.setDate(LocalDateTime.now());
    blogHistoryRepository.save(blogHistory);
  }
}
