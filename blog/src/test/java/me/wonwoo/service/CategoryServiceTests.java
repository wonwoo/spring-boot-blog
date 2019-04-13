package me.wonwoo.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import me.wonwoo.domain.model.Category;
import me.wonwoo.domain.repository.CategoryRepository;
import me.wonwoo.junit.MockitoJsonJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

/**
 * Created by wonwoo on 2017. 2. 12..
 */

@RunWith(MockitoJsonJUnitRunner.class)
public class CategoryServiceTests {

  private JacksonTester<Category> json;

  private JacksonTester<Page<Category>> json1;

  private JacksonTester<List<Category>> json2;

  @Mock
  private CategoryRepository categoryRepository;

  private CategoryService categoryService;

  @Before
  public void setup(){
    categoryService = new CategoryService(categoryRepository);
  }


  @Test
  public void createCategoryTest() throws IOException {
    final Category category = new Category(1L, "spring");
    given(categoryRepository.save(any(Category.class)))
        .willReturn(category);
    final Category result = categoryService.createCategory(category);
    assertThat(this.json.write(result))
        .isEqualToJson("createcategory.json");

  }

  @Test
  public void deleteTest() {
    doNothing().when(categoryRepository).deleteById(any());
    categoryService.delete(1L);
    verify(categoryRepository, times(1)).deleteById(1L);
  }

  @Test
  public void updateCategoryTest() {
    given(categoryRepository.findById(any(Long.class)))
        .willReturn(Optional.of(new Category(1L, "spring")));
    categoryService.updateCategory(new Category(1L, "jpa"));
    verify(categoryRepository, times(1)).findById(1L);
  }

  @Test
  public void findAllTest() throws IOException {
    Page<Category> page = new PageImpl<>(
        Arrays.asList(
            new Category(1L, "spring"),
            new Category(2L, "jpa")
        )
    );
    given(categoryRepository.findAll(any(Pageable.class)))
        .willReturn(page);
    final Page<Category> result = categoryService.findAll(PageRequest.of(0, 20));
    assertThat(this.json1.write(result))
        .isEqualToJson("findcategorypage.json");
  }
  @Test
  public void findAll1Test() throws IOException {
    given(categoryRepository.findAll())
        .willReturn(      Arrays.asList(
            new Category(1L, "spring"),
            new Category(2L, "jpa")
        ));
    final List<Category> result = categoryService.findAll();
    assertThat(this.json2.write(result))
        .isEqualToJson("findcategorylist.json");
  }
}