package me.wonwoo.domain.repository.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import me.wonwoo.domain.model.Category;
import me.wonwoo.domain.model.Post;
import me.wonwoo.domain.model.User;
import me.wonwoo.domain.repository.PostRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PostRepositoryImplTests {

	@Autowired
	private TestEntityManager testEntityManager;

	@Autowired
	private PostRepository postRepository;

	@Test
	public void findByPostsForCategory() {
		final User user = this.testEntityManager.persist(new User("wonwoo@test.com",
				"wonwoo",
				"wonwoo",
				"https://avatars.githubusercontent.com/u/747472?v=3", "password", true));
		final Post post = this.testEntityManager.persist(new Post("test title", "test content", "test content", "Y", user, Arrays.asList("spring", "jpa")));
		final Category category = this.testEntityManager.persist(new Category("spring"));
		Page<Post> posts = postRepository.findByPostsForCategory(category.getId(), PageRequest.of(0, 1));
		assertThat(posts.getTotalElements()).isEqualTo(1);
		assertThat(posts.getContent().get(0).getTitle()).isEqualTo("test title");
		assertThat(posts.getContent().get(0).getContent()).isEqualTo("test content");
		assertThat(posts.getContent().get(0).getCode()).isEqualTo("test content");
		assertThat(posts.getContent().get(0).getYn()).isEqualTo("Y");
		assertThat(posts.getContent().get(0).getCategory()).isEqualTo(category);
	}

	@Test
	public void findByPostsForTag() {
		final User user = this.testEntityManager.persist(new User("wonwoo@test.com",
				"wonwoo",
				"wonwoo",
				"https://avatars.githubusercontent.com/u/747472?v=3", "password", true));
		final Post post = this.testEntityManager.persist(new Post("test title", "test content", "test content", "Y", user, Arrays.asList("spring", "jpa")));
		final Category category = this.testEntityManager.persist(new Category("spring"));
		Page<Post> posts = postRepository.findByPostsForTag("spring", PageRequest.of(0, 1));
		assertThat(posts.getTotalElements()).isEqualTo(1);
		assertThat(posts.getContent().get(0).getTitle()).isEqualTo("test title");
		assertThat(posts.getContent().get(0).getContent()).isEqualTo("test content");
		assertThat(posts.getContent().get(0).getCode()).isEqualTo("test content");
		assertThat(posts.getContent().get(0).getYn()).isEqualTo("Y");
		assertThat(posts.getContent().get(0).getTags().get(0).getTag()).isEqualTo("spring");
		assertThat(posts.getContent().get(0).getTags().get(1).getTag()).isEqualTo("jpa");
	}
}