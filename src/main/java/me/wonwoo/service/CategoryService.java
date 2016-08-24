package me.wonwoo.service;

import lombok.RequiredArgsConstructor;
import me.wonwoo.domain.model.Category;
import me.wonwoo.domain.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by wonwoo on 2016. 8. 24..
 */
@Service
@Transactional
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
}
