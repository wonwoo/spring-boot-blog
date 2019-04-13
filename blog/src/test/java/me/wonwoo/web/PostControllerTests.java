package me.wonwoo.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import me.wonwoo.config.PostProperties;
import me.wonwoo.domain.model.Category;
import me.wonwoo.domain.model.CategoryPost;
import me.wonwoo.domain.model.Post;
import me.wonwoo.domain.model.Tag;
import me.wonwoo.domain.model.User;
import me.wonwoo.domain.repository.PostRepository;
import me.wonwoo.dto.PostDto;
import me.wonwoo.service.CategoryService;
import me.wonwoo.service.PostService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

/**
 * Created by wonwoolee on 2017. 8. 8..
 */
@WebMvcTest(PostController.class)
public class PostControllerTests extends AbstractControllerTests {

  @MockBean
  private PostRepository postRepository;

  @MockBean
  private PostProperties postProperties;

  @MockBean
  private CategoryService categoryService;

  @MockBean
  private PostService postService;

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void findByPost() throws Exception {
    given(postRepository.findByIdAndYn(any(), any()))
        .willReturn(tempPost());
    final MvcResult mvcResult = mockMvc.perform(get("/posts/1"))
        .andExpect(status().isOk())
        .andReturn();

    Post result = (Post) mvcResult.getModelAndView().getModel().get("post");
    assertThat(result.getCode()).isEqualTo("text content");
    assertThat(result.getContent()).isEqualTo("text content");
    assertThat(result.getTitle()).isEqualTo("post test");
    assertThat(result.getYn()).isEqualTo("Y");
  }

  @Test
  public void findByPostNotFoundException() throws Exception {
    given(postRepository.findByIdAndYn(any(), any()))
        .willReturn(null);
    mockMvc.perform(get("/posts/1"))
        .andExpect(status().isNotFound())
        .andReturn();
  }

  @Test
  public void newPost() throws Exception {
    final MvcResult mvcResult = mockMvc.perform(get("/posts/new/form"))
        .andExpect(status().isOk())
        .andReturn();
    String view = mvcResult.getModelAndView().getViewName();
    assertThat(view).isEqualTo("post/new");
  }

  @Test
  public void editPost() throws Exception {
    given(postRepository.findByIdAndYn(any(), any()))
        .willReturn(tempPost());
    final MvcResult mvcResult = mockMvc.perform(get("/posts/edit/1"))
        .andExpect(status().isOk())
        .andReturn();

    PostDto result = (PostDto) mvcResult.getModelAndView().getModel().get("editPost");
    assertThat(result.getCode()).isEqualTo("text content");
    assertThat(result.getContent()).isEqualTo("text content");
    assertThat(result.getTitle()).isEqualTo("post test");
    assertThat(result.getTags()).isEqualTo("spring,jpa");
    assertThat(result.getCategoryId()).isEqualTo(Collections.singletonList(1L));
    assertThat(result.getCategoryName()).isEqualTo(Collections.singletonList("spring"));
  }

  @Test
  public void editPostNotFoundException() throws Exception {
    given(postRepository.findByIdAndYn(any(), any()))
        .willReturn(null);
    mockMvc.perform(get("/posts/edit/1"))
        .andExpect(status().isNotFound())
        .andReturn();
  }

  @Test
  public void createPostHasError() throws Exception {
    MvcResult mvcResult = mockMvc.perform(post("/posts").with(csrf()))
        .andExpect(status().isOk())
        .andReturn();
    assertThat(mvcResult.getModelAndView().getViewName())
        .isEqualTo("post/new");

  }

  @Test
  public void createPost() throws Exception {
    given(postService.createPost(any())).willReturn(tempPost());
    mockMvc.perform(post("/posts").with(csrf())
        .param("title", "test title")
        .param("content", "test content")
        .param("categoryId", "1,2"))
        .andExpect(status().isFound())
        .andExpect(header().string(HttpHeaders.LOCATION, "/posts/1"));
  }

  @Test
  public void modifyPostHasError() throws Exception {
    MvcResult mvcResult = mockMvc.perform(post("/posts/1/edit").with(csrf()))
        .andExpect(status().isOk())
        .andReturn();
    assertThat(mvcResult.getModelAndView().getViewName())
        .isEqualTo("post/edit");
  }


  @Test
  public void modifyPost() throws Exception {
    doNothing().when(postService).updatePost(any(), any());
    mockMvc.perform(post("/posts/1/edit")
        .with(csrf())
        .param("title", "test title")
        .param("content", "test content")
        .param("categoryId", "1,2"))
        .andExpect(status().isFound())
        .andExpect(header().string(HttpHeaders.LOCATION, "/posts/1"));
  }

  @Test
  public void deletePost() throws Exception {
    doNothing().when(postService).deletePost(any());
    mockMvc.perform(post("/posts/1/delete").with(csrf()))
        .andExpect(status().isFound())
        .andExpect(header().string(HttpHeaders.LOCATION, "/#/"));
  }

  @Test
  public void categoryPost() throws Exception {
    Page<Post> page = new PageImpl<>(Collections.singletonList(tempPost()));

    given(postRepository.findByPostsForCategory(any(), any())).willReturn(page);
    MvcResult mvcResult = mockMvc.perform(get("/posts/category/1"))
        .andExpect(status().isOk())
        .andReturn();
    Page<Post> result = (Page<Post>) mvcResult.getModelAndView().getModel().get("posts");
    assertThat(result.getContent().get(0).getCode()).isEqualTo("text content");
    assertThat(result.getContent().get(0).getContent()).isEqualTo("text content");
    assertThat(result.getContent().get(0).getTitle()).isEqualTo("post test");
    assertThat(result.getContent().get(0).getTags()).isEqualTo(Arrays.asList(new Tag("spring"), new Tag("jpa")));
  }

  @Test
  public void getTags() throws Exception {
    Page<Post> page = new PageImpl<>(Collections.singletonList(tempPost()));

    given(postRepository.findByPostsForTag(any(), any())).willReturn(page);
    MvcResult mvcResult = mockMvc.perform(get("/posts/tags/spring"))
        .andExpect(status().isOk())
        .andReturn();
    Page<Post> result = (Page<Post>) mvcResult.getModelAndView().getModel().get("posts");
    assertThat(result.getContent().get(0).getCode()).isEqualTo("text content");
    assertThat(result.getContent().get(0).getContent()).isEqualTo("text content");
    assertThat(result.getContent().get(0).getTitle()).isEqualTo("post test");
    assertThat(result.getContent().get(0).getTags()).isEqualTo(Arrays.asList(new Tag("spring"), new Tag("jpa")));
  }


  private Post tempPost() {
    Post post = new Post("post test", "Y");
    post.setId(1L);
    post.setContent("text content");
    post.setRegDate(LocalDateTime.now());
    post.setCode("text content");
    post.setUser(new User(null, "wonwoo", null, null, null, true));
    post.setTags(Arrays.asList(new Tag("spring"), new Tag("jpa")));
    post.setCategoryPost(Collections.singletonList(new CategoryPost(new Category(1L, "spring"), post)));
    return post;
  }
}