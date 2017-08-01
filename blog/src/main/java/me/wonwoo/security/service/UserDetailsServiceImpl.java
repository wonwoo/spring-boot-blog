package me.wonwoo.security.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.wonwoo.domain.model.User;
import me.wonwoo.domain.repository.UserRepository;
import me.wonwoo.security.UserNotFoundException;

/**
 * Created by wonwoo on 2016. 10. 15..
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) {
    Optional<User> user = userRepository.findByGithub(username);
    return user.orElseThrow(() -> new UserNotFoundException(username));
  }
}