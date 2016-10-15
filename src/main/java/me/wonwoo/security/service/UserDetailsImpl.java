package me.wonwoo.security.service;

import me.wonwoo.domain.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by wonwoo on 2016. 10. 15..
 */
public class UserDetailsImpl extends org.springframework.security.core.userdetails.User {

  public UserDetailsImpl(User user) {
    super(user.getGithub(), user.getPassword(), authorities(user));
  }

  private static Collection<? extends GrantedAuthority> authorities(User user) {
    List<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
    if (user.isAdmin()) {
      authorities.addAll(AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER, ROLE_ADMIN"));
    }
    return authorities;
  }
}