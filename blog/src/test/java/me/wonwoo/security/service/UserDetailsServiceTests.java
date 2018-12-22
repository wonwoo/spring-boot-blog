package me.wonwoo.security.service;


import java.util.Optional;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import me.wonwoo.domain.model.User;
import me.wonwoo.domain.repository.UserRepository;
import me.wonwoo.security.UserNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class UserDetailsServiceTests {

	@Mock
	private UserRepository userRepository;

	private UserDetailsService userDetailsService;

	@Rule
	public final ExpectedException exception = ExpectedException.none();

	@Before
	public void setup() {
		userDetailsService = new UserDetailsServiceImpl(userRepository);
	}

	@Test
	public void loadUserByUsername() {
		given(userRepository.findByGithub(any())).willReturn(Optional.of(
				new User("wonwoo@test.com", "wonwoo", "wonwoo", "http://localhost:9999","pass!@#", true))
		);
		UserDetails userDetails = userDetailsService.loadUserByUsername("wonwoo");
		assertThat(userDetails.getUsername()).isEqualTo("wonwoo");
		assertThat(userDetails.getPassword()).isEqualTo("pass!@#");
		assertThat(userDetails.isAccountNonLocked()).isEqualTo(true);
		assertThat(userDetails.isAccountNonExpired()).isEqualTo(true);
		assertThat(userDetails.isCredentialsNonExpired()).isEqualTo(true);
		assertThat(userDetails.isEnabled()).isEqualTo(true);
	}

	@Test
	public void loadUserByUsernameUserNotFoundException() {
		exception.expect(UserNotFoundException.class);
		given(userRepository.findByGithub(any())).willReturn(Optional.empty());
		userDetailsService.loadUserByUsername("wonwoo");
	}

}