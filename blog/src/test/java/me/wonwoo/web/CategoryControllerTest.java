package me.wonwoo.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import me.wonwoo.domain.model.Category;
import me.wonwoo.domain.repository.CategoryRepository;
import me.wonwoo.service.CategoryService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


/**
 * Created by wonwoo on 2016. 8. 29..
 */
@WebMvcTest(CategoryController.class)
public class CategoryControllerTest extends AbstractControllerTests {

  @MockBean
  private CategoryRepository categoryRepository;

  @MockBean
  private CategoryService categoryService;

  @Autowired
  private MockMvc mockMvc;

  private Category category;

  @Before
  public void setup() {
    category = new Category(1L, "spring", LocalDateTime.now());
  }

  @Test
  public void categoriesTest() throws Exception {
    given(categoryService.findAll(any())).willReturn(new PageImpl<>(Collections.singletonList(category)));
    final MvcResult mvcResult = mockMvc.perform(get("/categories"))
      .andExpect(status().isOk())
      .andReturn();
    Page<Category> categories = (Page<Category>) mvcResult.getModelAndView().getModel().get("categories");
    assertThat(categories.getContent().get(0).getId()).isEqualTo(1L);
    assertThat(categories.getContent().get(0).getName()).isEqualTo("spring");

    verify(categoryService, atLeastOnce()).findAll(PageRequest.of(0, 20));

  }

  @Test
  public void newCategoryTest() throws Exception {
    mockMvc.perform(get("/categories/new")).andExpect(status().isOk());
  }

  @Test
  public void editTest() throws Exception {

    given(categoryRepository.findById(any(Long.class)))
      .willReturn(Optional.of(category));

    final MvcResult mvcResult = mockMvc.perform(get("/categories/{id}/edit", 1))
      .andExpect(status().isOk())
      .andReturn();

    Category category = (Category) mvcResult.getModelAndView().getModel().get("categoryDto");

    assertThat(category.getId()).isEqualTo(1L);
    assertThat(category.getName()).isEqualTo("spring");
    verify(categoryRepository, atLeastOnce()).findById(1L);

  }

  @Test
  public void createCategoryTest() throws Exception {
    given(categoryService.createCategory(any())).willReturn(new Category(1L));
    mockMvc.perform(post("/categories").with(csrf()).param("name", "spring").param("id", "1"))
      .andExpect(status().isFound());
    verify(categoryService, atLeastOnce()).createCategory(any());
  }

  @Test
  public void modifyCategoryTest() throws Exception {
    doNothing().when(categoryService).updateCategory(any());
    mockMvc.perform(post("/categories/{id}/edit", 1).with(csrf()).param("name", "jpa"))
      .andExpect(status().isFound());
    verify(categoryService, atLeastOnce()).updateCategory(any());
  }

  @Test
  public void deleteCategoryTest() throws Exception {
    doNothing().when(categoryService).delete(any());
    mockMvc.perform(post("/categories/{id}/delete", 1).with(csrf()))

      .andExpect(status().isFound());
    verify(categoryService, atLeastOnce()).delete(1L);

  }
}