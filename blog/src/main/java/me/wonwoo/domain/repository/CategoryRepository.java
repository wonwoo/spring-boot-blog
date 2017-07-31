package me.wonwoo.domain.repository;

import me.wonwoo.domain.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by wonwoo on 2016. 8. 15..
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
