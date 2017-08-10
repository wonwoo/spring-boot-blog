package me.wonwoo.blog;


import java.util.Collections;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.rule.OutputCapture;

import me.wonwoo.domain.model.Post;
import me.wonwoo.domain.repository.PostRepository;
import me.wonwoo.support.elasticsearch.PostElasticSearchService;
import me.wonwoo.support.elasticsearch.WpPosts;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class BlogIndexerTests {

	@Mock
	private PostRepository postRepository;

	@Mock
	private PostElasticSearchService postElasticSearchService;

	private BlogIndexer blogIndexer;

	@Rule
	public OutputCapture outputCapture = new OutputCapture();

	@Before
	public void setup() {
		blogIndexer = new BlogIndexer(postRepository, postElasticSearchService);
	}

	@Test
	public void indexItems() {
		Post post = createWpPost();

		given(postRepository.findByYnAndIndexingIsNull("Y")).willReturn(Collections.singletonList(post));
		Iterable<Post> posts = blogIndexer.indexItems();
		for (Post next : posts) {
			assertThat(next.getId()).isEqualTo(1L);
			assertThat(next.getContent()).isEqualTo("test content");
			assertThat(next.getTitle()).isEqualTo("test");
			assertThat(next.getCode()).isEqualTo("test content filtered");
		}
		verify(postRepository).findByYnAndIndexingIsNull("Y");
	}

	@Test
	public void indexItem() {
		Post post = createWpPost();
		doNothing().when(postElasticSearchService).save(any(WpPosts.class));
		blogIndexer.indexItem(post);
		verify(postElasticSearchService).save(any(WpPosts.class));
	}

	@Test
	public void save() {
		Post post = createWpPost();
		given(postRepository.save(post)).willReturn(post);
		blogIndexer.save(post);
		verify(postRepository).save(post);
	}

	@Test
	public void error() {
		Post post = createWpPost();
		blogIndexer.error(post, new NullPointerException());
		assertThat(outputCapture.toString()).contains("id : " + post.getId() + ", title : " + post.getTitle());
	}

	private Post createWpPost() {
		Post post = new Post(1L);
		post.setContent("test content");
		post.setTitle("test");
		post.setCode("test content filtered");
		return post;
	}
}