package me.wonwoo.blog;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import java.util.Collections;
import me.wonwoo.domain.model.Post;
import me.wonwoo.domain.repository.PostRepository;
import me.wonwoo.support.elasticsearch.BlogPost;
import me.wonwoo.support.elasticsearch.PostElasticSearchService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.rule.OutputCapture;

@RunWith(MockitoJUnitRunner.class)
public class FullIndexerTests {

	@Mock
	private PostRepository postRepository;

	@Mock
	private PostElasticSearchService postElasticSearchService;

	private FullIndexer fullIndexer;

	@Rule
	public OutputCapture outputCapture = new OutputCapture();

	@Before
	public void setup() {
        fullIndexer = new FullIndexer(postRepository, postElasticSearchService);
	}

	@Test
	public void indexItems() {
		Post post = createWpPost();

		given(postRepository.findByYnOrderByRegDateDesc("Y")).willReturn(Collections.singletonList(post));
		Iterable<Post> posts = fullIndexer.indexItems();
		for (Post next : posts) {
			assertThat(next.getId()).isEqualTo(1L);
			assertThat(next.getContent()).isEqualTo("test content");
			assertThat(next.getTitle()).isEqualTo("test");
			assertThat(next.getCode()).isEqualTo("test content filtered");
		}
		verify(postRepository).findByYnOrderByRegDateDesc("Y");
	}

	@Test
	public void indexItem() {
		Post post = createWpPost();
		doNothing().when(postElasticSearchService).save(any(BlogPost.class));
        fullIndexer.indexItem(post);
		verify(postElasticSearchService).save(any(BlogPost.class));
	}


	@Test
	public void error() {
		Post post = createWpPost();
        fullIndexer.error(post, new NullPointerException());
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