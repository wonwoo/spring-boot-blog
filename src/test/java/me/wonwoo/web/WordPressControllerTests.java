package me.wonwoo.web;

import me.wonwoo.support.elasticsearch.PostElasticSearchService;
import me.wonwoo.wordpress.WordPressClient;
import me.wonwoo.wordpress.domain.WpPosts;
import org.junit.Before;
import org.junit.Test;
import org.pegdown.PegDownProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
 * Created by wonwoo on 2017. 2. 15..
 */

@WebMvcTest(WordPressController.class)
@EnableSpringDataWebSupport
public class WordPressControllerTests extends AbstractControllerTests{

  @SpyBean
  private PegDownProcessor pegDownProcessor;

  @MockBean
  private PostElasticSearchService postElasticSearchService;

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
  }

  @Test
  public void findAll() throws Exception {
    given(postElasticSearchService.wpPosts(any()))
      .willReturn(new PageImpl<>(Collections.singletonList(wpPosts)));

    final MvcResult mvcResult = mockMvc.perform(get("/wordPress"))
      .andExpect(status().isOk())
      .andReturn();

    Page<WpPosts> wordPresses = (Page<WpPosts>) mvcResult.getModelAndView().getModel().get("wordPresses");
    assertThat(wordPresses.getContent().get(0).getId()).isEqualTo(1);
    assertThat(wordPresses.getContent().get(0).getPostAuthor()).isEqualTo(1);
    assertThat(wordPresses.getContent().get(0).getPostTitle()).isEqualTo("test title");
    assertThat(wordPresses.getContent().get(0).getPostType()).isEqualTo("public");
    assertThat(wordPresses.getContent().get(0).getHighlightedContent()).isEqualTo("test content");
    assertThat(wordPresses.getContent().get(0).getPostContent()).isEqualTo("<p>test content</p>");
    verify(postElasticSearchService, atLeastOnce()).wpPosts(new PageRequest(0, 3, Sort.Direction.DESC, "post_date"));
  }

  @Test
  public void search() throws Exception {


    given(postElasticSearchService.searchWpPosts(any(), any()))
      .willReturn(new PageImpl<>(Collections.singletonList(wpPosts)));

    final MvcResult mvcResult = mockMvc.perform(get("/wordPress/search").param("q", "test"))
      .andExpect(status().isOk())
      .andReturn();

    Page<WpPosts> wordPresses = (Page<WpPosts>) mvcResult.getModelAndView().getModel().get("wordPresses");
    assertThat(wordPresses.getContent().get(0).getId()).isEqualTo(1);
    assertThat(wordPresses.getContent().get(0).getPostAuthor()).isEqualTo(1);
    assertThat(wordPresses.getContent().get(0).getPostTitle()).isEqualTo("test title");
    assertThat(wordPresses.getContent().get(0).getPostType()).isEqualTo("public");
    assertThat(wordPresses.getContent().get(0).getHighlightedContent()).isEqualTo("test content");
    assertThat(wordPresses.getContent().get(0).getPostContent()).isEqualTo("<p>test content</p>");
    verify(postElasticSearchService, atLeastOnce()).searchWpPosts("test", new PageRequest(0, 3));

  }

  @Test
  public void findOne() throws Exception {
    given(postElasticSearchService.findOne(any()))
      .willReturn(wpPosts);

    final MvcResult mvcResult = mockMvc.perform(get("/wordPress/{id}", 1))
      .andExpect(status().isOk())
      .andReturn();

    WpPosts wpPosts = (WpPosts) mvcResult.getModelAndView().getModel().get("wordPress");
    assertThat(wpPosts.getId()).isEqualTo(1);
    assertThat(wpPosts.getPostAuthor()).isEqualTo(1);
    assertThat(wpPosts.getPostTitle()).isEqualTo("test title");
    assertThat(wpPosts.getPostType()).isEqualTo("public");
    assertThat(wpPosts.getHighlightedContent()).isEqualTo("test content");
    assertThat(wpPosts.getPostContent()).isEqualTo("<body>\n <p>test content</p>\n</body>");
    verify(postElasticSearchService, atLeastOnce()).findOne(1L);

  }

}