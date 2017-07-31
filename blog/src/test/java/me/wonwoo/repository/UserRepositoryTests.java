package me.wonwoo.repository;

import me.wonwoo.domain.model.User;
import me.wonwoo.domain.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by wonwoo on 2017. 2. 16..
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTests {
  @Autowired
  private TestEntityManager testEntityManager;

  @Autowired
  private UserRepository userRepository;

  @Test
  public void findByGithubTest() {
    this.testEntityManager.persist(new User("wonwoo@test.com",
      "wonwoo",
      "wonwoo",
      "https://avatars.githubusercontent.com/u/747472?v=3","password", true));
    User user = this.userRepository.findByGithub("wonwoo").get();
    assertThat(user.getUsername()).isEqualTo("wonwoo");
    assertThat(user.getAvatarUrl()).isEqualTo("https://avatars.githubusercontent.com/u/747472?v=3");
    assertThat(user.getName()).isEqualTo("wonwoo");
    assertThat(user.getEmail()).isEqualTo("wonwoo@test.com");
    assertThat(user.getPassword()).isEqualTo("password");
    assertThat(user.isAdmin()).isEqualTo(true);
  }

  @Test
  public void findBynameTest() {
    this.testEntityManager.persist(new User("wonwoo@test.com",
      "wonwoo",
      "wonwoo",
      "https://avatars.githubusercontent.com/u/747472?v=3","password", true));
    Collection<User> users = this.userRepository.findByName("wonwoo");
    final User user = users.iterator().next();
    assertThat(user.getUsername()).isEqualTo("wonwoo");
    assertThat(user.getAvatarUrl()).isEqualTo("https://avatars.githubusercontent.com/u/747472?v=3");
    assertThat(user.getName()).isEqualTo("wonwoo");
    assertThat(user.getEmail()).isEqualTo("wonwoo@test.com");
    assertThat(user.getPassword()).isEqualTo("password");
    assertThat(user.isAdmin()).isEqualTo(true);
  }
}