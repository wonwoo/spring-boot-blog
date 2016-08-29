package me.wonwoo;

import me.wonwoo.config.PostProperties;
import me.wonwoo.security.GitProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

@SpringBootApplication
@EntityScan(basePackageClasses = {SpringBootBlogApplication.class, Jsr310JpaConverters.class})
@EnableConfigurationProperties({GitProperties.class, PostProperties.class})
@EnableJpaAuditing
@EnableCaching
public class SpringBootBlogApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringBootBlogApplication.class, args);
  }


  @Bean
  public JCacheManagerCustomizer cacheManagerCustomizer() {
    return cm -> {
      cm.createCache("spring.blog.category", initConfiguration(Duration.ONE_MINUTE));
      cm.createCache("github.user", initConfiguration(Duration.ONE_HOUR));
    };
  }

  private MutableConfiguration<Object, Object> initConfiguration(Duration duration) {
    return new MutableConfiguration<>()
      .setStoreByValue(false)
      .setStatisticsEnabled(true)
      .setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(duration));
  }

  @Bean
  public RestTemplate restTemplate(GitProperties gitProperties) {
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.setInterceptors(Collections.singletonList(
      new GithubAppTokenInterceptor(gitProperties.getGithub().getToken())));
    return restTemplate;
  }

  private static class GithubAppTokenInterceptor implements ClientHttpRequestInterceptor {

    private final String token;

    GithubAppTokenInterceptor(String token) {
      this.token = token;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes,
                                        ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
      if (StringUtils.hasText(this.token)) {
        byte[] basicAuthValue = this.token.getBytes(StandardCharsets.UTF_8);
        httpRequest.getHeaders().set(HttpHeaders.AUTHORIZATION,
          "Basic " + Base64Utils.encodeToString(basicAuthValue));
      }
      return clientHttpRequestExecution.execute(httpRequest, bytes);
    }

  }

  @Bean
  public Java8TimeDialect java8TimeDialect() {
    return new Java8TimeDialect();
  }
//
//	//test data
//	@Bean
//	CommandLineRunner commandLineRunner(CategoryRepository categoryRepository, PostRepository postRepository, UserRepository userRepository){
//		return args -> {
//			User wonwoo = userRepository.save(new User("aoruqjfu@gmail.com", "wonwoo", "", ""));
//			Category category = new Category(1L,"spring");
//			categoryRepository.save(category);
//			categoryRepository.save(new Category(2L,"java"));
//			postRepository.save(Arrays.asList(
//				new Post("first Title", "<pre><code class=\"language-java\"><span class=\"hljs-function\"><span class=\"hljs-keyword\">public</span> <span class=\"hljs-keyword\">static</span> <span class=\"hljs-keyword\">void</span> <span class=\"hljs-title\">main</span><span class=\"hljs-params\">()</span></span>{\n\n}\n</code>\n</pre>", category,wonwoo),
//				new Post("second Title", "second Content", category,wonwoo),
//				new Post("third Title1", "third Content", category,wonwoo),
//				new Post("third Title", "third Content", category,wonwoo),
//				new Post("third Title", "third Content", category,wonwoo),
//				new Post("third Title", "third Content", category,wonwoo),
//				new Post("third Title", "third Content", category,wonwoo),
//				new Post("third Title", "third Content", category,wonwoo),
//				new Post("third Title", "third Content", category,wonwoo),
//				new Post("third Title", "third Content", category,wonwoo),
//				new Post("third Title", "third Content", category,wonwoo),
//				new Post("third Title", "third Content", category,wonwoo),
//				new Post("third Title", "third Content", category,wonwoo),
//				new Post("third Title", "third Content", category,wonwoo),
//				new Post("third Title", "third Content", category,wonwoo),
//				new Post("third Title", "third Content", category,wonwoo),
//				new Post("third Title", "third Content", category,wonwoo),
//				new Post("third Title", "third Content", category,wonwoo),
//				new Post("third Title", "third Content", category,wonwoo),
//				new Post("third Title", "third Content", category,wonwoo),
//				new Post("third 342Title", "third Content", category,wonwoo),
//				new Post("th4214rd Title", "third Content", category,wonwoo),
//				new Post("third Title", "third Content", category,wonwoo),
//				new Post("third Title", "third Content", category,wonwoo),
//				new Post("thi414d Title", "third Content", category,wonwoo),
//				new Post("third Title", "third Content", category,wonwoo),
//				new Post("third Title", "third Content", category,wonwoo),
//				new Post("thi3423rd Title", "third Content", category,wonwoo),
//				new Post("third Title", "third Content", category,wonwoo),
//				new Post("third Title", "third Content", category,wonwoo),
//				new Post("third 123Title", "third Content", category,wonwoo),
//				new Post("thir2412d Title", "third Content", category,wonwoo),
//				new Post("third Title", "third Content", category,wonwoo),
//				new Post("third Ti123tle", "third Content", category,wonwoo),
//				new Post("third Title", "third Content", category,wonwoo),
//				new Post("third Title", "third Content", category,wonwoo),
//				new Post("third Title", "third Content", category,wonwoo),
//				new Post("third Title", "third Content", category,wonwoo),
//				new Post("thir41d T4itle", "third Content", category,wonwoo),
//				new Post("th1325ird Title", "third Content", category,wonwoo),
//				new Post("third Title", "third Content", category,wonwoo),
//				new Post("third Title", "third Content", category,wonwoo),
//				new Post("thir51d Title", "third Content", category,wonwoo)
//			));
//		};
//	}
}
