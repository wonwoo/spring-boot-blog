package me.wonwoo.domain.repository;

import me.wonwoo.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

/**
 * Created by wonwoo on 2016. 8. 23..
 */
public interface UserRepository extends JpaRepository<User, Long> {

  User findByGithub(String github);

  Collection<User> findByName(String lastName);
}
