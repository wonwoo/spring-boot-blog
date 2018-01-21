package me.wonwoo.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import me.wonwoo.domain.model.Category;
import me.wonwoo.domain.model.CategoryPost;
import me.wonwoo.domain.model.Post;

public interface CategoryPostRepository extends JpaRepository<CategoryPost, Long> {
  CategoryPost findByCategoryAndPost(Category category, Post post);
}
