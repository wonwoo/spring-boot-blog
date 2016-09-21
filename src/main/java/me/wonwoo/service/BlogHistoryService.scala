package me.wonwoo.service

import me.wonwoo.domain.model.BlogHistory
import me.wonwoo.domain.repository.BlogHistoryRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
  * Created by wonwoo on 2016. 9. 21..
  */
@Service
@Transactional
class BlogHistoryService(val blogHistoryRepository: BlogHistoryRepository) {
  def save(blogHistory: BlogHistory) {
    blogHistoryRepository save blogHistory
  }

  @Transactional(readOnly = true)
  def findByDateBetween(pageable: Pageable) = blogHistoryRepository findAll pageable
}
