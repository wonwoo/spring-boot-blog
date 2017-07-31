package me.wonwoo.service;

import me.wonwoo.domain.model.BlogHistory;
import me.wonwoo.domain.repository.BlogHistoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by wonwoo on 2017. 2. 14..
 */
@Service
@Transactional
public class BlogHistoryService {

  private final BlogHistoryRepository blogHistoryRepository;

  public BlogHistoryService(BlogHistoryRepository blogHistoryRepository) {
    this.blogHistoryRepository = blogHistoryRepository;
  }

  public BlogHistory save(BlogHistory blogHistory) {
    return blogHistoryRepository.save(blogHistory);
  }

  public Page<BlogHistory> findByDateBetween(Pageable pageable) {
    return blogHistoryRepository.findAll(pageable);
  }
}
