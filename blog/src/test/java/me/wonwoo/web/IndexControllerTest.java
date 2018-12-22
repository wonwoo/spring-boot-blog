package me.wonwoo.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import me.wonwoo.config.PostProperties;
import me.wonwoo.domain.model.Category;
import me.wonwoo.domain.model.Post;
import me.wonwoo.domain.model.User;
import me.wonwoo.domain.repository.CategoryRepository;
import me.wonwoo.domain.repository.PostRepository;
import me.wonwoo.support.elasticsearch.PostElasticSearchService;
import me.wonwoo.support.elasticsearch.WpPosts;
import me.wonwoo.support.sidebar.SidebarContents;
import org.junit.Before;
import org.junit.Test;
import org.pegdown.PegDownProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

/**
 * Created by wonwoo on 2016. 8. 29..
 */
@WebMvcTest(IndexController.class)
public class IndexControllerTest extends AbstractControllerTests {

  @SpyBean
  private PegDownProcessor pegDownProcessor;

  @MockBean
  private PostElasticSearchService postElasticSearchService;

  @MockBean
  private SidebarContents sidebarContents;

  @SpyBean
  private PostProperties postProperties;

  @MockBean
  private CategoryRepository categoryRepository;

  @MockBean
  private PostRepository postRepository;

  @Autowired
  private MockMvc mockMvc;

  private WpPosts wpPosts;

  @Before
  public void setup() {
    wpPosts = new WpPosts();
    wpPosts.setId(1);
    wpPosts.setPostAuthor(1);
    wpPosts.setPostTitle("test title");
    wpPosts.setPostType("public");
    wpPosts.setPostDate(LocalDateTime.now());
    wpPosts.setHighlightedContent("test content");
    wpPosts.setPostContentFiltered("test content");
    given(categoryRepository.findAll()).willReturn(Arrays.asList(new Category("jpa"), new Category("spring")));
  }

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

  @Test
  public void search() throws Exception {
    postProperties.setFull(true);
    given(postElasticSearchService.searchWpPosts(any() , any()))
            .willReturn(new PageImpl<>(Collections.singletonList(wpPosts)));

    final MvcResult mvcResult = mockMvc.perform(get("/search"))
            .andExpect(status().isOk())
            .andReturn();

    Page<WpPosts> posts = (Page<WpPosts>) mvcResult.getModelAndView().getModel().get("posts");
    boolean isFull = (boolean) mvcResult.getModelAndView().getModel().get("show");
    assertThat(posts.getContent().get(0).getPostContent()).isEqualTo("<p>test content</p>");
    assertThat(posts.getContent().get(0).getPostTitle()).isEqualTo("test title");
    assertThat(posts.getContent().get(0).getPostContentFiltered()).isEqualTo("test content");
    assertThat(isFull).isTrue();
    verify(postElasticSearchService, atLeastOnce()).searchWpPosts(any() , any());
  }

}