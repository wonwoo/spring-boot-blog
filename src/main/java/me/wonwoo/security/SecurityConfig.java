package me.wonwoo.security;

import lombok.RequiredArgsConstructor;
import me.wonwoo.domain.model.User;
import me.wonwoo.domain.repository.UserRepository;
import me.wonwoo.github.GithubClient;
import me.wonwoo.github.GithubUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;

/**
 * Created by wonwoo on 2016. 8. 23..
 */
@Configuration
@EnableOAuth2Sso
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    private final GitProperties gitProperties;

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
                .antMatchers("/about", "/contact", "/guides/**", "/tut/**","/wordPress/**" , "/github/page/**").permitAll()
                .antMatchers("/", "/js/**", "/vendor/**", "/codemirror/**", "/markdown/**", "/login/**", "/css/**", "/img/**", "/webjars/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf()
                .ignoringAntMatchers("/admin/h2-console/*")
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .permitAll()
                .and()
                .headers()
                .frameOptions().sameOrigin();
    }

    @Bean
    public AuthoritiesExtractor authoritiesExtractor() {
        return map -> {
            String username = (String) map.get("login");
            if (this.gitProperties.getSecurity().getAdmins().contains(username)) {
                return AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER,ROLE_ADMIN");
            } else {
                return AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");
            }
        };
    }

    @Bean
    public PrincipalExtractor principalExtractor(GithubClient githubClient, UserRepository userRepository) {
        return map -> {
            String githubLogin = (String) map.get("login");
            User speaker = userRepository.findByGithub(githubLogin);
            if (speaker == null) {
                logger.info("Initialize user with githubId {}", githubLogin);
                GithubUser user = githubClient.getUser(githubLogin);
                speaker = new User(user.getEmail(), user.getName(), githubLogin, user.getAvatar());
                userRepository.save(speaker);
            }
            return speaker;
        };
    }
}