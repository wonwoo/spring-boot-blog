package me.wonwoo.security.service;

import lombok.RequiredArgsConstructor;
import me.wonwoo.domain.model.User;
import me.wonwoo.domain.repository.UserRepository;
import me.wonwoo.security.UserNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * Created by wonwoo on 2016. 10. 15..
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) {
    User user = userRepository.findByGithub(username);
    if(user == null){
      throw new UserNotFoundException(username);
    }
    return new UserDetailsImpl(user);
  }
}