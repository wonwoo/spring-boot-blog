package me.wonwoo.web;

import me.wonwoo.config.PostProperties;
import me.wonwoo.domain.model.Post;
import me.wonwoo.domain.model.User;
import me.wonwoo.domain.repository.CategoryRepository;
import me.wonwoo.domain.repository.PostRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by wonwoo on 2016. 8. 29..
 */
@WebMvcTest(IndexController.class)
@EnableSpringDataWebSupport
public class IndexControllerTest extends AbstractControllerTests {

  @MockBean
  private PostRepository postRepository;

  @SpyBean
  private PostProperties postProperties;

  @MockBean
  private CategoryRepository categoryRepository;

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void homeTest() throws Exception {
    postProperties.setFull(true);

    Post post = new Post("post test", "Y");
    post.setContent("text content");
    post.setRegDate(LocalDateTime.now());
    post.setCode("text content");
    post.setUser(new User(null, "wonwoo", null, null, null, true));
    given(postRepository.findAll(any(), any(Pageable.class)))
      .willReturn(new PageImpl<>(Collections.singletonList(post)));
    final MvcResult mvcResult = mockMvc.perform(get("/"))
      .andExpect(status().isOk())
      .andReturn();

    Page<Post> posts = (Page<Post>) mvcResult.getModelAndView().getModel().get("posts");
    boolean isFull = (boolean) mvcResult.getModelAndView().getModel().get("show");
    assertThat(posts.getContent().get(0).getContent()).isEqualTo("text content");
    assertThat(posts.getContent().get(0).getTitle()).isEqualTo("post test");
    assertThat(posts.getContent().get(0).getCode()).isEqualTo("text content");
    assertThat(posts.getContent().get(0).getYn()).isEqualTo("Y");
    assertThat(isFull).isTrue();
    verify(postRepository, atLeastOnce()).findAll(any(), any(Pageable.class));
  }
}