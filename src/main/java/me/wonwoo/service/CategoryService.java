package me.wonwoo.service;


import lombok.RequiredArgsConstructor;
import me.wonwoo.domain.model.Category;
import me.wonwoo.domain.repository.CategoryRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by wonwoo on 2016. 8. 24..
 */
@Service
@Transactional(transactionManager = "transactionManager")
@RequiredArgsConstructor
public class CategoryService {

  private final CategoryRepository categoryRepository;

  public Category createCategory(Category category){
    return categoryRepository.save(category);
  }

  public void delete(Long id) {
    categoryRepository.delete(id);
  }

  public void updateCategory(Category category) {
    Category oldCategory = categoryRepository.findOne(category.getId());
    if(oldCategory != null){
      oldCategory.setName(category.getName());
    }
  }

  @Transactional(readOnly = true, transactionManager = "transactionManager")
  @Cacheable("spring.blog.category")
  public Page<Category> findAll(Pageable pageable) {
    return categoryRepository.findAll(pageable);
  }

  @Transactional(readOnly = true, transactionManager = "transactionManager")
  @Cacheable("spring.blog.category")
  public List<Category> findAll() {
    return categoryRepository.findAll();
  }
}
