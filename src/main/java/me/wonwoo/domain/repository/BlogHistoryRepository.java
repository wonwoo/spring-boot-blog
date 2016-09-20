package me.wonwoo.domain.repository;

import me.wonwoo.domain.model.BlogHistory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by wonwoo on 2016. 9. 20..
 */
public interface BlogHistoryRepository extends JpaRepository<BlogHistory, Long> {
}
