package me.wonwoo.support.elasticsearch;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.wonwoo.wordpress.domain.WpPosts;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

/**
 * Created by wonwoo on 2017. 3. 2..
 */
@RunWith(MockitoJUnitRunner.class)
public class PostElasticSearchServiceTests {

  @Mock
  private ElasticsearchTemplate elasticsearchTemplate;

  @Mock
  private ObjectMapper objectMapper;

  private PostElasticSearchService postElasticSearchService;

  @Before
  public void setup() {
    postElasticSearchService = new PostElasticSearchService(elasticsearchTemplate, objectMapper);
  }

  @Test
  public void wpPosts() throws Exception {
    WpPosts wpPosts = new WpPosts();
    wpPosts.setPostContentFiltered("<p>content</p>");
    wpPosts.setPostContent("content");
    wpPosts.setId(1);
    wpPosts.setPostTitle("title");
    wpPosts.setPostStatus("publish");
    wpPosts.setPostType("post");
    AggregatedPage<WpPosts> page = new AggregatedPageImpl<>(Collections.singletonList(wpPosts));
    given(elasticsearchTemplate.queryForPage(any(SearchQuery.class), Matchers.<Class<WpPosts>>any()))
      .willReturn(page);
    final Page<WpPosts> result = postElasticSearchService.wpPosts(new PageRequest(0, 20));
    final List<WpPosts> content = result.getContent();
    assertThat(content.get(0).getId()).isEqualTo(1);
    assertThat(content.get(0).getPostTitle()).isEqualTo("title");
    assertThat(content.get(0).getPostStatus()).isEqualTo("publish");
    assertThat(content.get(0).getPostType()).isEqualTo("post");
    assertThat(content.get(0).getPostContent()).isEqualTo("content");
    assertThat(content.get(0).getPostContentFiltered()).isEqualTo("<p>content</p>");
  }

  @Test
  public void searchWpPosts() throws Exception {
    WpPosts wpPosts = new WpPosts();
    wpPosts.setPostContentFiltered("<p>content</p>");
    wpPosts.setPostContent("content");
    wpPosts.setId(1);
    wpPosts.setPostTitle("title");
    wpPosts.setPostStatus("publish");
    wpPosts.setPostType("post");
    AggregatedPage<WpPosts> page = new AggregatedPageImpl<>(Collections.singletonList(wpPosts));
    given(elasticsearchTemplate.queryForPage(any(SearchQuery.class), Matchers.<Class<WpPosts>>any(), any()))
      .willReturn(page);
    final Page<WpPosts> result = postElasticSearchService.searchWpPosts("spring boot", new PageRequest(0, 20));
    final List<WpPosts> content = result.getContent();
    assertThat(content.get(0).getId()).isEqualTo(1);
    assertThat(content.get(0).getPostTitle()).isEqualTo("title");
    assertThat(content.get(0).getPostStatus()).isEqualTo("publish");
    assertThat(content.get(0).getPostType()).isEqualTo("post");
    assertThat(content.get(0).getPostContent()).isEqualTo("content");
    assertThat(content.get(0).getPostContentFiltered()).isEqualTo("<p>content</p>");
  }

  @Test
  public void findOne() throws Exception {
    WpPosts wpPosts = new WpPosts();
    wpPosts.setPostContentFiltered("<p>content</p>");
    wpPosts.setPostContent("content");
    wpPosts.setId(1);
    wpPosts.setPostTitle("title");
    wpPosts.setPostStatus("publish");
    wpPosts.setPostType("post");
    given(elasticsearchTemplate.queryForObject(any(CriteriaQuery.class), Matchers.<Class<WpPosts>>any()))
    .willReturn(wpPosts);

    final WpPosts result = postElasticSearchService.findOne(1L);
    assertThat(result.getId()).isEqualTo(1);
    assertThat(result.getPostTitle()).isEqualTo("title");
    assertThat(result.getPostStatus()).isEqualTo("publish");
    assertThat(result.getPostType()).isEqualTo("post");
    assertThat(result.getPostContent()).isEqualTo("content");
    assertThat(result.getPostContentFiltered()).isEqualTo("<p>content</p>");
  }

  @Test
  public void findRelationPosts() throws Exception {
    WpPosts wpPosts = new WpPosts();
    wpPosts.setPostContentFiltered("<p>content</p>");
    wpPosts.setPostContent("content");
    wpPosts.setId(1);
    wpPosts.setPostTitle("title");
    wpPosts.setPostStatus("publish");
    wpPosts.setPostType("post");
    given(elasticsearchTemplate.queryForList(any(SearchQuery.class), Matchers.<Class<WpPosts>>any()))
    .willReturn(Collections.singletonList(wpPosts));
    final List<WpPosts> result = postElasticSearchService.findRelationPosts("spring boot");
    assertThat(result.get(0).getId()).isEqualTo(1);
    assertThat(result.get(0).getPostTitle()).isEqualTo("title");
    assertThat(result.get(0).getPostStatus()).isEqualTo("publish");
    assertThat(result.get(0).getPostType()).isEqualTo("post");
    assertThat(result.get(0).getPostContent()).isEqualTo("content");
    assertThat(result.get(0).getPostContentFiltered()).isEqualTo("<p>content</p>");
  }
}