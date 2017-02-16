package me.wonwoo.domain.repository;

import me.wonwoo.domain.model.Category;
import me.wonwoo.domain.model.CategoryPost;
import me.wonwoo.domain.model.Post;
import me.wonwoo.domain.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by wonwoo on 2017. 2. 16..
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class CategoryPostRepositoryTests {

  @Autowired
  private TestEntityManager testEntityManager;

  @Autowired
  private CategoryPostRepository categoryPostRepository;

  @Test
  public void findOneTest() {
    final User user = this.testEntityManager.persist(new User("wonwoo@test.com",
      "wonwoo",
      "wonwoo",
      "https://avatars.githubusercontent.com/u/747472?v=3", "password", true));
    final Post post = this.testEntityManager.persist(new Post("test title", "test content", "test content", "Y", user, Arrays.asList("spring", "jpa")));
    final Category category = this.testEntityManager.persist(new Category("spring"));
    final CategoryPost categoryPost = categoryPostRepository.save(new CategoryPost(category, post));
    assertThat(categoryPost.getPost()).isEqualTo(post);
    assertThat(categoryPost.getCategory()).isEqualTo(category);

  }
}