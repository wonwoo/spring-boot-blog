package me.wonwoo.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import me.wonwoo.domain.model.Category;

/**
 * Created by wonwoo on 2016. 8. 15..
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
