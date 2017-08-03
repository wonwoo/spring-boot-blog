package me.wonwoo.blog;


import java.util.Collections;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.rule.OutputCapture;

import me.wonwoo.domain.model.WpPost;
import me.wonwoo.domain.repository.WpPostsRepository;
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
	private WpPostsRepository wpPostsRepository;

	@Mock
	private PostElasticSearchService postElasticSearchService;

	private BlogIndexer blogIndexer;

	@Rule
	public OutputCapture outputCapture = new OutputCapture();

	@Before
	public void setup() {
		blogIndexer = new BlogIndexer(wpPostsRepository, postElasticSearchService);
	}

	@Test
	public void indexItems() {
		WpPost wpPost = createWpPost();

		given(wpPostsRepository.findByPostTypeAndPostStatusAndIndexingIsNull(any(), any())).willReturn(Collections.singletonList(wpPost));
		Iterable<WpPost> wpPosts = blogIndexer.indexItems();
		for (WpPost next : wpPosts) {
			assertThat(next.getId()).isEqualTo(1);
			assertThat(next.getPostAuthor()).isEqualTo(1);
			assertThat(next.getPostContent()).isEqualTo("test content");
			assertThat(next.getPostTitle()).isEqualTo("test");
			assertThat(next.getPostType()).isEqualTo("post");
			assertThat(next.getPostStatus()).isEqualTo("publish");
			assertThat(next.getPostContentFiltered()).isEqualTo("test content filtered");
		}
		verify(wpPostsRepository).findByPostTypeAndPostStatusAndIndexingIsNull("post", "publish");
	}

	@Test
	public void indexItem() {
		WpPost wpPost = createWpPost();
		doNothing().when(postElasticSearchService).save(any(WpPosts.class));
		blogIndexer.indexItem(wpPost);
		verify(postElasticSearchService).save(any(WpPosts.class));
	}

	@Test
	public void save() {
		WpPost wpPost = createWpPost();
		given(wpPostsRepository.save(wpPost)).willReturn(wpPost);
		blogIndexer.save(wpPost);
		verify(wpPostsRepository).save(wpPost);
	}

	@Test
	public void error() {
		WpPost wpPost = createWpPost();
		blogIndexer.error(wpPost, new NullPointerException());
		assertThat(outputCapture.toString()).contains("id : " + wpPost.getId() + ", title : " + wpPost.getPostTitle());
	}

	private WpPost createWpPost() {
		WpPost wpPost = new WpPost();
		wpPost.setId(1);
		wpPost.setPostAuthor(1);
		wpPost.setPostContent("test content");
		wpPost.setPostTitle("test");
		wpPost.setPostType("post");
		wpPost.setPostStatus("publish");
		wpPost.setPostContentFiltered("test content filtered");
		return wpPost;
	}
}