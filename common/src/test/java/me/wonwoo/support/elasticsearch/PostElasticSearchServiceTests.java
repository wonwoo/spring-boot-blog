package me.wonwoo.support.elasticsearch;

import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

import com.fasterxml.jackson.databind.ObjectMapper;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

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
    BlogPost blogPost = new BlogPost();
    blogPost.setPostContentFiltered("<p>content</p>");
    blogPost.setPostContent("content");
    blogPost.setId(1);
    blogPost.setPostTitle("title");
    blogPost.setPostStatus("publish");
    blogPost.setPostType("post");
    AggregatedPage<BlogPost> page = new AggregatedPageImpl<>(Collections.singletonList(blogPost));
    given(elasticsearchTemplate.queryForPage(any(SearchQuery.class), ArgumentMatchers.<Class<BlogPost>>any()))
      .willReturn(page);
    final Page<BlogPost> result = postElasticSearchService.wpPosts(PageRequest.of(0, 20));
    final List<BlogPost> content = result.getContent();
    assertThat(content.get(0).getId()).isEqualTo(1);
    assertThat(content.get(0).getPostTitle()).isEqualTo("title");
    assertThat(content.get(0).getPostStatus()).isEqualTo("publish");
    assertThat(content.get(0).getPostType()).isEqualTo("post");
    assertThat(content.get(0).getPostContent()).isEqualTo("content");
    assertThat(content.get(0).getPostContentFiltered()).isEqualTo("<p>content</p>");
  }

  @Test
  public void searchWpPosts() throws Exception {
    BlogPost blogPost = new BlogPost();
    blogPost.setPostContentFiltered("<p>content</p>");
    blogPost.setPostContent("content");
    blogPost.setId(1);
    blogPost.setPostTitle("title");
    blogPost.setPostStatus("publish");
    blogPost.setPostType("post");
    AggregatedPage<BlogPost> page = new AggregatedPageImpl<>(Collections.singletonList(blogPost));
    given(elasticsearchTemplate.queryForPage(any(SearchQuery.class), ArgumentMatchers.<Class<BlogPost>>any(), any()))
      .willReturn(page);
    final Page<BlogPost> result = postElasticSearchService.searchWpPosts("spring boot", PageRequest.of(0, 20));
    final List<BlogPost> content = result.getContent();
    assertThat(content.get(0).getId()).isEqualTo(1);
    assertThat(content.get(0).getPostTitle()).isEqualTo("title");
    assertThat(content.get(0).getPostStatus()).isEqualTo("publish");
    assertThat(content.get(0).getPostType()).isEqualTo("post");
    assertThat(content.get(0).getPostContent()).isEqualTo("content");
    assertThat(content.get(0).getPostContentFiltered()).isEqualTo("<p>content</p>");
  }

  @Test
  public void findOne() throws Exception {
    BlogPost blogPost = new BlogPost();
    blogPost.setPostContentFiltered("<p>content</p>");
    blogPost.setPostContent("content");
    blogPost.setId(1);
    blogPost.setPostTitle("title");
    blogPost.setPostStatus("publish");
    blogPost.setPostType("post");
    given(elasticsearchTemplate.queryForObject(any(CriteriaQuery.class), ArgumentMatchers.<Class<BlogPost>>any()))
    .willReturn(blogPost);

    final BlogPost result = postElasticSearchService.findOne(1L);
    assertThat(result.getId()).isEqualTo(1);
    assertThat(result.getPostTitle()).isEqualTo("title");
    assertThat(result.getPostStatus()).isEqualTo("publish");
    assertThat(result.getPostType()).isEqualTo("post");
    assertThat(result.getPostContent()).isEqualTo("content");
    assertThat(result.getPostContentFiltered()).isEqualTo("<p>content</p>");
  }

  @Test
  public void save() {
    given(elasticsearchTemplate.index(any())).willReturn(null);
    postElasticSearchService.save(new BlogPost());
    verify(elasticsearchTemplate).index(any());
  }

  @Test
  public void findRelationPosts() throws Exception {
    BlogPost blogPost = new BlogPost();
    blogPost.setPostContentFiltered("<p>content</p>");
    blogPost.setPostContent("content");
    blogPost.setId(1);
    blogPost.setPostTitle("title");
    blogPost.setPostStatus("publish");
    blogPost.setPostType("post");
    given(elasticsearchTemplate.queryForList(any(SearchQuery.class), ArgumentMatchers.<Class<BlogPost>>any()))
    .willReturn(Collections.singletonList(blogPost));
    final List<BlogPost> result = postElasticSearchService.findRelationPosts("spring boot");
    assertThat(result.get(0).getId()).isEqualTo(1);
    assertThat(result.get(0).getPostTitle()).isEqualTo("title");
    assertThat(result.get(0).getPostStatus()).isEqualTo("publish");
    assertThat(result.get(0).getPostType()).isEqualTo("post");
    assertThat(result.get(0).getPostContent()).isEqualTo("content");
    assertThat(result.get(0).getPostContentFiltered()).isEqualTo("<p>content</p>");
  }

  @Test
  public void update() {
    given(elasticsearchTemplate.update(any())).willReturn(null);
    postElasticSearchService.update("test", new BlogPost());
    verify(elasticsearchTemplate).update(any());
  }
}