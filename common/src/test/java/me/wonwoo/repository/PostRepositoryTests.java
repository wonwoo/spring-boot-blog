package me.wonwoo.repository;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import me.wonwoo.domain.model.Post;
import me.wonwoo.domain.model.User;
import me.wonwoo.domain.repository.PostRepository;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by wonwoo on 2017. 2. 16..
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class PostRepositoryTests {

  @Autowired
  private TestEntityManager testEntityManager;

  @Autowired
  private PostRepository postRepository;

  @Test
  public void findByIdAndYnTest() {
    final User user = this.testEntityManager.persist(new User("wonwoo@test.com",
      "wonwoo",
      "wonwoo",
      "https://avatars.githubusercontent.com/u/747472?v=3", "password", true));

    final Post persist = this.testEntityManager.persist(
      new Post("test title", "test content", "test content", "Y", user, Arrays.asList("spring", "jpa"))
    );
    Post post = this.postRepository.findByIdAndYn(persist.getId(), "Y");
    assertThat(post.getTitle()).isEqualTo("test title");
    assertThat(post.getContent()).isEqualTo("test content");
    assertThat(post.getCode()).isEqualTo("test content");
    assertThat(post.getYn()).isEqualTo("Y");
  }
}