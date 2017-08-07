package me.wonwoo.domain.repository;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import me.wonwoo.domain.model.WpPost;
import me.wonwoo.domain.repository.WpPostsRepository;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class WpPostsRepositoryTests {

	@Autowired
	private TestEntityManager testEntityManager;

	@Autowired
	private WpPostsRepository wpPostsRepository;

	@Test
	public void findByPostTypeAndPostStatusAndIndexingIsNull() {
		WpPost wpPost = createWpPost();
		this.testEntityManager.persist(wpPost);
		Iterable<WpPost> wpPosts = wpPostsRepository.findByPostTypeAndPostStatusAndIndexingIsNull("post", "publish");
		for (WpPost next : wpPosts) {
			assertThat(next.getId()).isEqualTo(1);
			assertThat(next.getPostAuthor()).isEqualTo(1);
			assertThat(next.getPostContent()).isEqualTo("post content");
			assertThat(next.getPostTitle()).isEqualTo("title");
			assertThat(next.getPostType()).isEqualTo("post");
			assertThat(next.getPostStatus()).isEqualTo("publish");
			assertThat(next.getPostContentFiltered()).isEqualTo("post content filtered");
		}
	}

	private WpPost createWpPost() {
		WpPost wpPost = new WpPost();
		wpPost.setId(1);
		wpPost.setPostAuthor(1);
		wpPost.setPostContent("post content");
		wpPost.setPostTitle("title");
		wpPost.setPostType("post");
		wpPost.setPostStatus("publish");
		wpPost.setPostContentFiltered("post content filtered");
		return wpPost;
	}

}