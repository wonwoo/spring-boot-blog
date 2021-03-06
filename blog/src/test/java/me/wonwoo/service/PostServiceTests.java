package me.wonwoo.service;

import java.util.Collections;

import me.wonwoo.support.elasticsearch.PostElasticSearchService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import me.wonwoo.domain.model.Category;
import me.wonwoo.domain.model.Post;
import me.wonwoo.domain.model.User;
import me.wonwoo.domain.repository.PostRepository;
import me.wonwoo.exception.NotFoundException;
import me.wonwoo.junit.MockitoJsonJUnitRunner;
import org.pegdown.PegDownProcessor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by wonwoo on 2017. 2. 12..
 */
@RunWith(MockitoJsonJUnitRunner.class)
public class PostServiceTests {

  @Mock
  private PostRepository postRepository;

  @Mock
  private PostElasticSearchService postElasticSearchService;

  @Mock
  private PegDownProcessor pegDownProcessor;

  @Rule
  public final ExpectedException exception = ExpectedException.none();

  private PostService postService;

  @Before
  public void setup() {
    postService = new PostService(postRepository, postElasticSearchService, pegDownProcessor);
  }

  @Test
  public void createPostTest() {
    final Post post = new Post("post title", "post content",
      "code", "Y", new Category(1L, "spring"),
      new User(), Collections.emptyList());
    post.setId(1L);
    given(postRepository.save(any(Post.class)))
      .willReturn(post);
    given(pegDownProcessor.markdownToHtml(any(String.class))).willReturn("code");
    doNothing().when(postElasticSearchService).save(any());
    final Post result = postService.createPost(post);
    assertThat(result.getTitle()).isEqualTo("post title");
    assertThat(result.getContent()).isEqualTo("post content");
    assertThat(result.getCode()).isEqualTo("code");
    assertThat(result.getYn()).isEqualTo("Y");
  }

  @Test
  public void updatePost() {
    final Post post = new Post("post title", "post content",
      "code", "Y", new Category(1L, "spring"),
      new User(), Collections.emptyList());
    given(postRepository.findByIdAndYn(any(), any()))
      .willReturn(post);
    given(pegDownProcessor.markdownToHtml(any(String.class))).willReturn("code");
    postService.updatePost(1L, post);
    verify(postRepository, times(1))
      .findByIdAndYn(1L, "Y");
  }

  @Test
  public void updateNotFoundIdPost() {
    exception.expect(NotFoundException.class);
    final Post post = new Post("post title", "post content",
            "code", "Y",  new Category(1L, "spring"),
            new User(), Collections.emptyList());
    given(postRepository.findByIdAndYn(any(), any()))
            .willReturn(null);
    postService.updatePost(1L, post);
  }

  @Test
  public void deletePostTest() {
    final Post post = new Post("post title", "post content",
      "code", "Y",  new Category(1L, "spring"),
      new User(), Collections.emptyList());
    given(postRepository.findByIdAndYn(any(), any()))
      .willReturn(post);
    postService.deletePost(1L);
    verify(postRepository, times(1))
      .findByIdAndYn(1L, "Y");
  }

  @Test
  public void deletePostNotFoundExceptionTest() {
    exception.expect(NotFoundException.class);
    given(postRepository.findByIdAndYn(any(), any()))
            .willReturn(null);
    postService.deletePost(1L);

  }

}