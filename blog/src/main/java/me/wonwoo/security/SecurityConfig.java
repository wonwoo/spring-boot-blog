package me.wonwoo.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by wonwoo on 2016. 8. 23..
 */
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers(HttpMethod.GET, "/posts/{id}").permitAll()
        .antMatchers(HttpMethod.GET, "/posts/tags/**").permitAll()
        .antMatchers(HttpMethod.GET, "/posts/category/{id}").permitAll()
        .antMatchers(HttpMethod.GET, "/posts").hasRole("ADMIN")
        .antMatchers("/posts/**").hasRole("ADMIN")
        .antMatchers("/categories/**").hasRole("ADMIN")
        .antMatchers("/news/**").hasRole("ADMIN")
        .antMatchers("/histories/**").hasRole("ADMIN")
        .antMatchers("/about", "/contact", "/guides/**",
            "/tut/**", "/wordPress/**", "/github/page/**").permitAll()
        .antMatchers("/", "/search", "/js/**", "/fonts/**", "/vendor/**",
            "/codemirror/**", "/markdown/**", "/login/**", "/css/**", "/img/**", "/webjars/**").permitAll()
        .anyRequest().authenticated()
        .and()
        .csrf()
        .ignoringAntMatchers("/admin/h2-console/*")
        .and()
        .formLogin()
        .loginPage("/login")
        .permitAll()
        .and()
        .httpBasic()
        .and()
        .logout()
        .logoutSuccessUrl("/")
        .permitAll()
        .and()
        .headers()
        .frameOptions().sameOrigin();
  }


  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}