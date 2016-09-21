package me.wonwoo.domain.repository

import java.awt.print.Pageable
import java.time.LocalDateTime

import me.wonwoo.domain.model.BlogHistory
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository

/**
  * Created by wonwoo on 2016. 9. 21..
  */
trait BlogHistoryRepository extends ElasticsearchCrudRepository[BlogHistory, java.lang.Long] {

  def findByDateBetween(start: LocalDateTime, end: LocalDateTime, pageable: Pageable);
}
